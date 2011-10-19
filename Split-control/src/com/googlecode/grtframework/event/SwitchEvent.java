package com.googlecode.grtframework.event;

import com.googlecode.grtframework.sensor.ISwitch;

/**
 * 
 * @author ajc
 * 
 */
public class SwitchEvent {

	public static final int PRESSED = 0;
	public static final int RELEASED = 1;

	private final ISwitch source;
	private final int id;

	public SwitchEvent(ISwitch source, int id) {
		this.source = source;
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public boolean isPressed() {
		return id == PRESSED;
	}

	public boolean isReleased() {
		return id == RELEASED;
	}

	public ISwitch getSource() {
		return source;
	}

}
