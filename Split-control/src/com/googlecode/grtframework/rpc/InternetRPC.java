package com.googlecode.grtframework.rpc;

import java.util.Enumeration;
import java.util.Vector;

import com.googlecode.grtframework.networking.GRTClientSocket;
import com.googlecode.grtframework.networking.SocketEvent;
import com.googlecode.grtframework.networking.SocketListener;

/**
 * 
 * @author ajc
 */
public class InternetRPC implements RPCConnection, SocketListener {

	private GRTClientSocket connection;
	private boolean running; // TODO grtobject type thing
	private Vector listeners = new Vector();

	public InternetRPC(String ip, int port) {
		connection = new GRTClientSocket(ip, port);
		start();
	}

	public void start() {
		connection.addSocketListener(this);
		connection.start();
	}

	public void send(RPCMessage message) {
		System.out.println("SENDING MESSAGE: " + message);
		connection.sendData(encode(message));
	}

	public void addMessageListener(RPCMessageListener l) {
	}

	public void removeMessageListener(RPCMessageListener l) {
	}

	private void notifyListeners(String received) {
		if (isTelemetryLine(received)) {
			// RPCMessage message = new RPCMessage(getKey(received),
			// getData(received));
			RPCMessage message = decode(received);
			System.out.println("RECEIVING MESSAGE: " + message);
			// TODO only notify specific 'keyed' listeners
			for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
				((RPCMessageListener) e.nextElement()).messageReceived(message);
			}

		}
	}

	private static String encode(RPCMessage m) {
		// newline to flush all buffers
		return ("USB" + m.getKey() + ":" + m.getData() + "\n");
	}

	private static RPCMessage decode(String received) {
		return new RPCMessage(getKey(received), getData(received));
	}

	private static boolean isTelemetryLine(String line) {
		return line.length() > 3 && line.substring(0, 3).equals("USB");// TODO
		// MAGICNUMBERS
	}

	private static int getKey(String line) {
		return Integer.parseInt(line.substring(3, line.indexOf(':')));
	}

	private static double getData(String line) {
		return Double.parseDouble((line.substring(line.indexOf(':') + 1)));
	}

	public void onConnect(SocketEvent e) { // TODO
	}

	public void onDisconnect(SocketEvent e) { // TODO
	}

	public void dataRecieved(SocketEvent e) {
		System.out.println("DATA RECEIEVED: " + e.getData());
		notifyListeners(e.getData());
	}
}
