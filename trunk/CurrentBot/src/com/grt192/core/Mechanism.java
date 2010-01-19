/*
 * Superclass for significant mechanism--a set of actuators and sensors
 * doing something useful on a robot.
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.core;

import java.util.Hashtable;

/**
 * A container for a set of actuators and sensors that are in a robot part. 
 * @author anand
 */
public abstract class Mechanism {

    protected Hashtable actuators;
    protected Hashtable sensors;

    public Mechanism(){
        actuators = new Hashtable();
        sensors = new Hashtable();
    }

    protected Hashtable getActuators() {
        return actuators;
    }

    protected Hashtable getSensors() {
        return sensors;
    }

    public Sensor getSensor(String name) {
        return ((Sensor) sensors.get(name));
    }

    public Actuator getActuator(String name) {
        return ((Actuator) actuators.get(name));
    }

    public void addSensor(String name, Sensor s){
        sensors.put(name, s);
    }

    public void addActuator(String name, Actuator a){
        actuators.put(name, a);
    }

    /**
     * Suspend all actuators and sensors on a mechanism
     */
    public void suspend(){
        while(actuators.elements().hasMoreElements()){
            ((Actuator) (actuators.elements().nextElement())).suspend();
        }
        while(sensors.elements().hasMoreElements()){
            ((Sensor) (sensors.elements().nextElement())).suspend();
        }
    }
    
    /**
     * Resume all actuators and sensors in this mechanism
     */
    public void resume(){
        while(actuators.elements().hasMoreElements()){
            ((Actuator) (actuators.elements().nextElement())).resume();
        }
        while(sensors.elements().hasMoreElements()){
            ((Sensor) (sensors.elements().nextElement())).resume();
        }
        
    }

    /**
     * Safety shutoff for all actuators in a mechanism
     */
    public void disable(){
        while(actuators.elements().hasMoreElements()){
            ((Actuator) (actuators.elements().nextElement())).stopActuator();
        }
        while(sensors.elements().hasMoreElements()){
            ((Sensor) (sensors.elements().nextElement())).stopSensor();
        }
        System.out.println("DISABLED: "+this);
    }
}
