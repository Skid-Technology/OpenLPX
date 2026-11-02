package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.key.KeyPattern;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public final class WolfAssetSet {

    public static final NbtMapCodec<WolfAssetSet> MAP_CODEC =
            new NbtMapCodec<WolfAssetSet>() {

                public WolfAssetSet decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    String text =
                            packetWrapper
                                            .getServerVersion()
                                            .isNewerThanOrEquals(ServerVersion.V_1_21_5)
                                    ? ""
                                    : "_texture";
                    ResourceLocation orThrow =
                            nBTCompound.getOrThrow(
                                    "wild" + text, ResourceLocation.CODEC, packetWrapper);
                    ResourceLocation orThrow2 =
                            nBTCompound.getOrThrow(
                                    "tame" + text, ResourceLocation.CODEC, packetWrapper);
                    ResourceLocation orThrow3 =
                            nBTCompound.getOrThrow(
                                    "angry" + text, ResourceLocation.CODEC, packetWrapper);
                    return new WolfAssetSet(orThrow, orThrow2, orThrow3);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        WolfAssetSet wolfAssetSet)
                        throws NbtCodecException {
                    String text =
                            packetWrapper
                                            .getServerVersion()
                                            .isNewerThanOrEquals(ServerVersion.V_1_21_5)
                                    ? ""
                                    : "_texture";
                    nBTCompound.set(
                            "wild" + text,
                            wolfAssetSet.getWildId(),
                            ResourceLocation.CODEC,
                            packetWrapper);
                    nBTCompound.set(
                            "tame" + text,
                            wolfAssetSet.getTameId(),
                            ResourceLocation.CODEC,
                            packetWrapper);
                    nBTCompound.set(
                            "angry" + text,
                            wolfAssetSet.getAngryId(),
                            ResourceLocation.CODEC,
                            packetWrapper);
                }
            };

    public static final NbtCodec<WolfAssetSet> CODEC = MAP_CODEC.codec();

    private final ResourceLocation wildId;

    private final ResourceLocation tameId;

    private final ResourceLocation angryId;

    public WolfAssetSet(
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2,
            ResourceLocation resourceLocation3) {
        this.wildId = resourceLocation;
        this.tameId = resourceLocation2;
        this.angryId = resourceLocation3;
    }

    @Internal
    public static WolfAssetSet getOrThrow(
            @KeyPattern.Namespace String text,
            @KeyPattern.Value String text2,
            @KeyPattern.Value String text3) {
        return new WolfAssetSet(
                new ResourceLocation(text, text2 + text3),
                new ResourceLocation(text, text2 + "_tame" + text3),
                new ResourceLocation(text, text2 + "_angry" + text3));
    }

    public ResourceLocation getWildId() {
        return this.wildId;
    }

    public ResourceLocation getTameId() {
        return this.tameId;
    }

    public ResourceLocation getAngryId() {
        return this.angryId;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof WolfAssetSet)) {
            return false;
        } else {
            WolfAssetSet wolfAssetSet = (WolfAssetSet) result;
            if (!this.wildId.equals(wolfAssetSet.wildId)) {
                return false;
            } else {
                return !this.tameId.equals(wolfAssetSet.tameId)
                        ? false
                        : this.angryId.equals(wolfAssetSet.angryId);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.wildId, this.tameId, this.angryId);
    }
}
