package it.ytnoos.lpx.lib.kyori.adventure.bossbar;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

@Internal
public interface BossBarImplementation {

    @Internal
    @NotNull
    static <I extends BossBarImplementation> I get(
            @NotNull BossBar bossBar, @NotNull Class<I> classValue) {
        return BossBarImpl.ImplementationAccessor.get(bossBar, classValue);
    }

    @Internal
    @NotNull
    default Iterable<? extends BossBarViewer> viewers() {
        return Collections.emptyList();
    }

    @Internal
    interface Provider {

        @Internal
        @NotNull
        BossBarImplementation create(@NotNull BossBar bossBar);
    }
}
