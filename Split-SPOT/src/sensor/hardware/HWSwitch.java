package sensor.hardware;

import java.util.Enumeration;
import java.util.Vector;

import sensor.Switch;
import sensor.core.HWSensor;
import sensor.event.SwitchEvent;
import sensor.event.SwitchListener;

public abstract class HWSwitch extends HWSensor implements Switch {

	public static int KEY_STATE = 0;

	public static final int PRESSED = (int) HWSensor.TRUE;

	public static final int RELEASED = (int) HWSensor.FALSE;

	protected abstract boolean getSwitchState();

	protected void poll() {
		setState(KEY_STATE, getSwitchState());
	}

	public void sensorStateChanged(int id, double prevData, double data) {
		SwitchEvent sw = new SwitchEvent();
		if (data == PRESSED) {
			for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
				((SwitchListener) e.nextElement()).switchPressed(sw);
			}
		} else if (data == RELEASED) {
			for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
				((SwitchListener) e.nextElement()).switchReleased(sw);
			}
		}
	}

	public void addSwitchListener(SwitchListener l) {
		listeners.addElement(l);

	}

	public void removeSwitchListener(SwitchListener l) {
		listeners.removeElement(l);

	}

	private Vector listeners = new Vector();

}
