package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

@NullMarked
public final class AttributeModifier {

    public static final NbtMapCodec<AttributeModifier> MAP_CODEC =
            new NbtMapCodec<AttributeModifier>() {

                public AttributeModifier decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21)) {
                        throw new NbtCodecException(
                                packetWrapper.getServerVersion() + " is not supported");
                    }
                    ResourceLocation orThrow =
                            nBTCompound.getOrThrow("id", ResourceLocation.CODEC, packetWrapper);
                    double value = nBTCompound.getNumberTagValueOrThrow("amount").doubleValue();
                    AttributeOperation orThrow2 =
                            nBTCompound.getOrThrow(
                                    "operation", AttributeOperation.CODEC, packetWrapper);
                    return new AttributeModifier(orThrow, value, orThrow2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        AttributeModifier attributeModifier)
                        throws NbtCodecException {
                    if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21)) {
                        throw new NbtCodecException(
                                packetWrapper.getServerVersion() + " is not supported");
                    }
                    nBTCompound.set(
                            "id", attributeModifier.id, ResourceLocation.CODEC, packetWrapper);
                    nBTCompound.setTag("amount", new NBTDouble(attributeModifier.amount));
                    nBTCompound.set(
                            "operation",
                            attributeModifier.operation,
                            AttributeOperation.CODEC,
                            packetWrapper);
                }
            };

    @Obsolete private final UUID uniqueId;

    private final ResourceLocation id;

    private final double amount;

    private final AttributeOperation operation;

    @Obsolete
    public AttributeModifier(UUID uniqueId, double amount, AttributeOperation attributeOperation) {
        this.uniqueId = uniqueId;
        this.id = new ResourceLocation(uniqueId.toString());
        this.amount = amount;
        this.operation = attributeOperation;
    }

    public AttributeModifier(
            ResourceLocation resourceLocation,
            double amount,
            AttributeOperation attributeOperation) {
        this.uniqueId =
                WrapperPlayServerUpdateAttributes.PropertyModifier.generateSemiUniqueId(
                        resourceLocation);
        this.id = resourceLocation;
        this.amount = amount;
        this.operation = attributeOperation;
    }

    public static AttributeModifier read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21)) {
            UUID uUID = packetWrapper.readUUID();
            double value = packetWrapper.readDouble();
            AttributeOperation attributeOperation =
                    packetWrapper.readEnum(AttributeOperation.values());
            return new AttributeModifier(uUID, value, attributeOperation);
        } else {
            ResourceLocation resourceLocation = ResourceLocation.read(packetWrapper);
            double value2 = packetWrapper.readDouble();
            AttributeOperation attributeOperation2 =
                    packetWrapper.readEnum(AttributeOperation.values());
            return new AttributeModifier(resourceLocation, value2, attributeOperation2);
        }
    }

    public static void write(PacketWrapper<?> packetWrapper, AttributeModifier attributeModifier) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21)) {
            packetWrapper.writeUUID(attributeModifier.uniqueId);
        } else {
            ResourceLocation.write(packetWrapper, attributeModifier.id);
        }
        packetWrapper.writeDouble(attributeModifier.amount);
        packetWrapper.writeEnum(attributeModifier.operation);
    }

    @Obsolete
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public double getAmount() {
        return this.amount;
    }

    public AttributeOperation getOperation() {
        return this.operation;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        } else {
            AttributeModifier attributeModifier;
            if (Double.compare((attributeModifier = (AttributeModifier) result).amount, this.amount)
                    != 0) {
                return false;
            } else {
                return !this.id.equals(attributeModifier.id)
                        ? false
                        : this.operation == attributeModifier.operation;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.amount, this.operation);
    }
}
