package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagEncoder;

/**
 *
 * @author GRTstudent
 */
public class JagEncoderEvent {
    //TODO implement EncoderEvent Object
        public static final int DEFAULT = 0;

    private GRTJagEncoder source;
    private int id;
    private double distance;
    private boolean forward;

    public JagEncoderEvent(GRTJagEncoder source, int id,
                                        double distance, boolean direction) {
        this.source = source;
        this.id = id;
        this.distance = distance;
        this.forward = direction;
    }

    public int getId() {
        return id;
    }

    public GRTJagEncoder getSource() {
        return source;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    
}
