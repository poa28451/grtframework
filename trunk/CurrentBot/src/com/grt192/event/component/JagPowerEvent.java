package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagPowerSensor;

public class JagPowerEvent {
	public static final int DEFAULT = -1;
	public static final int VOLTAGE_CHANGE = 0;
	public static final int CURRENT_CHANGE = 1;
	public static final int TEMPERATURE_CHANGE = 2;
	
    private GRTJagPowerSensor source;
    private int id;
    private double value;
    
	public JagPowerEvent(GRTJagPowerSensor source, int id, double value) {
		this.source = source;
		this.id = id;
		this.value = value;
	}

	public GRTJagPowerSensor getSource() {
		return source;
	}

	public int getId() {
		return id;
	}

	public double getValue() {
		return value;
	}
    
    
}
