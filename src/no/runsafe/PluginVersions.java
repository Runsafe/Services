package no.runsafe;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;

import java.util.Map;

public class PluginVersions extends ExecutableCommand
{
	public PluginVersions()
	{
		super("plugins", "Lists information about plugins using the runsafe framework", "runsafe.plugin.list");
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		StringBuilder result = new StringBuilder(0);
		for (RunsafePlugin plugin : RunsafePlugin.getPlugins("*"))
			result.append(String.format("%s %s\n", plugin.getName(), plugin.getDescription().getVersion()));

		return result.toString();
	}
}
