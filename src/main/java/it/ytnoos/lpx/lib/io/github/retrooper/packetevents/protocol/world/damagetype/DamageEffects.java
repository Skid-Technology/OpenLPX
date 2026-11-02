package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum DamageEffects implements CodecNameable {
    HURT("hurt"),
    THORNS("thorns"),
    DROWNING("drowning"),
    BURNING("burning"),
    POKING("poking"),
    FREEZING("freezing");

    public static final Index<String, DamageEffects> ID_INDEX =
            Index.create(DamageEffects.class, DamageEffects::getId);

    public static final NbtCodec<DamageEffects> CODEC = NbtCodecs.forEnum(values());

    private final String id;

    DamageEffects(String id) {
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
