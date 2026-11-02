package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.RecipeDisplayId;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.RecipeDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayServerCraftRecipeResponse
        extends PacketWrapper<WrapperPlayServerCraftRecipeResponse> {

    private int windowId;

    @Obsolete private RecipeDisplayId recipeId;

    @Obsolete private ResourceLocation recipeKey;

    private RecipeDisplay<?> recipeDisplay;

    public WrapperPlayServerCraftRecipeResponse(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerCraftRecipeResponse(int value, int value2) {
        this(value, new RecipeDisplayId(value2));
    }

    public WrapperPlayServerCraftRecipeResponse(int windowId, RecipeDisplayId recipeDisplayId) {
        super(PacketType.Play.Server.CRAFT_RECIPE_RESPONSE);
        this.windowId = windowId;
        this.recipeId = recipeDisplayId;
    }

    public WrapperPlayServerCraftRecipeResponse(int value, String text) {
        this(value, new ResourceLocation(text));
    }

    public WrapperPlayServerCraftRecipeResponse(int windowId, ResourceLocation resourceLocation) {
        super(PacketType.Play.Server.CRAFT_RECIPE_RESPONSE);
        this.windowId = windowId;
        this.recipeKey = resourceLocation;
    }

    public WrapperPlayServerCraftRecipeResponse(int windowId, RecipeDisplay<?> recipeDisplay2) {
        super(PacketType.Play.Server.CRAFT_RECIPE_RESPONSE);
        this.windowId = windowId;
        this.recipeDisplay = recipeDisplay2;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.windowId = this.readContainerId();
            this.recipeDisplay = RecipeDisplay.read(this);
        } else {
            this.windowId = this.readByte();
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
                this.recipeKey = this.readIdentifier();
            } else {
                this.recipeId = RecipeDisplayId.read(this);
            }
        }
    }

    @Override
    public void write() {
        this.writeContainerId(this.windowId);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            RecipeDisplay.write(this, this.recipeDisplay);
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.writeIdentifier(this.recipeKey);
        } else {
            RecipeDisplayId.write(this, this.recipeId);
        }
    }

    public void copy(WrapperPlayServerCraftRecipeResponse wrapperPlayServerCraftRecipeResponse) {
        this.windowId = wrapperPlayServerCraftRecipeResponse.windowId;
        this.recipeId = wrapperPlayServerCraftRecipeResponse.recipeId;
        this.recipeKey = wrapperPlayServerCraftRecipeResponse.recipeKey;
        this.recipeDisplay = wrapperPlayServerCraftRecipeResponse.recipeDisplay;
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

    public RecipeDisplay<?> getRecipeDisplay() {
        return this.recipeDisplay;
    }

    public void setRecipeDisplay(RecipeDisplay<?> recipeDisplay2) {
        this.recipeDisplay = recipeDisplay2;
    }
}
