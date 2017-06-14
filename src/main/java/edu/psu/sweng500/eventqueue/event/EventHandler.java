package edu.psu.sweng500.eventqueue.event;

import java.util.concurrent.ConcurrentLinkedQueue;


public class EventHandler {
    final ConcurrentLinkedQueue<EventListener> listeners = new ConcurrentLinkedQueue<EventListener>();

    //
    //
    // Listener management
    //
    public void addListener(EventListener l) {
        listeners.add(l);
    }

    public void removeListener(EventListener l) {
        listeners.remove(l);
    }

    public int getListenerCount() {
        return listeners.size();
    }
    
    private void handleException(EventListener l, Throwable e) {
        try {
            l.listenerException(e);
        }
        catch (Throwable e1) {
            // no op
        }
    }
}
