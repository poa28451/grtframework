package com.googlecode.grtframework.sensor.event;

import com.googlecode.grtframework.core.EventListener;

public interface SwitchListener extends EventListener {

	public void switchPressed(SwitchEvent sw);

	public void switchReleased(SwitchEvent sw);

}
