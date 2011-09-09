package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.sensor.core.Sensor;
import com.googlecode.grtframework.sensor.event.PotentiometerListener;


public interface Potentiometer extends Sensor {

	public void addPotentiometerListener(PotentiometerListener l);

	public void removePotentiometerListener(PotentiometerListener l);
}
