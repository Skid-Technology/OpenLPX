package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.legacy;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextReplacementConfig;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.flattener.ComponentFlattener;
import it.ytnoos.lpx.lib.kyori.adventure.text.flattener.FlattenerListener;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextFormat;
import it.ytnoos.lpx.lib.kyori.adventure.util.Services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

final class LegacyComponentSerializerImpl implements LegacyComponentSerializer {

    static final Pattern DEFAULT_URL_PATTERN =
            Pattern.compile(
                    "(?:(https?)://)?([-\\w_.]+\\.\\w{2,})(/([A-Za-z0-9\\-._~!$&'()*+,;=:@/]|%[0-9A-Fa-f]{2})*)?");

    static final Pattern URL_SCHEME_PATTERN = Pattern.compile("^[a-z][a-z0-9+\\-.]*:");

    private static final TextDecoration[] DECORATIONS = TextDecoration.values();

    private static final Optional<LegacyComponentSerializer.Provider> SERVICE =
            Services.service(LegacyComponentSerializer.Provider.class);

    static final Consumer<LegacyComponentSerializer.Builder> BUILDER =
            SERVICE.map(LegacyComponentSerializer.Provider::legacy).orElseGet(() -> result -> {});

    private final char character;

    private final char hexCharacter;

    @Nullable private final TextReplacementConfig urlReplacementConfig;

    private final boolean hexColours;

    private final boolean useTerriblyStupidHexFormat;

    private final ComponentFlattener flattener;

    private final CharacterAndFormatSet formats;

    LegacyComponentSerializerImpl(
            char character,
            char hexCharacter,
            @Nullable TextReplacementConfig textReplacementConfig,
            boolean hexColours,
            boolean useTerriblyStupidHexFormat,
            ComponentFlattener componentFlattener,
            CharacterAndFormatSet characterAndFormatSet) {
        this.character = character;
        this.hexCharacter = hexCharacter;
        this.urlReplacementConfig = textReplacementConfig;
        this.hexColours = hexColours;
        this.useTerriblyStupidHexFormat = useTerriblyStupidHexFormat;
        this.flattener = componentFlattener;
        this.formats = characterAndFormatSet;
    }

    @Nullable
    private LegacyComponentSerializerImpl.FormatCodeType determineFormatType(
            char value, String text, int value2) {
        if (value2 >= 14) {
            int value3 = value2 - 14;
            int value4 = value2 - 13;
            if (text.charAt(value3) == this.character && text.charAt(value4) == 'x') {
                return LegacyComponentSerializerImpl.FormatCodeType.BUNGEECORD_UNUSUAL_HEX;
            }
        }
        if (value == this.hexCharacter && text.length() - value2 >= 6) {
            return LegacyComponentSerializerImpl.FormatCodeType.KYORI_HEX;
        } else {
            return this.formats.characters.indexOf(value) != -1
                    ? LegacyComponentSerializerImpl.FormatCodeType.MOJANG_LEGACY
                    : null;
        }
    }

    @Nullable
    static LegacyFormat legacyFormat(char value) {
        int value2;
        if ((value2 = CharacterAndFormatSet.DEFAULT.characters.indexOf(value)) != -1) {
            TextFormat textFormat;
            if ((textFormat = CharacterAndFormatSet.DEFAULT.formats.get(value2))
                    instanceof NamedTextColor) {
                return new LegacyFormat((NamedTextColor) textFormat);
            }
            if (textFormat instanceof TextDecoration) {
                return new LegacyFormat((TextDecoration) textFormat);
            }
            if (textFormat instanceof Reset) {
                return LegacyFormat.RESET;
            }
        }
        return null;
    }

    @Nullable
    private LegacyComponentSerializerImpl.DecodedFormat decodeTextFormat(
            char value, String text, int value2) {
        LegacyComponentSerializerImpl.FormatCodeType formatCodeType;
        if ((formatCodeType = this.determineFormatType(value, text, value2)) == null) {
            return null;
        }
        if (formatCodeType == LegacyComponentSerializerImpl.FormatCodeType.KYORI_HEX) {
            TextColor textColor;
            if ((textColor = tryParseHexColor(text.substring(value2, value2 + 6))) != null) {
                return new LegacyComponentSerializerImpl.DecodedFormat(formatCodeType, textColor);
            }
        } else {
            if (formatCodeType == LegacyComponentSerializerImpl.FormatCodeType.MOJANG_LEGACY) {
                return new LegacyComponentSerializerImpl.DecodedFormat(
                        formatCodeType,
                        this.formats.formats.get(this.formats.characters.indexOf(value)));
            }
            if (formatCodeType
                    == LegacyComponentSerializerImpl.FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
                StringBuilder stringBuilder = new StringBuilder(6);
                for (int value3 = value2 - 1; value3 >= value2 - 11; value3 -= 2) {
                    stringBuilder.append(text.charAt(value3));
                }
                TextColor textColor2;
                if ((textColor2 = tryParseHexColor(stringBuilder.reverse().toString())) != null) {
                    return new LegacyComponentSerializerImpl.DecodedFormat(
                            formatCodeType, textColor2);
                }
            }
        }
        return null;
    }

    @Nullable
    private static TextColor tryParseHexColor(String text) {
        try {
            return TextColor.color(Integer.parseInt(text, 16));
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private static boolean isHexTextColor(TextFormat textFormat) {
        return textFormat instanceof TextColor && !(textFormat instanceof NamedTextColor);
    }

    @Nullable
    private String toLegacyCode(TextFormat textFormat) {
        if (isHexTextColor(textFormat)) {
            TextColor textColor = (TextColor) textFormat;
            if (this.hexColours) {
                String text = String.format("%06x", textColor.value());
                if (!this.useTerriblyStupidHexFormat) {
                    return this.hexCharacter + text;
                }
                StringBuilder stringBuilder = new StringBuilder(String.valueOf('x'));
                int value2 = 0;
                for (int size = text.length(); value2 < size; value2++) {
                    stringBuilder.append(this.character).append(text.charAt(value2));
                }
                return stringBuilder.toString();
            }
            if (!(textColor instanceof NamedTextColor)) {
                textFormat = TextColor.nearestColorTo(this.formats.colors, textColor);
            }
        }
        int value3;
        return (value3 = this.formats.formats.indexOf(textFormat)) == -1
                ? null
                : Character.toString(this.formats.characters.charAt(value3));
    }

    private TextComponent extractUrl(TextComponent textComponent) {
        if (this.urlReplacementConfig == null) {
            return textComponent;
        }
        Component component;
        return (component = textComponent.replaceText(this.urlReplacementConfig))
                        instanceof TextComponent
                ? (TextComponent) component
                : Component.text().append(component).build();
    }

    @NotNull
    @Override
    public TextComponent deserialize(@NotNull String text2) {
        int value;
        if ((value = text2.lastIndexOf(this.character, text2.length() - 2)) == -1) {
            return this.extractUrl(Component.text(text2));
        }
        ArrayList<TextComponent> values = new ArrayList<>();
        TextComponent.Builder builder = null;
        boolean matches = false;
        int size2 = text2.length();
        do {
            LegacyComponentSerializerImpl.DecodedFormat decodedFormat;
            if ((decodedFormat = this.decodeTextFormat(text2.charAt(value + 1), text2, value + 2))
                    != null) {
                int value2;
                if ((value2 =
                                value
                                        + (decodedFormat.encodedFormat
                                                        == LegacyComponentSerializerImpl
                                                                .FormatCodeType.KYORI_HEX
                                                ? 8
                                                : 2))
                        != size2) {
                    if (builder != null) {
                        if (matches) {
                            values.add(builder.build());
                            matches = false;
                            builder = Component.text();
                        } else {
                            builder = Component.text().append(builder.build());
                        }
                    } else {
                        builder = Component.text();
                    }
                    builder.content(text2.substring(value2, size2));
                } else if (builder == null) {
                    builder = Component.text();
                }
                if (!matches) {
                    matches = applyFormat(builder, decodedFormat.format);
                }
                if (decodedFormat.encodedFormat
                        == LegacyComponentSerializerImpl.FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
                    value -= 12;
                }
                size2 = value;
            }
        } while ((value = text2.lastIndexOf(this.character, value - 1)) != -1);
        if (builder != null) {
            values.add(builder.build());
        }
        String text3 = size2 > 0 ? text2.substring(0, size2) : "";
        if (values.size() == 1 && text3.isEmpty()) {
            return this.extractUrl((TextComponent) values.get(0));
        }
        Collections.reverse(values);
        return this.extractUrl(Component.text().content(text3).append(values).build());
    }

    @NotNull
    @Override
    public String serialize(@NotNull Component component) {
        LegacyComponentSerializerImpl.Cereal cereal = new LegacyComponentSerializerImpl.Cereal();
        this.flattener.flatten(component, cereal);
        return cereal.toString();
    }

    private static boolean applyFormat(
            TextComponent.@NotNull Builder builder, @NotNull TextFormat textFormat) {
        if (textFormat instanceof TextColor) {
            builder.colorIfAbsent((TextColor) textFormat);
            return true;
        } else if (textFormat instanceof TextDecoration) {
            builder.decoration((TextDecoration) textFormat, TextDecoration.State.TRUE);
            return false;
        } else if (textFormat instanceof Reset) {
            return true;
        } else {
            throw new IllegalArgumentException(
                    String.format("unknown format '%s'", textFormat.getClass()));
        }
    }

    @NotNull
    public LegacyComponentSerializer.Builder toBuilder() {
        return new LegacyComponentSerializerImpl.BuilderImpl(this);
    }

    static final class BuilderImpl implements LegacyComponentSerializer.Builder {

        private char character = 167;

        private char hexCharacter = '#';

        private TextReplacementConfig urlReplacementConfig = null;

        private boolean hexColours = false;

        private boolean useTerriblyStupidHexFormat = false;

        private ComponentFlattener flattener = ComponentFlattener.basic();

        private CharacterAndFormatSet formats = CharacterAndFormatSet.DEFAULT;

        BuilderImpl() {
            LegacyComponentSerializerImpl.BUILDER.accept(this);
        }

        BuilderImpl(@NotNull LegacyComponentSerializerImpl legacyComponentSerializerImpl) {
            this();
            this.character = legacyComponentSerializerImpl.character;
            this.hexCharacter = legacyComponentSerializerImpl.hexCharacter;
            this.urlReplacementConfig = legacyComponentSerializerImpl.urlReplacementConfig;
            this.hexColours = legacyComponentSerializerImpl.hexColours;
            this.useTerriblyStupidHexFormat =
                    legacyComponentSerializerImpl.useTerriblyStupidHexFormat;
            this.flattener = legacyComponentSerializerImpl.flattener;
            this.formats = legacyComponentSerializerImpl.formats;
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder character(char character) {
            this.character = character;
            return this;
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder hexCharacter(char hexCharacter) {
            this.hexCharacter = hexCharacter;
            return this;
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder extractUrls() {
            return this.extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, null);
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder extractUrls(@NotNull Pattern pattern) {
            return this.extractUrls(pattern, null);
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder extractUrls(@Nullable Style style) {
            return this.extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, style);
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder extractUrls(
                @NotNull Pattern pattern, @Nullable Style style2) {
            Objects.requireNonNull(pattern, "pattern");
            TextReplacementConfig.Builder builder2 = TextReplacementConfig.builder().match(pattern);
            this.urlReplacementConfig =
                    builder2.replacement(
                                    result -> {
                                        String text = result.content();
                                        if (!LegacyComponentSerializerImpl.URL_SCHEME_PATTERN
                                                .matcher(text)
                                                .find()) {
                                            text = "http://" + text;
                                        }
                                        try {
                                            new URI(text);
                                            return (style2 == null ? result : result.style(style2))
                                                    .clickEvent(ClickEvent.openUrl(text));
                                        } catch (URISyntaxException uRISyntaxException) {
                                            return result;
                                        }
                                    })
                            .build();
            return this;
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder hexColors() {
            this.hexColours = true;
            return this;
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder useUnusualXRepeatedCharacterHexFormat() {
            this.useTerriblyStupidHexFormat = true;
            return this;
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder flattener(
                @NotNull ComponentFlattener componentFlattener) {
            this.flattener = Objects.requireNonNull(componentFlattener, "flattener");
            return this;
        }

        @NotNull
        @Override
        public LegacyComponentSerializer.Builder formats(@NotNull List<CharacterAndFormat> values) {
            this.formats = CharacterAndFormatSet.of(values);
            return this;
        }

        @NotNull
        @Override
        public LegacyComponentSerializer build() {
            return new LegacyComponentSerializerImpl(
                    this.character,
                    this.hexCharacter,
                    this.urlReplacementConfig,
                    this.hexColours,
                    this.useTerriblyStupidHexFormat,
                    this.flattener,
                    this.formats);
        }
    }

    private final class Cereal implements FlattenerListener {

        private final StringBuilder sb = new StringBuilder();

        private final LegacyComponentSerializerImpl.Cereal.StyleState style =
                new LegacyComponentSerializerImpl.Cereal.StyleState();

        @Nullable private TextFormat lastWritten;

        private LegacyComponentSerializerImpl.Cereal.StyleState[] styles =
                new LegacyComponentSerializerImpl.Cereal.StyleState[8];

        private int head = -1;

        private Cereal() {}

        @Override
        public void pushStyle(@NotNull Style style) {
            int value;
            if ((value = ++this.head) >= this.styles.length) {
                this.styles = Arrays.copyOf(this.styles, this.styles.length * 2);
            }
            LegacyComponentSerializerImpl.Cereal.StyleState styleState;
            if ((styleState = this.styles[value]) == null) {
                this.styles[value] =
                        styleState = new LegacyComponentSerializerImpl.Cereal.StyleState();
            }
            if (value > 0) {
                styleState.set(this.styles[value - 1]);
            } else {
                styleState.clear();
            }
            styleState.apply(style);
        }

        @Override
        public void component(@NotNull String text) {
            if (!text.isEmpty()) {
                if (this.head < 0) {
                    throw new IllegalStateException("No style has been pushed!");
                }
                this.styles[this.head].applyFormat();
                this.sb.append(text);
            }
        }

        @Override
        public void popStyle(@NotNull Style style) {
            if (this.head-- < 0) {
                throw new IllegalStateException("Tried to pop beyond what was pushed!");
            }
        }

        void append(@NotNull TextFormat textFormat) {
            if (this.lastWritten != textFormat) {
                String text;
                if ((text = LegacyComponentSerializerImpl.this.toLegacyCode(textFormat)) == null) {
                    return;
                }
                this.sb.append(LegacyComponentSerializerImpl.this.character).append(text);
            }
            this.lastWritten = textFormat;
        }

        @Override
        public String toString() {
            return this.sb.toString();
        }

        private final class StyleState {

            @Nullable private TextColor color;

            private final Set<TextDecoration> decorations = EnumSet.noneOf(TextDecoration.class);

            private boolean needsReset;

            StyleState() {}

            void set(@NotNull LegacyComponentSerializerImpl.Cereal.StyleState styleState) {
                this.color = styleState.color;
                this.decorations.clear();
                this.decorations.addAll(styleState.decorations);
            }

            public void clear() {
                this.color = null;
                this.decorations.clear();
            }

            void apply(@NotNull Style style) {
                TextColor textColor;
                if ((textColor = style.color()) != null) {
                    this.color = textColor;
                }
                int value = 0;
                for (int value2 = LegacyComponentSerializerImpl.DECORATIONS.length;
                        value < value2;
                        value++) {
                    TextDecoration textDecoration =
                            LegacyComponentSerializerImpl.DECORATIONS[value];
                    switch (style.decoration(textDecoration)) {
                        case TRUE:
                            this.decorations.add(textDecoration);
                            break;
                        case FALSE:
                            if (this.decorations.remove(textDecoration)) {
                                this.needsReset = true;
                            }
                    }
                }
            }

            void applyFormat() {
                boolean matches = this.color != Cereal.this.style.color;
                if (this.needsReset) {
                    if (!matches) {
                        Cereal.this.append(Reset.INSTANCE);
                    }
                    this.needsReset = false;
                }
                if (!matches && Cereal.this.lastWritten != Reset.INSTANCE) {
                    if (!this.decorations.containsAll(Cereal.this.style.decorations)) {
                        this.applyFullFormat();
                    } else {
                        for (TextDecoration textDecoration : this.decorations) {
                            if (Cereal.this.style.decorations.add(textDecoration)) {
                                Cereal.this.append(textDecoration);
                            }
                        }
                    }
                } else {
                    this.applyFullFormat();
                }
            }

            private void applyFullFormat() {
                if (this.color != null) {
                    Cereal.this.append(this.color);
                } else {
                    Cereal.this.append(Reset.INSTANCE);
                }
                Cereal.this.style.color = this.color;
                for (TextDecoration textDecoration : this.decorations) {
                    Cereal.this.append(textDecoration);
                }
                Cereal.this.style.decorations.clear();
                Cereal.this.style.decorations.addAll(this.decorations);
            }
        }
    }

    static final class DecodedFormat {

        final LegacyComponentSerializerImpl.FormatCodeType encodedFormat;

        final TextFormat format;

        private DecodedFormat(
                LegacyComponentSerializerImpl.FormatCodeType formatCodeType,
                TextFormat textFormat) {
            if (textFormat == null) {
                throw new IllegalStateException("No format found");
            }
            this.encodedFormat = formatCodeType;
            this.format = textFormat;
        }
    }

    enum FormatCodeType {
        MOJANG_LEGACY,
        KYORI_HEX,
        BUNGEECORD_UNUSUAL_HEX
    }

    static final class Instances {

        static final LegacyComponentSerializer SECTION =
                LegacyComponentSerializerImpl.SERVICE
                        .map(LegacyComponentSerializer.Provider::legacySection)
                        .orElseGet(
                                () ->
                                        new LegacyComponentSerializerImpl(
                                                '§',
                                                '#',
                                                null,
                                                false,
                                                false,
                                                ComponentFlattener.basic(),
                                                CharacterAndFormatSet.DEFAULT));

        static final LegacyComponentSerializer AMPERSAND =
                LegacyComponentSerializerImpl.SERVICE
                        .map(LegacyComponentSerializer.Provider::legacyAmpersand)
                        .orElseGet(
                                () ->
                                        new LegacyComponentSerializerImpl(
                                                '&',
                                                '#',
                                                null,
                                                false,
                                                false,
                                                ComponentFlattener.basic(),
                                                CharacterAndFormatSet.DEFAULT));
    }
}
