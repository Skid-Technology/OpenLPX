package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.OptionState;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

final class DummyJSONComponentSerializer implements JSONComponentSerializer {

    static final JSONComponentSerializer INSTANCE = new DummyJSONComponentSerializer();

    @NotNull
    public Component deserialize(@NotNull String text) {
        throw new UnsupportedOperationException(
                "No JsonComponentSerializer implementation found\n\nAre you missing an implementation artifact like adventure-text-serializer-gson?\nIs your environment configured in a way that causes ServiceLoader to malfunction?");
    }

    @NotNull
    public String serialize(@NotNull Component component) {
        throw new UnsupportedOperationException(
                "No JsonComponentSerializer implementation found\n\nAre you missing an implementation artifact like adventure-text-serializer-gson?\nIs your environment configured in a way that causes ServiceLoader to malfunction?");
    }

    static final class BuilderImpl implements JSONComponentSerializer.Builder {

        @NotNull
        @Override
        public JSONComponentSerializer.Builder options(@NotNull OptionState optionState) {
            return this;
        }

        @NotNull
        @Override
        public JSONComponentSerializer.Builder editOptions(
                @NotNull Consumer<OptionState.Builder> consumer) {
            return this;
        }

        @Deprecated
        @NotNull
        @Override
        public JSONComponentSerializer.Builder downsampleColors() {
            return this;
        }

        @NotNull
        @Override
        public JSONComponentSerializer.Builder legacyHoverEventSerializer(
                @Nullable LegacyHoverEventSerializer legacyHoverEventSerializer2) {
            return this;
        }

        @Deprecated
        @NotNull
        @Override
        public JSONComponentSerializer.Builder emitLegacyHoverEvent() {
            return this;
        }

        @NotNull
        @Override
        public JSONComponentSerializer build() {
            return DummyJSONComponentSerializer.INSTANCE;
        }
    }
}
