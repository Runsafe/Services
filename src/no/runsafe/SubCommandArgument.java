package no.runsafe;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.argument.IContextualTabComplete;
import no.runsafe.framework.api.command.argument.OptionalArgument;
import no.runsafe.framework.api.player.IPlayer;
import org.bukkit.Server;

import java.util.List;

public class SubCommandArgument extends OptionalArgument implements IContextualTabComplete
{
	public SubCommandArgument(RunsafePlugin plugin)
	{
		super("subcommand-path", null);
		server = plugin.getServer();
	}

	@Override
	public List<String> getAlternatives(IPlayer executor, String partial, String... predecessors)
	{
//		PluginCommand command = server.getPluginCommand(predecessors[0]);
//		CommandExecutor commandExecutor = command.getExecutor();
//		if (commandExecutor instanceof ITabExecutor)
//			((ITabExecutor) commandExecutor).getHandler().getTargetSubCommand(executor, )
		return null;
	}

	Server server;
}
