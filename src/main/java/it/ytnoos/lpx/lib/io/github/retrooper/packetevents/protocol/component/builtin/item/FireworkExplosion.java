package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class FireworkExplosion {

    private FireworkExplosion.Shape shape;

    private List<Integer> colors;

    private List<Integer> fadeColors;

    private boolean hasTrail;

    private boolean hasTwinkle;

    public FireworkExplosion(
            FireworkExplosion.Shape shape2,
            List<Integer> colors,
            List<Integer> fadeColors,
            boolean hasTrail,
            boolean hasTwinkle) {
        this.shape = shape2;
        this.colors = colors;
        this.fadeColors = fadeColors;
        this.hasTrail = hasTrail;
        this.hasTwinkle = hasTwinkle;
    }

    public static FireworkExplosion read(PacketWrapper<?> packetWrapper) {
        FireworkExplosion.Shape shape = packetWrapper.readEnum(FireworkExplosion.Shape.values());
        List values2 = packetWrapper.readList(PacketWrapper::readInt, 128);
        List values3 = packetWrapper.readList(PacketWrapper::readInt, 128);
        boolean matches = packetWrapper.readBoolean();
        boolean matches2 = packetWrapper.readBoolean();
        return new FireworkExplosion(shape, values2, values3, matches, matches2);
    }

    public static void write(PacketWrapper<?> packetWrapper, FireworkExplosion fireworkExplosion) {
        packetWrapper.writeEnum(fireworkExplosion.shape);
        packetWrapper.writeList(fireworkExplosion.colors, PacketWrapper::writeInt);
        packetWrapper.writeList(fireworkExplosion.fadeColors, PacketWrapper::writeInt);
        packetWrapper.writeBoolean(fireworkExplosion.hasTrail);
        packetWrapper.writeBoolean(fireworkExplosion.hasTwinkle);
    }

    public FireworkExplosion.Shape getShape() {
        return this.shape;
    }

    public void setShape(FireworkExplosion.Shape shape2) {
        this.shape = shape2;
    }

    public void addColor(int value) {
        this.colors.add(value);
    }

    public List<Integer> getColors() {
        return this.colors;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    public void addFadeColor(int value) {
        this.fadeColors.add(value);
    }

    public List<Integer> getFadeColors() {
        return this.fadeColors;
    }

    public void setFadeColors(List<Integer> fadeColors) {
        this.fadeColors = fadeColors;
    }

    public boolean isHasTrail() {
        return this.hasTrail;
    }

    public void setHasTrail(boolean hasTrail) {
        this.hasTrail = hasTrail;
    }

    public boolean isHasTwinkle() {
        return this.hasTwinkle;
    }

    public void setHasTwinkle(boolean hasTwinkle) {
        this.hasTwinkle = hasTwinkle;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof FireworkExplosion)) {
            return false;
        } else {
            FireworkExplosion fireworkExplosion = (FireworkExplosion) result;
            if (this.hasTrail != fireworkExplosion.hasTrail) {
                return false;
            } else if (this.hasTwinkle != fireworkExplosion.hasTwinkle) {
                return false;
            } else if (this.shape != fireworkExplosion.shape) {
                return false;
            } else {
                return !this.colors.equals(fireworkExplosion.colors)
                        ? false
                        : this.fadeColors.equals(fireworkExplosion.fadeColors);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.shape, this.colors, this.fadeColors, this.hasTrail, this.hasTwinkle);
    }

    public enum Shape {
        SMALL_BALL,
        LARGE_BALL,
        STAR,
        CREEPER,
        BURST
    }
}
