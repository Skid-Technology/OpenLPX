package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server.WrapperCommonServerPostEffects;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class WrapperPlayServerPostEffects
        extends WrapperCommonServerPostEffects<WrapperPlayServerPostEffects> {

    public WrapperPlayServerPostEffects(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerPostEffects() {
        super(PacketType.Play.Server.POST_EFFECTS);
    }

    public WrapperPlayServerPostEffects(List<ResourceLocation> values) {
        super(PacketType.Play.Server.POST_EFFECTS, values);
    }
}
