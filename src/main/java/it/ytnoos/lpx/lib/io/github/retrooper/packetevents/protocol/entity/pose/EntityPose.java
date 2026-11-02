package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pose;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

public enum EntityPose {
    STANDING,
    FALL_FLYING,
    SLEEPING,
    SWIMMING,
    SPIN_ATTACK,
    CROUCHING,
    LONG_JUMPING,
    DYING,
    CROAKING,
    USING_TONGUE,
    SITTING,
    ROARING,
    SNIFFING,
    EMERGING,
    DIGGING,
    SLIDING,
    SHOOTING,
    INHALING;

    public int getId(ClientVersion clientVersion) {
        if (this == DYING && clientVersion.isOlderThan(ClientVersion.V_1_17)) {
            return 6;
        } else {
            return this.ordinal() >= 11 && clientVersion.isOlderThan(ClientVersion.V_1_19_3)
                    ? this.ordinal() - 1
                    : this.ordinal();
        }
    }

    public static EntityPose getById(ClientVersion clientVersion, int value) {
        if (value == 6 && clientVersion.isOlderThan(ClientVersion.V_1_17)) {
            return DYING;
        }
        if (value >= 10 && clientVersion.isOlderThan(ClientVersion.V_1_19_3)) {
            value++;
        }
        return values()[value];
    }
}
