package com.googlecode.grtframework.actuator;

//TODO: an actuator interface? a hardware interface?

/**
 * A Servo is a motor with precise position control.
 */
public interface IServo {

	/**
	 * Commands the Servo to move to a position
	 * 
	 * @param radians
	 *            where ahead = 0, +cw, -ccw
	 */
	public void setPosition(double radians);

	/**
	 * Commands the Servo to move a percentage of its range
	 * 
	 * @param percent
	 *            [-1 ... 1]
	 */
	public void setPercentPosition(double percent);

}
