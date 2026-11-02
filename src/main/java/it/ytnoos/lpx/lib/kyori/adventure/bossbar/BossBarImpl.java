package it.ytnoos.lpx.lib.kyori.adventure.bossbar;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.util.Services;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Stream;

final class BossBarImpl extends HackyBossBarPlatformBridge implements BossBar {

    private final List<BossBar.Listener> listeners = new CopyOnWriteArrayList<>();

    private Component name;

    private float progress;

    private BossBar.Color color;

    private BossBar.Overlay overlay;

    private final Set<BossBar.Flag> flags = EnumSet.noneOf(BossBar.Flag.class);

    @Nullable BossBarImplementation implementation;

    BossBarImpl(
            @NotNull Component component,
            float progress,
            @NotNull BossBar.Color color2,
            @NotNull BossBar.Overlay overlay2) {
        this.name = Objects.requireNonNull(component, "name");
        this.progress = progress;
        this.color = Objects.requireNonNull(color2, "color");
        this.overlay = Objects.requireNonNull(overlay2, "overlay");
    }

    BossBarImpl(
            @NotNull Component component,
            float value,
            @NotNull BossBar.Color color,
            @NotNull BossBar.Overlay overlay,
            @NotNull Set<BossBar.Flag> values) {
        this(component, value, color, overlay);
        this.flags.addAll(values);
    }

    @NotNull
    @Override
    public Component name() {
        return this.name;
    }

    @NotNull
    @Override
    public BossBar name(@NotNull Component component) {
        Objects.requireNonNull(component, "name");
        Component component2 = this.name;
        this.name = component;
        this.forEachListener(result -> result.bossBarNameChanged(this, component2, component));
        return this;
    }

    @Override
    public float progress() {
        return this.progress;
    }

    @NotNull
    @Override
    public BossBar progress(float value) {
        checkProgress(value);
        float value2 = this.progress;
        if (value != value2) {
            this.progress = value;
            this.forEachListener(result -> result.bossBarProgressChanged(this, value2, value));
        }
        return this;
    }

    static void checkProgress(float value) {
        if (value < 0.0F || value > 1.0F) {
            throw new IllegalArgumentException(
                    "progress must be between 0.0 and 1.0, was " + value);
        }
    }

    @NotNull
    @Override
    public BossBar.Color color() {
        return this.color;
    }

    @NotNull
    @Override
    public BossBar color(@NotNull BossBar.Color color2) {
        Objects.requireNonNull(color2, "color");
        BossBar.Color color3 = this.color;
        if (color2 != color3) {
            this.color = color2;
            this.forEachListener(result -> result.bossBarColorChanged(this, color3, color2));
        }
        return this;
    }

    @NotNull
    @Override
    public BossBar.Overlay overlay() {
        return this.overlay;
    }

    @NotNull
    @Override
    public BossBar overlay(@NotNull BossBar.Overlay overlay2) {
        Objects.requireNonNull(overlay2, "overlay");
        BossBar.Overlay overlay3 = this.overlay;
        if (overlay2 != overlay3) {
            this.overlay = overlay2;
            this.forEachListener(result -> result.bossBarOverlayChanged(this, overlay3, overlay2));
        }
        return this;
    }

    @NotNull
    @Override
    public Set<BossBar.Flag> flags() {
        return Collections.unmodifiableSet(this.flags);
    }

    @NotNull
    @Override
    public BossBar flags(@NotNull Set<BossBar.Flag> values) {
        if (values.isEmpty() && !this.flags.isEmpty()) {
            EnumSet values2 = EnumSet.copyOf(this.flags);
            this.flags.clear();
            this.forEachListener(
                    result -> result.bossBarFlagsChanged(this, Collections.emptySet(), values2));
        } else if (!this.flags.equals(values)) {
            EnumSet values3 = EnumSet.copyOf(this.flags);
            this.flags.clear();
            this.flags.addAll(values);
            EnumSet values4;
            EnumSet values5 = values4 = EnumSet.copyOf(values);
            values5.removeIf(values3::contains);
            EnumSet values6;
            values5 = values6 = EnumSet.copyOf((Collection) values3);
            Set<it.ytnoos.lpx.lib.kyori.adventure.bossbar.BossBar.Flag> values7 = this.flags;
            values5.removeIf(values7::contains);
            EnumSet values8 = values6;
            EnumSet values9 = values4;
            this.forEachListener(result2 -> result2.bossBarFlagsChanged(this, values9, values8));
        }
        return this;
    }

    @Override
    public boolean hasFlag(@NotNull BossBar.Flag flag) {
        return this.flags.contains(flag);
    }

    @NotNull
    @Override
    public BossBar addFlag(@NotNull BossBar.Flag flag) {
        return this.editFlags(flag, Set::add, BossBarImpl::onFlagsAdded);
    }

    @NotNull
    @Override
    public BossBar removeFlag(@NotNull BossBar.Flag flag) {
        return this.editFlags(flag, Set::remove, BossBarImpl::onFlagsRemoved);
    }

    @NotNull
    private BossBar editFlags(
            @NotNull BossBar.Flag flag,
            @NotNull BiPredicate<Set<BossBar.Flag>, BossBar.Flag> biPredicate,
            BiConsumer<BossBarImpl, Set<BossBar.Flag>> biConsumer) {
        if (biPredicate.test(this.flags, flag)) {
            biConsumer.accept(this, Collections.singleton(flag));
        }
        return this;
    }

    @NotNull
    @Override
    public BossBar addFlags(@NotNull BossBar.Flag... flag) {
        return this.editFlags(flag, Set::add, BossBarImpl::onFlagsAdded);
    }

    @NotNull
    @Override
    public BossBar removeFlags(@NotNull BossBar.Flag... flag) {
        return this.editFlags(flag, Set::remove, BossBarImpl::onFlagsRemoved);
    }

    @NotNull
    private BossBar editFlags(
            BossBar.Flag[] flags2,
            BiPredicate<Set<BossBar.Flag>, BossBar.Flag> biPredicate,
            BiConsumer<BossBarImpl, Set<BossBar.Flag>> biConsumer) {
        if (flags2.length == 0) {
            return this;
        }
        EnumSet values = null;
        int value = 0;
        for (int value2 = flags2.length; value < value2; value++) {
            if (biPredicate.test(this.flags, flags2[value])) {
                if (values == null) {
                    values = EnumSet.noneOf(BossBar.Flag.class);
                }
                values.add(flags2[value]);
            }
        }
        if (values != null) {
            biConsumer.accept(this, values);
        }
        return this;
    }

    @NotNull
    @Override
    public BossBar addFlags(@NotNull Iterable<BossBar.Flag> iterable) {
        return this.editFlags(iterable, Set::add, BossBarImpl::onFlagsAdded);
    }

    @NotNull
    @Override
    public BossBar removeFlags(@NotNull Iterable<BossBar.Flag> iterable) {
        return this.editFlags(iterable, Set::remove, BossBarImpl::onFlagsRemoved);
    }

    @NotNull
    private BossBar editFlags(
            Iterable<BossBar.Flag> iterable,
            BiPredicate<Set<BossBar.Flag>, BossBar.Flag> biPredicate,
            BiConsumer<BossBarImpl, Set<BossBar.Flag>> biConsumer) {
        EnumSet values = null;
        for (BossBar.Flag flag : iterable) {
            if (biPredicate.test(this.flags, flag)) {
                if (values == null) {
                    values = EnumSet.noneOf(BossBar.Flag.class);
                }
                values.add(flag);
            }
        }
        if (values != null) {
            biConsumer.accept(this, values);
        }
        return this;
    }

    @NotNull
    @Override
    public BossBar addListener(@NotNull BossBar.Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    @NotNull
    @Override
    public BossBar removeListener(@NotNull BossBar.Listener listener) {
        this.listeners.remove(listener);
        return this;
    }

    @NotNull
    @Override
    public Iterable<? extends BossBarViewer> viewers() {
        return this.implementation != null
                ? this.implementation.viewers()
                : Collections.emptyList();
    }

    private void forEachListener(@NotNull Consumer<BossBar.Listener> consumer) {
        for (BossBar.Listener listener : this.listeners) {
            consumer.accept(listener);
        }
    }

    private static void onFlagsAdded(BossBarImpl bossBarImpl, Set<BossBar.Flag> values) {
        bossBarImpl.forEachListener(
                result ->
                        result.bossBarFlagsChanged(
                                bossBarImpl, ((Set) values), Collections.emptySet()));
    }

    private static void onFlagsRemoved(BossBarImpl bossBarImpl, Set<BossBar.Flag> values) {
        bossBarImpl.forEachListener(
                result ->
                        result.bossBarFlagsChanged(
                                bossBarImpl, Collections.emptySet(), ((Set) values)));
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("name", this.name),
                ExaminableProperty.of("progress", this.progress),
                ExaminableProperty.of("color", this.color),
                ExaminableProperty.of("overlay", this.overlay),
                ExaminableProperty.of("flags", this.flags));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Internal
    static final class ImplementationAccessor {

        private static final Optional<BossBarImplementation.Provider> SERVICE =
                Services.service(BossBarImplementation.Provider.class);

        private ImplementationAccessor() {}

        @NotNull
        static <I extends BossBarImplementation> I get(
                @NotNull BossBar bossBar, @NotNull Class<I> classValue) {
            BossBarImplementation bossBarImplementation = ((BossBarImpl) bossBar).implementation;
            if (((BossBarImpl) bossBar).implementation == null) {
                bossBarImplementation = SERVICE.get().create(bossBar);
                ((BossBarImpl) bossBar).implementation = bossBarImplementation;
            }
            return (I) classValue.cast(bossBarImplementation);
        }
    }
}
