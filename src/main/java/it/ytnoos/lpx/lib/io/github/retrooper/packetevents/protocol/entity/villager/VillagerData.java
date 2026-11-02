package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.level.VillagerLevel;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.profession.VillagerProfession;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.profession.VillagerProfessions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.type.VillagerType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.type.VillagerTypes;

import org.jetbrains.annotations.Nullable;

public class VillagerData {

    private VillagerType type;

    private VillagerProfession profession;

    private int level;

    public VillagerData(
            VillagerType villagerType,
            VillagerProfession villagerProfession,
            VillagerLevel villagerLevel) {
        this(villagerType, villagerProfession, villagerLevel.getId());
    }

    public VillagerData(
            VillagerType villagerType, VillagerProfession villagerProfession, int level) {
        this.type = villagerType;
        this.profession = villagerProfession;
        this.level = level;
    }

    @Deprecated
    public VillagerData(int value, int value2, int value3) {
        this(VillagerTypes.getById(value), VillagerProfessions.getById(value2), value3);
    }

    public VillagerType getType() {
        return this.type;
    }

    public void setType(VillagerType villagerType) {
        this.type = villagerType;
    }

    public VillagerProfession getProfession() {
        return this.profession;
    }

    public void setProfession(VillagerProfession villagerProfession) {
        this.profession = villagerProfession;
    }

    public int getLevel() {
        return this.level;
    }

    @Nullable
    public VillagerLevel getVillagerLevel() {
        return VillagerLevel.getById(this.level);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLevel(VillagerLevel villagerLevel) {
        this.level = villagerLevel.getId();
    }
}
