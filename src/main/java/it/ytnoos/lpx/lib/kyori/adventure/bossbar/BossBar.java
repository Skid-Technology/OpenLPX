package it.ytnoos.lpx.lib.kyori.adventure.bossbar;

import it.ytnoos.lpx.lib.kyori.adventure.audience.Audience;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Set;

@NonExtendable
public interface BossBar extends Examinable {

    float MIN_PROGRESS = 0.0F;

    float MAX_PROGRESS = 1.0F;

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    float MIN_PERCENT = 0.0F;

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    float MAX_PERCENT = 1.0F;

    @NotNull
    static BossBar bossBar(
            @NotNull ComponentLike componentLike,
            float value,
            @NotNull BossBar.Color color,
            @NotNull BossBar.Overlay overlay) {
        BossBarImpl.checkProgress(value);
        return bossBar(componentLike.asComponent(), value, color, overlay);
    }

    @NotNull
    static BossBar bossBar(
            @NotNull Component component,
            float value,
            @NotNull BossBar.Color color,
            @NotNull BossBar.Overlay overlay) {
        BossBarImpl.checkProgress(value);
        return new BossBarImpl(component, value, color, overlay);
    }

    @NotNull
    static BossBar bossBar(
            @NotNull ComponentLike componentLike,
            float value,
            @NotNull BossBar.Color color,
            @NotNull BossBar.Overlay overlay,
            @NotNull Set<BossBar.Flag> values) {
        BossBarImpl.checkProgress(value);
        return bossBar(componentLike.asComponent(), value, color, overlay, values);
    }

    @NotNull
    static BossBar bossBar(
            @NotNull Component component,
            float value,
            @NotNull BossBar.Color color,
            @NotNull BossBar.Overlay overlay,
            @NotNull Set<BossBar.Flag> values) {
        BossBarImpl.checkProgress(value);
        return new BossBarImpl(component, value, color, overlay, values);
    }

    @NotNull
    Component name();

    @Contract("_ -> this")
    @NotNull
    default BossBar name(@NotNull ComponentLike componentLike) {
        return this.name(componentLike.asComponent());
    }

    @Contract("_ -> this")
    @NotNull
    BossBar name(@NotNull Component component);

    float progress();

    @Contract("_ -> this")
    @NotNull
    BossBar progress(float value);

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    default float percent() {
        return this.progress();
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract("_ -> this")
    @NotNull
    default BossBar percent(float value) {
        return this.progress(value);
    }

    @NotNull
    BossBar.Color color();

    @Contract("_ -> this")
    @NotNull
    BossBar color(@NotNull BossBar.Color color2);

    @NotNull
    BossBar.Overlay overlay();

    @Contract("_ -> this")
    @NotNull
    BossBar overlay(@NotNull BossBar.Overlay overlay2);

    @NotNull
    @UnmodifiableView
    Set<BossBar.Flag> flags();

    @Contract("_ -> this")
    @NotNull
    BossBar flags(@NotNull Set<BossBar.Flag> values);

    boolean hasFlag(@NotNull BossBar.Flag flag);

    @Contract("_ -> this")
    @NotNull
    BossBar addFlag(@NotNull BossBar.Flag flag);

    @Contract("_ -> this")
    @NotNull
    BossBar removeFlag(@NotNull BossBar.Flag flag);

    @Contract("_ -> this")
    @NotNull
    BossBar addFlags(@NotNull BossBar.Flag... flag);

    @Contract("_ -> this")
    @NotNull
    BossBar removeFlags(@NotNull BossBar.Flag... flag);

    @Contract("_ -> this")
    @NotNull
    BossBar addFlags(@NotNull Iterable<BossBar.Flag> iterable);

    @Contract("_ -> this")
    @NotNull
    BossBar removeFlags(@NotNull Iterable<BossBar.Flag> iterable);

    @Contract("_ -> this")
    @NotNull
    BossBar addListener(@NotNull BossBar.Listener listener);

    @Contract("_ -> this")
    @NotNull
    BossBar removeListener(@NotNull BossBar.Listener listener);

    @NotNull
    @UnmodifiableView
    Iterable<? extends BossBarViewer> viewers();

    @NotNull
    default BossBar addViewer(@NotNull Audience audience) {
        audience.showBossBar(this);
        return this;
    }

    @NotNull
    default BossBar removeViewer(@NotNull Audience audience) {
        audience.hideBossBar(this);
        return this;
    }

    enum Color {
        PINK("pink"),
        BLUE("blue"),
        RED("red"),
        GREEN("green"),
        YELLOW("yellow"),
        PURPLE("purple"),
        WHITE("white");

        public static final Index<String, BossBar.Color> NAMES =
                Index.create(BossBar.Color.class, result -> result.name);

        private final String name;

        Color(String name) {
            this.name = name;
        }
    }

    enum Flag {
        DARKEN_SCREEN("darken_screen"),
        PLAY_BOSS_MUSIC("play_boss_music"),
        CREATE_WORLD_FOG("create_world_fog");

        public static final Index<String, BossBar.Flag> NAMES =
                Index.create(BossBar.Flag.class, result -> result.name);

        private final String name;

        Flag(String name) {
            this.name = name;
        }
    }

    @OverrideOnly
    interface Listener {

        default void bossBarNameChanged(
                @NotNull BossBar bossBar,
                @NotNull Component component,
                @NotNull Component component2) {}

        default void bossBarProgressChanged(@NotNull BossBar bossBar, float value, float value2) {
            this.bossBarPercentChanged(bossBar, value, value2);
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        default void bossBarPercentChanged(@NotNull BossBar bossBar, float value, float value2) {}

        default void bossBarColorChanged(
                @NotNull BossBar bossBar,
                @NotNull BossBar.Color color,
                @NotNull BossBar.Color color2) {}

        default void bossBarOverlayChanged(
                @NotNull BossBar bossBar,
                @NotNull BossBar.Overlay overlay,
                @NotNull BossBar.Overlay overlay2) {}

        default void bossBarFlagsChanged(
                @NotNull BossBar bossBar,
                @NotNull Set<BossBar.Flag> values,
                @NotNull Set<BossBar.Flag> values2) {}
    }

    enum Overlay {
        PROGRESS("progress"),
        NOTCHED_6("notched_6"),
        NOTCHED_10("notched_10"),
        NOTCHED_12("notched_12"),
        NOTCHED_20("notched_20");

        public static final Index<String, BossBar.Overlay> NAMES =
                Index.create(BossBar.Overlay.class, result -> result.name);

        private final String name;

        Overlay(String name) {
            this.name = name;
        }
    }
}
