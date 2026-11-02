package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server.WrapperCommonServerPostEffects;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class WrapperConfigServerPostEffects
        extends WrapperCommonServerPostEffects<WrapperConfigServerPostEffects> {

    public WrapperConfigServerPostEffects(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerPostEffects() {
        super(PacketType.Configuration.Server.POST_EFFECTS);
    }

    public WrapperConfigServerPostEffects(List<ResourceLocation> values) {
        super(PacketType.Configuration.Server.POST_EFFECTS, values);
    }
}
