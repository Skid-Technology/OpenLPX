package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.PacketSide;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

public interface PacketTypeCommon {

    default String getName() {
        return ((Enum) this).name();
    }

    int getId(ClientVersion clientVersion);

    PacketSide getSide();

    @Nullable
    Class<? extends PacketWrapper<?>> getWrapperClass();
}
