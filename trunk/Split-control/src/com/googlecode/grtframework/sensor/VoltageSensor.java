package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.sensor.core.Sensor;
import com.googlecode.grtframework.sensor.event.VoltageSensorListener;


public interface VoltageSensor extends Sensor {

	public void addVoltageChangeListener(VoltageSensorListener l);

	public void removeVoltageChangeListener(VoltageSensorListener l);

}
