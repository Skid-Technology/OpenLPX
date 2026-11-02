package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTEnd;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.NbtTagHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class CustomClickEvent implements ClickEvent {

    private final ResourceLocation id;

    @Nullable private final NBT payload;

    public CustomClickEvent(ResourceLocation resourceLocation, @Nullable NBT nBT) {
        this.id = resourceLocation;
        this.payload = nBT;
    }

    public static CustomClickEvent decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        ResourceLocation orThrow =
                nBTCompound.getOrThrow("id", ResourceLocation::decode, packetWrapper);
        NBT tagOrNull = nBTCompound.getTagOrNull("payload");
        return new CustomClickEvent(orThrow, tagOrNull);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            CustomClickEvent customClickEvent) {
        nBTCompound.set("id", customClickEvent.id, ResourceLocation::encode, packetWrapper);
        if (customClickEvent.payload != null) {
            nBTCompound.setTag("payload", customClickEvent.payload);
        }
    }

    @Override
    public ClickEventAction<?> getAction() {
        return ClickEventActions.CUSTOM;
    }

    @Override
    public it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent asAdventure() {
        return it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent.custom(
                this.id.key(),
                new NbtTagHolder(this.payload != null ? this.payload : NBTEnd.INSTANCE));
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Nullable
    public NBT getPayload() {
        return this.payload;
    }
}
