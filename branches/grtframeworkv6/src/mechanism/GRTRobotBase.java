/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

/**
 * Encapsulates all components on the robot base.
 * 
 * @author ajc
 */
public class GRTRobotBase {

    private final GRTDriveTrain dt;

    public GRTRobotBase(GRTDriveTrain dt) {
        this.dt = dt;

    }

    public GRTDriveTrain getDriveTrain() {
        return dt;
    }
    
    public void tankDrive(double leftVelocity, double rightVelocity){
        dt.tankDrive(leftVelocity, rightVelocity);
    }
}
