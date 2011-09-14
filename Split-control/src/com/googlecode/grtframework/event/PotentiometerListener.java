package com.googlecode.grtframework.event;

import com.googlecode.grtframework.core.EventListener;

public interface PotentiometerListener extends EventListener {

	// TODO more
	public void rotationStopped(PotentiometerEvent pev);

	public void rotationStarted(PotentiometerEvent pev);

	public void rotationChanged(PotentiometerEvent pev);

}
