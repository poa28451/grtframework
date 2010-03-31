package com.grt192.sensor.canjaguar;

import java.util.Vector;

import com.grt192.actuator.GRTCANJaguar;
import com.grt192.core.Sensor;
import com.grt192.event.component.JagSwitchEvent;
import com.grt192.event.component.JagSwitchListener;

public class GRTJagSwitch extends Sensor {

	private GRTCANJaguar jaguar;
	private Vector switchListeners;

	public GRTJagSwitch(GRTCANJaguar jaguar, int pollTime, String id) {
		this.jaguar = jaguar;
		switchListeners = new Vector();
		this.id = id;
		this.setSleepTime(pollTime);
	}

	public void poll() {
		double previous = getState("leftSwitch");
		setState("leftSwitch", jaguar.getLeftLimitStatus());
		if(previous != getState("leftSwitch")){
			notifyLeftSwitch(getState("leftSwitch") == Sensor.TRUE);
		}
		previous = getState("rightSwitch");
		setState("rightSwitch", jaguar.getRightLimitStatus());
		if(previous != getState("rightSwitch")){
			notifyLeftSwitch(getState("rightSwitch") == Sensor.TRUE);
		}
	}

	protected void notifyLeftSwitch(boolean pressed) {
		for (int i = 0; i < switchListeners.size(); i++) {
			if (pressed)
				((JagSwitchListener) switchListeners.elementAt(i))
						.leftSwitchPressed(new JagSwitchEvent(this,
								JagSwitchEvent.LEFT_PRESSED, "leftSwitch"));
			else
				((JagSwitchListener) switchListeners.elementAt(i))
						.leftSwitchReleased(new JagSwitchEvent(this,
								JagSwitchEvent.LEFT_RELEASED, "leftSwitch"));
		}
	}

	protected void notifyRightSwitch(boolean pressed) {
		for (int i = 0; i < switchListeners.size(); i++) {
			if (pressed)
				((JagSwitchListener) switchListeners.elementAt(i))
						.rightSwitchPressed(new JagSwitchEvent(this,
								JagSwitchEvent.RIGHT_PRESSED, "rightSwitch"));
			else
				((JagSwitchListener) switchListeners.elementAt(i))
						.rightSwitchReleased(new JagSwitchEvent(this,
								JagSwitchEvent.RIGHT_RELEASED, "rightSwitch"));
		}
	}

}
