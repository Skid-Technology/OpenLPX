package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public interface ConsumeEffectType<T extends ConsumeEffect<?>> extends MappedEntity {

    T read(PacketWrapper<?> packetWrapper);

    void write(PacketWrapper<?> packetWrapper, T t);
}
