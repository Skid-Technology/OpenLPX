package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticDamageType extends AbstractMappedEntity implements DamageType {

    private final String messageId;

    private final DamageScaling scaling;

    private final float exhaustion;

    private final DamageEffects effects;

    private final DeathMessageType deathMessageType;

    @Internal
    public StaticDamageType(
            @Nullable TypesBuilderData typesBuilderData,
            String messageId,
            DamageScaling damageScaling,
            float exhaustion,
            DamageEffects damageEffects,
            DeathMessageType deathMessageType2) {
        super(typesBuilderData);
        this.messageId = messageId;
        this.scaling = damageScaling;
        this.exhaustion = exhaustion;
        this.effects = damageEffects;
        this.deathMessageType = deathMessageType2;
    }

    public DamageType copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticDamageType(
                typesBuilderData,
                this.messageId,
                this.scaling,
                this.exhaustion,
                this.effects,
                this.deathMessageType);
    }

    @Override
    public String getMessageId() {
        return this.messageId;
    }

    @Override
    public DamageScaling getScaling() {
        return this.scaling;
    }

    @Override
    public float getExhaustion() {
        return this.exhaustion;
    }

    @Override
    public DamageEffects getEffects() {
        return this.effects;
    }

    @Override
    public DeathMessageType getDeathMessageType() {
        return this.deathMessageType;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof StaticDamageType)) {
            return false;
        } else {
            StaticDamageType staticDamageType;
            if (Float.compare(
                            (staticDamageType = (StaticDamageType) result).exhaustion,
                            this.exhaustion)
                    != 0) {
                return false;
            } else if (!this.messageId.equals(staticDamageType.messageId)) {
                return false;
            } else if (this.scaling != staticDamageType.scaling) {
                return false;
            } else {
                return this.effects != staticDamageType.effects
                        ? false
                        : this.deathMessageType == staticDamageType.deathMessageType;
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.messageId, this.scaling, this.exhaustion, this.effects, this.deathMessageType);
    }

    @Override
    public String toString() {
        return "StaticDamageType{messageId='"
                + this.messageId
                + '\''
                + ", scaling="
                + this.scaling
                + ", exhaustion="
                + this.exhaustion
                + ", effects="
                + this.effects
                + ", deathMessageType="
                + this.deathMessageType
                + '}';
    }
}
