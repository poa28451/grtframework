package rpc.messenger;

import rpc.RPCConnection;
import rpc.RPCMessage;
import rpc.RPCMessenger;
import rpc.component.RPCSwitchComponent;
import sensor.Switch;
import sensor.event.SwitchEvent;
import sensor.event.SwitchListener;

public class SwitchMessenger implements RPCMessenger, RPCSwitchComponent,
		SwitchListener {

	private final int key;
	private final Switch sswitch;
	private final RPCConnection out;

	public SwitchMessenger(int key, Switch sw, RPCConnection out) {
		this.key = key;
		this.sswitch = sw;
		this.out = out;
	}

	public void startListening() {
		sswitch.addSwitchListener(this);
	}

	public void stopListening() {
		sswitch.removeSwitchListener(this);
	}

	public void switchPressed(SwitchEvent sw) {
		out.send(new RPCMessage(key, PRESSED));
	}

	public void switchReleased(SwitchEvent sw) {
		out.send(new RPCMessage(key, RELEASED));
	}

}
