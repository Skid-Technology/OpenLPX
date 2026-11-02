package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

final class StorageNBTComponentImpl
        extends NBTComponentImpl<StorageNBTComponent, StorageNBTComponent.Builder>
        implements StorageNBTComponent {

    private final Key storage;

    @NotNull
    static StorageNBTComponent create(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            String text,
            boolean matches,
            @Nullable ComponentLike componentLike,
            @NotNull Key key) {
        return new StorageNBTComponentImpl(
                ComponentLike.asComponents(values, IS_NOT_EMPTY),
                Objects.requireNonNull(style, "style"),
                Objects.requireNonNull(text, "nbtPath"),
                matches,
                ComponentLike.unbox(componentLike),
                Objects.requireNonNull(key, "storage"));
    }

    StorageNBTComponentImpl(
            @NotNull List<Component> values,
            @NotNull Style style,
            String text,
            boolean matches,
            @Nullable Component component,
            Key key) {
        super(values, style, text, matches, component);
        this.storage = key;
    }

    @NotNull
    public StorageNBTComponent nbtPath(@NotNull String text) {
        return Objects.equals(this.nbtPath, text)
                ? this
                : create(
                        this.children,
                        this.style,
                        text,
                        this.interpret,
                        this.separator,
                        this.storage);
    }

    @NotNull
    public StorageNBTComponent interpret(boolean matches) {
        return this.interpret == matches
                ? this
                : create(
                        this.children,
                        this.style,
                        this.nbtPath,
                        matches,
                        this.separator,
                        this.storage);
    }

    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }

    @NotNull
    public StorageNBTComponent separator(@Nullable ComponentLike componentLike) {
        return create(
                this.children,
                this.style,
                this.nbtPath,
                this.interpret,
                componentLike,
                this.storage);
    }

    @NotNull
    @Override
    public Key storage() {
        return this.storage;
    }

    @NotNull
    @Override
    public StorageNBTComponent storage(@NotNull Key key) {
        return Objects.equals(this.storage, key)
                ? this
                : create(
                        this.children,
                        this.style,
                        this.nbtPath,
                        this.interpret,
                        this.separator,
                        key);
    }

    @NotNull
    public StorageNBTComponent children(@NotNull List<? extends ComponentLike> values) {
        return create(
                values, this.style, this.nbtPath, this.interpret, this.separator, this.storage);
    }

    @NotNull
    public StorageNBTComponent style(@NotNull Style style2) {
        return create(
                this.children, style2, this.nbtPath, this.interpret, this.separator, this.storage);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof StorageNBTComponent)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        StorageNBTComponentImpl storageNBTComponentImpl = (StorageNBTComponentImpl) result;
        return Objects.equals(this.storage, storageNBTComponentImpl.storage());
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        return 1027333177 * value + this.storage.hashCode();
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    public StorageNBTComponent.@NotNull Builder toBuilder() {
        return new StorageNBTComponentImpl.BuilderImpl(this);
    }

    static class BuilderImpl
            extends AbstractNBTComponentBuilder<StorageNBTComponent, StorageNBTComponent.Builder>
            implements StorageNBTComponent.Builder {

        @Nullable private Key storage;

        BuilderImpl() {}

        BuilderImpl(@NotNull StorageNBTComponent storageNBTComponent) {
            super(storageNBTComponent);
            this.storage = storageNBTComponent.storage();
        }

        @Override
        public StorageNBTComponent.@NotNull Builder storage(@NotNull Key key) {
            this.storage = Objects.requireNonNull(key, "storage");
            return this;
        }

        @NotNull
        public StorageNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            } else if (this.storage == null) {
                throw new IllegalStateException("storage must be set");
            } else {
                return StorageNBTComponentImpl.create(
                        this.children,
                        this.buildStyle(),
                        this.nbtPath,
                        this.interpret,
                        this.separator,
                        this.storage);
            }
        }
    }
}
