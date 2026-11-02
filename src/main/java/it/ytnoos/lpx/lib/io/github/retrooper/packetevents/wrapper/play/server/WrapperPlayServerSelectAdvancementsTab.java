package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

public class WrapperPlayServerSelectAdvancementsTab
        extends PacketWrapper<WrapperPlayServerSelectAdvancementsTab> {

    @Nullable private ResourceLocation identifier;

    public WrapperPlayServerSelectAdvancementsTab(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSelectAdvancementsTab(@Nullable ResourceLocation resourceLocation) {
        super(PacketType.Play.Server.SELECT_ADVANCEMENTS_TAB);
        this.identifier = resourceLocation;
    }

    @Override
    public void read() {
        this.identifier = this.readOptional(PacketWrapper::readIdentifier);
    }

    @Override
    public void write() {
        this.writeOptional(this.identifier, PacketWrapper::writeIdentifier);
    }

    public void copy(
            WrapperPlayServerSelectAdvancementsTab wrapperPlayServerSelectAdvancementsTab) {
        this.identifier = wrapperPlayServerSelectAdvancementsTab.identifier;
    }

    @Nullable
    public ResourceLocation getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(@Nullable ResourceLocation resourceLocation) {
        this.identifier = resourceLocation;
    }
}
