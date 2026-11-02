package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion.ViaVersionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.PreViaSupport;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public final class PreViaPipelineSupport {

    private PreViaPipelineSupport() {}

    public static PreViaPipelineSupport.DispatchMode getDispatchMode() {
        if (!PreViaSupport.hasPreViaListeners()) {
            return PreViaPipelineSupport.DispatchMode.DISABLED;
        } else {
            return ViaVersionUtil.isAvailable()
                    ? PreViaPipelineSupport.DispatchMode.PIPELINE
                    : PreViaPipelineSupport.DispatchMode.FALLBACK;
        }
    }

    public static boolean shouldDispatchFallbackPreViaEvents(boolean matches) {
        return !matches && getDispatchMode() == PreViaPipelineSupport.DispatchMode.FALLBACK;
    }

    public enum DispatchMode {
        DISABLED,
        FALLBACK,
        PIPELINE
    }
}
