package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.transform;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.UserProfile;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EntityTransformType {

    public static final EntityTransformType IDENTITY = new EntityTransformType(null, 1.0F, null);

    @Nullable private EntityTransformType.TypeModifier typeModifier;

    private float scale;

    @Nullable private UserProfile playerSkin;

    public EntityTransformType(
            @Nullable EntityTransformType.TypeModifier typeModifier2,
            float scale,
            @Nullable UserProfile userProfile) {
        this.typeModifier = typeModifier2;
        this.scale = scale;
        this.playerSkin = userProfile;
    }

    public Optional<EntityTransformType.TypeModifier> getTypeModifier() {
        return Optional.ofNullable(this.typeModifier);
    }

    public void setTypeModifier(@Nullable EntityTransformType.TypeModifier typeModifier2) {
        this.typeModifier = typeModifier2;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Optional<UserProfile> getPlayerSkin() {
        return Optional.ofNullable(this.playerSkin);
    }

    public void setPlayerSkin(@Nullable UserProfile userProfile) {
        this.playerSkin = userProfile;
    }

    public static class TypeModifier {

        private EntityType entityType;

        @Nullable private NBTCompound tag;

        public TypeModifier(EntityType entityType2, @Nullable NBTCompound nBTCompound) {
            this.entityType = entityType2;
            this.tag = nBTCompound;
        }

        public EntityType getEntityType() {
            return this.entityType;
        }

        public void setEntityType(EntityType entityType2) {
            this.entityType = entityType2;
        }

        public Optional<NBTCompound> getTag() {
            return Optional.ofNullable(this.tag);
        }

        public void setTag(@Nullable NBTCompound nBTCompound) {
            this.tag = nBTCompound;
        }
    }
}
