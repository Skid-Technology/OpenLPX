package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.numbers;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
@NonExtendable
public interface ResolvableFloat {

    static ResolvableFloat.Constant constantFloat(float value) {
        return new ResolvableFloat.Constant(value);
    }

    static ResolvableFloat.Reference referenceFloat(ResourceLocation resourceLocation) {
        return new ResolvableFloat.Reference(resourceLocation);
    }

    static ResolvableFloat read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readBoolean()
                ? ResolvableFloat.Constant.read(packetWrapper)
                : ResolvableFloat.Reference.read(packetWrapper);
    }

    static void write(PacketWrapper<?> packetWrapper, ResolvableFloat resolvableFloat) {
        if (resolvableFloat instanceof ResolvableFloat.Constant) {
            packetWrapper.writeBoolean(true);
            ResolvableFloat.Constant.write(
                    packetWrapper, (ResolvableFloat.Constant) resolvableFloat);
        } else {
            if (!(resolvableFloat instanceof ResolvableFloat.Reference)) {
                throw new UnsupportedOperationException(
                        "Unsupported ResolvableFloat implementation: " + resolvableFloat);
            }
            packetWrapper.writeBoolean(false);
            ResolvableFloat.Reference.write(
                    packetWrapper, (ResolvableFloat.Reference) resolvableFloat);
        }
    }

    final class Constant implements ResolvableFloat {

        private final float value;

        public Constant(float value) {
            this.value = value;
        }

        public static ResolvableFloat.Constant read(PacketWrapper<?> packetWrapper) {
            float value = packetWrapper.readFloat();
            return new ResolvableFloat.Constant(value);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, ResolvableFloat.Constant constant) {
            packetWrapper.writeFloat(constant.value);
        }

        public float getValue() {
            return this.value;
        }

        @Override
        public boolean equals(@Nullable Object result) {
            return result != null && this.getClass() == result.getClass()
                    ? Float.compare(((ResolvableFloat.Constant) result).value, this.value) == 0
                    : false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.value);
        }
    }

    final class Reference implements ResolvableFloat {

        private final ResourceLocation location;

        public Reference(ResourceLocation resourceLocation) {
            this.location = resourceLocation;
        }

        public static ResolvableFloat.Reference read(PacketWrapper<?> packetWrapper) {
            ResourceLocation resourceLocation = ResourceLocation.read(packetWrapper);
            return new ResolvableFloat.Reference(resourceLocation);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, ResolvableFloat.Reference reference) {
            ResourceLocation.write(packetWrapper, reference.location);
        }

        public ResourceLocation getLocation() {
            return this.location;
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (result != null && this.getClass() == result.getClass()) {
                ResolvableFloat.Reference reference = (ResolvableFloat.Reference) result;
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
