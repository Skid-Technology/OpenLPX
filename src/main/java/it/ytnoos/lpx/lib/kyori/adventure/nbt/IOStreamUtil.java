package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class IOStreamUtil {

    private IOStreamUtil() {}

    static InputStream closeShield(final InputStream inputStream) {
        return new InputStream() {

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public int read(byte[] values) throws IOException {
                return inputStream.read(values);
            }

            @Override
            public int read(byte[] values, int value, int value2) throws IOException {
                return inputStream.read(values, value, value2);
            }
        };
    }

    static OutputStream closeShield(final OutputStream outputStream) {
        return new OutputStream() {

            @Override
            public void write(int result) throws IOException {
                outputStream.write(result);
            }

            @Override
            public void write(byte[] result) throws IOException {
                outputStream.write(result);
            }

            @Override
            public void write(byte[] result, int value, int value2) throws IOException {
                outputStream.write(result, value, value2);
            }
        };
    }
}
