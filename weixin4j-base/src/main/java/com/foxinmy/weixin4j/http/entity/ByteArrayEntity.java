package com.foxinmy.weixin4j.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.foxinmy.weixin4j.http.ContentType;

public class ByteArrayEntity implements HttpEntity {

	private final ContentType contentType;
	private final byte[] content;
	private final int off, len;

	public ByteArrayEntity(byte[] content) {
		this(content, ContentType.DEFAULT_BINARY);
	}

	public ByteArrayEntity(byte[] content, ContentType contentType) {
		this(content, 0, content.length, contentType);
	}

	public ByteArrayEntity(byte[] content, int off, int len,
			ContentType contentType) {
		this.content = content;
		this.off = off;
		this.len = len;
		this.contentType = contentType;
	}

	@Override
	public ContentType getContentType() {
		return contentType;
	}

	@Override
	public long getContentLength() {
		return len;
	}

	@Override
	public InputStream getContent() throws IOException {
		return new ByteArrayInputStream(this.content, this.off, this.len);
	}

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		outstream.write(this.content, this.off, this.len);
		outstream.flush();
	}
}
