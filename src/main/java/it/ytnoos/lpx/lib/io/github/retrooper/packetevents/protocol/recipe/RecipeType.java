package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import org.jetbrains.annotations.Nullable;

@Deprecated
public enum RecipeType {
    CRAFTING_SHAPELESS(RecipeSerializers.CRAFTING_SHAPELESS),
    CRAFTING_SHAPED(RecipeSerializers.CRAFTING_SHAPED),
    CRAFTING_SPECIAL_ARMORDYE(RecipeSerializers.CRAFTING_SPECIAL_ARMORDYE),
    CRAFTING_SPECIAL_BOOKCLONING(RecipeSerializers.CRAFTING_SPECIAL_BOOKCLONING),
    CRAFTING_SPECIAL_MAPCLONING(RecipeSerializers.CRAFTING_SPECIAL_MAPCLONING),
    CRAFTING_SPECIAL_MAPEXTENDING(RecipeSerializers.CRAFTING_SPECIAL_MAPEXTENDING),
    CRAFTING_SPECIAL_FIREWORK_ROCKET(RecipeSerializers.CRAFTING_SPECIAL_FIREWORK_ROCKET),
    CRAFTING_SPECIAL_FIREWORK_STAR(RecipeSerializers.CRAFTING_SPECIAL_FIREWORK_STAR),
    CRAFTING_SPECIAL_FIREWORK_STAR_FADE(RecipeSerializers.CRAFTING_SPECIAL_FIREWORK_STAR_FADE),
    CRAFTING_SPECIAL_REPAIRITEM(RecipeSerializers.CRAFTING_SPECIAL_REPAIRITEM),
    CRAFTING_SPECIAL_TIPPEDARROW(RecipeSerializers.CRAFTING_SPECIAL_TIPPEDARROW),
    CRAFTING_SPECIAL_BANNERDUPLICATE(RecipeSerializers.CRAFTING_SPECIAL_BANNERDUPLICATE),
    CRAFTING_SPECIAL_BANNERADDPATTERN(null),
    CRAFTING_SPECIAL_SHIELDDECORATION(RecipeSerializers.CRAFTING_SPECIAL_SHIELDDECORATION),
    CRAFTING_SPECIAL_SHULKERBOXCOLORING(RecipeSerializers.CRAFTING_SPECIAL_SHULKERBOXCOLORING),
    CRAFTING_SPECIAL_SUSPICIOUSSTEW(RecipeSerializers.CRAFTING_SPECIAL_SUSPICIOUSSTEW),
    SMELTING(RecipeSerializers.SMELTING),
    BLASTING(RecipeSerializers.BLASTING),
    SMOKING(RecipeSerializers.SMOKING),
    CAMPFIRE_COOKING(RecipeSerializers.CAMPFIRE_COOKING),
    STONECUTTING(RecipeSerializers.STONECUTTING),
    SMITHING(RecipeSerializers.SMITHING_TRANSFORM);

    @Nullable private final RecipeSerializer<?> serializer;

    RecipeType(@Nullable RecipeSerializer<?> recipeSerializer) {
        this.serializer = recipeSerializer;
    }

    public RecipeSerializer<?> getSerializer() {
        if (this.serializer == null) {
            throw new UnsupportedOperationException(
                    "No serializer found for legacy recipe type " + this);
        } else {
            return this.serializer;
        }
    }
}
