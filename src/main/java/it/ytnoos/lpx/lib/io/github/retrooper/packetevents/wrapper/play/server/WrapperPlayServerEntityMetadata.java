package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityMetadataProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;

public class WrapperPlayServerEntityMetadata
        extends PacketWrapper<WrapperPlayServerEntityMetadata> {

    private int entityID;

    private List<EntityData<?>> entityMetadata;

    public WrapperPlayServerEntityMetadata(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityMetadata(int entityID, List<EntityData<?>> entityMetadata) {
        super(PacketType.Play.Server.ENTITY_METADATA);
        this.entityID = entityID;
        this.entityMetadata = entityMetadata;
    }

    public WrapperPlayServerEntityMetadata(
            int value, EntityMetadataProvider entityMetadataProvider) {
        this(
                value,
                entityMetadataProvider.entityData(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion()));
    }

    @Override
    public void read() {
        this.entityID =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)
                        ? this.readVarInt()
                        : this.readInt();
        this.entityMetadata = this.readEntityMetadata();
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeVarInt(this.entityID);
        } else {
            this.writeInt(this.entityID);
        }
        this.writeEntityMetadata(this.entityMetadata);
    }

    public void copy(WrapperPlayServerEntityMetadata wrapperPlayServerEntityMetadata) {
        this.entityID = wrapperPlayServerEntityMetadata.entityID;
        this.entityMetadata = wrapperPlayServerEntityMetadata.entityMetadata;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public List<EntityData<?>> getEntityMetadata() {
        return this.entityMetadata;
    }

    public void setEntityMetadata(List<EntityData<?>> entityMetadata) {
        this.entityMetadata = entityMetadata;
    }

    public void setEntityMetadata(EntityMetadataProvider entityMetadataProvider) {
        this.entityMetadata =
                entityMetadataProvider.entityData(this.serverVersion.toClientVersion());
    }
}
