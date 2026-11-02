package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.RecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class Recipe<T extends RecipeData> {

    private final ResourceLocation key;

    private final RecipeSerializer<T> serializer;

    private final T data;

    @Deprecated
    public Recipe(RecipeType recipeType, String text, RecipeData recipeData) {
        this(
                new ResourceLocation(text),
                (RecipeSerializer<T>) recipeType.getSerializer(),
                (T) recipeData);
    }

    public Recipe(ResourceLocation resourceLocation, RecipeSerializer<T> recipeSerializer, T t) {
        this.key = resourceLocation;
        this.serializer = recipeSerializer;
        this.data = (T) t;
    }

    public static Recipe<?> read(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation;
        RecipeSerializer recipeSerializer;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            resourceLocation = packetWrapper.readIdentifier();
            recipeSerializer = packetWrapper.readMappedEntity(RecipeSerializers::getById);
        } else {
            recipeSerializer =
                    RecipeSerializers.getByName(packetWrapper.readIdentifier().toString());
            resourceLocation = packetWrapper.readIdentifier();
        }
        return read(packetWrapper, resourceLocation, recipeSerializer);
    }

    private static <T extends RecipeData> Recipe<T> read(
            PacketWrapper<?> packetWrapper,
            ResourceLocation resourceLocation,
            RecipeSerializer<T> recipeSerializer) {
        RecipeData recipeData = recipeSerializer.read(packetWrapper);
        return new Recipe<>(resourceLocation, recipeSerializer, (T) recipeData);
    }

    public static <T extends RecipeData> void write(
            PacketWrapper<?> packetWrapper, Recipe<T> recipe) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            packetWrapper.writeIdentifier(recipe.key);
            packetWrapper.writeMappedEntity(recipe.serializer);
        } else {
            packetWrapper.writeIdentifier(recipe.serializer.getName());
            packetWrapper.writeIdentifier(recipe.key);
        }
        recipe.serializer.write(packetWrapper, recipe.data);
    }

    @Deprecated
    public RecipeType getType() {
        return this.serializer.getLegacyType();
    }

    public String getIdentifier() {
        return this.key.toString();
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    public RecipeSerializer<T> getSerializer() {
        return this.serializer;
    }

    public T getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "Recipe{key="
                + this.key
                + ", serializer="
                + this.serializer
                + ", data="
                + this.data
                + 125;
    }
}
