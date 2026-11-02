package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.MaterialType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StateType {

    public static final NbtCodec<StateType.Mapped> MAPPED_CODEC =
            NbtCodecs.forRegistry(StateTypes.getRegistry());

    public static final NbtCodec<StateType> CODEC =
            MAPPED_CODEC.apply(StateType.Mapped::getStateType, StateType::getMapped);

    private final StateType.Mapped mapped;

    private final float blastResistance;

    private final float hardness;

    private final boolean isSolid;

    private final boolean isBlocking;

    private final boolean isAir;

    private final boolean requiresCorrectTool;

    private final boolean exceedsCube;

    private final MaterialType materialType;

    @Internal
    public StateType(
            TypesBuilderData typesBuilderData,
            float blastResistance,
            float hardness,
            boolean isSolid,
            boolean isBlocking,
            boolean isAir,
            boolean requiresCorrectTool,
            boolean exceedsCube,
            MaterialType materialType2) {
        this.mapped = new StateType.Mapped(typesBuilderData);
        this.blastResistance = blastResistance;
        this.hardness = hardness;
        this.isSolid = isSolid;
        this.isBlocking = isBlocking;
        this.isAir = isAir;
        this.requiresCorrectTool = requiresCorrectTool;
        this.exceedsCube = exceedsCube;
        this.materialType = materialType2;
    }

    public StateType.Mapped getMapped() {
        return this.mapped;
    }

    public WrappedBlockState createBlockState() {
        return WrappedBlockState.getDefaultState(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(), this);
    }

    public WrappedBlockState createBlockState(ClientVersion clientVersion) {
        return WrappedBlockState.getDefaultState(clientVersion, this);
    }

    public String getName() {
        return this.mapped.getName().getKey();
    }

    public float getBlastResistance() {
        return this.blastResistance;
    }

    public float getHardness() {
        return this.hardness;
    }

    public boolean isSolid() {
        return this.isSolid;
    }

    public boolean isBlocking() {
        return this.isBlocking;
    }

    public boolean isAir() {
        return this.isAir;
    }

    public boolean isRequiresCorrectTool() {
        return this.requiresCorrectTool;
    }

    public boolean isReplaceable() {
        switch (this.getMaterialType()) {
            case AIR:
            case STRUCTURAL_AIR:
            case REPLACEABLE_PLANT:
            case REPLACEABLE_FIREPROOF_PLANT:
            case REPLACEABLE_WATER_PLANT:
            case WATER:
            case BUBBLE_COLUMN:
            case LAVA:
            case TOP_SNOW:
            case FIRE:
                return true;
            default:
                return false;
        }
    }

    public boolean exceedsCube() {
        return this.exceedsCube;
    }

    public MaterialType getMaterialType() {
        return this.materialType;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            StateType stateType = (StateType) result;
            return Float.compare(this.blastResistance, stateType.blastResistance) == 0
                    && Float.compare(this.hardness, stateType.hardness) == 0
                    && this.isSolid == stateType.isSolid
                    && this.isBlocking == stateType.isBlocking
                    && this.isAir == stateType.isAir
                    && this.requiresCorrectTool == stateType.requiresCorrectTool
                    && this.exceedsCube == stateType.exceedsCube
                    && Objects.equals(this.getName(), stateType.getName())
                    && this.materialType == stateType.materialType;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.getName(),
                this.blastResistance,
                this.hardness,
                this.isSolid,
                this.isBlocking,
                this.isAir,
                this.requiresCorrectTool,
                this.exceedsCube,
                this.materialType);
    }

    public final class Mapped extends AbstractMappedEntity {

        @Internal
        public Mapped(@Nullable TypesBuilderData typesBuilderData) {
            super(typesBuilderData);
        }

        public StateType getStateType() {
            return StateType.this;
        }
    }
}
