package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class RunCommandClickEvent implements ClickEvent {

    private final String command;

    public RunCommandClickEvent(String command) {
        this.command = command;
    }

    public static RunCommandClickEvent decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        boolean newerThanOrEquals =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5);
        String stringTagValueOrThrow =
                nBTCompound.getStringTagValueOrThrow(newerThanOrEquals ? "command" : "value");
        return new RunCommandClickEvent(stringTagValueOrThrow);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            RunCommandClickEvent runCommandClickEvent) {
        boolean newerThanOrEquals =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5);
        nBTCompound.setTag(
                newerThanOrEquals ? "command" : "value",
                new NBTString(runCommandClickEvent.command));
    }

    @Override
    public ClickEventAction<?> getAction() {
        return ClickEventActions.RUN_COMMAND;
    }

    @Override
    public it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent asAdventure() {
        return it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent.runCommand(this.command);
    }

    public String getCommand() {
        return this.command;
    }
}
