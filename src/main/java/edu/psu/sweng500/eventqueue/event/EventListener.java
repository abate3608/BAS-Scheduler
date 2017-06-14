package event;

public interface EventListener {
	/**
     * Notification of an exception while calling a listener method.
     */
    void listenerException(Throwable e);
    
    
}
