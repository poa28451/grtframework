package com.googlecode.grtframework.sensor.core;

import com.googlecode.grtframework.event.EventPublisher;

/**
 * A sensor reports information regarding the environment. User code accesses
 * sensor data through sensor events.
 * 
 * @author ajc
 * 
 */
public interface Sensor extends EventPublisher {

	/**
	 * Used for abstract telemetry
	 * 
	 * @return name of this sensor
	 */
	public String getName();

}
