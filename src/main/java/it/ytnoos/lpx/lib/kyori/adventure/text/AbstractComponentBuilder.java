package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEventSource;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.util.ARGBLike;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

abstract class AbstractComponentBuilder<
                C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>>
        implements ComponentBuilder<C, B> {

    protected List<Component> children = Collections.emptyList();

    @Nullable private Style style;

    private Style.@Nullable Builder styleBuilder;

    protected AbstractComponentBuilder() {}

    protected AbstractComponentBuilder(@NotNull C c) {
        List<it.ytnoos.lpx.lib.kyori.adventure.text.Component> values;
        if (!(values = c.children()).isEmpty()) {
            this.children = new ArrayList<>(values);
        }
        if (c.hasStyling()) {
            this.style = c.style();
        }
    }

    @NotNull
    @Override
    public B append(@NotNull Component component) {
        if (component == Component.empty()) {
            return (B) this;
        }
        this.prepareChildren();
        this.children.add(Objects.requireNonNull(component, "component"));
        return (B) this;
    }

    @NotNull
    @Override
    public B append(@NotNull Component... component) {
        return this.append(component);
    }

    @NotNull
    @Override
    public B append(@NotNull ComponentLike... componentLike) {
        Objects.requireNonNull(componentLike, "components");
        boolean matches = false;
        int value = 0;
        for (int value2 = componentLike.length; value < value2; value++) {
            Component component;
            if ((component =
                            Objects.requireNonNull(componentLike[value], "components[?]")
                                    .asComponent())
                    != Component.empty()) {
                if (!matches) {
                    this.prepareChildren();
                    matches = true;
                }
                this.children.add(Objects.requireNonNull(component, "components[?]"));
            }
        }
        return (B) this;
    }

    @NotNull
    @Override
    public B append(@NotNull Iterable<? extends ComponentLike> iterable) {
        Objects.requireNonNull(iterable, "components");
        boolean matches = false;
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike> iterator2 =
                iterable.iterator();
        while (iterator2.hasNext()) {
            Component component;
            if ((component =
                            ((ComponentLike)
                                            Objects.requireNonNull(
                                                    (ComponentLike) iterator2.next(),
                                                    "components[?]"))
                                    .asComponent())
                    != Component.empty()) {
                if (!matches) {
                    this.prepareChildren();
                    matches = true;
                }
                this.children.add(Objects.requireNonNull(component, "components[?]"));
            }
        }
        return (B) this;
    }

    private void prepareChildren() {
        if (this.children == Collections.<Component>emptyList()) {
            this.children = new ArrayList<>();
        }
    }

    @NotNull
    @Override
    public B applyDeep(@NotNull Consumer<? super ComponentBuilder<?, ?>> consumer) {
        this.apply(consumer);
        if (this.children == Collections.<Component>emptyList()) {
            return (B) this;
        }
        ListIterator listIterator2 = this.children.listIterator();
        while (listIterator2.hasNext()) {
            Component component;
            if ((component = (Component) listIterator2.next()) instanceof BuildableComponent) {
                ComponentBuilder componentBuilder;
                (componentBuilder = ((BuildableComponent) component).toBuilder())
                        .applyDeep(consumer);
                listIterator2.set(componentBuilder.build());
            }
        }
        return (B) this;
    }

    @NotNull
    @Override
    public B mapChildren(
            @NotNull
                    Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>>
                            function) {
        if (this.children == Collections.<Component>emptyList()) {
            return (B) this;
        }
        ListIterator listIterator2 = this.children.listIterator();
        while (listIterator2.hasNext()) {
            Component component;
            if ((component = (Component) listIterator2.next()) instanceof BuildableComponent) {
                BuildableComponent buildableComponent =
                        Objects.requireNonNull(
                                (BuildableComponent) function.apply((BuildableComponent) component),
                                "mappedChild");
                if (component != buildableComponent) {
                    listIterator2.set(buildableComponent);
                }
            }
        }
        return (B) this;
    }

    @NotNull
    @Override
    public B mapChildrenDeep(
            @NotNull
                    Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>>
                            function) {
        if (this.children == Collections.<Component>emptyList()) {
            return (B) this;
        }
        ListIterator listIterator2 = this.children.listIterator();
        while (listIterator2.hasNext()) {
            Component component;
            if ((component = (Component) listIterator2.next()) instanceof BuildableComponent) {
                BuildableComponent buildableComponent;
                if ((buildableComponent =
                                Objects.requireNonNull(
                                        (BuildableComponent)
                                                function.apply((BuildableComponent) component),
                                        "mappedChild"))
                        .children()
                        .isEmpty()) {
                    if (component != buildableComponent) {
                        listIterator2.set(buildableComponent);
                    }
                } else {
                    ComponentBuilder componentBuilder;
                    (componentBuilder = buildableComponent.toBuilder()).mapChildrenDeep(function);
                    listIterator2.set(componentBuilder.build());
                }
            }
        }
        return (B) this;
    }

    @NotNull
    @Override
    public List<Component> children() {
        return Collections.unmodifiableList(this.children);
    }

    @NotNull
    @Override
    public B style(@NotNull Style style2) {
        this.style = style2;
        this.styleBuilder = null;
        return (B) this;
    }

    @NotNull
    @Override
    public B style(@NotNull Consumer<Style.Builder> consumer) {
        consumer.accept(this.styleBuilder());
        return (B) this;
    }

    @NotNull
    @Override
    public B font(@Nullable Key key) {
        this.styleBuilder().font(key);
        return (B) this;
    }

    @NotNull
    @Override
    public B color(@Nullable TextColor textColor) {
        this.styleBuilder().color(textColor);
        return (B) this;
    }

    @NotNull
    @Override
    public B colorIfAbsent(@Nullable TextColor textColor) {
        this.styleBuilder().colorIfAbsent(textColor);
        return (B) this;
    }

    @NotNull
    public B shadowColor(@Nullable ARGBLike aRGBLike) {
        this.styleBuilder().shadowColor(aRGBLike);
        return (B) this;
    }

    @NotNull
    public B shadowColorIfAbsent(@Nullable ARGBLike aRGBLike) {
        this.styleBuilder().shadowColorIfAbsent(aRGBLike);
        return (B) this;
    }

    @NotNull
    @Override
    public B decoration(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
        this.styleBuilder().decoration(textDecoration, state);
        return (B) this;
    }

    @NotNull
    @Override
    public B decorationIfAbsent(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
        this.styleBuilder().decorationIfAbsent(textDecoration, state);
        return (B) this;
    }

    @NotNull
    @Override
    public B clickEvent(@Nullable ClickEvent clickEvent2) {
        this.styleBuilder().clickEvent(clickEvent2);
        return (B) this;
    }

    @NotNull
    @Override
    public B hoverEvent(@Nullable HoverEventSource<?> hoverEventSource) {
        this.styleBuilder().hoverEvent(hoverEventSource);
        return (B) this;
    }

    @NotNull
    @Override
    public B insertion(@Nullable String text) {
        this.styleBuilder().insertion(text);
        return (B) this;
    }

    @NotNull
    @Override
    public B mergeStyle(@NotNull Component component, @NotNull Set<Style.Merge> values) {
        Style style2;
        if ((style2 = Objects.requireNonNull(component, "that").style()).isEmpty()
                && values.isEmpty()) {
            return (B) this;
        }
        this.styleBuilder().merge(style2, values);
        return (B) this;
    }

    @NotNull
    @Override
    public B resetStyle() {
        this.style = null;
        this.styleBuilder = null;
        return (B) this;
    }

    private Style.@NotNull Builder styleBuilder() {
        if (this.styleBuilder == null) {
            if (this.style != null) {
                this.styleBuilder = this.style.toBuilder();
                this.style = null;
            } else {
                this.styleBuilder = Style.style();
            }
        }
        return this.styleBuilder;
    }

    protected final boolean hasStyle() {
        return this.styleBuilder != null || this.style != null;
    }

    @NotNull
    protected Style buildStyle() {
        if (this.styleBuilder != null) {
            return this.styleBuilder.build();
        } else {
            return this.style != null ? this.style : Style.empty();
        }
    }
}
