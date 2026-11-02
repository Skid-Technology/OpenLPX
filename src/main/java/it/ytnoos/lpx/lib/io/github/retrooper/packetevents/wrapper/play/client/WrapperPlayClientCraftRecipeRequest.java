package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.RecipeDisplayId;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayClientCraftRecipeRequest
        extends PacketWrapper<WrapperPlayClientCraftRecipeRequest> {

    private int windowId;

    @Obsolete private ResourceLocation recipeKey;

    private RecipeDisplayId recipeId;

    private boolean makeAll;

    public WrapperPlayClientCraftRecipeRequest(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientCraftRecipeRequest(int value, int value2, boolean matches) {
        this(value, new RecipeDisplayId(value2), matches);
    }

    public WrapperPlayClientCraftRecipeRequest(
            int windowId, RecipeDisplayId recipeDisplayId, boolean makeAll) {
        super(PacketType.Play.Client.CRAFT_RECIPE_REQUEST);
        this.windowId = windowId;
        this.recipeId = recipeDisplayId;
        this.makeAll = makeAll;
    }

    public WrapperPlayClientCraftRecipeRequest(int value, String text, boolean matches) {
        this(value, new ResourceLocation(text), matches);
    }

    public WrapperPlayClientCraftRecipeRequest(
            int windowId, ResourceLocation resourceLocation, boolean makeAll) {
        super(PacketType.Play.Client.CRAFT_RECIPE_REQUEST);
        this.windowId = windowId;
        this.recipeKey = resourceLocation;
        this.makeAll = makeAll;
    }

    @Override
    public void read() {
        this.windowId = this.readContainerId();
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_21_2)
                && this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.recipeKey = this.readIdentifier();
        } else {
            this.recipeId = RecipeDisplayId.read(this);
        }
        this.makeAll = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeContainerId(this.windowId);
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_21_2)
                && this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.writeIdentifier(this.recipeKey);
        } else {
            RecipeDisplayId.write(this, this.recipeId);
        }
        this.writeBoolean(this.makeAll);
    }

    public void copy(WrapperPlayClientCraftRecipeRequest wrapperPlayClientCraftRecipeRequest) {
        this.windowId = wrapperPlayClientCraftRecipeRequest.windowId;
        this.recipeId = wrapperPlayClientCraftRecipeRequest.recipeId;
        this.recipeKey = wrapperPlayClientCraftRecipeRequest.recipeKey;
        this.makeAll = wrapperPlayClientCraftRecipeRequest.makeAll;
    }

    public int getWindowId() {
        return this.windowId;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
    }

    @Deprecated
    public <T> T getRecipe() {
        return (T)
                (this.serverVersion.isOlderThan(ServerVersion.V_1_21_2)
                                && this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)
                        ? this.recipeKey
                        : this.recipeId.getId());
    }

    @Deprecated
    public <T> void setRecipe(T t) {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.recipeKey = new ResourceLocation((String) t);
        } else {
            this.recipeId = new RecipeDisplayId((Integer) t);
        }
    }

    public ResourceLocation getRecipeKey() {
        return this.recipeKey;
    }

    public void setRecipeKey(ResourceLocation resourceLocation) {
        this.recipeKey = resourceLocation;
    }

    public RecipeDisplayId getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(RecipeDisplayId recipeDisplayId) {
        this.recipeId = recipeDisplayId;
    }

    public boolean isMakeAll() {
        return this.makeAll;
    }

    public void setMakeAll(boolean makeAll) {
        this.makeAll = makeAll;
    }
}
