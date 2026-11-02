package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.builtin.BlockPositionSource;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.builtin.EntityPositionSource;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public final class PositionSourceTypes {

    private static final VersionedRegistry<PositionSourceType<?>> REGISTRY =
            new VersionedRegistry<>("position_source_type");

    public static final PositionSourceType<BlockPositionSource> BLOCK =
            define(
                    "block",
                    BlockPositionSource::read,
                    BlockPositionSource::write,
                    BlockPositionSource::decodeSource,
                    BlockPositionSource::encodeSource);

    public static final PositionSourceType<EntityPositionSource> ENTITY =
            define(
                    "entity",
                    EntityPositionSource::read,
                    EntityPositionSource::write,
                    EntityPositionSource::decodeSource,
                    EntityPositionSource::encodeSource);

    private PositionSourceTypes() {}

    public static VersionedRegistry<PositionSourceType<?>> getRegistry() {
        return REGISTRY;
    }

    @Internal
    public static <T extends PositionSource> PositionSourceType<T> define(
            String text,
            PacketWrapper.Reader<T> reader,
            PacketWrapper.Writer<T> writer,
            PositionSourceTypes.Decoder<T> decoder,
            PositionSourceTypes.Encoder<T> encoder) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticPositionSourceType<>(
                                result,
                                ((PacketWrapper.Reader) reader),
                                ((PacketWrapper.Writer) writer),
                                ((PositionSourceTypes.Decoder) decoder),
                                ((PositionSourceTypes.Encoder) encoder)));
    }

    @Nullable
    public static PositionSourceType<?> getByName(String text) {
        return REGISTRY.getByName(text);
    }

    public static PositionSourceType<?> getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    static {
        REGISTRY.unloadMappings();
    }

    @FunctionalInterface
    public interface Decoder<T> {

        T decode(NBTCompound nBTCompound, ClientVersion clientVersion);
    }

    @FunctionalInterface
    public interface Encoder<T> {

        void encode(T t, ClientVersion clientVersion, NBTCompound nBTCompound);
    }
}
