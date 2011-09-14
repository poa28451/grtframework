package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.core.Sensor;
import com.googlecode.grtframework.event.RangefinderListener;


public interface IRangeFinder extends Sensor {

	public void addRangefinderListener(RangefinderListener l);

	public void removeRangefinderListener(RangefinderListener l);

}
