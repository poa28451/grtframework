/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.controller.partybot;

import com.grt192.core.StepController;
import com.grt192.mechanism.partybot.BallShooter;
import com.grt192.mechanism.partybot.PartyBotDriverStation;

/**
 *
 * @author Bonan
 */
public class BallShooterController extends StepController {

    private boolean timeShooting;
    private boolean continuousShooting;
    private boolean decreasing;
    private boolean increasing;


    public BallShooterController(BallShooter bs, PartyBotDriverStation ds) {
        super();
        addMechanism("Ball Shooter", bs);
        addMechanism("Driver Station", ds);
    }

    public void act() {
        BallShooter bs = (BallShooter) getMechanism("Ball Shooter");
        PartyBotDriverStation ds = (PartyBotDriverStation) getMechanism("Driver Station");
        if(ds.isIncSpeedButtonPressed() && !increasing){
            bs.incSpeed();
            System.out.println("Ball Speed "+bs.getSpeed());
            increasing = true;
        }
        increasing = !(!ds.isIncSpeedButtonPressed() && increasing);
        if(ds.isDecSpeedButtonPressed() && !decreasing){
            bs.decSpeed();
            System.out.println("Ball Speed "+bs.getSpeed());
            decreasing = true;
        }
        decreasing = !(!ds.isDecSpeedButtonPressed() && decreasing);
        if (ds.isTimedBallButtonPressed() && !timeShooting) {
            timeShooting = true;
            bs.fire();
            System.out.println("Ball Firing "+bs.getSpeed());
        }
        timeShooting = !(!ds.isTimedBallButtonPressed() && timeShooting);
        if(ds.isContinuousBallButtonPressed() && !continuousShooting) {
            bs.turnOn();
            continuousShooting = true;
        }
        if(!ds.isContinuousBallButtonPressed() && continuousShooting) {
            bs.turnOff();
            continuousShooting = false;
        }
    }

    public String toString() {
        return "Ball shooter: " + (timeShooting ? "" : "not ") + "shooting";
    }
}
