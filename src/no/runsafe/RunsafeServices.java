package no.runsafe;

import no.runsafe.command.*;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.features.Commands;

public class RunsafeServices extends RunsafePlugin
{
	@Override
	protected void pluginSetup()
	{
		addComponent(Commands.class);

		Command command = new Command("runsafe", "Runsafe core services", null);

		Command config = new Command("configuration", "Commands to control plugin configurations", null);
		config.addSubCommand(getInstance(ReloadConfigurationCommand.class));
		config.addSubCommand(getInstance(SetConfigurationCommand.class));
		config.addSubCommand(getInstance(ResetConfigurationCommand.class));
		command.addSubCommand(config);

		command.addSubCommand(getInstance(DebugLevelCommand.class));
		command.addSubCommand(getInstance(RunsafePluginVersions.class));

		addComponent(command);
	}
}
