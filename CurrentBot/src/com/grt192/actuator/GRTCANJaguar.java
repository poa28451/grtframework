package com.grt192.actuator;

import com.grt192.actuator.exception.GRTCANJaguarException;
import com.grt192.core.Actuator;
import com.grt192.core.Command;

import edu.wpi.first.addons.CANJaguar;
import edu.wpi.first.wpilibj.PIDOutput;

public class GRTCANJaguar extends Actuator implements PIDOutput {
	// Control Modes
	public static final int PERCENT_CONTROL = 1;
	public static final int SPEED_CONTROL = 2;
	public static final int POSITION_CONTROL = 3;
	public static final int CURRENT_CONTROL = 4;
	// Position Sensors
	public static final int POTENTIOMETER = 0;
	public static final int ENCODER = 1;
	// Neutral Modes
	public static final int COAST = 0;
	public static final int BRAKE = 1;
	public static final int JUMPER = 2;

	private CANJaguar jaguar;

	public GRTCANJaguar(int channel) {
		this(channel, 0);
	}

	public GRTCANJaguar(int channel, int controlMode) {
		switch (controlMode) {
		case PERCENT_CONTROL:
			jaguar = new CANJaguar(channel,
					CANJaguar.ControlMode.kPercentVoltage);
			break;
		case SPEED_CONTROL:
			jaguar = new CANJaguar(channel, CANJaguar.ControlMode.kSpeed);
			break;
		case POSITION_CONTROL:
			jaguar = new CANJaguar(channel, CANJaguar.ControlMode.kPosition);
			break;
		case CURRENT_CONTROL:
			jaguar = new CANJaguar(channel, CANJaguar.ControlMode.kCurrent);
			break;
		default:
			jaguar = new CANJaguar(channel);
		}
	}

	public void setPositionSensor(int sensor) {
		if (sensor == ENCODER) {
			jaguar.setPositionReference(CANJaguar.PositionReference.kEncoder);
		} else if (sensor == POTENTIOMETER) {
			jaguar
					.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
		}
	}

	public void setPID(double p, double i, double d) {
		jaguar.setPID(p, i, d);
	}

	public void setP(double p) {
		jaguar.setPID(p, jaguar.getI(), jaguar.getD());
	}

	public void setI(double i) {
		jaguar.setPID(jaguar.getP(), i, jaguar.getD());
	}

	public void setD(double d) {
		jaguar.setPID(jaguar.getP(), jaguar.getI(), d);
	}

	public double getP() {
		return jaguar.getP();
	}

	public double getI() {
		return jaguar.getI();
	}

	public double getD() {
		return jaguar.getD();
	}

	public void enableClosedLoop() {
		enableClosedLoop(0.0);
	}

	public void enableClosedLoop(double initialPosition) {
		jaguar.enableControl(initialPosition);
	}

	public void disableClosedLoop() {
		jaguar.disableControl();
	}

	public double getInputVoltage() {
		return jaguar.getBusVoltage();
	}

	public double getOutputVoltage() {
		return jaguar.getOutputVoltage();
	}

	public double getOutputCurrent() {
		return jaguar.getOutputCurrent();
	}

	public double getTemperature() {
		return jaguar.getTemperature();
	}

	public double getPosition() {
		return jaguar.getPosition();
	}

	public double getSpeed() {
		return jaguar.getSpeed();
	}

	public boolean getLeftLimitStatus() {
		return jaguar.getForwardLimitOK();
	}

	public boolean getRightLimitStatus() {
		return jaguar.getReverseLimitOK();
	}

	public void setVoltageRampRate(double rate) {
		jaguar.setVoltageRampRate(rate);
	}

	public void setEncoderResolution(int countsPerRev) {
		jaguar.configEncoderCodesPerRev((short) (countsPerRev));
	}

	public void setSoftLimits(double leftLimit, double rightLimit) {
		jaguar.configSoftPositionLimits(leftLimit, rightLimit);
	}
	
	public void disableSoftLimits(){
		jaguar.disableSoftPositionLimits();
	}

	public void setNeutralMode(int mode) {
		switch(mode){
		case COAST:
			jaguar.configNeutralMode(CANJaguar.NeutralMode.kCoast);
			break;
		case BRAKE:
			jaguar.configNeutralMode(CANJaguar.NeutralMode.kBrake);
			break;
		case JUMPER:
			jaguar.configNeutralMode(CANJaguar.NeutralMode.kJumper);
			break;
		}
	}

	public void setPotentiometerTurns(int turns) {
		jaguar.configPotentiometerTurns((short) turns);
	}

	public double getLastCommand() {
		return jaguar.get();
	}

	@Override
	protected void executeCommand(Command c) throws GRTCANJaguarException {
		double value = c.getValue();
		jaguar.set(value);
		// TODO throw fault exceptions
	}

	@Override
	protected void halt() {
		jaguar.set(0);
	}

	@Override
	public void pidWrite(double output) {
		jaguar.pidWrite(output);
	}

}
