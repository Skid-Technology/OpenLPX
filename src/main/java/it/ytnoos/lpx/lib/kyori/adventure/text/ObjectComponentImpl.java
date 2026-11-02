package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.ObjectContents;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

final class ObjectComponentImpl extends AbstractComponent implements ObjectComponent {

    private final ObjectContents contents;

    private ObjectComponentImpl(
            @NotNull List<Component> values,
            @NotNull Style style,
            @NotNull ObjectContents objectContents) {
        super(values, style);
        this.contents = objectContents;
    }

    @NotNull
    @Override
    public ObjectContents contents() {
        return this.contents;
    }

    @NotNull
    @Override
    public ObjectComponent contents(@NotNull ObjectContents objectContents) {
        return create(this.children, this.style, objectContents);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ObjectComponent)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        ObjectComponentImpl objectComponentImpl = (ObjectComponentImpl) result;
        return Objects.equals(this.contents, objectComponentImpl.contents());
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        return 830103491 * value + this.contents.hashCode();
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @NotNull
    public ObjectComponent.Builder toBuilder() {
        return new ObjectComponentImpl.BuilderImpl(this);
    }

    @NotNull
    static ObjectComponentImpl create(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            @NotNull ObjectContents objectContents) {
        return new ObjectComponentImpl(
                ComponentLike.asComponents(values, IS_NOT_EMPTY),
                Objects.requireNonNull(style, "style"),
                Objects.requireNonNull(objectContents, "contents"));
    }

    @NotNull
    public ObjectComponent children(@NotNull List<? extends ComponentLike> values) {
        return create(values, this.style, this.contents);
    }

    @NotNull
    public ObjectComponent style(@NotNull Style style2) {
        return create(this.children, style2, this.contents);
    }

    static final class BuilderImpl
            extends AbstractComponentBuilder<ObjectComponent, ObjectComponent.Builder>
            implements ObjectComponent.Builder {

        private ObjectContents objectContents;

        BuilderImpl() {}

        BuilderImpl(@NotNull ObjectComponent objectComponent) {
            super(objectComponent);
            this.objectContents = objectComponent.contents();
        }

        @NotNull
        @Override
        public ObjectComponent.Builder contents(@NotNull ObjectContents objectContents2) {
            this.objectContents = Objects.requireNonNull(objectContents2, "contents");
            return this;
        }

        @NotNull
        public ObjectComponent build() {
            if (this.objectContents == null) {
                throw new IllegalStateException("contents must be set");
            } else {
                return ObjectComponentImpl.create(
                        this.children, this.buildStyle(), this.objectContents);
            }
        }
    }
}
