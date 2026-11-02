package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ClickEventAction<T extends ClickEvent> extends MappedEntity {

    boolean isAllowFromServer();

    T decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper);

    void encode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, T t);
}
