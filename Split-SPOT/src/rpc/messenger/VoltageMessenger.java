package rpc.messenger;

import rpc.RPCConnection;
import rpc.RPCMessage;
import rpc.RPCMessenger;
import sensor.VoltageSensor;
import sensor.event.VoltageChangeEvent;
import sensor.event.VoltageSensorListener;

public class VoltageMessenger implements RPCMessenger, VoltageSensorListener {

	private final int key;
	private final VoltageSensor sw;
	private final RPCConnection out;

	public VoltageMessenger(int key, VoltageSensor sw, RPCConnection out) {
		this.key = key;
		this.sw = sw;
		this.out = out;
	}

	public void startListening() {
		sw.addVoltageChangeListener(this);
	}

	public void stopListening() {
		sw.removeVoltageChangeListener(this);
	}

	public void voltageChanged(VoltageChangeEvent ev) {
		out.send(new RPCMessage(key, ev.getVoltage()));
	}

}
