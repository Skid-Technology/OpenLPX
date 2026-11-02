package it.ytnoos.lpx.lib.kyori.option.value;

import it.ytnoos.lpx.lib.kyori.option.Option;

import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ValueSources {

    static final ValueSource ENVIRONMENT = new ValueSources.EnvironmentVariable("");

    static final ValueSource SYSTEM_PROPERTIES = new ValueSources.SystemProperty("");

    private ValueSources() {}

    static final class EnvironmentVariable implements ValueSource {

        private static final Pattern ENVIRONMENT_SUBST_PATTERN = Pattern.compile("[:\\-/]");

        private final String prefix;

        EnvironmentVariable(String text) {
            this.prefix = text.isEmpty() ? "" : text.toUpperCase(Locale.ROOT) + "_";
        }

        @Override
        @Nullable
        public <T> T value(Option<T> option) {
            StringBuffer stringBuffer;
            (stringBuffer = new StringBuffer(option.id().length() + this.prefix.length()))
                    .append(this.prefix);
            Matcher matcher2 = ENVIRONMENT_SUBST_PATTERN.matcher(option.id());
            while (matcher2.find()) {
                matcher2.appendReplacement(stringBuffer, "_");
            }
            matcher2.appendTail(stringBuffer);
            String text;
            return (T)
                    ((text = System.getenv(stringBuffer.toString().toUpperCase(Locale.ROOT)))
                                    == null
                            ? null
                            : option.valueType().parse(text));
        }
    }

    static final class SystemProperty implements ValueSource {

        private static final Pattern SYSTEM_PROP_SUBST_PATTERN = Pattern.compile("[:/]");

        private final String prefix;

        SystemProperty(String text) {
            this.prefix = text.isEmpty() ? "" : text + ".";
        }

        @Override
        @Nullable
        public <T> T value(Option<T> option) {
            StringBuffer stringBuffer;
            (stringBuffer = new StringBuffer(option.id().length() + this.prefix.length()))
                    .append(this.prefix);
            Matcher matcher2 = SYSTEM_PROP_SUBST_PATTERN.matcher(option.id());
            while (matcher2.find()) {
                matcher2.appendReplacement(stringBuffer, ".");
            }
            matcher2.appendTail(stringBuffer);
            String text;
            return (T)
                    ((text = System.getProperty(stringBuffer.toString())) == null
                            ? null
                            : option.valueType().parse(text));
        }
    }
}
