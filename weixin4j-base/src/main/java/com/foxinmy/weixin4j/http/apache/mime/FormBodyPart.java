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

import com.foxinmy.weixin4j.http.ContentType;
import com.foxinmy.weixin4j.http.apache.content.AbstractContentBody;
import com.foxinmy.weixin4j.http.apache.content.ContentBody;

/**
 * FormBodyPart class represents a content body that can be used as a part of multipart encoded
 * entities. This class automatically populates the header with standard fields based on
 * the content description of the enclosed body.
 *
 * @since 4.0
 */
public class FormBodyPart {

    private final String name;
    private final Header header;
    private final ContentBody body;

    FormBodyPart(final String name, final ContentBody body, final Header header) {
        super();
        this.name = name;
        this.body = body;
        this.header = header != null ? header : new Header();
    }

    /**
     *  (4.4) use {@link com.foxinmy.weixin4j.http.apache.mime.FormBodyPartBuilder}.
     */
    public FormBodyPart(final String name, final ContentBody body) {
        super();
        this.name = name;
        this.body = body;
        this.header = new Header();

        generateContentDisp(body);
        generateContentType(body);
        generateTransferEncoding(body);
    }

    public String getName() {
        return this.name;
    }

    public ContentBody getBody() {
        return this.body;
    }

    public Header getHeader() {
        return this.header;
    }

    public void addField(final String name, final String value) {
        this.header.addField(new MinimalField(name, value));
    }

    /**
     * (4.4) use {@link com.foxinmy.weixin4j.http.apache.mime.FormBodyPartBuilder}.
     */
    protected void generateContentDisp(final ContentBody body) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("form-data; name=\"");
        buffer.append(getName());
        buffer.append("\"");
        if (body.getFilename() != null) {
            buffer.append("; filename=\"");
            buffer.append(body.getFilename());
            buffer.append("\"");
        }
        addField(MIME.CONTENT_DISPOSITION, buffer.toString());
    }

    /**
     * (4.4) use {@link com.foxinmy.weixin4j.http.apache.mime.FormBodyPartBuilder}.
     */
    protected void generateContentType(final ContentBody body) {
        final ContentType contentType;
        if (body instanceof AbstractContentBody) {
            contentType = ((AbstractContentBody) body).getContentType();
        } else {
            contentType = null;
        }
        if (contentType != null) {
            addField(MIME.CONTENT_TYPE, contentType.toString());
        } else {
            final StringBuilder buffer = new StringBuilder();
            buffer.append(body.getMimeType()); // MimeType cannot be null
            if (body.getCharset() != null) { // charset may legitimately be null
                buffer.append("; charset=");
                buffer.append(body.getCharset());
            }
            addField(MIME.CONTENT_TYPE, buffer.toString());
        }
    }

    /**
     * (4.4) use {@link com.foxinmy.weixin4j.http.apache.mime.FormBodyPartBuilder}.
     */
    protected void generateTransferEncoding(final ContentBody body) {
        addField(MIME.CONTENT_TRANSFER_ENC, body.getTransferEncoding()); // TE cannot be null
    }
}
