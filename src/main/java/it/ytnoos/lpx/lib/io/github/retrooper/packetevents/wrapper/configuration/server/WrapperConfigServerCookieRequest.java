package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperConfigServerCookieRequest
        extends PacketWrapper<WrapperConfigServerCookieRequest> {

    private ResourceLocation key;

    public WrapperConfigServerCookieRequest(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerCookieRequest(ResourceLocation resourceLocation) {
        super(PacketType.Configuration.Server.COOKIE_REQUEST);
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

    public void copy(WrapperConfigServerCookieRequest wrapperConfigServerCookieRequest) {
        this.key = wrapperConfigServerCookieRequest.key;
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    public void setKey(ResourceLocation resourceLocation) {
        this.key = resourceLocation;
    }
}
