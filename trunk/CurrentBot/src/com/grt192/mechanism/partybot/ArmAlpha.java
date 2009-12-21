/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.mechanism.partybot;

import com.grt192.actuator.GRTGPIORelay;
import com.grt192.actuator.GRTVictor;
import com.grt192.core.Command;
import com.grt192.core.Mechanism;
import com.grt192.core.Sensor;
import com.grt192.sensor.GRTGyro;
import com.grt192.sensor.GRTPotentiometer;
import com.grt192.sensor.GRTSwitch;

/**
 *
 * @author Student
 */

//Arm Alpha has 4 solenoids for balloons and 1 motor for movement
public class ArmAlpha extends Mechanism{
    public static final int BALLOON_TIME = 16000;
    public static final int SENSOR_TIME = 50;
    public static final double ARM_SPEED = 1.0;
    public static final double ANGLE_ALLOWENCE = 5;
    public static final int BALLOON_COUNT = 4;
    public static final int NUM_OF_SWITCH = 2;
    public static final int INIT_RUN = 1000;
    public static final int PHASE_RUN = 10;

    private boolean hasBalloon1 = true;
    private boolean hasBalloon2 = true;
    private boolean hasBalloon3 = true;
    private boolean hasBalloon4 = true;
    private boolean inited = false;
    private boolean inPosOne = false;
    private boolean armMoving = false;

    private double currentAngle;

    public ArmAlpha(GRTGPIORelay balloon1, GRTGPIORelay balloon2, GRTVictor arm, GRTGyro gy,
            GRTSwitch switch1, GRTSwitch switch2,GRTPotentiometer pot) {
        balloon1.start();
        balloon2.start();
        arm.start();
        gy.start();
        switch1.start();
        switch2.start();
        pot.start();

        addActuator("Balloon1", balloon1);
        addActuator("Balloon2", balloon2);
        addActuator("Arm", arm);
        addSensor("Gyro", gy);
        addSensor("Switch1", switch1);
        addSensor("Switch2", switch2);
        addSensor("Potentiometer", pot);
        currentAngle = 0;
        hasBalloon1 = true;
        hasBalloon2 = true;
        hasBalloon3 = true;
        hasBalloon4 = true;

    }
    public ArmAlpha(int relayFwdPorts[], int relayBwdPorts[], int victorPort, int gyroPort,
            int[] switchPorts) {
        for(int i = 1; i <= BALLOON_COUNT/2; i++) {
            addActuator("Balloon" + i, new GRTGPIORelay(relayFwdPorts[i], relayBwdPorts[i]));
            getActuator("Balloon" + i).start();
        }
        addActuator("Arm", new GRTVictor(victorPort));
        getActuator("Arm").start();

        addSensor("Gyro", new GRTGyro(gyroPort, 50, "armGyro"));
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
                              new Command(GRTGPIORelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon1").enqueueCommand(
                            new Command(GRTGPIORelay.RELAY_OFF));
                    hasBalloon1 = false;
                }
                break;
            }
            case 2: {
                if(hasBalloon2) {
                    getActuator("Balloon1").enqueueCommand(
                            new Command(GRTGPIORelay.RELAY_REVERSE, BALLOON_TIME));
                    getActuator("Balloon1").enqueueCommand(
                            new Command(GRTGPIORelay.RELAY_OFF));
                    hasBalloon2 = false;
                }
                break;
            }
            case 3: {
                if(hasBalloon3) {
                    getActuator("Balloon2").enqueueCommand(
                            new Command(GRTGPIORelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon2").enqueueCommand(
                            new Command(GRTGPIORelay.RELAY_OFF));
                    hasBalloon3 = false;
                }
                
                break;
            }
            case 4: {
                if(hasBalloon4) {
                    getActuator("Balloon2").enqueueCommand(
                            new Command(GRTGPIORelay.RELAY_FORWARD, BALLOON_TIME));
                    getActuator("Balloon2").enqueueCommand(
                            new Command(GRTGPIORelay.RELAY_OFF));
                    hasBalloon4 = false;
                }
                break;
            }
        }
    }

    public void resetBalloons(){
        hasBalloon1 = true;
        hasBalloon2 = true;
        hasBalloon3 = true;
        hasBalloon4 = true;

    }
    
    public void initArm() {
        setSpeed(ARM_SPEED);
        while(!hitTop()) {
            try {
                Thread.sleep(SENSOR_TIME);
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }
        }
        setSpeed(-ARM_SPEED);
        try {
            Thread.sleep(INIT_RUN);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        setSpeed(0);
        inited = true;
        inPosOne = true;
    }

    public void MoveArm() {
        if(inited && !armMoving) {
            armMoving = true;
            setSpeed(ARM_SPEED * ((inPosOne) ? -1.0 : 1.0));
            int count = 0;
            while(count < PHASE_RUN) {
                if(hitTop() || hitBottom()) {
                    initArm();
                    return;
                }
                try {
                    Thread.sleep(SENSOR_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            setSpeed(0);
            armMoving = false;
        }
    }

    public double getGyroPosition(){
        return getSensor("Gyro").getState("Angle");
    }

    public double getPotPosition(){
        return getSensor("Potentiometer").getState("Value");
    }

    public boolean hitBottom(){
        return getSensor("Switch1").getState("State") == Sensor.TRUE;
                
    }
    public boolean hitTop() {
        return getSensor("Switch2").getState("State") == Sensor.TRUE;
    }
    public void setSpeed(double a) {
        getActuator("Arm").enqueueCommand(new Command(a));
    }
}