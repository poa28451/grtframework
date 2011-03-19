package com.grt192.core;

/**
 * A Controller that only responds to events
 * @author anand
 */
public abstract class EventController extends Controller {

    public void run() {
    }

    public abstract void startListening();

    public abstract void stopListening();
}
