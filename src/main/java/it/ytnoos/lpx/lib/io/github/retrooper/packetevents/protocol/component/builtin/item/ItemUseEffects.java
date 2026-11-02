package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ItemUseEffects {

    private boolean canSprint;

    private boolean interactVibrations;

    private float speedMultiplier;

    public ItemUseEffects(boolean canSprint, boolean interactVibrations, float speedMultiplier) {
        this.canSprint = canSprint;
        this.interactVibrations = interactVibrations;
        this.speedMultiplier = speedMultiplier;
    }

    public static ItemUseEffects read(PacketWrapper<?> packetWrapper) {
        boolean matches = packetWrapper.readBoolean();
        boolean matches2 = packetWrapper.readBoolean();
        float value = packetWrapper.readFloat();
        return new ItemUseEffects(matches, matches2, value);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemUseEffects itemUseEffects) {
        packetWrapper.writeBoolean(itemUseEffects.canSprint);
        packetWrapper.writeBoolean(itemUseEffects.interactVibrations);
        packetWrapper.writeFloat(itemUseEffects.speedMultiplier);
    }

    public boolean isCanSprint() {
        return this.canSprint;
    }

    public void setCanSprint(boolean canSprint) {
        this.canSprint = canSprint;
    }

    public boolean isInteractVibrations() {
        return this.interactVibrations;
    }

    public void setInteractVibrations(boolean interactVibrations) {
        this.interactVibrations = interactVibrations;
    }

    public float getSpeedMultiplier() {
        return this.speedMultiplier;
    }

    public void setSpeedMultiplier(float speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemUseEffects itemUseEffects = (ItemUseEffects) result;
            if (this.canSprint != itemUseEffects.canSprint) {
                return false;
            } else {
                return this.interactVibrations != itemUseEffects.interactVibrations
                        ? false
                        : Float.compare(itemUseEffects.speedMultiplier, this.speedMultiplier) == 0;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.canSprint, this.interactVibrations, this.speedMultiplier);
    }
}
