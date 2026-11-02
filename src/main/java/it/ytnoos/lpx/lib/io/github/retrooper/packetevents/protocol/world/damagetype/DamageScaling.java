package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum DamageScaling implements CodecNameable {
    NEVER("never"),
    WHEN_CAUSED_BY_LIVING_NON_PLAYER("when_caused_by_living_non_player"),
    ALWAYS("always");

    public static final Index<String, DamageScaling> ID_INDEX =
            Index.create(DamageScaling.class, DamageScaling::getId);

    public static final NbtCodec<DamageScaling> CODEC = NbtCodecs.forEnum(values());

    private final String id;

    DamageScaling(String id) {
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
