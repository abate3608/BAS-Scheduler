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
package edu.psu.sweng500.bacnetserver.bacnet4j2.obj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;

import edu.psu.sweng500.bacnetserver.bacnet4j2.LocalDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetRuntimeException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetServiceException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.DateTime;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.FileAccessMethod;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.PropertyIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.Boolean;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.ObjectIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.OctetString;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.UnsignedInteger;
import com.serotonin.io.StreamUtils;

/**
 * @author Matthew Lohbihler
 */
public class FileObject extends BACnetObject {
	private static final long serialVersionUID = 1089963077847602732L;

	/**
	 * The actual file that this object represents.
	 */
	private final File file;

	public FileObject(LocalDevice localDevice, ObjectIdentifier oid, File file, FileAccessMethod fileAccessMethod) {
		super(localDevice, oid);
		this.file = file;

		if (file.isDirectory())
			throw new BACnetRuntimeException("File is a directory");

		updateProperties();

		try {
			setProperty(PropertyIdentifier.fileAccessMethod, fileAccessMethod);
		} catch (BACnetServiceException e) {
			// Should never happen, but wrap in an unchecked just in case.
			throw new BACnetRuntimeException(e);
		}
	}

	public void updateProperties() {
		try {
			// TODO this is only a snapshot. Property read methods need to be
			// overridden to report real time values.
			setProperty(PropertyIdentifier.fileSize, new UnsignedInteger(new BigInteger(Long.toString(length()))));
			setProperty(PropertyIdentifier.modificationDate, new DateTime(file.lastModified()));
			setProperty(PropertyIdentifier.readOnly, new Boolean(!file.canWrite()));
		} catch (BACnetServiceException e) {
			// Should never happen, but wrap in an unchecked just in case.
			throw new BACnetRuntimeException(e);
		}
	}

	public long length() {
		return file.length();
	}

	public OctetString readData(long start, long length) throws IOException {
		FileInputStream in = new FileInputStream(file);
		try {
			while (start > 0) {
				long result = in.skip(start);
				if (result == -1)
					// EOF
					break;
				start -= result;
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			StreamUtils.transfer(in, out, length);
			return new OctetString(out.toByteArray());
		} finally {
			in.close();
		}
	}

	public void writeData(long start, OctetString fileData) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		try {
			byte data[] = fileData.getBytes();
			long newLength = start + data.length;
			raf.seek(start);
			raf.write(data);
			raf.setLength(newLength);
		} finally {
			raf.close();
		}

		updateProperties();
	}

	//
	// public SequenceOf<OctetString> readRecords(int start, int length) throws
	// IOException {
	//
	// }
}
