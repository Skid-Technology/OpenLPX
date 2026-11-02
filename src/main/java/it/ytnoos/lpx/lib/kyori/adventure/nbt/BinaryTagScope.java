package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import java.io.IOException;

interface BinaryTagScope extends AutoCloseable {

    @Override
    void close() throws IOException;

    final class NoOp implements BinaryTagScope {

        static final BinaryTagScope.NoOp INSTANCE = new BinaryTagScope.NoOp();

        private NoOp() {}

        @Override
        public void close() {}
    }
}
