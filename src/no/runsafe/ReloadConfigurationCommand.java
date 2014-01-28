package no.runsafe;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.IKernel;
import no.runsafe.framework.api.command.console.ConsoleCommand;
import no.runsafe.framework.features.Configuration;

import java.util.List;
import java.util.Map;

public class ReloadConfigurationCommand extends ConsoleCommand
{
	public ReloadConfigurationCommand()
	{
		super("reload", "Reloads configuration of runsafe plugins", new PluginArgument());
	}

	@Override
	public String OnExecute(Map<String, String> parameters)
	{
		List<RunsafePlugin> targets = RunsafePlugin.getPlugins(parameters.get("plugin"));
		for (IKernel kernel : targets)
		{
			Configuration feature = kernel.getComponent(Configuration.class);
			if (feature != null)
				feature.start();
		}
		return "Configuration reloaded";
	}
}
