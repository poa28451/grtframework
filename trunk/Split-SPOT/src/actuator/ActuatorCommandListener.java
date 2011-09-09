package actuator;

/**
 * 
 * @author anand
 */
public interface ActuatorCommandListener {

	public void commandDidComplete(ActuatorEvent e);

	public void commandDidFail(ActuatorEvent e);
}
