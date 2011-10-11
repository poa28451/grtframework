package com.googlecode.grtframework.vis;

/**
 * A MountedPosition encapsulates position references
 * 
 * @author ajc
 * 
 */
public interface IMountedPosition extends Mountable {

	public double getXRelative();

	public double getYRelative();

	/**
	 * 
	 * @param heading
	 */
	public void setHeadingRelative(double heading);

	/**
	 * 
	 * @param heading
	 */
	public void setHeadingAbsolute(double heading);

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setPositionRelative(double x, double y);

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setPositionAbsolute(double x, double y);

	/**
	 * 
	 * @return
	 */
	public double getR();

	/**
	 * 
	 * @return
	 */
	public double getTheta();

	/**
	 * 
	 * @return
	 */
	public double getHeadingRelative();

	/**
	 * 
	 * @return
	 */
	public Mountable[] getParents();

}
