package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.legacy.LegacyComponentSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.LogManager;
import it.ytnoos.lpx.lib.kyori.adventure.audience.Audience;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.logging.Level;

@NullMarked
@Internal
public final class BukkitLogManager extends LogManager {

    private static final Component PREFIX =
            Component.text("[packetevents] ").color(NamedTextColor.AQUA);

    public BukkitLogManager(PacketEventsAPI<?> packetEventsAPI) {
        super(packetEventsAPI);
    }

    @Override
    public void log(Level level, ComponentLike componentLike, @Nullable Throwable throwable) {
        ComponentBuilder componentBuilder = Component.text().append(PREFIX).append(componentLike);
        ConsoleCommandSender consoleCommandSender;
        if ((consoleCommandSender = Bukkit.getConsoleSender()) instanceof Audience) {
            consoleCommandSender.sendMessage(componentBuilder);
        } else {
            consoleCommandSender.sendMessage(
                    LegacyComponentSerializer.legacySection()
                            .serialize(componentBuilder.asComponent()));
        }
        if (throwable != null) {
            Bukkit.getLogger().log(level, "", throwable);
        }
    }
}
