package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamOutput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.storage.BaseStorage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.storage.BitStorage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.storage.LegacyFlexibleStorage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class DataPalette {

    public final PaletteType paletteType;

    public Palette palette;

    public BaseStorage storage;

    public DataPalette(Palette palette2, BaseStorage baseStorage, PaletteType paletteType2) {
        this.palette = palette2;
        this.storage = baseStorage;
        this.paletteType = paletteType2;
    }

    @Deprecated
    public static DataPalette createForChunk() {
        return PaletteType.CHUNK.create();
    }

    @Deprecated
    public static DataPalette createForBiome() {
        return PaletteType.BIOME.create();
    }

    @Deprecated
    public static DataPalette createEmpty(PaletteType paletteType) {
        return paletteType.create();
    }

    @Deprecated
    public static DataPalette read(NetStreamInput netStreamInput, PaletteType paletteType) {
        return read(netStreamInput, paletteType, true);
    }

    @Deprecated
    public static DataPalette read(
            NetStreamInput netStreamInput, PaletteType paletteType, boolean matches) {
        return read(netStreamInput, paletteType, matches, true);
    }

    public static DataPalette read(PacketWrapper<?> packetWrapper, PaletteType paletteType) {
        byte value = packetWrapper.readByte();
        Palette palette;
        BitStorage bitStorage;
        if (!((palette = readPalette(paletteType, value, packetWrapper))
                instanceof SingletonPalette)) {
            if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
                long[] values = packetWrapper.readLongArray();
                bitStorage = new BitStorage(value, paletteType.getStorageSize(), values);
            } else {
                bitStorage = new BitStorage(value, paletteType.getStorageSize());
                packetWrapper.readLongArray(bitStorage.getData());
            }
        } else {
            if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
                ByteBufHelper.skipBytes(packetWrapper.buffer, packetWrapper.readVarInt() * 8);
            }
            bitStorage = null;
        }
        return new DataPalette(palette, bitStorage, paletteType);
    }

    @Deprecated
    public static DataPalette read(
            NetStreamInput netStreamInput,
            PaletteType paletteType,
            boolean matches,
            boolean matches2) {
        byte value = netStreamInput.readByte();
        Palette palette;
        BitStorage bitStorage;
        if (!((palette = readPalette(paletteType, value, netStreamInput, matches))
                instanceof SingletonPalette)) {
            long[] values = matches2 ? netStreamInput.readLongs(netStreamInput.readVarInt()) : null;
            bitStorage = new BitStorage(value, paletteType.getStorageSize(), values);
            if (!matches2) {
                netStreamInput.readLongs(bitStorage.getData());
            }
        } else {
            if (matches2) {
                netStreamInput.readLongs(netStreamInput.readVarInt());
            }
            bitStorage = null;
        }
        return new DataPalette(palette, bitStorage, paletteType);
    }

    @Deprecated
    public static void write(NetStreamOutput netStreamOutput, DataPalette dataPalette) {
        write(netStreamOutput, dataPalette, true);
    }

    @Deprecated
    public static void write(
            NetStreamOutput netStreamOutput, DataPalette dataPalette, boolean matches) {
        if (dataPalette.palette instanceof SingletonPalette) {
            netStreamOutput.writeByte(0);
            netStreamOutput.writeVarInt(dataPalette.palette.idToState(0));
            if (matches) {
                netStreamOutput.writeVarInt(0);
            }
        } else {
            netStreamOutput.writeByte(dataPalette.storage.getBitsPerEntry());
            if (!(dataPalette.palette instanceof GlobalPalette)) {
                int size2 = dataPalette.palette.size();
                netStreamOutput.writeVarInt(size2);
                for (int index = 0; index < size2; index++) {
                    netStreamOutput.writeVarInt(dataPalette.palette.idToState(index));
                }
            }
            long[] data = dataPalette.storage.getData();
            if (matches) {
                netStreamOutput.writeVarInt(data.length);
            }
            netStreamOutput.writeLongs(data);
        }
    }

    @Deprecated
    public static DataPalette readLegacy(NetStreamInput netStreamInput) {
        int value = Math.max(4, netStreamInput.readByte() & 255);
        Palette palette = readPalette(PaletteType.CHUNK, value, netStreamInput, false);
        LegacyFlexibleStorage legacyFlexibleStorage =
                new LegacyFlexibleStorage(
                        value, netStreamInput.readLongs(netStreamInput.readVarInt()));
        return new DataPalette(palette, legacyFlexibleStorage, PaletteType.CHUNK);
    }

    public int get(int value, int value2, int value3) {
        if (this.storage != null) {
            int value4 = this.storage.get(index(this.paletteType, value, value2, value3));
            return this.palette.idToState(value4);
        } else {
            return this.palette.idToState(0);
        }
    }

    public int getAndSet(int value, int value2, int value3, int value4) {
        return this.palette.idToState(this.set(value, value2, value3, value4));
    }

    public int set(int value, int value2, int value3, int value4) {
        int value5;
        if ((value5 = this.palette.stateToId(value4)) == -1) {
            this.resizeOneUp();
            value5 = this.palette.stateToId(value4);
        }
        if (this.storage != null) {
            int value6 = index(this.paletteType, value, value2, value3);
            int value7 = this.storage.get(value6);
            this.storage.set(value6, value5);
            return value7;
        } else {
            return value4;
        }
    }

    @Deprecated
    private static Palette readPalette(
            PaletteType paletteType, int value, NetStreamInput netStreamInput, boolean matches) {
        if (value == 0 && matches) {
            return new SingletonPalette(netStreamInput);
        } else if (value <= paletteType.getMaxBitsPerEntryForList()) {
            int value2 =
                    paletteType.isForceMaxListPaletteSize()
                            ? paletteType.getMaxBitsPerEntryForList()
                            : value;
            return new ListPalette(value2, netStreamInput);
        } else {
            return value <= paletteType.getMaxBitsPerEntryForMap()
                    ? new MapPalette(value, netStreamInput)
                    : GlobalPalette.INSTANCE;
        }
    }

    private static Palette readPalette(
            PaletteType paletteType, int value, PacketWrapper<?> packetWrapper) {
        if (value == 0
                && packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_18)) {
            return new SingletonPalette(packetWrapper);
        } else if (value <= paletteType.getMaxBitsPerEntryForList()) {
            int value2 =
                    paletteType.isForceMaxListPaletteSize()
                            ? paletteType.getMaxBitsPerEntryForList()
                            : value;
            return new ListPalette(value2, packetWrapper);
        } else {
            return value <= paletteType.getMaxBitsPerEntryForMap()
                    ? new MapPalette(value, packetWrapper)
                    : GlobalPalette.INSTANCE;
        }
    }

    private void resizeOneUp() {
        Palette palette2 = this.palette;
        BaseStorage baseStorage = this.storage;
        int value = this.storage != null ? baseStorage.getBitsPerEntry() : 0;
        this.palette = createPalette(value + 1, this.paletteType);
        this.storage = new BitStorage(this.palette.getBits(), this.paletteType.getStorageSize());
        if (baseStorage != null) {
            int value2 = 0;
            for (int storageSize = this.paletteType.getStorageSize();
                    value2 < storageSize;
                    value2++) {
                this.storage.set(
                        value2,
                        this.palette.stateToId(palette2.idToState(baseStorage.get(value2))));
            }
        } else {
            this.palette.stateToId(palette2.idToState(0));
        }
    }

    private static Palette createPalette(int value, PaletteType paletteType) {
        if (value <= paletteType.getMaxBitsPerEntryForList()) {
            int value2 =
                    paletteType.isForceMaxListPaletteSize()
                            ? paletteType.getMaxBitsPerEntryForList()
                            : value;
            return new ListPalette(value2);
        } else {
            return value <= paletteType.getMaxBitsPerEntryForMap()
                    ? new MapPalette(value)
                    : GlobalPalette.INSTANCE;
        }
    }

    private static int index(PaletteType paletteType, int value, int value2, int value3) {
        return (value2 << paletteType.getBitShift() | value3) << paletteType.getBitShift() | value;
    }
}
