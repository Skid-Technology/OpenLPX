package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.logger;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.legacy.LegacyComponentSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.LogManager;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

@NullMarked
@Internal
public final class JulLegacyLogManager extends LogManager {

    private static final Logger LOGGER = Logger.getLogger("packetevents");

    public JulLegacyLogManager(PacketEventsAPI<?> packetEventsAPI) {
        super(packetEventsAPI);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    @Override
    public void log(Level level, ComponentLike componentLike, @Nullable Throwable throwable) {
        LOGGER.log(
                level,
                LegacyComponentSerializer.legacySection().serialize(componentLike.asComponent()),
                throwable);
    }
}
