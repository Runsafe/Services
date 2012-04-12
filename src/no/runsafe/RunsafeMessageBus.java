package no.runsafe;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.messaging.IMessagePump;
import no.runsafe.framework.messaging.IPumpProvider;
import no.runsafe.framework.messaging.MessagePump;

public class RunsafeMessageBus extends RunsafePlugin implements IPumpProvider
{
	@Override
	protected void PluginSetup()
	{
		//IOutput debugger = getComponent(IOutput.class);
		//debugger.setDebugLevel(Level.FINE);
		//this.addComponent(new MessagePumpDebugger(debugger));
		this.addComponent(new MessagePump());
	}

	public IMessagePump getInstance()
	{
		return getComponent(IMessagePump.class);
	}
}
