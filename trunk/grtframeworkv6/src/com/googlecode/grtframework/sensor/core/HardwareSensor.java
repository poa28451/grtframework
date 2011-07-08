package com.googlecode.grtframework.sensor.core;

/**
 * A sensor that gets its data through periodic polling
 * 
 * @author ajc
 * 
 */
public abstract class HardwareSensor {

	/*
	 * TODO list:
	 * 
	 * periodic task stuff
	 * 
	 * state saving
	 * 
	 * event framework (can this be done abstractly?
	 */

	// TODO idea: add randomness to polling, also save time of poll?s
	/**
	 * 
	 */
	protected abstract void poll();


}