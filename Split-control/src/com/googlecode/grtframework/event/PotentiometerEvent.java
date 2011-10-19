package com.googlecode.grtframework.event;

import com.googlecode.grtframework.sensor.IPotentiometer;

public class PotentiometerEvent {

	private final double angle;
	private final IPotentiometer source;

	public PotentiometerEvent(IPotentiometer source, double angle) {
		this.source = source;
		this.angle = angle;

	}

	/**
	 * Radians
	 * 
	 * @return
	 */
	public double getAngle() {
		return angle;
	}

	public IPotentiometer getSource() {
		return source;
	}

}
