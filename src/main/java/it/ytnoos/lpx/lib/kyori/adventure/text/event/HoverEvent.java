package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.key.Keyed;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleBuilderApplicable;
import it.ytnoos.lpx.lib.kyori.adventure.text.renderer.ComponentRenderer;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public final class HoverEvent<V>
        implements Examinable, HoverEventSource<V>, StyleBuilderApplicable {

    private final HoverEvent.Action<V> action;

    private final V value;

    @NotNull
    public static HoverEvent<Component> showText(@NotNull ComponentLike componentLike) {
        return showText(componentLike.asComponent());
    }

    @NotNull
    public static HoverEvent<Component> showText(@NotNull Component component) {
        return new HoverEvent<>(HoverEvent.Action.SHOW_TEXT, component);
    }

    @NotNull
    public static HoverEvent<HoverEvent.ShowItem> showItem(
            @NotNull Key key, @Range(from = 0L, to = 2147483647L) int value) {
        return showItem(key, value, Collections.emptyMap());
    }

    @NotNull
    public static HoverEvent<HoverEvent.ShowItem> showItem(
            @NotNull Keyed keyed, @Range(from = 0L, to = 2147483647L) int value) {
        return showItem(keyed, value, Collections.emptyMap());
    }

    @Deprecated
    @NotNull
    public static HoverEvent<HoverEvent.ShowItem> showItem(
            @NotNull Key key,
            @Range(from = 0L, to = 2147483647L) int value,
            @Nullable BinaryTagHolder binaryTagHolder) {
        return showItem(HoverEvent.ShowItem.showItem(key, value, binaryTagHolder));
    }

    @Deprecated
    @NotNull
    public static HoverEvent<HoverEvent.ShowItem> showItem(
            @NotNull Keyed keyed,
            @Range(from = 0L, to = 2147483647L) int value,
            @Nullable BinaryTagHolder binaryTagHolder) {
        return showItem(HoverEvent.ShowItem.showItem(keyed, value, binaryTagHolder));
    }

    @NotNull
    public static HoverEvent<HoverEvent.ShowItem> showItem(
            @NotNull Keyed keyed,
            @Range(from = 0L, to = 2147483647L) int value,
            @NotNull Map<Key, ? extends DataComponentValue> entries) {
        return showItem(HoverEvent.ShowItem.showItem(keyed, value, entries));
    }

    @NotNull
    public static HoverEvent<HoverEvent.ShowItem> showItem(@NotNull HoverEvent.ShowItem showItem2) {
        return new HoverEvent<>(HoverEvent.Action.SHOW_ITEM, showItem2);
    }

    @NotNull
    public static HoverEvent<HoverEvent.ShowEntity> showEntity(
            @NotNull Key key, @NotNull UUID uUID) {
        return showEntity(key, uUID, null);
    }

    @NotNull
    public static HoverEvent<HoverEvent.ShowEntity> showEntity(
            @NotNull Keyed keyed, @NotNull UUID uUID) {
        return showEntity(keyed, uUID, null);
    }

    @NotNull
    public static HoverEvent<HoverEvent.ShowEntity> showEntity(
            @NotNull Key key, @NotNull UUID uUID, @Nullable Component component) {
        return showEntity(HoverEvent.ShowEntity.of(key, uUID, component));
    }

    @NotNull
    public static HoverEvent<HoverEvent.ShowEntity> showEntity(
            @NotNull Keyed keyed, @NotNull UUID uUID, @Nullable Component component) {
        return showEntity(HoverEvent.ShowEntity.of(keyed, uUID, component));
    }

    @NotNull
    public static HoverEvent<HoverEvent.ShowEntity> showEntity(
            @NotNull HoverEvent.ShowEntity showEntity2) {
        return new HoverEvent<>(HoverEvent.Action.SHOW_ENTITY, showEntity2);
    }

    @Deprecated
    @NotNull
    public static HoverEvent<String> showAchievement(@NotNull String text) {
        return new HoverEvent<>(HoverEvent.Action.SHOW_ACHIEVEMENT, text);
    }

    @NotNull
    public static <V> HoverEvent<V> hoverEvent(@NotNull HoverEvent.Action<V> action, @NotNull V v) {
        return new HoverEvent<>(action, (V) v);
    }

    private HoverEvent(@NotNull HoverEvent.Action<V> action2, @NotNull V v) {
        this.action = Objects.requireNonNull(action2, "action");
        this.value = Objects.requireNonNull((V) v, "value");
    }

    @NotNull
    public HoverEvent.Action<V> action() {
        return this.action;
    }

    @NotNull
    public V value() {
        return this.value;
    }

    @NotNull
    public HoverEvent<V> value(@NotNull V v) {
        return new HoverEvent<>(this.action, (V) v);
    }

    @NotNull
    public <C> HoverEvent<V> withRenderedValue(
            @NotNull ComponentRenderer<C> componentRenderer, @NotNull C c) {
        V v = this.value;
        V v2;
        return (v2 = this.action.renderer.render(componentRenderer, c, (V) v)) != v
                ? new HoverEvent<>(this.action, (V) v2)
                : this;
    }

    @NotNull
    @Override
    public HoverEvent<V> asHoverEvent() {
        return this;
    }

    @NotNull
    @Override
    public HoverEvent<V> asHoverEvent(@NotNull UnaryOperator<V> unaryOperator) {
        return unaryOperator == UnaryOperator.identity()
                ? this
                : new HoverEvent<>(this.action, unaryOperator.apply(this.value));
    }

    @Override
    public void styleApply(Style.@NotNull Builder builder) {
        builder.hoverEvent(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            HoverEvent hoverEvent = (HoverEvent) result;
            return this.action == hoverEvent.action && this.value.equals(hoverEvent.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value2 = this.action.hashCode();
        return 31 * value2 + this.value.hashCode();
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("action", this.action),
                ExaminableProperty.of("value", this.value));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    public static final class Action<V> {

        public static final HoverEvent.Action<Component> SHOW_TEXT =
                new HoverEvent.Action<>(
                        "show_text",
                        Component.class,
                        true,
                        new HoverEvent.Action.Renderer<Component>() {

                            @NotNull
                            public <C> Component render(
                                    @NotNull ComponentRenderer<C> componentRenderer,
                                    @NotNull C c,
                                    @NotNull Component component) {
                                return componentRenderer.render(component, c);
                            }
                        });

        public static final HoverEvent.Action<HoverEvent.ShowItem> SHOW_ITEM =
                new HoverEvent.Action<>(
                        "show_item",
                        HoverEvent.ShowItem.class,
                        true,
                        new HoverEvent.Action.Renderer<HoverEvent.ShowItem>() {

                            @NotNull
                            public <C> HoverEvent.ShowItem render(
                                    @NotNull ComponentRenderer<C> componentRenderer,
                                    @NotNull C c,
                                    @NotNull HoverEvent.ShowItem showItem) {
                                return showItem;
                            }
                        });

        public static final HoverEvent.Action<HoverEvent.ShowEntity> SHOW_ENTITY =
                new HoverEvent.Action<>(
                        "show_entity",
                        HoverEvent.ShowEntity.class,
                        true,
                        new HoverEvent.Action.Renderer<HoverEvent.ShowEntity>() {

                            @NotNull
                            public <C> HoverEvent.ShowEntity render(
                                    @NotNull ComponentRenderer<C> componentRenderer,
                                    @NotNull C c,
                                    @NotNull HoverEvent.ShowEntity showEntity) {
                                return showEntity.name == null
                                        ? showEntity
                                        : showEntity.name(
                                                componentRenderer.render(showEntity.name, c));
                            }
                        });

        @Deprecated
        public static final HoverEvent.Action<String> SHOW_ACHIEVEMENT =
                new HoverEvent.Action<>(
                        "show_achievement",
                        String.class,
                        true,
                        new HoverEvent.Action.Renderer<String>() {

                            @NotNull
                            public <C> String render(
                                    @NotNull ComponentRenderer<C> componentRenderer,
                                    @NotNull C c,
                                    @NotNull String text) {
                                return text;
                            }
                        });

        public static final Index<String, HoverEvent.Action<?>> NAMES =
                Index.create(
                        result -> result.name, SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY, SHOW_ACHIEVEMENT);

        private final String name;

        private final Class<V> type;

        private final boolean readable;

        private final HoverEvent.Action.Renderer<V> renderer;

        Action(
                String name,
                Class<V> classValue,
                boolean readable,
                HoverEvent.Action.Renderer<V> renderer2) {
            this.name = name;
            this.type = classValue;
            this.readable = readable;
            this.renderer = renderer2;
        }

        @NotNull
        public Class<V> type() {
            return this.type;
        }

        public boolean readable() {
            return this.readable;
        }

        @NotNull
        @Override
        public String toString() {
            return this.name;
        }

        @FunctionalInterface
        interface Renderer<V> {

            @NotNull
            <C> V render(
                    @NotNull ComponentRenderer<C> componentRenderer, @NotNull C c, @NotNull V v);
        }
    }

    public static final class ShowEntity implements Examinable {

        private final Key type;

        private final UUID id;

        private final Component name;

        @NotNull
        public static HoverEvent.ShowEntity showEntity(@NotNull Key key, @NotNull UUID uUID) {
            return showEntity(key, uUID, null);
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        public static HoverEvent.ShowEntity of(@NotNull Key key, @NotNull UUID uUID) {
            return of(key, uUID, null);
        }

        @NotNull
        public static HoverEvent.ShowEntity showEntity(@NotNull Keyed keyed, @NotNull UUID uUID) {
            return showEntity(keyed, uUID, null);
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        public static HoverEvent.ShowEntity of(@NotNull Keyed keyed, @NotNull UUID uUID) {
            return of(keyed, uUID, null);
        }

        @NotNull
        public static HoverEvent.ShowEntity showEntity(
                @NotNull Key key, @NotNull UUID uUID, @Nullable Component component) {
            return new HoverEvent.ShowEntity(
                    Objects.requireNonNull(key, "type"),
                    Objects.requireNonNull(uUID, "id"),
                    component);
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        public static HoverEvent.ShowEntity of(
                @NotNull Key key, @NotNull UUID uUID, @Nullable Component component) {
            return new HoverEvent.ShowEntity(
                    Objects.requireNonNull(key, "type"),
                    Objects.requireNonNull(uUID, "id"),
                    component);
        }

        @NotNull
        public static HoverEvent.ShowEntity showEntity(
                @NotNull Keyed keyed, @NotNull UUID uUID, @Nullable Component component) {
            return new HoverEvent.ShowEntity(
                    Objects.requireNonNull(keyed, "type").key(),
                    Objects.requireNonNull(uUID, "id"),
                    component);
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        public static HoverEvent.ShowEntity of(
                @NotNull Keyed keyed, @NotNull UUID uUID, @Nullable Component component) {
            return new HoverEvent.ShowEntity(
                    Objects.requireNonNull(keyed, "type").key(),
                    Objects.requireNonNull(uUID, "id"),
                    component);
        }

        private ShowEntity(@NotNull Key key, @NotNull UUID id, @Nullable Component component) {
            this.type = key;
            this.id = id;
            this.name = component;
        }

        @NotNull
        public Key type() {
            return this.type;
        }

        @NotNull
        public HoverEvent.ShowEntity type(@NotNull Key key) {
            return Objects.requireNonNull(key, "type").equals(this.type)
                    ? this
                    : new HoverEvent.ShowEntity(key, this.id, this.name);
        }

        @NotNull
        public HoverEvent.ShowEntity type(@NotNull Keyed keyed) {
            return this.type(Objects.requireNonNull(keyed, "type").key());
        }

        @NotNull
        public UUID id() {
            return this.id;
        }

        @NotNull
        public HoverEvent.ShowEntity id(@NotNull UUID uUID) {
            return Objects.requireNonNull(uUID).equals(this.id)
                    ? this
                    : new HoverEvent.ShowEntity(this.type, uUID, this.name);
        }

        @Nullable
        public Component name() {
            return this.name;
        }

        @NotNull
        public HoverEvent.ShowEntity name(@Nullable Component component) {
            return Objects.equals(component, this.name)
                    ? this
                    : new HoverEvent.ShowEntity(this.type, this.id, component);
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (this == result) {
                return true;
            } else if (result != null && this.getClass() == result.getClass()) {
                HoverEvent.ShowEntity showEntity = (HoverEvent.ShowEntity) result;
                return this.type.equals(showEntity.type)
                        && this.id.equals(showEntity.id)
                        && Objects.equals(this.name, showEntity.name);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int value = this.type.hashCode();
            value = 31 * value + this.id.hashCode();
            return 31 * value + Objects.hashCode(this.name);
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("type", this.type),
                    ExaminableProperty.of("id", this.id),
                    ExaminableProperty.of("name", this.name));
        }

        @Override
        public String toString() {
            return Internals.toString(this);
        }
    }

    public static final class ShowItem implements Examinable {

        private final Key item;

        private final int count;

        @Nullable private final BinaryTagHolder nbt;

        private final Map<Key, DataComponentValue> dataComponents;

        @NotNull
        public static HoverEvent.ShowItem showItem(
                @NotNull Key key, @Range(from = 0L, to = 2147483647L) int value) {
            return showItem(key, value, Collections.emptyMap());
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        public static HoverEvent.ShowItem of(
                @NotNull Key key, @Range(from = 0L, to = 2147483647L) int value) {
            return showItem(key, value, Collections.emptyMap());
        }

        @NotNull
        public static HoverEvent.ShowItem showItem(
                @NotNull Keyed keyed, @Range(from = 0L, to = 2147483647L) int value) {
            return showItem(keyed, value, Collections.emptyMap());
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        public static HoverEvent.ShowItem of(
                @NotNull Keyed keyed, @Range(from = 0L, to = 2147483647L) int value) {
            return of(keyed, value, null);
        }

        @Deprecated
        @NotNull
        public static HoverEvent.ShowItem showItem(
                @NotNull Key key,
                @Range(from = 0L, to = 2147483647L) int value,
                @Nullable BinaryTagHolder binaryTagHolder) {
            return new HoverEvent.ShowItem(
                    Objects.requireNonNull(key, "item"),
                    value,
                    binaryTagHolder,
                    Collections.emptyMap());
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        public static HoverEvent.ShowItem of(
                @NotNull Key key,
                @Range(from = 0L, to = 2147483647L) int value,
                @Nullable BinaryTagHolder binaryTagHolder) {
            return new HoverEvent.ShowItem(
                    Objects.requireNonNull(key, "item"),
                    value,
                    binaryTagHolder,
                    Collections.emptyMap());
        }

        @Deprecated
        @NotNull
        public static HoverEvent.ShowItem showItem(
                @NotNull Keyed keyed,
                @Range(from = 0L, to = 2147483647L) int value,
                @Nullable BinaryTagHolder binaryTagHolder) {
            return new HoverEvent.ShowItem(
                    Objects.requireNonNull(keyed, "item").key(),
                    value,
                    binaryTagHolder,
                    Collections.emptyMap());
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        public static HoverEvent.ShowItem of(
                @NotNull Keyed keyed,
                @Range(from = 0L, to = 2147483647L) int value,
                @Nullable BinaryTagHolder binaryTagHolder) {
            return new HoverEvent.ShowItem(
                    Objects.requireNonNull(keyed, "item").key(),
                    value,
                    binaryTagHolder,
                    Collections.emptyMap());
        }

        @NotNull
        public static HoverEvent.ShowItem showItem(
                @NotNull Keyed keyed,
                @Range(from = 0L, to = 2147483647L) int value,
                @NotNull Map<Key, ? extends DataComponentValue> entries) {
            return new HoverEvent.ShowItem(
                    Objects.requireNonNull(keyed, "item").key(), value, null, entries);
        }

        private ShowItem(
                @NotNull Key key,
                @Range(from = 0L, to = 2147483647L) int count,
                @Nullable BinaryTagHolder binaryTagHolder,
                @NotNull Map<Key, ? extends DataComponentValue> entries) {
            this.item = key;
            this.count = count;
            this.nbt = binaryTagHolder;
            this.dataComponents = Collections.unmodifiableMap(new HashMap<>(entries));
        }

        @NotNull
        public Key item() {
            return this.item;
        }

        @NotNull
        public HoverEvent.ShowItem item(@NotNull Key key) {
            return Objects.requireNonNull(key, "item").equals(this.item)
                    ? this
                    : new HoverEvent.ShowItem(key, this.count, this.nbt, this.dataComponents);
        }

        @Range(from = 0L, to = 2147483647L)
        public int count() {
            return this.count;
        }

        @NotNull
        public HoverEvent.ShowItem count(@Range(from = 0L, to = 2147483647L) int value) {
            return value == this.count
                    ? this
                    : new HoverEvent.ShowItem(this.item, value, this.nbt, this.dataComponents);
        }

        @Deprecated
        @Nullable
        public BinaryTagHolder nbt() {
            return this.nbt;
        }

        @Deprecated
        @NotNull
        public HoverEvent.ShowItem nbt(@Nullable BinaryTagHolder binaryTagHolder) {
            return Objects.equals(binaryTagHolder, this.nbt)
                    ? this
                    : new HoverEvent.ShowItem(
                            this.item, this.count, binaryTagHolder, Collections.emptyMap());
        }

        @NotNull
        public Map<Key, DataComponentValue> dataComponents() {
            return this.dataComponents;
        }

        @NotNull
        public HoverEvent.ShowItem dataComponents(@NotNull Map<Key, DataComponentValue> entries) {
            return Objects.equals(this.dataComponents, entries)
                    ? this
                    : new HoverEvent.ShowItem(
                            this.item,
                            this.count,
                            null,
                            entries.isEmpty()
                                    ? Collections.emptyMap()
                                    : Collections.unmodifiableMap(new HashMap<>(entries)));
        }

        @NotNull
        public <V extends DataComponentValue> Map<Key, V> dataComponentsAs(
                @NotNull Class<V> classValue) {
            if (this.dataComponents.isEmpty()) {
                return Collections.emptyMap();
            }
            HashMap entries = new HashMap(this.dataComponents.size());
            for (Entry<Key, it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValue> entry :
                    this.dataComponents.entrySet()) {
                entries.put(
                        (Key) entry.getKey(),
                        DataComponentValueConverterRegistry.convert(
                                classValue,
                                (Key) entry.getKey(),
                                (DataComponentValue) entry.getValue()));
            }
            return Collections.unmodifiableMap(entries);
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (this == result) {
                return true;
            } else if (result != null && this.getClass() == result.getClass()) {
                HoverEvent.ShowItem showItem = (HoverEvent.ShowItem) result;
                return this.item.equals(showItem.item)
                        && this.count == showItem.count
                        && Objects.equals(this.nbt, showItem.nbt)
                        && Objects.equals(this.dataComponents, showItem.dataComponents);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int value = this.item.hashCode();
            value = 31 * value + Integer.hashCode(this.count);
            value = 31 * value + Objects.hashCode(this.nbt);
            return 31 * value + Objects.hashCode(this.dataComponents);
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("item", this.item),
                    ExaminableProperty.of("count", this.count),
                    ExaminableProperty.of("nbt", this.nbt),
                    ExaminableProperty.of("dataComponents", this.dataComponents));
        }

        @Override
        public String toString() {
            return Internals.toString(this);
        }
    }
}
