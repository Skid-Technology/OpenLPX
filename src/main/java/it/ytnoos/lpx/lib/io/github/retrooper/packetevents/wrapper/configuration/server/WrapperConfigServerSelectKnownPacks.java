package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.KnownPack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;

public class WrapperConfigServerSelectKnownPacks
        extends PacketWrapper<WrapperConfigServerSelectKnownPacks> {

    private List<KnownPack> knownPacks;

    public WrapperConfigServerSelectKnownPacks(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerSelectKnownPacks(List<KnownPack> knownPacks) {
        super(PacketType.Configuration.Server.SELECT_KNOWN_PACKS);
        this.knownPacks = knownPacks;
    }

    @Override
    public void read() {
        this.knownPacks = this.readList(PacketWrapper::readKnownPack);
    }

    @Override
    public void write() {
        this.writeList(this.knownPacks, PacketWrapper::writeKnownPack);
    }

    public void copy(WrapperConfigServerSelectKnownPacks wrapperConfigServerSelectKnownPacks) {
        this.knownPacks = wrapperConfigServerSelectKnownPacks.knownPacks;
    }

    public List<KnownPack> getKnownPacks() {
        return this.knownPacks;
    }

    public void setKnownPacks(List<KnownPack> knownPacks) {
        this.knownPacks = knownPacks;
    }
}
