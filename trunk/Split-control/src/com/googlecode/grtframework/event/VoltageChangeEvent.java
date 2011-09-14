package com.googlecode.grtframework.event;

public class VoltageChangeEvent {

	private final double voltage;

	public VoltageChangeEvent(double voltage) {
		this.voltage = voltage;

	}

	public double getVoltage() {
		return voltage;
	}

}
