package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface WolfSoundVariant
        extends MappedEntity, CopyableEntity<WolfSoundVariant>, DeepComparableEntity {

    NbtCodec<WolfSoundVariant> CODEC =
            new NbtCodec<WolfSoundVariant>() {

                public WolfSoundVariant decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_1)) {
                        WolfSoundSet wolfSoundSet = WolfSoundSet.CODEC.decode(nBT, packetWrapper);
                        return new StaticWolfSoundVariant(wolfSoundSet, wolfSoundSet);
                    } else {
                        NBTCompound nBTCompound;
                        WolfSoundSet orThrow =
                                (nBTCompound = (NBTCompound) nBT)
                                        .getOrThrow(
                                                "adult_sounds", WolfSoundSet.CODEC, packetWrapper);
                        WolfSoundSet orThrow2 =
                                nBTCompound.getOrThrow(
                                        "baby_sounds", WolfSoundSet.CODEC, packetWrapper);
                        return new StaticWolfSoundVariant(orThrow, orThrow2);
                    }
                }

                public NBT encode(PacketWrapper<?> packetWrapper, WolfSoundVariant wolfSoundVariant)
                        throws NbtCodecException {
                    if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_1)) {
                        return WolfSoundSet.CODEC.encode(
                                packetWrapper, wolfSoundVariant.getAdultSounds());
                    }
                    NBTCompound nBTCompound;
                    (nBTCompound = new NBTCompound())
                            .set(
                                    "adult_sounds",
                                    wolfSoundVariant.getAdultSounds(),
                                    WolfSoundSet.CODEC,
                                    packetWrapper);
                    nBTCompound.set(
                            "baby_sounds",
                            wolfSoundVariant.getBabySounds(),
                            WolfSoundSet.CODEC,
                            packetWrapper);
                    return nBTCompound;
                }
            };

    WolfSoundSet getAdultSounds();

    WolfSoundSet getBabySounds();

    @Deprecated
    default Sound getAmbientSound() {
        return this.getAdultSounds().getAmbientSound();
    }

    @Deprecated
    default Sound getDeathSound() {
        return this.getAdultSounds().getDeathSound();
    }

    @Deprecated
    default Sound getGrowlSound() {
        return this.getAdultSounds().getGrowlSound();
    }

    @Deprecated
    default Sound getHurtSound() {
        return this.getAdultSounds().getHurtSound();
    }

    @Deprecated
    default Sound getPantSound() {
        return this.getAdultSounds().getPantSound();
    }

    @Deprecated
    default Sound getWhineSound() {
        return this.getAdultSounds().getWhineSound();
    }

    static WolfSoundVariant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(WolfSoundVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, WolfSoundVariant wolfSoundVariant) {
        packetWrapper.writeMappedEntity(wolfSoundVariant);
    }

    @Deprecated
    static WolfSoundVariant decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return CODEC.decode(nBT, PacketWrapper.createDummyWrapper(clientVersion))
                .copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(WolfSoundVariant wolfSoundVariant, ClientVersion clientVersion) {
        return CODEC.encode(PacketWrapper.createDummyWrapper(clientVersion), wolfSoundVariant);
    }
}
