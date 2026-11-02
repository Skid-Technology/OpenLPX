package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.DebugSubscription;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class WrapperPlayServerDebugBlockValue
        extends PacketWrapper<WrapperPlayServerDebugBlockValue> {

    private Vector3i blockPos;

    private DebugSubscription.Update<?> update;

    public WrapperPlayServerDebugBlockValue(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDebugBlockValue(
            Vector3i vector3i, DebugSubscription.Update<?> update2) {
        super(PacketType.Play.Server.DEBUG_BLOCK_VALUE);
        this.blockPos = vector3i;
        this.update = update2;
    }

    @Override
    public void read() {
        this.blockPos = this.readBlockPosition();
        this.update = DebugSubscription.Update.read(this);
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.blockPos);
        DebugSubscription.Update.write(this, this.update);
    }

    public void copy(WrapperPlayServerDebugBlockValue wrapperPlayServerDebugBlockValue) {
        this.blockPos = wrapperPlayServerDebugBlockValue.blockPos;
        this.update = wrapperPlayServerDebugBlockValue.update;
    }

    public Vector3i getBlockPos() {
        return this.blockPos;
    }

    public void setBlockPos(Vector3i vector3i) {
        this.blockPos = vector3i;
    }

    public DebugSubscription.Update<?> getUpdate() {
        return this.update;
    }

    public void setUpdate(DebugSubscription.Update<?> update2) {
        this.update = update2;
    }
}
