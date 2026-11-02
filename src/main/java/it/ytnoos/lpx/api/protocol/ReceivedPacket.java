package it.ytnoos.lpx.api.protocol;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByteArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLongArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class ReceivedPacket {

    private static final ThreadLocal<NumberFormat> FORMAT =
            ThreadLocal.withInitial(() -> new DecimalFormat("0.000"));

    private final String playerName;

    private final ClientVersion clientVersion;

    private final long createdAtNanos;

    private final long receivedAtTick;

    private final PacketTypeCommon packetType;

    private final long size;

    private Object packet;

    private long elapsedTime;

    private String packetString;

    private List<NBT> originalNbtData;

    public ReceivedPacket(
            String playerName,
            ClientVersion clientVersion2,
            long receivedAtTick,
            PacketTypeCommon packetTypeCommon,
            long size,
            Object packet) {
        this.playerName = playerName;
        this.clientVersion = clientVersion2;
        this.receivedAtTick = receivedAtTick;
        this.packetType = packetTypeCommon;
        this.createdAtNanos = System.nanoTime();
        this.size = size;
        this.packet = packet;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public long getReceivedAtTick() {
        return this.receivedAtTick;
    }

    public ClientVersion getClientVersion() {
        return this.clientVersion;
    }

    public String getClientVersionName() {
        return this.clientVersion.getReleaseName();
    }

    public PacketTypeCommon getPacketType() {
        return this.packetType;
    }

    public Object getPacket() {
        return this.packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }

    public long getSize() {
        return this.size;
    }

    public void addOriginalNbt(NBT nBT) {
        if (this.originalNbtData == null) {
            this.originalNbtData = new ArrayList<>();
        }
        if (this.originalNbtData.size() < 1000) {
            this.originalNbtData.add(nBT);
        }
    }

    public long getElapsedTime() {
        if (this.elapsedTime == 0L) {
            this.elapsedTime = System.nanoTime() - this.createdAtNanos;
        }
        return this.elapsedTime;
    }

    public String getFormattedElapsedTime() {
        return FORMAT.get().format(this.getElapsedTime() / 1000000.0);
    }

    @Override
    public String toString() {
        if (this.packetString == null) {
            this.packetString = this.buildPacketString().replace('\n', ' ');
            this.originalNbtData = null;
        }
        return this.packetString;
    }

    private String buildPacketString() {
        if (this.packet == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields;
        int value = (fields = this.packet.getClass().getDeclaredFields()).length;
        for (int index = 0; index < value; index++) {
            Field field = fields[index];
            if (field.getDeclaringClass().equals(this.packet.getClass())) {
                try {
                    field.setAccessible(true);
                    Object result = field.get(this.packet);
                    stringBuilder.append(field.getName()).append("=");
                    appendValue(result, stringBuilder);
                    stringBuilder.append(" ");
                } catch (IllegalAccessException illegalAccessException) {
                }
            }
        }
        if (this.originalNbtData != null && !this.originalNbtData.isEmpty()) {
            stringBuilder.append("originalNbt=[");
            boolean matches = true;
            for (NBT nBT : this.originalNbtData) {
                if (!matches) {
                    stringBuilder.append(", ");
                }
                matches = false;
                appendNbt(nBT, stringBuilder);
            }
            stringBuilder.append("] ");
        }
        return stringBuilder.toString();
    }

    private static void appendValue(Object result, StringBuilder stringBuilder) {
        if (result instanceof ItemStack) {
            appendItemStack((ItemStack) result, stringBuilder);
        } else if (result instanceof Map) {
            appendMap((Map<?, ?>) result, stringBuilder);
        } else if (result instanceof Optional) {
            Optional optional;
            if ((optional = (Optional) result).isPresent()) {
                appendValue(optional.get(), stringBuilder);
            } else {
                stringBuilder.append("empty");
            }
        } else if (result instanceof Object[]) {
            appendArray((Object[]) result, stringBuilder);
        } else if (result instanceof NBT) {
            appendNbt((NBT) result, stringBuilder);
        } else if (result instanceof byte[] values) {
            stringBuilder.append(Arrays.toString(values));
        } else {
            stringBuilder.append(result);
        }
    }

    private static void appendItemStack(ItemStack itemStack, StringBuilder stringBuilder) {
        stringBuilder.append("[type=").append(itemStack.getType().getName());
        NBTCompound nBTCompound;
        if ((nBTCompound = itemStack.getNBT()) != null && !nBTCompound.isEmpty()) {
            stringBuilder.append(", nbt=");
            appendNbt(nBTCompound, stringBuilder);
        }
        if (itemStack.hasComponentPatches()) {
            appendComponentPatches(itemStack, stringBuilder);
        }
        stringBuilder.append("]");
    }

    private static void appendComponentPatches(ItemStack itemStack, StringBuilder stringBuilder) {
        ClientVersion version = itemStack.getVersion();
        for (Entry<ComponentType<?>, Optional<?>> entry :
                itemStack.getComponents().getPatches().entrySet()) {
            stringBuilder
                    .append(", ")
                    .append(((ComponentType) entry.getKey()).getName())
                    .append("=");
            if (((Optional) entry.getValue()).isPresent()) {
                try {
                    appendNbt(
                            ((ComponentType) entry.getKey())
                                    .encode(((Optional) entry.getValue()).get(), version),
                            stringBuilder);
                } catch (Exception exception) {
                    stringBuilder.append(((Optional) entry.getValue()).get());
                }
            } else {
                stringBuilder.append("REMOVED");
            }
        }
    }

    private static void appendMap(Map<?, ?> entries, StringBuilder stringBuilder) {
        stringBuilder.append("{");
        boolean matches = true;
        for (Entry<?, ?> entry : entries.entrySet()) {
            if (!matches) {
                stringBuilder.append(", ");
            }
            matches = false;
            stringBuilder.append(entry.getKey()).append("=");
            appendValue(entry.getValue(), stringBuilder);
        }
        stringBuilder.append("}");
    }

    private static void appendArray(Object[] results, StringBuilder stringBuilder) {
        if (results.length != 0) {
            stringBuilder.append("[");
            boolean matches = true;
            int value = results.length;
            for (int index = 0; index < value; index++) {
                Object result = results[index];
                if (!matches) {
                    stringBuilder.append(", ");
                }
                matches = false;
                appendValue(result, stringBuilder);
            }
            stringBuilder.append("]");
        }
    }

    private static void appendNbt(NBT nBT, StringBuilder stringBuilder) {
        if (nBT instanceof NBTCompound) {
            NBTCompound nBTCompound;
            if ((nBTCompound = (NBTCompound) nBT).isEmpty()) {
                return;
            }
            stringBuilder.append("[");
            boolean matches = true;
            for (Entry<String, NBT> entry : nBTCompound.getTags().entrySet()) {
                if (!matches) {
                    stringBuilder.append(", ");
                }
                matches = false;
                stringBuilder.append((String) entry.getKey()).append("=");
                appendNbt((NBT) entry.getValue(), stringBuilder);
            }
            stringBuilder.append("]");
        } else if (nBT instanceof NBTList) {
            NBTList nBTList;
            if ((nBTList = (NBTList) nBT).isEmpty()) {
                stringBuilder.append("[]");
                return;
            }
            boolean matches2 = true;
            for (NBT nBT2 : ((Iterable<NBT>) ((Iterable<?>) nBTList.getTags()))) {
                if (!matches2) {
                    stringBuilder.append(", ");
                }
                matches2 = false;
                appendNbt(nBT2, stringBuilder);
            }
        } else if (nBT instanceof NBTNumber nBTNumber) {
            stringBuilder.append(nBTNumber.getAsNumber());
        } else if (nBT instanceof NBTString nBTString) {
            stringBuilder.append(nBTString.getValue());
        } else if (nBT instanceof NBTByteArray nBTByteArray) {
            stringBuilder.append(Arrays.toString(nBTByteArray.getValue()));
        } else if (nBT instanceof NBTIntArray nBTIntArray) {
            stringBuilder.append(Arrays.toString(nBTIntArray.getValue()));
        } else if (nBT instanceof NBTLongArray nBTLongArray) {
            stringBuilder.append(Arrays.toString(nBTLongArray.getValue()));
        } else {
            stringBuilder.append(nBT);
        }
    }
}
