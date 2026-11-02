package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.button;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.action.Action;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class ActionButton {

    private final CommonButtonData button;

    @Nullable private final Action action;

    public ActionButton(CommonButtonData commonButtonData, @Nullable Action action2) {
        this.button = commonButtonData;
        this.action = action2;
    }

    public static ActionButton decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound;
        CommonButtonData commonButtonData =
                CommonButtonData.decode(nBTCompound = (NBTCompound) nBT, packetWrapper);
        Action orNull = nBTCompound.getOrNull("action", Action::decode, packetWrapper);
        return new ActionButton(commonButtonData, orNull);
    }

    public static NBT encode(PacketWrapper<?> packetWrapper, ActionButton actionButton) {
        NBTCompound nBTCompound;
        CommonButtonData.encode(
                nBTCompound = new NBTCompound(), packetWrapper, actionButton.button);
        if (actionButton.action != null) {
            nBTCompound.set("action", actionButton.action, Action::encode, packetWrapper);
        }
        return nBTCompound;
    }

    public CommonButtonData getButton() {
        return this.button;
    }

    @Nullable
    public Action getAction() {
        return this.action;
    }
}
