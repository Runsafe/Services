package no.runsafe.command;


import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.command.RunsafeConsoleCommand;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.player.RunsafePlayer;

import java.util.logging.Level;

public class DebugLevelCommand extends RunsafeConsoleCommand
{
	public DebugLevelCommand(IOutput output)
	{
		super("debuglevel", null, "plugin", "level");
		this.output = output;
	}

	@Override
	public String getCommandParams()
	{
		String part = commandName + " *|<plugin> NONE|SEVERE|WARNING|INFO|CONFIG|FINE|FINER|FINEST|ALL";
		if (superCommand != null)
			return superCommand.getCommandParams() + " " + part;
		return part;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, String[] args)
	{
		StringBuilder result = new StringBuilder(String.format("Setting debug level for %s to %s:\n", getArg("plugin"), getArg("level")));
		String pluginName = getArg("plugin");
		Level level = Level.parse(getArg("level"));
		for (RunsafePlugin plugin : RunsafePlugin.Instances.values())
		{
			if ("*".equals(getArg("plugin")) || plugin.getName().startsWith(pluginName))
			{
				IOutput output = plugin.getComponent(IOutput.class);
				output.setDebugLevel(level);
				result.append(String.format("[%s] Debug level is %s.\n", plugin.getName(), output.getDebugLevel().getName()));
			}
		}
		return result.toString();
	}

	private final IOutput output;
}
