package com.grt192.actuator;

import com.grt192.actuator.exception.ActuatorException;
import com.grt192.core.Actuator;
import com.grt192.core.Command;

import edu.wpi.first.wpilibj.Solenoid;

public class GRTTwoWaySolenoid extends Actuator{
	public static final double FORWARD = 1.0;
	public static final double REVERSE = -1.0;
	public static final double OFF = 0.0;
	
	private Solenoid forwardValve;
	private Solenoid reverseValve;
	
	public GRTTwoWaySolenoid(int fwdPin, int revPin){
		forwardValve = new Solenoid(fwdPin);
		reverseValve = new Solenoid(revPin);
	}
	
	protected void executeCommand(Command c) throws ActuatorException {
		if(c.getValue() == FORWARD){
			reverseValve.set(false);
			forwardValve.set(true);
		}else if(c.getValue() == REVERSE){
			forwardValve.set(false);
			reverseValve.set(true);
		}else if(c.getValue() == OFF){
			forwardValve.set(false);
			reverseValve.set(false);
		}
	}

	protected void halt() {
		forwardValve.set(false);
		reverseValve.set(false);
	}

}
