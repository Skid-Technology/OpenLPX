package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.instrument;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Instrument extends MappedEntity, CopyableEntity<Instrument>, DeepComparableEntity {

    Sound getSound();

    float getUseSeconds();

    default int getUseDuration() {
        return MathUtil.floor(this.getUseSeconds() * 20.0F);
    }

    float getRange();

    Component getDescription();

    static Instrument read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntityOrDirect(
                Instruments.getRegistry(), Instrument::readDirect);
    }

    static Instrument readDirect(PacketWrapper<?> packetWrapper) {
        Sound sound = Sound.read(packetWrapper);
        float value =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)
                        ? packetWrapper.readFloat()
                        : packetWrapper.readVarInt() * 20.0F;
        float value2 = packetWrapper.readFloat();
        Component component =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)
                        ? packetWrapper.readComponent()
                        : Component.empty();
        return new StaticInstrument(sound, value, value2, component);
    }

    static void write(PacketWrapper<?> packetWrapper, Instrument instrument) {
        packetWrapper.writeMappedEntityOrDirect(instrument, Instrument::writeDirect);
    }

    static void writeDirect(PacketWrapper<?> packetWrapper, Instrument instrument) {
        Sound.write(packetWrapper, instrument.getSound());
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            packetWrapper.writeFloat(instrument.getUseSeconds());
        } else {
            packetWrapper.writeVarInt(instrument.getUseDuration());
        }
        packetWrapper.writeFloat(instrument.getRange());
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            packetWrapper.writeComponent(instrument.getDescription());
        }
    }

    @Deprecated
    static Instrument decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion), typesBuilderData);
    }

    static Instrument decode(
            NBT nBT, PacketWrapper<?> packetWrapper, @Nullable TypesBuilderData typesBuilderData) {
        NBTCompound nBTCompound;
        Sound orThrow =
                (nBTCompound = (NBTCompound) nBT)
                        .getOrThrow("sound_event", Sound.CODEC, packetWrapper);
        float asFloat = nBTCompound.getNumberTagOrThrow("use_duration").getAsFloat();
        float asFloat2 = nBTCompound.getNumberTagOrThrow("range").getAsFloat();
        Component orThrow2 =
                nBTCompound.getOrThrow(
                        "description", packetWrapper.getSerializers(), packetWrapper);
        return new StaticInstrument(typesBuilderData, orThrow, asFloat, asFloat2, orThrow2);
    }

    @Deprecated
    static NBT encode(Instrument instrument, ClientVersion clientVersion) {
        return encode(PacketWrapper.createDummyWrapper(clientVersion), instrument);
    }

    static NBT encode(PacketWrapper<?> packetWrapper, Instrument instrument) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .set("sound_event", instrument.getSound(), Sound.CODEC, packetWrapper);
        nBTCompound.setTag("use_duration", new NBTFloat(instrument.getUseSeconds()));
        nBTCompound.setTag("range", new NBTFloat(instrument.getRange()));
        nBTCompound.set(
                "description",
                instrument.getDescription(),
                packetWrapper.getSerializers(),
                packetWrapper);
        return nBTCompound;
    }
}
