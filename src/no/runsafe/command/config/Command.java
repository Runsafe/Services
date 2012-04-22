package no.runsafe.command.config;

import no.runsafe.framework.IKernel;
import no.runsafe.framework.command.ICommand;
import no.runsafe.framework.command.RunsafeCommand;

import java.util.ArrayList;

public class Command extends RunsafeCommand
{
	public Command(IKernel kernel)
	{
		super("config", new ArrayList<ICommand>());
		addSubCommand(kernel.getInstance(ReloadCommand.class));
	}
}
