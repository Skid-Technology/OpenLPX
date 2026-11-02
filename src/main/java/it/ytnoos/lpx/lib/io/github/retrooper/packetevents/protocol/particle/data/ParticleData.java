package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class ParticleData {

    static ParticleData EMPTY = new ParticleData();

    public static <T extends ParticleData> T emptyData() {
        return (T) EMPTY;
    }

    public boolean isEmpty() {
        return true;
    }
}
