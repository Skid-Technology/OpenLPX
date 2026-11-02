package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonCookieResponse;

import org.jetbrains.annotations.Nullable;

public class WrapperPlayClientCookieResponse
        extends WrapperCommonCookieResponse<WrapperPlayClientCookieResponse> {

    @Deprecated
    public WrapperPlayClientCookieResponse(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayClientCookieResponse(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientCookieResponse(
            ResourceLocation resourceLocation, byte @Nullable [] values) {
        super(PacketType.Play.Client.COOKIE_RESPONSE, resourceLocation, values);
    }
}
