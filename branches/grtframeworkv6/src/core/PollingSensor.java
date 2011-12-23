/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 * A PollingSensor directly obtains data through the poll() method, as opposed 
 * to generic sensors that could receive events.
 * @author ajc
 */
public abstract class PollingSensor extends Sensor {

    private final int sleepTime;

    /**
     * 
     * @param name name of the sensor
     * @param sleepTime time between polls [ms]
     */
    public PollingSensor(String name, int sleepTime) {
        super(name);
        this.sleepTime = sleepTime;
    }

    /**
     * Called to poll sensor.
     */
    protected abstract void poll();

    public void run() {
        running = true;
        while (running) {
            
            //only poll, and thus only send events, if enabled
            if (enabled) {
                poll();
            }
            
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
