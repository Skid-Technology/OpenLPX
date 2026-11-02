package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.logger.ComponentLogManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.logger.JulLegacyLogManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.logger.Slf4jLogManager;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.logging.Level;

@NullMarked
@NonExtendable
public abstract class LogManager {

    protected static final String LOGGER_NAME = "packetevents";

    protected final PacketEventsAPI<?> packetevents;

    public LogManager(PacketEventsAPI<?> packetEventsAPI) {
        this.packetevents = packetEventsAPI;
    }

    @Deprecated
    public LogManager() {
        this(PacketEvents.getAPI());
    }

    private static String stripLegacyColors(String text) {
        int value;
        while ((value = text.indexOf(167)) != -1 && text.length() >= value + 2) {
            text = text.substring(0, value) + text.substring(value + 2);
        }
        return text;
    }

    @Internal
    public static LogManager construct(PacketEventsAPI<?> packetEventsAPI) {
        if (ComponentLogManager.exists()) {
            return new ComponentLogManager(packetEventsAPI);
        } else {
            return Slf4jLogManager.exists()
                    ? new Slf4jLogManager(packetEventsAPI)
                    : new JulLegacyLogManager(packetEventsAPI);
        }
    }

    protected void log(Level level, @Nullable NamedTextColor namedTextColor, String text) {
        this.log(level, namedTextColor, text, null);
    }

    protected void log(
            Level level,
            @Nullable NamedTextColor namedTextColor,
            String text2,
            @Nullable Throwable throwable) {
        ComponentLike componentLike = Component.text(stripLegacyColors(text2));
        if (namedTextColor != null) {
            componentLike = Component.text().append(componentLike).color(namedTextColor);
        }
        this.log(level, componentLike, throwable);
    }

    public abstract void log(
            Level level, ComponentLike componentLike, @Nullable Throwable throwable);

    public void info(String text) {
        this.log(Level.INFO, null, text);
    }

    public void warn(String text) {
        this.log(Level.WARNING, NamedTextColor.YELLOW, text);
    }

    public void warn(String text, @Nullable Throwable throwable) {
        this.log(Level.WARNING, NamedTextColor.YELLOW, text, throwable);
    }

    public void severe(String text) {
        this.log(Level.SEVERE, NamedTextColor.RED, text);
    }

    public void severe(String text, @Nullable Throwable throwable) {
        this.log(Level.SEVERE, NamedTextColor.RED, text, throwable);
    }

    public void packetException(String text, Throwable throwable) {
        if (PacketEvents.getAPI().getSettings().getPacketExceptionHandler() != null) {
            PacketEvents.getAPI().getSettings().getPacketExceptionHandler().accept(text, throwable);
        } else if (PacketEvents.getAPI().getSettings().isFullStackTraceEnabled()) {
            PacketEvents.getAPI().getLogger().log(Level.WARNING, text, throwable);
        } else {
            this.warn(text);
        }
    }

    public void debug(String text) {
        if (this.isDebug()) {
            this.log(Level.FINE, NamedTextColor.GRAY, text);
        }
    }

    public boolean isDebug() {
        return this.packetevents.getSettings().isDebugEnabled();
    }
}
