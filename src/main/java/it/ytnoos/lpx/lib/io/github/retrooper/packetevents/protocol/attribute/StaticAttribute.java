package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticAttribute extends AbstractMappedEntity implements Attribute {

    @Nullable private final ResourceLocation legacyName;

    private final double defaultValue;

    private final double minValue;

    private final double maxValue;

    @Internal
    public StaticAttribute(
            @Nullable TypesBuilderData typesBuilderData,
            String text,
            double defaultValue,
            double minValue,
            double maxValue) {
        super(typesBuilderData);
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.legacyName =
                text != null && typesBuilderData != null
                        ? new ResourceLocation(
                                typesBuilderData.getName().getNamespace(),
                                text + "." + typesBuilderData.getName().getKey())
                        : null;
    }

    @Override
    public ResourceLocation getName(ClientVersion clientVersion) {
        if (this.data == null) {
            throw new UnsupportedOperationException();
        } else {
            return !clientVersion.isNewerThanOrEquals(ClientVersion.V_1_21_2)
                            && this.legacyName != null
                    ? this.legacyName
                    : this.data.getName();
        }
    }

    @Override
    public double getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public double getMinValue() {
        return this.minValue;
    }

    @Override
    public double getMaxValue() {
        return this.maxValue;
    }
}
