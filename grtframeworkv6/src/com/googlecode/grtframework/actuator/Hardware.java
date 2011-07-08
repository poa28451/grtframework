package com.googlecode.grtframework.actuator;

/**
 * Specifies safety mechanisms for all classes that control hardware.
 * 
 * @author ajc
 * 
 */
public interface Hardware {

	/**
	 * Safely return to a low energy state.
	 */
	public void halt();

}
