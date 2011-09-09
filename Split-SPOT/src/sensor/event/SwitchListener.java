package sensor.event;

import core.EventListener;

public interface SwitchListener extends EventListener {

	public void switchPressed(SwitchEvent sw);

	public void switchReleased(SwitchEvent sw);

}
