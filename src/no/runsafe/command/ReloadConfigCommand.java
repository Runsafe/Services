package no.runsafe.command;

import no.runsafe.framework.command.RunsafeCommand;
import no.runsafe.framework.messaging.IMessagePump;
import no.runsafe.framework.messaging.Message;
import no.runsafe.framework.server.player.RunsafePlayer;

public class ReloadConfigCommand extends RunsafeCommand
{
	public ReloadConfigCommand(IMessagePump pump)
	{
		super("reload", null);
		messagePump = pump;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, String[] args)
	{
		Message reloadConfigMessage = new Message();
		reloadConfigMessage.setTargetService("Configuration");
		reloadConfigMessage.setQuestion("config.reload");
		messagePump.HandleMessageAll(reloadConfigMessage);
		return "Configuration reloaded";
	}

	private final IMessagePump messagePump;
}
