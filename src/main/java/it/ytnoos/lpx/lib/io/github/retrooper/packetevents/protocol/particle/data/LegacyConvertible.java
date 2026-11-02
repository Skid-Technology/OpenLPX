package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

public interface LegacyConvertible {

    LegacyParticleData toLegacy(ClientVersion clientVersion);
}
