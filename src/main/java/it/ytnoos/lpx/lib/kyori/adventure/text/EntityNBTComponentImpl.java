package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

final class EntityNBTComponentImpl
        extends NBTComponentImpl<EntityNBTComponent, EntityNBTComponent.Builder>
        implements EntityNBTComponent {

    private final String selector;

    static EntityNBTComponent create(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            String text,
            boolean matches,
            @Nullable ComponentLike componentLike,
            String text2) {
        return new EntityNBTComponentImpl(
                ComponentLike.asComponents(values, IS_NOT_EMPTY),
                Objects.requireNonNull(style, "style"),
                Objects.requireNonNull(text, "nbtPath"),
                matches,
                ComponentLike.unbox(componentLike),
                Objects.requireNonNull(text2, "selector"));
    }

    EntityNBTComponentImpl(
            @NotNull List<Component> values,
            @NotNull Style style,
            String text,
            boolean matches,
            @Nullable Component component,
            String selector) {
        super(values, style, text, matches, component);
        this.selector = selector;
    }

    @NotNull
    public EntityNBTComponent nbtPath(@NotNull String text) {
        return Objects.equals(this.nbtPath, text)
                ? this
                : create(
                        this.children,
                        this.style,
                        text,
                        this.interpret,
                        this.separator,
                        this.selector);
    }

    @NotNull
    public EntityNBTComponent interpret(boolean matches) {
        return this.interpret == matches
                ? this
                : create(
                        this.children,
                        this.style,
                        this.nbtPath,
                        matches,
                        this.separator,
                        this.selector);
    }

    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }

    @NotNull
    public EntityNBTComponent separator(@Nullable ComponentLike componentLike) {
        return create(
                this.children,
                this.style,
                this.nbtPath,
                this.interpret,
                componentLike,
                this.selector);
    }

    @NotNull
    @Override
    public String selector() {
        return this.selector;
    }

    @NotNull
    @Override
    public EntityNBTComponent selector(@NotNull String text) {
        return Objects.equals(this.selector, text)
                ? this
                : create(
                        this.children,
                        this.style,
                        this.nbtPath,
                        this.interpret,
                        this.separator,
                        text);
    }

    @NotNull
    public EntityNBTComponent children(@NotNull List<? extends ComponentLike> values) {
        return create(
                values, this.style, this.nbtPath, this.interpret, this.separator, this.selector);
    }

    @NotNull
    public EntityNBTComponent style(@NotNull Style style2) {
        return create(
                this.children, style2, this.nbtPath, this.interpret, this.separator, this.selector);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof EntityNBTComponent)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        EntityNBTComponentImpl entityNBTComponentImpl = (EntityNBTComponentImpl) result;
        return Objects.equals(this.selector, entityNBTComponentImpl.selector());
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        return -802279937 * value + this.selector.hashCode();
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    public EntityNBTComponent.@NotNull Builder toBuilder() {
        return new EntityNBTComponentImpl.BuilderImpl(this);
    }

    static final class BuilderImpl
            extends AbstractNBTComponentBuilder<EntityNBTComponent, EntityNBTComponent.Builder>
            implements EntityNBTComponent.Builder {

        @Nullable private String selector;

        BuilderImpl() {}

        BuilderImpl(@NotNull EntityNBTComponent entityNBTComponent) {
            super(entityNBTComponent);
            this.selector = entityNBTComponent.selector();
        }

        @Override
        public EntityNBTComponent.@NotNull Builder selector(@NotNull String text) {
            this.selector = Objects.requireNonNull(text, "selector");
            return this;
        }

        @NotNull
        public EntityNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            } else if (this.selector == null) {
                throw new IllegalStateException("selector must be set");
            } else {
                return EntityNBTComponentImpl.create(
                        this.children,
                        this.buildStyle(),
                        this.nbtPath,
                        this.interpret,
                        this.separator,
                        this.selector);
            }
        }
    }
}
