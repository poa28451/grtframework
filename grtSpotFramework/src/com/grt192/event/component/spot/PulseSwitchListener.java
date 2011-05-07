
package com.grt192.event.component.spot;

import com.grt192.sensor.spot.GRTPulseSwitch;

/**
 *
 * @author ajc
 */
public interface PulseSwitchListener {

    public void pulseRead(GRTPulseSwitch s, int usec);

}
