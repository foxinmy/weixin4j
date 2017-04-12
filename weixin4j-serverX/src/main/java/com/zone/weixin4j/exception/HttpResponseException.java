package com.zone.weixin4j.exception;

/**
 * Created by Yz on 2017/3/15.
 * HttpResponseException  --  异常类
 */
public class HttpResponseException extends Exception {

    private static final long serialVersionUID = 1932809035537660697L;

    private HttpResponseStatus httpResponseStatus;

    public HttpResponseException(HttpResponseStatus httpResponseStatus) {
        this.httpResponseStatus = httpResponseStatus;
    }

    public HttpResponseStatus getHttpResponseStatus() {
        return httpResponseStatus;
    }

    public void setHttpResponseStatus(HttpResponseStatus httpResponseStatus) {
        this.httpResponseStatus = httpResponseStatus;
    }

    public static class HttpResponseStatus {

        /**
         * 100 Continue
         */
        public static final HttpResponseStatus CONTINUE = newStatus(100, "Continue");

        /**
         * 101 Switching Protocols
         */
        public static final HttpResponseStatus SWITCHING_PROTOCOLS = newStatus(101, "Switching Protocols");

        /**
         * 102 Processing (WebDAV, RFC2518)
         */
        public static final HttpResponseStatus PROCESSING = newStatus(102, "Processing");

        /**
         * 200 OK
         */
        public static final HttpResponseStatus OK = newStatus(200, "OK");

        /**
         * 201 Created
         */
        public static final HttpResponseStatus CREATED = newStatus(201, "Created");

        /**
         * 202 Accepted
         */
        public static final HttpResponseStatus ACCEPTED = newStatus(202, "Accepted");

        /**
         * 203 Non-Authoritative Information (since HTTP/1.1)
         */
        public static final HttpResponseStatus NON_AUTHORITATIVE_INFORMATION =
                newStatus(203, "Non-Authoritative Information");

        /**
         * 204 No Content
         */
        public static final HttpResponseStatus NO_CONTENT = newStatus(204, "No Content");

        /**
         * 205 Reset Content
         */
        public static final HttpResponseStatus RESET_CONTENT = newStatus(205, "Reset Content");

        /**
         * 206 Partial Content
         */
        public static final HttpResponseStatus PARTIAL_CONTENT = newStatus(206, "Partial Content");

        /**
         * 207 Multi-Status (WebDAV, RFC2518)
         */
        public static final HttpResponseStatus MULTI_STATUS = newStatus(207, "Multi-Status");

        /**
         * 300 Multiple Choices
         */
        public static final HttpResponseStatus MULTIPLE_CHOICES = newStatus(300, "Multiple Choices");

        /**
         * 301 Moved Permanently
         */
        public static final HttpResponseStatus MOVED_PERMANENTLY = newStatus(301, "Moved Permanently");

        /**
         * 302 Found
         */
        public static final HttpResponseStatus FOUND = newStatus(302, "Found");

        /**
         * 303 See Other (since HTTP/1.1)
         */
        public static final HttpResponseStatus SEE_OTHER = newStatus(303, "See Other");

        /**
         * 304 Not Modified
         */
        public static final HttpResponseStatus NOT_MODIFIED = newStatus(304, "Not Modified");

        /**
         * 305 Use Proxy (since HTTP/1.1)
         */
        public static final HttpResponseStatus USE_PROXY = newStatus(305, "Use Proxy");

        /**
         * 307 Temporary Redirect (since HTTP/1.1)
         */
        public static final HttpResponseStatus TEMPORARY_REDIRECT = newStatus(307, "Temporary Redirect");

        /**
         * 400 Bad Request
         */
        public static final HttpResponseStatus BAD_REQUEST = newStatus(400, "Bad Request");

        /**
         * 401 Unauthorized
         */
        public static final HttpResponseStatus UNAUTHORIZED = newStatus(401, "Unauthorized");

        /**
         * 402 Payment Required
         */
        public static final HttpResponseStatus PAYMENT_REQUIRED = newStatus(402, "Payment Required");

        /**
         * 403 Forbidden
         */
        public static final HttpResponseStatus FORBIDDEN = newStatus(403, "Forbidden");

        /**
         * 404 Not Found
         */
        public static final HttpResponseStatus NOT_FOUND = newStatus(404, "Not Found");

        /**
         * 405 Method Not Allowed
         */
        public static final HttpResponseStatus METHOD_NOT_ALLOWED = newStatus(405, "Method Not Allowed");

        /**
         * 406 Not Acceptable
         */
        public static final HttpResponseStatus NOT_ACCEPTABLE = newStatus(406, "Not Acceptable");

        /**
         * 407 Proxy Authentication Required
         */
        public static final HttpResponseStatus PROXY_AUTHENTICATION_REQUIRED =
                newStatus(407, "Proxy Authentication Required");

        /**
         * 408 Request Timeout
         */
        public static final HttpResponseStatus REQUEST_TIMEOUT = newStatus(408, "Request Timeout");

        /**
         * 409 Conflict
         */
        public static final HttpResponseStatus CONFLICT = newStatus(409, "Conflict");

        /**
         * 410 Gone
         */
        public static final HttpResponseStatus GONE = newStatus(410, "Gone");

        /**
         * 411 Length Required
         */
        public static final HttpResponseStatus LENGTH_REQUIRED = newStatus(411, "Length Required");

        /**
         * 412 Precondition Failed
         */
        public static final HttpResponseStatus PRECONDITION_FAILED = newStatus(412, "Precondition Failed");

        /**
         * 413 Request Entity Too Large
         */
        public static final HttpResponseStatus REQUEST_ENTITY_TOO_LARGE =
                newStatus(413, "Request Entity Too Large");

        /**
         * 414 Request-URI Too Long
         */
        public static final HttpResponseStatus REQUEST_URI_TOO_LONG = newStatus(414, "Request-URI Too Long");

        /**
         * 415 Unsupported Media Type
         */
        public static final HttpResponseStatus UNSUPPORTED_MEDIA_TYPE = newStatus(415, "Unsupported Media Type");

        /**
         * 416 Requested Range Not Satisfiable
         */
        public static final HttpResponseStatus REQUESTED_RANGE_NOT_SATISFIABLE =
                newStatus(416, "Requested Range Not Satisfiable");

        /**
         * 417 Expectation Failed
         */
        public static final HttpResponseStatus EXPECTATION_FAILED = newStatus(417, "Expectation Failed");

        /**
         * 421 Misdirected Request
         * <p>
         * <a href="https://tools.ietf.org/html/draft-ietf-httpbis-http2-15#section-9.1.2">421 Status Code</a>
         */
        public static final HttpResponseStatus MISDIRECTED_REQUEST = newStatus(421, "Misdirected Request");

        /**
         * 422 Unprocessable Entity (WebDAV, RFC4918)
         */
        public static final HttpResponseStatus UNPROCESSABLE_ENTITY = newStatus(422, "Unprocessable Entity");

        /**
         * 423 Locked (WebDAV, RFC4918)
         */
        public static final HttpResponseStatus LOCKED = newStatus(423, "Locked");

        /**
         * 424 Failed Dependency (WebDAV, RFC4918)
         */
        public static final HttpResponseStatus FAILED_DEPENDENCY = newStatus(424, "Failed Dependency");

        /**
         * 425 Unordered Collection (WebDAV, RFC3648)
         */
        public static final HttpResponseStatus UNORDERED_COLLECTION = newStatus(425, "Unordered Collection");

        /**
         * 426 Upgrade Required (RFC2817)
         */
        public static final HttpResponseStatus UPGRADE_REQUIRED = newStatus(426, "Upgrade Required");

        /**
         * 428 Precondition Required (RFC6585)
         */
        public static final HttpResponseStatus PRECONDITION_REQUIRED = newStatus(428, "Precondition Required");

        /**
         * 429 Too Many Requests (RFC6585)
         */
        public static final HttpResponseStatus TOO_MANY_REQUESTS = newStatus(429, "Too Many Requests");

        /**
         * 431 Request Header Fields Too Large (RFC6585)
         */
        public static final HttpResponseStatus REQUEST_HEADER_FIELDS_TOO_LARGE =
                newStatus(431, "Request Header Fields Too Large");

        /**
         * 500 Internal Server Error
         */
        public static final HttpResponseStatus INTERNAL_SERVER_ERROR = newStatus(500, "Internal Server Error");

        /**
         * 501 Not Implemented
         */
        public static final HttpResponseStatus NOT_IMPLEMENTED = newStatus(501, "Not Implemented");

        /**
         * 502 Bad Gateway
         */
        public static final HttpResponseStatus BAD_GATEWAY = newStatus(502, "Bad Gateway");

        /**
         * 503 Service Unavailable
         */
        public static final HttpResponseStatus SERVICE_UNAVAILABLE = newStatus(503, "Service Unavailable");

        /**
         * 504 Gateway Timeout
         */
        public static final HttpResponseStatus GATEWAY_TIMEOUT = newStatus(504, "Gateway Timeout");

        /**
         * 505 HTTP Version Not Supported
         */
        public static final HttpResponseStatus HTTP_VERSION_NOT_SUPPORTED =
                newStatus(505, "HTTP Version Not Supported");

        /**
         * 506 Variant Also Negotiates (RFC2295)
         */
        public static final HttpResponseStatus VARIANT_ALSO_NEGOTIATES = newStatus(506, "Variant Also Negotiates");

        /**
         * 507 Insufficient Storage (WebDAV, RFC4918)
         */
        public static final HttpResponseStatus INSUFFICIENT_STORAGE = newStatus(507, "Insufficient Storage");

        /**
         * 510 Not Extended (RFC2774)
         */
        public static final HttpResponseStatus NOT_EXTENDED = newStatus(510, "Not Extended");

        /**
         * 511 Network Authentication Required (RFC6585)
         */
        public static final HttpResponseStatus NETWORK_AUTHENTICATION_REQUIRED =
                newStatus(511, "Network Authentication Required");

        private static HttpResponseStatus newStatus(int statusCode, String reasonPhrase) {
            return new HttpResponseStatus(statusCode, reasonPhrase, true);
        }

        private final int code;

        private final String reasonPhrase;

        public int getCode() {
            return code;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }

        /**
         * Creates a new instance with the specified {@code code} and its {@code reasonPhrase}.
         */

        private HttpResponseStatus(int code, String reasonPhrase, boolean bytes) {
            if (code < 0) {
                throw new IllegalArgumentException(
                        "code: " + code + " (expected: 0+)");
            }

            if (reasonPhrase == null) {
                throw new NullPointerException("reasonPhrase");
            }

            for (int i = 0; i < reasonPhrase.length(); i++) {
                char c = reasonPhrase.charAt(i);
                // Check prohibited characters.
                switch (c) {
                    case '\n':
                    case '\r':
                        throw new IllegalArgumentException(
                                "reasonPhrase contains one of the following prohibited characters: " +
                                        "\\r\\n: " + reasonPhrase);
                }
            }

            this.code = code;
            this.reasonPhrase = reasonPhrase;
        }
    }


}
