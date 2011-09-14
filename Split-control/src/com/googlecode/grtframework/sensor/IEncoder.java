package com.googlecode.grtframework.sensor;

import com.googlecode.grtframework.event.EncoderListener;

/**
 * 
 * @author ajc
 * 
 */
public interface IEncoder {

	public void addEncoderListener(EncoderListener l);

	public void removeEncoderListener(EncoderListener l);
}
