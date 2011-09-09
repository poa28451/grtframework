package sensor.event;

import core.EventListener;

public interface VoltageSensorListener extends EventListener {

	public void voltageChanged(VoltageChangeEvent ev);

}
