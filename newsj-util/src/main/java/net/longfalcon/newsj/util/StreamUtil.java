package net.longfalcon.newsj.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: Sten Martinez
 * Date: 10/15/15
 * Time: 10:20 PM
 */
public class StreamUtil {
    public static void transferByteArray(InputStream inputStream, OutputStream outputStream, int bufferSize) throws IOException {
        byte[] bytes = new byte[bufferSize];

        int value = -1;

        while ((value = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, value);
        }
    }
}
