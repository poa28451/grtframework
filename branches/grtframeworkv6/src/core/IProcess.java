package core;

/**
 * An IProcess is a component that can be enabled or disabled.
 * 
 * @author ajc
 * 
 */
public interface IProcess {

    public void enable();
    
    public void disable();
    
    public boolean isEnabled();
    
    public void halt();
    
    public boolean isRunning();
        

}
