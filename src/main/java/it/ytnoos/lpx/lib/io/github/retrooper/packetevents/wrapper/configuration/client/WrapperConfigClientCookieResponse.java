package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonCookieResponse;

import org.jetbrains.annotations.Nullable;

public class WrapperConfigClientCookieResponse
        extends WrapperCommonCookieResponse<WrapperConfigClientCookieResponse> {

    @Deprecated
    public WrapperConfigClientCookieResponse(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigClientCookieResponse(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperConfigClientCookieResponse(
            ResourceLocation resourceLocation, byte @Nullable [] values) {
        super(PacketType.Configuration.Client.COOKIE_RESPONSE, resourceLocation, values);
    }
}
