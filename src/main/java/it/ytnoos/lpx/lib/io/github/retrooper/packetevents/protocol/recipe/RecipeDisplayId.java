package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public final class RecipeDisplayId {

    private final int id;

    public RecipeDisplayId(int id) {
        this.id = id;
    }

    public static RecipeDisplayId read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        return new RecipeDisplayId(value);
    }

    public static void write(PacketWrapper<?> packetWrapper, RecipeDisplayId recipeDisplayId) {
        packetWrapper.writeVarInt(recipeDisplayId.id);
    }

    public int getId() {
        return this.id;
    }
}
