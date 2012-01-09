/** To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;
import sensor.GyroSensor;

/**
 *
 * @author calvin
 */
public class GyroEvent {
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;

    private GyroSensor source;
    private int id;
    private double rotation;

    public GyroEvent(GyroSensor source, int id, double rotation) {
        this.source = source;
        this.id = id;
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public int getId() {
        return id;
    }

    public GyroSensor getSource() {
        return source;
    }
}
