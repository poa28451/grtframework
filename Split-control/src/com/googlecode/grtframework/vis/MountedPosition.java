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

	private final Mountable root;

	/**
	 * 2D relative orientation (angle)
	 */
	private double mountedAngle;

	// POLAR FORMS

	/**
	 * 'r' from polar notation: used for 2D position in polar form
	 */
	private double polarR;
	/**
	 * theta from polar notation: used for 2D position in polar form
	 */
	private double polarTheta;

	public MountedPosition(Mountable root) {
		this.root = root;
	}

	public MountedPosition(Mountable root, int x, int y, double mountedAngle) {
		this.root = root;
		// this.xOffset = xOffset;
		// this.yOffset = yOffset;
		this.mountedAngle = mountedAngle;

		setPositionRelative(x, y);
		setHeadingRelative(mountedAngle);
	}

	public void setHeadingRelative(double heading) {
		mountedAngle = heading;
	}

	public void setHeadingAbsolute(double heading) {
		// TODO unimplemented0
	}

	public void setPositionRelative(double x, double y) {
		polarR = Math.sqrt((x * x) + (y * y));
		polarTheta = Math.atan2(y, x);
	}

	public void setPositionAbsolute(double x, double y) {
		setPositionRelative(x - root.getX(), y - root.getY());
	}

	public double getR() {
		return polarR;
	}

	public double getTheta() {
		return polarTheta;
	}

	public double getHeadingRelative() {
		return mountedAngle;
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
		return (int) (root.getX() + polarR
				* Math.cos(root.getHeading() + polarTheta));
	}

	/**
	 * 
	 * @return absolute y position with respect to screen
	 */
	public int getY() {
		// calculates offset using polar form
		return (int) (root.getY() + polarR
				* Math.sin(root.getHeading() + polarTheta));
	}

	public Mountable getRoot() {
		return root;
	}

}
