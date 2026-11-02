package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class RecipeBookSettings {

    private final Map<RecipeBookType, RecipeBookSettings.TypeState> states;

    public RecipeBookSettings(Map<RecipeBookType, RecipeBookSettings.TypeState> entries) {
        this.states = entries;
    }

    public static RecipeBookSettings read(PacketWrapper<?> packetWrapper) {
        EnumMap enumMap = new EnumMap<>(RecipeBookType.class);
        RecipeBookType[] recipeBookTypes;
        int value = (recipeBookTypes = RecipeBookType.values()).length;
        for (int index = 0; index < value; index++) {
            RecipeBookType recipeBookType = recipeBookTypes[index];
            enumMap.put(recipeBookType, RecipeBookSettings.TypeState.read(packetWrapper));
        }
        return new RecipeBookSettings(enumMap);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, RecipeBookSettings recipeBookSettings) {
        RecipeBookType[] recipeBookTypes;
        int value = (recipeBookTypes = RecipeBookType.values()).length;
        for (int index = 0; index < value; index++) {
            RecipeBookType recipeBookType = recipeBookTypes[index];
            RecipeBookSettings.TypeState.write(
                    packetWrapper, recipeBookSettings.getState(recipeBookType));
        }
    }

    public RecipeBookSettings.TypeState getState(RecipeBookType recipeBookType) {
        return this.states.computeIfAbsent(
                recipeBookType, result -> new RecipeBookSettings.TypeState());
    }

    public void setState(RecipeBookType recipeBookType, RecipeBookSettings.TypeState typeState) {
        this.states.put(recipeBookType, typeState);
    }

    public Map<RecipeBookType, RecipeBookSettings.TypeState> getStates() {
        return this.states;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof RecipeBookSettings)) {
            return false;
        }
        RecipeBookSettings recipeBookSettings = (RecipeBookSettings) result;
        return this.states.equals(recipeBookSettings.states);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.states);
    }

    @Override
    public String toString() {
        return "RecipeBookSettings{states=" + this.states + 125;
    }

    public static final class TypeState {

        private boolean open;

        private boolean filtering;

        public TypeState() {
            this(false, false);
        }

        public TypeState(boolean open, boolean filtering) {
            this.open = open;
            this.filtering = filtering;
        }

        public static RecipeBookSettings.TypeState read(PacketWrapper<?> packetWrapper) {
            boolean matches = packetWrapper.readBoolean();
            boolean matches2 = packetWrapper.readBoolean();
            return new RecipeBookSettings.TypeState(matches, matches2);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, RecipeBookSettings.TypeState typeState) {
            packetWrapper.writeBoolean(typeState.open);
            packetWrapper.writeBoolean(typeState.filtering);
        }

        public boolean isOpen() {
            return this.open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public boolean isFiltering() {
            return this.filtering;
        }

        public void setFiltering(boolean filtering) {
            this.filtering = filtering;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof RecipeBookSettings.TypeState)) {
                return false;
            }
            RecipeBookSettings.TypeState typeState = (RecipeBookSettings.TypeState) result;
            return this.open != typeState.open ? false : this.filtering == typeState.filtering;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.open, this.filtering);
        }

        @Override
        public String toString() {
            return "TypeState{open=" + this.open + ", filtering=" + this.filtering + 125;
        }
    }
}
