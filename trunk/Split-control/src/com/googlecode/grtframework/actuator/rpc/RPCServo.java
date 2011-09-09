package com.googlecode.grtframework.actuator.rpc;

import com.googlecode.grtframework.actuator.Servo;
import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;


/**
 * A servo that is actuated on the other side of an RPC connection.
 * 
 * @author ajc
 * 
 */
public class RPCServo implements Servo {

	// RPC
	private final RPCConnection out;
	private final int key;

	// PWM /angular constants
	private final double pwmRange;
	private final double average;
	private final double rotationRange;

	/**
	 * Constructs a new calibrated Servo given 2 calibration points.
	 * 
	 * @param out
	 * @param key
	 * @param leftPWM
	 * @param leftAngle
	 * @param rightPWM
	 * @param rightAngle
	 */
	public RPCServo(RPCConnection out, int key, int leftPWM, double leftAngle,
			int rightPWM, double rightAngle) {
		// RPC
		this.out = out;
		this.key = key;
		// Servo
		pwmRange = rightPWM - leftPWM;
		average = (rightPWM + leftPWM) / 2;
		rotationRange = rightAngle - leftAngle;

	}

	@Override
	public void setPercentPosition(double percent) {
		// sends a PWM value proportional to full range
		// System.out.println(percent);
		setPWM((int) (average - (percent * pwmRange / 2)));
	}

	@Override
	public void setPosition(double radians) {
		// converts angle to a percent.
		setPercentPosition(2 * radians / rotationRange);
	}

	/**
	 * Sends a PWM value to the servo
	 * 
	 * @param value
	 */
	protected void setPWM(int value) {
		// send a message only when able to
		if (out != null) {
			out.send(new RPCMessage(key, value));
		}
	}

}
