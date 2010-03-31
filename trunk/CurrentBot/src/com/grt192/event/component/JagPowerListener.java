package com.grt192.event.component;

public interface JagPowerListener {
	public void currentChanged(JagPowerEvent e);
	public void voltageChanged(JagPowerEvent e);
	public void temperatureChanged(JagPowerEvent e);
}
