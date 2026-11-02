package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ChickenSoundVariant
        extends MappedEntity, CopyableEntity<ChickenSoundVariant>, DeepComparableEntity {

    NbtCodec<ChickenSoundVariant> CODEC =
            (new NbtMapCodec<ChickenSoundVariant>() {

                        public ChickenSoundVariant decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            ChickenSoundSet orThrow =
                                    nBTCompound.getOrThrow(
                                            "adult_sounds", ChickenSoundSet.CODEC, packetWrapper);
                            ChickenSoundSet orThrow2 =
                                    nBTCompound.getOrThrow(
                                            "baby_sounds", ChickenSoundSet.CODEC, packetWrapper);
                            return new StaticChickenSoundVariant(orThrow, orThrow2);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                ChickenSoundVariant chickenSoundVariant)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "adult_sounds",
                                    chickenSoundVariant.getAdultSounds(),
                                    ChickenSoundSet.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "baby_sounds",
                                    chickenSoundVariant.getBabySounds(),
                                    ChickenSoundSet.CODEC,
                                    packetWrapper);
                        }
                    })
                    .codec();

    static ChickenSoundVariant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(ChickenSoundVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, ChickenSoundVariant chickenSoundVariant) {
        packetWrapper.writeMappedEntity(chickenSoundVariant);
    }

    ChickenSoundSet getAdultSounds();

    ChickenSoundSet getBabySounds();
}
