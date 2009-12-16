package com.grt192.core;

import java.util.Hashtable;

/**
 * 
 * @author anand
 */
public abstract class Controller extends Thread{
    protected Hashtable mechanisms;
    protected boolean running;

    public Controller(){
        mechanisms = new Hashtable();
        running = false;
    }

    public abstract void run();

    public void addMechanism(String name, Mechanism m){
        mechanisms.put(name, m);
    }

    public Mechanism getMechanism(String name) {
        return (Mechanism) mechanisms.get(name);
    }

    public Hashtable getMechanisms() {
        return mechanisms;
    }

    public void setMechanisms(Hashtable mechanisms) {
        this.mechanisms = mechanisms;
    }

    public void stopControl(){
        stopControl(false);
    }

    public void stopControl(boolean disable){
        if(disable){
            while(mechanisms.elements().hasMoreElements()){
                ((Mechanism) (mechanisms.elements().nextElement())).disable();
            }
        }
        System.out.println("STOPPING: "+this);
        running = false;

    }

    public boolean isRunning() {
        return running;
    }
}
