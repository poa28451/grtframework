package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagPotentiometer;

/**
 * A <code>JagPotentiometerEvent</code> encapsulates the potentiometer state
 * at the time of an interesting event.
 * @see GRTJagPotentiometer
 * @author Data
 */
public class JagPotentiometerEvent {

    //interesting event ID's
    public static final int DEFAULT = 0;
    public static final int DISTANCE = 1;
    public static final int STOPPED = 2;
    public static final int DIRECTION = 3;
    //TODO implement EncoderEvent Object
    //TODO implement speed
    private final GRTJagPotentiometer source;
    private final int id;
    private final double distance;
    private final boolean forward;

    public JagPotentiometerEvent(GRTJagPotentiometer source, int id,
            double distance, boolean direction) {
        this.source = source;
        this.id = id;
        this.distance = distance;
        this.forward = direction;
    }

    /** Gets an ID associated with what triggered this event */
    public int getId() {
        return id;
    }

    /** Gets the sensor source of this event */
    public GRTJagPotentiometer getSource() {
        return source;
    }

    /** Gets the distance reported at the time of this event **/
    public double getDistance() {
        return distance;
    }

    /** Gets the direction reported at the time of this event*/
    public boolean isForward() {
        return forward;
    }
}
