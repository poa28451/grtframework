/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

/**
 *
 * @author anand
 */
public interface AccelerometerListener {
    public void didReceiveAcceleration(AccelerometerEvent e);
    public void didAccelerationSpike(AccelerometerEvent e);
    public void didAccelerationChange(AccelerometerEvent e);
}
