package no.runsafe;

import no.runsafe.command.DebugLevelCommand;
import no.runsafe.command.ReloadConfigCommand;
import no.runsafe.command.RunsafePluginVersions;
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

		Command config = new Command("config", "Commands to control plugin configurations", null);
		config.addSubCommand(getInstance(ReloadConfigCommand.class));
		command.addSubCommand(config);

		command.addSubCommand(getInstance(DebugLevelCommand.class));
		command.addSubCommand(getInstance(RunsafePluginVersions.class));

		addComponent(command);
	}
}
