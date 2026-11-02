package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.logger;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.LogManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.Reflection;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.logger.slf4j.ComponentLogger;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.logging.Level;

@NullMarked
@Internal
public final class ComponentLogManager extends LogManager {

    private static final boolean EXISTS =
            Reflection.getClassByNameWithoutException(
                            "it.ytnoos.lpx.lib.kyori.adventure.text.logger.slf4j.ComponentLogger")
                    != null;

    private final ComponentLogger logger = ComponentLogger.logger("packetevents");

    public ComponentLogManager(PacketEventsAPI<?> packetEventsAPI) {
        super(packetEventsAPI);
    }

    public static boolean exists() {
        return EXISTS;
    }

    @Override
    public void log(Level level, ComponentLike componentLike, @Nullable Throwable throwable) {
        Component component = componentLike.asComponent();
        if (level == Level.FINEST || level == Level.FINER) {
            this.logger.trace(component, throwable);
        } else if (level == Level.FINE) {
            this.logger.debug(component, throwable);
        } else if (level == Level.INFO) {
            this.logger.info(component, throwable);
        } else if (level == Level.WARNING) {
            if (throwable != null) {
                this.logger.warn(component, throwable);
            } else {
                this.logger.warn(component);
            }
        } else {
            if (level != Level.SEVERE) {
                throw new UnsupportedOperationException(
                        level + " is unsupported (" + componentLike + ")");
            }
            if (throwable != null) {
                this.logger.error(component, throwable);
            } else {
                this.logger.error(component);
            }
        }
    }
}
