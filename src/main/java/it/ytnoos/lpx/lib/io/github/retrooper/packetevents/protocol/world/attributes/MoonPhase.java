package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum MoonPhase implements CodecNameable {
    FULL_MOON("full_moon"),
    WANING_GIBBOUS("waning_gibbous"),
    THIRD_QUARTER("third_quarter"),
    WANING_CRESCENT("waning_crescent"),
    NEW_MOON("new_moon"),
    WAXING_CRESCENT("waxing_crescent"),
    FIRST_QUARTER("first_quarter"),
    WAXING_GIBBOUS("waxing_gibbous");

    public static final NbtCodec<MoonPhase> CODEC = NbtCodecs.forEnum(values());

    private final String name;

    MoonPhase(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getCodecName() {
        return this.name;
    }
}
