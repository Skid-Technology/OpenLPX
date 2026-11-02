package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.banner;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface BannerPattern
        extends MappedEntity, CopyableEntity<BannerPattern>, DeepComparableEntity {

    ResourceLocation getAssetId();

    String getTranslationKey();

    static BannerPattern read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntityOrDirect(
                BannerPatterns.getRegistry(), BannerPattern::readDirect);
    }

    static void write(PacketWrapper<?> packetWrapper, BannerPattern bannerPattern) {
        packetWrapper.writeMappedEntityOrDirect(bannerPattern, BannerPattern::writeDirect);
    }

    static BannerPattern readDirect(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation = packetWrapper.readIdentifier();
        String text = packetWrapper.readString();
        return new StaticBannerPattern(resourceLocation, text);
    }

    static void writeDirect(PacketWrapper<?> packetWrapper, BannerPattern bannerPattern) {
        packetWrapper.writeIdentifier(bannerPattern.getAssetId());
        packetWrapper.writeString(bannerPattern.getTranslationKey());
    }

    static BannerPattern decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        NBTCompound nBTCompound = (NBTCompound) nBT;
        ResourceLocation resourceLocation =
                new ResourceLocation(nBTCompound.getStringTagValueOrThrow("asset_id"));
        String stringTagValueOrThrow = nBTCompound.getStringTagValueOrThrow("translation_key");
        return new StaticBannerPattern(typesBuilderData, resourceLocation, stringTagValueOrThrow);
    }

    static NBT encode(BannerPattern bannerPattern, ClientVersion clientVersion) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .setTag("asset_id", new NBTString(bannerPattern.getAssetId().toString()));
        nBTCompound.setTag("translation_key", new NBTString(bannerPattern.getTranslationKey()));
        return nBTCompound;
    }
}
