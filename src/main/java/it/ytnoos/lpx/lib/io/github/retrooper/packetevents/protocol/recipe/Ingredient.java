package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class Ingredient {

    private final ItemStack[] options;

    public Ingredient(ItemStack... itemStack) {
        this.options = itemStack;
    }

    public static Ingredient read(PacketWrapper<?> packetWrapper) {
        ItemStack[] itemStacks =
                packetWrapper.readArray(PacketWrapper::readItemStack, ItemStack.class);
        return new Ingredient(itemStacks);
    }

    public static void write(PacketWrapper<?> packetWrapper, Ingredient ingredient) {
        packetWrapper.writeArray(ingredient.options, PacketWrapper::writeItemStack);
    }

    public ItemStack[] getOptions() {
        return this.options;
    }
}
