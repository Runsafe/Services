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
		if ("*".equals(getArg("plugin")))
		{
			for (RunsafePlugin plugin : RunsafePlugin.Instances.values())
				plugin.getComponent(IOutput.class).setDebugLevel(Level.parse(getArg("level")));
		}
		else
		{
			RunsafePlugin plugin = RunsafePlugin.Instances.get(getArg("plugin"));
			if (plugin == null)
				return String.format("Unknown plugin %s", getArg("plugin"));

			plugin.getComponent(IOutput.class).setDebugLevel(Level.parse(getArg("level")));
		}
		return String.format("Debug level is now %s.", output.getDebugLevel().getName());
	}

	private final IOutput output;
}
