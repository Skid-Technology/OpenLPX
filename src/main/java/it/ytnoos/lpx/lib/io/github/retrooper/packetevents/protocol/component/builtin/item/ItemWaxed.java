package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ItemWaxed {

    public static final ItemWaxed INSTANCE = new ItemWaxed();

    private ItemWaxed() {}

    public static ItemWaxed read(PacketWrapper<?> packetWrapper) {
        return INSTANCE;
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemWaxed itemWaxed) {}
}
