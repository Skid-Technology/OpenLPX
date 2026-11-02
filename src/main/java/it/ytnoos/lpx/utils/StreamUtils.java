package it.ytnoos.lpx.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {

    private StreamUtils() {}

    public static void handleLimits(OutputStream outputStream, byte[] values) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(values.length);
        dataOutputStream.write(values);
        dataOutputStream.flush();
    }

    public static byte[] readByteArray(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte[] values = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(values);
        return values;
    }
}
