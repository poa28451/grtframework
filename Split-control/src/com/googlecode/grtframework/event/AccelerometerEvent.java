package com.googlecode.grtframework.event;

import com.googlecode.grtframework.sensor.IAccelerometer;

/**
 * 
 * @author ajc
 * 
 */
public class AccelerometerEvent {

	// public static final int AXIS_X = 0;
	// public static final int AXIS_Y = 1;
	// public static final int AXIS_Z = 2;

	private IAccelerometer source;
	private int id;
	private int axis;
	private double acceleration;

	public AccelerometerEvent(IAccelerometer source, int id, int axis,
			double acceleration) {
		this.source = source;
		this.id = id;
		this.axis = axis;
		this.acceleration = acceleration;

	}

	public IAccelerometer getSource() {
		return source;
	}

	public int getId() {
		return id;
	}

	public int getAxis() {
		return axis;
	}

	public double getAcceleration() {
		return acceleration;
	}

}
