package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.legacy.LegacyComponentSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class LegacyComponent {

    private static final LegacyComponent EMPTY = new LegacyComponent(Component.empty(), "");

    private final Component component;

    private final String legacy;

    public LegacyComponent(Component component) {
        this(component, LegacyComponentSerializer.legacySection().serialize(component));
    }

    public LegacyComponent(String text) {
        this(LegacyComponentSerializer.legacySection().deserialize(text), text);
    }

    private LegacyComponent(Component component2, String legacy) {
        this.component = component2;
        this.legacy = legacy;
    }

    public static LegacyComponent wrapOrEmpty(@Nullable Component component) {
        return component != null && component != Component.empty()
                ? new LegacyComponent(component)
                : EMPTY;
    }

    public static LegacyComponent empty() {
        return EMPTY;
    }

    public Component getComponent() {
        return this.component;
    }

    public String getLegacy() {
        return this.legacy;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            LegacyComponent legacyComponent = (LegacyComponent) result;
            return !this.component.equals(legacyComponent.component)
                    ? false
                    : this.legacy.equals(legacyComponent.legacy);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.component, this.legacy);
    }
}
