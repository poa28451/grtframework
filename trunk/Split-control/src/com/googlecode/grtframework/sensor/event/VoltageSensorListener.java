package com.googlecode.grtframework.sensor.event;

import com.googlecode.grtframework.core.EventListener;

public interface VoltageSensorListener extends EventListener {

	public void voltageChanged(VoltageChangeEvent ev);

}
