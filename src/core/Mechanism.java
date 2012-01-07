
package core;

import java.util.Vector;

/**
 *
 * A Mechanism is an abstraction of an item that has sensors and actuators
 * that are used to perform a more complex action.
 * 
 * @author gerberduffy
 */
public abstract class Mechanism extends GRTLoggedProcess { 
    
    private Vector actuators;
    private Vector sensors;
    
    public Mechanism(String name){
        super(name);
    }
    
    public void addActuator(Actuator act){
        actuators.addElement(act);
    }
    
    public void addSensor(Sensor s){
        sensors.addElement(s);
    }
    
    /*
     * Remove an Actuator object from the actuators Vector
     */
    public void removeActuator(Actuator act){
        actuators.removeElement(act);
    }
    
    /*
     * Remove and Actuator by its ID String.
     */
    public void removeActuator(String id){
        for (int i=0; i < actuators.size(); i++){
            if ( ((Actuator)actuators.elementAt(i)).getID().equals(id)){
                actuators.removeElementAt(i);
            }
        }
    }
    
    /*
     * Remove a Sensor object from the senors Vector.
     */
    public void removeSensor(Sensor s){
        sensors.removeElement(s);
    }
    
    
    /*
     * Remove a Sensor by its ID String.
     */
    public void removeSensor(String id){
        for (int i=0; i < actuators.size(); i++){
            if ( ((Sensor)sensors.elementAt(i)).getID().equals(id)){
                sensors.removeElementAt(i);
            }
        }
    }
    
    /*
     * Get a sensor with a particular id.
     * If not in the list, return null
     * 
     * @param id The ID of the sensor to be found
     */
    public Sensor getSensor(String id){
        
        for (int i=0; i < sensors.size(); i++){
            if ( ((Sensor)sensors.elementAt(i)).getID().equals(id)){
                return (Sensor)sensors.elementAt(i);
            }
        }
        
        return null;
    }
    
       
    /*
     * Get an actuator with a particular id.
     * If not in the list, return null
     * 
     * @param id The ID of the actuator to be found
     */
    public Actuator getActuator(String id){
        
        for (int i=0; i < actuators.size(); i++){
            if ( ((Sensor)actuators.elementAt(i)).getID().equals(id)){
                return (Actuator)actuators.elementAt(i);
            }
        }
        return null;
    }
    
    
    /*
     * Actuation code for the particular mechanism is implemented in this method.
     * Include any actuations or sensor reading here (or in methods called by this function).
     */
    public abstract void perform();
    
    
    /*
     * Run: Perform the mechanism task if we are enabled.
     */
    public void run(){
        while(isRunning()){
           
            if (isEnabled()){
                perform();
            }
            
        }
    }
    
}
