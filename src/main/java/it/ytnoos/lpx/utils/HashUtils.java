package it.ytnoos.lpx.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private HashUtils() {}

    public static String decodeString(byte[] values) {
        return new String(values);
    }

    public static byte[] sha256OrNull(byte[] values) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(values);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] merge(byte[] values, byte[] values2) {
        byte[] values3 = new byte[values.length + values2.length];
        System.arraycopy(values, 0, values3, 0, values.length);
        System.arraycopy(values2, 0, values3, values.length, values2.length);
        return values3;
    }

    public static byte[] sha256(byte[] values) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        instance.update(values);
        return instance.digest();
    }

    public static byte[] sha256(InputStream inputStream)
            throws IOException, NoSuchAlgorithmException {
        byte[] values = readAllBytes(inputStream);
        return sha256(values);
    }

    public static byte[] sha256(URL uRL) throws IOException, NoSuchAlgorithmException {
        InputStream inputStream = uRL.openStream();
        return sha256(inputStream);
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] values = new byte[16384];
        while (true) {
            int value = inputStream.read(values, 0, values.length);
            if (value == -1) {
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(values, 0, value);
        }
    }
}
