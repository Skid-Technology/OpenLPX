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

import java.util.Objects;

@NullMarked
public class ServerLinksDialog extends AbstractMappedEntity implements Dialog {

    private final CommonDialogData common;

    @Nullable private final ActionButton exitAction;

    private final int columns;

    private final int buttonWidth;

    public ServerLinksDialog(
            CommonDialogData commonDialogData,
            @Nullable ActionButton actionButton,
            int value,
            int value2) {
        this(null, commonDialogData, actionButton, value, value2);
    }

    @Internal
    public ServerLinksDialog(
            @Nullable TypesBuilderData typesBuilderData,
            CommonDialogData commonDialogData,
            @Nullable ActionButton actionButton,
            int columns,
            int buttonWidth) {
        super(typesBuilderData);
        this.common = commonDialogData;
        this.exitAction = actionButton;
        this.columns = columns;
        this.buttonWidth = buttonWidth;
    }

    public static ServerLinksDialog decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        CommonDialogData commonDialogData = CommonDialogData.decode(nBTCompound, packetWrapper);
        ActionButton orNull =
                nBTCompound.getOrNull("exit_action", ActionButton::decode, packetWrapper);
        int value = nBTCompound.getNumberTagValueOrDefault("columns", 2).intValue();
        int value2 = nBTCompound.getNumberTagValueOrDefault("button_width", 150).intValue();
        return new ServerLinksDialog(null, commonDialogData, orNull, value, value2);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            ServerLinksDialog serverLinksDialog) {
        CommonDialogData.encode(nBTCompound, packetWrapper, serverLinksDialog.common);
        if (serverLinksDialog.exitAction != null) {
            nBTCompound.set(
                    "exit_action",
                    serverLinksDialog.exitAction,
                    ActionButton::encode,
                    packetWrapper);
        }
        if (serverLinksDialog.columns != 2) {
            nBTCompound.setTag("columns", new NBTInt(serverLinksDialog.columns));
        }
        if (serverLinksDialog.buttonWidth != 150) {
            nBTCompound.setTag("button_width", new NBTInt(serverLinksDialog.buttonWidth));
        }
    }

    public Dialog copy(@Nullable TypesBuilderData typesBuilderData) {
        return new ServerLinksDialog(
                typesBuilderData, this.common, this.exitAction, this.columns, this.buttonWidth);
    }

    public CommonDialogData getCommon() {
        return this.common;
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
        return DialogTypes.SERVER_LINKS;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof ServerLinksDialog)) {
            return false;
        } else {
            ServerLinksDialog serverLinksDialog = (ServerLinksDialog) result;
            if (this.columns != serverLinksDialog.columns) {
                return false;
            } else if (this.buttonWidth != serverLinksDialog.buttonWidth) {
                return false;
            } else {
                return !this.common.equals(serverLinksDialog.common)
                        ? false
                        : Objects.equals(this.exitAction, serverLinksDialog.exitAction);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.common, this.exitAction, this.columns, this.buttonWidth);
    }
}
