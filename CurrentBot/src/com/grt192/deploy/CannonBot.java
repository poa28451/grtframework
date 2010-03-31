/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.grt192.deploy;


import com.grt192.controller.DashBoardController;
import com.grt192.controller.cannonbot.CBDriveController;
import com.grt192.core.GRTRobot;
import com.grt192.mechanism.GRTDriverStation;
import com.grt192.mechanism.cannonbot.CBTankDriveTrain;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class CannonBot extends GRTRobot {
	//Mechanisms
	private CBTankDriveTrain driveTrain;
	private GRTDriverStation driverStation;
	//Teleop Controllers
	private CBDriveController driveController;
	
	//Autonomous Controllers
	
	//Global Controllers
	private DashBoardController dashboardController;
	
    public CannonBot(){
    	driveTrain = new CBTankDriveTrain(1,2);
    	driverStation = new GRTDriverStation(1,2,3);
    	driveController = new CBDriveController (driveTrain, driverStation);
    	dashboardController = new DashBoardController();
    	dashboardController.start();
    	this.teleopControllers.addElement(driveController);
    	
    }
}
