package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public interface PositionSourceType<T extends PositionSource> extends MappedEntity {

    T read(PacketWrapper<?> packetWrapper);

    void write(PacketWrapper<?> packetWrapper, T t);

    T decode(NBTCompound nBTCompound, ClientVersion clientVersion);

    void encode(T t, ClientVersion clientVersion, NBTCompound nBTCompound);
}
