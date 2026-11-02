package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.action;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Action {

    static Action decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound;
        String stringTagValueOrThrow =
                (nBTCompound = (NBTCompound) nBT).getStringTagValueOrThrow("type");
        return ActionTypes.getRegistry()
                .getByNameOrThrow(stringTagValueOrThrow)
                .decode(nBTCompound, packetWrapper);
    }

    static NBT encode(PacketWrapper<?> packetWrapper, Action action) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .set("type", action.getType().getName(), ResourceLocation::encode, packetWrapper);
        ((ActionType<Action>) action.getType()).encode(nBTCompound, packetWrapper, action);
        return nBTCompound;
    }

    ActionType<?> getType();
}
