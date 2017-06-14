package edu.psu.sweng500.eventqueue.event;

public class EventAdapter implements EventListener {
	//@Override
    public void listenerException(Throwable e) {
        // Override as required
        e.printStackTrace();
    }
}
