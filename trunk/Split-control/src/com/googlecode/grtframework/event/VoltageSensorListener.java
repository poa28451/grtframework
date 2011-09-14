package com.googlecode.grtframework.event;

import com.googlecode.grtframework.core.EventListener;

public interface VoltageSensorListener extends EventListener {

	public void voltageChanged(VoltageChangeEvent ev);

}
