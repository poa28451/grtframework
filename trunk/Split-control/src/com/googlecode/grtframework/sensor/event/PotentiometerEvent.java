package com.googlecode.grtframework.sensor.event;

public class PotentiometerEvent {

	private final double angle;

	public PotentiometerEvent(double angle) {
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

}
