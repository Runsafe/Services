package no.runsafe;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.IKernel;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.console.ConsoleCommand;
import no.runsafe.framework.features.Configuration;

public class ReloadConfigurationCommand extends ConsoleCommand
{
	public ReloadConfigurationCommand()
	{
		super("reload", "Reloads configuration of runsafe plugins", new PluginArgument());
	}

	@Override
	public String OnExecute(IArgumentList parameters)
	{
		Iterable<RunsafePlugin> plugins = parameters.getValue("plugin");
		if (plugins == null)
			return null;
		for (IKernel kernel : plugins)
		{
			Configuration feature = kernel.getComponent(Configuration.class);
			if (feature != null)
				feature.start();
		}
		return "Configuration reloaded";
	}
}
