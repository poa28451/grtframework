package com.googlecode.grtframework.sensor.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.googlecode.grtframework.event.PotentiometerEvent;
import com.googlecode.grtframework.event.PotentiometerListener;
import com.googlecode.grtframework.sensor.IPotentiometer;
import com.googlecode.grtframework.vis.Displayable;
import com.googlecode.grtframework.vis.Displayer;
import com.googlecode.grtframework.vis.Mountable;
import com.googlecode.grtframework.vis.MountedPosition;


/**
 * Draws a potentiometer given its events
 * 
 * @author ajc
 * 
 */
public class PotentiometerDisplay implements Displayable,
		PotentiometerListener, Mountable {

	private static final int POTENTIOMETER_DIAMETER = 20;
	private static final int WIPER_LENGTH = 30;
	private static final int POTENTIOMETER_OVAL_START = -(POTENTIOMETER_DIAMETER / 2);
	private static final int ID_SIZE = 3;

	private final MountedPosition position;

	// source
	private final IPotentiometer pot;

	// heading received by Potentiometer, radians
	private double angle = 0;
	private final Displayer sim;

	public PotentiometerDisplay(Displayer sim, IPotentiometer pot,
			MountedPosition position) {
		this.sim = sim;
		this.pot = pot;
		this.position = position;
	}

	@Override
	public void rotationChanged(PotentiometerEvent pev) {
		angle = pev.getAngle();
	}

	@Override
	public void rotationStarted(PotentiometerEvent pev) {

	}

	@Override
	public void rotationStopped(PotentiometerEvent pev) {

	}

	@Override
	public void startListening() {
		pot.addPotentiometerListener(this);
	}

	@Override
	public void stopListening() {
		pot.addPotentiometerListener(this);
	}

	@Override
	public void display(Graphics g) {

		int realX = getX(); // absolute coordinates
		int realY = getY(); // absolute coordinates
		double realAngle = getHeading(); // absolute coordinates of wiper
		double realZeroAngle = position.getHeading();

		// draw oval
		g.setColor(Color.GRAY);
		g.fillOval(realX + POTENTIOMETER_OVAL_START, realY
				+ POTENTIOMETER_OVAL_START, POTENTIOMETER_DIAMETER,
				POTENTIOMETER_DIAMETER);

		// draw a line
		g.setColor(Color.DARK_GRAY);
		g.drawLine(realX, realY, (int) (WIPER_LENGTH * Math.cos(realAngle))
				+ realX, (int) (WIPER_LENGTH * Math.sin(realAngle)) + realY);

		// reporting
		g.drawString("" + Math.toDegrees(angle), realX + 5, realY);// TODO

		// draw reference line
		g.setColor(Color.yellow);
		g
				.drawLine(realX, realY, (int) (WIPER_LENGTH * Math
						.cos(realZeroAngle))
						+ realX, (int) (WIPER_LENGTH * Math.sin(realZeroAngle))
						+ realY);

		g.setColor(Color.GREEN);
		g.fillRect(realX, realY, ID_SIZE, ID_SIZE);

	}

	@Override
	public void startDisplaying() {
		sim.addDisplayable(this);
	}

	// *********
	// MOUNTABLE
	// *********
	@Override
	public double getHeading() {
		return position.getHeading() + angle;
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
