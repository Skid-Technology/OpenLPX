package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class JoinConfigurationImpl implements JoinConfiguration {

    static final Function<ComponentLike, Component> DEFAULT_CONVERTOR = ComponentLike::asComponent;

    static final Predicate<ComponentLike> DEFAULT_PREDICATE = result -> true;

    static final JoinConfigurationImpl NULL = new JoinConfigurationImpl();

    static final JoinConfiguration STANDARD_NEW_LINES =
            JoinConfiguration.separator(Component.newline());

    static final JoinConfiguration STANDARD_SPACES = JoinConfiguration.separator(Component.space());

    static final JoinConfiguration STANDARD_COMMA_SEPARATED =
            JoinConfiguration.separator(Component.text(","));

    static final JoinConfiguration STANDARD_COMMA_SPACE_SEPARATED =
            JoinConfiguration.separator(Component.text(", "));

    static final JoinConfiguration STANDARD_ARRAY_LIKE =
            JoinConfiguration.builder()
                    .separator(Component.text(", "))
                    .prefix(Component.text("["))
                    .suffix(Component.text("]"))
                    .build();

    private final Component prefix;

    private final Component suffix;

    private final Component separator;

    private final Component lastSeparator;

    private final Component lastSeparatorIfSerial;

    private final Function<ComponentLike, Component> convertor;

    private final Predicate<ComponentLike> predicate;

    private final Style rootStyle;

    private JoinConfigurationImpl() {
        this.prefix = null;
        this.suffix = null;
        this.separator = null;
        this.lastSeparator = null;
        this.lastSeparatorIfSerial = null;
        this.convertor = DEFAULT_CONVERTOR;
        this.predicate = DEFAULT_PREDICATE;
        this.rootStyle = Style.empty();
    }

    private JoinConfigurationImpl(@NotNull JoinConfigurationImpl.BuilderImpl builderImpl) {
        this.prefix = ComponentLike.unbox(builderImpl.prefix);
        this.suffix = ComponentLike.unbox(builderImpl.suffix);
        this.separator = ComponentLike.unbox(builderImpl.separator);
        this.lastSeparator = ComponentLike.unbox(builderImpl.lastSeparator);
        this.lastSeparatorIfSerial = ComponentLike.unbox(builderImpl.lastSeparatorIfSerial);
        this.convertor = builderImpl.convertor;
        this.predicate = builderImpl.predicate;
        this.rootStyle = builderImpl.rootStyle;
    }

    @Nullable
    @Override
    public Component prefix() {
        return this.prefix;
    }

    @Nullable
    @Override
    public Component suffix() {
        return this.suffix;
    }

    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }

    @Nullable
    @Override
    public Component lastSeparator() {
        return this.lastSeparator;
    }

    @Nullable
    @Override
    public Component lastSeparatorIfSerial() {
        return this.lastSeparatorIfSerial;
    }

    @NotNull
    @Override
    public Function<ComponentLike, Component> convertor() {
        return this.convertor;
    }

    @NotNull
    @Override
    public Predicate<ComponentLike> predicate() {
        return this.predicate;
    }

    @NotNull
    @Override
    public Style parentStyle() {
        return this.rootStyle;
    }

    public JoinConfiguration.@NotNull Builder toBuilder() {
        return new JoinConfigurationImpl.BuilderImpl(this);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("prefix", this.prefix),
                ExaminableProperty.of("suffix", this.suffix),
                ExaminableProperty.of("separator", this.separator),
                ExaminableProperty.of("lastSeparator", this.lastSeparator),
                ExaminableProperty.of("lastSeparatorIfSerial", this.lastSeparatorIfSerial),
                ExaminableProperty.of("convertor", this.convertor),
                ExaminableProperty.of("predicate", this.predicate),
                ExaminableProperty.of("rootStyle", this.rootStyle));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Contract(pure = true)
    @NotNull
    static Component join(
            @NotNull JoinConfiguration joinConfiguration,
            @NotNull Iterable<? extends ComponentLike> iterable) {
        Objects.requireNonNull(joinConfiguration, "config");
        Objects.requireNonNull(iterable, "components");
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike> iterator2;
        if (!(iterator2 = iterable.iterator()).hasNext()) {
            return singleElementJoin(joinConfiguration, null);
        }
        ComponentLike componentLike =
                Objects.requireNonNull(
                        (ComponentLike) iterator2.next(),
                        "Null elements in \"components\" are not allowed");
        int value = 0;
        if (!iterator2.hasNext()) {
            return singleElementJoin(joinConfiguration, componentLike);
        }
        Component component = joinConfiguration.prefix();
        Component component2 = joinConfiguration.suffix();
        Function<
                        it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike,
                        it.ytnoos.lpx.lib.kyori.adventure.text.Component>
                function = joinConfiguration.convertor();
        Predicate<it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike> predicate2 =
                joinConfiguration.predicate();
        Style style2;
        boolean matches = (style2 = joinConfiguration.parentStyle()) != Style.empty();
        Component component3;
        boolean matches2 = (component3 = joinConfiguration.separator()) != null;
        TextComponent.Builder builder = matches ? Component.text().style(style2) : Component.text();
        if (component != null) {
            builder.append(component);
        }
        while (componentLike != null) {
            if (!predicate2.test(componentLike)) {
                if (!iterator2.hasNext()) {
                    break;
                }
                componentLike = (ComponentLike) iterator2.next();
            } else {
                builder.append(
                        Objects.requireNonNull(
                                (Component) function.apply(componentLike),
                                "Null output from \"convertor\" is not allowed"));
                value++;
                if (!iterator2.hasNext()) {
                    componentLike = null;
                } else {
                    componentLike =
                            Objects.requireNonNull(
                                    (ComponentLike) iterator2.next(),
                                    "Null elements in \"components\" are not allowed");
                    if (iterator2.hasNext()) {
                        if (matches2) {
                            builder.append(component3);
                        }
                    } else {
                        Component component4 = null;
                        if (value > 1) {
                            component4 = joinConfiguration.lastSeparatorIfSerial();
                        }
                        if (component4 == null) {
                            component4 = joinConfiguration.lastSeparator();
                        }
                        if (component4 == null) {
                            component4 = joinConfiguration.separator();
                        }
                        if (component4 != null) {
                            builder.append(component4);
                        }
                    }
                }
            }
        }
        if (component2 != null) {
            builder.append(component2);
        }
        return builder.build();
    }

    @NotNull
    static Component singleElementJoin(
            @NotNull JoinConfiguration joinConfiguration, @Nullable ComponentLike componentLike) {
        Component component = joinConfiguration.prefix();
        Component component2 = joinConfiguration.suffix();
        Function<
                        it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike,
                        it.ytnoos.lpx.lib.kyori.adventure.text.Component>
                function = joinConfiguration.convertor();
        Predicate<it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike> predicate2 =
                joinConfiguration.predicate();
        Style style2;
        boolean matches = (style2 = joinConfiguration.parentStyle()) != Style.empty();
        if (component == null && component2 == null) {
            Component component3;
            if (componentLike != null && predicate2.test(componentLike)) {
                component3 = (Component) function.apply(componentLike);
            } else {
                component3 = Component.empty();
            }
            return matches ? Component.text().style(style2).append(component3).build() : component3;
        } else {
            TextComponent.Builder builder = Component.text();
            if (component != null) {
                builder.append(component);
            }
            if (componentLike != null && predicate2.test(componentLike)) {
                builder.append((Component) function.apply(componentLike));
            }
            if (component2 != null) {
                builder.append(component2);
            }
            return matches
                    ? Component.text().style(style2).append(builder).build()
                    : builder.build();
        }
    }

    static final class BuilderImpl implements JoinConfiguration.Builder {

        private ComponentLike prefix;

        private ComponentLike suffix;

        private ComponentLike separator;

        private ComponentLike lastSeparator;

        private ComponentLike lastSeparatorIfSerial;

        private Function<ComponentLike, Component> convertor;

        private Predicate<ComponentLike> predicate;

        private Style rootStyle;

        BuilderImpl() {
            this(JoinConfigurationImpl.NULL);
        }

        private BuilderImpl(@NotNull JoinConfigurationImpl joinConfigurationImpl) {
            this.separator = joinConfigurationImpl.separator;
            this.lastSeparator = joinConfigurationImpl.lastSeparator;
            this.prefix = joinConfigurationImpl.prefix;
            this.suffix = joinConfigurationImpl.suffix;
            this.convertor = joinConfigurationImpl.convertor;
            this.lastSeparatorIfSerial = joinConfigurationImpl.lastSeparatorIfSerial;
            this.predicate = joinConfigurationImpl.predicate;
            this.rootStyle = joinConfigurationImpl.rootStyle;
        }

        @NotNull
        @Override
        public JoinConfiguration.Builder prefix(@Nullable ComponentLike componentLike) {
            this.prefix = componentLike;
            return this;
        }

        @NotNull
        @Override
        public JoinConfiguration.Builder suffix(@Nullable ComponentLike componentLike) {
            this.suffix = componentLike;
            return this;
        }

        @NotNull
        @Override
        public JoinConfiguration.Builder separator(@Nullable ComponentLike componentLike) {
            this.separator = componentLike;
            return this;
        }

        @NotNull
        @Override
        public JoinConfiguration.Builder lastSeparator(@Nullable ComponentLike componentLike) {
            this.lastSeparator = componentLike;
            return this;
        }

        @NotNull
        @Override
        public JoinConfiguration.Builder lastSeparatorIfSerial(
                @Nullable ComponentLike componentLike) {
            this.lastSeparatorIfSerial = componentLike;
            return this;
        }

        @NotNull
        @Override
        public JoinConfiguration.Builder convertor(
                @NotNull Function<ComponentLike, Component> function) {
            this.convertor = Objects.requireNonNull(function, "convertor");
            return this;
        }

        @NotNull
        @Override
        public JoinConfiguration.Builder predicate(@NotNull Predicate<ComponentLike> predicate2) {
            this.predicate = Objects.requireNonNull(predicate2, "predicate");
            return this;
        }

        @NotNull
        @Override
        public JoinConfiguration.Builder parentStyle(@NotNull Style style) {
            this.rootStyle = Objects.requireNonNull(style, "rootStyle");
            return this;
        }

        @NotNull
        public JoinConfiguration build() {
            return new JoinConfigurationImpl(this);
        }
    }
}
