package no.runsafe;

import no.runsafe.command.EssentialsPlayers;
import no.runsafe.command.ReloadConfigCommand;
import no.runsafe.command.RunsafePluginVersions;
import no.runsafe.command.SetConsoleDebugLevelCommand;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.command.RunsafeCommand;
import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.configuration.IConfigurationFile;
import no.runsafe.framework.database.ISchemaUpdater;
import no.runsafe.framework.event.IPluginEnabled;
import no.runsafe.framework.messaging.IMessagePump;
import no.runsafe.framework.messaging.IPumpProvider;
import no.runsafe.framework.messaging.MessagePump;
import no.runsafe.framework.output.IOutput;

import java.io.InputStream;
import java.util.logging.Level;

public class RunsafeServices extends RunsafePlugin implements IPumpProvider, IConfigurationFile, IPluginEnabled {

	@Override
	protected void PluginSetup() {
		this.addComponent(new MessagePump());

		RunsafeCommand command = new RunsafeCommand("runsafe", null);

		RunsafeCommand config = new RunsafeCommand("config", null);
		config.addSubCommand(getInstance(ReloadConfigCommand.class));
		command.addSubCommand(config);

		command.addSubCommand(getInstance(SetConsoleDebugLevelCommand.class));
		command.addSubCommand(getInstance(RunsafePluginVersions.class));

		RunsafeCommand imports = new RunsafeCommand("import", null);
		imports.addSubCommand(getInstance(EssentialsPlayers.class));

		command.addSubCommand(imports);

		addComponent(command);

		addComponent(PlayerDatabase.class);
	}

	public IMessagePump getInstance() {
		return getComponent(IMessagePump.class);
	}

	@Override
	public String getConfigurationPath()
	{
		return "plugins/" + this.getName() + "/config.yml";
	}

	@Override
	public InputStream getDefaultConfiguration()
	{
		return getResource("defaults.yml");
	}

	@Override
	public void OnPluginEnabled() {
		String level = getComponent(IConfiguration.class).getConfigValueAsString("debug");
		if(level != null)
		{
			Level debugLevel = Level.parse(level);
			for(RunsafePlugin plugin : RunsafePlugin.Instances.values())
				plugin.getComponent(IOutput.class).setDebugLevel(debugLevel);
		}
	}
}
