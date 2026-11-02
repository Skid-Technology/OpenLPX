package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.PatchableComponentMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ItemStackSerialization {

    private static final Set<String> WARNED_COMPONENTS = ConcurrentHashMap.newKeySet();

    private ItemStackSerialization() {}

    public static ItemStack readOptionalTemplate(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_1)) {
            return read(packetWrapper);
        } else {
            return packetWrapper.readBoolean() ? readTemplate0(packetWrapper) : ItemStack.EMPTY;
        }
    }

    public static ItemStack readTemplate(PacketWrapper<?> packetWrapper) {
        return packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_1)
                ? read(packetWrapper)
                : readTemplate0(packetWrapper);
    }

    public static ItemStack readTemplate0(PacketWrapper<?> packetWrapper) {
        ItemType itemType = packetWrapper.readMappedEntity(ItemTypes.getRegistry());
        int value = packetWrapper.readVarInt();
        PatchableComponentMap patchableComponentMap =
                PatchableComponentMap.read(packetWrapper, itemType, false);
        ItemStack itemStack;
        if ((itemStack =
                        ItemStack.builder()
                                .type(itemType)
                                .amount(value)
                                .components(patchableComponentMap)
                                .build())
                .isEmpty()) {
            throw new IllegalStateException("Can't read empty item stack template: " + itemStack);
        } else {
            return itemStack;
        }
    }

    public static void writeOptionalTemplate(PacketWrapper<?> packetWrapper, ItemStack itemStack) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_1)) {
            write(packetWrapper, itemStack);
        } else {
            if (!itemStack.isEmpty()) {
                packetWrapper.writeBoolean(true);
                writeTemplate0(packetWrapper, itemStack);
            } else {
                packetWrapper.writeBoolean(false);
            }
        }
    }

    public static void writeTemplate(PacketWrapper<?> packetWrapper, ItemStack itemStack) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_1)) {
            write(packetWrapper, itemStack);
        } else {
            writeTemplate0(packetWrapper, itemStack);
        }
    }

    private static void writeTemplate0(PacketWrapper<?> packetWrapper, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            throw new IllegalStateException("Can't write empty item stack template: " + itemStack);
        }
        packetWrapper.writeMappedEntity(itemStack.getType());
        packetWrapper.writeVarInt(itemStack.getAmount());
        PatchableComponentMap.write(packetWrapper, itemStack, false);
    }

    public static ItemStack read(PacketWrapper<?> packetWrapper) {
        int value = ByteBufHelper.readerIndex(packetWrapper.buffer);
        try {
            packetWrapper.enterItemDepth();
            int maxItemDepth = PacketEvents.getAPI().getSettings().getMaxItemDepth();
            if (packetWrapper.getItemDepth() > maxItemDepth) {
                throw new PacketLimitException(
                        PacketLimitException.Limit.ITEM_DEPTH,
                        packetWrapper.getItemDepth(),
                        maxItemDepth);
            }
            int maxItemsPerPacket = PacketEvents.getAPI().getSettings().getMaxItemsPerPacket();
            int value2;
            if ((value2 = packetWrapper.incrementReadItemstacks()) > maxItemsPerPacket) {
                throw new PacketLimitException(
                        PacketLimitException.Limit.ITEMS_PER_PACKET, value2, maxItemsPerPacket);
            }
            ItemStack itemStack =
                    packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)
                            ? readModern(packetWrapper, false)
                            : readLegacy(packetWrapper);
            return finishItemStackRead(packetWrapper, itemStack, value);
        } finally {
            packetWrapper.exitItemDepth();
        }
    }

    private static ItemStack finishItemStackRead(
            PacketWrapper<?> packetWrapper, ItemStack itemStack, int value) {
        if (itemStack.isEmpty()) {
            return itemStack;
        }
        itemStack.setDecodedSizeBytes(ByteBufHelper.readerIndex(packetWrapper.buffer) - value);
        return packetWrapper.callItemStackDecodeEvent(itemStack);
    }

    public static void write(PacketWrapper<?> packetWrapper, @Nullable ItemStack itemStack) {
        ItemStack itemStack2 = itemStack == null ? ItemStack.EMPTY : itemStack;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            writeModern(packetWrapper, itemStack2);
        } else {
            writeLegacy(packetWrapper, itemStack2);
        }
    }

    private static ItemStack readLegacy(PacketWrapper<?> packetWrapper) {
        boolean matches;
        if ((matches = packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_13_2))
                && !packetWrapper.readBoolean()) {
            return ItemStack.EMPTY;
        }
        int value;
        if ((value = matches ? packetWrapper.readVarInt() : packetWrapper.readShort()) < 0
                && !matches) {
            return ItemStack.EMPTY;
        }
        ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
        ItemType byIdOrThrow = ItemTypes.getRegistry().getByIdOrThrow(clientVersion, value);
        byte value2 = packetWrapper.readByte();
        short value3 =
                clientVersion.isOlderThan(ClientVersion.V_1_13) ? packetWrapper.readShort() : -1;
        NBTCompound nBTCompound = packetWrapper.readNBT();
        return ItemStack.builder()
                .type(byIdOrThrow)
                .amount(value2)
                .nbt(nBTCompound)
                .legacyData(value3)
                .wrapper(packetWrapper)
                .build();
    }

    private static void writeLegacy(PacketWrapper<?> packetWrapper, ItemStack itemStack) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_13_2)) {
            int value =
                    itemStack.isEmpty()
                            ? -1
                            : itemStack
                                    .getType()
                                    .getId(packetWrapper.getServerVersion().toClientVersion());
            packetWrapper.writeShort(value);
            if (value != -1) {
                packetWrapper.writeByte(itemStack.getAmount());
                if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_13)) {
                    packetWrapper.writeShort(itemStack.getLegacyData());
                }
                packetWrapper.writeNBT(itemStack.getNBT());
            }
        } else if (itemStack.isEmpty()) {
            packetWrapper.writeBoolean(false);
        } else {
            packetWrapper.writeBoolean(true);
            packetWrapper.writeMappedEntity(itemStack.getType());
            packetWrapper.writeByte(itemStack.getAmount());
            packetWrapper.writeNBT(itemStack.getNBT());
        }
    }

    public static ItemStack readModern(PacketWrapper<?> packetWrapper) {
        int value = ByteBufHelper.readerIndex(packetWrapper.buffer);
        return finishItemStackRead(packetWrapper, readModern(packetWrapper, false), value);
    }

    public static ItemStack readUntrusted(PacketWrapper<?> packetWrapper) {
        int value = ByteBufHelper.readerIndex(packetWrapper.buffer);
        return finishItemStackRead(packetWrapper, readModern(packetWrapper, true), value);
    }

    private static ItemStack readModern(PacketWrapper<?> packetWrapper, boolean matches) {
        int value;
        if ((value = packetWrapper.readVarInt()) <= 0) {
            return ItemStack.EMPTY;
        }
        ItemType itemType = packetWrapper.readMappedEntity(ItemTypes.getRegistry());
        int value2 = packetWrapper.readVarInt();
        int value3 = packetWrapper.readVarInt();
        if (value2 == 0 && value3 == 0) {
            return ItemStack.builder().type(itemType).amount(value).wrapper(packetWrapper).build();
        }
        PatchableComponentMap patchableComponentMap =
                new PatchableComponentMap(
                        itemType.getComponents(packetWrapper.getServerVersion().toClientVersion()),
                        new HashMap<>(value2 + value3),
                        packetWrapper.getRegistryHolder());
        for (int index = 0; index < value2; index++) {
            ComponentType<?> componentType =
                    packetWrapper.readMappedEntity(ComponentTypes.getRegistry());
            int value4;
            if (matches) {
                int value5;
                if ((value5 = packetWrapper.readVarInt())
                        > ByteBufHelper.readableBytes(packetWrapper.buffer)) {
                    throw new RuntimeException(
                            "Component size "
                                    + value5
                                    + " for "
                                    + componentType.getName()
                                    + " out of bounds");
                }
                value4 = ByteBufHelper.readerIndex(packetWrapper.buffer) + value5;
            } else {
                value4 = -1;
            }
            Object result;
            try {
                result = componentType.read(packetWrapper);
            } catch (Exception exception) {
                if (value4 != -1) {
                    if (!WARNED_COMPONENTS.add(componentType.getName().toString())) {
                        ByteBufHelper.readerIndex(packetWrapper.buffer, value4);
                    } else {
                        if (WARNED_COMPONENTS.size() > 10) {
                            throw exception;
                        }
                        PacketEvents.getAPI()
                                .getLogger()
                                .warning(
                                        "Failed to read component "
                                                + componentType.getName()
                                                + ", skipping: "
                                                + exception.getMessage());
                        ByteBufHelper.readerIndex(packetWrapper.buffer, value4);
                    }
                    continue;
                }
                throw exception;
            }
            int value6;
            if (value4 != -1
                    && (value6 = ByteBufHelper.readerIndex(packetWrapper.buffer)) != value4) {
                throw new RuntimeException(
                        "Invalid component read for "
                                + componentType.getName()
                                + "; expected reader index "
                                + value4
                                + ", got reader index "
                                + value6);
            }
            patchableComponentMap.set((ComponentType) componentType, result);
        }
        for (int index2 = 0; index2 < value3; index2++) {
            patchableComponentMap.unset(
                    packetWrapper.readMappedEntity(ComponentTypes.getRegistry()));
        }
        return ItemStack.builder()
                .type(itemType)
                .amount(value)
                .components(patchableComponentMap)
                .wrapper(packetWrapper)
                .build();
    }

    public static void writeModern(PacketWrapper<?> packetWrapper, ItemStack itemStack) {
        writeModern(packetWrapper, itemStack, false);
    }

    public static void writeUntrusted(PacketWrapper<?> packetWrapper, ItemStack itemStack) {
        writeModern(packetWrapper, itemStack, true);
    }

    private static void writeModern(
            PacketWrapper<?> packetWrapper, ItemStack itemStack, boolean matches) {
        if (itemStack.isEmpty()) {
            packetWrapper.writeByte(0);
        } else {
            packetWrapper.writeVarInt(itemStack.getAmount());
            packetWrapper.writeMappedEntity(itemStack.getType());
            PatchableComponentMap.write(packetWrapper, itemStack, matches);
        }
    }
}
