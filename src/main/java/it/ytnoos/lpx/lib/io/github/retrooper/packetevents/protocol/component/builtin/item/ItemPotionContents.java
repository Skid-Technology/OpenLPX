package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.Potion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.Potions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ItemPotionContents {

    @Nullable private Potion potion;

    @Nullable private Integer customColor;

    private List<PotionEffect> customEffects;

    @Nullable private String customName;

    public ItemPotionContents(
            @Nullable Potion potion, @Nullable Integer value, List<PotionEffect> values) {
        this(potion, value, values, null);
    }

    public ItemPotionContents(
            @Nullable Potion potion2,
            @Nullable Integer customColor,
            List<PotionEffect> customEffects,
            @Nullable String customName) {
        this.potion = potion2;
        this.customColor = customColor;
        this.customEffects = customEffects;
        this.customName = customName;
    }

    public static ItemPotionContents read(PacketWrapper<?> packetWrapper) {
        Potion potion =
                packetWrapper.readOptional(result -> result.readMappedEntity(Potions::getById));
        Integer value = packetWrapper.readOptional(PacketWrapper::readInt);
        List values = packetWrapper.readList(PotionEffect::read);
        String text =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)
                        ? packetWrapper.readOptional(PacketWrapper::readString)
                        : null;
        return new ItemPotionContents(potion, value, values, text);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemPotionContents itemPotionContents) {
        packetWrapper.writeOptional(itemPotionContents.potion, PacketWrapper::writeMappedEntity);
        packetWrapper.writeOptional(itemPotionContents.customColor, PacketWrapper::writeInt);
        packetWrapper.writeList(itemPotionContents.customEffects, PotionEffect::write);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            packetWrapper.writeOptional(itemPotionContents.customName, PacketWrapper::writeString);
        }
    }

    @Nullable
    public Potion getPotion() {
        return this.potion;
    }

    public void setPotion(@Nullable Potion potion2) {
        this.potion = potion2;
    }

    @Nullable
    public Integer getCustomColor() {
        return this.customColor;
    }

    public void setCustomColor(@Nullable Integer customColor) {
        this.customColor = customColor;
    }

    private void addCustomEffect(PotionEffect potionEffect) {
        this.customEffects.add(potionEffect);
    }

    public List<PotionEffect> getCustomEffects() {
        return this.customEffects;
    }

    public void setCustomEffects(List<PotionEffect> customEffects) {
        this.customEffects = customEffects;
    }

    @Nullable
    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(@Nullable String customName) {
        this.customName = customName;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof ItemPotionContents)) {
            return false;
        } else {
            ItemPotionContents itemPotionContents = (ItemPotionContents) result;
            if (!Objects.equals(this.potion, itemPotionContents.potion)) {
                return false;
            } else if (!Objects.equals(this.customColor, itemPotionContents.customColor)) {
                return false;
            } else {
                return !this.customEffects.equals(itemPotionContents.customEffects)
                        ? false
                        : Objects.equals(this.customName, itemPotionContents.customName);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.potion, this.customColor, this.customEffects, this.customName);
    }
}
