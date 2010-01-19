package com.grt192.event.component;

import com.grt192.sensor.GRTPotentiometer;

/**
 *
 * @author anand
 */
public class PotentiometerEvent {
    public static final int DEFAULT = 0;
    private GRTPotentiometer source;
    private int id;
    private double value;

    public PotentiometerEvent(GRTPotentiometer source, int id, double value) {
        this.source = source;
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public GRTPotentiometer getSource() {
        return source;
    }

    public double getValue() {
        return value;
    }

    
}
