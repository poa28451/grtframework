/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.event.component.spot;

import com.grt192.sensor.spot.GRTDemoSwitch;

/**
 *
 * @author ajc
 */
public interface DemoSwitchListener {

    public void switchPressed(GRTDemoSwitch source);

    public void switchReleased(GRTDemoSwitch source);
}
