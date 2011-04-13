package com.grt192.event.component;

/**
 * A <code>JagPotentiometerListener</code> Listens for <code>GatPotentiometerEvents</code>
 * sent by a <code>GRTJagPotentiometer</code> 
 * @author Data
 */
public interface JagPotentiometerListener {

    /** Called when any change of the potentiometer signal occurs */
    public void countDidChange(JagPotentiometerEvent e);

    /** Called when potentiometer starts to move from an idle state */
    public void rotationDidStart(JagPotentiometerEvent e);

    /** Called when potentiometer detects zero movement.*/
    public void rotationDidStop(JagPotentiometerEvent e);

    /** Called when the direction of the potentiometer flips. */
    public void changedDirection(JagPotentiometerEvent e);
}
