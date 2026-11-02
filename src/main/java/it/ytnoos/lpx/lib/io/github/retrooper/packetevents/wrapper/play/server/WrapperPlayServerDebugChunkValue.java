package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.DebugSubscription;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector2i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class WrapperPlayServerDebugChunkValue
        extends PacketWrapper<WrapperPlayServerDebugChunkValue> {

    private Vector2i chunkPos;

    private DebugSubscription.Update<?> update;

    public WrapperPlayServerDebugChunkValue(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDebugChunkValue(
            Vector2i vector2i, DebugSubscription.Update<?> update2) {
        super(PacketType.Play.Server.DEBUG_CHUNK_VALUE);
        this.chunkPos = vector2i;
        this.update = update2;
    }

    @Override
    public void read() {
        this.chunkPos = Vector2i.read(this);
        this.update = DebugSubscription.Update.read(this);
    }

    @Override
    public void write() {
        Vector2i.write(this, this.chunkPos);
        DebugSubscription.Update.write(this, this.update);
    }

    public void copy(WrapperPlayServerDebugChunkValue wrapperPlayServerDebugChunkValue) {
        this.chunkPos = wrapperPlayServerDebugChunkValue.chunkPos;
        this.update = wrapperPlayServerDebugChunkValue.update;
    }

    public Vector2i getChunkPos() {
        return this.chunkPos;
    }

    public void setChunkPos(Vector2i vector2i) {
        this.chunkPos = vector2i;
    }

    public DebugSubscription.Update<?> getUpdate() {
        return this.update;
    }

    public void setUpdate(DebugSubscription.Update<?> update2) {
        this.update = update2;
    }
}
