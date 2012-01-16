package com.googlecode.grtframework.sensor;

import java.util.Enumeration;
import java.util.Vector;

import com.googlecode.grtframework.event.VoltageChangeEvent;
import com.googlecode.grtframework.event.VoltageSensorListener;
import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;
import com.googlecode.grtframework.rpc.RPCMessageListener;

public class RPCVoltageSensor implements RPCMessageListener, IVoltageSensor {

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
			VoltageChangeEvent ev = new VoltageChangeEvent(this, Double.parseDouble(message
					.getData()) );
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
