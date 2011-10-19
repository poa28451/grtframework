package com.googlecode.grtframework.event;

import com.googlecode.grtframework.sensor.IVoltageSensor;

public class VoltageChangeEvent {

	private final double voltage;
	private final IVoltageSensor source;

	public VoltageChangeEvent(IVoltageSensor source, double voltage) {
		this.source = source;
		this.voltage = voltage;

	}

	public double getVoltage() {
		return voltage;
	}

	public IVoltageSensor getSource() {
		return source;
	}

}
