package it.ytnoos.lpx.lib.io.github.retrooper.packetevents;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class PacketEvents {

    private static PacketEventsAPI<?> API;

    @Internal public static String IDENTIFIER;

    @Internal public static String ENCODER_NAME;

    @Internal public static String DECODER_NAME;

    @Internal public static String CONNECTION_HANDLER_NAME;

    @Internal public static String SERVER_CHANNEL_HANDLER_NAME;

    @Internal public static String TIMEOUT_HANDLER_NAME;

    public static PacketEventsAPI<?> getAPI() {
        return API;
    }

    public static void setAPI(PacketEventsAPI<?> packetEventsAPI) {
        API = packetEventsAPI;
    }
}
