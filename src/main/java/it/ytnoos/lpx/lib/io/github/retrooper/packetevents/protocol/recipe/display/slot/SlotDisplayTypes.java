package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public final class SlotDisplayTypes {

    private static final VersionedRegistry<SlotDisplayType<?>> REGISTRY =
            new VersionedRegistry<>("slot_display");

    public static final SlotDisplayType<EmptySlotDisplay> EMPTY =
            register("empty", EmptySlotDisplay::read, EmptySlotDisplay::write);

    public static final SlotDisplayType<AnyFuelSlotDisplay> ANY_FUEL =
            register("any_fuel", AnyFuelSlotDisplay::read, AnyFuelSlotDisplay::write);

    public static final SlotDisplayType<ItemSlotDisplay> ITEM =
            register("item", ItemSlotDisplay::read, ItemSlotDisplay::write);

    public static final SlotDisplayType<ItemStackSlotDisplay> ITEM_STACK =
            register("item_stack", ItemStackSlotDisplay::read, ItemStackSlotDisplay::write);

    public static final SlotDisplayType<TagSlotDisplay> TAG =
            register("tag", TagSlotDisplay::read, TagSlotDisplay::write);

    public static final SlotDisplayType<SmithingTrimSlotDisplay> SMITHING_TRIM =
            register(
                    "smithing_trim", SmithingTrimSlotDisplay::read, SmithingTrimSlotDisplay::write);

    public static final SlotDisplayType<WithRemainderSlotDisplay> WITH_REMAINDER =
            register(
                    "with_remainder",
                    WithRemainderSlotDisplay::read,
                    WithRemainderSlotDisplay::write);

    public static final SlotDisplayType<CompositeSlotDisplay> COMPOSITE =
            register("composite", CompositeSlotDisplay::read, CompositeSlotDisplay::write);

    public static final SlotDisplayType<WithAnyPotionSlotDisplay> WITH_ANY_POTION =
            register(
                    "with_any_potion",
                    WithAnyPotionSlotDisplay::read,
                    WithAnyPotionSlotDisplay::write);

    public static final SlotDisplayType<OnlyWithComponentSlotDisplay> ONLY_WITH_COMPONENT =
            register(
                    "only_with_component",
                    OnlyWithComponentSlotDisplay::read,
                    OnlyWithComponentSlotDisplay::write);

    public static final SlotDisplayType<DyedSlotDisplay> DYED =
            register("dyed", DyedSlotDisplay::read, DyedSlotDisplay::write);

    private SlotDisplayTypes() {}

    private static <T extends SlotDisplay<?>> SlotDisplayType<T> register(
            String text, PacketWrapper.Reader<T> reader, PacketWrapper.Writer<T> writer) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticSlotDisplayType<>(
                                result,
                                ((PacketWrapper.Reader) reader),
                                ((PacketWrapper.Writer) writer)));
    }

    public static VersionedRegistry<SlotDisplayType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
