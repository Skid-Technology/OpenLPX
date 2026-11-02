package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.action;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class DynamicCustomAction implements Action {

    private final ResourceLocation id;

    @Nullable private final NBTCompound additions;

    public DynamicCustomAction(
            ResourceLocation resourceLocation, @Nullable NBTCompound nBTCompound) {
        this.id = resourceLocation;
        this.additions = nBTCompound;
    }

    public static DynamicCustomAction decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        ResourceLocation orThrow =
                nBTCompound.getOrThrow("id", ResourceLocation::decode, packetWrapper);
        NBTCompound compoundTagOrNull = nBTCompound.getCompoundTagOrNull("additions");
        return new DynamicCustomAction(orThrow, compoundTagOrNull);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            DynamicCustomAction dynamicCustomAction) {
        nBTCompound.set("id", dynamicCustomAction.id, ResourceLocation::encode, packetWrapper);
        if (dynamicCustomAction.additions != null) {
            nBTCompound.setTag("additions", dynamicCustomAction.additions);
        }
    }

    @Override
    public ActionType<?> getType() {
        return ActionTypes.DYNAMIC_CUSTOM;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Nullable
    public NBTCompound getAdditions() {
        return this.additions;
    }
}
