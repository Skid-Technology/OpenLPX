package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureIndexUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum DialogAction {
    CLOSE("close", true),
    NONE("none", false),
    WAIT_FOR_RESPONSE("wait_for_response", true);

    public static final Index<String, DialogAction> NAME_INDEX =
            Index.create(DialogAction.class, DialogAction::getName);

    private final String name;

    private final boolean willUnpause;

    DialogAction(String name, boolean willUnpause) {
        this.name = name;
        this.willUnpause = willUnpause;
    }

    public static DialogAction decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        return AdventureIndexUtil.indexValueOrThrow(NAME_INDEX, ((NBTString) nBT).getValue());
    }

    public static NBT encode(PacketWrapper<?> packetWrapper, DialogAction dialogAction) {
        return new NBTString(dialogAction.name);
    }

    public String getName() {
        return this.name;
    }

    public boolean isWillUnpause() {
        return this.willUnpause;
    }
}
