/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import sensor.BatterySensor;

/**
 * Encapsulates all components on the robot base.
 * 
 * @author ajc
 */
public class GRTRobotBase {

    private final GRTDriveTrain dt;
    private final BatterySensor s;

    public GRTRobotBase(GRTDriveTrain dt, BatterySensor s) {
        this.dt = dt;
        this.s = s;
        s.start();
        s.enable();
    }

    public GRTDriveTrain getDriveTrain() {
        return dt;
    }
    
    public BatterySensor getBatterySensor(){
        return s;
    }
    
    public void tankDrive(double leftVelocity, double rightVelocity){
        dt.tankDrive(leftVelocity, rightVelocity);
    }
}
