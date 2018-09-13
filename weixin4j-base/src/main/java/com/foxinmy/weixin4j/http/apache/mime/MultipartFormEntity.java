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

package com.foxinmy.weixin4j.http.apache.mime;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.foxinmy.weixin4j.http.ContentType;
import com.foxinmy.weixin4j.http.entity.HttpEntity;

class MultipartFormEntity implements HttpEntity {

	private final AbstractMultipartForm multipart;
	private final ContentType contentType;
	private final long contentLength;

	MultipartFormEntity(final AbstractMultipartForm multipart,
			final ContentType contentType, final long contentLength) {
		super();
		this.multipart = multipart;
		this.contentType = contentType;
		this.contentLength = contentLength;
	}

	AbstractMultipartForm getMultipart() {
		return this.multipart;
	}

	public boolean isRepeatable() {
		return this.contentLength != -1;
	}

	public boolean isChunked() {
		return !isRepeatable();
	}

	public boolean isStreaming() {
		return !isRepeatable();
	}

	@Override
	public long getContentLength() {
		return this.contentLength;
	}

	public ContentType getContentType() {
		return this.contentType;
	}

	@Override
	public InputStream getContent() throws IOException {
		if (this.contentLength < 0) {
			throw new IllegalArgumentException("Content length is unknown");
		} else if (this.contentLength > 5 * 1024 * 1024) {
			throw new IllegalArgumentException("Content length is too long: "
					+ this.contentLength);
		}
		final ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		writeTo(outstream);
		outstream.flush();
		return new ByteArrayInputStream(outstream.toByteArray());
	}

	@Override
	public void writeTo(final OutputStream outstream) throws IOException {
		this.multipart.writeTo(outstream);
	}
}
