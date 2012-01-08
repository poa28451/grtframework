/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;
import sensor.Attack3Joystick;
/**
 *
 * @author dan
 */
public class Attack3JoystickEvent {
    public static final int DEFAULT = 0;
    private int id;
    private double value;
    private Attack3Joystick source;
    
    public Attack3JoystickEvent(Attack3Joystick source, int id, double value){
        this.source = source;
        this.id = id;
        this.value = value;
    }
    public int getId() {
        return id;
    }
    public Attack3Joystick getSource(){
        return source;
    }
    public double getValue() {
        return value;
    }
}
