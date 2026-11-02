package it.ytnoos.lpx.lib.kyori.adventure.text.flattener;

import it.ytnoos.lpx.lib.kyori.adventure.internal.properties.AdventureProperties;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.KeybindComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.ObjectComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.ScoreComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.SelectorComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.ObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.PlayerHeadObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.SpriteObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.util.InheritanceAwareMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

final class ComponentFlattenerImpl implements ComponentFlattener {

    static final ComponentFlattener BASIC =
            new ComponentFlattenerImpl.BuilderImpl()
                    .mapper(KeybindComponent.class, result -> result.keybind())
                    .mapper(
                            ScoreComponent.class,
                            result -> {
                                String text;
                                return (text = result.value()) != null ? text : "";
                            })
                    .mapper(SelectorComponent.class, SelectorComponent::pattern)
                    .mapper(TextComponent.class, TextComponent::content)
                    .mapper(
                            TranslatableComponent.class,
                            result -> {
                                String text;
                                return (text = result.fallback()) != null ? text : result.key();
                            })
                    .mapper(
                            ObjectComponent.class,
                            result -> {
                                SpriteObjectContents spriteObjectContents;
                                ObjectContents objectContents;
                                if ((objectContents = result.contents())
                                        instanceof SpriteObjectContents) {
                                    Key key =
                                            (spriteObjectContents =
                                                            (SpriteObjectContents) objectContents)
                                                    .atlas();
                                    return "["
                                            + spriteObjectContents.sprite().asMinimalString()
                                            + (!key.equals(SpriteObjectContents.DEFAULT_ATLAS)
                                                    ? "@" + key.asMinimalString()
                                                    : "")
                                            + "]";
                                } else if (objectContents
                                        instanceof PlayerHeadObjectContents playerHead) {
                                    return "["
                                            + (playerHead.name() != null
                                                    ? playerHead.name()
                                                    : "unknown player")
                                            + " head]";
                                } else {
                                    return "";
                                }
                            })
                    .build();

    static final ComponentFlattener TEXT_ONLY =
            new ComponentFlattenerImpl.BuilderImpl()
                    .mapper(TextComponent.class, TextComponent::content)
                    .build();

    private final InheritanceAwareMap<Component, ComponentFlattenerImpl.Handler> flatteners;

    private final Function<Component, String> unknownHandler;

    private final int maxNestedDepth;

    ComponentFlattenerImpl(
            InheritanceAwareMap<Component, ComponentFlattenerImpl.Handler> inheritanceAwareMap,
            @Nullable Function<Component, String> function,
            int maxNestedDepth) {
        this.flatteners = inheritanceAwareMap;
        this.unknownHandler = function;
        this.maxNestedDepth = maxNestedDepth;
    }

    @Override
    public void flatten(
            @NotNull Component component, @NotNull FlattenerListener flattenerListener) {
        this.flatten0(component, flattenerListener, 0, 0);
    }

    private void flatten0(
            @NotNull Component component2,
            @NotNull FlattenerListener flattenerListener,
            int value,
            int value2) {
        Objects.requireNonNull(component2, "input");
        Objects.requireNonNull(flattenerListener, "listener");
        if (component2 != Component.empty()) {
            if (this.maxNestedDepth != -1 && value2 > this.maxNestedDepth) {
                throw new IllegalStateException(
                        "Exceeded maximum nesting depth of "
                                + this.maxNestedDepth
                                + " while attempting to flatten components!");
            }
            ArrayDeque values = new ArrayDeque();
            ArrayDeque values2 = new ArrayDeque();
            values.push(new ComponentFlattenerImpl.StackEntry(component2, value, 1));
            while (!values.isEmpty()) {
                ComponentFlattenerImpl.StackEntry stackEntry;
                int value3;
                if ((value3 = (stackEntry = (ComponentFlattenerImpl.StackEntry) values.pop()).depth)
                        > 512) {
                    throw new IllegalStateException(
                            "Exceeded maximum depth of 512 while attempting to flatten components!");
                }
                Component component3 = stackEntry.component;
                ComponentFlattenerImpl.Handler handler = this.flattener(component3);
                Style style2 = component3.style();
                flattenerListener.pushStyle(style2);
                values2.push(style2);
                if (handler != null) {
                    handler.handle(this, component3, flattenerListener, value3, value2);
                }
                if (!component3.children().isEmpty() && flattenerListener.shouldContinue()) {
                    List<Component> values3;
                    for (int value4 = (values3 = component3.children()).size() - 1;
                            value4 >= 0;
                            value4--) {
                        if (value4 == values3.size() - 1) {
                            values.push(
                                    new ComponentFlattenerImpl.StackEntry(
                                            (Component) values3.get(value4),
                                            value3 + 1,
                                            stackEntry.stylesToPop + 1));
                        } else {
                            values.push(
                                    new ComponentFlattenerImpl.StackEntry(
                                            (Component) values3.get(value4), value3 + 1, 1));
                        }
                    }
                } else {
                    for (int value5 = stackEntry.stylesToPop; value5 > 0; value5--) {
                        Style style3 = (Style) values2.pop();
                        flattenerListener.popStyle(style3);
                    }
                }
            }
            while (!values2.isEmpty()) {
                Style style4 = (Style) values2.pop();
                flattenerListener.popStyle(style4);
            }
        }
    }

    @Nullable
    private <T extends Component> ComponentFlattenerImpl.Handler flattener(T t) {
        ComponentFlattenerImpl.Handler handler;
        if ((handler = this.flatteners.get((Class<? extends Component>) t.getClass())) == null
                && this.unknownHandler != null) {
            return (result, result2, result3, result4, result5) ->
                    result3.component(this.unknownHandler.apply(result2));
        } else {
            return handler;
        }
    }

    public ComponentFlattener.@NotNull Builder toBuilder() {
        return new ComponentFlattenerImpl.BuilderImpl(
                this.flatteners, this.unknownHandler, this.maxNestedDepth);
    }

    static final class BuilderImpl implements ComponentFlattener.Builder {

        private final InheritanceAwareMap.Builder<Component, ComponentFlattenerImpl.Handler>
                flatteners;

        @Nullable private Function<Component, String> unknownHandler;

        private int maxNestedDepth =
                AdventureProperties.DEFAULT_FLATTENER_NESTING_LIMIT.valueOr(-1);

        BuilderImpl() {
            this.flatteners =
                    InheritanceAwareMap.<Component, ComponentFlattenerImpl.Handler>builder()
                            .strict(true);
        }

        BuilderImpl(
                InheritanceAwareMap<Component, ComponentFlattenerImpl.Handler> inheritanceAwareMap,
                @Nullable Function<Component, String> function,
                int maxNestedDepth) {
            this.flatteners =
                    InheritanceAwareMap.<Component, ComponentFlattenerImpl.Handler>builder(
                                    inheritanceAwareMap)
                            .strict(true);
            this.unknownHandler = function;
            this.maxNestedDepth = maxNestedDepth;
        }

        @NotNull
        public ComponentFlattener build() {
            return new ComponentFlattenerImpl(
                    this.flatteners.build(), this.unknownHandler, this.maxNestedDepth);
        }

        @Override
        public <T extends Component> ComponentFlattener.@NotNull Builder mapper(
                @NotNull Class<T> classValue, @NotNull Function<T, String> function) {
            this.flatteners.put(
                    classValue,
                    (result, result2, result3, result4, result5) ->
                            result3.component((String) ((Function) function).apply(result2)));
            return this;
        }

        @Override
        public <T extends Component> ComponentFlattener.@NotNull Builder complexMapper(
                @NotNull Class<T> classValue,
                @NotNull BiConsumer<T, Consumer<Component>> biConsumer) {
            this.flatteners.put(
                    classValue,
                    (result, result2, result3, result4, result5) -> {
                        biConsumer.accept(
                                (T) result2,
                                result6 ->
                                        ((ComponentFlattenerImpl) result)
                                                .flatten0(
                                                        result6,
                                                        ((FlattenerListener) result3),
                                                        ((int) result4),
                                                        ((int) result5) + 1));
                    });
            return this;
        }

        @Override
        public ComponentFlattener.@NotNull Builder unknownMapper(
                @Nullable Function<Component, String> function) {
            this.unknownHandler = function;
            return this;
        }

        @NotNull
        @Override
        public ComponentFlattener.Builder nestingLimit(
                @Range(from = 1L, to = 2147483647L) int maxNestedDepth) {
            if (maxNestedDepth != -1 && maxNestedDepth < 1) {
                throw new IllegalArgumentException(
                        "limit must be positive or ComponentFlattener.NO_NESTING_LIMIT");
            }
            this.maxNestedDepth = maxNestedDepth;
            return this;
        }
    }

    @FunctionalInterface
    interface Handler {

        void handle(
                ComponentFlattenerImpl componentFlattenerImpl,
                Component component,
                FlattenerListener flattenerListener,
                int value,
                int value2);
    }

    private static final class StackEntry {

        final Component component;

        final int depth;

        final int stylesToPop;

        StackEntry(Component component2, int depth, int stylesToPop) {
            this.component = component2;
            this.depth = depth;
            this.stylesToPop = stylesToPop;
        }
    }
}
