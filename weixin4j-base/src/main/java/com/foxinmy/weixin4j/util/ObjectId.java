package com.foxinmy.weixin4j.util;

/*
 * Copyright (c) 2008-2014 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A globally unique identifier for objects.
 * </p>
 * 
 * <p>
 * Consists of 12 bytes, divided as follows:
 * </p>
 * <table border="1">
 * <caption>ObjectID layout</caption>
 * <tr>
 * <td>0</td>
 * <td>1</td>
 * <td>2</td>
 * <td>3</td>
 * <td>4</td>
 * <td>5</td>
 * <td>6</td>
 * <td>7</td>
 * <td>8</td>
 * <td>9</td>
 * <td>10</td>
 * <td>11</td>
 * </tr>
 * <tr>
 * <td colspan="4">time</td>
 * <td colspan="3">machine</td>
 * <td colspan="2">pid</td>
 * <td colspan="3">inc</td>
 * </tr>
 * </table>
 * 
 * <p>
 * Instances of this class are immutable.
 * </p>
 * 
 * @mongodb.driver.manual core/object-id ObjectId
 */
public class ObjectId implements Comparable<ObjectId>, java.io.Serializable {

	private static final long serialVersionUID = -4415279469780082174L;

	static final Logger LOGGER = Logger.getLogger("org.bson.ObjectId");

	/**
	 * Gets a new object id.
	 * 
	 * @return the new id
	 */
	public static ObjectId get() {
		return new ObjectId();
	}

	/**
	 * Checks if a string could be an {@code ObjectId}.
	 * 
	 * @param s
	 *            a potential ObjectId as a String.
	 * @return whether the string could be an object id
	 * @throws IllegalArgumentException
	 *             if hexString is null
	 */
	public static boolean isValid(String s) {
		if (s == null)
			return false;

		final int len = s.length();
		if (len != 24)
			return false;

		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9')
				continue;
			if (c >= 'a' && c <= 'f')
				continue;
			if (c >= 'A' && c <= 'F')
				continue;

			return false;
		}

		return true;
	}

	/**
	 * Constructs an ObjectId given its 12-byte binary representation.
	 * 
	 * @param b
	 *            a byte array of length 12
	 */
	public ObjectId(byte[] b) {
		if (b.length != 12)
			throw new IllegalArgumentException("need 12 bytes");
		ByteBuffer bb = ByteBuffer.wrap(b);
		_time = bb.getInt();
		_machine = bb.getInt();
		_inc = bb.getInt();
		_new = false;
	}

	/**
	 * Create a new object id.
	 */
	public ObjectId() {
		_time = (int) (System.currentTimeMillis() / 1000);
		_machine = _genmachine;
		_inc = _nextInc.getAndIncrement();
		_new = true;
	}

	@Override
	public int hashCode() {
		int x = _time;
		x += (_machine * 111);
		x += (_inc * 17);
		return x;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ObjectId)) {
			return false;
		}
		ObjectId other = (ObjectId) o;
		return _time == other._time && _machine == other._machine
				&& _inc == other._inc;
	}

	/**
	 * Converts this instance into a 24-byte hexadecimal string representation.
	 * 
	 * @return a string representation of the ObjectId in hexadecimal format
	 */
	public String toHexString() {
		final StringBuilder buf = new StringBuilder(24);

		for (final byte b : toByteArray()) {
			buf.append(String.format("%02x", b & 0xff));
		}

		return buf.toString();
	}

	/**
	 * Convert to a byte array. Note that the numbers are stored in big-endian
	 * order.
	 * 
	 * @return the byte array
	 */
	public byte[] toByteArray() {
		byte b[] = new byte[12];
		ByteBuffer bb = ByteBuffer.wrap(b);
		// by default BB is big endian like we need
		bb.putInt(_time);
		bb.putInt(_machine);
		bb.putInt(_inc);
		return b;
	}

	static String _pos(String s, int p) {
		return s.substring(p * 2, (p * 2) + 2);
	}

	public String toString() {
		byte b[] = toByteArray();

		StringBuilder buf = new StringBuilder(24);

		for (int i = 0; i < b.length; i++) {
			int x = b[i] & 0xFF;
			String s = Integer.toHexString(x);
			if (s.length() == 1)
				buf.append("0");
			buf.append(s);
		}

		return buf.toString();
	}

	int _compareUnsigned(int i, int j) {
		long li = 0xFFFFFFFFL;
		li = i & li;
		long lj = 0xFFFFFFFFL;
		lj = j & lj;
		long diff = li - lj;
		if (diff < Integer.MIN_VALUE)
			return Integer.MIN_VALUE;
		if (diff > Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		return (int) diff;
	}

	public int compareTo(ObjectId id) {
		if (id == null)
			return -1;

		int x = _compareUnsigned(_time, id._time);
		if (x != 0)
			return x;

		x = _compareUnsigned(_machine, id._machine);
		if (x != 0)
			return x;

		return _compareUnsigned(_inc, id._inc);
	}

	/**
	 * Gets the timestamp (number of seconds since the Unix epoch).
	 * 
	 * @return the timestamp
	 */
	public int getTimestamp() {
		return _time;
	}

	/**
	 * Gets the timestamp as a {@code Date} instance.
	 * 
	 * @return the Date
	 */
	public Date getDate() {
		return new Date(_time * 1000L);
	}

	/**
	 * Gets the current value of the auto-incrementing counter.
	 * 
	 * @return the current counter value.
	 */
	public static int getCurrentCounter() {
		return _nextInc.get();
	}

	final int _time;
	final int _machine;
	final int _inc;

	boolean _new;

	private static AtomicInteger _nextInc = new AtomicInteger(
			(new java.util.Random()).nextInt());

	private static final int _genmachine;
	static {
		try {
			// build a 2-byte machine piece based on NICs info
			int machinePiece;
			{
				try {
					StringBuilder sb = new StringBuilder();
					Enumeration<NetworkInterface> e = NetworkInterface
							.getNetworkInterfaces();
					while (e.hasMoreElements()) {
						NetworkInterface ni = e.nextElement();
						sb.append(ni.toString());
					}
					machinePiece = sb.toString().hashCode() << 16;
				} catch (Throwable e) {
					// exception sometimes happens with IBM JVM, use random
					LOGGER.log(Level.WARNING, e.getMessage(), e);
					machinePiece = (new Random().nextInt()) << 16;
				}
				LOGGER.fine("machine piece post: "
						+ Integer.toHexString(machinePiece));
			}

			// add a 2 byte process piece. It must represent not only the JVM
			// but the class loader.
			// Since static var belong to class loader there could be collisions
			// otherwise
			final int processPiece;
			{
				int processId = new java.util.Random().nextInt();
				try {
					processId = java.lang.management.ManagementFactory
							.getRuntimeMXBean().getName().hashCode();
				} catch (Throwable t) {
				}

				ClassLoader loader = ObjectId.class.getClassLoader();
				int loaderId = loader != null ? System.identityHashCode(loader)
						: 0;

				StringBuilder sb = new StringBuilder();
				sb.append(Integer.toHexString(processId));
				sb.append(Integer.toHexString(loaderId));
				processPiece = sb.toString().hashCode() & 0xFFFF;
				LOGGER.fine("process piece: "
						+ Integer.toHexString(processPiece));
			}

			_genmachine = machinePiece | processPiece;
			LOGGER.fine("machine : " + Integer.toHexString(_genmachine));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
