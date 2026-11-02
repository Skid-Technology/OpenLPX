package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerAddTransientBlock
        extends PacketWrapper<WrapperPlayServerAddTransientBlock> {

    private Vector3i position;

    private WrappedBlockState state;

    public WrapperPlayServerAddTransientBlock(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerAddTransientBlock(
            Vector3i vector3i, WrappedBlockState wrappedBlockState) {
        super(PacketType.Play.Server.ADD_TRANSIENT_BLOCK);
        this.position = vector3i;
        this.state = wrappedBlockState;
    }

    @Override
    public void read() {
        this.position = this.readBlockPosition();
        this.state = WrappedBlockState.read(this);
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.position);
        WrappedBlockState.write(this, this.state);
    }

    public void copy(WrapperPlayServerAddTransientBlock wrapperPlayServerAddTransientBlock) {
        this.position = wrapperPlayServerAddTransientBlock.position;
        this.state = wrapperPlayServerAddTransientBlock.state;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    public WrappedBlockState getState() {
        return this.state;
    }

    public void setState(WrappedBlockState wrappedBlockState) {
        this.state = wrappedBlockState;
    }
}
