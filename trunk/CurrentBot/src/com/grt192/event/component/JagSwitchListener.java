package com.grt192.event.component;

public interface JagSwitchListener {
	public void leftSwitchPressed(JagSwitchEvent e);
	public void leftSwitchReleased(JagSwitchEvent e);
	public void rightSwitchPressed(JagSwitchEvent e);
	public void rightSwitchReleased(JagSwitchEvent e);
}
