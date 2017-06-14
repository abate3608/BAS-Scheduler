package edu.psu.sweng500.bacnetserver.bacnet4j2.event;

import edu.psu.sweng500.bacnetserver.bacnet4j2.RemoteDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.RemoteObject;
import edu.psu.sweng500.bacnetserver.bacnet4j2.obj.BACnetObject;
import edu.psu.sweng500.bacnetserver.bacnet4j2.service.confirmed.ReinitializeDeviceRequest.ReinitializedStateOfDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.Encodable;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.Choice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.DateTime;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.PropertyValue;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.SequenceOf;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.TimeStamp;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.EventState;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.EventType;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.MessagePriority;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.NotifyType;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.notificationparameters.NotificationParameters;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.CharacterString;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.ObjectIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.UnsignedInteger;

/**
 * A default class for easy implementation of the DeviceEventListener interface. Instead of having to implement all of
 * the defined methods, listener classes can override this and only implement the desired methods.
 * 
 * @author Matthew Lohbihler
 */
public class DeviceEventAdapter implements DeviceEventListener {
    //@Override
    public void listenerException(Throwable e) {
        // Override as required
        e.printStackTrace();
    }

    //@Override
    public boolean allowPropertyWrite(BACnetObject obj, PropertyValue pv) {
        return true;
    }

    //@Override
    public void iAmReceived(final RemoteDevice d) {
        // Override as required
    }

    //@Override
    public void propertyWritten(final BACnetObject obj, final PropertyValue pv) {
        // Override as required
    }

    //@Override
    public void iHaveReceived(final RemoteDevice d, final RemoteObject o) {
        // Override as required
    }

    //@Override
    public void covNotificationReceived(final UnsignedInteger subscriberProcessIdentifier,
            final RemoteDevice initiatingDevice, final ObjectIdentifier monitoredObjectIdentifier,
            final UnsignedInteger timeRemaining, final SequenceOf<PropertyValue> listOfValues) {
        // Override as required
    }

    //@Override
    public void eventNotificationReceived(final UnsignedInteger processIdentifier, final RemoteDevice initiatingDevice,
            final ObjectIdentifier eventObjectIdentifier, final TimeStamp timeStamp,
            final UnsignedInteger notificationClass, final UnsignedInteger priority, final EventType eventType,
            final CharacterString messageText, final NotifyType notifyType,
            final edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.Boolean ackRequired, final EventState fromState,
            final EventState toState, final NotificationParameters eventValues) {
        // Override as required
    }

    //@Override
    public void textMessageReceived(final RemoteDevice textMessageSourceDevice, final Choice messageClass,
            final MessagePriority messagePriority, final CharacterString message) {
        // Override as required
    }

    //@Override
    public void privateTransferReceived(final UnsignedInteger vendorId, final UnsignedInteger serviceNumber,
            final Encodable serviceParameters) {
        // Override as required
    }

    //@Override
    public void reinitializeDevice(final ReinitializedStateOfDevice reinitializedStateOfDevice) {
        // Override as required
    }

    //@Override
    public void synchronizeTime(final DateTime dateTime, final boolean utc) {
        // Override as required
    }
}
