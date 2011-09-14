package com.googlecode.grtframework.event;

/**
 * 
 * @author ajc
 * 
 */
public interface EncoderListener {

	public void countDidChange(EncoderEvent e);

	public void rotationDidStart(EncoderEvent e);

	public void rotationDidStop(EncoderEvent e);

	public void changedDirection(EncoderEvent e);

}
