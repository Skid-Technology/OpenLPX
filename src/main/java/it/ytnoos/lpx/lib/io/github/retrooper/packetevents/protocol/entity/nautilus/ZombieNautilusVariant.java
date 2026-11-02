package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.nautilus;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureIndexUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface ZombieNautilusVariant
        extends MappedEntity, CopyableEntity<ZombieNautilusVariant>, DeepComparableEntity {

    ZombieNautilusVariant.ModelType getModelType();

    ResourceLocation getAssetId();

    static ZombieNautilusVariant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(ZombieNautilusVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, ZombieNautilusVariant zombieNautilusVariant) {
        packetWrapper.writeMappedEntity(zombieNautilusVariant);
    }

    static ZombieNautilusVariant decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        NBTCompound nBTCompound;
        String text;
        ZombieNautilusVariant.ModelType modelType =
                (text = (nBTCompound = (NBTCompound) nBT).getStringTagValueOrNull("model")) != null
                        ? ZombieNautilusVariant.ModelType.getByName(text)
                        : ZombieNautilusVariant.ModelType.NORMAL;
        ResourceLocation resourceLocation =
                new ResourceLocation(nBTCompound.getStringTagValueOrThrow("asset_id"));
        return new StaticZombieNautilusVariant(typesBuilderData, modelType, resourceLocation);
    }

    static NBT encode(ZombieNautilusVariant zombieNautilusVariant, ClientVersion clientVersion) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .setTag("model", new NBTString(zombieNautilusVariant.getModelType().getName()));
        nBTCompound.setTag(
                "asset_id", new NBTString(zombieNautilusVariant.getAssetId().toString()));
        return nBTCompound;
    }

    enum ModelType {
        NORMAL("normal"),
        WARM("warm");

        private static final Index<String, ZombieNautilusVariant.ModelType> NAME_INDEX =
                Index.create(
                        ZombieNautilusVariant.ModelType.class,
                        ZombieNautilusVariant.ModelType::getName);

        private final String name;

        ModelType(String name) {
            this.name = name;
        }

        public static ZombieNautilusVariant.ModelType getByName(String text) {
            return AdventureIndexUtil.indexValueOrThrow(NAME_INDEX, text);
        }

        public String getName() {
            return this.name;
        }
    }
}
