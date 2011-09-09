package com.googlecode.grtframework.vis;

/**
 * A Mountable can be mounted and something, and can be mounted upon.
 * 
 * @author ajc
 * 
 */
public interface Mountable {

	// TODO should mountables be the only things that can move?

	public int getX();

	public int getY();

	public double getHeading();

}
