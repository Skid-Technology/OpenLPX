package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.HashSet;
import java.util.Set;

public class WrapperConfigServerUpdateEnabledFeatures
        extends PacketWrapper<WrapperConfigServerUpdateEnabledFeatures> {

    private Set<ResourceLocation> features;

    public WrapperConfigServerUpdateEnabledFeatures(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerUpdateEnabledFeatures(Set<ResourceLocation> features) {
        super(PacketType.Configuration.Server.UPDATE_ENABLED_FEATURES);
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
            WrapperConfigServerUpdateEnabledFeatures wrapperConfigServerUpdateEnabledFeatures) {
        this.features = wrapperConfigServerUpdateEnabledFeatures.features;
    }

    public Set<ResourceLocation> getFeatures() {
        return this.features;
    }

    public void setFeatures(Set<ResourceLocation> features) {
        this.features = features;
    }
}
