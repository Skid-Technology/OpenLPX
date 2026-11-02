package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.body;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface DialogBody {

    static DialogBody decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound;
        String stringTagValueOrThrow =
                (nBTCompound = (NBTCompound) nBT).getStringTagValueOrThrow("type");
        return DialogBodyTypes.getRegistry()
                .getByNameOrThrow(stringTagValueOrThrow)
                .decode(nBTCompound, packetWrapper);
    }

    static NBT encode(PacketWrapper<?> packetWrapper, DialogBody dialogBody) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .set(
                        "type",
                        dialogBody.getType().getName(),
                        ResourceLocation::encode,
                        packetWrapper);
        ((DialogBodyType<DialogBody>) dialogBody.getType())
                .encode(nBTCompound, packetWrapper, dialogBody);
        return nBTCompound;
    }

    DialogBodyType<?> getType();
}
