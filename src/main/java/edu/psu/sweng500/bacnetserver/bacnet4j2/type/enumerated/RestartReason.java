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
package edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated;

import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.Enumerated;
import com.serotonin.util.queue.ByteQueue;

public class RestartReason extends Enumerated {
	private static final long serialVersionUID = -4199348259202899844L;
	public static final RestartReason unknown = new RestartReason(0);
	public static final RestartReason coldstart = new RestartReason(1);
	public static final RestartReason warmstart = new RestartReason(2);
	public static final RestartReason detectedPowerLost = new RestartReason(3);
	public static final RestartReason detectedPoweredOff = new RestartReason(4);
	public static final RestartReason hardwareWatchdog = new RestartReason(5);
	public static final RestartReason softwareWatchdog = new RestartReason(6);
	public static final RestartReason suspended = new RestartReason(7);

	public static final RestartReason[] ALL = { unknown, coldstart, warmstart, detectedPowerLost, detectedPoweredOff,
			hardwareWatchdog, softwareWatchdog, suspended, };

	public RestartReason(int value) {
		super(value);
	}

	public RestartReason(ByteQueue queue) {
		super(queue);
	}
}
