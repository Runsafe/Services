package no.runsafe.command;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;

import java.util.Map;

public class RunsafePluginVersions extends ExecutableCommand
{
	public RunsafePluginVersions()
	{
		super("plugins", "Lists information about plugins using the runsafe framework", "runsafe.plugin.list");
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> params)
	{
		StringBuilder result = new StringBuilder();
		for (RunsafePlugin plugin : RunsafePlugin.getPlugins("*"))
			result.append(String.format("%s %s\n", plugin.getName(), plugin.getDescription().getVersion()));

		return result.toString();
	}
}
