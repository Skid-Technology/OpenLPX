package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_16;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInputWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamOutput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamOutputWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.NibbleArray3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.DataPalette;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.PaletteType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

public class Chunk_v1_9 implements BaseChunk {

    private int blockCount;

    private final DataPalette dataPalette;

    @Nullable private NibbleArray3d blockLight;

    @Nullable private NibbleArray3d skyLight;

    public Chunk_v1_9(int value, DataPalette dataPalette) {
        this(value, dataPalette, null, null);
    }

    public Chunk_v1_9(
            int blockCount,
            DataPalette dataPalette2,
            @Nullable NibbleArray3d nibbleArray3d,
            @Nullable NibbleArray3d nibbleArray3d2) {
        this.blockCount = blockCount;
        this.dataPalette = dataPalette2;
        this.blockLight = nibbleArray3d;
        this.skyLight = nibbleArray3d2;
    }

    @Deprecated
    public Chunk_v1_9(NetStreamInput netStreamInput, boolean matches, boolean matches2) {
        this(
                netStreamInput,
                matches,
                matches2,
                PacketEvents.getAPI().getServerManager().getVersion());
    }

    @Deprecated
    private Chunk_v1_9(
            NetStreamInput netStreamInput,
            boolean matches,
            boolean matches2,
            ServerVersion serverVersion) {
        this.blockCount =
                serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)
                        ? netStreamInput.readShort()
                        : Integer.MAX_VALUE;
        this.dataPalette =
                serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)
                        ? DataPalette.read(netStreamInput, PaletteType.CHUNK, false)
                        : DataPalette.readLegacy(netStreamInput);
        this.blockLight = matches ? new NibbleArray3d(netStreamInput, 2048) : null;
        this.skyLight = matches2 ? new NibbleArray3d(netStreamInput, 2048) : null;
    }

    public static Chunk_v1_9 read(
            PacketWrapper<?> packetWrapper, boolean matches, boolean matches2) {
        NetStreamInputWrapper netStreamInputWrapper = new NetStreamInputWrapper(packetWrapper);
        return new Chunk_v1_9(
                netStreamInputWrapper, matches, matches2, packetWrapper.getServerVersion());
    }

    public static void write(PacketWrapper<?> packetWrapper, Chunk_v1_9 chunk_v1_9) {
        write(
                new NetStreamOutputWrapper(packetWrapper),
                chunk_v1_9,
                packetWrapper.getServerVersion());
    }

    @Deprecated
    public static void write(NetStreamOutput netStreamOutput, Chunk_v1_9 chunk_v1_9) {
        write(netStreamOutput, chunk_v1_9, PacketEvents.getAPI().getServerManager().getVersion());
    }

    @Deprecated
    private static void write(
            NetStreamOutput netStreamOutput, Chunk_v1_9 chunk_v1_9, ServerVersion serverVersion) {
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            netStreamOutput.writeShort(chunk_v1_9.blockCount);
        }
        DataPalette.write(netStreamOutput, chunk_v1_9.dataPalette);
        if (chunk_v1_9.blockLight != null) {
            netStreamOutput.writeBytes(chunk_v1_9.blockLight.getData());
        }
        if (chunk_v1_9.skyLight != null) {
            netStreamOutput.writeBytes(chunk_v1_9.skyLight.getData());
        }
    }

    @Override
    public int getBlockId(int value, int value2, int value3) {
        return this.dataPalette.get(value, value2, value3);
    }

    @Override
    public void set(int value, int value2, int value3, int value4) {
        int andSet = this.dataPalette.getAndSet(value, value2, value3, value4);
        if (this.blockCount != Integer.MAX_VALUE) {
            if (value4 != 0 && andSet == 0) {
                this.blockCount++;
            } else if (value4 == 0 && andSet != 0) {
                this.blockCount--;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        if (this.blockCount != Integer.MAX_VALUE) {
            return this.blockCount == 0;
        }
        for (int index = 0; index < 16; index++) {
            for (int index2 = 0; index2 < 16; index2++) {
                for (int index3 = 0; index3 < 16; index3++) {
                    if (this.dataPalette.get(index, index2, index3) != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Nullable
    public NibbleArray3d getSkyLight() {
        return this.skyLight;
    }

    public void setSkyLight(@Nullable NibbleArray3d nibbleArray3d) {
        this.skyLight = nibbleArray3d;
    }

    @Nullable
    public NibbleArray3d getBlockLight() {
        return this.blockLight;
    }

    public void setBlockLight(@Nullable NibbleArray3d nibbleArray3d) {
        this.blockLight = nibbleArray3d;
    }
}
