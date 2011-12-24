/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy;

import actuator.GRTVictor;
import controller.Driver;
import edu.wpi.first.wpilibj.DriverStation;
import mechanism.GRTDriveTrain;
import mechanism.GRTDriverStation;
import mechanism.GRTRobotBase;
import sensor.XBoxJoystick;

/**
 *
 * @author ajc
 */
public class MainRobot extends GRTRobot {

    private Driver driveControl;
    private GRTDriverStation driverStation;
    private GRTRobotBase robotBase;

    public MainRobot() {

        System.out.println("Running grtframeworkv6");

        //Driver station components
        XBoxJoystick primary = new XBoxJoystick(1, "primary", 12);
        XBoxJoystick secondary = new XBoxJoystick(2, "secondary", 12);
        System.out.println("Joysticks initialized");

        // PWM outputs
        GRTVictor leftDT1 = new GRTVictor(4, "leftDT1");
        GRTVictor leftDT2 = new GRTVictor(4, "leftDT2");
        GRTVictor rightDT1 = new GRTVictor(4, "rightDT1");
        GRTVictor rightDT2 = new GRTVictor(4, "rightDT2");
        System.out.println("Motors initialized");

        //Mechanisms
        GRTDriveTrain dt = new GRTDriveTrain(leftDT2, leftDT2, rightDT2, rightDT2);
        robotBase = new GRTRobotBase(dt);
        driverStation = new GRTDriverStation(primary, secondary);
        System.out.println("Mechanisms initialized");

        //Controllers
        driveControl = new Driver("driveControl", robotBase, driverStation);
        System.out.println("Controllers Initialized");

        addTeleopController(driveControl);


        System.out.println("Robot initialized OK");



    }
}
