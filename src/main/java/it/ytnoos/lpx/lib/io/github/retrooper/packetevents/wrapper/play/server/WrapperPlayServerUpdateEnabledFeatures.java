package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.HashSet;
import java.util.Set;

public class WrapperPlayServerUpdateEnabledFeatures
        extends PacketWrapper<WrapperPlayServerUpdateEnabledFeatures> {

    private Set<ResourceLocation> features;

    public WrapperPlayServerUpdateEnabledFeatures(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUpdateEnabledFeatures(Set<ResourceLocation> features) {
        super(PacketType.Play.Server.UPDATE_ENABLED_FEATURES);
        this.features = features;
    }

    @Override
    public void read() {
        this.features = this.readCollection(HashSet::new, PacketWrapper::readIdentifier);
    }

    @Override
    public void write() {
        this.writeCollection(this.features, PacketWrapper::writeIdentifier);
    }

    public void copy(
            WrapperPlayServerUpdateEnabledFeatures wrapperPlayServerUpdateEnabledFeatures) {
        this.features = wrapperPlayServerUpdateEnabledFeatures.features;
    }

    public Set<ResourceLocation> getFeatures() {
        return this.features;
    }

    public void setFeatures(Set<ResourceLocation> features) {
        this.features = features;
    }
}
