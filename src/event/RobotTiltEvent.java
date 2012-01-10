/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;
import balancer.RobotTiltAccel;

/**
 *
 * @author calvin
 */
public class RobotTiltEvent {
    private RobotTiltAccel source;
    private int id;
    private double tilt;

    public RobotTiltEvent(RobotTiltAccel source, int id, double tilt) {
        this.source = source;
        this.id = id;
        this.tilt = tilt;
    }

    public double getTilt() {
        return tilt;
    }

    public int getId() {
        return id;
    }

    public RobotTiltAccel getSource() {
        return source;
    }
}
