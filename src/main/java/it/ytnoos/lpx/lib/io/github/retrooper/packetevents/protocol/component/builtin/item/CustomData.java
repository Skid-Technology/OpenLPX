package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureNbtUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class CustomData {

    private CustomData() {}

    public static NBTCompound read(PacketWrapper<?> packetWrapper) {
        NBT nBT;
        if ((nBT = packetWrapper.readNBTRaw()) instanceof NBTCompound) {
            return (NBTCompound) nBT;
        } else if (nBT instanceof NBTString) {
            return (NBTCompound) AdventureNbtUtil.fromString(((NBTString) nBT).getValue());
        } else {
            throw new UnsupportedOperationException("Unsupported custom data tag: " + nBT);
        }
    }

    public static void write(PacketWrapper<?> packetWrapper, NBTCompound nBTCompound) {
        packetWrapper.writeNBT(nBTCompound);
    }
}
