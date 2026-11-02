package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.UnpooledByteBufAllocationHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.NetworkChunkData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_7.Chunk_v1_7;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_8.Chunk_v1_8;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl.ChunkReader_v1_7;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl.ChunkReader_v1_8;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.io.IOException;
import java.util.BitSet;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class WrapperPlayServerChunkDataBulk extends PacketWrapper<WrapperPlayServerChunkDataBulk> {

    private int[] x;

    private int[] z;

    private BaseChunk[][] chunks;

    private byte[][] biomeData;

    public WrapperPlayServerChunkDataBulk(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.read_1_8();
        } else {
            this.read_1_7();
        }
    }

    private void read_1_8() {
        boolean matches = this.readBoolean();
        int value = this.readVarInt();
        this.x = new int[value];
        this.z = new int[value];
        this.chunks = new BaseChunk[value][];
        this.biomeData = new byte[value][];
        NetworkChunkData[] networkChunkDatas = new NetworkChunkData[value];
        for (int index = 0; index < value; index++) {
            this.x[index] = this.readInt();
            this.z[index] = this.readInt();
            int value2;
            int value3;
            byte[] values =
                    new byte
                            [(value3 = Integer.bitCount(value2 = this.readUnsignedShort())) * 10240
                                    + (matches ? value3 * 2048 : 0)];
            networkChunkDatas[index] = new NetworkChunkData(value2, true, matches, values);
        }
        for (int index2 = 0; index2 < value; index2++) {
            BitSet bitSet = BitSet.valueOf(new long[] {networkChunkDatas[index2].getMask()});
            BaseChunk[] baseChunks =
                    new ChunkReader_v1_8()
                            .read(
                                    this.user.getDimensionType(),
                                    bitSet,
                                    null,
                                    true,
                                    matches,
                                    false,
                                    16,
                                    networkChunkDatas[index2].getData().length,
                                    this);
            this.chunks[index2] = baseChunks;
            this.biomeData[index2] = this.readBytes(256);
        }
    }

    private void read_1_7() {
        short value = this.readShort();
        int value2 = this.readInt();
        boolean matches = this.readBoolean();
        byte[] values = this.readBytes(value2);
        byte[] values2 = new byte[196864 * value];
        Inflater inflater;
        (inflater = new Inflater()).setInput(values, 0, value2);
        label79:
        {
            try {
                inflater.inflate(values2);
                break label79;
            } catch (DataFormatException dataFormatException) {
                new IOException("Bad compressed data format").printStackTrace();
            } finally {
                inflater.end();
            }
            return;
        }
        Object result = this.buffer;
        Object result2 = UnpooledByteBufAllocationHelper.wrappedBuffer(values2);
        this.x = new int[value];
        this.z = new int[value];
        this.chunks = new BaseChunk[value][];
        this.biomeData = new byte[value][];
        for (int index = 0; index < value; index++) {
            int value3 = this.readInt();
            int value4 = this.readInt();
            BitSet bitSet = BitSet.valueOf(new long[] {this.readUnsignedShort()});
            BitSet bitSet2 = BitSet.valueOf(new long[] {this.readUnsignedShort()});
            byte value5 = 0;
            byte value6 = 0;
            for (int index2 = 0; index2 < 16; index2++) {
                value5 += bitSet.get(index2) ? 1 : 0;
                value6 += bitSet2.get(index2) ? 1 : 0;
            }
            int value7 = 8192 * value5 + 256 + 2048 * value6;
            if (matches) {
                value7 += 2048 * value5;
            }
            this.buffer = result2;
            BaseChunk[] baseChunks =
                    new ChunkReader_v1_7()
                            .read(
                                    this.user.getDimensionType(),
                                    bitSet,
                                    bitSet2,
                                    true,
                                    matches,
                                    false,
                                    16,
                                    value7,
                                    this);
            byte[] values3 = this.readBytes(256);
            this.buffer = result;
            this.x[index] = value3;
            this.z[index] = value4;
            this.chunks[index] = baseChunks;
            this.biomeData[index] = values3;
        }
        ByteBufHelper.release(result2);
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.write_1_8();
        } else {
            this.write_1_7();
        }
    }

    public void copy(WrapperPlayServerChunkDataBulk wrapperPlayServerChunkDataBulk) {
        this.x = wrapperPlayServerChunkDataBulk.x;
        this.z = wrapperPlayServerChunkDataBulk.z;
        this.chunks = wrapperPlayServerChunkDataBulk.chunks;
        this.biomeData = wrapperPlayServerChunkDataBulk.biomeData;
    }

    private void write_1_8() {
        boolean matches = false;
        NetworkChunkData[] networkChunkDatas = new NetworkChunkData[this.chunks.length];
        for (int index = 0; index < this.chunks.length; index++) {
            networkChunkDatas[index] =
                    ChunkReader_v1_8.chunksToData(
                            (Chunk_v1_8[]) this.chunks[index], this.biomeData[index]);
            if (networkChunkDatas[index].hasSkyLight()) {
                matches = true;
            }
        }
        this.writeBoolean(matches);
        this.writeVarInt(this.chunks.length);
        for (int index2 = 0; index2 < this.x.length; index2++) {
            this.writeInt(this.x[index2]);
            this.writeInt(this.z[index2]);
            this.writeShort(networkChunkDatas[index2].getMask());
        }
        for (int index3 = 0; index3 < this.x.length; index3++) {
            this.writeBytes(networkChunkDatas[index3].getData());
        }
    }

    private void write_1_7() {
        int[] values = new int[this.chunks.length];
        int[] values2 = new int[this.chunks.length];
        int value = 0;
        byte[] values3 = new byte[0];
        boolean matches = false;
        for (int index = 0; index < this.chunks.length; index++) {
            NetworkChunkData networkChunkData =
                    ChunkReader_v1_7.chunksToData(
                            (Chunk_v1_7[]) this.chunks[index], this.biomeData[index]);
            if (values3.length < value + networkChunkData.getData().length) {
                byte[] values4 = new byte[value + networkChunkData.getData().length];
                System.arraycopy(values3, 0, values4, 0, values3.length);
                values3 = values4;
            }
            if (networkChunkData.hasSkyLight()) {
                matches = true;
            }
            System.arraycopy(
                    networkChunkData.getData(),
                    0,
                    values3,
                    value,
                    networkChunkData.getData().length);
            value += networkChunkData.getData().length;
            values[index] = networkChunkData.getMask();
            values2[index] = networkChunkData.getExtendedChunkMask();
        }
        Deflater deflater = new Deflater(-1);
        byte[] values5 = new byte[value];
        int value2;
        try {
            deflater.setInput(values3, 0, value);
            deflater.finish();
            value2 = deflater.deflate(values5);
        } finally {
            deflater.end();
        }
        this.writeShort(this.chunks.length);
        this.writeInt(value2);
        this.writeBoolean(matches);
        for (int index2 = 0; index2 < value2; index2++) {
            this.writeByte(values5[index2]);
        }
        for (int index3 = 0; index3 < this.chunks.length; index3++) {
            this.writeInt(this.x[index3]);
            this.writeInt(this.z[index3]);
            this.writeShort((short) (values[index3] & 65535));
            this.writeShort((short) (values2[index3] & 65535));
        }
    }

    public int[] getX() {
        return this.x;
    }

    public int[] getZ() {
        return this.z;
    }

    public BaseChunk[][] getChunks() {
        return this.chunks;
    }

    public byte[][] getBiomeData() {
        return this.biomeData;
    }
}
