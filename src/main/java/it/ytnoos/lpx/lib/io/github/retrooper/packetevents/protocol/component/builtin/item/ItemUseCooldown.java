package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetCooldown;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ItemUseCooldown {

    private float seconds;

    private Optional<ResourceLocation> cooldownGroup;

    public ItemUseCooldown(float value, @Nullable ResourceLocation resourceLocation) {
        this(value, Optional.ofNullable(resourceLocation));
    }

    public ItemUseCooldown(float seconds, Optional<ResourceLocation> optional) {
        this.seconds = seconds;
        this.cooldownGroup = optional;
    }

    public static ItemUseCooldown read(PacketWrapper<?> packetWrapper) {
        float value = packetWrapper.readFloat();
        ResourceLocation resourceLocation =
                packetWrapper.readOptional(PacketWrapper::readIdentifier);
        return new ItemUseCooldown(value, resourceLocation);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemUseCooldown itemUseCooldown) {
        packetWrapper.writeFloat(itemUseCooldown.seconds);
        packetWrapper.writeOptional(
                itemUseCooldown.cooldownGroup.orElse(null), PacketWrapper::writeIdentifier);
    }

    public WrapperPlayServerSetCooldown buildWrapper(ItemStack itemStack) {
        return this.buildWrapper(itemStack.getType());
    }

    public WrapperPlayServerSetCooldown buildWrapper(ItemType itemType) {
        int value = (int) (this.seconds * 20.0F);
        Optional<WrapperPlayServerSetCooldown> optional =
                this.cooldownGroup.map(result -> new WrapperPlayServerSetCooldown(result, value));
        return optional.orElseGet(() -> new WrapperPlayServerSetCooldown(itemType, value));
    }

    public float getSeconds() {
        return this.seconds;
    }

    public void setSeconds(float seconds) {
        this.seconds = seconds;
    }

    public Optional<ResourceLocation> getCooldownGroup() {
        return this.cooldownGroup;
    }

    public void setCooldownGroup(Optional<ResourceLocation> optional) {
        this.cooldownGroup = optional;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemUseCooldown)) {
            return false;
        }
        ItemUseCooldown itemUseCooldown;
        return Float.compare((itemUseCooldown = (ItemUseCooldown) result).seconds, this.seconds)
                        != 0
                ? false
                : this.cooldownGroup.equals(itemUseCooldown.cooldownGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.seconds, this.cooldownGroup);
    }

    @Override
    public String toString() {
        return "ItemUseCooldown{seconds="
                + this.seconds
                + ", cooldownGroup="
                + this.cooldownGroup
                + 125;
    }
}
