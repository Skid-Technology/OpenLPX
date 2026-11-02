package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.RecipeBookSettings;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerRecipeBookSettings
        extends PacketWrapper<WrapperPlayServerRecipeBookSettings> {

    private RecipeBookSettings settings;

    public WrapperPlayServerRecipeBookSettings(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerRecipeBookSettings(RecipeBookSettings recipeBookSettings) {
        super(PacketType.Play.Server.RECIPE_BOOK_SETTINGS);
        this.settings = recipeBookSettings;
    }

    @Override
    public void read() {
        this.settings = RecipeBookSettings.read(this);
    }

    @Override
    public void write() {
        RecipeBookSettings.write(this, this.settings);
    }

    public void copy(WrapperPlayServerRecipeBookSettings wrapperPlayServerRecipeBookSettings) {
        this.settings = wrapperPlayServerRecipeBookSettings.settings;
    }

    public RecipeBookSettings getSettings() {
        return this.settings;
    }

    public void setSettings(RecipeBookSettings recipeBookSettings) {
        this.settings = recipeBookSettings;
    }
}
