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
		{
			Map<String, String[]> aliases = plugin.getServer().getCommandAliases();
			for (String altcommand : aliases.keySet())
			{
				for (String alias : aliases.get(command))
				{
					if (alias != null && alias.equalsIgnoreCase(command))
					{
						commandObject = plugin.getServer().getPluginCommand(altcommand);
						break;
					}
				}
				if (commandObject != null)
					break;
			}
		}
		if (commandObject == null)
			return "&cNo command found matching '" + command + "'!";

		if (command.equalsIgnoreCase(commandObject.getName()))
			return "The command '" + commandObject.getName() + "' is provided by '" + commandObject.getPlugin();

		else
			return '\'' + command + "' maps to the command '" + commandObject.getName() + "' which is provided by '" + commandObject.getPlugin() + '\'';

	}

	private final RunsafePlugin plugin;
}
