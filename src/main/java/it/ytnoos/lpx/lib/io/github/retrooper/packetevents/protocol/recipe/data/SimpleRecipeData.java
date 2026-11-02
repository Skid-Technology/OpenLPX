package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.CraftingCategory;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class SimpleRecipeData implements RecipeData {

    private final CraftingCategory category;

    public SimpleRecipeData(CraftingCategory craftingCategory) {
        this.category = craftingCategory;
    }

    public static SimpleRecipeData read(PacketWrapper<?> packetWrapper) {
        CraftingCategory craftingCategory =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_3)
                        ? packetWrapper.readEnum(CraftingCategory.values())
                        : CraftingCategory.MISC;
        return new SimpleRecipeData(craftingCategory);
    }

    public static void write(PacketWrapper<?> packetWrapper, SimpleRecipeData simpleRecipeData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            packetWrapper.writeEnum(simpleRecipeData.getCategory());
        }
    }

    public CraftingCategory getCategory() {
        return this.category;
    }
}
