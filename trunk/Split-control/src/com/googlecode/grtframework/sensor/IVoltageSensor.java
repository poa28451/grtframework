package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.core.Sensor;
import com.googlecode.grtframework.event.VoltageSensorListener;


public interface IVoltageSensor extends Sensor {

	public void addVoltageChangeListener(VoltageSensorListener l);

	public void removeVoltageChangeListener(VoltageSensorListener l);

}
