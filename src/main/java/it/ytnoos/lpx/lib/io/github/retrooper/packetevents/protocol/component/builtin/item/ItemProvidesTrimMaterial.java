package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimmaterial.TrimMaterial;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimmaterial.TrimMaterials;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MaybeMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Objects;

public class ItemProvidesTrimMaterial {

    private MaybeMappedEntity<TrimMaterial> material;

    public ItemProvidesTrimMaterial(TrimMaterial trimMaterial) {
        this(new MaybeMappedEntity<>(trimMaterial));
    }

    @Obsolete
    public ItemProvidesTrimMaterial(MaybeMappedEntity<TrimMaterial> maybeMappedEntity) {
        this.material = maybeMappedEntity;
    }

    public static ItemProvidesTrimMaterial read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            return new ItemProvidesTrimMaterial(TrimMaterial.read(packetWrapper));
        }
        MaybeMappedEntity maybeMappedEntity =
                MaybeMappedEntity.read(
                        packetWrapper, TrimMaterials.getRegistry(), TrimMaterial::read);
        return new ItemProvidesTrimMaterial(maybeMappedEntity);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemProvidesTrimMaterial itemProvidesTrimMaterial) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            TrimMaterial.write(packetWrapper, itemProvidesTrimMaterial.material.getValueOrThrow());
        } else {
            MaybeMappedEntity.write(
                    packetWrapper, itemProvidesTrimMaterial.material, TrimMaterial::write);
        }
    }

    public MaybeMappedEntity<TrimMaterial> getMaterial() {
        return this.material;
    }

    public void setMaterial(MaybeMappedEntity<TrimMaterial> maybeMappedEntity) {
        this.material = maybeMappedEntity;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ItemProvidesTrimMaterial)) {
            return false;
        }
        ItemProvidesTrimMaterial itemProvidesTrimMaterial = (ItemProvidesTrimMaterial) result;
        return this.material.equals(itemProvidesTrimMaterial.material);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.material);
    }
}
