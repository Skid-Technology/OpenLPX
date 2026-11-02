package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.InteractionHand;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientUseItem extends PacketWrapper<WrapperPlayClientUseItem> {

    private InteractionHand hand;

    private int sequence;

    private float yaw;

    private float pitch;

    public WrapperPlayClientUseItem(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientUseItem(InteractionHand interactionHand) {
        this(interactionHand, 0);
    }

    public WrapperPlayClientUseItem(InteractionHand interactionHand, int value) {
        this(interactionHand, value, 0.0F, 0.0F);
    }

    public WrapperPlayClientUseItem(
            InteractionHand interactionHand, int sequence, float yaw, float pitch) {
        super(PacketType.Play.Client.USE_ITEM);
        this.hand = interactionHand;
        this.sequence = sequence;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void read() {
        this.hand = InteractionHand.getById(this.readVarInt());
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.sequence = this.readVarInt();
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21)) {
                this.yaw = this.readFloat();
                this.pitch = this.readFloat();
            }
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.hand.getId());
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.writeVarInt(this.sequence);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21)) {
                this.writeFloat(this.yaw);
                this.writeFloat(this.pitch);
            }
        }
    }

    public void copy(WrapperPlayClientUseItem wrapperPlayClientUseItem) {
        this.hand = wrapperPlayClientUseItem.hand;
        this.sequence = wrapperPlayClientUseItem.sequence;
        this.yaw = wrapperPlayClientUseItem.yaw;
        this.pitch = wrapperPlayClientUseItem.pitch;
    }

    public InteractionHand getHand() {
        return this.hand;
    }

    public void setHand(InteractionHand interactionHand) {
        this.hand = interactionHand;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
