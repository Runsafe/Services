package no.runsafe;

import no.runsafe.framework.messaging.IMessageBusService;
import no.runsafe.framework.messaging.Message;
import no.runsafe.framework.messaging.MessagePump;
import no.runsafe.framework.messaging.Response;
import no.runsafe.framework.output.IOutput;

import java.util.logging.Level;

public class MessagePumpDebugger extends MessagePump {
	public MessagePumpDebugger(IOutput output) {
		this.output = output;
	}

	@Override
	public void RegisterService(IMessageBusService service) {
		output.outputDebugToConsole(String.format("Adding service %s", service.getServiceName()), Level.FINE);
		super.RegisterService(service);
	}

	@Override
	public void UnregisterService(IMessageBusService service) {
		output.outputDebugToConsole(String.format("Removing service %s", service.getServiceName()), Level.FINE);
		super.UnregisterService(service);
	}

	@Override
	public Response HandleMessage(Message message) {
		Response response = super.HandleMessage(message);
		output.outputDebugToConsole(String.format("Request %s to service %s resulted in %s (%s)", message.getQuestion(), message.getTargetService(), response.getStatus(), response.getResponse()), Level.FINE);
		return response;
	}

	private IOutput output;
}
