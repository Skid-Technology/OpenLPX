package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.input;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface InputControl {

    static InputControl decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        String stringTagValueOrThrow = nBTCompound.getStringTagValueOrThrow("type");
        return InputControlTypes.getRegistry()
                .getByNameOrThrow(stringTagValueOrThrow)
                .decode(nBTCompound, packetWrapper);
    }

    static void encode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, InputControl inputControl) {
        nBTCompound.set(
                "type", inputControl.getType().getName(), ResourceLocation::encode, packetWrapper);
        ((InputControlType<InputControl>) inputControl.getType())
                .encode(nBTCompound, packetWrapper, inputControl);
    }

    InputControlType<?> getType();
}
