package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.type.VillagerType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.type.VillagerTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class VillagerVariantComponent {

    private VillagerType villagerType;

    public VillagerVariantComponent(VillagerType villagerType2) {
        this.villagerType = villagerType2;
    }

    public static VillagerVariantComponent read(PacketWrapper<?> packetWrapper) {
        VillagerType villagerType = packetWrapper.readMappedEntity(VillagerTypes.getRegistry());
        return new VillagerVariantComponent(villagerType);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, VillagerVariantComponent villagerVariantComponent) {
        packetWrapper.writeMappedEntity(villagerVariantComponent.villagerType);
    }

    public VillagerType getVillagerType() {
        return this.villagerType;
    }

    public void setVillagerType(VillagerType villagerType2) {
        this.villagerType = villagerType2;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof VillagerVariantComponent)) {
            return false;
        }
        VillagerVariantComponent villagerVariantComponent = (VillagerVariantComponent) result;
        return this.villagerType.equals(villagerVariantComponent.villagerType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.villagerType);
    }
}
