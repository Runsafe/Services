package no.runsafe;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import org.bukkit.command.PluginCommand;

import java.util.Map;

public class WhatProvidesCommand extends ExecutableCommand
{
	public WhatProvidesCommand(RunsafePlugin plugin)
	{
		super("whatprovides", "Check what plugin implements a given command", "runsafe.whatprovides", new RequiredArgument("command"));
		this.plugin = plugin;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		String command = parameters.getRequired("command");
		PluginCommand commandObject = plugin.getServer().getPluginCommand(command);
		if (commandObject == null)
			commandObject = findCommandByAlias(command);
		if (commandObject == null)
			return "&cNo regular command found matching '" + command + "'!";

		if (command.equalsIgnoreCase(commandObject.getName()))
			return "The command '" + commandObject.getName() + "' is provided by '" + commandObject.getPlugin() + '\'';
		else
			return '\'' + command + "' maps to the command '" + commandObject.getName() + "' which is provided by '" + commandObject.getPlugin() + '\'';

	}

	private PluginCommand findCommandByAlias(String command)
	{
		for (Map.Entry<String, String[]> aliasedCommand : plugin.getServer().getCommandAliases().entrySet())
			for (String alias : aliasedCommand.getValue())
				if (alias != null && alias.equalsIgnoreCase(command))
					return plugin.getServer().getPluginCommand(aliasedCommand.getKey());
		return null;
	}

	private final RunsafePlugin plugin;
}
