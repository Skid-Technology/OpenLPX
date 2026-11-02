package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientSetTestBlock extends PacketWrapper<WrapperPlayClientSetTestBlock> {

    private Vector3i position;

    private WrapperPlayClientSetTestBlock.TestBlockMode mode;

    private String message;

    public WrapperPlayClientSetTestBlock(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSetTestBlock(
            Vector3i vector3i,
            WrapperPlayClientSetTestBlock.TestBlockMode testBlockMode,
            String message) {
        super(PacketType.Play.Client.SET_TEST_BLOCK);
        this.position = vector3i;
        this.mode = testBlockMode;
        this.message = message;
    }

    @Override
    public void read() {
        this.position = this.readBlockPosition();
        this.mode = this.readEnum(WrapperPlayClientSetTestBlock.TestBlockMode.class);
        this.message = this.readString();
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.position);
        this.writeEnum(this.mode);
        this.writeString(this.message);
    }

    public void copy(WrapperPlayClientSetTestBlock wrapperPlayClientSetTestBlock) {
        this.position = wrapperPlayClientSetTestBlock.position;
        this.mode = wrapperPlayClientSetTestBlock.mode;
        this.message = wrapperPlayClientSetTestBlock.message;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    public WrapperPlayClientSetTestBlock.TestBlockMode getMode() {
        return this.mode;
    }

    public void setMode(WrapperPlayClientSetTestBlock.TestBlockMode testBlockMode) {
        this.mode = testBlockMode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum TestBlockMode {
        START,
        LOG,
        FAIL,
        ACCEPT
    }
}
