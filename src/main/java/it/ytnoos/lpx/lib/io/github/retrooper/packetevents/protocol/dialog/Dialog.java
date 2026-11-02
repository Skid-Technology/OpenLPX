package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.dialog.DialogLike;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface Dialog
        extends MappedEntity, DeepComparableEntity, CopyableEntity<Dialog>, DialogLike {

    static Dialog read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntityOrDirect(Dialogs.getRegistry(), Dialog::readDirect);
    }

    static void write(PacketWrapper<?> packetWrapper, Dialog dialog) {
        packetWrapper.writeMappedEntityOrDirect(dialog, Dialog::writeDirect);
    }

    static Dialog readDirect(PacketWrapper<?> packetWrapper) {
        return decodeDirect(packetWrapper.readNBTRaw(), packetWrapper, null);
    }

    static void writeDirect(PacketWrapper<?> packetWrapper, Dialog dialog) {
        packetWrapper.writeNBTRaw(encodeDirect(dialog, packetWrapper));
    }

    static Dialog decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        return nBT instanceof NBTString
                ? packetWrapper
                        .replaceRegistry(Dialogs.getRegistry())
                        .getByNameOrThrow(((NBTString) nBT).getValue())
                : decodeDirect(nBT, packetWrapper, null);
    }

    static NBT encode(PacketWrapper<?> packetWrapper, Dialog dialog) {
        return dialog.isRegistered()
                ? new NBTString(dialog.getName().toString())
                : encodeDirect(dialog, packetWrapper);
    }

    @Internal
    static Dialog decodeDirect(
            NBT nBT, PacketWrapper<?> packetWrapper, @Nullable TypesBuilderData typesBuilderData) {
        NBTCompound nBTCompound;
        String stringTagValueOrThrow =
                (nBTCompound = (NBTCompound) nBT).getStringTagValueOrThrow("type");
        return DialogTypes.getRegistry()
                .getByNameOrThrow(stringTagValueOrThrow)
                .decode(nBTCompound, packetWrapper)
                .copy(typesBuilderData);
    }

    @Internal
    static NBT encodeDirect(Dialog dialog, PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .setTag("type", new NBTString(dialog.getType().getName().toString()));
        ((DialogType<Dialog>) dialog.getType()).encode(nBTCompound, packetWrapper, dialog);
        return nBTCompound;
    }

    DialogType<?> getType();
}
