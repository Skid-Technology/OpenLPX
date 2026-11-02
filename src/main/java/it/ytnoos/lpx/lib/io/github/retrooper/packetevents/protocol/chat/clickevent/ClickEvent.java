package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.Dialog;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTEnd;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSupportUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.NbtTagHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Locale;

@NullMarked
public interface ClickEvent {

    static ClickEvent decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        String stringTagValueOrThrow = nBTCompound.getStringTagValueOrThrow("action");
        return ClickEventActions.getRegistry()
                .getByNameOrThrow(stringTagValueOrThrow)
                .decode(nBTCompound, packetWrapper);
    }

    static void encode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, ClickEvent clickEvent) {
        nBTCompound.set(
                "action",
                clickEvent.getAction().getName(),
                ResourceLocation::encode,
                packetWrapper);
        ((ClickEventAction<ClickEvent>) clickEvent.getAction())
                .encode(nBTCompound, packetWrapper, clickEvent);
    }

    ClickEventAction<?> getAction();

    static ClickEvent fromAdventure(
            it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent clickEvent) {
        switch (clickEvent.action().name().toLowerCase(Locale.ROOT)) {
            case "open_url":
                return new OpenUrlClickEvent(AdventureSupportUtil.getStringValue(clickEvent));
            case "open_file":
                return new OpenFileClickEvent(AdventureSupportUtil.getStringValue(clickEvent));
            case "run_command":
                return new RunCommandClickEvent(AdventureSupportUtil.getStringValue(clickEvent));
            case "suggest_command":
                return new SuggestCommandClickEvent(
                        AdventureSupportUtil.getStringValue(clickEvent));
            case "change_page":
                return new ChangePageClickEvent(AdventureSupportUtil.getStringValue(clickEvent));
            case "copy_to_clipboard":
                return new CopyToClipboardClickEvent(
                        AdventureSupportUtil.getStringValue(clickEvent));
            case "show_dialog":
                return new ShowDialogClickEvent(
                        (Dialog)
                                ((it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent.Payload
                                                        .Dialog)
                                                clickEvent.payload())
                                        .dialog());
            case "custom":
                it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent.Payload.Custom custom;
                NbtTagHolder nbtTagHolder =
                        (NbtTagHolder)
                                (custom =
                                                (it.ytnoos.lpx.lib.kyori.adventure.text.event
                                                                .ClickEvent.Payload.Custom)
                                                        clickEvent.payload())
                                        .nbt();
                return new CustomClickEvent(
                        new ResourceLocation(custom.key()),
                        nbtTagHolder != null && !(nbtTagHolder.getTag() instanceof NBTEnd)
                                ? nbtTagHolder.getTag()
                                : null);
            default:
                throw new UnsupportedOperationException("Unsupported clickevent: " + clickEvent);
        }
    }

    it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent asAdventure();
}
