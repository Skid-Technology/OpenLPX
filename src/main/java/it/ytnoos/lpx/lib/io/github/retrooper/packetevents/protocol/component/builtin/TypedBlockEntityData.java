package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.blockentity.BlockEntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.blockentity.BlockEntityTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public final class TypedBlockEntityData {

    private static final BlockEntityType FALLBACK_TYPE = BlockEntityTypes.FURNACE;

    private final BlockEntityType type;

    private final NBTCompound compound;

    public TypedBlockEntityData(NBTCompound nBTCompound) {
        this(FALLBACK_TYPE, nBTCompound);
    }

    public TypedBlockEntityData(BlockEntityType blockEntityType, NBTCompound nBTCompound) {
        this.type = blockEntityType;
        this.compound = nBTCompound;
    }

    public static TypedBlockEntityData read(PacketWrapper<?> packetWrapper) {
        BlockEntityType blockEntityType =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)
                        ? packetWrapper.readMappedEntity(BlockEntityTypes.getRegistry())
                        : FALLBACK_TYPE;
        NBTCompound nBTCompound = packetWrapper.readNBT();
        return new TypedBlockEntityData(blockEntityType, nBTCompound);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, TypedBlockEntityData typedBlockEntityData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            packetWrapper.writeMappedEntity(typedBlockEntityData.type);
        }
        packetWrapper.writeNBT(typedBlockEntityData.compound);
    }

    public BlockEntityType getType() {
        return this.type;
    }

    public NBTCompound getCompound() {
        return this.compound;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            TypedBlockEntityData typedBlockEntityData = (TypedBlockEntityData) result;
            return !this.type.equals(typedBlockEntityData.type)
                    ? false
                    : this.compound.equals(typedBlockEntityData.compound);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.compound);
    }
}
