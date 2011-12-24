package core;

/**
 * A GRTLoggedProcess is a controllable process. It can be initiated/terminated.
 * 
 * When a GRTLoggedProcess is constructed, it is immediately run, but not enabled.
 * 
 * 
 * @author ajc
 * 
 */
public abstract class GRTLoggedProcess extends Thread implements IProcess {

    protected final String name;
    protected boolean enabled = false;
    protected boolean running = true;

    public GRTLoggedProcess(String name) {
        this.name = name;
    }

    /**
     * 
     * @param message
     */
    protected void log(String message) {
        // String name = this.getClass().getSimpleName() + this.name;
    }

    /**
     * 
     * @param name
     * @param message
     */
    protected void log(String name, String message) {
    }

    /**
     * 
     * @param data
     */
    protected void log(double data) {
    }

    /**
     * 
     * @param name
     * @param data
     */
    protected void log(String name, double data) {
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void halt() {
        running = false;
        disable(); 
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Name
     * 
     * @return
     */
    public String getID() {
        return name;
    }
}
