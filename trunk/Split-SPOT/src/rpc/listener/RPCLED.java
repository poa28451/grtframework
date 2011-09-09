package rpc.listener;

import rpc.RPCConnection;
import rpc.RPCMessage;
import rpc.RPCMessageListener;
import actuator.CommandArray;
import actuator.instance.TriLED;

public class RPCLED implements RPCMessageListener {

	private final RPCConnection in;
	private final int key;
	private final TriLED led;

	public RPCLED(RPCConnection in, int key, TriLED led) {
		this.in = in;
		this.key = key;
		this.led = led;
	}

	public void startListening() {
		in.addMessageListener(this);
	}

	public void stopListening() {
		in.removeMessageListener(this);
	}

	public void messageReceived(RPCMessage message) {
		System.out.println("Received:" + message);
		if (message.getKey() == key) {// TODO selective listening
			led.enqueueCommand(new CommandArray(message.getData(), message
					.getData(), message.getData()));
			// System.out.println("Received:" + message.getData());
		}
		// if (message.getData() == Switch.PRESSED) {
		// led.up();
		// } else if (message.getData() == Switch.RELEASED) {
		// led.down();
		// }

	}

}
