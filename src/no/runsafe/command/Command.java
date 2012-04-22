package no.runsafe.command;

import no.runsafe.framework.IKernel;
import no.runsafe.framework.command.ICommand;
import no.runsafe.framework.command.RunsafeCommand;

import java.util.ArrayList;
import java.util.Collection;

public class Command extends RunsafeCommand
{
	public Command(IKernel kernel)
	{
		super("runsafe", new ArrayList<ICommand>());
		addSubCommand(kernel.getInstance(no.runsafe.command.config.Command.class));
	}
}
