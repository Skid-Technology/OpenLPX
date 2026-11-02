package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Either;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public class TimeMarkerInfo {

    private static final NbtCodec<TimeMarkerInfo> FULL_CODEC =
            (new NbtMapCodec<TimeMarkerInfo>() {

                        public TimeMarkerInfo decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            int value = nBTCompound.getNumberTagValueOrThrow("ticks").intValue();
                            boolean booleanOr = nBTCompound.getBooleanOr("show_in_commands", false);
                            return new TimeMarkerInfo(value, booleanOr);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                TimeMarkerInfo timeMarkerInfo)
                                throws NbtCodecException {
                            nBTCompound.setTag("ticks", new NBTInt(timeMarkerInfo.ticks));
                            if (timeMarkerInfo.showInCommands) {
                                nBTCompound.setTag("show_in_commands", new NBTByte(true));
                            }
                        }
                    })
                    .codec();

    public static final NbtCodec<TimeMarkerInfo> CODEC =
            NbtCodecs.either(NbtCodecs.INT, FULL_CODEC)
                    .apply(
                            result -> result.map(TimeMarkerInfo::new, Function.identity()),
                            result ->
                                    result.showInCommands
                                            ? Either.createRight(result)
                                            : Either.createLeft(result.ticks));

    private final int ticks;

    private final boolean showInCommands;

    public TimeMarkerInfo(int value) {
        this(value, false);
    }

    public TimeMarkerInfo(int ticks, boolean showInCommands) {
        this.ticks = ticks;
        this.showInCommands = showInCommands;
    }

    public int getTicks() {
        return this.ticks;
    }

    public boolean isShowInCommands() {
        return this.showInCommands;
    }
}
