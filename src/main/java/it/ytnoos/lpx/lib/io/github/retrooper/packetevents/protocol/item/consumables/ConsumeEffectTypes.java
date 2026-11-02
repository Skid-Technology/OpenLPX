package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin.ApplyEffectsConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin.ClearAllEffectsConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin.PlaySoundConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin.RemoveEffectsConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin.TeleportRandomlyConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class ConsumeEffectTypes {

    private static final VersionedRegistry<ConsumeEffectType<?>> REGISTRY =
            new VersionedRegistry<>("consume_effect_type");

    public static final ConsumeEffectType<ApplyEffectsConsumeEffect> APPLY_EFFECTS =
            define(
                    "apply_effects",
                    ApplyEffectsConsumeEffect::read,
                    ApplyEffectsConsumeEffect::write);

    public static final ConsumeEffectType<RemoveEffectsConsumeEffect> REMOVE_EFFECTS =
            define(
                    "remove_effects",
                    RemoveEffectsConsumeEffect::read,
                    RemoveEffectsConsumeEffect::write);

    public static final ConsumeEffectType<ClearAllEffectsConsumeEffect> CLEAR_ALL_EFFECTS =
            define(
                    "clear_all_effects",
                    ClearAllEffectsConsumeEffect::read,
                    ClearAllEffectsConsumeEffect::write);

    public static final ConsumeEffectType<TeleportRandomlyConsumeEffect> TELEPORT_RANDOMLY =
            define(
                    "teleport_randomly",
                    TeleportRandomlyConsumeEffect::read,
                    TeleportRandomlyConsumeEffect::write);

    public static final ConsumeEffectType<PlaySoundConsumeEffect> PLAY_SOUND =
            define("play_sound", PlaySoundConsumeEffect::read, PlaySoundConsumeEffect::write);

    private ConsumeEffectTypes() {}

    @Internal
    public static <T extends ConsumeEffect<?>> ConsumeEffectType<T> define(
            String text, PacketWrapper.Reader<T> reader, PacketWrapper.Writer<T> writer) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticConsumeEffectType<>(
                                result,
                                ((PacketWrapper.Reader) reader),
                                ((PacketWrapper.Writer) writer)));
    }

    public static VersionedRegistry<ConsumeEffectType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
