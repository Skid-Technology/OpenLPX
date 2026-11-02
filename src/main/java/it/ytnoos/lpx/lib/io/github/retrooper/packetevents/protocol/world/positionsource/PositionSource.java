package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class PositionSource {

    protected final PositionSourceType<?> type;

    public PositionSource(PositionSourceType<?> positionSourceType) {
        this.type = positionSourceType;
    }

    public static PositionSource decode(NBT nBT, ClientVersion clientVersion) {
        NBTCompound nBTCompound;
        String text;
        PositionSourceType positionSourceType;
        if ((positionSourceType =
                        PositionSourceTypes.getByName(
                                text =
                                        (nBTCompound = (NBTCompound) nBT)
                                                .getStringTagValueOrThrow("type")))
                == null) {
            throw new IllegalStateException("Can't find position source type with id " + text);
        } else {
            return positionSourceType.decode(nBTCompound, clientVersion);
        }
    }

    public static NBT encode(PositionSource positionSource, ClientVersion clientVersion) {
        return encode(
                positionSource,
                (PositionSourceType<PositionSource>) positionSource.getType(),
                clientVersion);
    }

    public static <T extends PositionSource> NBT encode(
            T t, PositionSourceType<T> positionSourceType, ClientVersion clientVersion) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .setTag("type", new NBTString(positionSourceType.getName().toString()));
        positionSourceType.encode(t, clientVersion, nBTCompound);
        return nBTCompound;
    }

    public PositionSourceType<?> getType() {
        return this.type;
    }
}
