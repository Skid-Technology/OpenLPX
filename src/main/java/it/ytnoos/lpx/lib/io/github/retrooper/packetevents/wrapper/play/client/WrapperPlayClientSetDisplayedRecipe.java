package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.RecipeDisplayId;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayClientSetDisplayedRecipe
        extends PacketWrapper<WrapperPlayClientSetDisplayedRecipe> {

    @Obsolete private ResourceLocation recipe;

    private RecipeDisplayId recipeId;

    public WrapperPlayClientSetDisplayedRecipe(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    @Obsolete
    public WrapperPlayClientSetDisplayedRecipe(ResourceLocation resourceLocation) {
        super(PacketType.Play.Client.SET_DISPLAYED_RECIPE);
        this.recipe = resourceLocation;
    }

    public WrapperPlayClientSetDisplayedRecipe(RecipeDisplayId recipeDisplayId) {
        super(PacketType.Play.Client.SET_DISPLAYED_RECIPE);
        this.recipeId = recipeDisplayId;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.recipeId = RecipeDisplayId.read(this);
        } else {
            this.recipe = this.readIdentifier();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            RecipeDisplayId.write(this, this.recipeId);
        } else {
            this.writeIdentifier(this.recipe);
        }
    }

    public void copy(WrapperPlayClientSetDisplayedRecipe wrapperPlayClientSetDisplayedRecipe) {
        this.recipe = wrapperPlayClientSetDisplayedRecipe.recipe;
        this.recipeId = wrapperPlayClientSetDisplayedRecipe.recipeId;
    }

    @Obsolete
    public ResourceLocation getRecipe() {
        return this.recipe;
    }

    @Obsolete
    public void setRecipe(ResourceLocation resourceLocation) {
        this.recipe = resourceLocation;
    }

    public RecipeDisplayId getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(RecipeDisplayId recipeDisplayId) {
        this.recipeId = recipeDisplayId;
    }
}
