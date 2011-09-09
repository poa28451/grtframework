package com.googlecode.grtframework.sensor.rpc;

import java.util.Enumeration;
import java.util.Vector;

import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;
import com.googlecode.grtframework.rpc.RPCMessageListener;
import com.googlecode.grtframework.rpc.component.RPCSwitchComponent;
import com.googlecode.grtframework.sensor.Switch;
import com.googlecode.grtframework.sensor.event.SwitchEvent;
import com.googlecode.grtframework.sensor.event.SwitchListener;


public class RPCSwitch implements RPCMessageListener, RPCSwitchComponent,
		Switch {

	private final RPCConnection in;
	private final int rpc_key;

	private Vector listeners = new Vector();

	public RPCSwitch(RPCConnection in, int rpc_key) {
		this.in = in;
		this.rpc_key = rpc_key;
	}

	@Override
	public void start() {
		startListening();
	}

	@Override
	public void startListening() {
		in.addMessageListener(this);
	}

	@Override
	public void stopListening() {
		in.removeMessageListener(this);
	}

	@Override
	public void messageReceived(RPCMessage message) {
		if (message.getKey() == rpc_key) {
			if (message.getData() == PRESSED) {
				SwitchEvent ev = new SwitchEvent();
				for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
					((SwitchListener) e.nextElement()).switchPressed(ev);
				}
				// System.out.println("press!");
			} else if (message.getData() == RELEASED) {
				SwitchEvent ev = new SwitchEvent();
				for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
					((SwitchListener) e.nextElement()).switchReleased(ev);
				}
				// System.out.println("release!");
			}
		}
	}

	@Override
	public void addSwitchListener(SwitchListener l) {
		listeners.addElement(l);
	}

	@Override
	public void removeSwitchListener(SwitchListener l) {
		listeners.removeElement(l);
	}

}
