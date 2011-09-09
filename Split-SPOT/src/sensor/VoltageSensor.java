package sensor;

import sensor.core.Sensor;
import sensor.event.VoltageSensorListener;

public interface VoltageSensor extends Sensor {

	public void addVoltageChangeListener(VoltageSensorListener l);

	public void removeVoltageChangeListener(VoltageSensorListener l);

}
