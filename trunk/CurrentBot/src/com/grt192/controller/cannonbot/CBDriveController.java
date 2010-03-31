package com.grt192.controller.cannonbot;

import com.grt192.core.EventController;
import com.grt192.event.component.JoystickEvent;
import com.grt192.event.component.JoystickListener;
import com.grt192.mechanism.GRTDriverStation;
import com.grt192.mechanism.cannonbot.CBTankDriveTrain;
import com.grt192.sensor.GRTJoystick;

public class CBDriveController extends EventController implements
		JoystickListener {

	private CBTankDriveTrain driveTrain;
	private GRTDriverStation driverStation;

	public CBDriveController(CBTankDriveTrain driveTrain,
			GRTDriverStation driverStation) {
		this.driveTrain = driveTrain;
		this.driverStation = driverStation;

		this.addMechanism("dt", driveTrain);
		this.addMechanism("ds", driverStation);

		((GRTJoystick) driverStation.getSensor("leftJoystick"))
				.addJoystickListener(this);
		((GRTJoystick) driverStation.getSensor("rightJoystick"))
				.addJoystickListener(this);

	}

	public void throttleMoved(JoystickEvent e) {
		// TODO Auto-generated method stub

	}

	public void xAxisMoved(JoystickEvent e) {
		// TODO Auto-generated method stub

	}

	public void yAxisMoved(JoystickEvent e) {
		if(e.getSource().getId().equals("leftJoystick")){
		
		}else if(e.getSource().getId().equals("rightJoystick")){
			
		}
		
		driveTrain.tankDrive(driverStation.getYLeftJoystick(), driverStation.getYRightJoystick());

	}

	public void zAxisMoved(JoystickEvent e) {
		// TODO Auto-generated method stub

	}

}
