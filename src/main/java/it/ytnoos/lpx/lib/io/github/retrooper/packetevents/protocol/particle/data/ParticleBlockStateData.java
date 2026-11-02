package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.BlockStateCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ParticleBlockStateData extends ParticleData implements LegacyConvertible {

    private WrappedBlockState blockState;

    public ParticleBlockStateData(WrappedBlockState wrappedBlockState) {
        this.blockState = wrappedBlockState;
    }

    public WrappedBlockState getBlockState() {
        return this.blockState;
    }

    public void setBlockState(WrappedBlockState wrappedBlockState) {
        this.blockState = wrappedBlockState;
    }

    public static ParticleBlockStateData read(PacketWrapper<?> packetWrapper) {
        int value;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
            value = packetWrapper.readVarInt();
        } else if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_13)) {
            value = packetWrapper.readInt();
        } else {
            value = packetWrapper.readVarInt();
        }
        return new ParticleBlockStateData(
                WrappedBlockState.getByGlobalId(
                        packetWrapper.getServerVersion().toClientVersion(), value));
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ParticleBlockStateData particleBlockStateData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
            packetWrapper.writeVarInt(particleBlockStateData.getBlockState().getGlobalId());
        } else {
            packetWrapper.writeInt(particleBlockStateData.getBlockState().getGlobalId());
        }
    }

    public static ParticleBlockStateData decode(
            NBTCompound nBTCompound, ClientVersion clientVersion) {
        String text =
                clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5) ? "block_state" : "value";
        WrappedBlockState orThrow =
                nBTCompound.getOrThrow(
                        text,
                        BlockStateCodec.CODEC,
                        PacketWrapper.createDummyWrapper(clientVersion));
        return new ParticleBlockStateData(orThrow);
    }

    public static void encode(
            ParticleBlockStateData particleBlockStateData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        String text =
                clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5) ? "block_state" : "value";
        nBTCompound.setTag(
                text,
                BlockStateCodec.CODEC.encode(
                        PacketWrapper.createDummyWrapper(clientVersion),
                        particleBlockStateData.blockState));
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public LegacyParticleData toLegacy(ClientVersion clientVersion) {
        return LegacyParticleData.ofOne(this.blockState.getGlobalId());
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ParticleBlockStateData particleBlockStateData = (ParticleBlockStateData) result;
            return this.blockState.equals(particleBlockStateData.blockState);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.blockState);
    }
}
