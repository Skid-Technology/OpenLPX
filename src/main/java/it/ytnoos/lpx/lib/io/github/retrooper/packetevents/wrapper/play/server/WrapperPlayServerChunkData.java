package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.UnpooledByteBufAllocationHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.ChunkBitMask;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.Column;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.HeightmapType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.LightData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.NetworkChunkData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.TileEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_16.Chunk_v1_9;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_7.Chunk_v1_7;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_8.Chunk_v1_8;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v_1_18.Chunk_v1_18;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.ChunkReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl.ChunkReader_v1_16;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl.ChunkReader_v1_18;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl.ChunkReader_v1_7;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl.ChunkReader_v1_8;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl.ChunkReader_v1_9;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.BitSet;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class WrapperPlayServerChunkData extends PacketWrapper<WrapperPlayServerChunkData> {

    private static ChunkReader_v1_7 chunkReader_v1_7 = new ChunkReader_v1_7();

    private static ChunkReader_v1_8 chunkReader_v1_8 = new ChunkReader_v1_8();

    private static ChunkReader_v1_9 chunkReader_v1_9 = new ChunkReader_v1_9();

    private static ChunkReader_v1_16 chunkReader_v1_16 = new ChunkReader_v1_16();

    private static ChunkReader_v1_18 chunkReader_v1_18 = new ChunkReader_v1_18();

    private Column column;

    private LightData lightData;

    private boolean ignoreOldData;

    public WrapperPlayServerChunkData(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerChunkData(Column column) {
        this(column, null, false);
    }

    public WrapperPlayServerChunkData(Column column, LightData lightData) {
        this(column, lightData, false);
    }

    public WrapperPlayServerChunkData(Column column2, LightData lightData2, boolean ignoreOldData) {
        super(PacketType.Play.Server.CHUNK_DATA);
        this.column = column2;
        this.lightData = lightData2;
        this.ignoreOldData = ignoreOldData;
    }

    @Override
    public void read() {
        int value = this.readInt();
        int value2 = this.readInt();
        boolean matches =
                !this.serverVersion.isOlderThan(ServerVersion.V_1_17) || this.readBoolean();
        if (this.serverVersion == ServerVersion.V_1_16
                || this.serverVersion == ServerVersion.V_1_16_1) {
            this.ignoreOldData = this.readBoolean();
        }
        BitSet bitSet =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)
                        ? null
                        : ChunkBitMask.readChunkMask(this);
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14);
        NBTCompound nBTCompound = null;
        Map entries = null;
        if (newerThanOrEquals) {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
                entries = this.readMap(HeightmapType::read, PacketWrapper::readLongArray);
            } else {
                nBTCompound = this.readNBT();
            }
        }
        BitSet bitSet2 = null;
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            bitSet2 = ChunkBitMask.readChunkMask(this);
        }
        int value3 = 16;
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            value3 = this.user.getTotalWorldHeight() >> 4;
        }
        boolean matches2 = matches && this.serverVersion.isOlderThan(ServerVersion.V_1_18);
        boolean olderThan = this.serverVersion.isOlderThan(ServerVersion.V_1_13);
        int[] values = null;
        byte[] values2 = null;
        if (matches2 && this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
            values = this.readVarIntArray();
        } else if (matches2 && this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15)) {
            values = new int[1024];
            for (int index = 0; index < values.length; index++) {
                values[index] = this.readInt();
            }
        }
        boolean matches3 =
                (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)
                                || this.serverVersion.isOlderThan(ServerVersion.V_1_14))
                        && !this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_8_8);
        boolean matches4 =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)
                        || this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_8_8)
                        || this.user != null
                                && this.user.getDimensionType().hasSkyLight()
                                && this.serverVersion.isOlderThan(ServerVersion.V_1_14);
        Object result = this.buffer;
        int value4;
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            byte[] values3 = this.inflate(this.readByteArray(), bitSet, matches);
            this.buffer = UnpooledByteBufAllocationHelper.wrappedBuffer(values3);
            value4 = values3.length;
        } else {
            value4 = this.readVarInt();
        }
        BaseChunk[] baseChunks;
        try {
            int value5 = ByteBufHelper.readerIndex(this.buffer) + value4;
            baseChunks =
                    this.getChunkReader()
                            .read(
                                    this.user.getDimensionType(),
                                    bitSet,
                                    bitSet2,
                                    matches,
                                    matches3,
                                    matches4,
                                    value3,
                                    value4,
                                    this);
            if (matches2 && this.serverVersion.isOlderThan(ServerVersion.V_1_15)) {
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
                    values = new int[256];
                    for (int index2 = 0; index2 < values.length; index2++) {
                        values[index2] = this.readInt();
                    }
                } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                    values2 = new byte[256];
                    for (int index3 = 0; index3 < values2.length; index3++) {
                        values2[index3] = this.readByte();
                    }
                } else if (value4 == 0) {
                    values2 = new byte[0];
                } else {
                    values2 = this.readBytes(256);
                }
            }
            int value6 = ByteBufHelper.readerIndex(this.buffer);
            if (value5 != value6) {
                if (value5 < value6) {
                    throw new RuntimeException(
                            "Error while decoding chunk at "
                                    + value
                                    + " "
                                    + value2
                                    + "; expected reader index "
                                    + value5
                                    + ", got "
                                    + value6);
                }
                ByteBufHelper.readerIndex(this.buffer, value5);
            }
        } finally {
            if (this.buffer != result) {
                ByteBufHelper.release(this.buffer);
                this.buffer = result;
            }
        }
        TileEntity[] tileEntitys =
                new TileEntity
                        [this.serverVersion.isOlderThan(ServerVersion.V_1_9)
                                ? 0
                                : this.readVarInt()];
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
            for (int index4 = 0; index4 < tileEntitys.length; index4++) {
                tileEntitys[index4] =
                        new TileEntity(
                                this.readByte(),
                                this.readShort(),
                                this.readVarInt(),
                                this.readNBT());
            }
        } else {
            for (int index5 = 0; index5 < tileEntitys.length; index5++) {
                tileEntitys[index5] = new TileEntity(this.readNBT());
            }
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.lightData = LightData.read(this);
        }
        if (matches2) {
            if (newerThanOrEquals) {
                if (olderThan) {
                    this.column =
                            new Column(
                                    value,
                                    value2,
                                    true,
                                    baseChunks,
                                    tileEntitys,
                                    nBTCompound,
                                    values2);
                } else {
                    this.column =
                            new Column(
                                    value,
                                    value2,
                                    true,
                                    baseChunks,
                                    tileEntitys,
                                    nBTCompound,
                                    values);
                }
            } else if (olderThan) {
                this.column = new Column(value, value2, true, baseChunks, tileEntitys, values2);
            } else {
                this.column = new Column(value, value2, true, baseChunks, tileEntitys, values);
            }
        } else if (newerThanOrEquals) {
            if (entries != null) {
                this.column = new Column(value, value2, matches, baseChunks, tileEntitys, entries);
            } else {
                this.column =
                        new Column(value, value2, matches, baseChunks, tileEntitys, nBTCompound);
            }
        } else {
            this.column = new Column(value, value2, matches, baseChunks, tileEntitys);
        }
    }

    private byte[] inflate(byte[] values, BitSet bitSet, boolean matches) {
        byte value = 0;
        for (int index = 0; index < 16; index++) {
            value += bitSet.get(index) ? 1 : 0;
        }
        int value2 = 12288 * value;
        if (matches) {
            value2 += 256;
        }
        byte[] values2 = new byte[value2];
        Inflater inflater;
        (inflater = new Inflater()).setInput(values, 0, values.length);
        try {
            inflater.inflate(values2);
        } catch (DataFormatException dataFormatException) {
            dataFormatException.printStackTrace();
        } finally {
            inflater.end();
        }
        return values2;
    }

    @Override
    public void write() {
        this.writeInt(this.column.getX());
        this.writeInt(this.column.getZ());
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18);
        boolean newerThanOrEquals2 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17);
        boolean newerThanOrEquals3 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9);
        boolean newerThanOrEquals4 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8);
        if (!newerThanOrEquals2) {
            this.writeBoolean(this.column.isFullChunk());
        }
        boolean matches = false;
        if (this.serverVersion == ServerVersion.V_1_16
                || this.serverVersion == ServerVersion.V_1_16_1) {
            this.writeBoolean(this.ignoreOldData);
        }
        BitSet bitSet = new BitSet();
        BaseChunk[] chunks = this.column.getChunks();
        if (!this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            if (newerThanOrEquals4) {
                NetworkChunkData networkChunkData =
                        ChunkReader_v1_8.chunksToData(
                                (Chunk_v1_8[]) chunks, this.column.getBiomeDataBytes());
                this.writeShort(networkChunkData.getMask());
                this.writeByteArray(networkChunkData.getData());
            } else {
                NetworkChunkData networkChunkData2 =
                        ChunkReader_v1_7.chunksToData(
                                (Chunk_v1_7[]) chunks, this.column.getBiomeDataBytes());
                Deflater deflater = new Deflater(-1);
                byte[] values = new byte[networkChunkData2.getData().length];
                int value;
                try {
                    deflater.setInput(
                            networkChunkData2.getData(), 0, networkChunkData2.getData().length);
                    deflater.finish();
                    value = deflater.deflate(values);
                } finally {
                    deflater.end();
                }
                this.writeShort(networkChunkData2.getMask());
                this.writeShort(networkChunkData2.getExtendedChunkMask());
                this.writeInt(value);
                ByteBufHelper.writeBytes(this.buffer, values, 0, value);
            }
        } else {
            Object result = this.buffer;
            Object result2 = ByteBufHelper.allocateNewBuffer(this.buffer);
            this.buffer = result2;
            for (int index = 0; index < chunks.length; index++) {
                BaseChunk baseChunk = chunks[index];
                if (newerThanOrEquals) {
                    Chunk_v1_18.write(this, (Chunk_v1_18) baseChunk);
                } else if (newerThanOrEquals3 && baseChunk != null) {
                    bitSet.set(index);
                    Chunk_v1_9.write(this, (Chunk_v1_9) baseChunk);
                }
            }
            this.buffer = result;
            if (this.serverVersion.isOlderThan(ServerVersion.V_1_21_6)
                    && this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
                int mojangZeroByteSuffixLength =
                        ChunkReader_v1_18.getMojangZeroByteSuffixLength(chunks);
                int value2;
                if ((value2 = ByteBufHelper.writerIndex(result2) + mojangZeroByteSuffixLength)
                        > ByteBufHelper.capacity(result2)) {
                    ByteBufHelper.capacity(result2, value2);
                }
                ByteBufHelper.writerIndex(result2, value2);
            }
            if (this.column.isFullChunk() && this.serverVersion.isOlderThan(ServerVersion.V_1_15)) {
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
                    int value3 = ((Object[]) (result = this.column.getBiomeDataInts())).length;
                    for (int index2 = 0; index2 < value3; index2++) {
                        int value4 = (int) ((Object[]) result)[index2];
                        ByteBufHelper.writeInt(result2, value4);
                    }
                } else {
                    int value5 = ((Object[]) (result = this.column.getBiomeDataBytes())).length;
                    for (int index3 = 0; index3 < value5; index3++) {
                        byte value6 = (byte) ((Object[]) result)[index3];
                        ByteBufHelper.writeByte(result2, value6);
                    }
                }
                matches = true;
            }
            if (!newerThanOrEquals) {
                ChunkBitMask.writeChunkMask(this, bitSet);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
                    this.writeMap(
                            this.column.getHeightmaps(),
                            HeightmapType::write,
                            PacketWrapper::writeLongArray);
                } else {
                    this.writeNBT(this.column.getHeightMaps());
                }
            }
            if (this.column.hasBiomeData()
                    && this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15)
                    && !newerThanOrEquals) {
                boolean olderThan = this.serverVersion.isOlderThan(ServerVersion.V_1_13);
                int[] biomeDataInts = this.column.getBiomeDataInts();
                byte[] biomeDataBytes = this.column.getBiomeDataBytes();
                if (olderThan) {
                    int value7 = biomeDataBytes.length;
                    for (int index4 = 0; index4 < value7; index4++) {
                        byte value8 = biomeDataBytes[index4];
                        this.writeByte(value8);
                    }
                } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
                    this.writeVarIntArray(biomeDataInts);
                } else {
                    int value9 = biomeDataInts.length;
                    for (int index5 = 0; index5 < value9; index5++) {
                        int value10 = biomeDataInts[index5];
                        this.writeInt(value10);
                    }
                }
                matches = true;
            }
            this.writeVarInt(ByteBufHelper.readableBytes(result2));
            ByteBufHelper.writeBytes(this.buffer, result2);
            ByteBufHelper.release(result2);
            if (this.column.hasBiomeData() && !matches) {
                byte[] values2 = new byte[256];
                int[] biomeDataInts2 = this.column.getBiomeDataInts();
                for (int index6 = 0; index6 < values2.length; index6++) {
                    values2[index6] = (byte) biomeDataInts2[index6];
                }
                this.writeByteArray(values2);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
                this.writeVarInt(this.column.getTileEntities().length);
                TileEntity[] tileEntitys;
                int value11 = (tileEntitys = this.column.getTileEntities()).length;
                for (int index7 = 0; index7 < value11; index7++) {
                    TileEntity tileEntity = tileEntitys[index7];
                    this.writeByte(tileEntity.getPackedByte());
                    this.writeShort(tileEntity.getYShort());
                    this.writeVarInt(tileEntity.getType());
                    this.writeNBT(tileEntity.getNBT());
                }
            } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                TileEntity[] tileEntities = this.column.getTileEntities();
                this.writeVarInt(tileEntities.length);
                int value12 = tileEntities.length;
                for (int index8 = 0; index8 < value12; index8++) {
                    TileEntity tileEntity2 = tileEntities[index8];
                    this.writeNBT(tileEntity2.getNBT());
                }
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
                LightData.write(this, this.lightData);
            }
        }
    }

    public void copy(WrapperPlayServerChunkData wrapperPlayServerChunkData) {
        this.column = wrapperPlayServerChunkData.column;
        this.lightData =
                wrapperPlayServerChunkData.lightData != null
                        ? wrapperPlayServerChunkData.lightData.clone()
                        : null;
        this.ignoreOldData = wrapperPlayServerChunkData.ignoreOldData;
    }

    public Column getColumn() {
        return this.column;
    }

    public void setColumn(Column column2) {
        this.column = column2;
    }

    public LightData getLightData() {
        return this.lightData;
    }

    public void setLightData(LightData lightData2) {
        this.lightData = lightData2;
    }

    public boolean isIgnoreOldData() {
        return this.ignoreOldData;
    }

    public void setIgnoreOldData(boolean ignoreOldData) {
        this.ignoreOldData = ignoreOldData;
    }

    private ChunkReader getChunkReader() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
            return chunkReader_v1_18;
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            return chunkReader_v1_16;
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            return chunkReader_v1_9;
        } else {
            return this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)
                    ? chunkReader_v1_8
                    : chunkReader_v1_7;
        }
    }
}
