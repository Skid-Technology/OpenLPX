package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public abstract class RecipeDisplay<T extends RecipeDisplay<?>> {

    protected final RecipeDisplayType<T> type;

    public RecipeDisplay(RecipeDisplayType<T> recipeDisplayType) {
        this.type = recipeDisplayType;
    }

    public static RecipeDisplay<?> read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(RecipeDisplayTypes.getRegistry()).read(packetWrapper);
    }

    public static <T extends RecipeDisplay<?>> void write(PacketWrapper<?> packetWrapper, T t) {
        packetWrapper.writeMappedEntity(t.getType());
        ((RecipeDisplayType) t.getType()).write(packetWrapper, t);
    }

    public RecipeDisplayType<T> getType() {
        return this.type;
    }
}
