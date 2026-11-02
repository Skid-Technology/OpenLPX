package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.InteractionHand;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class WrapperPlayClientAnimation extends PacketWrapper<WrapperPlayClientAnimation> {

    private InteractionHand interactionHand;

    public WrapperPlayClientAnimation(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientAnimation(InteractionHand interactionHand2) {
        super(PacketType.Play.Client.ANIMATION);
        this.interactionHand = interactionHand2;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.interactionHand = InteractionHand.getById(this.readVarInt());
        } else {
            this.interactionHand = InteractionHand.MAIN_HAND;
        }
    }

    public void copy(WrapperPlayClientAnimation wrapperPlayClientAnimation) {
        this.interactionHand = wrapperPlayClientAnimation.interactionHand;
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.writeVarInt(this.interactionHand.getId());
        }
    }

    public InteractionHand getHand() {
        return this.interactionHand;
    }

    public void setHand(InteractionHand interactionHand2) {
        this.interactionHand = interactionHand2;
    }
}
