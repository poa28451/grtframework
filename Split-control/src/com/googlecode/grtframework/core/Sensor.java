package com.googlecode.grtframework.core;


public interface Sensor extends EventPublisher {

	/** Encoding for an exception during data acquisition */
	public static final double ERROR = -999.99;

	public void start();

}
