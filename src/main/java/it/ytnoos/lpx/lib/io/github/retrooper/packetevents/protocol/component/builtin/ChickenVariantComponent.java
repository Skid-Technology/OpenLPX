package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken.ChickenVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken.ChickenVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MaybeMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Objects;

public class ChickenVariantComponent {

    private MaybeMappedEntity<ChickenVariant> variant;

    public ChickenVariantComponent(ChickenVariant chickenVariant) {
        this(new MaybeMappedEntity<>(chickenVariant));
    }

    @Obsolete
    public ChickenVariantComponent(MaybeMappedEntity<ChickenVariant> maybeMappedEntity) {
        this.variant = maybeMappedEntity;
    }

    public static ChickenVariantComponent read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            return new ChickenVariantComponent(ChickenVariant.read(packetWrapper));
        }
        MaybeMappedEntity maybeMappedEntity =
                MaybeMappedEntity.read(
                        packetWrapper, ChickenVariants.getRegistry(), ChickenVariant::read);
        return new ChickenVariantComponent(maybeMappedEntity);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ChickenVariantComponent chickenVariantComponent) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            ChickenVariant.write(packetWrapper, chickenVariantComponent.variant.getValueOrThrow());
        } else {
            MaybeMappedEntity.write(
                    packetWrapper, chickenVariantComponent.variant, ChickenVariant::write);
        }
    }

    public MaybeMappedEntity<ChickenVariant> getVariant() {
        return this.variant;
    }

    public void setVariant(MaybeMappedEntity<ChickenVariant> maybeMappedEntity) {
        this.variant = maybeMappedEntity;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ChickenVariantComponent)) {
            return false;
        }
        ChickenVariantComponent chickenVariantComponent = (ChickenVariantComponent) result;
        return this.variant.equals(chickenVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
