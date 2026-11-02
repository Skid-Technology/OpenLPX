package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig;

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
public interface PigSoundVariant
        extends MappedEntity, CopyableEntity<PigSoundVariant>, DeepComparableEntity {

    NbtCodec<PigSoundVariant> CODEC =
            (new NbtMapCodec<PigSoundVariant>() {

                        public PigSoundVariant decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            PigSoundSet orThrow =
                                    nBTCompound.getOrThrow(
                                            "adult_sounds", PigSoundSet.CODEC, packetWrapper);
                            PigSoundSet orThrow2 =
                                    nBTCompound.getOrThrow(
                                            "baby_sounds", PigSoundSet.CODEC, packetWrapper);
                            return new StaticPigSoundVariant(orThrow, orThrow2);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                PigSoundVariant pigSoundVariant)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "adult_sounds",
                                    pigSoundVariant.getAdultSounds(),
                                    PigSoundSet.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "baby_sounds",
                                    pigSoundVariant.getBabySounds(),
                                    PigSoundSet.CODEC,
                                    packetWrapper);
                        }
                    })
                    .codec();

    static PigSoundVariant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(PigSoundVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, PigSoundVariant pigSoundVariant) {
        packetWrapper.writeMappedEntity(pigSoundVariant);
    }

    PigSoundSet getAdultSounds();

    PigSoundSet getBabySounds();
}
