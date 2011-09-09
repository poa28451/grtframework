package sensor;

import sensor.core.Sensor;
import sensor.event.SwitchListener;

public interface Switch extends Sensor {

	public void addSwitchListener(SwitchListener l);

	public void removeSwitchListener(SwitchListener l);

}
