package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.body;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class PlainMessageDialogBody implements DialogBody {

    private final PlainMessage message;

    public PlainMessageDialogBody(PlainMessage plainMessage) {
        this.message = plainMessage;
    }

    public static PlainMessageDialogBody decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        return new PlainMessageDialogBody(PlainMessage.decode(nBTCompound, packetWrapper));
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            PlainMessageDialogBody plainMessageDialogBody) {
        PlainMessage.encode(nBTCompound, packetWrapper, plainMessageDialogBody.message);
    }

    @Override
    public DialogBodyType<?> getType() {
        return DialogBodyTypes.PLAIN_MESSAGE;
    }

    public PlainMessage getMessage() {
        return this.message;
    }
}
