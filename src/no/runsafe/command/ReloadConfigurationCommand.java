package no.runsafe.command;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.IKernel;
import no.runsafe.framework.api.command.console.ConsoleCommand;
import no.runsafe.framework.internal.configuration.ConfigurationEngine;

import java.util.List;
import java.util.Map;

public class ReloadConfigurationCommand extends ConsoleCommand
{
	public ReloadConfigurationCommand()
	{
		super("reload", "Reloads configuration of runsafe plugins", new PluginArgument());
	}

	@Override
	public String OnExecute(Map<String, String> params)
	{
		List<RunsafePlugin> targets = RunsafePlugin.getPlugins(params.get("plugin"));
		for (IKernel kernel : targets)
			kernel.getComponent(ConfigurationEngine.class).load();
		return "Configuration reloaded";
	}
}
