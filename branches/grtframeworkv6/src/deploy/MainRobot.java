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
import mechanism.LinearDrive;
import rpc.connection.NetworkRPC;
import rpc.telemetry.SensorLogger;
import sensor.BatterySensor;
import sensor.XBoxJoystick;

/**
 *
 * @author ajc
 */
public class MainRobot extends GRTRobot {

    //Global Controllers
    private SensorLogger batteryLogger;
    //Teleop Controllers
    private Driver driveControl;
    private GRTDriverStation driverStation;
    private GRTRobotBase robotBase;

    public MainRobot() {

        System.out.println("Running grtframeworkv6");

        //RPC Connection
        NetworkRPC rpcConn = new NetworkRPC(180);

        //Driver station components
        XBoxJoystick primary = new XBoxJoystick(1, 12, "primary");
        XBoxJoystick secondary = new XBoxJoystick(2, 12, "secondary");
        System.out.println("Joysticks initialized");

        //Battery Sensor
        BatterySensor batterySensor = new BatterySensor(10, "battery");

        // PWM outputs
        GRTVictor leftDT1 = new GRTVictor(4, "leftDT1");
        GRTVictor leftDT2 = new GRTVictor(3, "leftDT2");
        GRTVictor rightDT1 = new GRTVictor(6, "rightDT1");
        GRTVictor rightDT2 = new GRTVictor(10, "rightDT2");
        System.out.println("Motors initialized");

        //Mechanisms
        GRTDriveTrain dt = new GRTDriveTrain(leftDT1, leftDT2, rightDT1, rightDT2);
        robotBase = new GRTRobotBase(dt, batterySensor);
        driverStation = new GRTDriverStation(primary, secondary, new int[]{1, 2},
                "driverStation");
        driverStation.enable();
        System.out.println("Mechanisms initialized");

        //Controllers
        driveControl = new Driver(robotBase, driverStation, new LinearDrive(), "driveControl");
        batteryLogger = new SensorLogger(batterySensor, rpcConn, new int[]{23}, "batterylogger");
        System.out.println("Controllers Initialized");


        // Start/prepare controllers
        batteryLogger.enable();
        addTeleopController(driveControl);


        System.out.println("Robot initialized OK");



    }
}
