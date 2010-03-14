package com.grt192.actuator.exception;

import com.grt192.actuator.GRTCANJaguar;

public class GRTCANJaguarException extends ActuatorException{

	//Faults
	public static final int CURRENT_FAULT = 1;
	public static final int VOLTAGE_FAULT = 2;
	public static final int TEMPERATURE_FAULT = 3;
	
	private int faultId;
	private String description;
	
	public GRTCANJaguarException(int faultId, GRTCANJaguar source,
			String description) {
		super(source);
		this.faultId = faultId;
		this.description = description;
	}
	public int getFaultId() {
		return faultId;
	}

	public String getDescription() {
		return description;
	}
	
	
}
