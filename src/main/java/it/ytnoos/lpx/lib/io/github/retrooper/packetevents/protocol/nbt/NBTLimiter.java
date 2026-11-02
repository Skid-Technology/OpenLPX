package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@NonExtendable
public interface NBTLimiter {

    int DEFAULT_MAX_SIZE = Integer.getInteger("packetevents.nbt.default-max-size", 2097152);

    int DEFAULT_MAX_DEPTH = Integer.getInteger("packetevents.nbt.default-max-depth", 512);

    static NBTLimiter noop() {
        return new NBTLimiter() {

            @Override
            public void increment(int value) {}

            @Override
            public void checkReadability(int value) {}

            @Override
            public void enterDepth() {}

            @Override
            public void exitDepth() {}

            @Override
            public void countElements(int value) {}
        };
    }

    static NBTLimiter forBuffer(Object result) {
        return forBuffer(result, DEFAULT_MAX_SIZE);
    }

    static NBTLimiter forBuffer(Object result, int value) {
        return forBuffer(result, value, DEFAULT_MAX_DEPTH);
    }

    static NBTLimiter forBuffer(Object result, int value, int value2) {
        return forBuffer(result, value, value2, null);
    }

    static NBTLimiter forBuffer(
            final Object result,
            final int value,
            final int value2,
            @Nullable final NBTElementCounter nBTElementCounter) {
        return new NBTLimiter() {

            private int bytes;

            private int depth;

            @Override
            public void increment(int bytes) {
                if (bytes < 0) {
                    throw new IllegalArgumentException(
                            "Can't increment NBT limiter by negative amount: " + bytes);
                }
                if (this.bytes + bytes > value) {
                    throw new PacketLimitException(
                            PacketLimitException.Limit.NBT_SIZE, (long) this.bytes + bytes, value);
                }
                this.bytes += bytes;
            }

            @Override
            public void checkReadability(int value3) {
                int value4 = ByteBufHelper.readableBytes(result);
                if (value3 > value4) {
                    throw new IllegalArgumentException(
                            "Can't read more than possible: " + value3 + " > " + value4);
                }
            }

            @Override
            public void enterDepth() {
                if (this.depth >= value2) {
                    throw new PacketLimitException(
                            PacketLimitException.Limit.NBT_DEPTH, this.depth + 1L, value2);
                }
                this.depth++;
            }

            @Override
            public void exitDepth() {
                if (this.depth <= 0) {
                    throw new IllegalArgumentException("Can't exit top-level depth");
                }
                this.depth--;
            }

            @Override
            public void countElements(int value3) {
                if (nBTElementCounter != null) {
                    nBTElementCounter.add(value3);
                }
            }
        };
    }

    void increment(int value);

    void checkReadability(int value);

    void enterDepth();

    void exitDepth();

    void countElements(int value);
}
