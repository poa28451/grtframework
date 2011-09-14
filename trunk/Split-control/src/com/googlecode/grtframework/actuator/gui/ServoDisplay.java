package com.googlecode.grtframework.actuator.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.googlecode.grtframework.actuator.IServo;
import com.googlecode.grtframework.vis.Displayable;
import com.googlecode.grtframework.vis.Displayer;
import com.googlecode.grtframework.vis.Mountable;
import com.googlecode.grtframework.vis.MountedPosition;

/**
 * A virtual Servo that can be used as a MountPoint.
 * 
 * @author ajc
 * 
 */
public class ServoDisplay implements Displayable, IServo, Mountable {

	// Display visual constraints
	private static final int HORN_LENGTH = 40;

	private static final int CHASSIS_LENGTH = 30;
	private static final int CHASSIS_WIDTH = 15;
	private static final int CHASSIS_X_START = CHASSIS_LENGTH
			- (CHASSIS_WIDTH / 2);
	private static final int CHASSIS_Y_START = CHASSIS_WIDTH / 2;

	private static final int CHASSIS_ID_SIZE = 3;

	private MountedPosition position;

	private double hornAngle;
	private final Displayer display;

	/**
	 * Constructs a Virtual Servo given its displayer, MountPoint, and 2D
	 * orientation relative to the MountPoint
	 * 
	 * @param display
	 * @param mp
	 * @param x
	 * @param y
	 * @param mountPointAngle
	 */
	public ServoDisplay(Displayer display, MountedPosition position) {
		this.display = display;
		this.position = position;
	}

	@Override
	public void setPercentPosition(double percent) {
		this.hornAngle = (percent * Math.PI);
	}

	@Override
	public void setPosition(double radians) {
		this.hornAngle = radians;
	}

	// ******
	// DISPLAY
	// *******
	@Override
	public void startDisplaying() {
		display.addDisplayable(this);
	}

	@Override
	public void display(Graphics g) {

		// MountPoint methods can be used because the MountPoint is the same as
		// the Servo's center 2D orientation
		int y = getY();
		int x = getX();
		// Heading is calculated on its own because the angle of the entire
		// servo is not the same as the angle of the MountPoint(on the horns)
		double trueHeading = position.getHeading();
		// draw chassis
		drawChassis(g, x, y, trueHeading);
		// draw horns
		drawHorns(g, x, y, getHeading());

	}

	/**
	 * Draws a Servo chassis given absolute 2D orientation and position.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param heading
	 */
	private static void drawChassis(Graphics g, int x, int y, double heading) {
		Graphics2D g2 = (Graphics2D) g;

		// rotate to trueHeading with respect to absolute 2d orientation and
		// position
		AffineTransform orig = g2.getTransform();
		AffineTransform box = new AffineTransform();
		box.rotate(heading, x, y);
		g2.setTransform(box);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(x - CHASSIS_X_START, y - CHASSIS_Y_START, CHASSIS_LENGTH,
				CHASSIS_WIDTH);

		// lord english horn ID's
		Color rand = new Color((int) (Math.random() * 255), (int) (Math
				.random() * 255), (int) (Math.random() * 255));
		g.setColor(rand);
		g.fillRect(x, y, CHASSIS_ID_SIZE, CHASSIS_ID_SIZE);

		// unrotate
		g2.setTransform(orig);
	}

	/**
	 * Draws a servo's horns given absolute 2D orientation and position
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param heading
	 * @param hornAngle
	 */
	private static void drawHorns(Graphics g, int x, int y, double absHorns) {
		g.setColor(Color.GRAY);
		g.drawLine(x, y, (int) (HORN_LENGTH * Math.cos(absHorns)) + x,
				(int) (HORN_LENGTH * Math.sin(absHorns)) + y);

	}

	// *********
	// MOUNTABLE
	// *********
	@Override
	public double getHeading() {
		return position.getHeading() + hornAngle;
	}

	@Override
	public int getX() {
		return position.getX();
	}

	@Override
	public int getY() {
		return position.getY();
	}

}