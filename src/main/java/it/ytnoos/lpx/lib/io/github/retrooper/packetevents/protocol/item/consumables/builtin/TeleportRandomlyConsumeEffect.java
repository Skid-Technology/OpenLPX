package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffectTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class TeleportRandomlyConsumeEffect extends ConsumeEffect<TeleportRandomlyConsumeEffect> {

    private final float diameter;

    private final boolean directionalParticles;

    public TeleportRandomlyConsumeEffect(float value) {
        this(value, true);
    }

    public TeleportRandomlyConsumeEffect(float diameter, boolean directionalParticles) {
        super(ConsumeEffectTypes.TELEPORT_RANDOMLY);
        this.diameter = diameter;
        this.directionalParticles = directionalParticles;
    }

    public static TeleportRandomlyConsumeEffect read(PacketWrapper<?> packetWrapper) {
        float value = packetWrapper.readFloat();
        boolean matches =
                packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_3)
                        || packetWrapper.readBoolean();
        return new TeleportRandomlyConsumeEffect(value, matches);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            TeleportRandomlyConsumeEffect teleportRandomlyConsumeEffect) {
        packetWrapper.writeFloat(teleportRandomlyConsumeEffect.diameter);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
            packetWrapper.writeBoolean(teleportRandomlyConsumeEffect.directionalParticles);
        }
    }

    public float getDiameter() {
        return this.diameter;
    }

    public boolean isDirectionalParticles() {
        return this.directionalParticles;
    }
}
