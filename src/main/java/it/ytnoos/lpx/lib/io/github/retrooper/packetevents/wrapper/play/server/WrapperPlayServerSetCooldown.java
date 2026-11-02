package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayServerSetCooldown extends PacketWrapper<WrapperPlayServerSetCooldown> {

    private ResourceLocation cooldownGroup;

    private int cooldownTicks;

    public WrapperPlayServerSetCooldown(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Obsolete
    public WrapperPlayServerSetCooldown(ItemType itemType, int value) {
        this(itemType.getName(), value);
    }

    public WrapperPlayServerSetCooldown(ResourceLocation resourceLocation, int cooldownTicks) {
        super(PacketType.Play.Server.SET_COOLDOWN);
        this.cooldownGroup = resourceLocation;
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.cooldownGroup = this.readIdentifier();
        } else {
            ItemType itemType = this.readMappedEntity(ItemTypes.getRegistry());
            this.cooldownGroup = itemType.getName();
        }
        this.cooldownTicks = this.readVarInt();
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeIdentifier(this.cooldownGroup);
        } else {
            this.writeMappedEntity(this.getItem());
        }
        this.writeVarInt(this.cooldownTicks);
    }

    public void copy(WrapperPlayServerSetCooldown wrapperPlayServerSetCooldown) {
        this.cooldownGroup = wrapperPlayServerSetCooldown.cooldownGroup;
        this.cooldownTicks = wrapperPlayServerSetCooldown.cooldownTicks;
    }

    public ResourceLocation getCooldownGroup() {
        return this.cooldownGroup;
    }

    public void setCooldownGroup(ResourceLocation resourceLocation) {
        this.cooldownGroup = resourceLocation;
    }

    @Obsolete
    public ItemType getItem() {
        ItemType itemType;
        if ((itemType = ItemTypes.getByName(this.cooldownGroup.toString())) == null) {
            throw new IllegalStateException(
                    "Can't get legacy cooldown item for cooldown group " + this.cooldownGroup);
        } else {
            return itemType;
        }
    }

    @Obsolete
    public void setItem(ItemType itemType) {
        this.cooldownGroup = itemType.getName();
    }

    public int getCooldownTicks() {
        return this.cooldownTicks;
    }

    public void setCooldownTicks(int cooldownTicks) {
        this.cooldownTicks = cooldownTicks;
    }
}
