package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerDestroyEntities
        extends PacketWrapper<WrapperPlayServerDestroyEntities> {

    private int[] entityIDs;

    public WrapperPlayServerDestroyEntities(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDestroyEntities(int... entityIDs) {
        super(PacketType.Play.Server.DESTROY_ENTITIES);
        this.entityIDs = entityIDs;
    }

    public WrapperPlayServerDestroyEntities(int value) {
        super(PacketType.Play.Server.DESTROY_ENTITIES);
        this.entityIDs = new int[] {value};
    }

    @Override
    public void read() {
        if (this.serverVersion == ServerVersion.V_1_17) {
            this.entityIDs = new int[] {this.readVarInt()};
        } else if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            short value = this.readUnsignedByte();
            this.entityIDs = new int[value];
            for (int index = 0; index < value; index++) {
                this.entityIDs[index] = this.readInt();
            }
        } else {
            int value2 = this.readVarInt();
            this.entityIDs = new int[value2];
            for (int index2 = 0; index2 < value2; index2++) {
                this.entityIDs[index2] = this.readVarInt();
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion == ServerVersion.V_1_17) {
            this.writeVarInt(this.entityIDs[0]);
        } else if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeByte(this.entityIDs.length);
            int[] values = this.entityIDs;
            int value = this.entityIDs.length;
            for (int index = 0; index < value; index++) {
                int value2 = values[index];
                this.writeInt(value2);
            }
        } else {
            this.writeVarInt(this.entityIDs.length);
            int[] values2 = this.entityIDs;
            int value3 = this.entityIDs.length;
            for (int index2 = 0; index2 < value3; index2++) {
                int value4 = values2[index2];
                this.writeVarInt(value4);
            }
        }
    }

    public void copy(WrapperPlayServerDestroyEntities wrapperPlayServerDestroyEntities) {
        this.entityIDs = wrapperPlayServerDestroyEntities.entityIDs;
    }

    public int[] getEntityIds() {
        return this.entityIDs;
    }

    public void setEntityIds(int[] entityIDs) {
        this.entityIDs = entityIDs;
    }
}
