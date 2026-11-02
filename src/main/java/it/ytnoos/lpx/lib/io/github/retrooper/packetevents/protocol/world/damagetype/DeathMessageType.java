package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum DeathMessageType implements CodecNameable {
    DEFAULT("default"),
    FALL_VARIANTS("fall_variants"),
    INTENTIONAL_GAME_DESIGN("intentional_game_design");

    public static final Index<String, DeathMessageType> ID_INDEX =
            Index.create(DeathMessageType.class, DeathMessageType::getId);

    public static final NbtCodec<DeathMessageType> CODEC = NbtCodecs.forEnum(values());

    private final String id;

    DeathMessageType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String getCodecName() {
        return this.id;
    }
}
