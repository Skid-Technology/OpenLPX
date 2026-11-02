package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.Dialog;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.Dialogs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRef;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class ShowDialogClickEvent implements ClickEvent {

    private final MappedEntityRef<Dialog> dialog;

    public ShowDialogClickEvent(Dialog dialog) {
        this(new MappedEntityRef.Static<>(dialog));
    }

    public ShowDialogClickEvent(MappedEntityRef<Dialog> mappedEntityRef) {
        this.dialog = mappedEntityRef;
    }

    public static ShowDialogClickEvent decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        MappedEntityRef mappedEntityRef =
                MappedEntityRef.decode(
                        nBTCompound.getTagOrThrow("dialog"),
                        Dialogs.getRegistry(),
                        Dialog::decode,
                        packetWrapper);
        return new ShowDialogClickEvent(mappedEntityRef);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            ShowDialogClickEvent showDialogClickEvent) {
        nBTCompound.setTag(
                "dialog",
                MappedEntityRef.encode(packetWrapper, Dialog::encode, showDialogClickEvent.dialog));
    }

    @Override
    public ClickEventAction<?> getAction() {
        return ClickEventActions.SHOW_DIALOG;
    }

    @Override
    public it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent asAdventure() {
        return it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent.showDialog(
                this.dialog.get());
    }

    public MappedEntityRef<Dialog> getDialogRef() {
        return this.dialog;
    }

    public Dialog getDialog() {
        return this.dialog.get();
    }
}
