package it.ytnoos.lpx.utils;

import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class PacketWrapperUtils {

    private PacketWrapperUtils() {}

    public static PacketWrapper createPacketWrapper(
            ProtocolPlayer protocolPlayer, Object result, boolean matches) {
        PacketWrapper packetWrapper =
                new PacketWrapper(
                        protocolPlayer.getUser().getClientVersion(),
                        matches
                                ? protocolPlayer.getUser().getClientVersion().toServerVersion()
                                : PacketEvents.getAPI().getServerManager().getVersion(),
                        -2);
        packetWrapper.buffer = result;
        packetWrapper.markReceivedData();
        return packetWrapper;
    }

    public static void handlePacketWrapper(PacketWrapper packetWrapper) {
        if (packetWrapper != null) {
            if (packetWrapper.buffer != null && ByteBufHelper.refCnt(packetWrapper.buffer) > 0) {
                ByteBufHelper.release(packetWrapper.buffer);
            }
        }
    }
}
