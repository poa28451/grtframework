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
        XBoxJoystick primary = new XBoxJoystick(1, "primary", 12);
        XBoxJoystick secondary = new XBoxJoystick(2, "secondary", 12);
        System.out.println("Joysticks initialized");

        //Battery Sensor
        BatterySensor batterySensor = new BatterySensor(10, "battery");

        // PWM outputs
        GRTVictor leftDT1 = new GRTVictor(4, "leftDT1");
        GRTVictor leftDT2 = new GRTVictor(4, "leftDT2");
        GRTVictor rightDT1 = new GRTVictor(4, "rightDT1");
        GRTVictor rightDT2 = new GRTVictor(4, "rightDT2");
        System.out.println("Motors initialized");

        //Mechanisms
        GRTDriveTrain dt = new GRTDriveTrain(leftDT2, leftDT2, rightDT2, rightDT2);
        robotBase = new GRTRobotBase(dt, batterySensor);
        driverStation = new GRTDriverStation(primary, secondary);
        System.out.println("Mechanisms initialized");

        //Controllers
        driveControl = new Driver("driveControl", robotBase, driverStation);
        batteryLogger = new SensorLogger(batterySensor, rpcConn, new int[]{23}, "batterylogger");
        System.out.println("Controllers Initialized");


        //Start/prepare controllers
        batteryLogger.enable();
        addTeleopController(driveControl);


        System.out.println("Robot initialized OK");



    }
}
