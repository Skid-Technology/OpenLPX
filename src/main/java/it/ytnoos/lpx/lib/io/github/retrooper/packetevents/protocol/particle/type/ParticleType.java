package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface ParticleType<T extends ParticleData> extends MappedEntity {

    T readData(PacketWrapper<?> packetWrapper);

    void writeData(PacketWrapper<?> packetWrapper, T t);

    T decodeData(NBTCompound nBTCompound, ClientVersion clientVersion);

    void encodeData(T t, ClientVersion clientVersion, NBTCompound nBTCompound);

    @Deprecated
    default Function<PacketWrapper<?>, ParticleData> readDataFunction() {
        return this::readData;
    }

    @Deprecated
    default BiConsumer<PacketWrapper<?>, ParticleData> writeDataFunction() {
        return (result, result2) -> this.writeData(result, (T) result2);
    }
}
