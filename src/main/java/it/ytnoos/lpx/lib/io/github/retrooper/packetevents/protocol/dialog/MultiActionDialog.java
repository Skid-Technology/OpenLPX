package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.button.ActionButton;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@NullMarked
public class MultiActionDialog extends AbstractMappedEntity implements Dialog {

    private final CommonDialogData common;

    private final List<ActionButton> actions;

    @Nullable private final ActionButton exitAction;

    private final int columns;

    public MultiActionDialog(
            CommonDialogData commonDialogData,
            List<ActionButton> values,
            @Nullable ActionButton actionButton,
            int value) {
        this(null, commonDialogData, values, actionButton, value);
    }

    @Internal
    public MultiActionDialog(
            @Nullable TypesBuilderData typesBuilderData,
            CommonDialogData commonDialogData,
            List<ActionButton> actions,
            @Nullable ActionButton actionButton,
            int columns) {
        super(typesBuilderData);
        this.common = commonDialogData;
        this.actions = actions;
        this.exitAction = actionButton;
        this.columns = columns;
    }

    public static MultiActionDialog decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        CommonDialogData commonDialogData = CommonDialogData.decode(nBTCompound, packetWrapper);
        List listOrThrow =
                nBTCompound.getListOrThrow("actions", ActionButton::decode, packetWrapper);
        ActionButton orNull =
                nBTCompound.getOrNull("exit_action", ActionButton::decode, packetWrapper);
        int value = nBTCompound.getNumberTagValueOrDefault("columns", 2).intValue();
        return new MultiActionDialog(null, commonDialogData, listOrThrow, orNull, value);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            MultiActionDialog multiActionDialog) {
        CommonDialogData.encode(nBTCompound, packetWrapper, multiActionDialog.common);
        nBTCompound.setList(
                "actions", multiActionDialog.actions, ActionButton::encode, packetWrapper);
        if (multiActionDialog.exitAction != null) {
            nBTCompound.set(
                    "exit_action",
                    multiActionDialog.exitAction,
                    ActionButton::encode,
                    packetWrapper);
        }
        if (multiActionDialog.columns != 2) {
            nBTCompound.setTag("columns", new NBTInt(multiActionDialog.columns));
        }
    }

    public Dialog copy(@Nullable TypesBuilderData typesBuilderData) {
        return new MultiActionDialog(
                typesBuilderData, this.common, this.actions, this.exitAction, this.columns);
    }

    public CommonDialogData getCommon() {
        return this.common;
    }

    public List<ActionButton> getActions() {
        return this.actions;
    }

    @Nullable
    public ActionButton getExitAction() {
        return this.exitAction;
    }

    public int getColumns() {
        return this.columns;
    }

    @Override
    public DialogType<?> getType() {
        return DialogTypes.MULTI_ACTION;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof MultiActionDialog)) {
            return false;
        } else {
            MultiActionDialog multiActionDialog = (MultiActionDialog) result;
            if (this.columns != multiActionDialog.columns) {
                return false;
            } else if (!this.common.equals(multiActionDialog.common)) {
                return false;
            } else {
                return !this.actions.equals(multiActionDialog.actions)
                        ? false
                        : Objects.equals(this.exitAction, multiActionDialog.exitAction);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.common, this.actions, this.exitAction, this.columns);
    }
}
