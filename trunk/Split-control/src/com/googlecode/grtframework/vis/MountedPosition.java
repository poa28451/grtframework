package com.googlecode.grtframework.vis;

/**
 * MountedPosition describes the 2D position of an object with reference to a
 * Mountable root, which is treated as an origin of sorts.
 * 
 * A MountedPosition always refers to the body
 * 
 * @author ajc
 * 
 */
public class MountedPosition {

	// TODO figure out what should be final
	private final Mountable root;
	private final int xOffset; // if this isn't constant, then polar forms need
	// to be
	// updated
	private final int yOffset;
	private final double mountedAngle; // maybe use mountedAngle instead of
										// angle

	// POLAR FORMS
	private final double mountDistance; // distance between the
	// mountable(origin) and this component
	private final double mountTheta; // 'mount angle': polar theta from x and y

	public MountedPosition(Mountable root, int xOffset, int yOffset,
			double mountedAngle) {
		this.root = root;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.mountedAngle = mountedAngle;

		mountDistance = Math.sqrt((xOffset * xOffset) + (yOffset * yOffset));
		mountTheta = Math.atan2(yOffset, xOffset);

	}

	/*
	 * ABSOLUTE 2D POSITION
	 */

	/**
	 * 
	 * @return absolute angle with respect to screen
	 */
	public double getHeading() {
		return root.getHeading() + mountedAngle;
	}

	/**
	 * 
	 * @return absolute x position with respect to screen
	 */
	public int getX() {
		// calculates offset using polar form
		return (int) (root.getX() + mountDistance
				* Math.cos(root.getHeading() + mountTheta));
	}

	/**
	 * 
	 * @return absolute y position with respect to screen
	 */
	public int getY() {
		// calculates offset using polar form
		return (int) (root.getY() + mountDistance
				* Math.sin(root.getHeading() + mountTheta));
	}

}
