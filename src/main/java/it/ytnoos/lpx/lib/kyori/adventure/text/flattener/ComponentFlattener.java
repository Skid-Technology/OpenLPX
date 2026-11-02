package it.ytnoos.lpx.lib.kyori.adventure.text.flattener;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@NonExtendable
public interface ComponentFlattener
        extends Buildable<ComponentFlattener, ComponentFlattener.Builder> {

    int NO_NESTING_LIMIT = -1;

    @NotNull
    static ComponentFlattener.Builder builder() {
        return new ComponentFlattenerImpl.BuilderImpl();
    }

    @NotNull
    static ComponentFlattener basic() {
        return ComponentFlattenerImpl.BASIC;
    }

    @NotNull
    static ComponentFlattener textOnly() {
        return ComponentFlattenerImpl.TEXT_ONLY;
    }

    void flatten(@NotNull Component component, @NotNull FlattenerListener flattenerListener);

    @NonExtendable
    interface Builder
            extends AbstractBuilder<ComponentFlattener>, Buildable.Builder<ComponentFlattener> {

        @NotNull
        <T extends Component> ComponentFlattener.Builder mapper(
                @NotNull Class<T> classValue, @NotNull Function<T, String> function);

        @NotNull
        <T extends Component> ComponentFlattener.Builder complexMapper(
                @NotNull Class<T> classValue,
                @NotNull BiConsumer<T, Consumer<Component>> biConsumer);

        @NotNull
        ComponentFlattener.Builder unknownMapper(@Nullable Function<Component, String> function);

        @NotNull
        ComponentFlattener.Builder nestingLimit(@Range(from = 1L, to = 2147483647L) int value);
    }
}
