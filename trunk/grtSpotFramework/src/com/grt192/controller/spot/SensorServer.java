package com.grt192.controller.spot;

import com.grt192.core.Sensor;
import com.grt192.core.controller.EventController;
import com.grt192.event.SensorChangeListener;
import com.grt192.event.SensorEvent;
import com.grt192.networking.spot.RadiogramServer;

/**
 * Broadcasts sensor data from a single sensor on a given port
 * 
 * @author ajc
 */
public class SensorServer extends EventController implements
		SensorChangeListener {

	/** Separates key from value in sending UTF data */
	public static final char SEPARATOR = ':';

	private final Sensor s;
	private RadiogramServer server;

	/**
	 * Constructs a new telemetry server
	 * 
	 * @param source
	 *            Sensor to send data from
	 * @param port
	 *            port to broadcast on
	 */
	public SensorServer(Sensor source, int port) {
		this.s = source;
		server = new RadiogramServer(port);
	}

	// start reading data from sensor and sending data
	public void startListening() {
		s.addSensorChangeListener(this);
	}

	// stop reading data from sensor and stop sending data
	public void stopListening() {
		s.removeSensorChangeListener(this);
	}

	public void sensorStateChanged(SensorEvent e, String key) {
		server.sendData(key + SEPARATOR + e.getData(key));
		log(key + ":" + e.getData(key));
	}
}
