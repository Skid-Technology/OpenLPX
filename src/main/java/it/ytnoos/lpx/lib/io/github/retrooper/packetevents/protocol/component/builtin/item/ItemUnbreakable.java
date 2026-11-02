package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Objects;

public class ItemUnbreakable {

    @Obsolete private boolean showInTooltip;

    public ItemUnbreakable() {
        this(true);
    }

    @Obsolete
    public ItemUnbreakable(boolean showInTooltip) {
        this.showInTooltip = showInTooltip;
    }

    public static ItemUnbreakable read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            return new ItemUnbreakable();
        }
        boolean matches = packetWrapper.readBoolean();
        return new ItemUnbreakable(matches);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemUnbreakable itemUnbreakable) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            packetWrapper.writeBoolean(itemUnbreakable.showInTooltip);
        }
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
    public boolean equals(Object result) {
        if (!(result instanceof ItemUnbreakable)) {
            return false;
        }
        ItemUnbreakable itemUnbreakable = (ItemUnbreakable) result;
        return this.showInTooltip == itemUnbreakable.showInTooltip;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.showInTooltip);
    }
}
