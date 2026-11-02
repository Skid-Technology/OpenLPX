package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface BinaryTag extends BinaryTagLike, Examinable {

    @NotNull
    BinaryTagType<? extends BinaryTag> type();

    @NotNull
    @Override
    default BinaryTag asBinaryTag() {
        return this;
    }
}
