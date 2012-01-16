package com.googlecode.grtframework.sensor;

import java.util.Enumeration;
import java.util.Vector;

import com.googlecode.grtframework.event.SwitchEvent;
import com.googlecode.grtframework.event.SwitchListener;
import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;
import com.googlecode.grtframework.rpc.RPCMessageListener;
import com.googlecode.grtframework.rpc.component.RPCSwitchComponent;

public class RPCSwitch implements RPCMessageListener, RPCSwitchComponent,
		ISwitch {

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

			if ( Double.parseDouble( message.getData() ) == PRESSED ) {

				SwitchEvent ev = new SwitchEvent(this, SwitchEvent.PRESSED);
				for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
					((SwitchListener) e.nextElement()).switchPressed(ev);
				}
				// System.out.println("press!");
			} else if ( Double.parseDouble(message.getData()) == RELEASED) {
				SwitchEvent ev = new SwitchEvent(this, SwitchEvent.PRESSED);
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
