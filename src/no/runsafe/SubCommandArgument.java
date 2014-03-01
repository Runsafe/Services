package no.runsafe;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.ICommandHandler;
import no.runsafe.framework.api.command.argument.IContextualTabComplete;
import no.runsafe.framework.api.command.argument.OptionalArgument;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.command.ITabExecutor;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubCommandArgument extends OptionalArgument implements IContextualTabComplete
{
	public SubCommandArgument(RunsafePlugin plugin)
	{
		super("subcommand-path");
		server = plugin.getServer();
	}

	@Override
	public boolean isWhitespaceInclusive()
	{
		return true;
	}

	@Override
	public List<String> getAlternatives(IPlayer executor, String partial, String... predecessors)
	{
		PluginCommand command = server.getPluginCommand(predecessors[0]);
		CommandExecutor commandExecutor = command.getExecutor();
		if (commandExecutor instanceof ITabExecutor)
		{
			ICommandHandler targetCommand = ((ITabExecutor) commandExecutor).getHandler()
				.getTargetSubCommand(executor, Arrays.copyOfRange(predecessors, 1, predecessors.length));
			if (targetCommand != null)
				return targetCommand.getSubCommands(executor);
		}
		return Collections.emptyList();
	}

	Server server;
}
