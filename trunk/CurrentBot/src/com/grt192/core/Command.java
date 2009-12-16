/*
 * Message carrying object that represents an order for a specific actuator
 *
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.core;

/**
 *
 * @author Ryo
 */
public final class Command {

    private double value;
    private int sleepTime;
    private boolean atomic;

    public Command(double value){
        this(value, 0);
    }

    public Command(double value, int sleepTime){
        this(value, sleepTime, false);
    }

    public Command(double value, int sleepTime, boolean reset) {
        this.value = value;
        this.sleepTime = sleepTime;
        this.atomic = reset;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public double getValue() {
        return value;
    }

    public boolean isAtomic() {
        return atomic;
    }

    public void setAtomic(boolean atomic) {
        this.atomic = atomic;
    }

    
}
