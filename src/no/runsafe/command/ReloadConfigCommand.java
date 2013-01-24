package no.runsafe.command;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.command.console.ConsoleCommand;
import no.runsafe.framework.configuration.IConfiguration;

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
		for (IConfiguration config : RunsafePlugin.getPluginAPI(IConfiguration.class))
			config.load();
		return "Configuration reloaded";
	}
}
