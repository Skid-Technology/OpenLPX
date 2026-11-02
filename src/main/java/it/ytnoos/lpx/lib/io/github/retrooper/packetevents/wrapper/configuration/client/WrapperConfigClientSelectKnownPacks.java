package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.KnownPack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;

public class WrapperConfigClientSelectKnownPacks
        extends PacketWrapper<WrapperConfigClientSelectKnownPacks> {

    private List<KnownPack> knownPacks;

    public WrapperConfigClientSelectKnownPacks(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperConfigClientSelectKnownPacks(List<KnownPack> knownPacks) {
        super(PacketType.Configuration.Client.SELECT_KNOWN_PACKS);
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

    public void copy(WrapperConfigClientSelectKnownPacks wrapperConfigClientSelectKnownPacks) {
        this.knownPacks = wrapperConfigClientSelectKnownPacks.knownPacks;
    }

    public List<KnownPack> getKnownPacks() {
        return this.knownPacks;
    }

    public void setKnownPacks(List<KnownPack> knownPacks) {
        this.knownPacks = knownPacks;
    }
}
