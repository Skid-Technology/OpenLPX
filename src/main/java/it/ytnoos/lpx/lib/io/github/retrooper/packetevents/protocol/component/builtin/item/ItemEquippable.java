package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ItemEquippable {

    private EquipmentSlot slot;

    private Sound equipSound;

    @Nullable private ResourceLocation assetId;

    @Nullable private ResourceLocation cameraOverlay;

    @Nullable private MappedEntitySet<EntityType> allowedEntities;

    private boolean dispensable;

    private boolean swappable;

    private boolean damageOnHurt;

    private boolean equipOnInteract;

    private boolean canBeSheared;

    private Sound shearingSound;

    @Obsolete
    public ItemEquippable(
            EquipmentSlot equipmentSlot,
            Sound sound,
            @Nullable ResourceLocation resourceLocation,
            @Nullable ResourceLocation resourceLocation2,
            @Nullable MappedEntitySet<EntityType> mappedEntitySet,
            boolean matches,
            boolean matches2,
            boolean matches3) {
        this(
                equipmentSlot,
                sound,
                resourceLocation,
                resourceLocation2,
                mappedEntitySet,
                matches,
                matches2,
                matches3,
                false);
    }

    @Obsolete
    public ItemEquippable(
            EquipmentSlot equipmentSlot,
            Sound sound,
            @Nullable ResourceLocation resourceLocation,
            @Nullable ResourceLocation resourceLocation2,
            @Nullable MappedEntitySet<EntityType> mappedEntitySet,
            boolean matches,
            boolean matches2,
            boolean matches3,
            boolean matches4) {
        this(
                equipmentSlot,
                sound,
                resourceLocation,
                resourceLocation2,
                mappedEntitySet,
                matches,
                matches2,
                matches3,
                matches4,
                false,
                Sounds.ITEM_SHEARS_SNIP);
    }

    public ItemEquippable(
            EquipmentSlot equipmentSlot,
            Sound sound,
            @Nullable ResourceLocation resourceLocation,
            @Nullable ResourceLocation resourceLocation2,
            @Nullable MappedEntitySet<EntityType> mappedEntitySet,
            boolean dispensable,
            boolean swappable,
            boolean damageOnHurt,
            boolean equipOnInteract,
            boolean canBeSheared,
            Sound sound2) {
        this.slot = equipmentSlot;
        this.equipSound = sound;
        this.assetId = resourceLocation;
        this.cameraOverlay = resourceLocation2;
        this.allowedEntities = mappedEntitySet;
        this.dispensable = dispensable;
        this.swappable = swappable;
        this.damageOnHurt = damageOnHurt;
        this.equipOnInteract = equipOnInteract;
        this.canBeSheared = canBeSheared;
        this.shearingSound = sound2;
    }

    public static ItemEquippable read(PacketWrapper<?> packetWrapper) {
        EquipmentSlot byComponentId =
                EquipmentSlot.getByComponentId(
                        packetWrapper.getServerVersion(), packetWrapper.readVarInt());
        Sound sound = Sound.read(packetWrapper);
        ResourceLocation resourceLocation =
                packetWrapper.readOptional(PacketWrapper::readIdentifier);
        ResourceLocation resourceLocation2 =
                packetWrapper.readOptional(PacketWrapper::readIdentifier);
        MappedEntitySet mappedEntitySet =
                packetWrapper.readOptional(
                        result -> MappedEntitySet.read(result, EntityTypes.getRegistry()));
        boolean matches = packetWrapper.readBoolean();
        boolean matches2 = packetWrapper.readBoolean();
        boolean matches3 = packetWrapper.readBoolean();
        boolean matches4 = false;
        boolean matches5 = false;
        Sound sound2 = Sounds.ITEM_SHEARS_SNIP;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            matches4 = packetWrapper.readBoolean();
            if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                matches5 = packetWrapper.readBoolean();
                sound2 = Sound.read(packetWrapper);
            }
        }
        return new ItemEquippable(
                byComponentId,
                sound,
                resourceLocation,
                resourceLocation2,
                mappedEntitySet,
                matches,
                matches2,
                matches3,
                matches4,
                matches5,
                sound2);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemEquippable itemEquippable) {
        packetWrapper.writeVarInt(
                itemEquippable.slot.getComponentId(packetWrapper.getServerVersion()));
        Sound.write(packetWrapper, itemEquippable.equipSound);
        packetWrapper.writeOptional(itemEquippable.assetId, PacketWrapper::writeIdentifier);
        packetWrapper.writeOptional(itemEquippable.cameraOverlay, PacketWrapper::writeIdentifier);
        packetWrapper.writeOptional(itemEquippable.allowedEntities, MappedEntitySet::write);
        packetWrapper.writeBoolean(itemEquippable.dispensable);
        packetWrapper.writeBoolean(itemEquippable.swappable);
        packetWrapper.writeBoolean(itemEquippable.damageOnHurt);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            packetWrapper.writeBoolean(itemEquippable.equipOnInteract);
            if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                packetWrapper.writeBoolean(itemEquippable.canBeSheared);
                Sound.write(packetWrapper, itemEquippable.shearingSound);
            }
        }
    }

    public EquipmentSlot getSlot() {
        return this.slot;
    }

    public void setSlot(EquipmentSlot equipmentSlot) {
        this.slot = equipmentSlot;
    }

    public Sound getEquipSound() {
        return this.equipSound;
    }

    public void setEquipSound(Sound sound) {
        this.equipSound = sound;
    }

    @Nullable
    public ResourceLocation getAssetId() {
        return this.assetId;
    }

    public void setAssetId(@Nullable ResourceLocation resourceLocation) {
        this.assetId = resourceLocation;
    }

    @Nullable
    public ResourceLocation getCameraOverlay() {
        return this.cameraOverlay;
    }

    public void setCameraOverlay(@Nullable ResourceLocation resourceLocation) {
        this.cameraOverlay = resourceLocation;
    }

    @Nullable
    public MappedEntitySet<EntityType> getAllowedEntities() {
        return this.allowedEntities;
    }

    public void setAllowedEntities(@Nullable MappedEntitySet<EntityType> mappedEntitySet) {
        this.allowedEntities = mappedEntitySet;
    }

    public boolean isDispensable() {
        return this.dispensable;
    }

    public void setDispensable(boolean dispensable) {
        this.dispensable = dispensable;
    }

    public boolean isSwappable() {
        return this.swappable;
    }

    public void setSwappable(boolean swappable) {
        this.swappable = swappable;
    }

    public boolean isDamageOnHurt() {
        return this.damageOnHurt;
    }

    public void setDamageOnHurt(boolean damageOnHurt) {
        this.damageOnHurt = damageOnHurt;
    }

    public boolean isEquipOnInteract() {
        return this.equipOnInteract;
    }

    public void setEquipOnInteract(boolean equipOnInteract) {
        this.equipOnInteract = equipOnInteract;
    }

    public boolean isCanBeSheared() {
        return this.canBeSheared;
    }

    public void setCanBeSheared(boolean canBeSheared) {
        this.canBeSheared = canBeSheared;
    }

    public Sound getShearingSound() {
        return this.shearingSound;
    }

    public void setShearingSound(Sound sound) {
        this.shearingSound = sound;
    }

    @Deprecated
    @Nullable
    public ResourceLocation getModel() {
        return this.assetId;
    }

    @Deprecated
    public void setModel(@Nullable ResourceLocation resourceLocation) {
        this.assetId = resourceLocation;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ItemEquippable)) {
            return false;
        } else {
            ItemEquippable itemEquippable = (ItemEquippable) result;
            if (this.dispensable != itemEquippable.dispensable) {
                return false;
            } else if (this.swappable != itemEquippable.swappable) {
                return false;
            } else if (this.damageOnHurt != itemEquippable.damageOnHurt) {
                return false;
            } else if (this.equipOnInteract != itemEquippable.equipOnInteract) {
                return false;
            } else if (this.canBeSheared != itemEquippable.canBeSheared) {
                return false;
            } else if (this.slot != itemEquippable.slot) {
                return false;
            } else if (!this.equipSound.equals(itemEquippable.equipSound)) {
                return false;
            } else if (!Objects.equals(this.assetId, itemEquippable.assetId)) {
                return false;
            } else if (!Objects.equals(this.cameraOverlay, itemEquippable.cameraOverlay)) {
                return false;
            } else {
                return !Objects.equals(this.allowedEntities, itemEquippable.allowedEntities)
                        ? false
                        : this.shearingSound.equals(itemEquippable.shearingSound);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.slot,
                this.equipSound,
                this.assetId,
                this.cameraOverlay,
                this.allowedEntities,
                this.dispensable,
                this.swappable,
                this.damageOnHurt,
                this.equipOnInteract,
                this.canBeSheared,
                this.shearingSound);
    }

    @Override
    public String toString() {
        return "ItemEquippable{slot="
                + this.slot
                + ", equipSound="
                + this.equipSound
                + ", assetId="
                + this.assetId
                + ", cameraOverlay="
                + this.cameraOverlay
                + ", allowedEntities="
                + this.allowedEntities
                + ", dispensable="
                + this.dispensable
                + ", swappable="
                + this.swappable
                + ", damageOnHurt="
                + this.damageOnHurt
                + ", equipOnInteract="
                + this.equipOnInteract
                + ", canBeSheared="
                + this.canBeSheared
                + ", shearingSound="
                + this.shearingSound
                + '}';
    }
}
