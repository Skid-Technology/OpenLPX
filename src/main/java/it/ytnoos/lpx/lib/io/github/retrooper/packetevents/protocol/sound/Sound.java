package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface Sound extends MappedEntity, DeepComparableEntity {

    NbtCodec<Sound> CODEC =
            new NbtCodec<Sound>() {

                public Sound decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    if (nBT instanceof NBTString) {
                        return Sounds.getByNameOrCreate(((NBTString) nBT).getValue());
                    }
                    NBTCompound nBTCompound;
                    ResourceLocation orThrow =
                            (nBTCompound = nBT.castOrThrow(NBTCompound.class))
                                    .getOrThrow("sound_id", ResourceLocation.CODEC, packetWrapper);
                    Float orNull = nBTCompound.getOrNull("range", NbtCodecs.FLOAT, packetWrapper);
                    return new StaticSound(orThrow, orNull);
                }

                public NBT encode(PacketWrapper<?> packetWrapper, Sound sound) {
                    if (sound.isRegistered()) {
                        return new NBTString(sound.getName().toString());
                    }
                    NBTCompound nBTCompound;
                    (nBTCompound = new NBTCompound())
                            .set(
                                    "sound_id",
                                    sound.getSoundId(),
                                    ResourceLocation.CODEC,
                                    packetWrapper);
                    if (sound.getRange() != null) {
                        nBTCompound.set("range", sound.getRange(), NbtCodecs.FLOAT, packetWrapper);
                    }
                    return nBTCompound;
                }
            };

    ResourceLocation getSoundId();

    @Nullable Float getRange();

    static Sound read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntityOrDirect(Sounds::getById, Sound::readDirect);
    }

    static Sound readDirect(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation = packetWrapper.readIdentifier();
        Float value = packetWrapper.readOptional(PacketWrapper::readFloat);
        return new StaticSound(resourceLocation, value);
    }

    static void write(PacketWrapper<?> packetWrapper, Sound sound) {
        packetWrapper.writeMappedEntityOrDirect(sound, Sound::writeDirect);
    }

    static void writeDirect(PacketWrapper<?> packetWrapper, Sound sound) {
        packetWrapper.writeIdentifier(sound.getSoundId());
        packetWrapper.writeOptional(sound.getRange(), PacketWrapper::writeFloat);
    }

    @Deprecated
    static Sound decode(NBT nBT, ClientVersion clientVersion) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion));
    }

    @Deprecated
    static Sound decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        return CODEC.decode(nBT, packetWrapper);
    }

    @Deprecated
    static NBT encode(Sound sound, ClientVersion clientVersion) {
        return encode(PacketWrapper.createDummyWrapper(clientVersion), sound);
    }

    @Deprecated
    static NBT encode(PacketWrapper<?> packetWrapper, Sound sound) {
        return CODEC.encode(packetWrapper, sound);
    }
}
