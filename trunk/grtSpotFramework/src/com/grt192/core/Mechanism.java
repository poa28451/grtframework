package com.grt192.core;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Superclass for significant mechanism--a set of actuators and sensors doing
 * something useful on a robot. A container for a set of actuators and sensors
 * that are in a robot part.
 * 
 * @author anand
 */
public abstract class Mechanism extends GRTObject {

	protected Hashtable actuators;
	protected Hashtable sensors;

	public Mechanism() {
		actuators = new Hashtable();
		sensors = new Hashtable();
	}

	protected Hashtable getActuators() {
		return actuators;
	}

	protected Hashtable getSensors() {
		return sensors;
	}

	public Sensor getSensor(String name) {
		return ((Sensor) sensors.get(name));
	}

	public Actuator getActuator(String name) {
		return ((Actuator) actuators.get(name));
	}

	protected void addSensor(String name, Sensor s) {
		sensors.put(name, s);
	}

	protected void addActuator(String name, Actuator a) {
		actuators.put(name, a);
	}

	/**
	 * Suspend all actuators and sensors on a mechanism
	 */
	public void pause() {
		Enumeration e = actuators.elements();
		while (e.hasMoreElements()) {
			((Actuator) (e.nextElement())).hold();
		}
		e = sensors.elements();
		while (e.hasMoreElements()) {
			((Sensor) (e.nextElement())).pause();
		}
	}

	/**
	 * Resume all actuators and sensors in this mechanism
	 */
	public void unpause() {
		Enumeration e = actuators.elements();
		while (e.hasMoreElements()) {
			((Actuator) (e.nextElement())).unpause();
		}
		e = sensors.elements();
		while (e.hasMoreElements()) {
			((Sensor) (e.nextElement())).unpause();
		}

	}

	/**
	 * Safety shutoff for all actuators in a mechanism
	 */
	public void disable() {
		Enumeration e = actuators.elements();
		while (e.hasMoreElements()) {
			((Actuator) (e.nextElement())).stopActuator();
		}
		while (e.hasMoreElements()) {
			((Sensor) (e.nextElement())).stopSensor();
		}
		System.out.println("DISABLED: " + this);
	}
}
