package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import it.ytnoos.lpx.lib.kyori.adventure.audience.Audience;
import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.dialog.DialogLike;
import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.key.Keyed;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleBuilderApplicable;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class ClickEvent implements Examinable, StyleBuilderApplicable {

    private final ClickEvent.Action action;

    private final ClickEvent.Payload payload;

    @NotNull
    public static ClickEvent openUrl(@NotNull String text) {
        return new ClickEvent(ClickEvent.Action.OPEN_URL, ClickEvent.Payload.string(text));
    }

    @NotNull
    public static ClickEvent openUrl(@NotNull URL uRL) {
        return openUrl(uRL.toExternalForm());
    }

    @NotNull
    public static ClickEvent openFile(@NotNull String text) {
        return new ClickEvent(ClickEvent.Action.OPEN_FILE, ClickEvent.Payload.string(text));
    }

    @NotNull
    public static ClickEvent runCommand(@NotNull String text) {
        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, ClickEvent.Payload.string(text));
    }

    @NotNull
    public static ClickEvent suggestCommand(@NotNull String text) {
        return new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ClickEvent.Payload.string(text));
    }

    @Deprecated
    @NotNull
    public static ClickEvent changePage(@NotNull String text) {
        Objects.requireNonNull(text, "page");
        return new ClickEvent(
                ClickEvent.Action.CHANGE_PAGE, ClickEvent.Payload.integer(Integer.parseInt(text)));
    }

    @NotNull
    public static ClickEvent changePage(int value) {
        return new ClickEvent(ClickEvent.Action.CHANGE_PAGE, ClickEvent.Payload.integer(value));
    }

    @NotNull
    public static ClickEvent copyToClipboard(@NotNull String text) {
        return new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, ClickEvent.Payload.string(text));
    }

    @NotNull
    public static ClickEvent callback(@NotNull ClickCallback<Audience> clickCallback) {
        return ClickCallbackInternals.PROVIDER.create(
                Objects.requireNonNull(clickCallback, "function"),
                ClickCallbackOptionsImpl.DEFAULT);
    }

    @NotNull
    public static ClickEvent callback(
            @NotNull ClickCallback<Audience> clickCallback,
            ClickCallback.@NotNull Options options) {
        return ClickCallbackInternals.PROVIDER.create(
                Objects.requireNonNull(clickCallback, "function"),
                Objects.requireNonNull(options, "options"));
    }

    @NotNull
    public static ClickEvent callback(
            @NotNull ClickCallback<Audience> clickCallback,
            @NotNull Consumer<ClickCallback.Options.Builder> consumer) {
        return ClickCallbackInternals.PROVIDER.create(
                Objects.requireNonNull(clickCallback, "function"),
                AbstractBuilder.configureAndBuild(
                        ClickCallback.Options.builder(),
                        Objects.requireNonNull(consumer, "optionsBuilder")));
    }

    @NotNull
    public static ClickEvent showDialog(@NotNull DialogLike dialogLike) {
        Objects.requireNonNull(dialogLike, "dialog");
        return new ClickEvent(ClickEvent.Action.SHOW_DIALOG, ClickEvent.Payload.dialog(dialogLike));
    }

    @Deprecated
    @NotNull
    public static ClickEvent custom(@NotNull Key key, @NotNull String text) {
        return custom(key, BinaryTagHolder.binaryTagHolder(text));
    }

    @NotNull
    public static ClickEvent custom(@NotNull Key key, @NotNull BinaryTagHolder binaryTagHolder) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(binaryTagHolder, "nbt");
        return new ClickEvent(
                ClickEvent.Action.CUSTOM, ClickEvent.Payload.custom(key, binaryTagHolder));
    }

    @Deprecated
    @NotNull
    public static ClickEvent clickEvent(@NotNull ClickEvent.Action action, @NotNull String text) {
        if (action == ClickEvent.Action.CHANGE_PAGE) {
            return changePage(text);
        } else if (!action.payloadType().equals(ClickEvent.Payload.Text.class)) {
            throw new IllegalArgumentException(
                    "Action " + action + " does not support string payloads");
        } else {
            return new ClickEvent(action, ClickEvent.Payload.string(text));
        }
    }

    @NotNull
    public static ClickEvent clickEvent(
            @NotNull ClickEvent.Action action, @NotNull ClickEvent.Payload payload) {
        return new ClickEvent(action, payload);
    }

    private ClickEvent(@NotNull ClickEvent.Action action2, @NotNull ClickEvent.Payload payload2) {
        if (!action2.supports(payload2)) {
            throw new IllegalArgumentException(
                    "Action " + action2 + " does not support payload " + payload2);
        }
        this.action = Objects.requireNonNull(action2, "action");
        this.payload = Objects.requireNonNull(payload2, "payload");
    }

    @NotNull
    public ClickEvent.Action action() {
        return this.action;
    }

    @Deprecated
    @NotNull
    public String value() {
        if (this.payload instanceof ClickEvent.Payload.Text) {
            return ((ClickEvent.Payload.Text) this.payload).value();
        } else if (this.action == ClickEvent.Action.CHANGE_PAGE) {
            return String.valueOf(((ClickEvent.Payload.Int) this.payload).integer());
        } else {
            throw new IllegalStateException("Payload is not a string payload, is " + this.payload);
        }
    }

    @NotNull
    public ClickEvent.Payload payload() {
        return this.payload;
    }

    @Override
    public void styleApply(Style.@NotNull Builder builder) {
        builder.clickEvent(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            ClickEvent clickEvent = (ClickEvent) result;
            return this.action == clickEvent.action
                    && Objects.equals(this.payload, clickEvent.payload);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value = this.action.hashCode();
        return 31 * value + this.payload.hashCode();
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("action", this.action),
                ExaminableProperty.of("payload", this.payload));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    public enum Action {
        OPEN_URL("open_url", true, ClickEvent.Payload.Text.class),
        OPEN_FILE("open_file", false, ClickEvent.Payload.Text.class),
        RUN_COMMAND("run_command", true, ClickEvent.Payload.Text.class),
        SUGGEST_COMMAND("suggest_command", true, ClickEvent.Payload.Text.class),
        CHANGE_PAGE("change_page", true, ClickEvent.Payload.Int.class),
        COPY_TO_CLIPBOARD("copy_to_clipboard", true, ClickEvent.Payload.Text.class),
        SHOW_DIALOG("show_dialog", false, ClickEvent.Payload.Dialog.class),
        CUSTOM("custom", true, ClickEvent.Payload.Custom.class);

        public static final Index<String, ClickEvent.Action> NAMES =
                Index.create(ClickEvent.Action.class, result -> result.name);

        private final String name;

        private final boolean readable;

        private final Class<? extends ClickEvent.Payload> payloadType;

        Action(
                @NotNull String name,
                boolean readable,
                @NotNull Class<? extends ClickEvent.Payload> classValue) {
            this.name = name;
            this.readable = readable;
            this.payloadType = classValue;
        }

        public boolean readable() {
            return this.readable;
        }

        public boolean supports(@NotNull ClickEvent.Payload payload) {
            Objects.requireNonNull(payload, "payload");
            return this.payloadType.isAssignableFrom(payload.getClass());
        }

        @Deprecated
        @NotNull
        public Class<? extends ClickEvent.Payload> payloadType() {
            return this.payloadType;
        }

        @NotNull
        @Override
        public String toString() {
            return this.name;
        }
    }

    public interface Payload extends Examinable {

        static ClickEvent.Payload.@NotNull Text string(@NotNull String text) {
            Objects.requireNonNull(text, "value");
            return new PayloadImpl.TextImpl(text);
        }

        static ClickEvent.Payload.@NotNull Int integer(int value) {
            return new PayloadImpl.IntImpl(value);
        }

        static ClickEvent.Payload.@NotNull Dialog dialog(@NotNull DialogLike dialogLike) {
            Objects.requireNonNull(dialogLike, "dialog");
            return new PayloadImpl.DialogImpl(dialogLike);
        }

        @Deprecated
        static ClickEvent.Payload.@NotNull Custom custom(@NotNull Key key, @NotNull String text) {
            return custom(key, BinaryTagHolder.binaryTagHolder(text));
        }

        static ClickEvent.Payload.@NotNull Custom custom(
                @NotNull Key key, @NotNull BinaryTagHolder binaryTagHolder) {
            Objects.requireNonNull(key, "key");
            Objects.requireNonNull(binaryTagHolder, "nbt");
            return new PayloadImpl.CustomImpl(key, binaryTagHolder);
        }

        interface Custom extends ClickEvent.Payload, Keyed {

            @Deprecated
            @NotNull
            String data();

            @NotNull
            BinaryTagHolder nbt();
        }

        interface Dialog extends ClickEvent.Payload {

            @NotNull
            DialogLike dialog();
        }

        interface Int extends ClickEvent.Payload {

            int integer();
        }

        interface Text extends ClickEvent.Payload {

            @NotNull
            String value();
        }
    }
}
