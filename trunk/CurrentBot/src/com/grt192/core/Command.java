package com.grt192.core;

/**
 * Message carrying object that represents an order for a specific actuator
 * @author Ryo
 */
public final class Command {

    private double[] values;
    private int sleepTime;
    private boolean atomic;

    public Command(double value) {
        this(new double[]{value});
    }

    public Command(double value1, double value2) {
        this(new double[]{value1, value2});
    }

    public Command(double value1, double value2, double value3) {
        this(new double[]{value1, value2, value3});
    }

    public Command(double value, int sleepTime){
        this(new double[]{value}, sleepTime, false);
    }

    public Command(double value, int sleepTime, boolean atomic) {
        this(new double[]{value}, sleepTime, atomic);
    }

    public Command(double[] values) {
        this(values, 0);
    }

    public Command(double[] values, int sleepTime) {
        this(values, sleepTime, false);
    }

    public Command(double[] values, int sleepTime, boolean atomic) {
        this.values = values;
        this.sleepTime = sleepTime;
        this.atomic = atomic;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public double getValue() {
        return getValue(0);
    }

    public double getValue(int index) {
        return values[index];
    }

    public int size() {
        return values.length;
    }

    public boolean isAtomic() {
        return atomic;
    }
}
