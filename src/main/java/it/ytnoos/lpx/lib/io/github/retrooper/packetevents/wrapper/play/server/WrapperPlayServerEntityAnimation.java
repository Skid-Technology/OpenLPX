package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Arrays;
import java.util.Comparator;

public class WrapperPlayServerEntityAnimation
        extends PacketWrapper<WrapperPlayServerEntityAnimation> {

    private int entityID;

    private WrapperPlayServerEntityAnimation.EntityAnimationType type;

    public WrapperPlayServerEntityAnimation(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityAnimation(
            int entityID,
            WrapperPlayServerEntityAnimation.EntityAnimationType entityAnimationType) {
        super(PacketType.Play.Server.ENTITY_ANIMATION);
        this.entityID = entityID;
        this.type = entityAnimationType;
    }

    @Override
    public void read() {
        this.entityID = this.readVarInt();
        this.type =
                WrapperPlayServerEntityAnimation.EntityAnimationType.getById(
                        this.serverVersion, this.readUnsignedByte());
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityID);
        this.writeByte(this.type.getId(this.serverVersion));
    }

    public void copy(WrapperPlayServerEntityAnimation wrapperPlayServerEntityAnimation) {
        this.entityID = wrapperPlayServerEntityAnimation.entityID;
        this.type = wrapperPlayServerEntityAnimation.type;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public WrapperPlayServerEntityAnimation.EntityAnimationType getType() {
        return this.type;
    }

    public void setType(WrapperPlayServerEntityAnimation.EntityAnimationType entityAnimationType) {
        this.type = entityAnimationType;
    }

    public enum EntityAnimationType {
        @Obsolete
        SWING_MAIN_ARM(0, -1),
        @Obsolete
        HURT(1, -1),
        WAKE_UP(2, 0),
        @Obsolete
        SWING_OFF_HAND(3, -1),
        CRITICAL_HIT(4, 1),
        MAGIC_CRITICAL_HIT(5, 2);

        private static final WrapperPlayServerEntityAnimation.EntityAnimationType[] LEGACY_VALUES =
                Arrays.stream(values())
                        .filter(result -> result.legacyId != -1)
                        .sorted(Comparator.comparingInt(result -> result.legacyId))
                        .toArray(WrapperPlayServerEntityAnimation.EntityAnimationType[]::new);

        private static final WrapperPlayServerEntityAnimation.EntityAnimationType[] VALUES =
                Arrays.stream(values())
                        .filter(result -> result.id != -1)
                        .sorted(Comparator.comparingInt(result -> result.id))
                        .toArray(WrapperPlayServerEntityAnimation.EntityAnimationType[]::new);

        private final int legacyId;

        private final int id;

        EntityAnimationType(int legacyId, int id) {
            this.legacyId = legacyId;
            this.id = id;
        }

        public static WrapperPlayServerEntityAnimation.EntityAnimationType getById(
                ServerVersion serverVersion, int value) {
            return (serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)
                            ? VALUES
                            : LEGACY_VALUES)
                    [value];
        }

        @Deprecated
        public static WrapperPlayServerEntityAnimation.EntityAnimationType getById(int value) {
            return values()[value];
        }

        public int getId(ServerVersion serverVersion) {
            int value;
            if ((value =
                            serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)
                                    ? this.id
                                    : this.legacyId)
                    < 0) {
                throw new IllegalStateException(this + " has no id on " + serverVersion);
            } else {
                return value;
            }
        }
    }
}
