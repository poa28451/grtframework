package com.googlecode.grtframework.event;

/**
 * An event represents a noteworthy occurrence, and abstract all relevant data
 * regarding this occurrence
 * 
 * @author ajc
 * 
 */
public interface Event {
	/*
	 * almost impossible to enforce anything as an interface
	 */

	/**
	 * 
	 * @return the object that sent this event
	 */
	public EventPublisher getSource();

}