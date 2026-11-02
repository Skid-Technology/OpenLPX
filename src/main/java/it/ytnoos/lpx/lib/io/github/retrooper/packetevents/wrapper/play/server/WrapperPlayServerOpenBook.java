package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.InteractionHand;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerOpenBook extends PacketWrapper<WrapperPlayServerOpenBook> {

    private InteractionHand hand;

    public WrapperPlayServerOpenBook(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerOpenBook(InteractionHand interactionHand) {
        super(PacketType.Play.Server.OPEN_BOOK);
        this.hand = interactionHand;
    }

    @Override
    public void read() {
        this.hand = InteractionHand.values()[this.readVarInt()];
    }

    @Override
    public void write() {
        this.writeVarInt(this.hand.ordinal());
    }

    public void copy(WrapperPlayServerOpenBook wrapperPlayServerOpenBook) {
        this.hand = wrapperPlayServerOpenBook.hand;
    }

    public InteractionHand getHand() {
        return this.hand;
    }

    public void setHand(InteractionHand interactionHand) {
        this.hand = interactionHand;
    }
}
