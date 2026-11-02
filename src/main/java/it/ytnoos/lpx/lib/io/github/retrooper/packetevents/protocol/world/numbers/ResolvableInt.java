package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.numbers;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
@NonExtendable
public interface ResolvableInt {

    static ResolvableInt.Constant constantInt(int value) {
        return new ResolvableInt.Constant(value);
    }

    static ResolvableInt.Reference referenceInt(ResourceLocation resourceLocation) {
        return new ResolvableInt.Reference(resourceLocation);
    }

    static ResolvableInt read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readBoolean()
                ? ResolvableInt.Constant.read(packetWrapper)
                : ResolvableInt.Reference.read(packetWrapper);
    }

    static void write(PacketWrapper<?> packetWrapper, ResolvableInt resolvableInt) {
        if (resolvableInt instanceof ResolvableInt.Constant) {
            packetWrapper.writeBoolean(true);
            ResolvableInt.Constant.write(packetWrapper, (ResolvableInt.Constant) resolvableInt);
        } else {
            if (!(resolvableInt instanceof ResolvableInt.Reference)) {
                throw new UnsupportedOperationException(
                        "Unsupported ResolvableInt implementation: " + resolvableInt);
            }
            packetWrapper.writeBoolean(false);
            ResolvableInt.Reference.write(packetWrapper, (ResolvableInt.Reference) resolvableInt);
        }
    }

    final class Constant implements ResolvableInt {

        private final int value;

        public Constant(int value) {
            this.value = value;
        }

        public static ResolvableInt.Constant read(PacketWrapper<?> packetWrapper) {
            int value = packetWrapper.readInt();
            return new ResolvableInt.Constant(value);
        }

        public static void write(PacketWrapper<?> packetWrapper, ResolvableInt.Constant constant) {
            packetWrapper.writeInt(constant.value);
        }

        public int getValue() {
            return this.value;
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (result != null && this.getClass() == result.getClass()) {
                ResolvableInt.Constant constant = (ResolvableInt.Constant) result;
                return this.value == constant.value;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.value);
        }
    }

    final class Reference implements ResolvableInt {

        private final ResourceLocation location;

        public Reference(ResourceLocation resourceLocation) {
            this.location = resourceLocation;
        }

        public static ResolvableInt.Reference read(PacketWrapper<?> packetWrapper) {
            ResourceLocation resourceLocation = ResourceLocation.read(packetWrapper);
            return new ResolvableInt.Reference(resourceLocation);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, ResolvableInt.Reference reference) {
            ResourceLocation.write(packetWrapper, reference.location);
        }

        public ResourceLocation getLocation() {
            return this.location;
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (result != null && this.getClass() == result.getClass()) {
                ResolvableInt.Reference reference = (ResolvableInt.Reference) result;
                return this.location.equals(reference.location);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.location);
        }
    }
}
