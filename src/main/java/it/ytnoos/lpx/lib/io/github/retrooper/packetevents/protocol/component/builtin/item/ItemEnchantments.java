package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment.type.EnchantmentType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment.type.EnchantmentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class ItemEnchantments implements Iterable<Entry<EnchantmentType, Integer>> {

    public static final ItemEnchantments EMPTY =
            new ItemEnchantments(Collections.emptyMap(), true) {

                @Override
                public void setEnchantments(Map<EnchantmentType, Integer> entries) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void setShowInTooltip(boolean matches) {
                    throw new UnsupportedOperationException();
                }
            };

    private Map<EnchantmentType, Integer> enchantments;

    @Obsolete private boolean showInTooltip;

    public ItemEnchantments(Map<EnchantmentType, Integer> entries) {
        this(entries, true);
    }

    @Obsolete
    public ItemEnchantments(Map<EnchantmentType, Integer> entries, boolean showInTooltip) {
        this.enchantments = Collections.unmodifiableMap(entries);
        this.showInTooltip = showInTooltip;
    }

    public static ItemEnchantments read(PacketWrapper<?> packetWrapper) {
        Map entries =
                packetWrapper.readMap(
                        result ->
                                ((PacketWrapper) packetWrapper)
                                        .readMappedEntity(EnchantmentTypes.getRegistry()),
                        PacketWrapper::readVarInt);
        boolean matches =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        || packetWrapper.readBoolean();
        return new ItemEnchantments(entries, matches);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemEnchantments itemEnchantments) {
        ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
        Map<EnchantmentType, Integer> enchantments = itemEnchantments.getEnchantments();
        packetWrapper.writeMap(
                enchantments,
                (result, result2) -> result.writeVarInt(result2.getId(clientVersion)),
                PacketWrapper::writeVarInt);
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            packetWrapper.writeBoolean(itemEnchantments.isShowInTooltip());
        }
    }

    public int getEnchantmentLevel(EnchantmentType enchantmentType) {
        return this.enchantments.getOrDefault(enchantmentType, 0);
    }

    public void setEnchantmentLevel(EnchantmentType enchantmentType, int value) {
        if (value == 0) {
            this.enchantments.remove(enchantmentType);
        } else {
            this.enchantments.put(enchantmentType, value);
        }
    }

    public boolean isEmpty() {
        return this.getEnchantmentCount() < 1;
    }

    public int getEnchantmentCount() {
        return this.enchantments.size();
    }

    public Map<EnchantmentType, Integer> getEnchantments() {
        return this.enchantments;
    }

    public void setEnchantments(Map<EnchantmentType, Integer> entries) {
        this.enchantments = entries;
    }

    @Obsolete
    public boolean isShowInTooltip() {
        return this.showInTooltip;
    }

    @Obsolete
    public void setShowInTooltip(boolean showInTooltip) {
        this.showInTooltip = showInTooltip;
    }

    @Override
    public Iterator<Entry<EnchantmentType, Integer>> iterator() {
        return this.enchantments.entrySet().iterator();
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemEnchantments)) {
            return false;
        }
        ItemEnchantments itemEnchantments = (ItemEnchantments) result;
        return this.showInTooltip != itemEnchantments.showInTooltip
                ? false
                : this.enchantments.equals(itemEnchantments.enchantments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.enchantments, this.showInTooltip);
    }

    @Override
    public String toString() {
        return "ItemEnchantments{enchantments="
                + this.enchantments
                + ", showInTooltip="
                + this.showInTooltip
                + 125;
    }
}
