package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.ints.IntProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class RandomizedIntStateProvider extends AbstractMappedEntity
        implements BlockStateProvider {

    public static final NbtMapCodec<RandomizedIntStateProvider> MAP_CODEC =
            new NbtMapCodec<RandomizedIntStateProvider>() {

                public RandomizedIntStateProvider decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    BlockStateProvider orThrow =
                            nBTCompound.getOrThrow(
                                    "source", BlockStateProvider.CODEC, packetWrapper);
                    String stringTagValueOrThrow = nBTCompound.getStringTagValueOrThrow("property");
                    IntProvider orThrow2 =
                            nBTCompound.getOrThrow("values", IntProvider.CODEC, packetWrapper);
                    return new RandomizedIntStateProvider(
                            null, orThrow, stringTagValueOrThrow, orThrow2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        RandomizedIntStateProvider randomizedIntStateProvider)
                        throws NbtCodecException {
                    nBTCompound.set(
                            "source",
                            randomizedIntStateProvider.source,
                            BlockStateProvider.CODEC,
                            packetWrapper);
                    nBTCompound.setTag(
                            "property", new NBTString(randomizedIntStateProvider.property));
                    nBTCompound.set(
                            "values",
                            randomizedIntStateProvider.values,
                            IntProvider.CODEC,
                            packetWrapper);
                }
            };

    private final BlockStateProvider source;

    private final String property;

    private final IntProvider values;

    public RandomizedIntStateProvider(
            BlockStateProvider blockStateProvider, String text, IntProvider intProvider) {
        this(null, blockStateProvider, text, intProvider);
    }

    @Internal
    public RandomizedIntStateProvider(
            @Nullable TypesBuilderData typesBuilderData,
            BlockStateProvider blockStateProvider,
            String property,
            IntProvider intProvider) {
        super(typesBuilderData);
        this.source = blockStateProvider;
        this.property = property;
        this.values = intProvider;
    }

    public BlockStateProvider getSource() {
        return this.source;
    }

    public String getProperty() {
        return this.property;
    }

    public IntProvider getValues() {
        return this.values;
    }

    @Override
    public BlockStateProviderType<?> getType() {
        return BlockStateProviderTypes.RANDOMIZED_INT;
    }

    public RandomizedIntStateProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new RandomizedIntStateProvider(
                typesBuilderData, this.source, this.property, this.values);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof RandomizedIntStateProvider)) {
            return false;
        } else {
            RandomizedIntStateProvider randomizedIntStateProvider =
                    (RandomizedIntStateProvider) result;
            if (!this.source.equals(randomizedIntStateProvider.source)) {
                return false;
            } else {
                return !this.property.equals(randomizedIntStateProvider.property)
                        ? false
                        : this.values.equals(randomizedIntStateProvider.values);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.source, this.property, this.values);
    }
}
