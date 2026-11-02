package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WrapperPlayServerResourcePackRemove
        extends PacketWrapper<WrapperPlayServerResourcePackRemove> {

    @Nullable private UUID packId;

    public WrapperPlayServerResourcePackRemove(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerResourcePackRemove(@Nullable UUID packId) {
        super(PacketType.Play.Server.RESOURCE_PACK_REMOVE);
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

    public void copy(WrapperPlayServerResourcePackRemove wrapperPlayServerResourcePackRemove) {
        this.packId = wrapperPlayServerResourcePackRemove.packId;
    }

    @Nullable
    public UUID getPackId() {
        return this.packId;
    }

    public void setPackId(@Nullable UUID packId) {
        this.packId = packId;
    }
}
