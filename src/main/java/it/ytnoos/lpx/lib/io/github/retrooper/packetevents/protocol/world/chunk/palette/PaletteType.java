package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInputWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamOutputWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.storage.BitStorage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public enum PaletteType {
    BIOME(3, 3, false, 2),
    CHUNK(4, 8, true, 4);

    private final int maxBitsPerEntryForList;

    private final int maxBitsPerEntryForMap;

    private final boolean forceMaxListPaletteSize;

    private final int bitShift;

    private final int storageSize;

    PaletteType(
            int maxBitsPerEntryForList,
            int maxBitsPerEntryForMap,
            boolean forceMaxListPaletteSize,
            int bitShift) {
        this.maxBitsPerEntryForList = maxBitsPerEntryForList;
        this.maxBitsPerEntryForMap = maxBitsPerEntryForMap;
        this.forceMaxListPaletteSize = forceMaxListPaletteSize;
        this.bitShift = bitShift;
        this.storageSize = 1 << bitShift * 3;
    }

    public static void write(PacketWrapper<?> packetWrapper, DataPalette dataPalette) {
        boolean olderThan = packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5);
        DataPalette.write(new NetStreamOutputWrapper(packetWrapper), dataPalette, olderThan);
    }

    public DataPalette read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_16)
                ? DataPalette.readLegacy(new NetStreamInputWrapper(packetWrapper))
                : DataPalette.read(packetWrapper, this);
    }

    public DataPalette create() {
        int maxBitsPerEntryForList = this.getMaxBitsPerEntryForList();
        ListPalette listPalette = new ListPalette(maxBitsPerEntryForList);
        BitStorage bitStorage = new BitStorage(maxBitsPerEntryForList, this.getStorageSize());
        return new DataPalette(listPalette, bitStorage, this);
    }

    public int getMaxBitsPerEntryForList() {
        return this.maxBitsPerEntryForList;
    }

    public int getMaxBitsPerEntryForMap() {
        return this.maxBitsPerEntryForMap;
    }

    public boolean isForceMaxListPaletteSize() {
        return this.forceMaxListPaletteSize;
    }

    public int getBitShift() {
        return this.bitShift;
    }

    public int getStorageSize() {
        return this.storageSize;
    }

    @Deprecated
    public int getMaxBitsPerEntry() {
        return this.maxBitsPerEntryForMap;
    }

    @Deprecated
    public int getMinBitsPerEntry() {
        return this.maxBitsPerEntryForList;
    }
}
