/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import com.grt192.sensor.*;
import com.grt192.actuator.*;
import com.grt192.core.GRTRobot;
import com.grt192.mechanism.*;
import com.grt192.controller.teleop.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobot extends GRTRobot {

    //Global Controllers
    //Autonomous Controllers

    //Teleop Controllers
    private DriveEventController driveControl;

    //Mechanisms
    protected GRTDriverStation ds;
    protected GRTRobotBase robotbase;

    
    public MainRobot(){
        GRTJoystick left = new GRTJoystick(1,50, "left");
        GRTJoystick right = new GRTJoystick(2,50, "right");
        robotbase = new GRTRobotBase(new int[]{1,2}, 1, 2);

        ds = new GRTDriverStation(left, right);

        driveControl = new DriveEventController(robotbase, ds);
        teleopControllers.addElement(driveControl);
    }
}
