package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface CowSoundVariant
        extends MappedEntity, CopyableEntity<CowSoundVariant>, DeepComparableEntity {

    NbtCodec<CowSoundVariant> CODEC =
            CowSoundSet.CODEC.apply(StaticCowSoundVariant::new, CowSoundVariant::getSounds);

    static CowSoundVariant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(CowSoundVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, CowSoundVariant cowSoundVariant) {
        packetWrapper.writeMappedEntity(cowSoundVariant);
    }

    CowSoundSet getSounds();
}
