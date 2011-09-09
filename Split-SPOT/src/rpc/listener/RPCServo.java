package rpc.listener;

import rpc.RPCConnection;
import rpc.RPCMessage;
import rpc.RPCMessageListener;
import actuator.instance.GRTServo;

public class RPCServo implements RPCMessageListener {

	private final RPCConnection in;
	private final int key;
	private final GRTServo servo;

	public RPCServo(RPCConnection in, int key, GRTServo servo) {
		this.in = in;
		this.key = key;
		this.servo = servo;
	}

	public void startListening() {
		in.addMessageListener(this);
	}

	public void stopListening() {
		in.removeMessageListener(this);
	}

	public void messageReceived(RPCMessage message) {
		if (message.getKey() == key) {
			servo.enqueueCommand(message.getData());
		}
	}

}
