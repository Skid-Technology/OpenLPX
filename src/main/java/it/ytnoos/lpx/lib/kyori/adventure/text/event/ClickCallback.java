package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import it.ytnoos.lpx.lib.kyori.adventure.audience.Audience;
import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.permission.PermissionChecker;
import it.ytnoos.lpx.lib.kyori.adventure.util.PlatformAPI;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.function.Consumer;
import java.util.function.Predicate;

@FunctionalInterface
public interface ClickCallback<T extends Audience> {

    Duration DEFAULT_LIFETIME = Duration.ofHours(12L);

    int UNLIMITED_USES = -1;

    @CheckReturnValue
    @Contract(pure = true)
    @NotNull
    static <W extends Audience, N extends W> ClickCallback<W> widen(
            @NotNull ClickCallback<N> clickCallback,
            @NotNull Class<N> classValue,
            @Nullable Consumer<? super Audience> consumer) {
        return result -> {
            if (((Class) classValue).isInstance(result)) {
                clickCallback.accept(classValue.cast(result));
            } else if (((Consumer) consumer) != null) {
                ((Consumer) consumer).accept(result);
            }
        };
    }

    @CheckReturnValue
    @Contract(pure = true)
    @NotNull
    static <W extends Audience, N extends W> ClickCallback<W> widen(
            @NotNull ClickCallback<N> clickCallback, @NotNull Class<N> classValue) {
        return widen(clickCallback, classValue, null);
    }

    void accept(@NotNull T t);

    @CheckReturnValue
    @Contract(pure = true)
    @NotNull
    default ClickCallback<T> filter(@NotNull Predicate<T> predicate) {
        return this.filter(predicate, null);
    }

    @CheckReturnValue
    @Contract(pure = true)
    @NotNull
    default ClickCallback<T> filter(
            @NotNull Predicate<T> predicate, @Nullable Consumer<? super Audience> consumer) {
        return result -> {
            if (((Predicate) predicate).test(result)) {
                this.accept(result);
            } else if (((Consumer) consumer) != null) {
                ((Consumer) consumer).accept(result);
            }
        };
    }

    @CheckReturnValue
    @Contract(pure = true)
    @NotNull
    default ClickCallback<T> requiringPermission(@NotNull String text) {
        return this.requiringPermission(text, null);
    }

    @CheckReturnValue
    @Contract(pure = true)
    @NotNull
    default ClickCallback<T> requiringPermission(
            @NotNull String text, @Nullable Consumer<? super Audience> consumer) {
        return this.filter(
                result ->
                        result.getOrDefault(
                                        PermissionChecker.POINTER,
                                        ClickCallbackInternals.ALWAYS_FALSE)
                                .test(text),
                consumer);
    }

    @NonExtendable
    interface Options extends Examinable {

        @NotNull
        static ClickCallback.Options.Builder builder() {
            return new ClickCallbackOptionsImpl.BuilderImpl();
        }

        @NotNull
        static ClickCallback.Options.Builder builder(@NotNull ClickCallback.Options options) {
            return new ClickCallbackOptionsImpl.BuilderImpl(options);
        }

        int uses();

        @NotNull
        Duration lifetime();

        @NonExtendable
        interface Builder extends AbstractBuilder<ClickCallback.Options> {

            @NotNull
            ClickCallback.Options.Builder uses(int value);

            @NotNull
            ClickCallback.Options.Builder lifetime(@NotNull TemporalAmount temporalAmount);
        }
    }

    @PlatformAPI
    @Internal
    interface Provider {

        @NotNull
        ClickEvent create(
                @NotNull ClickCallback<Audience> clickCallback,
                @NotNull ClickCallback.Options options);
    }
}
