package com.googlecode.grtframework.vis;

/**
 * A RootMount is the lowest level mount, that isn't mounted to anything
 * 
 * There can only be one root mount
 * 
 * @author ajc
 * 
 */
public class RootMount implements Mountable {

	private static final int ORIGIN_X = 0;
	private static final int ORIGIN_Y = 0;
	private static final double ORIGIN_HEADING = 0;

	private static class SingletonHolder {
		public static final RootMount instance = new RootMount();
	}

	/**
	 * 
	 * 
	 * @return The root mount
	 */
	public static RootMount get() {
		return SingletonHolder.instance;
	}

	/**
	 * Default ctor, should not be used
	 */
	private RootMount() {

	}

	@Override
	public double getHeading() {
		return ORIGIN_HEADING;
	}

	@Override
	public int getX() {
		return ORIGIN_X;
	}

	@Override
	public int getY() {
		return ORIGIN_Y;
	}

}
