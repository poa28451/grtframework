package com.googlecode.gunncs.graph;
import java.awt.Color;


public class LinePlotTest {
	// test
	public static void main(String[] args) {
		TimedLinePlot frame = new TimedLinePlot("sin(x)", 100);
		frame.setYRangingFixed(-1, 1);
		// frame.setYRangingAuto();

		frame.addData("Sin", Color.green, "radians", "none");

		frame.addData("Cos", Color.ORANGE, "radians", "none");
		double x = 0;
		for (;;) {
			System.out.println(x);
			x += .01;
			frame.addPoint("Sin", Math.sin(x));
			frame.addPoint("Cos", Math.cos(x));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
