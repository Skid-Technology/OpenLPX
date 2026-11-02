package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemSwingAnimation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.InteractionHand;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerSwingAnimation
        extends PacketWrapper<WrapperPlayServerSwingAnimation> {

    private int entityId;

    private InteractionHand hand;

    private ItemSwingAnimation animation;

    public WrapperPlayServerSwingAnimation(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSwingAnimation(
            int entityId, InteractionHand interactionHand, ItemSwingAnimation itemSwingAnimation) {
        super(PacketType.Play.Server.SWING_ANIMATION);
        this.entityId = entityId;
        this.hand = interactionHand;
        this.animation = itemSwingAnimation;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        this.hand = this.readEnum(InteractionHand.class);
        this.animation = ItemSwingAnimation.read(this);
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        this.writeEnum(this.hand);
        ItemSwingAnimation.write(this, this.animation);
    }

    public void copy(WrapperPlayServerSwingAnimation wrapperPlayServerSwingAnimation) {
        this.entityId = wrapperPlayServerSwingAnimation.entityId;
        this.hand = wrapperPlayServerSwingAnimation.hand;
        this.animation = wrapperPlayServerSwingAnimation.animation;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public InteractionHand getHand() {
        return this.hand;
    }

    public void setHand(InteractionHand interactionHand) {
        this.hand = interactionHand;
    }

    public ItemSwingAnimation getAnimation() {
        return this.animation;
    }

    public void setAnimation(ItemSwingAnimation itemSwingAnimation) {
        this.animation = itemSwingAnimation;
    }
}
