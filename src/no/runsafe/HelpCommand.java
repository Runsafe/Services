package no.runsafe;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.internal.command.ITabExecutor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

public class HelpCommand extends ExecutableCommand
{
	public HelpCommand(RunsafePlugin plugin)
	{
		super("help", "Get command usage help", "runsafe.help", new RequiredArgument("command"), new TrailingArgument("subcommand-path", false));
		this.plugin = plugin;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		PluginCommand command = plugin.getCommand(parameters.get("command"));
		CommandExecutor commandExecutor = command.getExecutor();
		if (commandExecutor instanceof ITabExecutor)
		{
			String path = parameters.get("subcommand-path");
			if (path != null)
				return ((ITabExecutor) commandExecutor).getHandler().getSubCommandUsage(executor, path.split("\\s+"));
			return ((ITabExecutor) commandExecutor).getHandler().getUsage(executor);
		}

		return command.getUsage();
	}

	private final RunsafePlugin plugin;
}
