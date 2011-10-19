package com.googlecode.grtframework.sensor;

import java.util.Enumeration;
import java.util.Vector;

import com.googlecode.grtframework.event.RangefinderEvent;
import com.googlecode.grtframework.event.RangefinderListener;
import com.googlecode.grtframework.event.VoltageChangeEvent;
import com.googlecode.grtframework.event.VoltageSensorListener;

public class Maxbotix implements IRangeFinder, VoltageSensorListener {

	private static final double V_PER_INCH = .0098;

	private final IVoltageSensor vin;
	private Vector listeners = new Vector();

	public Maxbotix(IVoltageSensor vin) {
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
		RangefinderEvent rfev = new RangefinderEvent(this, ev.getVoltage()
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
