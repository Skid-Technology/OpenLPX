package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ChangePageClickEvent implements ClickEvent {

    private final int page;

    @Obsolete
    public ChangePageClickEvent(String text) {
        this(Integer.parseInt(text));
    }

    public ChangePageClickEvent(int page) {
        this.page = page;
    }

    public static ChangePageClickEvent decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        int value =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        ? nBTCompound.getNumberTagValueOrThrow("page").intValue()
                        : Integer.parseInt(nBTCompound.getStringTagValueOrThrow("value"));
        return new ChangePageClickEvent(value);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            ChangePageClickEvent changePageClickEvent) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            nBTCompound.setTag("page", new NBTInt(changePageClickEvent.page));
        } else {
            nBTCompound.setTag("value", new NBTString(Integer.toString(changePageClickEvent.page)));
        }
    }

    @Override
    public ClickEventAction<?> getAction() {
        return ClickEventActions.CHANGE_PAGE;
    }

    @Override
    public it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent asAdventure() {
        return it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent.changePage(this.page);
    }

    public int getPage() {
        return this.page;
    }
}
