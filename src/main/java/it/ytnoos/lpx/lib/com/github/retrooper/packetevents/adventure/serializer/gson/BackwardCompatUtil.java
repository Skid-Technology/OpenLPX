package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.compat.ReflectionNames;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.adventure.text.BuildableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.ObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.util.Codec;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.UUID;

public final class BackwardCompatUtil {

    public static final boolean IS_4_10_0_OR_NEWER;

    public static final boolean IS_4_13_0_OR_NEWER;

    public static final boolean IS_4_15_0_OR_NEWER;

    public static final boolean IS_4_17_0_OR_NEWER;

    public static final boolean IS_4_18_0_OR_NEWER;

    public static final boolean IS_4_22_0_OR_NEWER;

    public static final boolean IS_4_23_0_OR_NEWER;

    public static final boolean IS_4_25_0_OR_NEWER;

    public static final boolean IS_4_26_0_OR_NEWER;

    private static final Method COMPONENT_BUILDER_BUILD;

    private BackwardCompatUtil() {}

    public static HoverEvent.ShowItem createShowItem(
            @NotNull Key key,
            @Range(from = 0L, to = 2147483647L) int value,
            @Nullable BinaryTagHolder binaryTagHolder) {
        try {
            return HoverEvent.ShowItem.showItem(key, value, binaryTagHolder);
        } catch (NoSuchMethodError noSuchMethodError) {
            return HoverEvent.ShowItem.of(key, value, binaryTagHolder);
        }
    }

    public static HoverEvent.ShowEntity createShowEntity(
            @NotNull Key key, @NotNull UUID uUID, @Nullable Component component) {
        try {
            return HoverEvent.ShowEntity.showEntity(key, uUID, component);
        } catch (NoSuchMethodError noSuchMethodError) {
            return HoverEvent.ShowEntity.of(key, uUID, component);
        }
    }

    @NotNull
    public static <D, E, DX extends Throwable, EX extends Throwable>
            Codec<D, E, DX, EX> createCodec(
                    @NotNull Codec.Decoder<D, E, DX> decoder,
                    @NotNull Codec.Encoder<D, E, EX> encoder) {
        try {
            return Codec.codec(decoder, encoder);
        } catch (NoSuchMethodError noSuchMethodError) {
            return Codec.of(decoder, encoder);
        }
    }

    public static ComponentBuilder<?, ?> toBuilder(Component component) {
        return IS_4_26_0_OR_NEWER
                ? component.toBuilder()
                : ((BuildableComponent) component).toBuilder();
    }

    public static Component build(ComponentBuilder<?, ?> componentBuilder) {
        try {
            return (Component) COMPONENT_BUILDER_BUILD.invoke(componentBuilder);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    static {
        byte value = 0;
        try {
            BinaryTagHolder.binaryTagHolder("");
            value = 1;
        } catch (Throwable throwable) {
        }
        IS_4_10_0_OR_NEWER = (value != 0);
        boolean matches = false;
        try {
            Component.translatable().fallback("");
            matches = true;
        } catch (Throwable throwable2) {
        }
        IS_4_13_0_OR_NEWER = matches;
        boolean matches2 = false;
        try {
            Component.translatable().arguments(Component.empty());
            matches2 = true;
        } catch (Throwable throwable3) {
        }
        IS_4_15_0_OR_NEWER = matches2;
        boolean matches3 = false;
        try {
            HoverEvent.ShowItem.showItem(Key.key("air"), 1, Collections.emptyMap());
            matches3 = true;
        } catch (Throwable throwable4) {
        }
        IS_4_17_0_OR_NEWER = matches3;
        boolean matches4 = false;
        try {
            Style.empty().shadowColor();
            matches4 = true;
        } catch (Throwable throwable5) {
        }
        IS_4_18_0_OR_NEWER = matches4;
        boolean matches5 = false;
        try {
            ClickEvent.custom(Key.key("test"), BinaryTagHolder.binaryTagHolder("{test:true}"));
            matches5 = true;
        } catch (Throwable throwable6) {
        }
        IS_4_23_0_OR_NEWER = matches5;
        boolean matches6 = matches5;
        try {
            ClickEvent.custom(Key.key("test"), "{test:true}");
            matches6 = true;
        } catch (Throwable throwable7) {
        }
        IS_4_22_0_OR_NEWER = matches6;
        boolean matches7 = false;
        try {
            ObjectContents.playerHead();
            matches7 = true;
        } catch (Throwable throwable8) {
        }
        IS_4_25_0_OR_NEWER = matches7;
        boolean matches8 = false;
        try {
            Component.text(42).toBuilder();
            matches8 = true;
        } catch (Throwable throwable9) {
        }
        IS_4_26_0_OR_NEWER = matches8;
        try {
            Class[] classs = new Class[0];
            COMPONENT_BUILDER_BUILD =
                    ComponentBuilder.class.getMethod(
                            ReflectionNames.methodName("build", ComponentBuilder.class, classs),
                            classs);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public interface ShowAchievementToComponent {

        @NotNull
        Component convert(@NotNull String text);
    }
}
