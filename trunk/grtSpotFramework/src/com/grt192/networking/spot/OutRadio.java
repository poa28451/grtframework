/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.networking.spot;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;

import com.grt192.prototyper.PrototyperUtils;

/**
 * A monodirectional socket which only sends data to a <code>InRadio</code>.
 * 
 * @author ajc
 */
public class OutRadio extends EventSocket {

	private final String host;
	private final int port;
	private DataOutputStream dos;
	// private DataOutputStreamWriter writer;
	private boolean open;

	/**
	 * Opens an outbound write only connection from a prototyped host with a
	 * provided prototype on provided port.
	 * 
	 * @param prototype
	 *            prototype of protyped host
	 * @param port
	 *            port to listen for
	 * @param debug
	 *            true to print debug messages
	 * @return
	 */
	public static OutRadio toPrototype(int prototype, int port, boolean debug) {
		String host = PrototyperUtils.getAddress(prototype);
		if (host == null) {
			return null;
		}
		return new OutRadio(PrototyperUtils.getAddress(prototype), port, debug);
	}

	/**
	 * Opens an outbound write only connection from a prototyped host with a
	 * provided prototype on provided port.
	 * 
	 * @param prototype
	 *            prototype of protyped host
	 * @param port
	 *            port to listen for
	 */
	public OutRadio(String host, int port) {
		this(host, port, false);
	}

	/**
	 * Opens an outbound write only connection from a prototyped host with a
	 * provided prototype on provided port.
	 * 
	 * @param prototype
	 *            prototype of protyped host
	 * @param port
	 *            port to listen for
	 * @param debug
	 *            true to print debug messages
	 */
	public OutRadio(String host, int port, boolean debug) {
		this.host = host;
		this.port = port;
		this.debug = debug;
		open = false;
		// writer = null;
	}

	/** Tries to open the data input stream */
	public void connect() {
		String url = "radiostream://" + host + ":" + port;
		try {
			dos = Connector.openDataOutputStream(url);
			// writer = new DataOutputStreamWriter(dos);
			// writer.start();
			open = true;
		} catch (IOException ex) {
			ex.printStackTrace();
			dos = null;
			open = false;
			// writer = null;
		}
	}

	public void sendData(String data) {
		// if (writer != null) {
		// writer.enqueueData(data);
		// }
		// Assert.that(writer != null);
		try {
			dos.writeUTF(data);
			dos.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			disconnect();
			connect();
		}
	}

	/** We define connected as if the server acknowledged the connection */
	public boolean isConnected() {
		return dos != null && open;
	}

	/** Closes the stream */
	public void disconnect() {
		try {
			// dos.flush();
			dos.close();
			open = false;
			notifyDisconnect();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
