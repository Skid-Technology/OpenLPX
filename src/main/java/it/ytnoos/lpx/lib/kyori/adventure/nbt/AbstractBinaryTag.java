package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.string.StringExaminer;

import org.jetbrains.annotations.NotNull;

abstract class AbstractBinaryTag implements BinaryTag {

    @NotNull
    @Override
    public final String examinableName() {
        return this.type().toString();
    }

    @Override
    public final String toString() {
        return this.examine(StringExaminer.simpleEscaping());
    }
}
