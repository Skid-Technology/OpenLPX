package it.ytnoos.lpx.lib.kyori.adventure.internal.properties;

import it.ytnoos.lpx.lib.kyori.adventure.util.PlatformAPI;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

@Internal
public final class AdventureProperties {

    public static final AdventureProperties.Property<Boolean> DEBUG =
            property("debug", Boolean::parseBoolean, false);

    public static final AdventureProperties.Property<String> DEFAULT_TRANSLATION_LOCALE =
            property("defaultTranslationLocale", Function.identity(), null);

    public static final AdventureProperties.Property<Boolean> SERVICE_LOAD_FAILURES_ARE_FATAL =
            property("serviceLoadFailuresAreFatal", Boolean::parseBoolean, Boolean.TRUE, false);

    public static final AdventureProperties.Property<Boolean>
            TEXT_WARN_WHEN_LEGACY_FORMATTING_DETECTED =
                    property(
                            "text.warnWhenLegacyFormattingDetected",
                            Boolean::parseBoolean,
                            Boolean.FALSE);

    public static final AdventureProperties.Property<Integer> DEFAULT_FLATTENER_NESTING_LIMIT =
            property("defaultFlattenerNestingLimit", Integer::parseInt, -1);

    private AdventureProperties() {}

    @NotNull
    public static <T> AdventureProperties.Property<T> property(
            @NotNull String text, @NotNull Function<String, T> function, @Nullable T t) {
        return property(text, function, (T) t, true);
    }

    @NotNull
    public static <T> AdventureProperties.Property<T> property(
            @NotNull String text,
            @NotNull Function<String, T> function,
            @Nullable T t,
            boolean matches) {
        return AdventurePropertiesImpl.property(text, function, (T) t, matches);
    }

    @PlatformAPI
    @Internal
    public interface DefaultOverrideProvider {

        @Nullable
        <T> T overrideDefault(@NotNull AdventureProperties.Property<T> property, @Nullable T t);
    }

    @Internal
    @NonExtendable
    public interface Property<T> {

        @Nullable
        T value();

        @NotNull
        default T valueOr(@NotNull T t) {
            T t2;
            return (T)
                    ((t2 = this.value()) == null
                            ? Objects.requireNonNull((T) t, "defaultValue")
                            : t2);
        }
    }
}
