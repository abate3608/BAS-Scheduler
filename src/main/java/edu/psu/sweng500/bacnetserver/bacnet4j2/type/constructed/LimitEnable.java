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
package bacnet4j2.type.constructed;

import bacnet4j2.type.primitive.BitString;
import com.serotonin.util.queue.ByteQueue;

public class LimitEnable extends BitString {
    private static final long serialVersionUID = -8754983228968085042L;

    public LimitEnable(boolean lowLimitEnable, boolean highLimitEnable) {
        super(new boolean[] { lowLimitEnable, highLimitEnable });
    }

    public LimitEnable(ByteQueue queue) {
        super(queue);
    }

    public boolean isLowLimitEnable() {
        return getValue()[0];
    }

    public boolean isHighLimitEnable() {
        return getValue()[1];
    }
}
