package com.googlecode.grtframework.vis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * 
 * @author ajc
 * 
 */
public class Displayer extends JComponent {

	private ArrayList<Displayable> drawables;
	private boolean paintLoop;

	public Displayer() {
		drawables = new ArrayList<Displayable>();
		paintLoop = false;
	}

	// TODO precondition, and where is this called from?
	/**
	 * Starts painting.
	 * 
	 * @precondition is there any precondition?
	 */
	public void startPaintLoop() {
		paintLoop = true;
		repaint();
	}

	/**
	 * Stops painting
	 */
	public void stopPaintLoop() {
		paintLoop = false;

	}

	public void paintComponent(Graphics g) {

		// render hints
		renderHints(g);
		// white background
		whiteBG(g);
		// paint things
		paintDisplayables(g);
		// g.setColor(Color.RED);
		// g.fillRect(100, 100, 100, 100);
		// g.setColor(Color.BLACK);
		// g.drawString("Epic... Fail.", 150, 150);
		if (paintLoop) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}

	private void renderHints(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_SPEED);
		// g2.setRenderingHint(RenderingHints.KEY_D, RenderingHints.VALUE_DI)
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_SPEED);
	}

	private void whiteBG(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintDisplayables(Graphics g) {
		for (Displayable d : drawables) {
			d.display(g);
		}
	}

	/**
	 * Adds an object to be drawn
	 * 
	 * @param d
	 */
	public void addDisplayable(Displayable d) {
		drawables.add(d);
	}
}
