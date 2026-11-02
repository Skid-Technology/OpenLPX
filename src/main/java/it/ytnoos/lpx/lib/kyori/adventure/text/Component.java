package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEventSource;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.ShadowColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleBuilderApplicable;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleGetter;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleSetter;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.ObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.translation.Translatable;
import it.ytnoos.lpx.lib.kyori.adventure.util.ARGBLike;
import it.ytnoos.lpx.lib.kyori.adventure.util.ForwardingIterator;
import it.ytnoos.lpx.lib.kyori.adventure.util.IntFunction2;
import it.ytnoos.lpx.lib.kyori.adventure.util.MonkeyBars;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Stream;

@NonExtendable
public interface Component
        extends ComponentBuilderApplicable,
                ComponentLike,
                Examinable,
                HoverEventSource<Component>,
                StyleGetter,
                StyleSetter<Component> {

    BiPredicate<? super Component, ? super Component> EQUALS = Objects::equals;

    BiPredicate<? super Component, ? super Component> EQUALS_IDENTITY =
            (result, result2) -> result == result2;

    Predicate<? super Component> IS_NOT_EMPTY = result -> result != empty();

    @NotNull
    static TextComponent empty() {
        return TextComponentImpl.EMPTY;
    }

    @NotNull
    static TextComponent newline() {
        return TextComponentImpl.NEWLINE;
    }

    @NotNull
    static TextComponent space() {
        return TextComponentImpl.SPACE;
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent join(
            @NotNull ComponentLike componentLike, @NotNull ComponentLike... componentLike2) {
        return join(componentLike, Arrays.asList(componentLike2));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent join(
            @NotNull ComponentLike componentLike, Iterable<? extends ComponentLike> iterable) {
        Component component;
        return (component = join(JoinConfiguration.separator(componentLike), iterable))
                        instanceof TextComponent
                ? (TextComponent) component
                : text().append(component).build();
    }

    @Contract(pure = true)
    @NotNull
    static Component join(
            JoinConfiguration.@NotNull Builder builder, @NotNull ComponentLike... componentLike) {
        return join(builder, Arrays.asList(componentLike));
    }

    @Contract(pure = true)
    @NotNull
    static Component join(
            JoinConfiguration.@NotNull Builder builder,
            @NotNull Iterable<? extends ComponentLike> iterable) {
        return JoinConfigurationImpl.join(builder.build(), iterable);
    }

    @Contract(pure = true)
    @NotNull
    static Component join(
            @NotNull JoinConfiguration joinConfiguration, @NotNull ComponentLike... componentLike) {
        return join(joinConfiguration, Arrays.asList(componentLike));
    }

    @Contract(pure = true)
    @NotNull
    static Component join(
            @NotNull JoinConfiguration joinConfiguration,
            @NotNull Iterable<? extends ComponentLike> iterable) {
        return JoinConfigurationImpl.join(joinConfiguration, iterable);
    }

    @NotNull
    static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent() {
        return toComponent(empty());
    }

    @NotNull
    static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent(
            @NotNull Component component) {
        Supplier<TextComponent.Builder> supplier = Component::text;
        BiConsumer<TextComponent.Builder, Component> biConsumer =
                (result, result2) -> {
                    if (component != empty() && !result.children().isEmpty()) {
                        result.append(component);
                    }
                    result.append(result2);
                };
        return Collector.of(
                supplier,
                biConsumer,
                (result, result2) -> {
                    List values = result.children();
                    TextComponent.Builder builder = text().append(values);
                    if (!values.isEmpty()) {
                        builder.append(component);
                    }
                    builder.append(result2.children());
                    return builder;
                },
                ComponentBuilder::build);
    }

    @Contract(pure = true)
    static BlockNBTComponent.@NotNull Builder blockNBT() {
        return new BlockNBTComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static BlockNBTComponent blockNBT(
            @NotNull Consumer<? super BlockNBTComponent.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(blockNBT(), consumer);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static BlockNBTComponent blockNBT(@NotNull String text, BlockNBTComponent.@NotNull Pos pos) {
        return blockNBT(text, false, pos);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static BlockNBTComponent blockNBT(
            @NotNull String text, boolean matches, BlockNBTComponent.@NotNull Pos pos) {
        return blockNBT(text, matches, null, pos);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static BlockNBTComponent blockNBT(
            @NotNull String text,
            boolean matches,
            @Nullable ComponentLike componentLike,
            BlockNBTComponent.@NotNull Pos pos) {
        return BlockNBTComponentImpl.create(
                Collections.emptyList(), Style.empty(), text, matches, componentLike, pos);
    }

    @Contract(pure = true)
    static EntityNBTComponent.@NotNull Builder entityNBT() {
        return new EntityNBTComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static EntityNBTComponent entityNBT(
            @NotNull Consumer<? super EntityNBTComponent.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(entityNBT(), consumer);
    }

    @Contract("_, _ -> new")
    @NotNull
    static EntityNBTComponent entityNBT(@NotNull String text, @NotNull String text2) {
        return entityNBT().nbtPath(text).selector(text2).build();
    }

    @Contract(pure = true)
    static KeybindComponent.@NotNull Builder keybind() {
        return new KeybindComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static KeybindComponent keybind(@NotNull Consumer<? super KeybindComponent.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(keybind(), consumer);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(@NotNull String text) {
        return keybind(text, Style.empty());
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(KeybindComponent.@NotNull KeybindLike keybindLike) {
        return keybind(Objects.requireNonNull(keybindLike, "keybind").asKeybind(), Style.empty());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(@NotNull String text, @NotNull Style style) {
        return KeybindComponentImpl.create(
                Collections.emptyList(), Objects.requireNonNull(style, "style"), text);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(
            KeybindComponent.@NotNull KeybindLike keybindLike, @NotNull Style style) {
        return KeybindComponentImpl.create(
                Collections.emptyList(),
                Objects.requireNonNull(style, "style"),
                Objects.requireNonNull(keybindLike, "keybind").asKeybind());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(@NotNull String text, @Nullable TextColor textColor) {
        return keybind(text, Style.style(textColor));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(
            KeybindComponent.@NotNull KeybindLike keybindLike, @Nullable TextColor textColor) {
        return keybind(
                Objects.requireNonNull(keybindLike, "keybind").asKeybind(), Style.style(textColor));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(
            @NotNull String text,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return keybind(text, Style.style(textColor, textDecoration));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(
            KeybindComponent.@NotNull KeybindLike keybindLike,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return keybind(
                Objects.requireNonNull(keybindLike, "keybind").asKeybind(),
                Style.style(textColor, textDecoration));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(
            @NotNull String text,
            @Nullable TextColor textColor,
            @NotNull Set<TextDecoration> values) {
        return keybind(text, Style.style(textColor, values));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static KeybindComponent keybind(
            KeybindComponent.@NotNull KeybindLike keybindLike,
            @Nullable TextColor textColor,
            @NotNull Set<TextDecoration> values) {
        return keybind(
                Objects.requireNonNull(keybindLike, "keybind").asKeybind(),
                Style.style(textColor, values));
    }

    @Contract(pure = true)
    static ObjectComponent.@NotNull Builder object() {
        return new ObjectComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static ObjectComponent object(@NotNull Consumer<? super ObjectComponent.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(object(), consumer);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static ObjectComponent object(@NotNull ObjectContents objectContents) {
        return ObjectComponentImpl.create(Collections.emptyList(), Style.empty(), objectContents);
    }

    @Contract(pure = true)
    static ScoreComponent.@NotNull Builder score() {
        return new ScoreComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static ScoreComponent score(@NotNull Consumer<? super ScoreComponent.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(score(), consumer);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static ScoreComponent score(@NotNull String text, @NotNull String text2) {
        return score(text, text2, null);
    }

    @Deprecated
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static ScoreComponent score(
            @NotNull String text, @NotNull String text2, @Nullable String text3) {
        return ScoreComponentImpl.create(
                Collections.emptyList(), Style.empty(), text, text2, text3);
    }

    @Contract(pure = true)
    static SelectorComponent.@NotNull Builder selector() {
        return new SelectorComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static SelectorComponent selector(
            @NotNull Consumer<? super SelectorComponent.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(selector(), consumer);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static SelectorComponent selector(@NotNull String text) {
        return selector(text, null);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static SelectorComponent selector(@NotNull String text, @Nullable ComponentLike componentLike) {
        return SelectorComponentImpl.create(
                Collections.emptyList(), Style.empty(), text, componentLike);
    }

    @Contract(pure = true)
    static StorageNBTComponent.@NotNull Builder storageNBT() {
        return new StorageNBTComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static StorageNBTComponent storageNBT(
            @NotNull Consumer<? super StorageNBTComponent.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(storageNBT(), consumer);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static StorageNBTComponent storageNBT(@NotNull String text, @NotNull Key key) {
        return storageNBT(text, false, key);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static StorageNBTComponent storageNBT(@NotNull String text, boolean matches, @NotNull Key key) {
        return storageNBT(text, matches, null, key);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static StorageNBTComponent storageNBT(
            @NotNull String text,
            boolean matches,
            @Nullable ComponentLike componentLike,
            @NotNull Key key) {
        return StorageNBTComponentImpl.create(
                Collections.emptyList(), Style.empty(), text, matches, componentLike, key);
    }

    @Contract(pure = true)
    static TextComponent.@NotNull Builder text() {
        return new TextComponentImpl.BuilderImpl();
    }

    @NotNull
    static TextComponent textOfChildren(@NotNull ComponentLike... componentLike) {
        return componentLike.length == 0
                ? empty()
                : TextComponentImpl.create(Arrays.asList(componentLike), Style.empty(), "");
    }

    @Contract("_ -> new")
    @NotNull
    static TextComponent text(@NotNull Consumer<? super TextComponent.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(text(), consumer);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(@NotNull String text2) {
        return text2.isEmpty() ? empty() : text(text2, Style.empty());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(@NotNull String text2, @NotNull Style style) {
        return TextComponentImpl.create(
                Collections.emptyList(), Objects.requireNonNull(style, "style"), text2);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(@NotNull String text2, @Nullable TextColor textColor) {
        return text(text2, Style.style(textColor));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            @NotNull String text2,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return text(text2, Style.style(textColor, textDecoration));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            @NotNull String text2,
            @Nullable TextColor textColor,
            @NotNull Set<TextDecoration> values) {
        return text(text2, Style.style(textColor, values));
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(boolean matches) {
        return text(String.valueOf(matches));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(boolean matches, @NotNull Style style) {
        return text(String.valueOf(matches), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(boolean matches, @Nullable TextColor textColor) {
        return text(String.valueOf(matches), textColor);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            boolean matches,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return text(String.valueOf(matches), textColor, textDecoration);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            boolean matches, @Nullable TextColor textColor, @NotNull Set<TextDecoration> values) {
        return text(String.valueOf(matches), textColor, values);
    }

    @Contract(pure = true)
    @NotNull
    static TextComponent text(char value) {
        if (value == '\n') {
            return newline();
        } else {
            return value == ' ' ? space() : text(String.valueOf(value));
        }
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(char value2, @NotNull Style style) {
        return text(String.valueOf(value2), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(char value2, @Nullable TextColor textColor) {
        return text(String.valueOf(value2), textColor);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            char value2,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return text(String.valueOf(value2), textColor, textDecoration);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            char value2, @Nullable TextColor textColor, @NotNull Set<TextDecoration> values) {
        return text(String.valueOf(value2), textColor, values);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(double value2) {
        return text(String.valueOf(value2));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(double value2, @NotNull Style style) {
        return text(String.valueOf(value2), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(double value2, @Nullable TextColor textColor) {
        return text(String.valueOf(value2), textColor);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            double value2,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return text(String.valueOf(value2), textColor, textDecoration);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            double value2, @Nullable TextColor textColor, @NotNull Set<TextDecoration> values) {
        return text(String.valueOf(value2), textColor, values);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(float value2) {
        return text(String.valueOf(value2));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(float value2, @NotNull Style style) {
        return text(String.valueOf(value2), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(float value2, @Nullable TextColor textColor) {
        return text(String.valueOf(value2), textColor);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            float value2,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return text(String.valueOf(value2), textColor, textDecoration);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            float value2, @Nullable TextColor textColor, @NotNull Set<TextDecoration> values) {
        return text(String.valueOf(value2), textColor, values);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(int value2) {
        return text(String.valueOf(value2));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(int value2, @NotNull Style style) {
        return text(String.valueOf(value2), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(int value2, @Nullable TextColor textColor) {
        return text(String.valueOf(value2), textColor);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            int value2, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecoration) {
        return text(String.valueOf(value2), textColor, textDecoration);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            int value2, @Nullable TextColor textColor, @NotNull Set<TextDecoration> values) {
        return text(String.valueOf(value2), textColor, values);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TextComponent text(long value2) {
        return text(String.valueOf(value2));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(long value2, @NotNull Style style) {
        return text(String.valueOf(value2), style);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TextComponent text(long value2, @Nullable TextColor textColor) {
        return text(String.valueOf(value2), textColor);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            long value2,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return text(String.valueOf(value2), textColor, textDecoration);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TextComponent text(
            long value2, @Nullable TextColor textColor, @NotNull Set<TextDecoration> values) {
        return text(String.valueOf(value2), textColor, values);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static <C> VirtualComponent virtual(
            @NotNull Class<C> classValue,
            @NotNull VirtualComponentRenderer<C> virtualComponentRenderer) {
        Objects.requireNonNull(classValue, "context type");
        Objects.requireNonNull(virtualComponentRenderer, "renderer");
        return VirtualComponentImpl.createVirtual(classValue, virtualComponentRenderer);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static <C> VirtualComponent virtual(
            @NotNull Class<C> classValue,
            @NotNull VirtualComponentRenderer<C> virtualComponentRenderer,
            @NotNull Style style) {
        Objects.requireNonNull(classValue, "context type");
        Objects.requireNonNull(virtualComponentRenderer, "renderer");
        return VirtualComponentImpl.createVirtual(
                classValue, virtualComponentRenderer, Collections.emptyList(), style);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static <C> VirtualComponent virtual(
            @NotNull Class<C> classValue,
            @NotNull VirtualComponentRenderer<C> virtualComponentRenderer,
            @NotNull StyleBuilderApplicable... styleBuilderApplicable) {
        Objects.requireNonNull(classValue, "context type");
        Objects.requireNonNull(virtualComponentRenderer, "renderer");
        return VirtualComponentImpl.createVirtual(
                classValue,
                virtualComponentRenderer,
                Collections.emptyList(),
                Style.style(styleBuilderApplicable));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static <C> VirtualComponent virtual(
            @NotNull Class<C> classValue,
            @NotNull VirtualComponentRenderer<C> virtualComponentRenderer,
            @NotNull Iterable<StyleBuilderApplicable> iterable) {
        Objects.requireNonNull(classValue, "context type");
        Objects.requireNonNull(virtualComponentRenderer, "renderer");
        return VirtualComponentImpl.createVirtual(
                classValue,
                virtualComponentRenderer,
                Collections.emptyList(),
                Style.style(iterable));
    }

    @Contract(pure = true)
    static TranslatableComponent.@NotNull Builder translatable() {
        return new TranslatableComponentImpl.BuilderImpl();
    }

    @Contract("_ -> new")
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Consumer<? super TranslatableComponent.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(translatable(), consumer);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull String text) {
        return translatable(text, Style.empty());
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull Translatable translatable2) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                Style.empty());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull String text, @Nullable String text2) {
        return translatable(text, text2, Style.empty());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2, @Nullable String text) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                text,
                Style.empty());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull String text, @NotNull Style style) {
        return TranslatableComponentImpl.create(
                Collections.emptyList(),
                Objects.requireNonNull(style, "style"),
                text,
                null,
                Collections.emptyList());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2, @NotNull Style style) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(), style);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text, @Nullable String text2, @NotNull Style style) {
        return TranslatableComponentImpl.create(
                Collections.emptyList(),
                Objects.requireNonNull(style, "style"),
                text,
                text2,
                Collections.emptyList());
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2, @Nullable String text, @NotNull Style style) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                text,
                style);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable String text2,
            @NotNull StyleBuilderApplicable... styleBuilderApplicable) {
        return translatable(
                Objects.requireNonNull(text, "key"), text2, Style.style(styleBuilderApplicable));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable String text,
            @NotNull Iterable<StyleBuilderApplicable> iterable) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                text,
                Style.style(iterable));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text, @Nullable String text2, @NotNull ComponentLike... componentLike) {
        return translatable(text, text2, Style.empty(), componentLike);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable String text,
            @NotNull ComponentLike... componentLike) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                text,
                componentLike);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable String text2,
            @NotNull Style style,
            @NotNull ComponentLike... componentLike) {
        return TranslatableComponentImpl.create(
                Collections.emptyList(),
                Objects.requireNonNull(style, "style"),
                text,
                text2,
                Objects.requireNonNull(componentLike, "args"));
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable String text,
            @NotNull Style style,
            @NotNull ComponentLike... componentLike) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                text,
                style,
                componentLike);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable String text2,
            @NotNull Style style,
            @NotNull List<? extends ComponentLike> values) {
        return TranslatableComponentImpl.create(
                Collections.emptyList(),
                style,
                text,
                text2,
                Objects.requireNonNull(values, "args"));
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable String text,
            @NotNull Style style,
            @NotNull List<? extends ComponentLike> values) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                text,
                style,
                values);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable String text2,
            @NotNull List<? extends ComponentLike> values,
            @NotNull Iterable<StyleBuilderApplicable> iterable) {
        return TranslatableComponentImpl.create(
                Collections.emptyList(),
                Style.style(iterable),
                text,
                text2,
                Objects.requireNonNull(values, "args"));
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable String text,
            @NotNull List<? extends ComponentLike> values,
            @NotNull Iterable<StyleBuilderApplicable> iterable) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                text,
                values,
                iterable);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable String text2,
            @NotNull List<? extends ComponentLike> values,
            @NotNull StyleBuilderApplicable... styleBuilderApplicable) {
        return TranslatableComponentImpl.create(
                Collections.emptyList(),
                Style.style(styleBuilderApplicable),
                text,
                text2,
                Objects.requireNonNull(values, "args"));
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable String text,
            @NotNull List<? extends ComponentLike> values,
            @NotNull StyleBuilderApplicable... styleBuilderApplicable) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                text,
                values,
                styleBuilderApplicable);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(@NotNull String text, @Nullable TextColor textColor) {
        return translatable(text, Style.style(textColor));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2, @Nullable TextColor textColor) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(), textColor);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return translatable(text, Style.style(textColor, textDecoration));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable TextColor textColor,
            TextDecoration @NotNull ... textDecoration) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                textColor,
                textDecoration);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable TextColor textColor,
            @NotNull Set<TextDecoration> values) {
        return translatable(text, Style.style(textColor, values));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable TextColor textColor,
            @NotNull Set<TextDecoration> values) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                textColor,
                values);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text, @NotNull ComponentLike... componentLike) {
        return translatable(text, Style.empty(), componentLike);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2, @NotNull ComponentLike... componentLike) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                componentLike);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text, @NotNull Style style, @NotNull ComponentLike... componentLike) {
        return TranslatableComponentImpl.create(
                Collections.emptyList(),
                Objects.requireNonNull(style, "style"),
                text,
                null,
                Objects.requireNonNull(componentLike, "args"));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @NotNull Style style,
            @NotNull ComponentLike... componentLike) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                style,
                componentLike);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable TextColor textColor,
            @NotNull ComponentLike... componentLike) {
        return translatable(text, Style.style(textColor), componentLike);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable TextColor textColor,
            @NotNull ComponentLike... componentLike) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                textColor,
                componentLike);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable TextColor textColor,
            @NotNull Set<TextDecoration> values,
            @NotNull ComponentLike... componentLike) {
        return translatable(text, Style.style(textColor, values), componentLike);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable TextColor textColor,
            @NotNull Set<TextDecoration> values,
            @NotNull ComponentLike... componentLike) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                textColor,
                values,
                componentLike);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text, @NotNull List<? extends ComponentLike> values) {
        return TranslatableComponentImpl.create(
                Collections.emptyList(),
                Style.empty(),
                text,
                null,
                Objects.requireNonNull(values, "args"));
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2, @NotNull List<? extends ComponentLike> values) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(), values);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @NotNull Style style,
            @NotNull List<? extends ComponentLike> values) {
        return TranslatableComponentImpl.create(
                Collections.emptyList(),
                Objects.requireNonNull(style, "style"),
                text,
                null,
                Objects.requireNonNull(values, "args"));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @NotNull Style style,
            @NotNull List<? extends ComponentLike> values) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                style,
                values);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable TextColor textColor,
            @NotNull List<? extends ComponentLike> values) {
        return translatable(text, Style.style(textColor), values);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable TextColor textColor,
            @NotNull List<? extends ComponentLike> values) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                textColor,
                values);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull String text,
            @Nullable TextColor textColor,
            @NotNull Set<TextDecoration> values,
            @NotNull List<? extends ComponentLike> values2) {
        return translatable(text, Style.style(textColor, values), values2);
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    @NotNull
    static TranslatableComponent translatable(
            @NotNull Translatable translatable2,
            @Nullable TextColor textColor,
            @NotNull Set<TextDecoration> values,
            @NotNull List<? extends ComponentLike> values2) {
        return translatable(
                Objects.requireNonNull(translatable2, "translatable").translationKey(),
                textColor,
                values,
                values2);
    }

    @NotNull
    @Unmodifiable
    List<Component> children();

    @Contract(pure = true)
    @NotNull
    Component children(@NotNull List<? extends ComponentLike> values);

    default boolean contains(@NotNull Component component) {
        return this.contains(component, EQUALS_IDENTITY);
    }

    default boolean contains(
            @NotNull Component component,
            @NotNull BiPredicate<? super Component, ? super Component> biPredicate) {
        if (biPredicate.test(this, component)) {
            return true;
        }
        Iterator<it.ytnoos.lpx.lib.kyori.adventure.text.Component> iterator2 =
                this.children().iterator();
        while (iterator2.hasNext()) {
            if (((Component) iterator2.next()).contains(component, biPredicate)) {
                return true;
            }
        }
        HoverEvent hoverEvent2;
        if ((hoverEvent2 = this.hoverEvent()) != null) {
            Object result = hoverEvent2.value();
            Component component2 = null;
            if (result instanceof Component) {
                component2 = (Component) hoverEvent2.value();
            } else if (result instanceof HoverEvent.ShowEntity) {
                component2 = ((HoverEvent.ShowEntity) result).name();
            }
            if (component2 != null) {
                if (biPredicate.test(component, component2)) {
                    return true;
                }
                Iterator<it.ytnoos.lpx.lib.kyori.adventure.text.Component> iterator3 =
                        component2.children().iterator();
                while (iterator3.hasNext()) {
                    if (((Component) iterator3.next()).contains(component, biPredicate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    default void detectCycle(@NotNull Component component) {
        if (component.contains(this)) {
            throw new IllegalStateException(
                    "Component cycle detected between " + this + " and " + component);
        }
    }

    @Contract(pure = true)
    @NotNull
    default Component append(@NotNull Component component) {
        return this.append((ComponentLike) component);
    }

    @NotNull
    default Component append(@NotNull ComponentLike componentLike) {
        Objects.requireNonNull(componentLike, "like");
        Component component;
        Objects.requireNonNull(component = componentLike.asComponent(), "component");
        if (component == empty()) {
            return this;
        }
        List<it.ytnoos.lpx.lib.kyori.adventure.text.Component> values = this.children();
        return this.children(MonkeyBars.addOne(values, component));
    }

    @Contract(pure = true)
    @NotNull
    default Component append(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        return this.append(componentBuilder.build());
    }

    @Contract(pure = true)
    @NotNull
    default Component appendNewline() {
        return this.append(newline());
    }

    @Contract(pure = true)
    @NotNull
    default Component appendSpace() {
        return this.append(space());
    }

    @Contract(pure = true)
    @NotNull
    default Component append(@NotNull ComponentLike... componentLike) {
        if (componentLike.length == 0) {
            return this;
        }
        ArrayList values;
        (values = new ArrayList(componentLike.length + this.children().size()))
                .addAll(this.children());
        Collections.addAll(values, componentLike);
        return this.children(values);
    }

    @Contract(pure = true)
    @NotNull
    default Component append(@NotNull List<? extends ComponentLike> values) {
        if (values.isEmpty()) {
            return this;
        }
        if (this.children().isEmpty()) {
            return this.children(values);
        }
        ArrayList values2;
        (values2 = new ArrayList(values.size() + this.children().size())).addAll(this.children());
        values2.addAll(values);
        return this.children(values2);
    }

    @Contract(pure = true)
    @NotNull
    default Component applyFallbackStyle(@NotNull Style style2) {
        Objects.requireNonNull(style2, "style");
        return this.style(this.style().merge(style2, Style.Merge.Strategy.IF_ABSENT_ON_TARGET));
    }

    @Contract(pure = true)
    @NotNull
    default Component applyFallbackStyle(
            @NotNull StyleBuilderApplicable... styleBuilderApplicable) {
        return this.applyFallbackStyle(Style.style(styleBuilderApplicable));
    }

    @NotNull
    Style style();

    @Contract(pure = true)
    @NotNull
    Component style(@NotNull Style style2);

    @Contract(pure = true)
    @NotNull
    default Component style(@NotNull Consumer<Style.Builder> consumer) {
        return this.style(this.style().edit(consumer));
    }

    @Contract(pure = true)
    @NotNull
    default Component style(
            @NotNull Consumer<Style.Builder> consumer, Style.Merge.@NotNull Strategy strategy) {
        return this.style(this.style().edit(consumer, strategy));
    }

    @Contract(pure = true)
    @NotNull
    default Component style(Style.@NotNull Builder builder) {
        return this.style(builder.build());
    }

    @Contract(pure = true)
    @NotNull
    default Component mergeStyle(@NotNull Component component) {
        return this.mergeStyle(component, Style.Merge.all());
    }

    @Contract(pure = true)
    @NotNull
    default Component mergeStyle(
            @NotNull Component component, Style.@NotNull Merge @NotNull ... merge) {
        return this.mergeStyle(component, Style.Merge.merges(merge));
    }

    @Contract(pure = true)
    @NotNull
    default Component mergeStyle(@NotNull Component component, @NotNull Set<Style.Merge> values) {
        return this.style(this.style().merge(component.style(), values));
    }

    @Nullable
    @Override
    default Key font() {
        return this.style().font();
    }

    @NotNull
    default Component font(@Nullable Key key) {
        return this.style(this.style().font(key));
    }

    @Nullable
    @Override
    default TextColor color() {
        return this.style().color();
    }

    @Nullable
    @Override
    default ShadowColor shadowColor() {
        return this.style().shadowColor();
    }

    @Contract(pure = true)
    @NotNull
    default Component color(@Nullable TextColor textColor) {
        return this.style(this.style().color(textColor));
    }

    @Contract(pure = true)
    @NotNull
    default Component colorIfAbsent(@Nullable TextColor textColor) {
        return this.color() == null ? this.color(textColor) : this;
    }

    @Contract(pure = true)
    @NotNull
    default Component shadowColor(@Nullable ARGBLike aRGBLike) {
        return this.style(this.style().shadowColor(aRGBLike));
    }

    @Contract(pure = true)
    @NotNull
    default Component shadowColorIfAbsent(@Nullable ARGBLike aRGBLike) {
        return this.shadowColor() == null ? this.shadowColor(aRGBLike) : this;
    }

    @Override
    default boolean hasDecoration(@NotNull TextDecoration textDecoration) {
        return StyleGetter.super.hasDecoration(textDecoration);
    }

    @Contract(pure = true)
    @NotNull
    default Component decorate(@NotNull TextDecoration textDecoration) {
        return (Component) StyleSetter.super.decorate(textDecoration);
    }

    @Override
    default TextDecoration.@NotNull State decoration(@NotNull TextDecoration textDecoration) {
        return this.style().decoration(textDecoration);
    }

    @Contract(pure = true)
    @NotNull
    default Component decoration(@NotNull TextDecoration textDecoration, boolean matches) {
        return (Component) StyleSetter.super.decoration(textDecoration, matches);
    }

    @Contract(pure = true)
    @NotNull
    default Component decoration(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
        return this.style(this.style().decoration(textDecoration, state));
    }

    @NotNull
    default Component decorationIfAbsent(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
        Objects.requireNonNull(state, "state");
        return this.decoration(textDecoration) == TextDecoration.State.NOT_SET
                ? this.style(this.style().decoration(textDecoration, state))
                : this;
    }

    @NotNull
    @Override
    default Map<TextDecoration, TextDecoration.State> decorations() {
        return this.style().decorations();
    }

    @Contract(pure = true)
    @NotNull
    default Component decorations(@NotNull Map<TextDecoration, TextDecoration.State> entries) {
        return this.style(this.style().decorations(entries));
    }

    @Nullable
    @Override
    default ClickEvent clickEvent() {
        return this.style().clickEvent();
    }

    @Contract(pure = true)
    @NotNull
    default Component clickEvent(@Nullable ClickEvent clickEvent2) {
        return this.style(this.style().clickEvent(clickEvent2));
    }

    @Nullable
    @Override
    default HoverEvent<?> hoverEvent() {
        return this.style().hoverEvent();
    }

    @Contract(pure = true)
    @NotNull
    default Component hoverEvent(@Nullable HoverEventSource<?> hoverEventSource) {
        return this.style(this.style().hoverEvent(hoverEventSource));
    }

    @Nullable
    @Override
    default String insertion() {
        return this.style().insertion();
    }

    @Contract(pure = true)
    @NotNull
    default Component insertion(@Nullable String text) {
        return this.style(this.style().insertion(text));
    }

    default boolean hasStyling() {
        return !this.style().isEmpty();
    }

    @ScopedComponentOverrideNotRequired
    @Contract(pure = true)
    @NotNull
    default Component replaceText(@NotNull Consumer<TextReplacementConfig.Builder> consumer) {
        Objects.requireNonNull(consumer, "configurer");
        return this.replaceText(
                AbstractBuilder.configureAndBuild(TextReplacementConfig.builder(), consumer));
    }

    @ScopedComponentOverrideNotRequired
    @Contract(pure = true)
    @NotNull
    default Component replaceText(@NotNull TextReplacementConfig textReplacementConfig) {
        Objects.requireNonNull(textReplacementConfig, "replacement");
        if (!(textReplacementConfig instanceof TextReplacementConfigImpl)) {
            throw new IllegalArgumentException(
                    "Provided replacement was a custom TextReplacementConfig implementation, which is not supported.");
        } else {
            return TextReplacementRenderer.INSTANCE.render(
                    this, ((TextReplacementConfigImpl) textReplacementConfig).createState());
        }
    }

    @ScopedComponentOverrideNotRequired
    @NotNull
    default Component compact() {
        return this.compact(null);
    }

    @ScopedComponentOverrideNotRequired
    @NotNull
    default Component compact(@Nullable Style style) {
        return ComponentCompaction.compact(this, style);
    }

    @NotNull
    default Iterable<Component> iterable(
            @NotNull ComponentIteratorType componentIteratorType,
            @NotNull ComponentIteratorFlag @Nullable ... componentIteratorFlag) {
        return this.iterable(
                componentIteratorType,
                componentIteratorFlag == null
                        ? Collections.emptySet()
                        : MonkeyBars.enumSet(ComponentIteratorFlag.class, componentIteratorFlag));
    }

    @NotNull
    default Iterable<Component> iterable(
            @NotNull ComponentIteratorType componentIteratorType,
            @NotNull Set<ComponentIteratorFlag> values) {
        Objects.requireNonNull(componentIteratorType, "type");
        Objects.requireNonNull(values, "flags");
        Supplier supplier = () -> this.iterator(componentIteratorType, ((Set) values));
        return new ForwardingIterator<>(
                supplier, () -> this.spliterator(componentIteratorType, ((Set) values)));
    }

    @NotNull
    default Iterator<Component> iterator(
            @NotNull ComponentIteratorType componentIteratorType,
            @NotNull ComponentIteratorFlag @Nullable ... componentIteratorFlag) {
        return this.iterator(
                componentIteratorType,
                componentIteratorFlag == null
                        ? Collections.emptySet()
                        : MonkeyBars.enumSet(ComponentIteratorFlag.class, componentIteratorFlag));
    }

    @NotNull
    default Iterator<Component> iterator(
            @NotNull ComponentIteratorType componentIteratorType,
            @NotNull Set<ComponentIteratorFlag> values) {
        return new ComponentIterator(
                this,
                Objects.requireNonNull(componentIteratorType, "type"),
                Objects.requireNonNull(values, "flags"));
    }

    @NotNull
    default Spliterator<Component> spliterator(
            @NotNull ComponentIteratorType componentIteratorType,
            @NotNull ComponentIteratorFlag @Nullable ... componentIteratorFlag) {
        return this.spliterator(
                componentIteratorType,
                componentIteratorFlag == null
                        ? Collections.emptySet()
                        : MonkeyBars.enumSet(ComponentIteratorFlag.class, componentIteratorFlag));
    }

    @NotNull
    default Spliterator<Component> spliterator(
            @NotNull ComponentIteratorType componentIteratorType,
            @NotNull Set<ComponentIteratorFlag> values) {
        return Spliterators.spliteratorUnknownSize(
                this.iterator(componentIteratorType, values), 1296);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(pure = true)
    @NotNull
    default Component replaceText(@NotNull String text, @Nullable ComponentLike componentLike) {
        return this.replaceText(result -> result.matchLiteral(text).replacement(componentLike));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(pure = true)
    @NotNull
    default Component replaceText(
            @NotNull Pattern pattern,
            @NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function) {
        return this.replaceText(result -> result.match(pattern).replacement(((Function) function)));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(pure = true)
    @NotNull
    default Component replaceFirstText(
            @NotNull String text, @Nullable ComponentLike componentLike) {
        return this.replaceText(
                result -> result.matchLiteral(text).once().replacement(componentLike));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(pure = true)
    @NotNull
    default Component replaceFirstText(
            @NotNull Pattern pattern,
            @NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function) {
        return this.replaceText(
                result -> result.match(pattern).once().replacement(((Function) function)));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(pure = true)
    @NotNull
    default Component replaceText(
            @NotNull String text, @Nullable ComponentLike componentLike, int value) {
        return this.replaceText(
                result -> result.matchLiteral(text).times(value).replacement(componentLike));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(pure = true)
    @NotNull
    default Component replaceText(
            @NotNull Pattern pattern,
            @NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function,
            int value) {
        return this.replaceText(
                result -> result.match(pattern).times(value).replacement(((Function) function)));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(pure = true)
    @NotNull
    default Component replaceText(
            @NotNull String text,
            @Nullable ComponentLike componentLike,
            @NotNull IntFunction2<PatternReplacementResult> intFunction2) {
        return this.replaceText(
                result ->
                        result.matchLiteral(text)
                                .replacement(componentLike)
                                .condition(((IntFunction2) intFunction2)));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(pure = true)
    @NotNull
    default Component replaceText(
            @NotNull Pattern pattern,
            @NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function,
            @NotNull IntFunction2<PatternReplacementResult> intFunction2) {
        return this.replaceText(
                result ->
                        result.match(pattern)
                                .replacement(((Function) function))
                                .condition(((IntFunction2) intFunction2)));
    }

    @NotNull
    ComponentBuilder<?, ?> toBuilder();

    @Override
    default void componentBuilderApply(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        componentBuilder.append(this);
    }

    @NotNull
    @Override
    default Component asComponent() {
        return this;
    }

    @NotNull
    @Override
    default HoverEvent<Component> asHoverEvent(@NotNull UnaryOperator<Component> unaryOperator) {
        return HoverEvent.showText(unaryOperator.apply(this));
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("style", this.style()),
                ExaminableProperty.of("children", this.children()));
    }
}
