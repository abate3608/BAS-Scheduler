package bacnet4j2.event;

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
 * A default class for easy implementation of the DeviceEventListener interface. Instead of having to implement all of
 * the defined methods, listener classes can override this and only implement the desired methods.
 * 
 * @author Matthew Lohbihler
 */
public class DeviceEventAdapter implements DeviceEventListener {
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
        // Override as required
    }

    @Override
    public void propertyWritten(final BACnetObject obj, final PropertyValue pv) {
        // Override as required
    }

    @Override
    public void iHaveReceived(final RemoteDevice d, final RemoteObject o) {
        // Override as required
    }

    @Override
    public void covNotificationReceived(final UnsignedInteger subscriberProcessIdentifier,
            final RemoteDevice initiatingDevice, final ObjectIdentifier monitoredObjectIdentifier,
            final UnsignedInteger timeRemaining, final SequenceOf<PropertyValue> listOfValues) {
        // Override as required
    }

    @Override
    public void eventNotificationReceived(final UnsignedInteger processIdentifier, final RemoteDevice initiatingDevice,
            final ObjectIdentifier eventObjectIdentifier, final TimeStamp timeStamp,
            final UnsignedInteger notificationClass, final UnsignedInteger priority, final EventType eventType,
            final CharacterString messageText, final NotifyType notifyType,
            final bacnet4j2.type.primitive.Boolean ackRequired, final EventState fromState,
            final EventState toState, final NotificationParameters eventValues) {
        // Override as required
    }

    @Override
    public void textMessageReceived(final RemoteDevice textMessageSourceDevice, final Choice messageClass,
            final MessagePriority messagePriority, final CharacterString message) {
        // Override as required
    }

    @Override
    public void privateTransferReceived(final UnsignedInteger vendorId, final UnsignedInteger serviceNumber,
            final Encodable serviceParameters) {
        // Override as required
    }

    @Override
    public void reinitializeDevice(final ReinitializedStateOfDevice reinitializedStateOfDevice) {
        // Override as required
    }

    @Override
    public void synchronizeTime(final DateTime dateTime, final boolean utc) {
        // Override as required
    }
}
