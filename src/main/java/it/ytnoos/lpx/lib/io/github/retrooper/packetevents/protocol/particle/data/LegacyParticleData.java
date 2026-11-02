package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Arrays;

@NullMarked
public class LegacyParticleData extends ParticleData implements LegacyConvertible {

    private final int[] legacyData;

    public static LegacyParticleData nullValue(int value) {
        int[] values;
        Arrays.fill(values = new int[getSize(value)], 0);
        return new LegacyParticleData(values);
    }

    public static LegacyParticleData ofTwo(int value, int value2) {
        return new LegacyParticleData(value, value2);
    }

    public static LegacyParticleData ofOne(int value) {
        return new LegacyParticleData(value);
    }

    public static LegacyParticleData zero() {
        return new LegacyParticleData();
    }

    public static LegacyParticleData ofBlock(ItemType itemType, byte value) {
        return new LegacyParticleData(
                itemType.getId(
                                PacketEvents.getAPI()
                                        .getServerManager()
                                        .getVersion()
                                        .toClientVersion())
                        | value << 12);
    }

    public static LegacyParticleData ofItem(ItemType itemType, byte value) {
        return new LegacyParticleData(
                itemType.getId(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion()),
                value);
    }

    LegacyParticleData(int... legacyData) {
        this.legacyData = legacyData;
    }

    public int[] getLegacyData() {
        return this.legacyData;
    }

    public void validate(int value) {
        if (this.legacyData.length != getSize(value)) {
            throw new IllegalArgumentException(
                    "Invalid size for type " + value + ": " + this.legacyData.length);
        }
    }

    public static int getSize(int value) {
        if (value == 36) {
            return 2;
        } else {
            return value != 37 && value != 38 && value != 46 ? 0 : 1;
        }
    }

    public static LegacyParticleData read(PacketWrapper<?> packetWrapper, int value) {
        int[] values = new int[getSize(value)];
        for (int index = 0; index < values.length; index++) {
            values[index] = packetWrapper.readVarInt();
        }
        return new LegacyParticleData(values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, int value, LegacyParticleData legacyParticleData) {
        legacyParticleData.validate(value);
        for (int index = 0; index < legacyParticleData.legacyData.length; index++) {
            packetWrapper.writeVarInt(legacyParticleData.legacyData[index]);
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public LegacyParticleData toLegacy(ClientVersion clientVersion) {
        return this;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            LegacyParticleData legacyParticleData = (LegacyParticleData) result;
            return Arrays.equals(this.legacyData, legacyParticleData.legacyData);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.legacyData);
    }
}
