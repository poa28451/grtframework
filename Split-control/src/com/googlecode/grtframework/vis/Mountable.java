package com.googlecode.grtframework.vis;

/**
 * A Mountable can be mounted and something, and can be mounted upon. Mountables
 * have Cartesian coordinates
 * 
 * @author ajc
 * 
 */
public interface Mountable {

	// TODO should mountables be the only things that can move?

	/**
	 * 
	 * @return absolute x coordinate with respect to screen
	 */
	public int getX();

	/**
	 * 
	 * @return absolute y coordinate with respect to screen
	 */
	public int getY();

	/**
	 * 
	 * @return absolute heading with respect to screen
	 */
	public double getHeading();

}
