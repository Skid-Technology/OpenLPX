package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class WrapperPlayServerPlayerInfoRemove
        extends PacketWrapper<WrapperPlayServerPlayerInfoRemove> {

    private List<UUID> profileIds;

    public WrapperPlayServerPlayerInfoRemove(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerPlayerInfoRemove(List<UUID> profileIds) {
        super(PacketType.Play.Server.PLAYER_INFO_REMOVE);
        this.profileIds = profileIds;
    }

    public WrapperPlayServerPlayerInfoRemove(UUID... uUID) {
        super(PacketType.Play.Server.PLAYER_INFO_REMOVE);
        this.profileIds = new ArrayList<>();
        Collections.addAll(this.profileIds, uUID);
    }

    @Override
    public void read() {
        this.profileIds = this.readList(PacketWrapper::readUUID);
    }

    @Override
    public void write() {
        this.writeList(this.profileIds, PacketWrapper::writeUUID);
    }

    public void copy(WrapperPlayServerPlayerInfoRemove wrapperPlayServerPlayerInfoRemove) {
        this.profileIds = wrapperPlayServerPlayerInfoRemove.profileIds;
    }

    public List<UUID> getProfileIds() {
        return this.profileIds;
    }

    public void setProfileIds(List<UUID> profileIds) {
        this.profileIds = profileIds;
    }
}
