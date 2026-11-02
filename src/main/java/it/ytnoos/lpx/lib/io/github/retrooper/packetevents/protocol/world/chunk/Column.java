package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLongArray;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Column {

    private final int x;

    private final int z;

    private final boolean fullChunk;

    private final BaseChunk[] chunks;

    private final TileEntity[] tileEntities;

    private final boolean hasHeightmaps;

    @Nullable private NBTCompound heightmapsNbt;

    @Nullable private Map<HeightmapType, long[]> heightmaps;

    private final boolean hasBiomeData;

    private int[] biomeDataInts;

    private byte[] biomeDataBytes;

    public Column(
            int x,
            int z,
            boolean fullChunk,
            BaseChunk[] baseChunks,
            TileEntity[] tileEntitys,
            int[] values) {
        this.x = x;
        this.z = z;
        this.fullChunk = fullChunk;
        this.chunks = Arrays.copyOf(baseChunks, baseChunks.length);
        this.tileEntities = tileEntitys != null ? tileEntitys : new TileEntity[0];
        this.hasHeightmaps = false;
        this.heightmapsNbt = new NBTCompound();
        this.hasBiomeData = true;
        this.biomeDataInts = values != null ? Arrays.copyOf(values, values.length) : null;
    }

    public Column(
            int x, int z, boolean fullChunk, BaseChunk[] baseChunks, TileEntity[] tileEntitys) {
        this.x = x;
        this.z = z;
        this.fullChunk = fullChunk;
        this.chunks = Arrays.copyOf(baseChunks, baseChunks.length);
        this.tileEntities = tileEntitys != null ? tileEntitys : new TileEntity[0];
        this.hasHeightmaps = false;
        this.heightmapsNbt = new NBTCompound();
        this.hasBiomeData = false;
        this.biomeDataInts = new int[1024];
    }

    public Column(
            int x,
            int z,
            boolean fullChunk,
            BaseChunk[] baseChunks,
            TileEntity[] tileEntitys,
            NBTCompound nBTCompound) {
        this.x = x;
        this.z = z;
        this.fullChunk = fullChunk;
        this.chunks = Arrays.copyOf(baseChunks, baseChunks.length);
        this.tileEntities = tileEntitys != null ? tileEntitys : new TileEntity[0];
        this.hasHeightmaps = true;
        this.heightmapsNbt = nBTCompound;
        this.hasBiomeData = false;
        this.biomeDataInts = new int[1024];
    }

    public Column(
            int x,
            int z,
            boolean fullChunk,
            BaseChunk[] baseChunks,
            TileEntity[] tileEntitys,
            Map<HeightmapType, long[]> entries) {
        this.x = x;
        this.z = z;
        this.fullChunk = fullChunk;
        this.chunks = Arrays.copyOf(baseChunks, baseChunks.length);
        this.tileEntities = tileEntitys != null ? tileEntitys : new TileEntity[0];
        this.hasHeightmaps = true;
        this.heightmapsNbt = null;
        this.heightmaps = entries;
        this.hasBiomeData = false;
        this.biomeDataInts = new int[1024];
    }

    public Column(
            int x,
            int z,
            boolean fullChunk,
            BaseChunk[] baseChunks,
            TileEntity[] tileEntitys,
            NBTCompound nBTCompound,
            int[] values) {
        this.x = x;
        this.z = z;
        this.fullChunk = fullChunk;
        this.chunks = Arrays.copyOf(baseChunks, baseChunks.length);
        this.tileEntities = tileEntitys != null ? tileEntitys : new TileEntity[0];
        this.hasHeightmaps = true;
        this.heightmapsNbt = nBTCompound;
        this.hasBiomeData = true;
        this.biomeDataInts = values != null ? Arrays.copyOf(values, values.length) : null;
    }

    public Column(
            int x,
            int z,
            boolean fullChunk,
            BaseChunk[] baseChunks,
            TileEntity[] tileEntitys,
            NBTCompound nBTCompound,
            byte[] values) {
        this.x = x;
        this.z = z;
        this.fullChunk = fullChunk;
        this.chunks = Arrays.copyOf(baseChunks, baseChunks.length);
        this.tileEntities = tileEntitys != null ? tileEntitys : new TileEntity[0];
        this.hasHeightmaps = true;
        this.heightmapsNbt = nBTCompound;
        this.hasBiomeData = true;
        this.biomeDataBytes = values != null ? Arrays.copyOf(values, values.length) : null;
    }

    public Column(
            int x,
            int z,
            boolean fullChunk,
            BaseChunk[] baseChunks,
            TileEntity[] tileEntitys,
            byte[] values) {
        this.x = x;
        this.z = z;
        this.fullChunk = fullChunk;
        this.chunks = Arrays.copyOf(baseChunks, baseChunks.length);
        this.tileEntities = tileEntitys != null ? tileEntitys : new TileEntity[0];
        this.hasHeightmaps = false;
        this.heightmapsNbt = new NBTCompound();
        this.hasBiomeData = true;
        this.biomeDataBytes = values != null ? Arrays.copyOf(values, values.length) : null;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public boolean isFullChunk() {
        return this.fullChunk;
    }

    public BaseChunk[] getChunks() {
        return this.chunks;
    }

    public TileEntity[] getTileEntities() {
        return this.tileEntities;
    }

    public boolean hasHeightMaps() {
        return this.hasHeightmaps;
    }

    @Deprecated
    public NBTCompound getHeightMaps() {
        if (this.heightmapsNbt == null) {
            this.heightmapsNbt = new NBTCompound();
            for (Entry<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk
                                    .HeightmapType,
                            long[]>
                    entry : this.getHeightmaps().entrySet()) {
                this.heightmapsNbt.setTag(
                        ((HeightmapType) entry.getKey()).getSerializationKey(),
                        new NBTLongArray((long[]) entry.getValue()));
            }
        }
        return this.heightmapsNbt;
    }

    public Map<HeightmapType, long[]> getHeightmaps() {
        if (this.heightmaps == null) {
            if (this.hasHeightmaps && !this.heightmapsNbt.isEmpty()) {
                this.heightmaps = new EnumMap<>(HeightmapType.class);
                Iterator<
                                Entry<
                                        String,
                                        it.ytnoos
                                                .lpx
                                                .lib
                                                .io
                                                .github
                                                .retrooper
                                                .packetevents
                                                .protocol
                                                .nbt
                                                .NBT>>
                        iterator2 = this.heightmapsNbt.getTags().entrySet().iterator();
                while (iterator2.hasNext()) {
                    Entry entry;
                    HeightmapType heightmapType;
                    if ((heightmapType =
                                            HeightmapType.getHeightmapType(
                                                    (String)
                                                            (entry = (Entry) iterator2.next())
                                                                    .getKey()))
                                    != null
                            && entry.getValue() instanceof NBTLongArray) {
                        long[] value = ((NBTLongArray) entry.getValue()).getValue();
                        this.heightmaps.put(heightmapType, value);
                    }
                }
            } else {
                this.heightmaps = Collections.emptyMap();
            }
        }
        return this.heightmaps;
    }

    public boolean hasBiomeData() {
        return this.hasBiomeData;
    }

    public int[] getBiomeDataInts() {
        return this.biomeDataInts;
    }

    public byte[] getBiomeDataBytes() {
        return this.biomeDataBytes;
    }
}
