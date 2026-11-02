package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ParticleItemStackData extends ParticleData implements LegacyConvertible {

    private static final NbtCodec<ItemStack> PRE_26_1_ITEM_CODEC =
            ItemStackCodec.CODEC.withAlternative(ItemStackCodec.INLINE_CODEC);

    private ItemStack itemStack;

    public ParticleItemStackData(ItemStack itemStack2) {
        this.itemStack = itemStack2;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(ItemStack itemStack2) {
        this.itemStack = itemStack2;
    }

    public static ParticleItemStackData read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_13)
                ? new ParticleItemStackData(ItemStackSerialization.readTemplate(packetWrapper))
                : new ParticleItemStackData(
                        ItemStack.builder()
                                .type(
                                        ItemTypes.getRegistry()
                                                .getByIdOrThrow(
                                                        packetWrapper.getClientVersion(),
                                                        packetWrapper.readVarInt()))
                                .wrapper(packetWrapper)
                                .build());
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ParticleItemStackData particleItemStackData) {
        ItemStackSerialization.writeTemplate(packetWrapper, particleItemStackData.getItemStack());
    }

    public static ParticleItemStackData decode(
            NBTCompound nBTCompound, ClientVersion clientVersion) {
        String text = clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5) ? "item" : "value";
        NbtCodec<ItemStack> nbtCodec =
                clientVersion.isNewerThanOrEquals(ClientVersion.V_26_1)
                        ? ItemStackCodec.TEMPLATE_CODEC
                        : PRE_26_1_ITEM_CODEC;
        ItemStack itemStack =
                ((ItemStack)
                        nBTCompound.getOrThrow(
                                text, nbtCodec, PacketWrapper.createDummyWrapper(clientVersion)));
        return new ParticleItemStackData(itemStack);
    }

    public static void encode(
            ParticleItemStackData particleItemStackData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        String text = clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5) ? "item" : "value";
        NbtCodec<ItemStack> nbtCodec =
                clientVersion.isNewerThanOrEquals(ClientVersion.V_26_1)
                        ? ItemStackCodec.TEMPLATE_CODEC
                        : PRE_26_1_ITEM_CODEC;
        nBTCompound.set(
                text,
                particleItemStackData.itemStack,
                nbtCodec,
                PacketWrapper.createDummyWrapper(clientVersion));
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public LegacyParticleData toLegacy(ClientVersion clientVersion) {
        return LegacyParticleData.ofTwo(
                this.itemStack.getType().getId(clientVersion), this.itemStack.getLegacyData());
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ParticleItemStackData particleItemStackData = (ParticleItemStackData) result;
            return this.itemStack.equals(particleItemStackData.itemStack);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.itemStack);
    }
}
