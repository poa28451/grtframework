package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.sensor.core.Sensor;
import com.googlecode.grtframework.sensor.event.RangefinderListener;


public interface RangeFinder extends Sensor {

	public void addRangefinderListener(RangefinderListener l);

	public void removeRangefinderListener(RangefinderListener l);

}
