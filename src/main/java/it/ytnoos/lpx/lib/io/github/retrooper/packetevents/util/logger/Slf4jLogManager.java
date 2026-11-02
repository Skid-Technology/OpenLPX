package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.logger;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.LogManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.Reflection;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

@NullMarked
@Internal
public final class Slf4jLogManager extends LogManager {

    private static final boolean EXISTS =
            Reflection.getClassByNameWithoutException("org.slf4j.Logger") != null;

    private final Logger logger = LoggerFactory.getLogger("packetevents");

    public Slf4jLogManager(PacketEventsAPI<?> packetEventsAPI) {
        super(packetEventsAPI);
    }

    public static boolean exists() {
        return EXISTS;
    }

    @Override
    public void log(Level level, ComponentLike componentLike, @Nullable Throwable throwable) {
        String text = AdventureSerializer.stringify(componentLike.asComponent());
        if (level == Level.FINEST || level == Level.FINER) {
            this.logger.trace(text, throwable);
        } else if (level == Level.FINE) {
            this.logger.debug(text, throwable);
        } else if (level == Level.INFO) {
            this.logger.info(text, throwable);
        } else if (level == Level.WARNING) {
            this.logger.warn(text, throwable);
        } else {
            if (level != Level.SEVERE) {
                throw new UnsupportedOperationException(
                        level + " is unsupported (" + componentLike + ")");
            }
            this.logger.error(text, throwable);
        }
    }
}
