package no.runsafe.command;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.IKernel;
import no.runsafe.framework.api.command.console.ConsoleCommand;
import no.runsafe.framework.internal.configuration.ConfigurationEngine;

import java.util.Map;

public class ResetConfigurationCommand extends ConsoleCommand
{
	public ResetConfigurationCommand()
	{
		super("reset", "Resets the configuration for the selected plugin to the defaults.", new PluginArgument());
	}

	@Override
	public String OnExecute(Map<String, String> parameters)
	{
		IKernel plugin = RunsafePlugin.getPlugin(parameters.get("plugin"));
		if (plugin == null)
			return "Invalid plugin specified";
		ConfigurationEngine engine = plugin.getComponent(ConfigurationEngine.class);
		if (engine.restoreToDefaults())
			return String.format("All configuration for the plugin %s has been rolled back to their defaults.", parameters.get("plugin"));

		return "Negative on that, Houston!";
	}
}
