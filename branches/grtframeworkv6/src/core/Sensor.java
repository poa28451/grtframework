/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import event.SensorChangeListener;
import event.SensorEvent;
import java.util.Vector;

/**
 * A sensor sends sensor event data.
 * @author ajc
 */
public abstract class Sensor extends GRTLoggedProcess{
    
    private Vector listeners;
    
    public Sensor(String name){
        super(name);
        listeners = new Vector();
    }
    
    protected void notifyStateChange(int id, double data){
        SensorEvent e = new SensorEvent(this, id, data);
    
        for(int i = 0; i<listeners.size(); i++){
            ((SensorChangeListener) listeners.elementAt(i)).sensorStateChanged(e);
        }
    }
    
    public void addSensorStateChangeListener(SensorChangeListener l){
        listeners.addElement(l);
    }
    
    public void removeSensorStateChangeListener(SensorChangeListener l){
        listeners.removeElement(l);
    }
    
}
