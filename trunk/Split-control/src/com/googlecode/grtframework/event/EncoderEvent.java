package com.googlecode.grtframework.event;

import com.googlecode.grtframework.sensor.IEncoder;

/**
 * 
 * @author ajc
 * 
 */
public class EncoderEvent {
	public static final int DEFAULT = 0;
	public static final int DISTANCE = 1;
	public static final int STOPPED = 2;
	public static final int DIRECTION = 3;

	private final IEncoder source;
	private final int id;
	private final double distance;
	private final boolean forward;

	public EncoderEvent(IEncoder source, int id, double distance,
			boolean direction) {
		this.source = source;
		this.id = id;
		this.distance = distance;
		this.forward = direction;
	}

	public int getId() {
		return id;
	}

	public IEncoder getSource() {
		return source;
	}

	public double getDistance() {
		return distance;
	}

	public boolean isForward() {
		return forward;
	}
}
