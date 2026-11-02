package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.StaticComponentMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment.EnchantmentDefinition;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRefSet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.ResolvableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.GlobalRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticEnchantmentType extends AbstractMappedEntity
        implements EnchantmentType, ResolvableEntity {

    private final Component description;

    private final EnchantmentDefinition definition;

    private final MappedEntityRefSet<EnchantmentType> exclusiveSetRef;

    @Nullable private MappedEntitySet<EnchantmentType> exclusiveSet;

    private final StaticComponentMap effects;

    public StaticEnchantmentType(
            Component component,
            EnchantmentDefinition enchantmentDefinition,
            MappedEntitySet<EnchantmentType> mappedEntitySet,
            StaticComponentMap staticComponentMap) {
        this(null, component, enchantmentDefinition, mappedEntitySet, staticComponentMap);
    }

    @Internal
    public StaticEnchantmentType(
            @Nullable TypesBuilderData typesBuilderData,
            Component component,
            EnchantmentDefinition enchantmentDefinition,
            MappedEntityRefSet<EnchantmentType> mappedEntityRefSet,
            StaticComponentMap staticComponentMap) {
        super(typesBuilderData);
        this.description = component;
        this.definition = enchantmentDefinition;
        this.exclusiveSetRef = mappedEntityRefSet;
        this.effects = staticComponentMap;
    }

    @Override
    public void doResolve(PacketWrapper<?> packetWrapper) {
        this.exclusiveSet =
                this.exclusiveSetRef.resolve(packetWrapper, EnchantmentTypes.getRegistry());
    }

    public EnchantmentType copy(@Nullable TypesBuilderData typesBuilderData) {
        StaticEnchantmentType staticEnchantmentType;
        (staticEnchantmentType =
                                new StaticEnchantmentType(
                                        typesBuilderData,
                                        this.description,
                                        this.definition,
                                        this.exclusiveSetRef,
                                        this.effects))
                        .exclusiveSet =
                this.exclusiveSet;
        return staticEnchantmentType;
    }

    @Override
    public Component getDescription() {
        return this.description;
    }

    @Override
    public EnchantmentDefinition getDefinition() {
        return this.definition;
    }

    @Override
    public MappedEntitySet<EnchantmentType> getExclusiveSet() {
        if (this.exclusiveSet == null) {
            this.exclusiveSet =
                    this.exclusiveSetRef.resolve(
                            PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                            GlobalRegistryHolder.INSTANCE,
                            EnchantmentTypes.getRegistry());
        }
        return this.exclusiveSet;
    }

    @Override
    public MappedEntityRefSet<EnchantmentType> getExclusiveRefSet() {
        return this.exclusiveSetRef;
    }

    @Override
    public StaticComponentMap getEffects() {
        return this.effects;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof StaticEnchantmentType)) {
            return false;
        } else {
            StaticEnchantmentType staticEnchantmentType = (StaticEnchantmentType) result;
            if (!this.description.equals(staticEnchantmentType.description)) {
                return false;
            } else if (!this.definition.equals(staticEnchantmentType.definition)) {
                return false;
            } else {
                return !this.exclusiveSetRef.equals(staticEnchantmentType.exclusiveSetRef)
                        ? false
                        : this.effects.equals(staticEnchantmentType.effects);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.description, this.definition, this.exclusiveSetRef, this.effects);
    }

    @Override
    public String toString() {
        return "StaticEnchantmentType{description="
                + this.description
                + ", definition="
                + this.definition
                + ", exclusiveSetRef="
                + this.exclusiveSetRef
                + ", effects="
                + this.effects
                + "}";
    }
}
