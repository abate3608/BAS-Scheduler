package edu.psu.sweng500.eventqueue.event;

import java.util.concurrent.ExecutorService;

public class EventAdapterAsync implements EventListener {
	private ExecutorService dispatchService;

    public EventAdapterAsync() {
        this(null);
    }

    public EventAdapterAsync(ExecutorService dispatchService) {
        this.dispatchService = dispatchService;
    }

    public ExecutorService getDispatchService() {
        return dispatchService;
    }

    public void setDispatchService(ExecutorService dispatchService) {
        this.dispatchService = dispatchService;
    }

   // @Override
    public void listenerException(Throwable e) {
        // Override as required
        e.printStackTrace();
    }
    
    
    
    //
    //
    // 
    //
    private void dispatch(DispatchCallback callback) {
        if (dispatchService == null)
            listenerException(new IllegalStateException("DeviceEventHandler has not been initialized"));
        else
            dispatchService.execute(new EventDispatcher(callback));
    }

    /**
     * Class for dispatching an event to multiple listeners
     * 
     */
    private class EventDispatcher implements Runnable {
        DispatchCallback callback;

        EventDispatcher(DispatchCallback callback) {
            this.callback = callback;
        }

        //@Override
        public void run() {
            try {
                callback.dispatch();
            }
            catch (Throwable e) {
                try {
                    EventAdapterAsync.this.listenerException(e);
                }
                catch (Throwable e1) {
                    // no op
                }
            }
        }
    }

    /**
     * Interface for defining how a particular event is dispatched to listeners
     * 
     */
    private interface DispatchCallback {
        void dispatch();
    }
    
}
