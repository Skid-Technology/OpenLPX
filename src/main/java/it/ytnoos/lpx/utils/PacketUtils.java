package it.ytnoos.lpx.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByteArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLongArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTShort;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;

import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;

public class PacketUtils {

    private PacketUtils() {}

    public static int calculateLimit(NBTCompound nBTCompound, int value) {
        if (nBTCompound == null) {
            return 0;
        }
        int value2 = 0;
        for (Entry<String, NBT> entry : nBTCompound.getTags().entrySet()) {
            {
                value2 += ((String) entry.getKey()).getBytes(StandardCharsets.UTF_8).length;
            }
            Object value3 = entry.getValue();
            NBT nBT = (NBT) value3;
            Integer value4 = ((Integer) value);
            value2 += calculateLimit(nBT, value4);
        }
        return value2;
    }

    public static int calculateLimit(NBT nBT, int value) {
        int value2 = value;
        if (value2 >= 50) {
            RuntimeException runtimeException = new RuntimeException("NBT has too many nests");
            throw runtimeException;
        } else if (nBT instanceof NBTCompound nBTCompound2) {
            value2++;
            int value3 = value2;
            Integer value4 = ((Integer) value3);
            NBTCompound nBTCompound = nBTCompound2;
            return calculateLimit(nBTCompound, value4);
        } else if (!(nBT instanceof NBTList)) {
            if (nBT instanceof NBTByte) {
                return 1;
            }
            if (nBT instanceof NBTByteArray) {
                return ((NBTByteArray) nBT).getValue().length;
            }
            if (nBT instanceof NBTDouble) {
                return 8;
            }
            if (nBT instanceof NBTFloat) {
                return 4;
            }
            if (nBT instanceof NBTInt) {
                return 4;
            }
            if (nBT instanceof NBTIntArray) {
                return ((NBTIntArray) nBT).getValue().length * 4;
            }
            if (nBT instanceof NBTLong) {
                return 8;
            }
            if (nBT instanceof NBTLongArray) {
                return ((NBTLongArray) nBT).getValue().length * 8;
            }
            if (nBT instanceof NBTShort) {
                return 2;
            }
            if (nBT instanceof NBTString) {
                return ((NBTString) nBT).getValue().getBytes(StandardCharsets.UTF_8).length;
            }
            String simpleName = nBT.getClass().getSimpleName();
            RuntimeException runtimeException2 =
                    new RuntimeException((String) "Unknown NBT type " + simpleName);
            throw runtimeException2;
        } else {
            int value5 = 0;
            for (NBT nBT2 : ((Iterable<NBT>) ((Iterable<?>) ((NBTList) nBT).getTags()))) {
                int value6 = value2;
                Integer value7 = ((Integer) value6);
                value5 += calculateLimit(nBT2, value7);
            }
            return value5;
        }
    }

    public static int calculateLimit(ItemStack itemStack) {
        NBTCompound nBT = itemStack.getNBT();
        Integer value = Integer.valueOf((byte) 0);
        return calculateLimit(nBT, value);
    }

    public static boolean testText(NBTCompound nBTCompound, String text) {
        for (NBT nBT : nBTCompound.getTags().values()) {
            if (testText(nBT, text)) {
                return true;
            }
        }
        return false;
    }

    public static boolean testText(NBT nBT, String text) {
        if (nBT instanceof NBTString) {
            return ((NBTString) nBT).getValue().contains(text);
        } else if (nBT instanceof NBTCompound nBTCompound2) {
            NBTCompound nBTCompound = nBTCompound2;
            return testText(nBTCompound, text);
        } else {
            if (nBT instanceof NBTList) {
                for (NBT nBT2 : ((Iterable<NBT>) ((Iterable<?>) ((NBTList) nBT).getTags()))) {
                    if (testText(nBT2, text)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static void handleChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.channel().close();
        channelHandlerContext.channel().flush();
    }

    public static void handleChannel(Channel channel) {
        channel.close();
        channel.flush();
    }

    public static void handleBuffer(ByteBuf byteBuf) {
        byteBuf.readBytes(byteBuf.readableBytes());
    }

    public static void handleBuffer2(ByteBuf byteBuf) {
        ByteBuf byteBuf2 = byteBuf;
        if (byteBuf2.refCnt() >= 1) {
            byteBuf2 = byteBuf2.readBytes(byteBuf2.readableBytes());
        }
        ByteBuf byteBuf3 = byteBuf2;
        release(byteBuf3);
    }

    public static void release(ByteBuf byteBuf) {
        if (byteBuf.refCnt() > 0) {
            byteBuf.release(byteBuf.refCnt());
        }
    }

    public static long getSize(Object result) {
        return ByteBufHelper.capacity(result);
    }
}
