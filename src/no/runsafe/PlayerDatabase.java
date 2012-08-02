package no.runsafe;

import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.IRepository;
import no.runsafe.framework.database.ISchemaChanges;
import no.runsafe.framework.event.player.IPlayerJoinEvent;
import no.runsafe.framework.event.player.IPlayerQuitEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.player.RunsafePlayerJoinEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerQuitEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerDatabase implements ISchemaChanges, IPlayerJoinEvent, IPlayerQuitEvent, IRepository<PlayerData, String>
{
	public PlayerDatabase(IOutput console, IDatabase database)
	{
		this.console = console;
		this.database = database;
	}

	@Override
	public String getTableName()
	{
		return "player_db";
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> queries = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(
			"CREATE TABLE player_db (" +
				"`name` varchar(255) NOT NULL," +
				"`joined` datetime NOT NULL," +
				"`login` datetime NOT NULL," +
				"`logout` datetime NULL," +
				"`banned` datetime NULL," +
				"`ban_reason` varchar(255) NULL," +
				"`ban_by` varchar(255) NULL," +
				"`ip` int unsigned NULL," +
				"PRIMARY KEY(`name`)" +
				")"
		);
		queries.put(1, sql);
		return queries;
	}

	@Override
	public void OnPlayerJoinEvent(RunsafePlayerJoinEvent event)
	{
		console.fine("Updating player_db with login time");
		PreparedStatement update = database.prepare(
			"INSERT INTO player_db (`name`,`joined`,`login`,`ip`) VALUES (?,NOW(),NOW(),INET_ATON(?))" +
				"ON DUPLICATE KEY UPDATE `login`=VALUES(`login`), `ip`=VALUES(`ip`)"
		);
		try
		{
			update.setString(1, event.getPlayer().getName());
			update.setString(2, event.getPlayer().getRawPlayer().getAddress().getAddress().getHostAddress());
			update.executeUpdate();
			console.fine("Finished updating player_db with login time");
		}
		catch (SQLException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	@Override
	public void OnPlayerQuit(RunsafePlayerQuitEvent event)
	{
		console.fine("Updating player_db with logout time");
		PreparedStatement update = database.prepare(
			"UPDATE player_db SET `logout`=NOW() WHERE `name`=?"
		);
		try
		{
			update.setString(1, event.getPlayer().getName());
			update.executeUpdate();
			console.fine("Finished updating player_db with logout time");
		}
		catch (SQLException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	@Override
	public PlayerData get(String player)
	{
		PlayerData result = null;
		PreparedStatement select = database.prepare(
			"SELECT name, joined, login, logout, banned FROM player_db WHERE name=?"
		);
		try
		{
			select.setString(1, player);
			ResultSet data = select.executeQuery();
			if (data.first())
			{
				result = new PlayerData();
				result.setName(data.getString(1));
				result.setJoined(data.getDate(2));
				result.setLogin(data.getDate(3));
				result.setLogout(data.getDate(4));
				result.setBanned(data.getDate(5));
			}
		}
		catch (SQLException e)
		{
			console.write(e.getMessage());
		}
		return result;
	}

	@Override
	public void persist(PlayerData data)
	{
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void delete(PlayerData data)
	{
		throw new UnsupportedOperationException("Not implemented");
	}

	private IOutput console;
	private IDatabase database;
}