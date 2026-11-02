package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers.AttributeModifier;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class StaticAttributeType<T> extends AbstractMappedEntity implements AttributeType<T> {

    @Nullable private final NbtCodec<T> valueCodec;

    private final NbtCodec<AttributeModifier<T, ?>> modifierCodec;

    @Internal
    public StaticAttributeType(
            @Nullable TypesBuilderData typesBuilderData,
            @Nullable NbtCodec<T> nbtCodec,
            NbtCodec<AttributeModifier<T, ?>> nbtCodec2) {
        super(typesBuilderData);
        this.valueCodec = nbtCodec;
        this.modifierCodec = nbtCodec2;
    }

    @Override
    public boolean isSynced() {
        return this.valueCodec != null;
    }

    @Override
    public NbtCodec<T> getValueCodec() {
        if (this.valueCodec == null) {
            throw new UnsupportedOperationException();
        } else {
            return this.valueCodec;
        }
    }

    @Override
    public NbtCodec<AttributeModifier<T, ?>> getModifierCodec() {
        return this.modifierCodec;
    }
}
