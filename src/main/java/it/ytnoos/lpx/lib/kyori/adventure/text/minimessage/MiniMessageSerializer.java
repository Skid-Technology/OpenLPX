package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.ClaimConsumer;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.Emitable;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.QuotingOverride;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.TokenEmitter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

final class MiniMessageSerializer {

    private MiniMessageSerializer() {}

    @NotNull
    static String serialize(
            @NotNull Component component,
            @NotNull SerializableResolver serializableResolver,
            boolean matches) {
        StringBuilder stringBuilder = new StringBuilder();
        MiniMessageSerializer.Collector collector;
        (collector =
                        new MiniMessageSerializer.Collector(
                                serializableResolver, matches, stringBuilder))
                .mark();
        visit(component, collector, serializableResolver, true);
        if (matches) {
            collector.popAll();
        } else {
            collector.completeTag();
        }
        return stringBuilder.toString();
    }

    private static void visit(
            @NotNull Component component,
            MiniMessageSerializer.Collector collector,
            SerializableResolver serializableResolver,
            boolean matches) {
        serializableResolver.handle(component, collector);
        Component component2;
        if ((component2 = collector.flushClaims(component)) == null) {
            component2 = component;
        }
        Iterator<Component> iterator2 = component2.children().iterator();
        while (iterator2.hasNext()) {
            collector.mark();
            visit(
                    (Component) iterator2.next(),
                    collector,
                    serializableResolver,
                    matches && !iterator2.hasNext());
        }
        if (!matches) {
            collector.popToMark();
        }
    }

    static final class Collector implements TokenEmitter, ClaimConsumer {

        private static final char[] TEXT_ESCAPES = new char[] {'\\', '<'};

        private static final char[] TAG_TOKENS = new char[] {'>', ':'};

        private static final char[] SINGLE_QUOTED_ESCAPES = new char[] {'\\', '\''};

        private static final char[] DOUBLE_QUOTED_ESCAPES = new char[] {'\\', '"'};

        private final SerializableResolver resolver;

        private final boolean strict;

        private final StringBuilder consumer;

        private String[] activeTags = new String[4];

        private int tagLevel = 0;

        private MiniMessageSerializer.Collector.TagState tagState =
                MiniMessageSerializer.Collector.TagState.TEXT;

        @Nullable Emitable componentClaim;

        final Set<String> claimedStyleElements = new HashSet<>();

        Collector(
                SerializableResolver serializableResolver,
                boolean strict,
                StringBuilder stringBuilder) {
            this.resolver = serializableResolver;
            this.strict = strict;
            this.consumer = stringBuilder;
        }

        private void pushActiveTag(String text) {
            if (this.tagLevel >= this.activeTags.length) {
                this.activeTags = Arrays.copyOf(this.activeTags, this.activeTags.length * 2);
            }
            this.activeTags[this.tagLevel++] = text;
        }

        private String popTag(boolean matches) {
            if (this.tagLevel-- <= 0) {
                throw new IllegalStateException("Unbalanced tags, tried to pop below depth");
            } else {
                String text = this.activeTags[this.tagLevel];
                if (!matches && text == "__<'\"\\MARK__") {
                    throw new IllegalStateException(
                            "Tried to pop past mark, tag stack: "
                                    + Arrays.toString(this.activeTags)
                                    + " @ "
                                    + this.tagLevel);
                } else {
                    return text;
                }
            }
        }

        void mark() {
            this.pushActiveTag("__<'\"\\MARK__");
        }

        void popToMark() {
            if (this.tagLevel != 0) {
                String text;
                while ((text = this.popTag(true)) != "__<'\"\\MARK__") {
                    this.emitClose(text);
                }
            }
        }

        void popAll() {
            while (this.tagLevel > 0) {
                String text;
                if ((text = this.activeTags[--this.tagLevel]) != "__<'\"\\MARK__") {
                    this.emitClose(text);
                }
            }
        }

        void completeTag() {
            if (this.tagState.isTag) {
                this.consumer.append('>');
                this.tagState = MiniMessageSerializer.Collector.TagState.TEXT;
            }
        }

        @NotNull
        public MiniMessageSerializer.Collector tag(@NotNull String text) {
            this.completeTag();
            this.consumer.append('<');
            this.escapeTagContent(text, QuotingOverride.UNQUOTED);
            this.tagState = MiniMessageSerializer.Collector.TagState.MID;
            this.pushActiveTag(text);
            return this;
        }

        @NotNull
        @Override
        public TokenEmitter selfClosingTag(@NotNull String text) {
            this.completeTag();
            this.consumer.append('<');
            this.escapeTagContent(text, QuotingOverride.UNQUOTED);
            this.tagState = MiniMessageSerializer.Collector.TagState.MID_SELF_CLOSING;
            return this;
        }

        @NotNull
        @Override
        public TokenEmitter argument(@NotNull String text) {
            if (!this.tagState.isTag) {
                throw new IllegalStateException("Not within a tag!");
            }
            this.consumer.append(':');
            this.escapeTagContent(text, null);
            return this;
        }

        @NotNull
        @Override
        public TokenEmitter argument(
                @NotNull String text, @NotNull QuotingOverride quotingOverride) {
            if (!this.tagState.isTag) {
                throw new IllegalStateException("Not within a tag!");
            }
            this.consumer.append(':');
            this.escapeTagContent(
                    text, Objects.requireNonNull(quotingOverride, "quotingPreference"));
            return this;
        }

        @NotNull
        @Override
        public TokenEmitter argument(@NotNull Component component) {
            String text = MiniMessageSerializer.serialize(component, this.resolver, this.strict);
            return this.argument(text, QuotingOverride.QUOTED);
        }

        @NotNull
        public MiniMessageSerializer.Collector text(@NotNull String text2) {
            this.completeTag();
            appendEscaping(this.consumer, text2, TEXT_ESCAPES, true);
            return this;
        }

        private void escapeTagContent(String text, @Nullable QuotingOverride quotingOverride) {
            boolean matches = quotingOverride == QuotingOverride.QUOTED;
            boolean matches2 = false;
            boolean matches3 = false;
            for (int index = 0; index < text.length(); index++) {
                char value = text.charAt(index);
                if (value != '>' && value != ':' && value != ' ') {
                    if (value == '\'') {
                        matches2 = true;
                        break;
                    }
                    if (value == '"') {
                        matches3 = true;
                        if (matches) {}
                    }
                } else {
                    matches = true;
                }
            }
            if (matches2) {
                this.consumer.append('"');
                appendEscaping(this.consumer, text, DOUBLE_QUOTED_ESCAPES, true);
                this.consumer.append('"');
            } else if (!matches3 && !matches) {
                appendEscaping(this.consumer, text, TAG_TOKENS, false);
            } else {
                this.consumer.append('\'');
                appendEscaping(this.consumer, text, SINGLE_QUOTED_ESCAPES, true);
                this.consumer.append('\'');
            }
        }

        static void appendEscaping(
                StringBuilder stringBuilder, String text, char[] values, boolean matches) {
            int value = 0;
            boolean matches2 = false;
            for (int index = 0; index < text.length(); index++) {
                char value2 = text.charAt(index);
                boolean matches3 = false;
                int value3 = values.length;
                for (int index2 = 0; index2 < value3; index2++) {
                    char value4 = values[index2];
                    if (value2 == value4) {
                        if (!matches) {
                            throw new IllegalArgumentException(
                                    "Invalid escapable character '"
                                            + value2
                                            + "' found at index "
                                            + index
                                            + " in string '"
                                            + text
                                            + "'");
                        }
                        matches3 = true;
                        break;
                    }
                }
                if (matches3) {
                    if (matches2) {
                        stringBuilder.append(text, value, index);
                    }
                    value = index + 1;
                    stringBuilder.append('\\').append(value2);
                } else {
                    matches2 = true;
                }
            }
            if (value < text.length() && matches2) {
                stringBuilder.append(text, value, text.length());
            }
        }

        @NotNull
        public MiniMessageSerializer.Collector pop() {
            this.emitClose(this.popTag(false));
            return this;
        }

        private void emitClose(@NotNull String text) {
            if (this.tagState.isTag) {
                if (this.tagState == MiniMessageSerializer.Collector.TagState.MID) {
                    this.consumer.append('/');
                }
                this.consumer.append('>');
                this.tagState = MiniMessageSerializer.Collector.TagState.TEXT;
            } else {
                this.consumer.append('<').append('/');
                this.escapeTagContent(text, QuotingOverride.UNQUOTED);
                this.consumer.append('>');
            }
        }

        @Override
        public void style(@NotNull String text, @NotNull Emitable emitable) {
            if (this.claimedStyleElements.add(Objects.requireNonNull(text, "claimKey"))) {
                emitable.emit(this);
            }
        }

        @Override
        public boolean component(@NotNull Emitable emitable) {
            if (this.componentClaim != null) {
                return false;
            }
            this.componentClaim = Objects.requireNonNull(emitable, "componentClaim");
            return true;
        }

        @Override
        public boolean componentClaimed() {
            return this.componentClaim != null;
        }

        @Override
        public boolean styleClaimed(@NotNull String text) {
            return this.claimedStyleElements.contains(text);
        }

        @Nullable
        Component flushClaims(Component component) {
            Component component2 = null;
            if (this.componentClaim != null) {
                this.componentClaim.emit(this);
                component2 = this.componentClaim.substitute();
                this.componentClaim = null;
            } else {
                if (!(component instanceof TextComponent)) {
                    throw new IllegalStateException("Unclaimed component " + component);
                }
                this.text(((TextComponent) component).content());
            }
            this.claimedStyleElements.clear();
            return component2;
        }

        enum TagState {
            TEXT(false),
            MID(true),
            MID_SELF_CLOSING(true);

            final boolean isTag;

            TagState(boolean isTag) {
                this.isTag = isTag;
            }
        }
    }
}
