package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.button.ActionButton;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ConfirmationDialog extends AbstractMappedEntity implements Dialog {

    private final CommonDialogData common;

    private final ActionButton yesButton;

    private final ActionButton noButton;

    public ConfirmationDialog(
            CommonDialogData commonDialogData,
            ActionButton actionButton,
            ActionButton actionButton2) {
        this(null, commonDialogData, actionButton, actionButton2);
    }

    @Internal
    public ConfirmationDialog(
            @Nullable TypesBuilderData typesBuilderData,
            CommonDialogData commonDialogData,
            ActionButton actionButton,
            ActionButton actionButton2) {
        super(typesBuilderData);
        this.common = commonDialogData;
        this.yesButton = actionButton;
        this.noButton = actionButton2;
    }

    public static ConfirmationDialog decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        CommonDialogData commonDialogData = CommonDialogData.decode(nBTCompound, packetWrapper);
        ActionButton orThrow = nBTCompound.getOrThrow("yes", ActionButton::decode, packetWrapper);
        ActionButton orThrow2 = nBTCompound.getOrThrow("no", ActionButton::decode, packetWrapper);
        return new ConfirmationDialog(null, commonDialogData, orThrow, orThrow2);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            ConfirmationDialog confirmationDialog) {
        CommonDialogData.encode(nBTCompound, packetWrapper, confirmationDialog.common);
        nBTCompound.set("yes", confirmationDialog.yesButton, ActionButton::encode, packetWrapper);
        nBTCompound.set("no", confirmationDialog.noButton, ActionButton::encode, packetWrapper);
    }

    public Dialog copy(@Nullable TypesBuilderData typesBuilderData) {
        return new ConfirmationDialog(typesBuilderData, this.common, this.yesButton, this.noButton);
    }

    public CommonDialogData getCommon() {
        return this.common;
    }

    public ActionButton getYesButton() {
        return this.yesButton;
    }

    public ActionButton getNoButton() {
        return this.noButton;
    }

    @Override
    public DialogType<?> getType() {
        return DialogTypes.CONFIRMATION;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof ConfirmationDialog)) {
            return false;
        } else {
            ConfirmationDialog confirmationDialog = (ConfirmationDialog) result;
            if (!this.common.equals(confirmationDialog.common)) {
                return false;
            } else {
                return !this.yesButton.equals(confirmationDialog.yesButton)
                        ? false
                        : this.noButton.equals(confirmationDialog.noButton);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.common, this.yesButton, this.noButton);
    }
}
