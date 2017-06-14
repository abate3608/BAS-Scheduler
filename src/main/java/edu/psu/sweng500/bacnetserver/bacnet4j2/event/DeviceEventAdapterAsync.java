package bacnet4j2.event;

import java.util.concurrent.ExecutorService;

import bacnet4j2.RemoteDevice;
import bacnet4j2.RemoteObject;
import bacnet4j2.obj.BACnetObject;
import bacnet4j2.service.confirmed.ReinitializeDeviceRequest.ReinitializedStateOfDevice;
import bacnet4j2.type.Encodable;
import bacnet4j2.type.constructed.Choice;
import bacnet4j2.type.constructed.DateTime;
import bacnet4j2.type.constructed.PropertyValue;
import bacnet4j2.type.constructed.SequenceOf;
import bacnet4j2.type.constructed.TimeStamp;
import bacnet4j2.type.enumerated.EventState;
import bacnet4j2.type.enumerated.EventType;
import bacnet4j2.type.enumerated.MessagePriority;
import bacnet4j2.type.enumerated.NotifyType;
import bacnet4j2.type.notificationparameters.NotificationParameters;
import bacnet4j2.type.primitive.CharacterString;
import bacnet4j2.type.primitive.ObjectIdentifier;
import bacnet4j2.type.primitive.UnsignedInteger;

/**
 * Event listener adapter that provides asynchronous notifications. Override the "async" methods to get async
 * notifications, or override the non-async method to receive synchronously. Note that methods that are not
 * overridden will go through the async dispatcher, so the non-async methods should be overridden even if they are not
 * needed.
 * 
 * @author Matthew
 */
public class DeviceEventAdapterAsync implements DeviceEventListener {
    private ExecutorService dispatchService;

    public DeviceEventAdapterAsync() {
        this(null);
    }

    public DeviceEventAdapterAsync(ExecutorService dispatchService) {
        this.dispatchService = dispatchService;
    }

    public ExecutorService getDispatchService() {
        return dispatchService;
    }

    public void setDispatchService(ExecutorService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @Override
    public void listenerException(Throwable e) {
        // Override as required
        e.printStackTrace();
    }

    @Override
    public boolean allowPropertyWrite(BACnetObject obj, PropertyValue pv) {
        return true;
    }

    @Override
    public void iAmReceived(final RemoteDevice d) {
        dispatch(new DispatchCallback() {
            @Override
            public void dispatch() {
                iAmReceivedAsync(d);
            }
        });
    }

    /**
     * @param d
     */
    public void iAmReceivedAsync(RemoteDevice d) {
        // Override as required
    }

    @Override
    public void propertyWritten(final BACnetObject obj, final PropertyValue pv) {
        dispatch(new DispatchCallback() {
            @Override
            public void dispatch() {
                propertyWrittenAsync(obj, pv);
            }
        });
    }

    /**
     * @param obj
     * @param pv
     */
    public void propertyWrittenAsync(BACnetObject obj, PropertyValue pv) {
        // Override as required
    }

    @Override
    public void iHaveReceived(final RemoteDevice d, final RemoteObject o) {
        dispatch(new DispatchCallback() {
            @Override
            public void dispatch() {
                iHaveReceivedAsync(d, o);
            }
        });
    }

    /**
     * @param d
     * @param o
     */
    public void iHaveReceivedAsync(RemoteDevice d, RemoteObject o) {
        // Override as required
    }

    @Override
    public void covNotificationReceived(final UnsignedInteger subscriberProcessIdentifier,
            final RemoteDevice initiatingDevice, final ObjectIdentifier monitoredObjectIdentifier,
            final UnsignedInteger timeRemaining, final SequenceOf<PropertyValue> listOfValues) {
        dispatch(new DispatchCallback() {
            @Override
            public void dispatch() {
                covNotificationReceivedASync(subscriberProcessIdentifier, initiatingDevice, monitoredObjectIdentifier,
                        timeRemaining, listOfValues);
            }
        });
    }

    /**
     * @param subscriberProcessIdentifier
     * @param initiatingDevice
     * @param monitoredObjectIdentifier
     * @param timeRemaining
     * @param listOfValues
     */
    public void covNotificationReceivedASync(UnsignedInteger subscriberProcessIdentifier,
            RemoteDevice initiatingDevice, ObjectIdentifier monitoredObjectIdentifier, UnsignedInteger timeRemaining,
            SequenceOf<PropertyValue> listOfValues) {
        // Override as required
    }

    @Override
    public void eventNotificationReceived(final UnsignedInteger processIdentifier, final RemoteDevice initiatingDevice,
            final ObjectIdentifier eventObjectIdentifier, final TimeStamp timeStamp,
            final UnsignedInteger notificationClass, final UnsignedInteger priority, final EventType eventType,
            final CharacterString messageText, final NotifyType notifyType,
            final bacnet4j2.type.primitive.Boolean ackRequired, final EventState fromState,
            final EventState toState, final NotificationParameters eventValues) {
        dispatch(new DispatchCallback() {
            @Override
            public void dispatch() {
                eventNotificationReceivedAsyn(processIdentifier, initiatingDevice, eventObjectIdentifier, timeStamp,
                        notificationClass, priority, eventType, messageText, notifyType, ackRequired, fromState,
                        toState, eventValues);
            }
        });
    }

    /**
     * @param processIdentifier
     * @param initiatingDevice
     * @param eventObjectIdentifier
     * @param timeStamp
     * @param notificationClass
     * @param priority
     * @param eventType
     * @param messageText
     * @param notifyType
     * @param ackRequired
     * @param fromState
     * @param toState
     * @param eventValues
     */
    public void eventNotificationReceivedAsyn(UnsignedInteger processIdentifier, RemoteDevice initiatingDevice,
            ObjectIdentifier eventObjectIdentifier, TimeStamp timeStamp, UnsignedInteger notificationClass,
            UnsignedInteger priority, EventType eventType, CharacterString messageText, NotifyType notifyType,
            bacnet4j2.type.primitive.Boolean ackRequired, EventState fromState, EventState toState,
            NotificationParameters eventValues) {
        // Override as required
    }

    @Override
    public void textMessageReceived(final RemoteDevice textMessageSourceDevice, final Choice messageClass,
            final MessagePriority messagePriority, final CharacterString message) {
        dispatch(new DispatchCallback() {
            @Override
            public void dispatch() {
                textMessageReceivedAsync(textMessageSourceDevice, messageClass, messagePriority, message);
            }
        });
    }

    /**
     * @param textMessageSourceDevice
     * @param messageClass
     * @param messagePriority
     * @param message
     */
    public void textMessageReceivedAsync(RemoteDevice textMessageSourceDevice, Choice messageClass,
            MessagePriority messagePriority, CharacterString message) {
        // Override as required
    }

    @Override
    public void privateTransferReceived(final UnsignedInteger vendorId, final UnsignedInteger serviceNumber,
            final Encodable serviceParameters) {
        dispatch(new DispatchCallback() {
            @Override
            public void dispatch() {
                privateTransferReceivedAsync(vendorId, serviceNumber, serviceParameters);
            }
        });
    }

    /**
     * @param vendorId
     * @param serviceNumber
     * @param serviceParameters
     */
    public void privateTransferReceivedAsync(UnsignedInteger vendorId, UnsignedInteger serviceNumber,
            Encodable serviceParameters) {
        // Override as required
    }

    @Override
    public void reinitializeDevice(final ReinitializedStateOfDevice reinitializedStateOfDevice) {
        dispatch(new DispatchCallback() {
            @Override
            public void dispatch() {
                reinitializeDeviceAsync(reinitializedStateOfDevice);
            }
        });
    }

    /**
     * @param reinitializedStateOfDevice
     */
    public void reinitializeDeviceAsync(ReinitializedStateOfDevice reinitializedStateOfDevice) {
        // Override as required
    }

    @Override
    public void synchronizeTime(final DateTime dateTime, final boolean utc) {
        dispatch(new DispatchCallback() {
            @Override
            public void dispatch() {
                synchronizeTimeAsync(dateTime, utc);
            }
        });
    }

    /**
     * @param dateTime
     * @param utc
     */
    public void synchronizeTimeAsync(DateTime dateTime, boolean utc) {
        // Override as required
    }

    //
    //
    // Private stuff
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
     * @author mlohbihler
     */
    private class EventDispatcher implements Runnable {
        DispatchCallback callback;

        EventDispatcher(DispatchCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                callback.dispatch();
            }
            catch (Throwable e) {
                try {
                    DeviceEventAdapterAsync.this.listenerException(e);
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
     * @author mlohbihler
     */
    private interface DispatchCallback {
        void dispatch();
    }
}
