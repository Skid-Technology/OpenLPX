package it.ytnoos.lpx.utils;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.HashedStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;

import java.util.Optional;

public class ItemUtils {

    private ItemUtils() {
        UnsupportedOperationException unsupportedOperationException =
                new UnsupportedOperationException("This class cannot be instantiated");
        throw unsupportedOperationException;
    }

    public static Optional<String> getDisplayName(ItemStack itemStack) {
        NBTCompound nBT = itemStack.getNBT();
        if (nBT == null) {
            return Optional.empty();
        }
        NBTCompound compoundTagOrNull = nBT.getCompoundTagOrNull("display");
        return compoundTagOrNull == null
                ? Optional.empty()
                : Optional.ofNullable(compoundTagOrNull.getStringTagValueOrNull("Name"));
    }

    public static Optional<
                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt
                                    .NBTString>>
            createOptional(ItemStack itemStack) {
        NBTCompound nBT = itemStack.getNBT();
        if (nBT == null) {
            return Optional.empty();
        }
        NBTCompound compoundTagOrNull = nBT.getCompoundTagOrNull("display");
        return compoundTagOrNull == null
                ? Optional.empty()
                : Optional.ofNullable(compoundTagOrNull.getStringListTagOrNull("Lore"));
    }

    public static boolean testItemStack(ItemStack itemStack) {
        boolean empty = itemStack.getComponents().getPatches().isEmpty();
        NBTCompound nBT = itemStack.getNBT();
        boolean matches = nBT == null || nBT.isEmpty();
        return empty && matches;
    }

    public static boolean isEmpty(HashedStack hashedStack) {
        return hashedStack.getComponents().getAddedComponents().isEmpty();
    }
}
