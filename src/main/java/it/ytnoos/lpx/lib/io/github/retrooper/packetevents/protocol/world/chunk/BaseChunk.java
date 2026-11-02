package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_16.Chunk_v1_9;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_7.Chunk_v1_7;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_8.Chunk_v1_8;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v_1_18.Chunk_v1_18;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.DataPalette;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.ListPalette;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.PaletteType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.storage.LegacyFlexibleStorage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;

public interface BaseChunk {

    int getBlockId(int value, int value2, int value3);

    default WrappedBlockState get(ClientVersion clientVersion, int value, int value2, int value3) {
        return this.get(clientVersion, value, value2, value3, true);
    }

    default WrappedBlockState get(
            ClientVersion clientVersion, int value, int value2, int value3, boolean matches) {
        return WrappedBlockState.getByGlobalId(
                clientVersion, this.getBlockId(value, value2, value3), matches);
    }

    default WrappedBlockState get(int value, int value2, int value3) {
        return this.get(value, value2, value3, true);
    }

    default WrappedBlockState get(int value, int value2, int value3, boolean matches) {
        return this.get(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                value,
                value2,
                value3,
                matches);
    }

    default void set(int value, int value2, int value3, WrappedBlockState wrappedBlockState) {
        this.set(value, value2, value3, wrappedBlockState.getGlobalId());
    }

    void set(int value, int value2, int value3, int value4);

    default void set(ClientVersion clientVersion, int value, int value2, int value3, int value4) {
        this.set(value, value2, value3, value4);
    }

    boolean isEmpty();

    static BaseChunk create() {
        ServerVersion serverVersion;
        if ((serverVersion = PacketEvents.getAPI().getServerManager().getVersion())
                .isNewerThanOrEquals(ServerVersion.V_1_18)) {
            return new Chunk_v1_18(serverVersion.toClientVersion());
        } else if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            return new Chunk_v1_9(0, PaletteType.CHUNK.create());
        } else if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            return new Chunk_v1_9(
                    0,
                    new DataPalette(
                            new ListPalette(4),
                            new LegacyFlexibleStorage(4, 4096),
                            PaletteType.CHUNK));
        } else {
            return serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)
                    ? new Chunk_v1_8(new ShortArray3d(4096), null, null)
                    : new Chunk_v1_7(false, true);
        }
    }
}
