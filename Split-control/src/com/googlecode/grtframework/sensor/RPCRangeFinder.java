package com.googlecode.grtframework.sensor;

import java.util.ArrayList;

import com.googlecode.grtframework.event.RangefinderEvent;
import com.googlecode.grtframework.event.RangefinderListener;
import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;
import com.googlecode.grtframework.rpc.RPCMessageListener;

/**
 * an RPC connected accelerometer
 * 
 * @author ajc
 * 
 */
public class RPCRangeFinder implements RPCMessageListener, IRangeFinder {

	private final RPCConnection conn;
	private final int keys;

	private ArrayList<RangefinderListener> listeners;

	/**
	 * 
	 * @param conn
	 *            rpc connection to listen to
	 * @param keys
	 *            X, Y,and Z rpc keys
	 */
	public RPCRangeFinder(RPCConnection conn, int keys) {
		this.conn = conn;
		this.keys = keys;
		listeners = new ArrayList<RangefinderListener>();
	}

	@Override
	public void messageReceived(RPCMessage message) {
		// assigns axis to the index of the keys array that message is from
		if (message.getKey() == keys) {
			notify(message.getData());
		}

	}

	private void notify(double distance) {
		RangefinderEvent e = new RangefinderEvent(this, distance);
		// notify all listeners
		for (RangefinderListener l : listeners) {
			l.distanceChanged(e);
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
	public void addRangefinderListener(RangefinderListener l) {
		listeners.add(l);
	}

	@Override
	public void removeRangefinderListener(RangefinderListener l) {
		listeners.remove(l);
	}

	@Override
	public void start() {

	}

}
