package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface DebugSubscription<T> extends MappedEntity {

    T read(PacketWrapper<?> packetWrapper);

    void write(PacketWrapper<?> packetWrapper, T t);

    final class Event<T> {

        private final DebugSubscription<T> subscription;

        private final T value;

        public Event(DebugSubscription<T> debugSubscription, T t) {
            this.subscription = debugSubscription;
            this.value = (T) t;
        }

        public static DebugSubscription.Event<?> read(PacketWrapper<?> packetWrapper) {
            DebugSubscription debugSubscription;
            Object result =
                    (debugSubscription =
                                    packetWrapper.readMappedEntity(
                                            DebugSubscriptions.getRegistry()))
                            .read(packetWrapper);
            return new DebugSubscription.Event<>(debugSubscription, result);
        }

        public static <T> void write(
                PacketWrapper<?> packetWrapper, DebugSubscription.Event<T> event) {
            packetWrapper.writeMappedEntity(event.subscription);
            event.subscription.write(packetWrapper, event.value);
        }

        public DebugSubscription<T> getSubscription() {
            return this.subscription;
        }

        public T getValue() {
            return this.value;
        }
    }

    final class Update<T> {

        private final DebugSubscription<T> subscription;

        @Nullable private final T value;

        public Update(DebugSubscription<T> debugSubscription, @Nullable T t) {
            this.subscription = debugSubscription;
            this.value = (T) t;
        }

        public static DebugSubscription.Update<?> read(PacketWrapper<?> packetWrapper) {
            DebugSubscription debugSubscription =
                    packetWrapper.readMappedEntity(DebugSubscriptions.getRegistry());
            Object result = packetWrapper.readOptional(debugSubscription::read);
            return new DebugSubscription.Update<>(debugSubscription, result);
        }

        public static <T> void write(
                PacketWrapper<?> packetWrapper, DebugSubscription.Update<T> update) {
            packetWrapper.writeMappedEntity(update.subscription);
            DebugSubscription debugSubscription = update.subscription;
            packetWrapper.writeOptional(update.value, debugSubscription::write);
        }

        public DebugSubscription<T> getSubscription() {
            return this.subscription;
        }

        @Nullable
        public T getValue() {
            return this.value;
        }
    }
}
