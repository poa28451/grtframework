package com.grt192.sensor.canjaguar;

import java.util.Vector;

import com.grt192.actuator.GRTCANJaguar;
import com.grt192.core.Sensor;
import com.grt192.event.component.EncoderEvent;
import com.grt192.event.component.JagEncoderEvent;
import com.grt192.event.component.JagEncoderListener;

import edu.wpi.first.wpilibj.PIDSource;

public class GRTJagEncoder extends Sensor implements PIDSource{
	private GRTCANJaguar jaguar;
	private Vector encoderListeners;

	public GRTJagEncoder(GRTCANJaguar jag, int pollTime, String id) {
		this.id = id;
		setSleepTime(pollTime);
		this.jaguar = jag;
		encoderListeners = new Vector();
	}

	public void poll() {
		double previous = getState("Direction");
		setState("Direction", (jaguar.getSpeed() >= 0));
		if (previous != getState("Direction"))
			this.notifyDirectionChanged();

		setState("Speed", jaguar.getSpeed());
		setState("Distance", jaguar.getPosition());
		if (previous != getState("Distance"))
			this.notifyEncoderChange();

		setState("Stopped", (jaguar.getSpeed() == 0));
		if (previous != getState("Stopped"))
			if (getState("Stopped") == Sensor.TRUE)
				this.notifyEncoderStopped();
			else
				this.notifyEncoderStarted();

	}

	protected void notifyEncoderChange() {
		for (int i = 0; i < encoderListeners.size(); i++) {
			((JagEncoderListener) encoderListeners.elementAt(i))
					.countDidChange(new JagEncoderEvent(this,
							EncoderEvent.DEFAULT, getState("Distance"),
							(getState("Direction") == Sensor.TRUE)));
		}
	}
	
	public void setResolution(int countsPerRev) {
		jaguar.setEncoderResolution((short) (countsPerRev));
	}

	public void addEncoderListener(JagEncoderListener a) {
		encoderListeners.addElement(a);
	}

	public void removeEncoderListener(JagEncoderListener a) {
		encoderListeners.removeElement(a);
	}

	protected void notifyEncoderStarted() {
		for (int i = 0; i < encoderListeners.size(); i++) {
			((JagEncoderListener) encoderListeners.elementAt(i))
					.rotationDidStart(new JagEncoderEvent(this,
							EncoderEvent.DEFAULT, getState("Distance"),
							(getState("Direction") == Sensor.TRUE)));
		}
	}

	protected void notifyEncoderStopped() {
		for (int i = 0; i < encoderListeners.size(); i++) {
			((JagEncoderListener) encoderListeners.elementAt(i))
					.rotationDidStop(new JagEncoderEvent(this,
							EncoderEvent.DEFAULT, getState("Distance"),
							(getState("Direction") == Sensor.TRUE)));
		}
	}

	protected void notifyDirectionChanged() {
		for (int i = 0; i < encoderListeners.size(); i++) {
			((JagEncoderListener) encoderListeners.elementAt(i))
					.rotationDidStop(new JagEncoderEvent(this,
							EncoderEvent.DEFAULT, getState("Distance"),
							(getState("Direction") == Sensor.TRUE)));
		}
	}

	public double pidGet() {
		return getState("Distance");
	}

}