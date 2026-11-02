package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.Recipe;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.RecipePropertySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.SingleInputOptionDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.List;
import java.util.Map;

public class WrapperPlayServerDeclareRecipes
        extends PacketWrapper<WrapperPlayServerDeclareRecipes> {

    @Obsolete private Recipe<?>[] recipes;

    private Map<ResourceLocation, RecipePropertySet> itemSets;

    private List<SingleInputOptionDisplay> stonecutterRecipes;

    public WrapperPlayServerDeclareRecipes(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Obsolete
    public WrapperPlayServerDeclareRecipes(Recipe<?>[] recipes2) {
        super(PacketType.Play.Server.DECLARE_RECIPES);
        this.recipes = recipes2;
    }

    public WrapperPlayServerDeclareRecipes(
            Map<ResourceLocation, RecipePropertySet> entries,
            List<SingleInputOptionDisplay> stonecutterRecipes) {
        super(PacketType.Play.Server.DECLARE_RECIPES);
        this.itemSets = entries;
        this.stonecutterRecipes = stonecutterRecipes;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.itemSets = this.readMap(PacketWrapper::readIdentifier, RecipePropertySet::read);
            this.stonecutterRecipes = this.readList(SingleInputOptionDisplay::read);
        } else {
            this.recipes = this.readArray(Recipe::read, Recipe.class);
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeMap(this.itemSets, PacketWrapper::writeIdentifier, RecipePropertySet::write);
            this.writeList(this.stonecutterRecipes, SingleInputOptionDisplay::write);
        } else {
            this.writeArray(this.recipes, Recipe::write);
        }
    }

    public void copy(WrapperPlayServerDeclareRecipes wrapperPlayServerDeclareRecipes) {
        this.recipes = wrapperPlayServerDeclareRecipes.recipes;
        this.itemSets = wrapperPlayServerDeclareRecipes.itemSets;
        this.stonecutterRecipes = wrapperPlayServerDeclareRecipes.stonecutterRecipes;
    }

    @Obsolete
    public Recipe<?>[] getRecipes() {
        return this.recipes;
    }

    @Obsolete
    public void setRecipes(Recipe<?>[] recipes2) {
        this.recipes = recipes2;
    }

    public Map<ResourceLocation, RecipePropertySet> getItemSets() {
        return this.itemSets;
    }

    public void setItemSets(Map<ResourceLocation, RecipePropertySet> entries) {
        this.itemSets = entries;
    }

    public List<SingleInputOptionDisplay> getStonecutterRecipes() {
        return this.stonecutterRecipes;
    }

    public void setStonecutterRecipes(List<SingleInputOptionDisplay> stonecutterRecipes) {
        this.stonecutterRecipes = stonecutterRecipes;
    }
}
