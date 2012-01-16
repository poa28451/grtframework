package com.googlecode.grtframework.sensor;

import java.util.ArrayList;

import com.googlecode.grtframework.event.AccelerometerEvent;
import com.googlecode.grtframework.event.AccelerometerListener;
import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;
import com.googlecode.grtframework.rpc.RPCMessageListener;

/**
 * an RPC connected accelerometer
 * 
 * @author ajc
 * 
 */
public class RPCAccelerometer implements RPCMessageListener, IAccelerometer {

	private final RPCConnection conn;
	private final int[] keys;

	private ArrayList<AccelerometerListener> listeners;

	/**
	 * 
	 * @param conn
	 *            rpc connection to listen to
	 * @param keys
	 *            X, Y,and Z rpc keys
	 */
	public RPCAccelerometer(RPCConnection conn, int[] keys) {
		this.conn = conn;
		this.keys = keys;
		listeners = new ArrayList<AccelerometerListener>();
	}

	@Override
	public void messageReceived(RPCMessage message) {
		int axis = -1;
		// assigns axis to the index of the keys array that message is from
		for (int i = 0; i < keys.length; i++) {
			if (message.getKey() == keys[i]) {
				axis = i;
			}
		}
		notify(axis, Double.parseDouble(message.getData()) );

	}

	private void notify(int axis, double acceleration) {
		AccelerometerEvent ev = new AccelerometerEvent(this, -1, axis,
				acceleration);// id currently not useds

		// notify all listeners
		for (AccelerometerListener l : listeners) {
			l.accelerationChanged(ev);
		}
	}

	@Override
	public void startListening() {
		if (conn != null) {
			conn.addMessageListener(this);
		}

	}

	@Override
	public void stopListening() {
		if (conn != null) {
			conn.addMessageListener(this);
		}
	}

	@Override
	public void addAccelerometerListener(AccelerometerListener l) {
		listeners.add(l);
	}

	@Override
	public void removeAccelerometerListener(AccelerometerListener l) {
		listeners.remove(l);
	}

}
