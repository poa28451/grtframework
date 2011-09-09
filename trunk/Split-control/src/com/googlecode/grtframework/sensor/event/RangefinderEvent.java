package com.googlecode.grtframework.sensor.event;

public class RangefinderEvent {

	private final double distance;

	public RangefinderEvent(double distance) {
		this.distance = distance;

	}

	public double getDistance() {
		return distance;
	}

	public String toString() {
		return "Distance:" + distance;
	}

}
