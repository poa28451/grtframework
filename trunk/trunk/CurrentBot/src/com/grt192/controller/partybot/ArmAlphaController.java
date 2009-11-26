/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.controller.partybot;

import com.grt192.core.StepController;
import com.grt192.mechanism.partybot.ArmAlpha;
import com.grt192.mechanism.partybot.PartyBotDriverStation;

/**
 *
 * @author ryo
 */
public class ArmAlphaController extends StepController{
    public static final double[] ARM_POSITIONS = {340, 0, 20};

    private boolean firing = false;
    private boolean armMoving = false;
    private int state = 0;
    private int balloonIndex = 1;

    public ArmAlphaController(ArmAlpha alpha, PartyBotDriverStation ds) {
        super();
        addMechanism("DriverStation", ds);
        addMechanism("ArmAlpha", alpha);
    }
    public void act() {
        System.out.println("Arm at "
                +((ArmAlpha) getMechanism("ArmAlpha")).getPosition() +" "
                +((ArmAlpha) getMechanism("ArmAlpha")).isStuck());

        
        boolean balloonButton = ((PartyBotDriverStation)
                getMechanism("DriverStation")).isBalloonButtonPressed();
        boolean ArmButton = ((PartyBotDriverStation)
                getMechanism("DriverStation")).isAlphaPressed();
        boolean Arm1Button = ((PartyBotDriverStation) 
                getMechanism("DriverStation")).isAlphaPressed();
        boolean Arm2Button = ((PartyBotDriverStation)
                getMechanism("DriverStation")).isBetaPressed();

        if(balloonButton && !firing) {
            ((ArmAlpha) getMechanism("ArmAlpha")).fireBalloon(balloonIndex);
            balloonIndex++;
            firing = true;
            balloonIndex %= ArmAlpha.BALLOON_COUNT;
            System.out.println("Firing Balloon");
        }
        firing = !(!balloonButton && firing);

        /*
        if(ArmButton && !armMoving) {
            ((ArmAlpha) getMechanism("ArmAlpha")).MoveArm(ARM_POSITIONS[state]);
            state = (state + 1)%3;
            armMoving = true;
            System.out.println("Moving Arm Alpha");
        }*/

        if(Arm1Button) {
            ((ArmAlpha) getMechanism("ArmAlpha")).setSpeed(1);
            state = (state + 1)%3;
            armMoving = true;
            System.out.println("Moving Arm Alpha");
        }


        if(Arm2Button) {
            ((ArmAlpha) getMechanism("ArmAlpha")).setSpeed(-1);
            state = (state + 1)%3;
            armMoving = true;
            System.out.println("Moving Arm Alpha");
        }
        if(!Arm1Button || !Arm2Button) {
            ((ArmAlpha) getMechanism("ArmAlpha")).setSpeed(0);
            state = (state + 1)%3;
            armMoving = true;
            System.out.println("Moving Arm Alpha");
        }



        armMoving = !(armMoving && !ArmButton);
    }

}
