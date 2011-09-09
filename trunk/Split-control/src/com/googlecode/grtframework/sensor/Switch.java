package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.sensor.core.Sensor;
import com.googlecode.grtframework.sensor.event.SwitchListener;


public interface Switch extends Sensor {

	public void addSwitchListener(SwitchListener l);

	public void removeSwitchListener(SwitchListener l);

}
