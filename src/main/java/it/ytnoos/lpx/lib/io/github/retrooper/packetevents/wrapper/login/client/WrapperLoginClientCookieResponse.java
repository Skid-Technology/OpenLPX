package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonCookieResponse;

import org.jetbrains.annotations.Nullable;

public class WrapperLoginClientCookieResponse
        extends WrapperCommonCookieResponse<WrapperLoginClientCookieResponse> {

    @Deprecated
    public WrapperLoginClientCookieResponse(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperLoginClientCookieResponse(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperLoginClientCookieResponse(
            ResourceLocation resourceLocation, byte @Nullable [] values) {
        super(PacketType.Login.Client.COOKIE_RESPONSE, resourceLocation, values);
    }
}
