package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.NotNull;

public interface DataComponentValue extends Examinable {

    static DataComponentValue.@NotNull Removed removed() {
        return RemovedDataComponentValueImpl.REMOVED;
    }

    interface Removed extends DataComponentValue {}

    interface TagSerializable extends DataComponentValue {

        @NotNull
        BinaryTagHolder asBinaryTag();
    }
}
