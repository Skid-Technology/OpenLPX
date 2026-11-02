package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ItemStackCodec {

    public static final NbtCodec<ItemStack> INLINE_CODEC =
            ItemTypes.getCodec()
                    .apply(result -> ItemStack.builder().type(result).build(), ItemStack::getType);

    public static final NbtMapCodec<ItemStack> TEMPLATE_MAP_CODEC =
            new NbtMapCodec<ItemStack>() {

                public ItemStack decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    return ItemStack.builder()
                            .type(nBTCompound.getOrThrow("id", ItemTypes.getCodec(), packetWrapper))
                            .amount(nBTCompound.getOr("count", NbtCodecs.INT, 1, packetWrapper))
                            .build();
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        ItemStack itemStack)
                        throws NbtCodecException {
                    nBTCompound.setTag(
                            "id", new NBTString(itemStack.getType().getName().toString()));
                    if (itemStack.getAmount() != 1) {
                        nBTCompound.setTag("count", new NBTInt(itemStack.getAmount()));
                    }
                }
            };

    public static final NbtCodec<ItemStack> TEMPLATE_CODEC =
            TEMPLATE_MAP_CODEC.codec().withAlternative(INLINE_CODEC);

    public static final NbtMapCodec<ItemStack> MAP_CODEC =
            new NbtMapCodec<ItemStack>() {

                public ItemStack decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    boolean newerThanOrEquals =
                            packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_20_5);
                    return ItemStack.builder()
                            .type(nBTCompound.getOrThrow("id", ItemTypes.getCodec(), packetWrapper))
                            .amount(
                                    nBTCompound.getOr(
                                            newerThanOrEquals ? "count" : "Count",
                                            NbtCodecs.INT,
                                            1,
                                            packetWrapper))
                            .nbt(nBTCompound.getCompoundTagOrNull("tag"))
                            .build();
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        ItemStack itemStack)
                        throws NbtCodecException {
                    boolean newerThanOrEquals =
                            packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_20_5);
                    nBTCompound.setTag(
                            "id", new NBTString(itemStack.getType().getName().toString()));
                    if (!newerThanOrEquals || itemStack.getAmount() != 1) {
                        nBTCompound.setTag(
                                newerThanOrEquals ? "count" : "Count",
                                new NBTInt(itemStack.getAmount()));
                    }
                    if (!newerThanOrEquals && itemStack.getNBT() != null) {
                        nBTCompound.setTag("tag", itemStack.getNBT());
                    }
                }
            };

    public static final NbtCodec<ItemStack> CODEC = MAP_CODEC.codec();

    private ItemStackCodec() {}
}
