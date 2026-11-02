package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.InteractionHand;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.LpVector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3f;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayClientInteractEntity
        extends PacketWrapper<WrapperPlayClientInteractEntity> {

    private int entityId;

    private WrapperPlayClientInteractEntity.InteractAction action;

    @Nullable private Vector3d location;

    private InteractionHand hand;

    private boolean sneaking;

    public WrapperPlayClientInteractEntity(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    @Deprecated
    public WrapperPlayClientInteractEntity(
            int value,
            WrapperPlayClientInteractEntity.InteractAction interactAction,
            InteractionHand interactionHand,
            Optional<Vector3f> optional,
            Optional<Boolean> optional2) {
        this(
                value,
                interactAction,
                optional.<Vector3d>map(Vector3d::new).orElse(null),
                interactionHand,
                optional2.orElse(false));
    }

    public WrapperPlayClientInteractEntity(
            int value, InteractionHand interactionHand, Vector3d vector3d, boolean matches) {
        this(
                value,
                WrapperPlayClientInteractEntity.InteractAction.INTERACT_AT,
                vector3d,
                interactionHand,
                matches);
    }

    @Obsolete
    public WrapperPlayClientInteractEntity(
            int entityId,
            WrapperPlayClientInteractEntity.InteractAction interactAction,
            @Nullable Vector3d vector3d,
            InteractionHand interactionHand,
            boolean sneaking) {
        super(PacketType.Play.Client.INTERACT_ENTITY);
        this.entityId = entityId;
        this.action = interactAction;
        this.location = vector3d;
        this.hand = interactionHand;
        this.sneaking = sneaking;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_1)) {
            this.entityId = this.readVarInt();
            this.hand = this.readEnum(InteractionHand.values());
            this.location = LpVector3d.read(this);
            this.sneaking = this.readBoolean();
            this.action = WrapperPlayClientInteractEntity.InteractAction.INTERACT_AT;
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.entityId = this.readVarInt();
            this.action = this.readEnum(WrapperPlayClientInteractEntity.InteractAction.values());
            if (this.action == WrapperPlayClientInteractEntity.InteractAction.INTERACT_AT) {
                this.location = new Vector3d(Vector3f.read(this));
            } else {
                this.location = Vector3d.zero();
            }
            if (!this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                    || this.action != WrapperPlayClientInteractEntity.InteractAction.INTERACT
                            && this.action
                                    != WrapperPlayClientInteractEntity.InteractAction.INTERACT_AT) {
                this.hand = InteractionHand.MAIN_HAND;
            } else {
                this.hand = this.readEnum(InteractionHand.values());
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
                this.sneaking = this.readBoolean();
            }
        } else {
            this.entityId = this.readInt();
            this.action = WrapperPlayClientInteractEntity.InteractAction.VALUES[this.readByte()];
            this.hand = InteractionHand.MAIN_HAND;
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_1)) {
            this.writeVarInt(this.entityId);
            this.writeEnum(this.hand);
            LpVector3d.write(this, this.location != null ? this.location : Vector3d.zero());
            this.writeBoolean(this.sneaking);
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeVarInt(this.entityId);
            this.writeEnum(this.action);
            if (this.action == WrapperPlayClientInteractEntity.InteractAction.INTERACT_AT) {
                Vector3f.write(
                        this,
                        new Vector3f(this.location != null ? this.location : Vector3d.zero()));
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                    && (this.action == WrapperPlayClientInteractEntity.InteractAction.INTERACT
                            || this.action
                                    == WrapperPlayClientInteractEntity.InteractAction
                                            .INTERACT_AT)) {
                this.writeEnum(this.hand);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
                this.writeBoolean(this.sneaking);
            }
        } else {
            this.writeInt(this.entityId);
            this.writeByte(this.action.ordinal());
        }
    }

    public void copy(WrapperPlayClientInteractEntity wrapperPlayClientInteractEntity) {
        this.entityId = wrapperPlayClientInteractEntity.entityId;
        this.action = wrapperPlayClientInteractEntity.action;
        this.location = wrapperPlayClientInteractEntity.location;
        this.hand = wrapperPlayClientInteractEntity.hand;
        this.sneaking = wrapperPlayClientInteractEntity.sneaking;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Obsolete
    public WrapperPlayClientInteractEntity.InteractAction getAction() {
        return this.action;
    }

    @Obsolete
    public void setAction(WrapperPlayClientInteractEntity.InteractAction interactAction) {
        this.action = interactAction;
    }

    public InteractionHand getHand() {
        return this.hand;
    }

    public void setHand(InteractionHand interactionHand) {
        this.hand = interactionHand;
    }

    @UnknownNullability
    public Vector3d getLocation() {
        return this.location;
    }

    public void setLocation(@UnknownNullability Vector3d vector3d) {
        this.location = vector3d;
    }

    @Deprecated
    public Optional<Vector3f> getTarget() {
        return Optional.ofNullable(this.location).map(Vector3f::new);
    }

    @Deprecated
    public void setTarget(Optional<Vector3f> optional) {
        this.location = optional.<Vector3d>map(Vector3d::new).orElse(null);
    }

    public Optional<Boolean> isSneaking() {
        return Optional.of(this.sneaking);
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    @Deprecated
    public void setSneaking(Optional<Boolean> optional) {
        this.sneaking = optional.orElse(false);
    }

    @Obsolete
    public enum InteractAction {
        INTERACT,
        ATTACK,
        INTERACT_AT;

        @Deprecated
        public static final WrapperPlayClientInteractEntity.InteractAction[] VALUES = values();
    }
}
