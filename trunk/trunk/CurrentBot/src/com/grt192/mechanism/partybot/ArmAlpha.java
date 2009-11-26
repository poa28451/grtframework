/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.mechanism.partybot;

import com.grt192.actuator.GRTRelay;
import com.grt192.actuator.GRTVictor;
import com.grt192.core.Command;
import com.grt192.core.Mechanism;
import com.grt192.core.Sensor;
import com.grt192.sensor.GRTGyro;
import com.grt192.sensor.GRTSwitch;

/**
 *
 * @author Student
 */

//Arm Alpha has 4 solenoids for balloons and 1 motor for movement
public class ArmAlpha extends Mechanism{
    public static final int BALLOON_TIME = 16000;
    public static final int SENSOR_TIME = 50;
    public static final double ARM_SPEED = -1.0;
    public static final double ANGLE_ALLOWENCE = 5;
    public static final int BALLOON_COUNT = 8;
    public static final int NUM_OF_SWITCH = 2;

    private boolean hasBalloon1 = true;
    private boolean hasBalloon2 = true;
    private boolean hasBalloon3 = true;
    private boolean hasBalloon4 = true;
    private boolean hasBalloon5 = true;
    private boolean hasBalloon6 = true;
    private boolean hasBalloon7 = true;
    private boolean hasBalloon8 = true;

    private double currentAngle;

    public ArmAlpha(GRTRelay balloon1, GRTRelay balloon2, GRTRelay balloon3, 
            GRTRelay balloon4, GRTVictor arm, GRTGyro gy,
            GRTSwitch switch1, GRTSwitch switch2) {
        balloon1.start();
        balloon2.start();
        balloon3.start();
        balloon4.start();
        arm.start();
        gy.start();
        switch1.start();
        switch2.start();

        addActuator("Balloon1", balloon1);
        addActuator("Balloon2", balloon2);
        addActuator("Balloon3", balloon3);
        addActuator("Balloon4", balloon4);
        addActuator("Arm", arm);
        addSensor("Gyro", gy);
        addSensor("Switch1", switch1);
        addSensor("Switch2", switch2);
        currentAngle = 0;
        hasBalloon1 = true;
        hasBalloon2 = true;
        hasBalloon3 = true;
        hasBalloon4 = true;
        hasBalloon5 = true;
        hasBalloon6 = true;
        hasBalloon7 = true;
        hasBalloon8 = true;

    }
    public ArmAlpha(int relayPorts[], int victorPort, int gyroPort,
            int[] switchPorts) {
        for(int i = 1; i <= BALLOON_COUNT/2; i++) {
            addActuator("Balloon" + i, new GRTRelay(relayPorts[i]));
            getActuator("Balloon" + i).start();
        }
        addActuator("Arm", new GRTVictor(victorPort));
        getActuator("Arm").start();

        addSensor("Gyro", new GRTGyro(gyroPort, 50));
        getSensor("Gyro").start();
        for(int i = 1; i <= NUM_OF_SWITCH; i++) {
            addSensor("Switch" + i, new GRTSwitch(switchPorts[i], 25));
            getSensor("Swicth" + i).start();
        }
    }

    public void fireBalloon(int balloon) {
        switch (balloon) {
            case 1: {
                if(hasBalloon1) {
                    getActuator("Balloon1").enqueueCommand(
                              new Command(GRTRelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon1").enqueueCommand(
                            new Command(GRTRelay.RELAY_OFF));
                    hasBalloon1 = false;
                }
                break;
            }
            case 2: {
                if(hasBalloon2) {
                    getActuator("Balloon1").enqueueCommand(
                            new Command(GRTRelay.RELAY_REVERSE, BALLOON_TIME));
                    getActuator("Balloon1").enqueueCommand(
                            new Command(GRTRelay.RELAY_OFF));
                    hasBalloon2 = false;
                }
                break;
            }
            case 3: {
                if(hasBalloon3) {
                    getActuator("Balloon2").enqueueCommand(
                            new Command(GRTRelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon2").enqueueCommand(
                            new Command(GRTRelay.RELAY_OFF));
                    hasBalloon3 = false;
                }
                
                break;
            }
            case 4: {
                if(hasBalloon4) {
                    getActuator("Balloon2").enqueueCommand(
                            new Command(GRTRelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon2").enqueueCommand(
                            new Command(GRTRelay.RELAY_OFF));
                    hasBalloon4 = false;
                }
                break;
            }
            case 5: {
                if(hasBalloon5) {
                    getActuator("Balloon3").enqueueCommand(
                            new Command(GRTRelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon3").enqueueCommand(
                            new Command(GRTRelay.RELAY_OFF));
                    hasBalloon5 = false;
                }
                break;
            }
            case 6: {
                if(hasBalloon6) {
                    getActuator("Balloon3").enqueueCommand(
                            new Command(GRTRelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon3").enqueueCommand(
                            new Command(GRTRelay.RELAY_OFF));
                    hasBalloon6 = false;
                }
                break;
            }
            case 7: {
                if(hasBalloon7) {
                    getActuator("Balloon4").enqueueCommand(
                            new Command(GRTRelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon4").enqueueCommand(
                            new Command(GRTRelay.RELAY_OFF));
                    hasBalloon7 = false;
                }
                break;
            }
            case 8: {
                if(hasBalloon8) {
                    getActuator("Balloon4").enqueueCommand(
                            new Command(GRTRelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon4").enqueueCommand(
                            new Command(GRTRelay.RELAY_OFF));
                    hasBalloon8 = false;
                }
                break;
            }
        }
    }
    
    public void MoveArm(double angle) {

        currentAngle = getSensor("Gyro").getState("Angle");
        
        if(direction(currentAngle, angle)) {
            getActuator("Arm").enqueueCommand(new Command(ARM_SPEED));
        } else {
            getActuator("Arm").enqueueCommand(new Command(-ARM_SPEED));
        }

        boolean exit = false;
        while(!exit && getSensor("Gyro").getState("Angle") - angle > -ANGLE_ALLOWENCE &&
                getSensor("Gyro").getState("Angle") - angle < ANGLE_ALLOWENCE) {
                    //the two switches are never supposed to be turned on
            /*if (((GRTSwitch) getSensor("Switch1")).getState()) {
                suspend();
                exit = true;
            }
            if (!((GRTSwitch) getSensor("Switch2")).getState()) {
                suspend();
                exit = true;
            }*/
            try {
                Thread.sleep(SENSOR_TIME);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        getActuator("Arm").enqueueCommand(new Command(0));
    }
    public boolean direction(double current, double target) {
        boolean value = true;
        if(current - target < -180) {
            value = true;
        } else if(current - target > -180 && current - target < 0) {
            value = false;
        } else if(current - target < 180 && current - target > 0) {
            value = true;
        } else {
            value = false;
        }
        return value;

    }

    public double getPosition(){
        return getSensor("Gyro").getState("Angle");
    }

    public boolean isStuck(){
        return (getSensor("Switch1").getState("State") == Sensor.TRUE)
                || (getSensor("Switch2").getState("State") == Sensor.TRUE);
    }
    public void setSpeed(double a) {
        getActuator("Arm").enqueueCommand(new Command(a));
    }
 
}
