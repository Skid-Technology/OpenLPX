package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.body;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface DialogBodyType<T extends DialogBody> extends MappedEntity {

    T decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper);

    void encode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, T t);
}
