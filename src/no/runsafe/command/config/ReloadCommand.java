package no.runsafe.command.config;

import no.runsafe.framework.command.RunsafeCommand;
import no.runsafe.framework.messaging.IMessagePump;
import no.runsafe.framework.messaging.Message;

public class ReloadCommand extends RunsafeCommand
{
	public ReloadCommand(IMessagePump pump)
	{
		super("reload", null);
		messagePump = pump;
	}

	@Override
	public boolean Execute(String[] args)
	{
		Message reloadConfigMessage = new Message();
		reloadConfigMessage.setTargetService("Configuration");
		reloadConfigMessage.setQuestion("config.reload");
		messagePump.HandleMessageAll(reloadConfigMessage);
		return true;
	}

	private IMessagePump messagePump;
}
