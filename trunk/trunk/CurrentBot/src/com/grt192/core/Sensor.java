/*
 * Superclass for all specific sensors on a robot.
 * Reviewed AG 11/2/2009 -- OK
 */

package com.grt192.core;

import java.util.Hashtable;

/**
 *
 * @author anand
 */
public abstract class Sensor extends Thread{
    public static final double FALSE = 0.0;
    public static final double TRUE = 1.0;

    protected Hashtable state;
    protected int sleepTime = 50;
    private boolean running;
    private boolean suspended;

    public Sensor(){
        state = new Hashtable();
        running = false;
        suspended = false;
    }
    
    public void run() {
        running = true;
        while(running) { 
            try {
                if(!suspended)
                    poll();
                
                sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public abstract void poll();

    public void setState(String key, double value){
        state.put(key, new Double(value));
    }


    public void setState(String key, boolean value){
        if(value) {
            state.put(key, new Double(TRUE));
        } else {
            state.put(key, new Double(FALSE));
        }
        
    }


    public synchronized double getState(String key){
        if(state.containsKey(key)){
            return ((Double) (state.get(key))).doubleValue();
        }
        else return -1.0;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }
    

    public Hashtable getStateTable() {
        return state;
    }

    public void stopSensor() {
        running = false;
    }


    public boolean isSuspended() {
        return suspended;
    }

    public void suspend() {
        this.suspended = true;
    }

    public void resume(){
        this.suspended = false;
    }

}
