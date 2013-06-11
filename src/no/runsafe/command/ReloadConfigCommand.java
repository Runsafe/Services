package no.runsafe.command;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.console.ConsoleCommand;
import no.runsafe.framework.internal.configuration.ConfigurationEngine;

import java.util.HashMap;

public class ReloadConfigCommand extends ConsoleCommand
{
	public ReloadConfigCommand()
	{
		super("reload", "Reloads configuration of runsafe plugins");
	}

	@Override
	public String OnExecute(HashMap<String, String> params)
	{
		for (ConfigurationEngine config : RunsafePlugin.getPluginAPI(ConfigurationEngine.class))
			config.load();
		return "Configuration reloaded";
	}
}
