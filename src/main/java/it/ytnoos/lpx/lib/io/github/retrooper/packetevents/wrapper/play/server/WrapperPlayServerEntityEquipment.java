package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.Equipment;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.List;

public class WrapperPlayServerEntityEquipment
        extends PacketWrapper<WrapperPlayServerEntityEquipment> {

    private int entityId;

    private List<Equipment> equipment;

    public WrapperPlayServerEntityEquipment(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityEquipment(int entityId, List<Equipment> equipment) {
        super(PacketType.Play.Server.ENTITY_EQUIPMENT);
        this.entityId = entityId;
        this.equipment = equipment;
    }

    @Override
    public void read() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.entityId = this.readInt();
        } else {
            this.entityId = this.readVarInt();
        }
        this.readEquipment();
    }

    protected void readEquipment() {
        this.equipment = new ArrayList<>();
        byte value;
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            do {
                value = this.readByte();
                EquipmentSlot byId = EquipmentSlot.getById(this.serverVersion, value & 127);
                ItemStack itemStack = this.readItemStack();
                this.equipment.add(new Equipment(byId, itemStack));
            } while ((value & -128) != 0);
        } else {
            EquipmentSlot equipmentSlot;
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                equipmentSlot = EquipmentSlot.getById(this.serverVersion, this.readVarInt());
            } else {
                equipmentSlot = EquipmentSlot.getById(this.serverVersion, this.readShort());
            }
            this.equipment.add(new Equipment(equipmentSlot, this.readItemStack()));
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeInt(this.entityId);
        } else {
            this.writeVarInt(this.entityId);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            int size2 = this.equipment.size();
            for (int index = 0; index < size2; index++) {
                Equipment equipment2 = this.equipment.get(index);
                boolean matches = index == size2 - 1;
                this.writeByte(
                        matches
                                ? equipment2.getSlot().getId(this.serverVersion)
                                : equipment2.getSlot().getId(this.serverVersion) | -128);
                this.writeItemStack(equipment2.getItem());
            }
        } else {
            Equipment equipment3 = this.equipment.get(0);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                this.writeVarInt(equipment3.getSlot().getId(this.serverVersion));
            } else {
                this.writeShort(equipment3.getSlot().getId(this.serverVersion));
            }
            this.writeItemStack(equipment3.getItem());
        }
    }

    public void copy(WrapperPlayServerEntityEquipment wrapperPlayServerEntityEquipment) {
        this.entityId = wrapperPlayServerEntityEquipment.entityId;
        this.equipment = wrapperPlayServerEntityEquipment.equipment;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public List<Equipment> getEquipment() {
        return this.equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }
}
