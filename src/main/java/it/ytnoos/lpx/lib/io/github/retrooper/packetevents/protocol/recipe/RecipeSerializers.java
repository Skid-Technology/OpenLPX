package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.CookedRecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.RecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.ShapedRecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.ShapelessRecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.SimpleRecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.SmithingRecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.SmithingTrimRecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.StoneCuttingRecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@Obsolete
public final class RecipeSerializers {

    private static final VersionedRegistry<RecipeSerializer<?>> REGISTRY =
            new VersionedRegistry<>("legacy_recipe_serializer");

    public static final RecipeSerializer<ShapedRecipeData> CRAFTING_SHAPED =
            define(
                    "crafting_shaped",
                    ShapedRecipeData::read,
                    ShapedRecipeData::write,
                    RecipeType.CRAFTING_SHAPED);

    public static final RecipeSerializer<ShapelessRecipeData> CRAFTING_SHAPELESS =
            define(
                    "crafting_shapeless",
                    ShapelessRecipeData::read,
                    ShapelessRecipeData::write,
                    RecipeType.CRAFTING_SHAPELESS);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_ARMORDYE =
            define(
                    "crafting_special_armordye",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_ARMORDYE);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_BOOKCLONING =
            define(
                    "crafting_special_bookcloning",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_BOOKCLONING);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_MAPCLONING =
            define(
                    "crafting_special_mapcloning",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_MAPCLONING);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_MAPEXTENDING =
            define(
                    "crafting_special_mapextending",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_MAPEXTENDING);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_FIREWORK_ROCKET =
            define(
                    "crafting_special_firework_rocket",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_FIREWORK_ROCKET);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_FIREWORK_STAR =
            define(
                    "crafting_special_firework_star",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_FIREWORK_STAR_FADE =
            define(
                    "crafting_special_firework_star_fade",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_FIREWORK_STAR_FADE);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_TIPPEDARROW =
            define(
                    "crafting_special_tippedarrow",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_TIPPEDARROW);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_BANNERDUPLICATE =
            define(
                    "crafting_special_bannerduplicate",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_BANNERDUPLICATE);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_SHIELDDECORATION =
            define(
                    "crafting_special_shielddecoration",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_SHIELDDECORATION);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_SHULKERBOXCOLORING =
            define(
                    "crafting_special_shulkerboxcoloring",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_SHULKERBOXCOLORING);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_SUSPICIOUSSTEW =
            define(
                    "crafting_special_suspiciousstew",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_SUSPICIOUSSTEW);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_SPECIAL_REPAIRITEM =
            define(
                    "crafting_special_repairitem",
                    SimpleRecipeData::read,
                    SimpleRecipeData::write,
                    RecipeType.CRAFTING_SPECIAL_REPAIRITEM);

    public static final RecipeSerializer<CookedRecipeData> SMELTING =
            define(
                    "smelting",
                    CookedRecipeData::read,
                    CookedRecipeData::write,
                    RecipeType.SMELTING);

    public static final RecipeSerializer<CookedRecipeData> BLASTING =
            define(
                    "blasting",
                    CookedRecipeData::read,
                    CookedRecipeData::write,
                    RecipeType.BLASTING);

    public static final RecipeSerializer<CookedRecipeData> SMOKING =
            define("smoking", CookedRecipeData::read, CookedRecipeData::write, RecipeType.SMOKING);

    public static final RecipeSerializer<CookedRecipeData> CAMPFIRE_COOKING =
            define(
                    "campfire_cooking",
                    CookedRecipeData::read,
                    CookedRecipeData::write,
                    RecipeType.CAMPFIRE_COOKING);

    public static final RecipeSerializer<StoneCuttingRecipeData> STONECUTTING =
            define(
                    "stonecutting",
                    StoneCuttingRecipeData::read,
                    StoneCuttingRecipeData::write,
                    RecipeType.STONECUTTING);

    @Obsolete
    public static final RecipeSerializer<SmithingRecipeData> SMITHING =
            define(
                    "smithing",
                    result -> SmithingRecipeData.read(result, true),
                    (result, result2) -> SmithingRecipeData.write(result, result2, true),
                    RecipeType.SMITHING);

    public static final RecipeSerializer<SmithingRecipeData> SMITHING_TRANSFORM =
            define(
                    "smithing_transform",
                    SmithingRecipeData::read,
                    SmithingRecipeData::write,
                    RecipeType.SMITHING);

    public static final RecipeSerializer<SmithingTrimRecipeData> SMITHING_TRIM =
            define("smithing_trim", SmithingTrimRecipeData::read, SmithingTrimRecipeData::write);

    public static final RecipeSerializer<SimpleRecipeData> CRAFTING_DECORATED_POT =
            define("crafting_decorated_pot", SimpleRecipeData::read, SimpleRecipeData::write);

    private static final RecipeSerializer<SimpleRecipeData> CRAFTING_IMBUE =
            define("crafting_imbue", SimpleRecipeData::read, SimpleRecipeData::write);

    private static final RecipeSerializer<SimpleRecipeData> CRAFTING_DYE =
            define("crafting_dye", SimpleRecipeData::read, SimpleRecipeData::write);

    private static final RecipeSerializer<SimpleRecipeData> CRAFTING_TRANSMUTE =
            define("crafting_transmute", SimpleRecipeData::read, SimpleRecipeData::write);

    private RecipeSerializers() {}

    public static VersionedRegistry<RecipeSerializer<?>> getRegistry() {
        return REGISTRY;
    }

    @Internal
    public static <T extends RecipeData> RecipeSerializer<T> define(
            String text, PacketWrapper.Reader<T> reader, PacketWrapper.Writer<T> writer) {
        return define(text, reader, writer, null);
    }

    @Internal
    public static <T extends RecipeData> RecipeSerializer<T> define(
            String text,
            PacketWrapper.Reader<T> reader,
            PacketWrapper.Writer<T> writer,
            @Nullable RecipeType recipeType) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticRecipeSerializer<>(
                                result,
                                ((PacketWrapper.Reader) reader),
                                ((PacketWrapper.Writer) writer),
                                recipeType));
    }

    public static RecipeSerializer<?> getByName(String text) {
        return REGISTRY.getByName(text);
    }

    public static RecipeSerializer<?> getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static Collection<RecipeSerializer<?>> values() {
        return REGISTRY.getEntries();
    }

    static {
        REGISTRY.unloadMappings();
    }
}
