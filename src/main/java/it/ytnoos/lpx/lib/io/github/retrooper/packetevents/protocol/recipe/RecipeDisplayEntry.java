package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.category.RecipeBookCategories;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.category.RecipeBookCategory;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.RecipeDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public final class RecipeDisplayEntry {

    private RecipeDisplayId id;

    private RecipeDisplay<?> display;

    @Nullable private Integer group;

    private RecipeBookCategory category;

    @Nullable private List<MappedEntitySet<ItemType>> ingredients;

    public RecipeDisplayEntry(
            RecipeDisplayId recipeDisplayId,
            RecipeDisplay<?> recipeDisplay,
            @Nullable Integer group,
            RecipeBookCategory recipeBookCategory,
            @Nullable List<MappedEntitySet<ItemType>> ingredients) {
        this.id = recipeDisplayId;
        this.display = recipeDisplay;
        this.group = group;
        this.category = recipeBookCategory;
        this.ingredients = ingredients;
    }

    public static RecipeDisplayEntry read(PacketWrapper<?> packetWrapper) {
        RecipeDisplayId recipeDisplayId = RecipeDisplayId.read(packetWrapper);
        RecipeDisplay recipeDisplay = RecipeDisplay.read(packetWrapper);
        Integer value = packetWrapper.readNullableVarInt();
        RecipeBookCategory recipeBookCategory =
                packetWrapper.readMappedEntity(RecipeBookCategories.getRegistry());
        List values =
                packetWrapper.readOptional(
                        result ->
                                result.readList(
                                        result2 ->
                                                MappedEntitySet.read(
                                                        result2, ItemTypes.getRegistry())));
        return new RecipeDisplayEntry(
                recipeDisplayId, recipeDisplay, value, recipeBookCategory, values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, RecipeDisplayEntry recipeDisplayEntry) {
        RecipeDisplayId.write(packetWrapper, recipeDisplayEntry.id);
        RecipeDisplay.write(packetWrapper, recipeDisplayEntry.display);
        packetWrapper.writeNullableVarInt(recipeDisplayEntry.group);
        packetWrapper.writeMappedEntity(recipeDisplayEntry.category);
        packetWrapper.writeOptional(
                recipeDisplayEntry.ingredients,
                (result, result2) -> result.writeList(result2, MappedEntitySet::write));
    }

    public RecipeDisplayId getId() {
        return this.id;
    }

    public void setId(RecipeDisplayId recipeDisplayId) {
        this.id = recipeDisplayId;
    }

    public RecipeDisplay<?> getDisplay() {
        return this.display;
    }

    public void setDisplay(RecipeDisplay<?> recipeDisplay) {
        this.display = recipeDisplay;
    }

    @Nullable
    public Integer getGroup() {
        return this.group;
    }

    public void setGroup(@Nullable Integer group) {
        this.group = group;
    }

    public RecipeBookCategory getCategory() {
        return this.category;
    }

    public void setCategory(RecipeBookCategory recipeBookCategory) {
        this.category = recipeBookCategory;
    }

    @Nullable
    public List<MappedEntitySet<ItemType>> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(@Nullable List<MappedEntitySet<ItemType>> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof RecipeDisplayEntry)) {
            return false;
        } else {
            RecipeDisplayEntry recipeDisplayEntry = (RecipeDisplayEntry) result;
            if (this.id != recipeDisplayEntry.id) {
                return false;
            } else if (!this.display.equals(recipeDisplayEntry.display)) {
                return false;
            } else if (!Objects.equals(this.group, recipeDisplayEntry.group)) {
                return false;
            } else {
                return !this.category.equals(recipeDisplayEntry.category)
                        ? false
                        : Objects.equals(this.ingredients, recipeDisplayEntry.ingredients);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.display, this.group, this.category, this.ingredients);
    }

    @Override
    public String toString() {
        return "RecipeDisplayEntry{id="
                + this.id
                + ", display="
                + this.display
                + ", group="
                + this.group
                + ", category="
                + this.category
                + ", ingredients="
                + this.ingredients
                + 125;
    }
}
