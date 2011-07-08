package com.googlecode.grtframework.event;

/**
 * Receives Events from an EventPublisher and takes action on such events.
 * 
 * @see EventPublisher
 * @see Event
 * 
 * @author ajc
 * 
 */
public interface EventListener {
	/*
	 * status: done
	 */
	
	/**
	 * Makes this EventListener subscribe to events
	 */
	public void startListening();
	
	/**
	 * Makes this EventListener un-subscribe to events
	 */
	public void stopListening();

}
