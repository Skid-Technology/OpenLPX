package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

abstract class NBTComponentImpl<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>>
        extends AbstractComponent implements NBTComponent<C, B> {

    static final boolean INTERPRET_DEFAULT = false;

    final String nbtPath;

    final boolean interpret;

    @Nullable final Component separator;

    NBTComponentImpl(
            @NotNull List<Component> values,
            @NotNull Style style,
            String nbtPath,
            boolean interpret,
            @Nullable Component component) {
        super(values, style);
        this.nbtPath = nbtPath;
        this.interpret = interpret;
        this.separator = component;
    }

    @NotNull
    @Override
    public String nbtPath() {
        return this.nbtPath;
    }

    @Override
    public boolean interpret() {
        return this.interpret;
    }

    @NotNull
    public abstract B toBuilder();

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof NBTComponent)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        NBTComponent nBTComponent = (NBTComponent) result;
        return Objects.equals(this.nbtPath, nBTComponent.nbtPath())
                && this.interpret == nBTComponent.interpret()
                && Objects.equals(this.separator, nBTComponent.separator());
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        value = 31 * value + this.nbtPath.hashCode();
        value = 31 * value + Boolean.hashCode(this.interpret);
        return 31 * value + Objects.hashCode(this.separator);
    }
}
