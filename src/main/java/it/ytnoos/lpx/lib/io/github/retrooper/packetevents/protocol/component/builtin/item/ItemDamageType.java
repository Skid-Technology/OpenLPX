package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MaybeMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ItemDamageType {

    private MaybeMappedEntity<DamageType> damageType;

    public ItemDamageType(DamageType damageType) {
        this(new MaybeMappedEntity<>(damageType));
    }

    @Obsolete
    public ItemDamageType(MaybeMappedEntity<DamageType> maybeMappedEntity) {
        this.damageType = maybeMappedEntity;
    }

    public static ItemDamageType read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            return new ItemDamageType(DamageType.read(packetWrapper));
        }
        MaybeMappedEntity maybeMappedEntity =
                MaybeMappedEntity.read(packetWrapper, DamageTypes.getRegistry(), DamageType::read);
        return new ItemDamageType(maybeMappedEntity);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemDamageType itemDamageType) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            DamageType.write(packetWrapper, itemDamageType.damageType.getValueOrThrow());
        } else {
            MaybeMappedEntity.write(packetWrapper, itemDamageType.damageType, DamageType::write);
        }
    }

    public MaybeMappedEntity<DamageType> getDamageType() {
        return this.damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.setDamageType(new MaybeMappedEntity<>(damageType));
    }

    public void setDamageType(MaybeMappedEntity<DamageType> maybeMappedEntity) {
        this.damageType = maybeMappedEntity;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemDamageType itemDamageType = (ItemDamageType) result;
            return this.damageType.equals(itemDamageType.damageType);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.damageType);
    }
}
