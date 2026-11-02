package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public final class EnchantmentCost {

    private final int base;

    private final int perLevelAboveFirst;

    public EnchantmentCost(int base, int perLevelAboveFirst) {
        this.base = base;
        this.perLevelAboveFirst = perLevelAboveFirst;
    }

    @Deprecated
    public static EnchantmentCost decode(NBT nBT, ClientVersion clientVersion) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion));
    }

    public static EnchantmentCost decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound;
        int asInt = (nBTCompound = (NBTCompound) nBT).getNumberTagOrThrow("base").getAsInt();
        int asInt2 = nBTCompound.getNumberTagOrThrow("per_level_above_first").getAsInt();
        return new EnchantmentCost(asInt, asInt2);
    }

    @Deprecated
    public static NBT encode(EnchantmentCost enchantmentCost, ClientVersion clientVersion) {
        return encode(PacketWrapper.createDummyWrapper(clientVersion), enchantmentCost);
    }

    public static NBT encode(PacketWrapper<?> packetWrapper, EnchantmentCost enchantmentCost) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound()).setTag("base", new NBTInt(enchantmentCost.base));
        nBTCompound.setTag("per_level_above_first", new NBTInt(enchantmentCost.perLevelAboveFirst));
        return nBTCompound;
    }

    public int getBase() {
        return this.base;
    }

    public int getPerLevelAboveFirst() {
        return this.perLevelAboveFirst;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof EnchantmentCost)) {
            return false;
        }
        EnchantmentCost enchantmentCost = (EnchantmentCost) result;
        return this.base != enchantmentCost.base
                ? false
                : this.perLevelAboveFirst == enchantmentCost.perLevelAboveFirst;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.base, this.perLevelAboveFirst);
    }

    @Override
    public String toString() {
        return "EnchantmentCost{base="
                + this.base
                + ", perLevelAboveFirst="
                + this.perLevelAboveFirst
                + 125;
    }
}
