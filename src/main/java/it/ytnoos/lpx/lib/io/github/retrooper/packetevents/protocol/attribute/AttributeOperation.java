package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum AttributeOperation implements CodecNameable {
    ADDITION("add_value"),
    MULTIPLY_BASE("add_multiplied_base"),
    MULTIPLY_TOTAL("add_multiplied_total");

    public static final NbtCodec<AttributeOperation> CODEC = NbtCodecs.forEnum(values());

    private final String name;

    AttributeOperation(String name) {
        this.name = name;
    }

    @Override
    public String getCodecName() {
        return this.name;
    }
}
