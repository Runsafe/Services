package no.runsafe;

import no.runsafe.command.*;
import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.command.RunsafeCommand;
import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.configuration.IConfigurationFile;
import no.runsafe.framework.event.IPluginEnabled;
import no.runsafe.framework.messaging.IMessagePump;
import no.runsafe.framework.messaging.IPumpProvider;
import no.runsafe.framework.messaging.MessagePump;
import no.runsafe.framework.output.IOutput;

import java.io.InputStream;
import java.util.logging.Level;

public class RunsafeServices extends RunsafeConfigurablePlugin implements IPumpProvider, IPluginEnabled
{
	@Override
	protected void PluginSetup()
	{
		this.addComponent(MessagePump.class);

		RunsafeCommand command = new RunsafeCommand("runsafe");

		RunsafeCommand config = new RunsafeCommand("config");
		config.addSubCommand(getInstance(ReloadConfigCommand.class));
		command.addSubCommand(config);

		command.addSubCommand(getInstance(DebugLevelCommand.class));
		command.addSubCommand(getInstance(RunsafePluginVersions.class));

		//RunsafeCommand imports = new RunsafeCommand("import");
		//imports.addSubCommand(getInstance(EssentialsPlayers.class));

		//command.addSubCommand(imports);

		addComponent(command);
	}

	public IMessagePump getInstance()
	{
		return getComponent(IMessagePump.class);
	}

	@Override
	public void OnPluginEnabled()
	{
		String level = getComponent(IConfiguration.class).getConfigValueAsString("debug");
		if (level != null)
		{
			Level debugLevel = Level.parse(level);
			for (RunsafePlugin plugin : RunsafePlugin.Instances.values())
				plugin.getComponent(IOutput.class).setDebugLevel(debugLevel);
		}
	}
}
