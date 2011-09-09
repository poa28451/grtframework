package com.googlecode.grtframework.sensor.rpc;

import java.util.Enumeration;
import java.util.Vector;

import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;
import com.googlecode.grtframework.rpc.RPCMessageListener;
import com.googlecode.grtframework.sensor.VoltageSensor;
import com.googlecode.grtframework.sensor.event.VoltageChangeEvent;
import com.googlecode.grtframework.sensor.event.VoltageSensorListener;


public class RPCVoltageSensor implements RPCMessageListener, VoltageSensor {

	private final RPCConnection in;
	private final int key;

	private Vector listeners = new Vector();

	public RPCVoltageSensor(RPCConnection in, int key) {
		this.in = in;
		this.key = key;
	}

	public void start() {
		startListening();
	}

	public void startListening() {
		in.addMessageListener(this);
	}

	public void stopListening() {
		in.removeMessageListener(this);
	}

	public void messageReceived(RPCMessage message) {
		if (message.getKey() == key) {
			VoltageChangeEvent ev = new VoltageChangeEvent(message.getData());
			for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
				((VoltageSensorListener) e.nextElement()).voltageChanged(ev);
			}
		}
	}

	public void addVoltageChangeListener(VoltageSensorListener l) {
		listeners.addElement(l);
	}

	public void removeVoltageChangeListener(VoltageSensorListener l) {
		listeners.removeElement(l);
	}

}
