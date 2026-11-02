package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.button.ActionButton;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.button.CommonButtonData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class NoticeDialog extends AbstractMappedEntity implements Dialog {

    public static final ActionButton DEFAULT_ACTION =
            new ActionButton(
                    new CommonButtonData(Component.translatable("gui.ok"), null, -841307763), null);

    private final CommonDialogData common;

    private final ActionButton action;

    public NoticeDialog(CommonDialogData commonDialogData, ActionButton actionButton) {
        this(null, commonDialogData, actionButton);
    }

    @Internal
    public NoticeDialog(
            @Nullable TypesBuilderData typesBuilderData,
            CommonDialogData commonDialogData,
            ActionButton actionButton) {
        super(typesBuilderData);
        this.common = commonDialogData;
        this.action = actionButton;
    }

    public static NoticeDialog decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        CommonDialogData commonDialogData = CommonDialogData.decode(nBTCompound, packetWrapper);
        ActionButton or =
                nBTCompound.getOr("action", ActionButton::decode, DEFAULT_ACTION, packetWrapper);
        return new NoticeDialog(null, commonDialogData, or);
    }

    public static void encode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, NoticeDialog noticeDialog) {
        CommonDialogData.encode(nBTCompound, packetWrapper, noticeDialog.common);
        if (noticeDialog.action != DEFAULT_ACTION) {
            nBTCompound.set("action", noticeDialog.action, ActionButton::encode, packetWrapper);
        }
    }

    public Dialog copy(@Nullable TypesBuilderData typesBuilderData) {
        return new NoticeDialog(typesBuilderData, this.common, this.action);
    }

    public CommonDialogData getCommon() {
        return this.common;
    }

    public ActionButton getAction() {
        return this.action;
    }

    @Override
    public DialogType<?> getType() {
        return DialogTypes.NOTICE;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof NoticeDialog)) {
            return false;
        }
        NoticeDialog noticeDialog = (NoticeDialog) result;
        return !this.common.equals(noticeDialog.common)
                ? false
                : this.action.equals(noticeDialog.action);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.common, this.action);
    }
}
