package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.UnpooledByteBufAllocationHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.GlobalRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.util.Map;
import java.util.WeakHashMap;

@NullMarked
public interface ComponentValueRef<T> {

    @Internal
    static <T> ComponentValueRef<T> ofBytes(
            ComponentType<T> componentType, byte[] values, ClientVersion clientVersion) {
        ServerVersion serverVersion = clientVersion.toServerVersion();
        return asCached(
                result -> {
                    Object result2 = UnpooledByteBufAllocationHelper.wrappedBuffer(values);
                    try {
                        PacketWrapper packetWrapper;
                        (packetWrapper =
                                        PacketWrapper.createUniversalPacketWrapper(
                                                result2, serverVersion))
                                .setRegistryHolder(result);
                        return (T) ((ComponentType) componentType).read(packetWrapper);
                    } finally {
                        ByteBufHelper.release(result2);
                    }
                });
    }

    static <T> ComponentValueRef<T> ofStatic(T t) {
        return result -> (T) ((Object) t);
    }

    static <T> ComponentValueRef<T> asCached(final ComponentValueRef<T> componentValueRef) {
        return new ComponentValueRef<T>() {

            private final Map<IRegistryHolder, T> values = new WeakHashMap<>();

            @Override
            public synchronized T resolve(IRegistryHolder registryHolder) {
                return this.values.computeIfAbsent(registryHolder, componentValueRef::resolve);
            }
        };
    }

    @Deprecated
    default T resolveUsingGlobals() {
        return this.resolve(GlobalRegistryHolder.INSTANCE);
    }

    T resolve(IRegistryHolder registryHolder);
}
