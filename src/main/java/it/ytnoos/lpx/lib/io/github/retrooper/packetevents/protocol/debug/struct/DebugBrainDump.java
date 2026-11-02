package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Set;

@NullMarked
public final class DebugBrainDump {

    private final String name;

    private final String profession;

    private final int xp;

    private final float health;

    private final float maxHealth;

    private final String inventory;

    private final boolean wantsGolem;

    private final int angerLevel;

    private final List<String> activities;

    private final List<String> behaviors;

    private final List<String> memories;

    private final List<String> gossips;

    private final Set<Vector3i> pois;

    private final Set<Vector3i> potentialPois;

    public DebugBrainDump(
            String name,
            String profession,
            int xp,
            float health,
            float maxHealth,
            String inventory,
            boolean wantsGolem,
            int angerLevel,
            List<String> activities,
            List<String> behaviors,
            List<String> memories,
            List<String> gossips,
            Set<Vector3i> pois,
            Set<Vector3i> potentialPois) {
        this.name = name;
        this.profession = profession;
        this.xp = xp;
        this.health = health;
        this.maxHealth = maxHealth;
        this.inventory = inventory;
        this.wantsGolem = wantsGolem;
        this.angerLevel = angerLevel;
        this.activities = activities;
        this.behaviors = behaviors;
        this.memories = memories;
        this.gossips = gossips;
        this.pois = pois;
        this.potentialPois = potentialPois;
    }

    public static DebugBrainDump read(PacketWrapper<?> packetWrapper) {
        String text = packetWrapper.readString();
        String text2 = packetWrapper.readString();
        int value = packetWrapper.readInt();
        float value2 = packetWrapper.readFloat();
        float value3 = packetWrapper.readFloat();
        String text3 = packetWrapper.readString();
        boolean matches = packetWrapper.readBoolean();
        int value4 = packetWrapper.readInt();
        List values = packetWrapper.readList(PacketWrapper::readString);
        List values2 = packetWrapper.readList(PacketWrapper::readString);
        List values3 = packetWrapper.readList(PacketWrapper::readString);
        List values4 = packetWrapper.readList(PacketWrapper::readString);
        Set values5 = packetWrapper.readSet(PacketWrapper::readBlockPosition);
        Set values6 = packetWrapper.readSet(PacketWrapper::readBlockPosition);
        return new DebugBrainDump(
                text, text2, value, value2, value3, text3, matches, value4, values, values2,
                values3, values4, values5, values6);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugBrainDump debugBrainDump) {
        packetWrapper.writeString(debugBrainDump.name);
        packetWrapper.writeString(debugBrainDump.profession);
        packetWrapper.writeInt(debugBrainDump.xp);
        packetWrapper.writeFloat(debugBrainDump.health);
        packetWrapper.writeFloat(debugBrainDump.maxHealth);
        packetWrapper.writeString(debugBrainDump.inventory);
        packetWrapper.writeBoolean(debugBrainDump.wantsGolem);
        packetWrapper.writeInt(debugBrainDump.angerLevel);
        packetWrapper.writeList(debugBrainDump.activities, PacketWrapper::writeString);
        packetWrapper.writeList(debugBrainDump.behaviors, PacketWrapper::writeString);
        packetWrapper.writeList(debugBrainDump.memories, PacketWrapper::writeString);
        packetWrapper.writeList(debugBrainDump.gossips, PacketWrapper::writeString);
        packetWrapper.writeSet(debugBrainDump.pois, PacketWrapper::writeBlockPosition);
        packetWrapper.writeSet(debugBrainDump.potentialPois, PacketWrapper::writeBlockPosition);
    }

    public String getName() {
        return this.name;
    }

    public String getProfession() {
        return this.profession;
    }

    public int getXp() {
        return this.xp;
    }

    public float getHealth() {
        return this.health;
    }

    public float getMaxHealth() {
        return this.maxHealth;
    }

    public String getInventory() {
        return this.inventory;
    }

    public boolean isWantsGolem() {
        return this.wantsGolem;
    }

    public int getAngerLevel() {
        return this.angerLevel;
    }

    public List<String> getActivities() {
        return this.activities;
    }

    public List<String> getBehaviors() {
        return this.behaviors;
    }

    public List<String> getMemories() {
        return this.memories;
    }

    public List<String> getGossips() {
        return this.gossips;
    }

    public Set<Vector3i> getPois() {
        return this.pois;
    }

    public Set<Vector3i> getPotentialPois() {
        return this.potentialPois;
    }
}
