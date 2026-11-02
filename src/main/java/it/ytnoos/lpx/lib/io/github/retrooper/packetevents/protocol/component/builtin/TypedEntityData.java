package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public final class TypedEntityData {

    private static final EntityType FALLBACK_TYPE = EntityTypes.PIG;

    private final EntityType type;

    private final NBTCompound compound;

    public TypedEntityData(NBTCompound nBTCompound) {
        this(FALLBACK_TYPE, nBTCompound);
    }

    public TypedEntityData(EntityType entityType, NBTCompound nBTCompound) {
        this.type = entityType;
        this.compound = nBTCompound;
    }

    public static TypedEntityData read(PacketWrapper<?> packetWrapper) {
        EntityType entityType =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)
                        ? packetWrapper.readMappedEntity(EntityTypes.getRegistry())
                        : FALLBACK_TYPE;
        NBTCompound nBTCompound = packetWrapper.readNBT();
        return new TypedEntityData(entityType, nBTCompound);
    }

    public static void write(PacketWrapper<?> packetWrapper, TypedEntityData typedEntityData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            packetWrapper.writeMappedEntity(typedEntityData.type);
        }
        packetWrapper.writeNBT(typedEntityData.compound);
    }

    public EntityType getType() {
        return this.type;
    }

    public NBTCompound getCompound() {
        return this.compound;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            TypedEntityData typedEntityData = (TypedEntityData) result;
            return !this.type.equals(typedEntityData.type)
                    ? false
                    : this.compound.equals(typedEntityData.compound);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.compound);
    }
}
