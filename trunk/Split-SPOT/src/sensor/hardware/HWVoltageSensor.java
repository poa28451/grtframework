package sensor.hardware;

import java.util.Enumeration;
import java.util.Vector;

import sensor.VoltageSensor;
import sensor.core.HWSensor;
import sensor.event.VoltageChangeEvent;
import sensor.event.VoltageSensorListener;

public abstract class HWVoltageSensor extends HWSensor implements VoltageSensor {

	public static int KEY_VOLTAGE = 0;

	private Vector listeners = new Vector();

	protected abstract double getVoltage();

	public void addVoltageChangeListener(VoltageSensorListener l) {
		listeners.addElement(l);
	}

	public void removeVoltageChangeListener(VoltageSensorListener l) {
		listeners.removeElement(l);
	}

	protected void poll() {
		setState(KEY_VOLTAGE, getVoltage());
	}

	protected void sensorStateChanged(int id, double prevData, double data) {
		VoltageChangeEvent ev = new VoltageChangeEvent(data);
		for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
			((VoltageSensorListener) e.nextElement()).voltageChanged(ev);
		}
	}

}
