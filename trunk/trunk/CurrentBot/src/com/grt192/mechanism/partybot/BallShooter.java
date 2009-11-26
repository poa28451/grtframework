/*
 * they said they just have a wheel type thing, and it needs a speed controller
 * type thing
 */
package com.grt192.mechanism.partybot;

import com.grt192.actuator.GRTVictor;
import com.grt192.core.Command;
import com.grt192.core.Mechanism;

/**
 *
 * @author Bonan and James
 */
public class BallShooter extends Mechanism {

    public static final int SHOOT_SLEEP = 1000;
    public static final double INCREMENT = .02;
    private double speed = 0.8;


    public BallShooter(GRTVictor v) {
        v.start();
        addActuator("Victor", v);
    }

    public BallShooter(int victorPort) {
        addActuator("Victor", new GRTVictor(victorPort));
        getActuator("Victor").start();
    }

    public void fire() {
        ((GRTVictor) getActuator("Victor")).enqueueCommand(new Command(speed));
        try {
            Thread.sleep(SHOOT_SLEEP);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        ((GRTVictor) getActuator("Victor")).enqueueCommand(new Command(0));
    }

    public void turnOn() {
        Command c = new Command(speed);
        getActuator("Victor").enqueueCommand(c);
    }

    public void turnOff() {
        Command c = new Command(0);
        getActuator("Victor").enqueueCommand(c);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void incSpeed(){
        speed+=INCREMENT;
        if(speed > 1.0)
            speed = 1.0;
    }

    public void decSpeed(){
        speed-=INCREMENT;
        if(speed < 0)
            speed = 0;
    }

    public String toString() {
        return "Ball shooter";
    }
}
