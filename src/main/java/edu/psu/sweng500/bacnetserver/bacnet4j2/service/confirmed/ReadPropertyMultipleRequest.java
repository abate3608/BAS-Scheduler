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
package bacnet4j2.service.confirmed;

import java.util.ArrayList;
import java.util.List;

import bacnet4j2.LocalDevice;
import bacnet4j2.exception.BACnetErrorException;
import bacnet4j2.exception.BACnetException;
import bacnet4j2.exception.BACnetServiceException;
import bacnet4j2.obj.BACnetObject;
import bacnet4j2.obj.ObjectProperties;
import bacnet4j2.obj.PropertyTypeDefinition;
import bacnet4j2.service.acknowledgement.AcknowledgementService;
import bacnet4j2.service.acknowledgement.ReadPropertyMultipleAck;
import bacnet4j2.type.constructed.Address;
import bacnet4j2.type.constructed.BACnetError;
import bacnet4j2.type.constructed.PropertyReference;
import bacnet4j2.type.constructed.ReadAccessResult;
import bacnet4j2.type.constructed.ReadAccessResult.Result;
import bacnet4j2.type.constructed.ReadAccessSpecification;
import bacnet4j2.type.constructed.SequenceOf;
import bacnet4j2.type.enumerated.PropertyIdentifier;
import bacnet4j2.type.primitive.ObjectIdentifier;
import bacnet4j2.type.primitive.OctetString;
import bacnet4j2.type.primitive.UnsignedInteger;
import com.serotonin.util.queue.ByteQueue;

public class ReadPropertyMultipleRequest extends ConfirmedRequestService {
    private static final long serialVersionUID = 1994873785772969841L;

    public static final byte TYPE_ID = 14;

    private final SequenceOf<ReadAccessSpecification> listOfReadAccessSpecs;

    public ReadPropertyMultipleRequest(SequenceOf<ReadAccessSpecification> listOfReadAccessSpecs) {
        this.listOfReadAccessSpecs = listOfReadAccessSpecs;
    }

    @Override
    public byte getChoiceId() {
        return TYPE_ID;
    }

    @Override
    public void write(ByteQueue queue) {
        write(queue, listOfReadAccessSpecs);
    }

    ReadPropertyMultipleRequest(ByteQueue queue) throws BACnetException {
        listOfReadAccessSpecs = readSequenceOf(queue, ReadAccessSpecification.class);
    }

    @Override
    public AcknowledgementService handle(LocalDevice localDevice, Address from, OctetString linkService)
            throws BACnetException {
        BACnetObject obj;
        ObjectIdentifier oid;
        List<ReadAccessResult> readAccessResults = new ArrayList<ReadAccessResult>();
        List<Result> results;

        try {
            for (ReadAccessSpecification req : listOfReadAccessSpecs) {
                results = new ArrayList<Result>();
                oid = req.getObjectIdentifier();
                obj = localDevice.getObjectRequired(oid);

                for (PropertyReference propRef : req.getListOfPropertyReferences())
                    addProperty(obj, results, propRef.getPropertyIdentifier(), propRef.getPropertyArrayIndex());

                readAccessResults.add(new ReadAccessResult(oid, new SequenceOf<Result>(results)));
            }
        }
        catch (BACnetServiceException e) {
            throw new BACnetErrorException(getChoiceId(), e);
        }

        return new ReadPropertyMultipleAck(new SequenceOf<ReadAccessResult>(readAccessResults));
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((listOfReadAccessSpecs == null) ? 0 : listOfReadAccessSpecs.hashCode());
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
        final ReadPropertyMultipleRequest other = (ReadPropertyMultipleRequest) obj;
        if (listOfReadAccessSpecs == null) {
            if (other.listOfReadAccessSpecs != null)
                return false;
        }
        else if (!listOfReadAccessSpecs.equals(other.listOfReadAccessSpecs))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ReadPropertyMultipleRequest [listOfReadAccessSpecs=" + listOfReadAccessSpecs + "]";
    }

    private void addProperty(BACnetObject obj, List<Result> results, PropertyIdentifier pid, UnsignedInteger pin) {
        if (pid.intValue() == PropertyIdentifier.all.intValue()) {
            for (PropertyTypeDefinition def : ObjectProperties.getPropertyTypeDefinitions(obj.getId().getObjectType()))
                addProperty(obj, results, def.getPropertyIdentifier(), pin);
        }
        else if (pid.intValue() == PropertyIdentifier.required.intValue()) {
            for (PropertyTypeDefinition def : ObjectProperties.getRequiredPropertyTypeDefinitions(obj.getId()
                    .getObjectType()))
                addProperty(obj, results, def.getPropertyIdentifier(), pin);
        }
        else if (pid.intValue() == PropertyIdentifier.optional.intValue()) {
            for (PropertyTypeDefinition def : ObjectProperties.getOptionalPropertyTypeDefinitions(obj.getId()
                    .getObjectType()))
                addProperty(obj, results, def.getPropertyIdentifier(), pin);
        }
        else {
            // Get the specified property.
            try {
                results.add(new Result(pid, pin, obj.getPropertyRequired(pid, pin)));
            }
            catch (BACnetServiceException e) {
                results.add(new Result(pid, pin, new BACnetError(e.getErrorClass(), e.getErrorCode())));
            }
        }
    }
}
