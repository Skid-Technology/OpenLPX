package it.ytnoos.lpx.lib.kyori.adventure.internal.properties;

import it.ytnoos.lpx.lib.kyori.adventure.util.Services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

final class AdventurePropertiesImpl {

    private static final Properties PROPERTIES = new Properties();

    private static void print(Throwable throwable) {
        throwable.printStackTrace();
    }

    private AdventurePropertiesImpl() {}

    @VisibleForTesting
    @NotNull
    static String systemPropertyName(String text) {
        return String.join(".", "net", "kyori", "adventure", text);
    }

    static <T> AdventureProperties.@NotNull Property<T> property(
            @NotNull String text,
            @NotNull Function<String, T> function,
            @Nullable T t,
            boolean matches) {
        return new AdventurePropertiesImpl.PropertyImpl<>(text, function, (T) t, matches);
    }

    static {
        Path path;
        if (Files.isRegularFile(
                path =
                        Optional.ofNullable(System.getProperty(systemPropertyName("config")))
                                .map(result -> Paths.get(result))
                                .orElseGet(() -> Paths.get("config", "adventure.properties")))) {
            try {
                InputStream inputStream = Files.newInputStream(path);
                try {
                    PROPERTIES.load(inputStream);
                } catch (Throwable throwable) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException oException) {
                print(oException);
            }
        }
    }

    private static final class PropertyImpl<T> implements AdventureProperties.Property<T> {

        private final String name;

        private final Function<String, T> parser;

        @Nullable private final T defaultValue;

        private final boolean allowProviderDefaultOverride;

        private boolean valueCalculated;

        @Nullable private T value;

        PropertyImpl(
                @NotNull String name,
                @NotNull Function<String, T> function,
                @Nullable T t,
                boolean allowProviderDefaultOverride) {
            this.name = name;
            this.parser = function;
            this.defaultValue = (T) t;
            this.allowProviderDefaultOverride = allowProviderDefaultOverride;
        }

        @Nullable
        @Override
        public T value() {
            if (!this.valueCalculated) {
                String text;
                if ((text =
                                System.getProperty(
                                        AdventurePropertiesImpl.systemPropertyName(this.name),
                                        AdventurePropertiesImpl.PROPERTIES.getProperty(this.name)))
                        != null) {
                    this.value = this.parser.apply(text);
                }
                if (this.value == null) {
                    if (this.allowProviderDefaultOverride) {
                        this.value =
                                AdventurePropertiesImpl.Providers.DEFAULT_PROVIDER
                                        .<T>map(
                                                result ->
                                                        result.overrideDefault(
                                                                this, this.defaultValue))
                                        .orElse(this.defaultValue);
                    } else {
                        this.value = this.defaultValue;
                    }
                }
                this.valueCalculated = true;
            }
            return this.value;
        }

        @Override
        public boolean equals(@Nullable Object result) {
            return this == result;
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    static final class Providers {

        @NotNull
        static final Optional<AdventureProperties.DefaultOverrideProvider> DEFAULT_PROVIDER =
                Services.service(AdventureProperties.DefaultOverrideProvider.class);
    }
}
