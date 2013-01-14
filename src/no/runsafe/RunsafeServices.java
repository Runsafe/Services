package no.runsafe;

import no.runsafe.command.DebugLevelCommand;
import no.runsafe.command.ReloadConfigCommand;
import no.runsafe.command.RunsafePluginVersions;
import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.command.Command;
import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IPluginEnabled;
import no.runsafe.framework.messaging.IMessagePump;
import no.runsafe.framework.messaging.IPumpProvider;
import no.runsafe.framework.messaging.MessagePump;
import no.runsafe.framework.output.IOutput;

import java.util.logging.Level;

public class RunsafeServices extends RunsafeConfigurablePlugin implements IPumpProvider, IPluginEnabled
{
	@Override
	protected void PluginSetup()
	{
		this.addComponent(MessagePump.class);

		Command command = new Command("runsafe", "Runsafe core services", null);

		Command config = new Command("config", "Commands to control plugin configurations", null);
		config.addSubCommand(getInstance(ReloadConfigCommand.class));
		command.addSubCommand(config);

		command.addSubCommand(getInstance(DebugLevelCommand.class));
		command.addSubCommand(getInstance(RunsafePluginVersions.class));

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
