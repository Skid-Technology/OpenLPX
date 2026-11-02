package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface StorageNBTComponent
        extends NBTComponent<StorageNBTComponent, StorageNBTComponent.Builder>,
                ScopedComponent<StorageNBTComponent> {

    @NotNull
    Key storage();

    @Contract(pure = true)
    @NotNull
    StorageNBTComponent storage(@NotNull Key key);

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(ExaminableProperty.of("storage", this.storage())),
                NBTComponent.super.examinableProperties());
    }

    interface Builder
            extends NBTComponentBuilder<StorageNBTComponent, StorageNBTComponent.Builder> {

        @Contract("_ -> this")
        @NotNull
        StorageNBTComponent.Builder storage(@NotNull Key key);
    }
}
