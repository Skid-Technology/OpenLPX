package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerOpenHorseWindow
        extends PacketWrapper<WrapperPlayServerOpenHorseWindow> {

    private int windowId;

    private int slotCount;

    private int entityId;

    public WrapperPlayServerOpenHorseWindow(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerOpenHorseWindow(int windowId, int slotCount, int entityId) {
        super(PacketType.Play.Server.OPEN_HORSE_WINDOW);
        this.windowId = windowId;
        this.slotCount = slotCount;
        this.entityId = entityId;
    }

    @Override
    public void read() {
        this.windowId = this.readContainerId();
        this.slotCount = this.readVarInt();
        this.entityId = this.readInt();
    }

    @Override
    public void write() {
        this.writeContainerId(this.windowId);
        this.writeVarInt(this.slotCount);
        this.writeInt(this.entityId);
    }

    public void copy(WrapperPlayServerOpenHorseWindow wrapperPlayServerOpenHorseWindow) {
        this.windowId = wrapperPlayServerOpenHorseWindow.windowId;
        this.slotCount = wrapperPlayServerOpenHorseWindow.slotCount;
        this.entityId = wrapperPlayServerOpenHorseWindow.entityId;
    }

    public int getWindowId() {
        return this.windowId;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
    }

    public int getSlotCount() {
        return this.slotCount;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount = slotCount;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
