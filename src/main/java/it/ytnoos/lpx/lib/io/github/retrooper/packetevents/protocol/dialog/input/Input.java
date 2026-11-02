package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.input;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class Input {

    private final String key;

    private final InputControl control;

    public Input(String key, InputControl inputControl) {
        this.key = key;
        this.control = inputControl;
    }

    public static Input decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound;
        String stringTagValueOrThrow =
                (nBTCompound = (NBTCompound) nBT).getStringTagValueOrThrow("key");
        InputControl inputControl = InputControl.decode(nBTCompound, packetWrapper);
        return new Input(stringTagValueOrThrow, inputControl);
    }

    public static NBT encode(PacketWrapper<?> packetWrapper, Input input) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound()).setTag("key", new NBTString(input.key));
        InputControl.encode(nBTCompound, packetWrapper, input.control);
        return nBTCompound;
    }

    public String getKey() {
        return this.key;
    }

    public InputControl getControl() {
        return this.control;
    }
}
