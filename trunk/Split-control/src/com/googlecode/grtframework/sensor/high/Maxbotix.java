package com.googlecode.grtframework.sensor.high;

import java.util.Enumeration;
import java.util.Vector;

import com.googlecode.grtframework.sensor.RangeFinder;
import com.googlecode.grtframework.sensor.VoltageSensor;
import com.googlecode.grtframework.sensor.event.RangefinderEvent;
import com.googlecode.grtframework.sensor.event.RangefinderListener;
import com.googlecode.grtframework.sensor.event.VoltageChangeEvent;
import com.googlecode.grtframework.sensor.event.VoltageSensorListener;


public class Maxbotix implements RangeFinder, VoltageSensorListener {

	private static final double V_PER_INCH = .0098;

	private final VoltageSensor vin;
	private Vector listeners = new Vector();

	public Maxbotix(VoltageSensor vin) {
		this.vin = vin;
	}

	public void start() {
		startListening();
	}

	public void startListening() {
		vin.addVoltageChangeListener(this);
	}

	public void stopListening() {
		vin.removeVoltageChangeListener(this);
	}

	public void voltageChanged(VoltageChangeEvent ev) {
		RangefinderEvent rfev = new RangefinderEvent(ev.getVoltage()
				/ V_PER_INCH);
		for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
			((RangefinderListener) e.nextElement()).distanceChanged(rfev);
		}
		System.out.println(rfev);
	}

	public void addRangefinderListener(RangefinderListener l) {
		listeners.addElement(l);
	}

	public void removeRangefinderListener(RangefinderListener l) {
		listeners.removeElement(l);
	}

}
