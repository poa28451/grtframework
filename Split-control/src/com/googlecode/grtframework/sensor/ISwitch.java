package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.core.Sensor;
import com.googlecode.grtframework.event.SwitchListener;


public interface ISwitch extends Sensor {

	public void addSwitchListener(SwitchListener l);

	public void removeSwitchListener(SwitchListener l);

}
