package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import java.util.Collections;

final class ListBinaryTag0 {

    private ListBinaryTag0() {}

    static BinaryTag unbox(CompoundBinaryTag compoundBinaryTag) {
        BinaryTag binaryTag;
        return compoundBinaryTag.size() == 1 && (binaryTag = compoundBinaryTag.get("")) != null
                ? binaryTag
                : compoundBinaryTag;
    }

    static CompoundBinaryTag box(BinaryTag binaryTag) {
        return needsBox(binaryTag)
                ? new CompoundBinaryTagImpl(Collections.singletonMap("", binaryTag))
                : (CompoundBinaryTag) binaryTag;
    }

    private static boolean needsBox(BinaryTag binaryTag) {
        CompoundBinaryTag compoundBinaryTag;
        return !(binaryTag instanceof CompoundBinaryTag)
                ? true
                : (compoundBinaryTag = (CompoundBinaryTag) binaryTag).size() == 1
                        && compoundBinaryTag.get("") != null;
    }
}
