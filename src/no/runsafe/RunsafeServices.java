package no.runsafe;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.command.RunsafeCommand;
import no.runsafe.framework.messaging.IMessagePump;
import no.runsafe.framework.messaging.IPumpProvider;
import no.runsafe.framework.messaging.MessagePump;

public class RunsafeServices extends RunsafePlugin implements IPumpProvider {
	@Override
	protected void PluginSetup() {
		this.addComponent(new MessagePump());
		RunsafeCommand command = new RunsafeCommand("runsafe", null);

		RunsafeCommand config = new RunsafeCommand("config", null);
		config.addSubCommand(getInstance(ReloadConfigCommand.class));
		command.addSubCommand(config);

		command.addSubCommand(getInstance(SetConsoleDebugLevelCommand.class));
		command.addSubCommand(getInstance(RunsafePluginVersions.class));

		addComponent(command);
	}

	public IMessagePump getInstance() {
		return getComponent(IMessagePump.class);
	}
}
