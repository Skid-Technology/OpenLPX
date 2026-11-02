package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WrapperConfigServerResourcePackRemove
        extends PacketWrapper<WrapperConfigServerResourcePackRemove> {

    @Nullable private UUID packId;

    public WrapperConfigServerResourcePackRemove(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerResourcePackRemove(@Nullable UUID packId) {
        super(PacketType.Configuration.Server.RESOURCE_PACK_REMOVE);
        this.packId = packId;
    }

    @Override
    public void read() {
        this.packId = this.readOptional(PacketWrapper::readUUID);
    }

    @Override
    public void write() {
        this.writeOptional(this.packId, PacketWrapper::writeUUID);
    }

    public void copy(WrapperConfigServerResourcePackRemove wrapperConfigServerResourcePackRemove) {
        this.packId = wrapperConfigServerResourcePackRemove.packId;
    }

    @Nullable
    public UUID getPackId() {
        return this.packId;
    }

    public void setPackId(@Nullable UUID packId) {
        this.packId = packId;
    }
}
