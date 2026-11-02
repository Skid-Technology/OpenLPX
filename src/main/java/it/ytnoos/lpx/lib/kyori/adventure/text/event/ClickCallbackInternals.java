package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import it.ytnoos.lpx.lib.kyori.adventure.audience.Audience;
import it.ytnoos.lpx.lib.kyori.adventure.permission.PermissionChecker;
import it.ytnoos.lpx.lib.kyori.adventure.util.Services;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.NotNull;

final class ClickCallbackInternals {

    static final PermissionChecker ALWAYS_FALSE = PermissionChecker.always(TriState.FALSE);

    static final ClickCallback.Provider PROVIDER =
            Services.service(ClickCallback.Provider.class)
                    .orElseGet(ClickCallbackInternals.Fallback::new);

    private ClickCallbackInternals() {}

    static final class Fallback implements ClickCallback.Provider {

        @NotNull
        @Override
        public ClickEvent create(
                @NotNull ClickCallback<Audience> clickCallback,
                ClickCallback.@NotNull Options options) {
            return ClickEvent.suggestCommand("Callbacks are not supported on this platform!");
        }
    }
}
