package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v_1_18;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamOutput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamOutputWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.DataPalette;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.PaletteType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class Chunk_v1_18 implements BaseChunk {

    private final ClientVersion version;

    private int blockCount;

    private int fluidCount;

    private final DataPalette chunkData;

    private final DataPalette biomeData;

    @Deprecated
    public Chunk_v1_18() {
        this(PacketEvents.getAPI().getServerManager().getVersion().toClientVersion());
    }

    public Chunk_v1_18(ClientVersion clientVersion) {
        this.version = clientVersion;
        this.chunkData = PaletteType.CHUNK.create();
        this.biomeData = PaletteType.BIOME.create();
    }

    @Deprecated
    @Obsolete
    public Chunk_v1_18(int value, DataPalette dataPalette, DataPalette dataPalette2) {
        this(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                value,
                0,
                dataPalette,
                dataPalette2);
    }

    @Deprecated
    public Chunk_v1_18(int value, int value2, DataPalette dataPalette, DataPalette dataPalette2) {
        this(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                value,
                value2,
                dataPalette,
                dataPalette2);
    }

    @Obsolete
    public Chunk_v1_18(
            ClientVersion clientVersion,
            int value,
            DataPalette dataPalette,
            DataPalette dataPalette2) {
        this(clientVersion, value, 0, dataPalette, dataPalette2);
    }

    public Chunk_v1_18(
            ClientVersion clientVersion,
            int blockCount,
            int fluidCount,
            DataPalette dataPalette,
            DataPalette dataPalette2) {
        this.version = clientVersion;
        this.blockCount = blockCount;
        this.fluidCount = fluidCount;
        this.chunkData = dataPalette;
        this.biomeData = dataPalette2;
    }

    public static Chunk_v1_18 read(PacketWrapper<?> packetWrapper) {
        ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
        short value = packetWrapper.readShort();
        short value2 =
                clientVersion.isNewerThanOrEquals(ClientVersion.V_26_1)
                        ? packetWrapper.readShort()
                        : 0;
        DataPalette dataPalette = DataPalette.read(packetWrapper, PaletteType.CHUNK);
        DataPalette dataPalette2 = DataPalette.read(packetWrapper, PaletteType.BIOME);
        return new Chunk_v1_18(clientVersion, value, value2, dataPalette, dataPalette2);
    }

    @Deprecated
    public static Chunk_v1_18 read(NetStreamInput netStreamInput) {
        return read(netStreamInput, true);
    }

    @Deprecated
    public static Chunk_v1_18 read(NetStreamInput netStreamInput, boolean matches) {
        return read(netStreamInput, matches, false);
    }

    @Deprecated
    public static Chunk_v1_18 read(
            NetStreamInput netStreamInput, boolean matches, boolean matches2) {
        return read(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                netStreamInput,
                matches,
                matches2);
    }

    @Deprecated
    public static Chunk_v1_18 read(
            ClientVersion clientVersion,
            NetStreamInput netStreamInput,
            boolean matches,
            boolean matches2) {
        short value = netStreamInput.readShort();
        short value2 = matches2 ? netStreamInput.readShort() : 0;
        DataPalette dataPalette =
                DataPalette.read(netStreamInput, PaletteType.CHUNK, true, matches);
        DataPalette dataPalette2 =
                DataPalette.read(netStreamInput, PaletteType.BIOME, true, matches);
        return new Chunk_v1_18(clientVersion, value, value2, dataPalette, dataPalette2);
    }

    public static void write(PacketWrapper<?> packetWrapper, Chunk_v1_18 chunk_v1_18) {
        boolean olderThan = packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5);
        boolean newerThanOrEquals =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1);
        write(new NetStreamOutputWrapper(packetWrapper), chunk_v1_18, olderThan, newerThanOrEquals);
    }

    @Deprecated
    public static void write(NetStreamOutput netStreamOutput, Chunk_v1_18 chunk_v1_18) {
        write(netStreamOutput, chunk_v1_18, true);
    }

    @Deprecated
    public static void write(
            NetStreamOutput netStreamOutput, Chunk_v1_18 chunk_v1_18, boolean matches) {
        write(netStreamOutput, chunk_v1_18, matches, false);
    }

    @Deprecated
    public static void write(
            NetStreamOutput netStreamOutput,
            Chunk_v1_18 chunk_v1_18,
            boolean matches,
            boolean matches2) {
        netStreamOutput.writeShort(chunk_v1_18.blockCount);
        if (matches2) {
            netStreamOutput.writeShort(chunk_v1_18.fluidCount);
        }
        DataPalette.write(netStreamOutput, chunk_v1_18.chunkData, matches);
        DataPalette.write(netStreamOutput, chunk_v1_18.biomeData, matches);
    }

    @Override
    public int getBlockId(int value, int value2, int value3) {
        return this.chunkData.get(value, value2, value3);
    }

    @Override
    public void set(int value, int value2, int value3, WrappedBlockState wrappedBlockState) {
        int andSet =
                this.chunkData.getAndSet(value, value2, value3, wrappedBlockState.getGlobalId());
        WrappedBlockState wrappedBlockState2;
        if ((wrappedBlockState2 = WrappedBlockState.getByGlobalId(this.version, andSet, false))
                .getType()
                .isAir()) {
            if (!wrappedBlockState.getType().isAir()) {
                this.blockCount++;
            }
        } else if (wrappedBlockState.getType().isAir()) {
            this.blockCount--;
        }
        if (this.version.isNewerThanOrEquals(ClientVersion.V_26_1)) {
            if (!wrappedBlockState2.isFluid()) {
                if (wrappedBlockState.isFluid()) {
                    this.fluidCount++;
                }
            } else if (!wrappedBlockState.isFluid()) {
                this.fluidCount--;
            }
        }
    }

    @Override
    public void set(int value, int value2, int value3, int value4) {
        this.set(
                value,
                value2,
                value3,
                WrappedBlockState.getByGlobalId(this.version, value4, false));
    }

    @Override
    public void set(ClientVersion clientVersion, int value, int value2, int value3, int value4) {
        this.set(
                value,
                value2,
                value3,
                WrappedBlockState.getByGlobalId(clientVersion, value4, false));
    }

    @Override
    public boolean isEmpty() {
        return this.blockCount == 0;
    }

    public int getBlockCount() {
        return this.blockCount;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }

    public int getFluidCount() {
        return this.fluidCount;
    }

    public void setFluidCount(int fluidCount) {
        this.fluidCount = fluidCount;
    }

    public DataPalette getChunkData() {
        return this.chunkData;
    }

    public DataPalette getBiomeData() {
        return this.biomeData;
    }
}
