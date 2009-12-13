/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.controller.partybot;

import com.grt192.core.StepController;
import com.grt192.mechanism.partybot.ArmBeta;
import com.grt192.mechanism.partybot.PartyBotDriverStation;

/**
 *
 * @author ryo
 */
public class ArmBetaController extends StepController{
    private boolean armMoving;

    public ArmBetaController(ArmBeta beta, PartyBotDriverStation ds) {
        super();
        addMechanism("ArmBeta", beta);
        addMechanism("DriverStation", ds);
        armMoving = false;
    }
    
    public void act() {
        boolean armButton = ((PartyBotDriverStation)
                            getMechanism("DriverStation")).isBetaPressed();
        if(armButton && !armMoving) {
            System.out.println("Moving Arm Beta");
            ((ArmBeta) getMechanism("ArmBeta")).moveArm();
            armMoving = true;
        }

        armMoving = !(armMoving && !armButton);
        
    }

    public boolean isArmMoving() {
        return armMoving;
    }

    public void setArmMoving(boolean armMoving) {
        this.armMoving = armMoving;
    }

}
