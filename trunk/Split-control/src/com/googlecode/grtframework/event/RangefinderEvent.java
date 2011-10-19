package com.googlecode.grtframework.event;

import com.googlecode.grtframework.sensor.IRangeFinder;

public class RangefinderEvent {

	private final double distance;
	private final IRangeFinder source;

	public RangefinderEvent(IRangeFinder source, double distance) {
		this.source = source;
		this.distance = distance;

	}

	public double getDistance() {
		return distance;
	}

	public IRangeFinder getSource() {
		return source;
	}

	public String toString() {
		return "Distance:" + distance;
	}

}
