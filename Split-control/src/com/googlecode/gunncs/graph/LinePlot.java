package com.googlecode.gunncs.graph;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyFixedViewport;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyUnbounded;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.util.Range;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.HashMap;

import javax.swing.JFrame;

/**
 * A LinePlot can display various points of data
 * 
 * @author ajc
 * 
 */
public class LinePlot {

	private static final Color LINE_COLOR = Color.LIGHT_GRAY;
	private static final Color BG_COLOR = Color.BLACK;

	private JFrame frame;
	private Chart2D chart;
	private ITrace2D trace;

	private HashMap<String, ITrace2D> traces;
	private final int capacity;

	/**
	 * Construct a LinePlot with a given name and data capacity
	 * 
	 * @param name
	 *            name of chart
	 * @param capacity
	 *            size of data to show
	 */
	public LinePlot(String name, int capacity) {
		this.capacity = capacity;
		chart = new Chart2D();
		chart.setGridColor(Color.WHITE);

		// swing jframe
		frame = new JFrame(name);
		frame.getContentPane().add(chart);
		frame.setVisible(true);

		traces = new HashMap<String, ITrace2D>();

		chart.setBackground(BG_COLOR);
	}

	/**
	 * Sets the Y bounds
	 * 
	 * @param min
	 * @param max
	 */
	public void setYRangingFixed(double min, double max) {
		Range r = new Range(min, max);
		chart.getAxisY().setRange(r);
		chart.getAxisY().setRangePolicy(new RangePolicyFixedViewport(r));
	}

	/**
	 * Sets Y range to admit any data
	 */
	public void setYRangingAuto() {
		chart.getAxisY().setRangePolicy(new RangePolicyUnbounded());
	}

	/**
	 * Adds a point to the given data set
	 * 
	 * @param name
	 *            name of data set
	 * @param x
	 *            x
	 * @param y
	 */
	public void addPoint(String name, double x, double y) {
		ITrace2D trace = traces.get(name);
		if (trace == null) {
			trace = new Trace2DLtd(capacity);
			trace.setColor(LINE_COLOR);
			trace.setName(name);
			chart.addTrace(trace);
			traces.put(name, trace);
		}
		trace.addPoint(x, y);
	}

	/**
	 * Adds a new data set given its color and units
	 * 
	 * @param name
	 *            name of data set
	 * @param c
	 *            color of data set
	 * @param xUnits
	 *            name of x units
	 * @param yUnits
	 *            name of y units
	 */
	public void addData(String name, Color c, String xUnits, String yUnits) {
		setDataColor(name, c);
		setDataUnits(name, xUnits, yUnits);
	}

	/**
	 * Sets data color
	 * 
	 * @param name
	 *            name of data set
	 * @param c
	 *            color of data
	 */
	public void setDataColor(String name, Color c) {
		ITrace2D trace = traces.get(name);
		if (trace == null) {
			trace = new Trace2DLtd(capacity);
			trace.setColor(LINE_COLOR);
			trace.setName(name);
			chart.addTrace(trace);
			traces.put(name, trace);
		}
		trace.setColor(c);
	}

	/**
	 * Adjusts trace width
	 * 
	 * @param name
	 *            name of data set
	 * @param width
	 *            width of trace [pixels]
	 */
	public void setDataStroke(String name, int width) {
		ITrace2D trace = traces.get(name);
		if (trace == null) {
			trace = new Trace2DLtd(capacity);
			trace.setColor(LINE_COLOR);
			trace.setName(name);
			chart.addTrace(trace);
			traces.put(name, trace);
		}
		Stroke s = new BasicStroke(width);
		trace.setStroke(s);
	}

	/**
	 * Sets a data set to be visible or not
	 * 
	 * @param name
	 *            name of data set
	 * @param visible
	 *            true to show data, false to not
	 */
	public void setDataVisible(String name, boolean visible) {
		ITrace2D trace = traces.get(name);
		if (trace == null) {
			trace = new Trace2DLtd(capacity);
			trace.setColor(LINE_COLOR);
			trace.setName(name);
			chart.addTrace(trace);
			traces.put(name, trace);
		}
		trace.setVisible(visible);
	}

	/**
	 * Sets data set physical units
	 * 
	 * @param name
	 *            name of data set
	 * @param x
	 *            name of x physical units
	 * @param y
	 *            name of y physical units
	 */
	public void setDataUnits(String name, String x, String y) {
		ITrace2D trace = traces.get(name);
		if (trace == null) {
			trace = new Trace2DLtd(capacity);
			trace.setColor(LINE_COLOR);
			trace.setName(name);
			chart.addTrace(trace);
			traces.put(name, trace);
		}
		trace.setPhysicalUnits(x, y);
	}

	/**
	 * Sets the graph's visibility
	 * 
	 * @param visible
	 *            true to show, false to hide
	 */
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

}