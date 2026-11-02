package it.ytnoos.lpx.lib.kyori.adventure.resource;

import it.ytnoos.lpx.lib.kyori.adventure.audience.Audience;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface ResourcePackCallback {

    @NotNull
    static ResourcePackCallback noOp() {
        return ResourcePackCallbacks.NO_OP;
    }

    @NotNull
    static ResourcePackCallback onTerminal(
            @NotNull BiConsumer<UUID, Audience> biConsumer,
            @NotNull BiConsumer<UUID, Audience> biConsumer2) {
        return (result, result2, result3) -> {
            if (result2 == ResourcePackStatus.SUCCESSFULLY_LOADED) {
                ((BiConsumer) biConsumer).accept(result, result3);
            } else if (!result2.intermediate()) {
                ((BiConsumer) biConsumer2).accept(result, result3);
            }
        };
    }

    void packEventReceived(
            @NotNull UUID uUID,
            @NotNull ResourcePackStatus resourcePackStatus,
            @NotNull Audience audience);
}
