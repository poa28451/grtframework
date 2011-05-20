package com.grt192.sensor.spot;

import com.grt192.controller.spot.SensorServer;
import com.grt192.core.Sensor;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import com.grt192.networking.spot.GRTServant;
import com.grt192.utils.Util;

/**
 * A NetServentSensor reads Sensor data from a matching
 * <code>ServentedSensorService</code>.
 * 
 * @author ajc
 */
public class NetServantSensor extends Sensor implements SocketListener {

	private GRTServant client;
	private final String sourceAddress;

	/**
	 * Opens a sensor that reads data from a remote sensor
	 * 
	 * @param sourceAddress
	 *            address origin of remote sensor
	 * @param port
	 *            port remote sensor server is broadcasting on
	 */
	public NetServantSensor(String sourceAddress, int port) {
		this.sourceAddress = sourceAddress;
		client = new GRTServant(port);
	}

	/**
	 * Starts the sensor by making it start to listen
	 */
	public void start() {
		client.addSocketListenerIn(sourceAddress, this);
		// these don't start.
	}

	public void poll() {
		// no poll: only on event
	}

	public void onConnect(SocketEvent e) {
	}

	public void onDisconnect(SocketEvent e) {
	}

	public void dataRecieved(SocketEvent e) {
		String s = e.getData();
		String type = s.substring(0, s.indexOf(SensorServer.SEPARATOR));
		String message = s.substring(s.indexOf(SensorServer.SEPARATOR) + 1);
		setState(type, Util.doubleValue(message));
		// log("Type:\t" + type);
		// log("Data:\t" + message);
	}
}
