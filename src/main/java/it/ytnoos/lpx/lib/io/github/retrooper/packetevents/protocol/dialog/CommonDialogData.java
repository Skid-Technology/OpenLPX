package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.body.DialogBody;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.input.Input;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
public final class CommonDialogData {

    private final Component title;

    @Nullable private final Component externalTitle;

    private final boolean canCloseWithEscape;

    private final boolean pause;

    private final DialogAction afterAction;

    private final List<DialogBody> body;

    private final List<Input> inputs;

    public CommonDialogData(
            Component component,
            @Nullable Component component2,
            boolean canCloseWithEscape,
            boolean pause,
            DialogAction dialogAction,
            List<DialogBody> body,
            List<Input> inputs) {
        if (pause && !dialogAction.isWillUnpause()) {
            throw new IllegalArgumentException(
                    "Dialogs that pause the game must use after_action values that unpause it after user action!");
        }
        this.title = component;
        this.externalTitle = component2;
        this.canCloseWithEscape = canCloseWithEscape;
        this.pause = pause;
        this.afterAction = dialogAction;
        this.body = body;
        this.inputs = inputs;
    }

    public static CommonDialogData decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        Component orThrow =
                nBTCompound.getOrThrow(
                        "title", AdventureSerializer.serializer(packetWrapper), packetWrapper);
        Component orNull =
                nBTCompound.getOrNull(
                        "external_title",
                        AdventureSerializer.serializer(packetWrapper),
                        packetWrapper);
        boolean booleanOr = nBTCompound.getBooleanOr("can_close_with_escape", true);
        boolean booleanOr2 = nBTCompound.getBooleanOr("pause", true);
        DialogAction or =
                nBTCompound.getOr(
                        "after_action", DialogAction::decode, DialogAction.CLOSE, packetWrapper);
        List listOrEmpty = nBTCompound.getListOrEmpty("body", DialogBody::decode, packetWrapper);
        List listOrEmpty2 = nBTCompound.getListOrEmpty("inputs", Input::decode, packetWrapper);
        return new CommonDialogData(
                orThrow, orNull, booleanOr, booleanOr2, or, listOrEmpty, listOrEmpty2);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            CommonDialogData commonDialogData) {
        nBTCompound.set(
                "title",
                commonDialogData.title,
                AdventureSerializer.serializer(packetWrapper),
                packetWrapper);
        if (commonDialogData.externalTitle != null) {
            nBTCompound.set(
                    "external_title",
                    commonDialogData.externalTitle,
                    AdventureSerializer.serializer(packetWrapper),
                    packetWrapper);
        }
        if (!commonDialogData.canCloseWithEscape) {
            nBTCompound.setTag("can_close_with_escape", new NBTByte(false));
        }
        if (!commonDialogData.pause) {
            nBTCompound.setTag("pause", new NBTByte(false));
        }
        if (commonDialogData.afterAction != DialogAction.CLOSE) {
            nBTCompound.set(
                    "after_action",
                    commonDialogData.afterAction,
                    DialogAction::encode,
                    packetWrapper);
        }
        if (!commonDialogData.body.isEmpty()) {
            nBTCompound.setCompactList(
                    "body", commonDialogData.body, DialogBody::encode, packetWrapper);
        }
        if (!commonDialogData.inputs.isEmpty()) {
            nBTCompound.setList("inputs", commonDialogData.inputs, Input::encode, packetWrapper);
        }
    }

    public Component getTitle() {
        return this.title;
    }

    @Nullable
    public Component getExternalTitle() {
        return this.externalTitle;
    }

    public boolean isCanCloseWithEscape() {
        return this.canCloseWithEscape;
    }

    public boolean isPause() {
        return this.pause;
    }

    public DialogAction getAfterAction() {
        return this.afterAction;
    }

    public List<DialogBody> getBody() {
        return this.body;
    }

    public List<Input> getInputs() {
        return this.inputs;
    }
}
