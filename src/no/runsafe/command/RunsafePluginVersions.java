package no.runsafe.command;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.command.ICommand;
import no.runsafe.framework.command.RunsafeCommand;
import no.runsafe.framework.server.player.RunsafePlayer;

import java.util.Collection;

public class RunsafePluginVersions extends RunsafeCommand {
	public RunsafePluginVersions() {
		super("plugins", null);
	}

	@Override
	public String requiredPermission() {
		return "runsafe.plugin.list";
	}

	@Override
	public String OnExecute(RunsafePlayer executor, String[] args) {
		StringBuilder result = new StringBuilder();
		for(String plugin : RunsafePlugin.Instances.keySet())
			result.append(String.format("%s %s\n", plugin, RunsafePlugin.Instances.get(plugin).getDescription().getVersion()));

		return result.toString();
	}
}
