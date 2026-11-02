package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.AlphaColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.Particle;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers.AttributeModifier;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.BiomeEffects;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@NullMarked
public final class AttributeTypes {

    private static final VersionedRegistry<AttributeType<?>> REGISTRY =
            new VersionedRegistry<>("attribute_type");

    public static final AttributeType<Boolean> BOOLEAN =
            define("boolean", NbtCodecs.BOOLEAN, AttributeModifier.BOOLEAN_LIBRARY);

    @Obsolete public static final AttributeType<TriState> TRI_STATE = defineUnsynced("tri_state");

    public static final AttributeType<Float> FLOAT =
            define("float", NbtCodecs.FLOAT, AttributeModifier.FLOAT_LIBRARY);

    public static final AttributeType<Float> ANGLE_DEGREES =
            define("angle_degrees", NbtCodecs.FLOAT, AttributeModifier.FLOAT_LIBRARY);

    public static final AttributeType<Color> RGB_COLOR =
            define("rgb_color", NbtCodecs.RGB_COLOR, AttributeModifier.RGB_COLOR_LIBRARY);

    public static final AttributeType<AlphaColor> ARGB_COLOR =
            define("argb_color", NbtCodecs.ARGB_COLOR, AttributeModifier.ARGB_COLOR_LIBRARY);

    public static final AttributeType<Integer> INTEGER =
            define("integer", NbtCodecs.INT, AttributeModifier.INTEGER_LIBRARY);

    public static final AttributeType<MoonPhase> MOON_PHASE = define("moon_phase", MoonPhase.CODEC);

    @Obsolete public static final AttributeType<?> ACTIVITY = defineUnsynced("activity");

    @Obsolete public static final AttributeType<?> BED_RULE = defineUnsynced("bed_rule");

    public static final AttributeType<Particle<?>> PARTICLE = define("particle", Particle.CODEC);

    public static final AttributeType<List<BiomeEffects.ParticleSettings>> AMBIENT_PARTICLES =
            define(
                    "ambient_particles",
                    BiomeEffects.ParticleSettings.CODEC.applyList(),
                    AttributeModifier.listLibrary());

    public static final AttributeType<BackgroundMusic> BACKGROUND_MUSIC =
            define("background_music", BackgroundMusic.CODEC);

    public static final AttributeType<AmbientSounds> AMBIENT_SOUNDS =
            define("ambient_sounds", AmbientSounds.CODEC);

    @Obsolete
    public static final AttributeType<?> MOB_SPAWN_SETTINGS = defineUnsynced("mob_spawn_settings");

    private AttributeTypes() {}

    @Internal
    public static <T> AttributeType<T> defineUnsynced(String text) {
        return define(text, null, Collections.emptyMap());
    }

    @Internal
    public static <T> AttributeType<T> define(String text, NbtCodec<T> nbtCodec) {
        return define(text, nbtCodec, Collections.emptyMap());
    }

    @Internal
    public static <T> AttributeType<T> define(
            String text,
            @Nullable NbtCodec<T> nbtCodec,
            Map<AttributeModifier.Operation, AttributeModifier<T, ?>> entries) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticAttributeType<>(
                                result,
                                ((NbtCodec) nbtCodec),
                                createModifierCodec(((Map) entries))));
    }

    @Internal
    public static <T> NbtCodec<AttributeModifier<T, ?>> createModifierCodec(
            final Map<AttributeModifier.Operation, AttributeModifier<T, ?>> entries) {
        final HashMap entries2;
        (entries2 = new HashMap(entries.size() + 1))
                .put(AttributeModifier.Operation.OVERRIDE, AttributeModifier.override());
        entries2.putAll(entries);
        final HashMap entries3 = new HashMap(entries.size() + 1);
        for (Entry result3 : ((Iterable<Entry>) ((Iterable<?>) entries2.entrySet()))) {
            entries3.put(
                    (AttributeModifier) result3.getValue(),
                    (AttributeModifier.Operation) result3.getKey());
        }
        return new NbtCodec<AttributeModifier<T, ?>>() {

            public AttributeModifier<T, ?> decode(NBT result, PacketWrapper<?> result2)
                    throws NbtCodecException {
                AttributeModifier.Operation operation =
                        AttributeModifier.Operation.CODEC.decode(result, result2);
                AttributeModifier result3;
                if ((result3 = (AttributeModifier) entries2.get(operation)) == null) {
                    throw new NbtCodecException(
                            "Unsupported operation " + operation + " for " + entries);
                } else {
                    return result3;
                }
            }

            public NBT encode(PacketWrapper<?> result, AttributeModifier<T, ?> result2)
                    throws NbtCodecException {
                AttributeModifier.Operation operation2;
                if ((operation2 = (AttributeModifier.Operation) entries3.get(result2)) == null) {
                    throw new NbtCodecException(
                            "Unsupported modifier " + result2 + " for " + entries);
                } else {
                    return AttributeModifier.Operation.CODEC.encode(result, operation2);
                }
            }
        };
    }

    public static VersionedRegistry<AttributeType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
