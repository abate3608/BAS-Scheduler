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
import edu.psu.sweng500.bacnetserver.bacnet4j2.service.Service;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.Address;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.ServicesSupported;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.OctetString;
import com.serotonin.util.queue.ByteQueue;

abstract public class UnconfirmedRequestService extends Service {
	private static final long serialVersionUID = 8962921362279665295L;

	public static UnconfirmedRequestService createUnconfirmedRequestService(ServicesSupported services, byte type,
			ByteQueue queue) throws BACnetException {
		if (type == IAmRequest.TYPE_ID) {
			if (services.isIAm())
				return new IAmRequest(queue);
			return null;
		}

		if (type == IHaveRequest.TYPE_ID) {
			if (services.isIHave())
				return new IHaveRequest(queue);
			return null;
		}

		if (type == UnconfirmedCovNotificationRequest.TYPE_ID) {
			if (services.isUnconfirmedCovNotification())
				return new UnconfirmedCovNotificationRequest(queue);
			return null;
		}

		if (type == UnconfirmedEventNotificationRequest.TYPE_ID) {
			if (services.isUnconfirmedEventNotification())
				return new UnconfirmedEventNotificationRequest(queue);
			return null;
		}

		if (type == UnconfirmedPrivateTransferRequest.TYPE_ID) {
			if (services.isUnconfirmedPrivateTransfer())
				return new UnconfirmedPrivateTransferRequest(queue);
			return null;
		}

		if (type == UnconfirmedTextMessageRequest.TYPE_ID) {
			if (services.isUnconfirmedTextMessage())
				return new UnconfirmedTextMessageRequest(queue);
			return null;
		}

		if (type == TimeSynchronizationRequest.TYPE_ID) {
			if (services.isTimeSynchronization())
				return new TimeSynchronizationRequest(queue);
			return null;
		}

		if (type == WhoHasRequest.TYPE_ID) {
			if (services.isWhoHas())
				return new WhoHasRequest(queue);
			return null;
		}

		if (type == WhoIsRequest.TYPE_ID) {
			if (services.isWhoIs())
				return new WhoIsRequest(queue);
			return null;
		}

		if (type == UTCTimeSynchronizationRequest.TYPE_ID) {
			if (services.isUtcTimeSynchronization())
				return new UTCTimeSynchronizationRequest(queue);
			return null;
		}

		throw new BACnetException("Unsupported unconfirmed service: " + (type & 0xff));
	}

	abstract public void handle(LocalDevice localDevice, Address from, OctetString linkService) throws BACnetException;
}
