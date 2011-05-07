
package com.grt192.event.component.spot;

import com.grt192.sensor.spot.GRTSwitch;

/**
 *
 * @author anand
 */
public interface SwitchListener {
    public void switchPressed(GRTSwitch source);
    public void switchReleased(GRTSwitch source);
}
