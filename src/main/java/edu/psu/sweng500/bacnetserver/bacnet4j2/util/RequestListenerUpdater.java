package bacnet4j2.util;

import bacnet4j2.type.Encodable;
import bacnet4j2.type.enumerated.PropertyIdentifier;
import bacnet4j2.type.primitive.ObjectIdentifier;
import bacnet4j2.type.primitive.UnsignedInteger;

public class RequestListenerUpdater {
    private final RequestListener callback;
    private final PropertyValues propertyValues;
    private final int max;
    private int current;
    private boolean cancelled;

    public RequestListenerUpdater(RequestListener callback, PropertyValues propertyValues, int max) {
        this.callback = callback;
        this.propertyValues = propertyValues;
        this.max = max;
    }

    public void increment(ObjectIdentifier oid, PropertyIdentifier pid, UnsignedInteger pin, Encodable value) {
        current++;
        if (callback != null)
            cancelled = callback.requestProgress(((double) current) / max, oid, pid, pin, value);
        propertyValues.add(oid, pid, pin, value);
    }

    public boolean cancelled() {
        return cancelled;
    }
}
