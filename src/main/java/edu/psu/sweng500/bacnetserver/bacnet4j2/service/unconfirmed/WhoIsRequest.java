/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * When signing a commercial license with Serotonin Software Technologies Inc.,
 * the following extension to GPL is made. A special exception to the GPL is 
 * included to allow you to distribute a combined work that includes BAcnet4J 
 * without being obliged to provide the source code for any proprietary components.
 */
package edu.psu.sweng500.bacnetserver.bacnet4j2.service.unconfirmed;

import edu.psu.sweng500.bacnetserver.bacnet4j2.LocalDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.obj.BACnetObject;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.Address;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.OctetString;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.UnsignedInteger;
import com.serotonin.util.queue.ByteQueue;
import java.util.Date;

public class WhoIsRequest extends UnconfirmedRequestService {
    private static final long serialVersionUID = 4853007370475322913L;

    public static final byte TYPE_ID = 8;

    private UnsignedInteger deviceInstanceRangeLowLimit;
    private UnsignedInteger deviceInstanceRangeHighLimit;

    public WhoIsRequest() {
        // no op
    }

    public WhoIsRequest(UnsignedInteger deviceInstanceRangeLowLimit, UnsignedInteger deviceInstanceRangeHighLimit) {
        this.deviceInstanceRangeLowLimit = deviceInstanceRangeLowLimit;
        this.deviceInstanceRangeHighLimit = deviceInstanceRangeHighLimit;
    }

    @Override
    public byte getChoiceId() {
        return TYPE_ID;
    }

    @Override
    public void handle(LocalDevice localDevice, Address from, OctetString linkService) throws BACnetException {
        BACnetObject local = localDevice.getConfiguration();
        
        //Phong Nguyen
        Date date = new Date();
        System.out.println(date.toString() + ": Who-is request received from " + from);
        
        // Check if we're in the device id range.
        if (deviceInstanceRangeLowLimit != null && local.getInstanceId() < deviceInstanceRangeLowLimit.intValue())
            return;

        if (deviceInstanceRangeHighLimit != null && local.getInstanceId() > deviceInstanceRangeHighLimit.intValue())
            return;

        // Return the result in a i am message.
        //IAmRequest iam = localDevice.getIAm();
        //localDevice.sendGlobalBroadcast(iam);
        //localDevice.sendUnconfirmed(from.getDescription(), null, localDevice.getIAm()); 
        
        
        //Phong Nguyen 6-4-2017
        localDevice.sendBroadcast(from, null, localDevice.getIAm()); 
        System.out.println(date.toString() + ": Respond to Who-is received from " + from);
        
        
    }

    @Override
    public void write(ByteQueue queue) {
        writeOptional(queue, deviceInstanceRangeLowLimit, 0);
        writeOptional(queue, deviceInstanceRangeHighLimit, 1);
    }

    WhoIsRequest(ByteQueue queue) throws BACnetException {
        deviceInstanceRangeLowLimit = readOptional(queue, UnsignedInteger.class, 0);
        deviceInstanceRangeHighLimit = readOptional(queue, UnsignedInteger.class, 1);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result
                + ((deviceInstanceRangeHighLimit == null) ? 0 : deviceInstanceRangeHighLimit.hashCode());
        result = PRIME * result + ((deviceInstanceRangeLowLimit == null) ? 0 : deviceInstanceRangeLowLimit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final WhoIsRequest other = (WhoIsRequest) obj;
        if (deviceInstanceRangeHighLimit == null) {
            if (other.deviceInstanceRangeHighLimit != null)
                return false;
        }
        else if (!deviceInstanceRangeHighLimit.equals(other.deviceInstanceRangeHighLimit))
            return false;
        if (deviceInstanceRangeLowLimit == null) {
            if (other.deviceInstanceRangeLowLimit != null)
                return false;
        }
        else if (!deviceInstanceRangeLowLimit.equals(other.deviceInstanceRangeLowLimit))
            return false;
        return true;
    }
}
