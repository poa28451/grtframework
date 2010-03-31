package com.grt192.mechanism.cannonbot;

import com.grt192.actuator.GRTCANJaguar;
import com.grt192.core.Mechanism;
import com.grt192.sensor.GRTGyro;
import com.grt192.sensor.canjaguar.GRTJagEncoder;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class CBTankDriveTrain extends Mechanism implements PIDOutput {

	public static final int WAIT_INTERVAL = 15;

	private double turnP = .10;
	private double turnI = .10;
	private double turnD = .10;

	private double driveP = .10;
	private double driveI = .10;
	private double driveD = .10;

	private GRTCANJaguar leftJaguar;
	private GRTCANJaguar rightJaguar;
	private GRTJagEncoder leftEncoder;
	private GRTJagEncoder rightEncoder;
	private GRTGyro canGyro;

	private boolean pointTurn;

	private PIDController turnControl;
	private PIDController leftDriveControl;
	private PIDController rightDriveControl;

	public CBTankDriveTrain(int lfpin, int rfpin) {
		this(new GRTCANJaguar(lfpin), new GRTCANJaguar(rfpin));
	}

	public CBTankDriveTrain(GRTCANJaguar lfjaguar, GRTCANJaguar rfjaguar) {
		this.leftJaguar = lfjaguar;
		leftJaguar.start();

		this.rightJaguar = rfjaguar;
		rightJaguar.start();

		this.leftEncoder = new GRTJagEncoder(lfjaguar, 3, "ljagencoder");
		leftEncoder.start();

		this.rightEncoder = new GRTJagEncoder(rfjaguar, 17, "rjagencoder");
		rightEncoder.start();

		this.canGyro = new GRTGyro(1, 5, "cangyro");
		canGyro.start();

		turnControl = new PIDController(turnP, turnI, turnD, canGyro, this);
		leftDriveControl = new PIDController(driveP, driveI, driveD,
				leftEncoder, this);
		rightDriveControl = new PIDController(driveP, driveI, driveD,
				rightEncoder, this);
		pointTurn = true;
		addActuator("lfJaguar", leftJaguar);
		addActuator("rbJaguar", rightJaguar);
		addSensor("ljagencoder", leftEncoder);
		addSensor("rjagencoder", rightEncoder);
		addSensor("cangyro", canGyro);

	}

	public double getDriveP() {
		return driveP;
	}

	public void setDriveP(double driveP) {
		this.driveP = driveP;
	}

	public double getDriveI() {
		return driveI;
	}

	public void setDriveI(double driveI) {
		this.driveI = driveI;
	}

	public double getDriveD() {
		return driveD;
	}

	public void setDriveD(double driveD) {
		this.driveD = driveD;
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
		this.leftJaguar.enqueueCommand(leftSpeed);
		this.rightJaguar.enqueueCommand(rightSpeed);
	}

	public void driveToPosition(double position) {
		driveToPosition(position, position);
	}

	public void driveToPosition(double leftPosition, double rightPosition) {
		leftDriveControl.setSetpoint(leftPosition);
		rightDriveControl.setSetpoint(rightPosition);
		leftDriveControl.enable();
		rightDriveControl.enable();
		while (!leftDriveControl.onTarget() && rightDriveControl.onTarget()) {
			// Block
			block();
		}
		leftDriveControl.reset();
		rightDriveControl.reset();
	}

	public void driveDistance(double distance) {
		driveDistance(distance, distance);
	}

	public void driveDistance(double leftDistance, double rightDistance) {
		driveToPosition(leftEncoder.getState("Distance") + leftDistance,
				rightEncoder.getState("Distance") + rightDistance);
	}

	public void turnTo(double angle) {
		turnTo(angle, true);
	}

	public void turnTo(double angle, boolean point) {
		pointTurn = point;
		turnControl.setSetpoint(angle);
		turnControl.enable();
		while (!turnControl.onTarget()) {
			// Block
			block();
		}
		turnControl.reset();
	}

	public void pidWrite(double output) {
		// Scale speed control to output ratio
		if (pointTurn) {
			tankDrive(output, -output);
		} else {
			tankDrive((1 + output) / 2, 1 - (1 + output) / 2);
		}
	}

	public void stop() {
		tankDrive(0, 0);
	}

	public double getTurnP() {
		return turnP;
	}

	public void setTurnP(double turnP) {
		this.turnP = turnP;
	}

	public double getTurnI() {
		return turnI;
	}

	public void setTurnI(double turnI) {
		this.turnI = turnI;
	}

	public double getTurnD() {
		return turnD;
	}

	public void setTurnD(double turnD) {
		this.turnD = turnD;
	}

	public boolean isPointTurn() {
		return pointTurn;
	}

	public void setPointTurn(boolean pointTurn) {
		this.pointTurn = pointTurn;
	}
	
	private void block(){
		try {
			Thread.sleep(WAIT_INTERVAL);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
