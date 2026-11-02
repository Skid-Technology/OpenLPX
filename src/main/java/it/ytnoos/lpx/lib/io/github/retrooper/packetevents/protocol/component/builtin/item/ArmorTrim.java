package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimmaterial.TrimMaterial;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimpattern.TrimPattern;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Objects;

public class ArmorTrim {

    private TrimMaterial material;

    private TrimPattern pattern;

    @Obsolete private boolean showInTooltip;

    public ArmorTrim(TrimMaterial trimMaterial, TrimPattern trimPattern) {
        this(trimMaterial, trimPattern, true);
    }

    @Obsolete
    public ArmorTrim(TrimMaterial trimMaterial, TrimPattern trimPattern, boolean showInTooltip) {
        this.material = trimMaterial;
        this.pattern = trimPattern;
        this.showInTooltip = showInTooltip;
    }

    public static ArmorTrim read(PacketWrapper<?> packetWrapper) {
        TrimMaterial trimMaterial = TrimMaterial.read(packetWrapper);
        TrimPattern trimPattern = TrimPattern.read(packetWrapper);
        boolean matches =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        || packetWrapper.readBoolean();
        return new ArmorTrim(trimMaterial, trimPattern, matches);
    }

    public static void write(PacketWrapper<?> packetWrapper, ArmorTrim armorTrim) {
        TrimMaterial.write(packetWrapper, armorTrim.material);
        TrimPattern.write(packetWrapper, armorTrim.pattern);
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            packetWrapper.writeBoolean(armorTrim.showInTooltip);
        }
    }

    public TrimMaterial getMaterial() {
        return this.material;
    }

    public void setMaterial(TrimMaterial trimMaterial) {
        this.material = trimMaterial;
    }

    public TrimPattern getPattern() {
        return this.pattern;
    }

    public void setPattern(TrimPattern trimPattern) {
        this.pattern = trimPattern;
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
        } else if (!(result instanceof ArmorTrim)) {
            return false;
        } else {
            ArmorTrim armorTrim = (ArmorTrim) result;
            if (this.showInTooltip != armorTrim.showInTooltip) {
                return false;
            } else {
                return !this.material.equals(armorTrim.material)
                        ? false
                        : this.pattern.equals(armorTrim.pattern);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.material, this.pattern, this.showInTooltip);
    }

    @Override
    public String toString() {
        return "ArmorTrim{material="
                + this.material
                + ", pattern="
                + this.pattern
                + ", showInTooltip="
                + this.showInTooltip
                + 125;
    }
}
