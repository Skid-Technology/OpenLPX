package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class WrapperPlayServerGameTestHighlightPos
        extends PacketWrapper<WrapperPlayServerGameTestHighlightPos> {

    private Vector3i absolutePos;

    private Vector3i relativePos;

    public WrapperPlayServerGameTestHighlightPos(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerGameTestHighlightPos(Vector3i vector3i, Vector3i vector3i2) {
        super(PacketType.Play.Server.GAME_TEST_HIGHLIGHT_POS);
        this.absolutePos = vector3i;
        this.relativePos = vector3i2;
    }

    @Override
    public void read() {
        this.absolutePos = this.readBlockPosition();
        this.relativePos = this.readBlockPosition();
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.absolutePos);
        this.writeBlockPosition(this.relativePos);
    }

    public void copy(WrapperPlayServerGameTestHighlightPos wrapperPlayServerGameTestHighlightPos) {
        this.absolutePos = wrapperPlayServerGameTestHighlightPos.absolutePos;
        this.relativePos = wrapperPlayServerGameTestHighlightPos.relativePos;
    }

    public Vector3i getAbsolutePos() {
        return this.absolutePos;
    }

    public void setAbsolutePos(Vector3i vector3i) {
        this.absolutePos = vector3i;
    }

    public Vector3i getRelativePos() {
        return this.relativePos;
    }

    public void setRelativePos(Vector3i vector3i) {
        this.relativePos = vector3i;
    }
}
