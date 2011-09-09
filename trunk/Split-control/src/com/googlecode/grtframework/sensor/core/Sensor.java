package com.googlecode.grtframework.sensor.core;

import com.googlecode.grtframework.core.EventPublisher;

public interface Sensor extends EventPublisher {

	/** Encoding for an exception during data acquisition */
	public static final double ERROR = -999.99;

	public void start();

}
