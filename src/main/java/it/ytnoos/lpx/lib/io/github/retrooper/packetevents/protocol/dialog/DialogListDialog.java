package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.button.ActionButton;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRefSet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class DialogListDialog extends AbstractMappedEntity implements Dialog {

    private final CommonDialogData common;

    private final MappedEntityRefSet<Dialog> dialogs;

    @Nullable private final ActionButton exitAction;

    private final int columns;

    private final int buttonWidth;

    public DialogListDialog(
            CommonDialogData commonDialogData,
            MappedEntityRefSet<Dialog> mappedEntityRefSet,
            @Nullable ActionButton actionButton,
            int value,
            int value2) {
        this(null, commonDialogData, mappedEntityRefSet, actionButton, value, value2);
    }

    @Internal
    public DialogListDialog(
            @Nullable TypesBuilderData typesBuilderData,
            CommonDialogData commonDialogData,
            MappedEntityRefSet<Dialog> mappedEntityRefSet,
            @Nullable ActionButton actionButton,
            int columns,
            int buttonWidth) {
        super(typesBuilderData);
        this.common = commonDialogData;
        this.dialogs = mappedEntityRefSet;
        this.exitAction = actionButton;
        this.columns = columns;
        this.buttonWidth = buttonWidth;
    }

    public static DialogListDialog decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        CommonDialogData commonDialogData = CommonDialogData.decode(nBTCompound, packetWrapper);
        MappedEntityRefSet orThrow =
                nBTCompound.getOrThrow("dialogs", MappedEntitySet::decodeRefSet, packetWrapper);
        ActionButton orNull =
                nBTCompound.getOrNull("exit_action", ActionButton::decode, packetWrapper);
        int value = nBTCompound.getNumberTagValueOrDefault("columns", 2).intValue();
        int value2 = nBTCompound.getNumberTagValueOrDefault("button_width", 150).intValue();
        return new DialogListDialog(null, commonDialogData, orThrow, orNull, value, value2);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            DialogListDialog dialogListDialog) {
        CommonDialogData.encode(nBTCompound, packetWrapper, dialogListDialog.common);
        nBTCompound.set(
                "dialogs", dialogListDialog.dialogs, MappedEntitySet::encodeRefSet, packetWrapper);
        if (dialogListDialog.exitAction != null) {
            nBTCompound.set(
                    "exit_action",
                    dialogListDialog.exitAction,
                    ActionButton::encode,
                    packetWrapper);
        }
        if (dialogListDialog.columns != 2) {
            nBTCompound.setTag("columns", new NBTInt(dialogListDialog.columns));
        }
        if (dialogListDialog.buttonWidth != 150) {
            nBTCompound.setTag("button_width", new NBTInt(dialogListDialog.buttonWidth));
        }
    }

    public Dialog copy(@Nullable TypesBuilderData typesBuilderData) {
        return new DialogListDialog(
                typesBuilderData,
                this.common,
                this.dialogs,
                this.exitAction,
                this.columns,
                this.buttonWidth);
    }

    public CommonDialogData getCommon() {
        return this.common;
    }

    public MappedEntityRefSet<Dialog> getDialogs() {
        return this.dialogs;
    }

    @Nullable
    public ActionButton getExitAction() {
        return this.exitAction;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getButtonWidth() {
        return this.buttonWidth;
    }

    @Override
    public DialogType<?> getType() {
        return DialogTypes.DIALOG_LIST;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof DialogListDialog)) {
            return false;
        } else {
            DialogListDialog dialogListDialog = (DialogListDialog) result;
            if (this.columns != dialogListDialog.columns) {
                return false;
            } else if (this.buttonWidth != dialogListDialog.buttonWidth) {
                return false;
            } else if (!this.common.equals(dialogListDialog.common)) {
                return false;
            } else {
                return !this.dialogs.equals(dialogListDialog.dialogs)
                        ? false
                        : Objects.equals(this.exitAction, dialogListDialog.exitAction);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.common, this.dialogs, this.exitAction, this.columns, this.buttonWidth);
    }
}
