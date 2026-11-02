package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.score;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class ScoreFormatTypes {

    private static final VersionedRegistry<ScoreFormatType<?>> REGISTRY =
            new VersionedRegistry<>("number_format_type");

    public static final ScoreFormatType<BlankScoreFormat> BLANK =
            define("blank", BlankScoreFormat::read, BlankScoreFormat::write);

    public static final ScoreFormatType<StyledScoreFormat> STYLED =
            define("styled", StyledScoreFormat::read, StyledScoreFormat::write);

    public static final ScoreFormatType<FixedScoreFormat> FIXED =
            define("fixed", FixedScoreFormat::read, FixedScoreFormat::write);

    private ScoreFormatTypes() {}

    public static VersionedRegistry<ScoreFormatType<?>> getRegistry() {
        return REGISTRY;
    }

    public static Collection<ScoreFormatType<?>> values() {
        return REGISTRY.getEntries();
    }

    @Deprecated
    public static ScoreFormat read(PacketWrapper<?> packetWrapper) {
        return ScoreFormat.readTyped(packetWrapper);
    }

    @Deprecated
    public static void write(PacketWrapper<?> packetWrapper, ScoreFormat scoreFormat) {
        ScoreFormat.writeTyped(packetWrapper, scoreFormat);
    }

    @Deprecated
    @Internal
    public static <T extends ScoreFormat> ScoreFormatType<T> define(
            int value,
            String text,
            Class<T> classValue,
            Function<PacketWrapper<?>, T> function,
            BiConsumer<PacketWrapper<?>, T> biConsumer) {
        PacketWrapper.Reader<T> reader = function::apply;
        return define(text, reader, biConsumer::accept);
    }

    @Internal
    public static <T extends ScoreFormat> ScoreFormatType<T> define(
            String text, PacketWrapper.Reader<T> reader, PacketWrapper.Writer<T> writer) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticScoreFormatType<>(
                                result,
                                ((PacketWrapper.Reader) reader),
                                ((PacketWrapper.Writer) writer)));
    }

    @Nullable
    public static ScoreFormatType<?> getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    @Nullable
    public static ScoreFormatType<?> getByName(String text) {
        return REGISTRY.getByName(text);
    }

    @Nullable
    public static ScoreFormatType<?> getByName(ResourceLocation resourceLocation) {
        return REGISTRY.getByName(resourceLocation);
    }

    static {
        REGISTRY.unloadMappings();
    }
}
