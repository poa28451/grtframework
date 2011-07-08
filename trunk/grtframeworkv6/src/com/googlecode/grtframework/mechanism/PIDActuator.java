package com.googlecode.grtframework.mechanism;

import com.googlecode.grtframework.controller.core.PIDController;

/**
 * Receives events from a PIDController for sophisticated servoing
 * 
 * @see PIDController
 * 
 * @author ajc
 * 
 */
public interface PIDActuator extends Mechanism {

	/**
	 * Controls this Actuator numerically
	 * 
	 * @param gain
	 */
	public void adjust(double gain);
}
