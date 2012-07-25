package no.runsafe.command;

import no.runsafe.framework.command.ICommand;
import no.runsafe.framework.command.RunsafeConsoleCommand;
import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.server.player.RunsafePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class EssentialsPlayers extends RunsafeConsoleCommand {
	public EssentialsPlayers(IDatabase db) {
		super("essentials", null);
		database = db;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, String[] args) {
		PreparedStatement update = database.prepare(
				"INSERT INTO player_db (`name`,`joined`,`login`,`logout`,`ip`,`banned`,`ban_reason`) VALUES (?,?,?,?,INET_ATON(?),?,?)" +
						"ON DUPLICATE KEY UPDATE " +
						"`joined`=VALUES(`joined`)," +
						"`login`=VALUES(`login`), " +
						"`logout`=VALUES(`logout`)," +
						"`ip`=VALUES(`ip`)," +
						"`banned`=VALUES(`banned`)," +
						"`ban_reason`=VALUES(`ban_reason`)"
		);

		File sourceDir = new File("plugins/Essentials/userdata");

		for(File file : sourceDir.listFiles())
		{
			YamlConfiguration playerData = new YamlConfiguration();
			try {
				playerData.load(file);
			} catch(IOException e) {
				Console.write(String.format("Error reading file %s", file.getName()));
			} catch(InvalidConfigurationException e) {
				Console.write(String.format("Error reading file %s - %s", file.getName(), e.getMessage()));
			}

			Timestamp login = new Timestamp(playerData.getLong("timestamps.login"));
			Timestamp logout = null;
			if(playerData.contains("timestamps.logout"))
				logout = new Timestamp(playerData.getLong("timestamps.logout"));
			String ip = playerData.getString("ipAddress");

			String banReason = null;
			if(playerData.contains("ban.reason"))
				banReason = playerData.getString("ban.reason");

			try {
				update.setString(1, file.getName().replace(".yml", ""));
				update.setTimestamp(2, login);
				update.setTimestamp(3, login);
				update.setTimestamp(4, logout);
				update.setString(5, ip);
				update.setTimestamp(6, banReason == null ? null : logout);
				update.setString(7, banReason);
				update.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
		return "Player data loaded from Essentials.";
	}

	private IDatabase database;
}
