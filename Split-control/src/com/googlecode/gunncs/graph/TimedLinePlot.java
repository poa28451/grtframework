package com.googlecode.gunncs.graph;

import java.awt.Color;

/**
 * A TimedLinePlot facilitates using time as the X axis
 * 
 * @author ajc
 * 
 */
public class TimedLinePlot extends LinePlot {

	private static double START_TIME;

	/**
	 * 
	 * @param name
	 * @param capacity
	 */
	public TimedLinePlot(String name, int capacity) {
		super(name, capacity);
		resetTime();
	}

	/**
	 * Adds a point using time as x.
	 * 
	 * @param name
	 *            name of data
	 * @param y
	 *            y value
	 */
	public void addPoint(String name, double y) {
		super.addPoint(name, currentTime(), y);
	}

	/**
	 * Sets the clock to zero
	 */
	public void resetTime() {
		START_TIME = System.currentTimeMillis() / 1000.0;
	}

	/**
	 * 
	 * @return time elapsed since clock zeroed [seconds]
	 */
	private double currentTime() {
		return System.currentTimeMillis() / 1000.0 - START_TIME;
	}

	/**
	 * Add
	 * 
	 * @param name
	 * @param c
	 * @param yUnits
	 */
	public void addData(String name, Color c, String yUnits) {
		super.addData(name, c, "time", yUnits);
	}
}
