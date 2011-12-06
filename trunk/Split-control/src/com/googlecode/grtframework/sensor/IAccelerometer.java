package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.event.AccelerometerListener;

/**
 * 
 * @author ajc
 * 
 */
public interface IAccelerometer {

	public void addAccelerometerListener(AccelerometerListener l);

	public void removeAccelerometerListener(AccelerometerListener l);

}
