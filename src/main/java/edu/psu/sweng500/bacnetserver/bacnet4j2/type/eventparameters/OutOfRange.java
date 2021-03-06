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
package edu.psu.sweng500.bacnetserver.bacnet4j2.type.eventparameters;

import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.Real;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.UnsignedInteger;
import com.serotonin.util.queue.ByteQueue;

public class OutOfRange extends EventParameter {
	private static final long serialVersionUID = 6279370879628323322L;

	public static final byte TYPE_ID = 5;

	private final UnsignedInteger timeDelay;
	private final Real lowLimit;
	private final Real highLimit;
	private final Real deadband;

	public OutOfRange(UnsignedInteger timeDelay, Real lowLimit, Real highLimit, Real deadband) {
		this.timeDelay = timeDelay;
		this.lowLimit = lowLimit;
		this.highLimit = highLimit;
		this.deadband = deadband;
	}

	@Override
	protected void writeImpl(ByteQueue queue) {
		write(queue, timeDelay, 0);
		write(queue, lowLimit, 1);
		write(queue, highLimit, 2);
		write(queue, deadband, 3);
	}

	public OutOfRange(ByteQueue queue) throws BACnetException {
		timeDelay = read(queue, UnsignedInteger.class, 0);
		lowLimit = read(queue, Real.class, 1);
		highLimit = read(queue, Real.class, 2);
		deadband = read(queue, Real.class, 3);
	}

	@Override
	protected int getTypeId() {
		return TYPE_ID;
	}

	public UnsignedInteger getTimeDelay() {
		return timeDelay;
	}

	public Real getLowLimit() {
		return lowLimit;
	}

	public Real getHighLimit() {
		return highLimit;
	}

	public Real getDeadband() {
		return deadband;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((deadband == null) ? 0 : deadband.hashCode());
		result = PRIME * result + ((highLimit == null) ? 0 : highLimit.hashCode());
		result = PRIME * result + ((lowLimit == null) ? 0 : lowLimit.hashCode());
		result = PRIME * result + ((timeDelay == null) ? 0 : timeDelay.hashCode());
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
		final OutOfRange other = (OutOfRange) obj;
		if (deadband == null) {
			if (other.deadband != null)
				return false;
		} else if (!deadband.equals(other.deadband))
			return false;
		if (highLimit == null) {
			if (other.highLimit != null)
				return false;
		} else if (!highLimit.equals(other.highLimit))
			return false;
		if (lowLimit == null) {
			if (other.lowLimit != null)
				return false;
		} else if (!lowLimit.equals(other.lowLimit))
			return false;
		if (timeDelay == null) {
			if (other.timeDelay != null)
				return false;
		} else if (!timeDelay.equals(other.timeDelay))
			return false;
		return true;
	}
}
