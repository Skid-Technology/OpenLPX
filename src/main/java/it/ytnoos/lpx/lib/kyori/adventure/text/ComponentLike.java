package it.ytnoos.lpx.lib.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface ComponentLike {

    @NotNull
    static List<Component> asComponents(@NotNull List<? extends ComponentLike> values) {
        return asComponents(values, null);
    }

    @NotNull
    static List<Component> asComponents(
            @NotNull List<? extends ComponentLike> values,
            @Nullable Predicate<? super Component> predicate) {
        Objects.requireNonNull(values, "likes");
        int value;
        if ((value = values.size()) == 0) {
            return Collections.emptyList();
        }
        ArrayList<it.ytnoos.lpx.lib.kyori.adventure.text.Component> values2 = null;
        for (int index = 0; index < value; index++) {
            ComponentLike componentLike;
            if ((componentLike = (ComponentLike) values.get(index)) == null) {
                throw new NullPointerException("likes[" + index + "]");
            }
            Component component = componentLike.asComponent();
            if (predicate == null || predicate.test(component)) {
                if (values2 == null) {
                    values2 = new ArrayList(value);
                }
                values2.add(component);
            }
        }
        if (values2 == null) {
            return Collections.emptyList();
        }
        values2.trimToSize();
        return Collections.unmodifiableList(values2);
    }

    @Nullable
    static Component unbox(@Nullable ComponentLike componentLike) {
        return componentLike != null ? componentLike.asComponent() : null;
    }

    @Contract(pure = true)
    @NotNull
    Component asComponent();
}
