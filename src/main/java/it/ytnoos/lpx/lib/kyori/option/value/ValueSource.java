package it.ytnoos.lpx.lib.kyori.option.value;

import it.ytnoos.lpx.lib.kyori.option.Option;

import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface ValueSource {

    static ValueSource environmentVariable() {
        return environmentVariable("");
    }

    static ValueSource environmentVariable(String text) {
        return new ValueSources.EnvironmentVariable(text);
    }

    static ValueSource systemProperty() {
        return systemProperty("");
    }

    static ValueSource systemProperty(String text) {
        return new ValueSources.SystemProperty(text);
    }

    @Nullable <T> T value(Option<T> option);
}
