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

package com.foxinmy.weixin4j.http;

import com.foxinmy.weixin4j.util.CharArrayBuffer;
import com.foxinmy.weixin4j.util.NameValue;

/**
 * Basic implementation for formatting header value elements.
 * Instances of this class are stateless and thread-safe.
 * Derived classes are expected to maintain these properties.
 *
 * @since 4.0
 */
public class HeaderValueFormatter {

    public final static HeaderValueFormatter INSTANCE = new HeaderValueFormatter();

    /**
     * Special characters that can be used as separators in HTTP parameters.
     * These special characters MUST be in a quoted string to be used within
     * a parameter value .
     */
    public final static String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";

    /**
     * Unsafe special characters that must be escaped using the backslash
     * character
     */
    public final static String UNSAFE_CHARS = "\"\\";

    public HeaderValueFormatter() {
        super();
    }

    /**
     * Formats a set of parameters.
     *
     * @param nvps      the parameters to format
     * @param quote     <code>true</code> to always format with quoted values,
     *                  <code>false</code> to use quotes only when necessary
     * @param formatter         the formatter to use, or <code>null</code>
     *                          for the {@link #INSTANCE default}
     *
     * @return  the formatted parameters
     */
    public static
        String formatParameters(final NameValue[] nvps,
                                final boolean quote,
                                final HeaderValueFormatter formatter) {
        return (formatter != null ? formatter : HeaderValueFormatter.INSTANCE)
                .formatParameters(null, nvps, quote).toString();
    }


    // non-javadoc, see interface HeaderValueFormatter
    public CharArrayBuffer formatParameters(final CharArrayBuffer charBuffer,
                                            final NameValue[] nvps,
                                            final boolean quote) {
        final int len = estimateParametersLen(nvps);
        CharArrayBuffer buffer = charBuffer;
        if (buffer == null) {
            buffer = new CharArrayBuffer(len);
        } else {
            buffer.ensureCapacity(len);
        }

        for (int i = 0; i < nvps.length; i++) {
            if (i > 0) {
                buffer.append("; ");
            }
            formatNameValuePair(buffer, nvps[i], quote);
        }

        return buffer;
    }


    /**
     * Estimates the length of formatted parameters.
     *
     * @param nvps      the parameters to format, or <code>null</code>
     *
     * @return  a length estimate, in number of characters
     */
    protected int estimateParametersLen(final NameValue[] nvps) {
        if ((nvps == null) || (nvps.length < 1)) {
            return 0;
        }

        int result = (nvps.length-1) * 2; // "; " between the parameters
        for (final NameValue nvp : nvps) {
            result += estimateNameValuePairLen(nvp);
        }

        return result;
    }


    /**
     * Formats a name-value pair.
     *
     * @param nvp       the name-value pair to format
     * @param quote     <code>true</code> to always format with a quoted value,
     *                  <code>false</code> to use quotes only when necessary
     * @param formatter         the formatter to use, or <code>null</code>
     *                          for the {@link #INSTANCE default}
     *
     * @return  the formatted name-value pair
     */
    public static
        String formatNameValuePair(final NameValue nvp,
                                   final boolean quote,
                                   final HeaderValueFormatter formatter) {
        return (formatter != null ? formatter : HeaderValueFormatter.INSTANCE)
                .formatNameValuePair(null, nvp, quote).toString();
    }


    // non-javadoc, see interface HeaderValueFormatter
    public CharArrayBuffer formatNameValuePair(final CharArrayBuffer charBuffer,
                                               final NameValue nvp,
                                               final boolean quote) {
        final int len = estimateNameValuePairLen(nvp);
        CharArrayBuffer buffer = charBuffer;
        if (buffer == null) {
            buffer = new CharArrayBuffer(len);
        } else {
            buffer.ensureCapacity(len);
        }

        buffer.append(nvp.getName());
        final String value = nvp.getValue();
        if (value != null) {
            buffer.append('=');
            doFormatValue(buffer, value, quote);
        }

        return buffer;
    }


    /**
     * Estimates the length of a formatted name-value pair.
     *
     * @param nvp       the name-value pair to format, or <code>null</code>
     *
     * @return  a length estimate, in number of characters
     */
    protected int estimateNameValuePairLen(final NameValue nvp) {
        if (nvp == null) {
            return 0;
        }

        int result = nvp.getName().length(); // name
        final String value = nvp.getValue();
        if (value != null) {
            // assume quotes, but no escaped characters
            result += 3 + value.length(); // ="value"
        }
        return result;
    }


    /**
     * Actually formats the value of a name-value pair.
     * This does not include a leading = character.
     * Called from {@link #formatNameValuePair formatNameValuePair}.
     *
     * @param buffer    the buffer to append to, never <code>null</code>
     * @param value     the value to append, never <code>null</code>
     * @param quote     <code>true</code> to always format with quotes,
     *                  <code>false</code> to use quotes only when necessary
     */
    protected void doFormatValue(final CharArrayBuffer buffer,
                                 final String value,
                                 final boolean quote) {

        boolean quoteFlag = quote;
        if (!quoteFlag) {
            for (int i = 0; (i < value.length()) && !quoteFlag; i++) {
                quoteFlag = isSeparator(value.charAt(i));
            }
        }

        if (quoteFlag) {
            buffer.append('"');
        }
        for (int i = 0; i < value.length(); i++) {
            final char ch = value.charAt(i);
            if (isUnsafe(ch)) {
                buffer.append('\\');
            }
            buffer.append(ch);
        }
        if (quoteFlag) {
            buffer.append('"');
        }
    }


    /**
     * Checks whether a character is a {@link #SEPARATORS separator}.
     *
     * @param ch        the character to check
     *
     * @return  <code>true</code> if the character is a separator,
     *          <code>false</code> otherwise
     */
    protected boolean isSeparator(final char ch) {
        return SEPARATORS.indexOf(ch) >= 0;
    }


    /**
     * Checks whether a character is {@link #UNSAFE_CHARS unsafe}.
     *
     * @param ch        the character to check
     *
     * @return  <code>true</code> if the character is unsafe,
     *          <code>false</code> otherwise
     */
    protected boolean isUnsafe(final char ch) {
        return UNSAFE_CHARS.indexOf(ch) >= 0;
    }


} // class BasicHeaderValueFormatter
