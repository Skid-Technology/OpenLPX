package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;

@NonExtendable
public interface JoinConfiguration
        extends Buildable<JoinConfiguration, JoinConfiguration.Builder>, Examinable {

    @NotNull
    static JoinConfiguration.Builder builder() {
        return new JoinConfigurationImpl.BuilderImpl();
    }

    @NotNull
    static JoinConfiguration noSeparators() {
        return JoinConfigurationImpl.NULL;
    }

    @NotNull
    static JoinConfiguration newlines() {
        return JoinConfigurationImpl.STANDARD_NEW_LINES;
    }

    @NotNull
    static JoinConfiguration spaces() {
        return JoinConfigurationImpl.STANDARD_SPACES;
    }

    @NotNull
    static JoinConfiguration commas(boolean matches) {
        return matches
                ? JoinConfigurationImpl.STANDARD_COMMA_SPACE_SEPARATED
                : JoinConfigurationImpl.STANDARD_COMMA_SEPARATED;
    }

    @NotNull
    static JoinConfiguration arrayLike() {
        return JoinConfigurationImpl.STANDARD_ARRAY_LIKE;
    }

    @NotNull
    static JoinConfiguration separator(@Nullable ComponentLike componentLike) {
        return componentLike == null
                ? JoinConfigurationImpl.NULL
                : builder().separator(componentLike).build();
    }

    @NotNull
    static JoinConfiguration separators(
            @Nullable ComponentLike componentLike, @Nullable ComponentLike componentLike2) {
        return componentLike == null && componentLike2 == null
                ? JoinConfigurationImpl.NULL
                : builder().separator(componentLike).lastSeparator(componentLike2).build();
    }

    @Nullable
    Component prefix();

    @Nullable
    Component suffix();

    @Nullable
    Component separator();

    @Nullable
    Component lastSeparator();

    @Nullable
    Component lastSeparatorIfSerial();

    @NotNull
    Function<ComponentLike, Component> convertor();

    @NotNull
    Predicate<ComponentLike> predicate();

    @NotNull
    Style parentStyle();

    interface Builder
            extends AbstractBuilder<JoinConfiguration>, Buildable.Builder<JoinConfiguration> {

        @Contract("_ -> this")
        @NotNull
        JoinConfiguration.Builder prefix(@Nullable ComponentLike componentLike);

        @Contract("_ -> this")
        @NotNull
        JoinConfiguration.Builder suffix(@Nullable ComponentLike componentLike);

        @Contract("_ -> this")
        @NotNull
        JoinConfiguration.Builder separator(@Nullable ComponentLike componentLike);

        @Contract("_ -> this")
        @NotNull
        JoinConfiguration.Builder lastSeparator(@Nullable ComponentLike componentLike);

        @Contract("_ -> this")
        @NotNull
        JoinConfiguration.Builder lastSeparatorIfSerial(@Nullable ComponentLike componentLike);

        @Contract("_ -> this")
        @NotNull
        JoinConfiguration.Builder convertor(@NotNull Function<ComponentLike, Component> function);

        @Contract("_ -> this")
        @NotNull
        JoinConfiguration.Builder predicate(@NotNull Predicate<ComponentLike> predicate2);

        @Contract("_ -> this")
        @NotNull
        JoinConfiguration.Builder parentStyle(@NotNull Style style);
    }
}
