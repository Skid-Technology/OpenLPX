package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.easing;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.FloatUnaryOperator;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class StaticEasingType extends AbstractMappedEntity implements EasingType {

    private final FloatUnaryOperator operator;

    @Internal
    public StaticEasingType(
            @Nullable TypesBuilderData typesBuilderData, FloatUnaryOperator floatUnaryOperator) {
        super(typesBuilderData);
        this.operator = floatUnaryOperator;
    }

    @Override
    public float apply(float value) {
        return this.operator.apply(value);
    }
}
