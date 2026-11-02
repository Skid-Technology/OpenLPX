package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.TestInstanceData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientTestInstanceBlockAction
        extends PacketWrapper<WrapperPlayClientTestInstanceBlockAction> {

    private Vector3i position;

    private WrapperPlayClientTestInstanceBlockAction.Action action;

    private TestInstanceData data;

    public WrapperPlayClientTestInstanceBlockAction(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientTestInstanceBlockAction(
            Vector3i vector3i,
            WrapperPlayClientTestInstanceBlockAction.Action action2,
            TestInstanceData testInstanceData) {
        super(PacketType.Play.Client.TEST_INSTANCE_BLOCK_ACTION);
        this.position = vector3i;
        this.action = action2;
        this.data = testInstanceData;
    }

    @Override
    public void read() {
        this.position = this.readBlockPosition();
        this.action = this.readEnum(WrapperPlayClientTestInstanceBlockAction.Action.class);
        this.data = TestInstanceData.read(this);
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.position);
        this.writeEnum(this.action);
        TestInstanceData.write(this, this.data);
    }

    public void copy(
            WrapperPlayClientTestInstanceBlockAction wrapperPlayClientTestInstanceBlockAction) {
        this.position = wrapperPlayClientTestInstanceBlockAction.position;
        this.action = wrapperPlayClientTestInstanceBlockAction.action;
        this.data = wrapperPlayClientTestInstanceBlockAction.data;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    public WrapperPlayClientTestInstanceBlockAction.Action getAction() {
        return this.action;
    }

    public void setAction(WrapperPlayClientTestInstanceBlockAction.Action action2) {
        this.action = action2;
    }

    public TestInstanceData getData() {
        return this.data;
    }

    public void setData(TestInstanceData testInstanceData) {
        this.data = testInstanceData;
    }

    public enum Action {
        INIT,
        QUERY,
        SET,
        RESET,
        SAVE,
        EXPORT,
        RUN
    }
}
