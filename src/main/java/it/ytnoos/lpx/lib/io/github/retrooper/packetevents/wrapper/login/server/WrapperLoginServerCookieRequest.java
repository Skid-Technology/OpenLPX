package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperLoginServerCookieRequest
        extends PacketWrapper<WrapperLoginServerCookieRequest> {

    private ResourceLocation key;

    public WrapperLoginServerCookieRequest(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperLoginServerCookieRequest(ResourceLocation resourceLocation) {
        super(PacketType.Login.Server.COOKIE_REQUEST);
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

    public void copy(WrapperLoginServerCookieRequest wrapperLoginServerCookieRequest) {
        this.key = wrapperLoginServerCookieRequest.key;
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    public void setKey(ResourceLocation resourceLocation) {
        this.key = resourceLocation;
    }
}
