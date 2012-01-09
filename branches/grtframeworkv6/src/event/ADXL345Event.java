/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;
import sensor.ADXL345;

/**
 *
 * @author calvin
 */
public class ADXL345Event {
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;

    private ADXL345 source;
    private int id;
    private double acceleration;

    public ADXL345Event(ADXL345 source, int id, double acceleration) {
        this.source = source;
        this.id = id;
        this.acceleration = acceleration;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public int getId() {
        return id;
    }

    public ADXL345 getSource() {
        return source;
    }
}
