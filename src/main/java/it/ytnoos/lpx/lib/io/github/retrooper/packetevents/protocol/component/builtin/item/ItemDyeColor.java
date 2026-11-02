package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Objects;

public class ItemDyeColor {

    private int rgb;

    @Obsolete private boolean showInTooltip;

    public ItemDyeColor(int value) {
        this(value, true);
    }

    @Obsolete
    public ItemDyeColor(int rgb, boolean showInTooltip) {
        this.rgb = rgb;
        this.showInTooltip = showInTooltip;
    }

    public static ItemDyeColor read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readInt();
        boolean matches =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        || packetWrapper.readBoolean();
        return new ItemDyeColor(value, matches);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemDyeColor itemDyeColor) {
        packetWrapper.writeInt(itemDyeColor.rgb);
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            packetWrapper.writeBoolean(itemDyeColor.showInTooltip);
        }
    }

    public int getRgb() {
        return this.rgb;
    }

    public void setRgb(int rgb) {
        this.rgb = rgb;
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
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemDyeColor)) {
            return false;
        }
        ItemDyeColor itemDyeColor = (ItemDyeColor) result;
        return this.rgb != itemDyeColor.rgb
                ? false
                : this.showInTooltip == itemDyeColor.showInTooltip;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.rgb, this.showInTooltip);
    }
}
