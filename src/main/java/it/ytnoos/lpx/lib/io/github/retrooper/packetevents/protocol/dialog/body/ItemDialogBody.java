package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.body;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class ItemDialogBody implements DialogBody {

    private final ItemStack item;

    @Nullable private final PlainMessage description;

    private final boolean showDecorations;

    private final boolean showTooltip;

    private final int width;

    private final int height;

    public ItemDialogBody(
            ItemStack itemStack,
            @Nullable PlainMessage plainMessage,
            boolean showDecorations,
            boolean showTooltip,
            int width,
            int height) {
        this.item = itemStack;
        this.description = plainMessage;
        this.showDecorations = showDecorations;
        this.showTooltip = showTooltip;
        this.width = width;
        this.height = height;
    }

    public static ItemDialogBody decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        NbtCodec<ItemStack> nbtCodec =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)
                        ? ItemStackCodec.TEMPLATE_CODEC
                        : ItemStackCodec.CODEC;
        ItemStack itemStack = ((ItemStack) nBTCompound.getOrThrow("item", nbtCodec, packetWrapper));
        PlainMessage orNull =
                nBTCompound.getOrNull("description", PlainMessage::decode, packetWrapper);
        boolean booleanOr = nBTCompound.getBooleanOr("show_decorations", true);
        boolean booleanOr2 = nBTCompound.getBooleanOr("show_tooltip", true);
        int value = nBTCompound.getNumberTagValueOrDefault("width", 16).intValue();
        int value2 = nBTCompound.getNumberTagValueOrDefault("height", 16).intValue();
        return new ItemDialogBody(itemStack, orNull, booleanOr, booleanOr2, value, value2);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            ItemDialogBody itemDialogBody) {
        NbtCodec<ItemStack> nbtCodec =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)
                        ? ItemStackCodec.TEMPLATE_CODEC
                        : ItemStackCodec.CODEC;
        nBTCompound.set("item", itemDialogBody.item, nbtCodec, packetWrapper);
        if (itemDialogBody.description != null) {
            nBTCompound.set(
                    "description", itemDialogBody.description, PlainMessage::encode, packetWrapper);
        }
        if (!itemDialogBody.showDecorations) {
            nBTCompound.setTag("show_decorations", new NBTByte(false));
        }
        if (!itemDialogBody.showTooltip) {
            nBTCompound.setTag("show_tooltip", new NBTByte(false));
        }
        if (itemDialogBody.width != 16) {
            nBTCompound.setTag("width", new NBTInt(itemDialogBody.width));
        }
        if (itemDialogBody.height != 16) {
            nBTCompound.setTag("height", new NBTInt(itemDialogBody.height));
        }
    }

    @Override
    public DialogBodyType<?> getType() {
        return DialogBodyTypes.ITEM;
    }

    public ItemStack getItem() {
        return this.item;
    }

    @Nullable
    public PlainMessage getDescription() {
        return this.description;
    }

    public boolean isShowDecorations() {
        return this.showDecorations;
    }

    public boolean isShowTooltip() {
        return this.showTooltip;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
