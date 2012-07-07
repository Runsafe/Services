package no.runsafe;

import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.ISchemaUpdater;
import no.runsafe.framework.database.SchemaRevisionRepository;
import no.runsafe.framework.output.IOutput;

import java.sql.PreparedStatement;

public class PlayerDatabase implements ISchemaUpdater {
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
							"`seen` datetime NOT NULL," +
							"`banned` datetime NULL," +
							"`ban_reason` varchar(255) NULL," +
							"`ban_by` varchar(255) NULL," +
							"`ip` int unsigned NULL" +
							")"
			);
			revision = 1;
		}
		repository.setRevision("player_db", revision);
	}

	private SchemaRevisionRepository repository;
	private IOutput console;
	private IDatabase database;
}