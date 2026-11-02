package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.BitSet;

public class LightData implements Cloneable {

    private boolean trustEdges;

    private BitSet blockLightMask;

    private BitSet skyLightMask;

    private BitSet emptyBlockLightMask;

    private BitSet emptySkyLightMask;

    private int skyLightCount;

    private int blockLightCount;

    private byte[][] skyLightArray;

    private byte[][] blockLightArray;

    public LightData() {}

    public LightData(
            boolean trustEdges,
            BitSet bitSet,
            BitSet bitSet2,
            BitSet bitSet3,
            BitSet bitSet4,
            int skyLightCount,
            int blockLightCount,
            byte[][] skyLightArray,
            byte[][] blockLightArray) {
        this.trustEdges = trustEdges;
        this.blockLightMask = bitSet;
        this.skyLightMask = bitSet2;
        this.emptyBlockLightMask = bitSet3;
        this.emptySkyLightMask = bitSet4;
        this.skyLightCount = skyLightCount;
        this.blockLightCount = blockLightCount;
        this.skyLightArray = skyLightArray;
        this.blockLightArray = blockLightArray;
    }

    public LightData clone() {
        try {
            LightData lightData;
            (lightData = (LightData) super.clone()).blockLightMask =
                    (BitSet) this.blockLightMask.clone();
            lightData.skyLightMask = (BitSet) this.skyLightMask.clone();
            lightData.emptyBlockLightMask = (BitSet) this.emptyBlockLightMask.clone();
            lightData.emptySkyLightMask = (BitSet) this.emptySkyLightMask.clone();
            lightData.skyLightArray = (byte[][]) this.skyLightArray.clone();
            lightData.blockLightArray = (byte[][]) this.blockLightArray.clone();
            return lightData;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError();
        }
    }

    public boolean isTrustEdges() {
        return this.trustEdges;
    }

    public void setTrustEdges(boolean trustEdges) {
        this.trustEdges = trustEdges;
    }

    public BitSet getBlockLightMask() {
        return this.blockLightMask;
    }

    public void setBlockLightMask(BitSet bitSet) {
        this.blockLightMask = bitSet;
    }

    public BitSet getSkyLightMask() {
        return this.skyLightMask;
    }

    public void setSkyLightMask(BitSet bitSet) {
        this.skyLightMask = bitSet;
    }

    public BitSet getEmptyBlockLightMask() {
        return this.emptyBlockLightMask;
    }

    public void setEmptyBlockLightMask(BitSet bitSet) {
        this.emptyBlockLightMask = bitSet;
    }

    public BitSet getEmptySkyLightMask() {
        return this.emptySkyLightMask;
    }

    public void setEmptySkyLightMask(BitSet bitSet) {
        this.emptySkyLightMask = bitSet;
    }

    public int getSkyLightCount() {
        return this.skyLightCount;
    }

    public void setSkyLightCount(int skyLightCount) {
        this.skyLightCount = skyLightCount;
    }

    public int getBlockLightCount() {
        return this.blockLightCount;
    }

    public void setBlockLightCount(int blockLightCount) {
        this.blockLightCount = blockLightCount;
    }

    public byte[][] getSkyLightArray() {
        return this.skyLightArray;
    }

    public void setSkyLightArray(byte[][] skyLightArray) {
        this.skyLightArray = skyLightArray;
    }

    public byte[][] getBlockLightArray() {
        return this.blockLightArray;
    }

    public void setBlockLightArray(byte[][] blockLightArray) {
        this.blockLightArray = blockLightArray;
    }

    public static LightData read(PacketWrapper<?> packetWrapper) {
        LightData lightData = new LightData();
        ServerVersion serverVersion;
        if ((serverVersion = packetWrapper.getServerVersion())
                .isOlderThanOrEquals(ServerVersion.V_1_19_4)) {
            lightData.trustEdges = packetWrapper.readBoolean();
        }
        lightData.skyLightMask = ChunkBitMask.readChunkMask(packetWrapper);
        lightData.blockLightMask = ChunkBitMask.readChunkMask(packetWrapper);
        lightData.emptySkyLightMask = ChunkBitMask.readChunkMask(packetWrapper);
        lightData.emptyBlockLightMask = ChunkBitMask.readChunkMask(packetWrapper);
        boolean newerThanOrEquals = serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17);
        lightData.skyLightCount = newerThanOrEquals ? packetWrapper.readVarInt() : 18;
        lightData.skyLightArray = new byte[lightData.skyLightCount][];
        for (int index = 0; index < lightData.skyLightCount; index++) {
            if (newerThanOrEquals || lightData.skyLightMask.get(index)) {
                lightData.skyLightArray[index] = packetWrapper.readByteArray();
            }
        }
        lightData.blockLightCount = newerThanOrEquals ? packetWrapper.readVarInt() : 18;
        lightData.blockLightArray = new byte[lightData.blockLightCount][];
        for (int index2 = 0; index2 < lightData.blockLightCount; index2++) {
            if (newerThanOrEquals || lightData.blockLightMask.get(index2)) {
                lightData.blockLightArray[index2] = packetWrapper.readByteArray();
            }
        }
        return lightData;
    }

    public static void write(PacketWrapper<?> packetWrapper, LightData lightData) {
        ServerVersion serverVersion;
        if ((serverVersion = packetWrapper.getServerVersion())
                .isOlderThanOrEquals(ServerVersion.V_1_19_4)) {
            packetWrapper.writeBoolean(lightData.trustEdges);
        }
        ChunkBitMask.writeChunkMask(packetWrapper, lightData.skyLightMask);
        ChunkBitMask.writeChunkMask(packetWrapper, lightData.blockLightMask);
        ChunkBitMask.writeChunkMask(packetWrapper, lightData.emptySkyLightMask);
        ChunkBitMask.writeChunkMask(packetWrapper, lightData.emptyBlockLightMask);
        boolean matches;
        if (matches = serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            packetWrapper.writeVarInt(lightData.skyLightCount);
        }
        for (int index = 0; index < lightData.skyLightCount; index++) {
            if (matches || lightData.skyLightMask.get(index)) {
                packetWrapper.writeByteArray(lightData.skyLightArray[index]);
            }
        }
        if (matches) {
            packetWrapper.writeVarInt(lightData.blockLightCount);
        }
        for (int index2 = 0; index2 < lightData.blockLightCount; index2++) {
            if (matches || lightData.blockLightMask.get(index2)) {
                packetWrapper.writeByteArray(lightData.blockLightArray[index2]);
            }
        }
    }
}
