package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerCookieRequest extends PacketWrapper<WrapperPlayServerCookieRequest> {

    private ResourceLocation key;

    public WrapperPlayServerCookieRequest(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerCookieRequest(ResourceLocation resourceLocation) {
        super(PacketType.Play.Server.COOKIE_REQUEST);
        this.key = resourceLocation;
    }

    @Override
    public void read() {
        this.key = this.readIdentifier();
    }

    @Override
    public void write() {
        this.writeIdentifier(this.key);
    }

    public void copy(WrapperPlayServerCookieRequest wrapperPlayServerCookieRequest) {
        this.key = wrapperPlayServerCookieRequest.key;
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    public void setKey(ResourceLocation resourceLocation) {
        this.key = resourceLocation;
    }
}
