package no.runsafe;

import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.IRepository;
import no.runsafe.framework.database.ISchemaUpdater;
import no.runsafe.framework.database.SchemaRevisionRepository;
import no.runsafe.framework.event.player.IPlayerJoinEvent;
import no.runsafe.framework.event.player.IPlayerQuitEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.event.player.RunsafePlayerJoinEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerQuitEvent;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDatabase implements ISchemaUpdater, IPlayerJoinEvent, IPlayerQuitEvent, IRepository<PlayerData, String> {
	public PlayerDatabase(SchemaRevisionRepository revisionRepository, IOutput console, IDatabase database) {
		repository = revisionRepository;
		this.console = console;
		this.database = database;
	}

	@Override
	public void Run() {
		int revision = repository.getRevision("player_db");
		if(revision < 1) {
			console.write("Creating table player_db");
			PreparedStatement create = database.prepare(
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
			try {
				create.execute();
				revision = 1;
			} catch(SQLException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
		repository.setRevision("player_db", revision);
	}

	@Override
	public void OnPlayerJoinEvent(RunsafePlayerJoinEvent event) {
		console.fine("Updating player_db with login time");
		PreparedStatement update = database.prepare(
				"INSERT INTO player_db (`name`,`joined`,`login`,`ip`) VALUES (?,NOW(),NOW(),INET_ATON(?))" +
						"ON DUPLICATE KEY UPDATE `login`=VALUES(`login`), `ip`=VALUES(`ip`)"
		);
		try {
			update.setString(1, event.getPlayer().getName());
			update.setString(2, event.getPlayer().getRawPlayer().getAddress().getAddress().getHostAddress());
			update.executeUpdate();
			console.fine("Finished updating player_db with login time");
		} catch(SQLException e) {
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
		try {
			update.setString(1, event.getPlayer().getName());
			update.executeUpdate();
			console.fine("Finished updating player_db with logout time");
		} catch(SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	@Override
	public PlayerData get(String player) {
		PlayerData result = null;
		PreparedStatement select = database.prepare(
			"SELECT name, joined, login, logout, banned FROM player_db WHERE name=?"
		);
		try {
			select.setString(1, player);
			ResultSet data = select.executeQuery();
			if(data.first())
			{
				result = new PlayerData();
				result.setName(data.getString(1));
				result.setJoined(data.getDate(2));
				result.setLogin(data.getDate(3));
				result.setLogout(data.getDate(4));
				result.setBanned(data.getDate(5));
			}
		} catch(SQLException e) {
			console.write(e.getMessage());
		}
		return result;
	}

	@Override
	public void persist(PlayerData data) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void delete(PlayerData data) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private SchemaRevisionRepository repository;
	private IOutput console;
	private IDatabase database;
}