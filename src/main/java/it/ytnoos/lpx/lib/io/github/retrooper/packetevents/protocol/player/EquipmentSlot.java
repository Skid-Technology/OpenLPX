package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;

import org.jetbrains.annotations.Nullable;

public enum EquipmentSlot {
    MAIN_HAND(0, 0),
    OFF_HAND(0, 5),
    BOOTS(1, 1),
    LEGGINGS(2, 2),
    CHEST_PLATE(3, 3),
    HELMET(4, 4),
    BODY(0, 6),
    SADDLE(0, 7);

    private static final EquipmentSlot[] VALUES = values();

    private static final EquipmentSlot[] BY_COMPONENT_ID = new EquipmentSlot[VALUES.length];

    private final byte legacyId;

    private final byte componentId;

    EquipmentSlot(int value, int value2) {
        this.legacyId = (byte) value;
        this.componentId = (byte) value2;
    }

    public int getComponentId(ServerVersion serverVersion) {
        return this.componentId;
    }

    public int getId(ServerVersion serverVersion) {
        return this.getId(serverVersion.toClientVersion());
    }

    public int getId(ClientVersion clientVersion) {
        return clientVersion.isOlderThan(ClientVersion.V_1_9) ? this.legacyId : this.ordinal();
    }

    @Nullable
    public static EquipmentSlot getById(ClientVersion clientVersion, int value) {
        if (clientVersion.isOlderThan(ClientVersion.V_1_9)) {
            EquipmentSlot[] equipmentSlots = VALUES;
            int value2 = VALUES.length;
            for (int index = 0; index < value2; index++) {
                EquipmentSlot equipmentSlot;
                if ((equipmentSlot = equipmentSlots[index]).getId(clientVersion) == value) {
                    return equipmentSlot;
                }
            }
            return null;
        } else {
            return VALUES[value];
        }
    }

    @Nullable
    public static EquipmentSlot getById(ServerVersion serverVersion, int value) {
        return getById(serverVersion.toClientVersion(), value);
    }

    public static EquipmentSlot getByComponentId(ServerVersion serverVersion, int value) {
        return BY_COMPONENT_ID[value];
    }

    static {
        EquipmentSlot[] equipmentSlots = VALUES;
        int value = VALUES.length;
        for (int index = 0; index < value; index++) {
            EquipmentSlot equipmentSlot = equipmentSlots[index];
            BY_COMPONENT_ID[equipmentSlot.componentId] = equipmentSlot;
        }
    }
}
