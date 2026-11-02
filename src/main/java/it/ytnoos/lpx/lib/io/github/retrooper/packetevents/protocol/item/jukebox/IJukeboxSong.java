package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.jukebox;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface IJukeboxSong
        extends MappedEntity, CopyableEntity<IJukeboxSong>, DeepComparableEntity {

    Sound getSound();

    Component getDescription();

    float getLengthInSeconds();

    int getComparatorOutput();

    @Deprecated
    static IJukeboxSong decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion), typesBuilderData);
    }

    static IJukeboxSong decode(
            NBT nBT, PacketWrapper<?> packetWrapper, @Nullable TypesBuilderData typesBuilderData) {
        NBTCompound nBTCompound;
        Sound orThrow =
                (nBTCompound = (NBTCompound) nBT)
                        .getOrThrow("sound_event", Sound.CODEC, packetWrapper);
        Component orThrow2 =
                nBTCompound.getOrThrow(
                        "description", packetWrapper.getSerializers(), packetWrapper);
        float asFloat = nBTCompound.getNumberTagOrThrow("length_in_seconds").getAsFloat();
        int asInt = nBTCompound.getNumberTagOrThrow("comparator_output").getAsInt();
        return new JukeboxSong(typesBuilderData, orThrow, orThrow2, asFloat, asInt);
    }

    @Deprecated
    static NBT encode(IJukeboxSong jukeboxSong, ClientVersion clientVersion) {
        return encode(PacketWrapper.createDummyWrapper(clientVersion), jukeboxSong);
    }

    static NBT encode(PacketWrapper<?> packetWrapper, IJukeboxSong jukeboxSong) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .set("sound_event", jukeboxSong.getSound(), Sound.CODEC, packetWrapper);
        nBTCompound.set(
                "description",
                jukeboxSong.getDescription(),
                packetWrapper.getSerializers(),
                packetWrapper);
        nBTCompound.setTag("length_in_seconds", new NBTFloat(jukeboxSong.getLengthInSeconds()));
        nBTCompound.setTag("comparator_output", new NBTInt(jukeboxSong.getComparatorOutput()));
        return nBTCompound;
    }

    static IJukeboxSong read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntityOrDirect(
                JukeboxSongs.getRegistry(), IJukeboxSong::readDirect);
    }

    static IJukeboxSong readDirect(PacketWrapper<?> packetWrapper) {
        Sound sound = Sound.read(packetWrapper);
        Component component = packetWrapper.readComponent();
        float value = packetWrapper.readFloat();
        int value2 = packetWrapper.readVarInt();
        return new JukeboxSong(null, sound, component, value, value2);
    }

    static void write(PacketWrapper<?> packetWrapper, IJukeboxSong jukeboxSong) {
        packetWrapper.writeMappedEntityOrDirect(jukeboxSong, IJukeboxSong::writeDirect);
    }

    static void writeDirect(PacketWrapper<?> packetWrapper, IJukeboxSong jukeboxSong) {
        Sound.write(packetWrapper, jukeboxSong.getSound());
        packetWrapper.writeComponent(jukeboxSong.getDescription());
        packetWrapper.writeFloat(jukeboxSong.getLengthInSeconds());
        packetWrapper.writeVarInt(jukeboxSong.getComparatorOutput());
    }
}
