/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import com.grt192.controller.teleop.DriveController;
import com.grt192.controller.partybot.ArmAlphaController;
import com.grt192.controller.partybot.ArmBetaController;
import com.grt192.controller.partybot.BallShooterController;
import com.grt192.mechanism.partybot.BallShooter;
import com.grt192.mechanism.partybot.ArmAlpha;
import com.grt192.mechanism.partybot.ArmBeta;
import com.grt192.sensor.*;
import com.grt192.actuator.*;
import com.grt192.controller.*;
import com.grt192.mechanism.*;
import com.grt192.mechanism.partybot.PartyBotDriverStation;
import edu.wpi.first.wpilibj.SimpleRobot;
import java.util.Hashtable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobot extends SimpleRobot {


    //Shared objects
    protected Hashtable globals;

    //Autonomous Controllers

    //Teleop Controllers
    private DriveController driveControl;
    private ArmAlphaController armAlphaControl;
    private ArmBetaController armBetaControl;
    private BallShooterController ballFireControl;

    //Mechanisms
    protected PartyBotDriverStation ds;
    protected BallShooter bs;
    protected GRTRobotBase rb;
    protected ArmBeta armBeta;
    protected ArmAlpha armAlpha;

    
    public MainRobot(){
        GRTJoystick left = new GRTJoystick(1,50);
        GRTJoystick right = new GRTJoystick(2,50);
        GRTDriveTrain dt = new GRTDriveTrain(2,3);

        ds = new PartyBotDriverStation(left, right);
        bs = new BallShooter(new GRTVictor(5));
        rb = new GRTRobotBase(dt, new GRTGyro(2,50),
                                            new GRTAccelerometer(3,50));

        armBeta = new ArmBeta(new GRTRelay(1));
        armAlpha = new ArmAlpha(new GRTRelay(2),
                                new GRTRelay(3),
                                new GRTRelay(4),
                                new GRTRelay(5),
                                new GRTVictor(4),
                                new GRTGyro(1,50),
                                new GRTSwitch(1,25),
                                new GRTSwitch(2,25));
        driveControl = new DriveController(rb, ds);
        armAlphaControl = new ArmAlphaController(armAlpha, ds);
        armBetaControl = new ArmBetaController(armBeta, ds);
        ballFireControl = new BallShooterController(bs, ds);
        globals = new Hashtable();
    }

    public synchronized Hashtable getGlobals() {
        return globals;
    }

    /**
     * Get an object shared between controllers
     * @param key
     * @return value
     */
    public synchronized Object getGlobal(String key) {
        return globals.get(key);
    }
    
    /**
     * Add an object to be shared between controllers
     * @param key
     * @param value
     */
    public synchronized void putGlobal(String key, Object value){
        globals.put(key, value);
    }

    /**
     * This method is called when the robot enters Autonomous mode
     * if any Teleop controllers are started, they should be stopped
     * then, all autonomous controllers ar started
     */
    public void autonomous() {
        //Stop teleopcontrollers if started
        if(armAlphaControl.isRunning())
            this.armAlphaControl.stopControl();
        if(armBetaControl.isRunning())
            this.armBetaControl.stopControl();
        if(ballFireControl.isRunning())
            this.ballFireControl.stopControl();
        if(driveControl.isRunning())
            this.driveControl.stopControl();
        
        //Start
    }

    /**
     * This method is called when the robot enters Operator Control mode
     * if any Autonomous controllers are started, they should be stopped
     * then, all teleop controllers ar started
     */
    public void operatorControl() {
        //Stop AutonomousControllers if started
        
        //Start
        this.armAlphaControl.start();
        this.armBetaControl.start();
        this.ballFireControl.start();
        this.driveControl.start();
    }

    public ArmAlphaController getArmAlphaControl() {
        return armAlphaControl;
    }

    public void setArmAlphaControl(ArmAlphaController armAlphaControl) {
        this.armAlphaControl = armAlphaControl;
    }

    public ArmBetaController getArmBetaControl() {
        return armBetaControl;
    }

    public void setArmBetaControl(ArmBetaController armBetaControl) {
        this.armBetaControl = armBetaControl;
    }

    public BallShooterController getBallFireControl() {
        return ballFireControl;
    }

    public void setBallFireControl(BallShooterController ballFireControl) {
        this.ballFireControl = ballFireControl;
    }

    public DriveController getDriveControl() {
        return driveControl;
    }

    public void setDriveControl(DriveController driveControl) {
        this.driveControl = driveControl;
    }

    
}
