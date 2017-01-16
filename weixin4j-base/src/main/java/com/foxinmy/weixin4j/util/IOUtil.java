package com.foxinmy.weixin4j.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * IOUtil
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className IOUtil
 * @date 2014年9月22日
 * @see org.apache.commons.io.IOUtils
 * @since JDK 1.6
 */
public class IOUtil {

    private static final int EOF                 = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static byte[] toByteArray(Reader input) throws IOException {
        return toByteArray(input, Charset.defaultCharset());
    }

    public static byte[] toByteArray(Reader input, Charset encoding) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output, encoding);
        return output.toByteArray();
    }

    public static void copy(Reader input, OutputStream output, Charset encoding) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output, encoding);
        copyLarge(input, out, new char[DEFAULT_BUFFER_SIZE]);
        out.flush();
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    private static long copyLarge(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
        return output.toByteArray();
    }

    private static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    private static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static void close(Closeable stream) {
        try {
            if(stream == null){
                return ;
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
