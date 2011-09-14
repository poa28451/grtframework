package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.core.Sensor;
import com.googlecode.grtframework.event.PotentiometerListener;


public interface IPotentiometer extends Sensor {

	public void addPotentiometerListener(PotentiometerListener l);

	public void removePotentiometerListener(PotentiometerListener l);
}
