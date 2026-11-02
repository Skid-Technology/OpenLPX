package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class ItemDeathProtection {

    private List<ConsumeEffect<?>> deathEffects;

    public ItemDeathProtection(List<ConsumeEffect<?>> deathEffects) {
        this.deathEffects = deathEffects;
    }

    public static ItemDeathProtection read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(ConsumeEffect::readFull);
        return new ItemDeathProtection(values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemDeathProtection itemDeathProtection) {
        packetWrapper.writeList(itemDeathProtection.deathEffects, ConsumeEffect::writeFull);
    }

    public List<ConsumeEffect<?>> getDeathEffects() {
        return this.deathEffects;
    }

    public void setDeathEffects(List<ConsumeEffect<?>> deathEffects) {
        this.deathEffects = deathEffects;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemDeathProtection)) {
            return false;
        }
        ItemDeathProtection itemDeathProtection = (ItemDeathProtection) result;
        return this.deathEffects.equals(itemDeathProtection.deathEffects);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.deathEffects);
    }

    @Override
    public String toString() {
        return "ItemDeathProtection{deathEffects=" + this.deathEffects + 125;
    }
}
