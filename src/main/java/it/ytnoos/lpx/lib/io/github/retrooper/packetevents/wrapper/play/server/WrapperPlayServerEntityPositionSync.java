package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.EntityPositionData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath.LinearPositionPath;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath.PositionPath;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayServerEntityPositionSync
        extends PacketWrapper<WrapperPlayServerEntityPositionSync> {

    private int id;

    private EntityPositionData values;

    private PositionPath position;

    private float yRot;

    private float xRot;

    private boolean onGround;

    public WrapperPlayServerEntityPositionSync(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Obsolete
    public WrapperPlayServerEntityPositionSync(
            int id, EntityPositionData entityPositionData, boolean onGround) {
        super(PacketType.Play.Server.ENTITY_POSITION_SYNC);
        this.id = id;
        this.values = entityPositionData;
        this.onGround = onGround;
        this.position = new LinearPositionPath(entityPositionData.getPosition());
        this.yRot = entityPositionData.getYaw();
        this.xRot = entityPositionData.getPitch();
    }

    public WrapperPlayServerEntityPositionSync(
            int id, PositionPath positionPath, float yRot, float xRot, boolean onGround) {
        super(PacketType.Play.Server.ENTITY_POSITION_SYNC);
        this.id = id;
        this.position = positionPath;
        this.yRot = yRot;
        this.xRot = xRot;
        this.onGround = onGround;
    }

    @Override
    public void read() {
        this.id = this.readVarInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            this.position = PositionPath.read(this);
            this.yRot = this.readFloat();
            this.xRot = this.readFloat();
        } else {
            this.setValues(EntityPositionData.read(this));
        }
        this.onGround = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeVarInt(this.id);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            PositionPath.write(this, this.getPosition());
            this.writeFloat(this.yRot);
            this.writeFloat(this.xRot);
        } else {
            EntityPositionData.write(this, this.getValues());
        }
        this.writeBoolean(this.onGround);
    }

    public void copy(WrapperPlayServerEntityPositionSync wrapperPlayServerEntityPositionSync) {
        this.id = wrapperPlayServerEntityPositionSync.id;
        this.values = wrapperPlayServerEntityPositionSync.values;
        this.position = wrapperPlayServerEntityPositionSync.position;
        this.yRot = wrapperPlayServerEntityPositionSync.yRot;
        this.xRot = wrapperPlayServerEntityPositionSync.xRot;
        this.onGround = wrapperPlayServerEntityPositionSync.onGround;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EntityPositionData getValues() {
        if (this.values == null) {
            this.values =
                    new EntityPositionData(
                            this.getPosition().getEndPosition(),
                            Vector3d.zero(),
                            this.getYRot(),
                            this.getXRot());
        }
        return this.values;
    }

    public void setValues(EntityPositionData entityPositionData) {
        this.values = entityPositionData;
        this.yRot = entityPositionData.getYaw();
        this.xRot = entityPositionData.getPitch();
    }

    public PositionPath getPosition() {
        if (this.position == null) {
            this.position = new LinearPositionPath(this.getValues().getPosition());
        }
        return this.position;
    }

    public void setPosition(PositionPath positionPath) {
        this.position = positionPath;
    }

    public float getYRot() {
        return this.yRot;
    }

    public void setYRot(float yRot) {
        this.yRot = yRot;
    }

    public float getXRot() {
        return this.xRot;
    }

    public void setXRot(float xRot) {
        this.xRot = xRot;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
