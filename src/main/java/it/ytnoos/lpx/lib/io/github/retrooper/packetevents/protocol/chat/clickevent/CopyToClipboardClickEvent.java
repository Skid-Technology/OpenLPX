package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class CopyToClipboardClickEvent implements ClickEvent {

    private final String value;

    public CopyToClipboardClickEvent(String value) {
        this.value = value;
    }

    public static CopyToClipboardClickEvent decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        String stringTagValueOrThrow = nBTCompound.getStringTagValueOrThrow("value");
        return new CopyToClipboardClickEvent(stringTagValueOrThrow);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            CopyToClipboardClickEvent copyToClipboardClickEvent) {
        nBTCompound.setTag("value", new NBTString(copyToClipboardClickEvent.value));
    }

    @Override
    public ClickEventAction<?> getAction() {
        return ClickEventActions.COPY_TO_CLIPBOARD;
    }

    @Override
    public it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent asAdventure() {
        return it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent.copyToClipboard(this.value);
    }

    public String getValue() {
        return this.value;
    }
}
