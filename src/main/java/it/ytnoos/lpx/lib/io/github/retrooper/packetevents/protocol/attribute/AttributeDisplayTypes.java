package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class AttributeDisplayTypes {

    private static final VersionedRegistry<AttributeDisplayType<?>> REGISTRY =
            new VersionedRegistry<>("attribute_display_type");

    public static final AttributeDisplayType<DefaultAttributeDisplay> DEFAULT =
            define("default", DefaultAttributeDisplay::read, DefaultAttributeDisplay::write);

    public static final AttributeDisplayType<HiddenAttributeDisplay> HIDDEN =
            define("hidden", HiddenAttributeDisplay::read, HiddenAttributeDisplay::write);

    public static final AttributeDisplayType<OverrideAttributeDisplay> OVERRIDE =
            define("override", OverrideAttributeDisplay::read, OverrideAttributeDisplay::write);

    private AttributeDisplayTypes() {}

    @Internal
    public static <T extends AttributeDisplay> AttributeDisplayType<T> define(
            String text, PacketWrapper.Reader<T> reader, PacketWrapper.Writer<T> writer) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticAttributeDisplayType<>(
                                result,
                                ((PacketWrapper.Reader) reader),
                                ((PacketWrapper.Writer) writer)));
    }

    public static VersionedRegistry<AttributeDisplayType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
