package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.event.AccelerometerListener;

/**
 * 
 * @author ajc
 * 
 */
public interface IAccelerometer {

	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;

	public void addAccelerometerListener(AccelerometerListener l);

	public void removeAccelerometerListener(AccelerometerListener l);

}
