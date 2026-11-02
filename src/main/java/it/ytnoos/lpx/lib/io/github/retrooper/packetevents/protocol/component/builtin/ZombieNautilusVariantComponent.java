package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.nautilus.ZombieNautilusVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.nautilus.ZombieNautilusVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MaybeMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ZombieNautilusVariantComponent {

    private MaybeMappedEntity<ZombieNautilusVariant> variant;

    public ZombieNautilusVariantComponent(ZombieNautilusVariant zombieNautilusVariant) {
        this(new MaybeMappedEntity<>(zombieNautilusVariant));
    }

    public ZombieNautilusVariantComponent(
            MaybeMappedEntity<ZombieNautilusVariant> maybeMappedEntity) {
        this.variant = maybeMappedEntity;
    }

    public static ZombieNautilusVariantComponent read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            return new ZombieNautilusVariantComponent(ZombieNautilusVariant.read(packetWrapper));
        }
        MaybeMappedEntity maybeMappedEntity =
                MaybeMappedEntity.read(
                        packetWrapper,
                        ZombieNautilusVariants.getRegistry(),
                        ZombieNautilusVariant::read);
        return new ZombieNautilusVariantComponent(maybeMappedEntity);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            ZombieNautilusVariantComponent zombieNautilusVariantComponent) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            ZombieNautilusVariant.write(
                    packetWrapper, zombieNautilusVariantComponent.variant.getValueOrThrow());
        } else {
            MaybeMappedEntity.write(
                    packetWrapper,
                    zombieNautilusVariantComponent.variant,
                    ZombieNautilusVariant::write);
        }
    }

    public MaybeMappedEntity<ZombieNautilusVariant> getVariant() {
        return this.variant;
    }

    public void setVariant(MaybeMappedEntity<ZombieNautilusVariant> maybeMappedEntity) {
        this.variant = maybeMappedEntity;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ZombieNautilusVariantComponent)) {
            return false;
        }
        ZombieNautilusVariantComponent zombieNautilusVariantComponent =
                (ZombieNautilusVariantComponent) result;
        return this.variant.equals(zombieNautilusVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
