/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.foxinmy.weixin4j.http.apache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Random;

import com.foxinmy.weixin4j.http.ContentType;
import com.foxinmy.weixin4j.http.entity.HttpEntity;

/**
 * Multipart/form coded HTTP entity consisting of multiple body parts.
 *
 * @since 4.0
 */
public class MultipartEntity implements HttpEntity {

	/**
	 * The pool of ASCII chars to be used for generating a multipart boundary.
	 */
	private final static char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	private final HttpMultipart multipart;

	// @GuardedBy("dirty") // we always read dirty before accessing length
	private long length;
	private volatile boolean dirty; // used to decide whether to recalculate
									// length

	/**
	 * Creates an instance using the specified parameters
	 * 
	 * @param mode
	 *            the mode to use, may be {@code null}, in which case
	 *            {@link HttpMultipartMode#STRICT} is used
	 * @param boundary
	 *            the boundary string, may be {@code null}, in which case
	 *            {@link #generateBoundary()} is invoked to create the string
	 * @param charset
	 *            the character set to use, may be {@code null}, in which case
	 *            {@link MIME#DEFAULT_CHARSET} - i.e. US-ASCII - is used.
	 */
	public MultipartEntity(HttpMultipartMode mode, String boundary,
			Charset charset) {
		super();
		if (boundary == null) {
			boundary = generateBoundary();
		}
		if (mode == null) {
			mode = HttpMultipartMode.STRICT;
		}
		this.multipart = new HttpMultipart("form-data", charset, boundary, mode);
		this.dirty = true;
	}

	/**
	 * Creates an instance using the specified {@link HttpMultipartMode} mode.
	 * Boundary and charset are set to {@code null}.
	 * 
	 * @param mode
	 *            the desired mode
	 */
	public MultipartEntity(final HttpMultipartMode mode) {
		this(mode, null, null);
	}

	/**
	 * Creates an instance using mode {@link HttpMultipartMode#STRICT}
	 */
	public MultipartEntity() {
		this(HttpMultipartMode.STRICT, null, null);
	}

	protected String generateBoundary() {
		StringBuilder buffer = new StringBuilder();
		Random rand = new Random();
		int count = rand.nextInt(11) + 30; // a random size from 30 to 40
		for (int i = 0; i < count; i++) {
			buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
		}
		return buffer.toString();
	}

	public void addPart(final FormBodyPart bodyPart) {
		this.multipart.addBodyPart(bodyPart);
		this.dirty = true;
	}

	public void addPart(final String name, final ContentBody contentBody) {
		addPart(new FormBodyPart(name, contentBody));
	}

	public boolean isRepeatable() {
		for (FormBodyPart part : this.multipart.getBodyParts()) {
			ContentBody body = part.getBody();
			if (body.getContentLength() < 0) {
				return false;
			}
		}
		return true;
	}

	public boolean isChunked() {
		return !isRepeatable();
	}

	public boolean isStreaming() {
		return !isRepeatable();
	}

	@Override
	public long getContentLength() {
		if (this.dirty) {
			this.length = this.multipart.getTotalLength();
			this.dirty = false;
		}
		return this.length;
	}

	@Override
	public ContentType getContentType() {
		return ContentType.MULTIPART_FORM_DATA;
	}

	public void consumeContent() throws IOException,
			UnsupportedOperationException {
		if (isStreaming()) {
			throw new UnsupportedOperationException(
					"Streaming entity does not implement #consumeContent()");
		}
	}

	@Override
	public InputStream getContent() throws IOException,
			UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"Multipart form entity does not implement #getContent()");
	}

	@Override
	public void writeTo(final OutputStream outstream) throws IOException {
		this.multipart.writeTo(outstream);
		outstream.flush();
	}
}
