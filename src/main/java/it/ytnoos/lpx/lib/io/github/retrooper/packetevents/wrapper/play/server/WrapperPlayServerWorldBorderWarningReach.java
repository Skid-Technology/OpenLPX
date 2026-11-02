package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerWorldBorderWarningReach
        extends PacketWrapper<WrapperPlayServerWorldBorderWarningReach> {

    private int warningBlocks;

    public WrapperPlayServerWorldBorderWarningReach(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerWorldBorderWarningReach(int warningBlocks) {
        super(PacketType.Play.Server.WORLD_BORDER_WARNING_REACH);
        this.warningBlocks = warningBlocks;
    }

    @Override
    public void read() {
        this.warningBlocks = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.warningBlocks);
    }

    public void copy(
            WrapperPlayServerWorldBorderWarningReach wrapperPlayServerWorldBorderWarningReach) {
        this.warningBlocks = wrapperPlayServerWorldBorderWarningReach.warningBlocks;
    }

    public int getWarningBlocks() {
        return this.warningBlocks;
    }

    public void setWarningBlocks(int warningBlocks) {
        this.warningBlocks = warningBlocks;
    }
}
