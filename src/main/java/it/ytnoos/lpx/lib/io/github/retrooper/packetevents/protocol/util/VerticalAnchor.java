package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class VerticalAnchor {

    public static final VerticalAnchor BOTTOM = aboveBottom(0);

    public static final VerticalAnchor TOP = belowTop(0);

    public static final NbtMapCodec<VerticalAnchor> MAP_CODEC =
            new NbtMapCodec<VerticalAnchor>() {

                public VerticalAnchor decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    VerticalAnchor.Type type = null;
                    VerticalAnchor.Type[] types;
                    int value = (types = VerticalAnchor.Type.values()).length;
                    for (int index = 0; index < value; index++) {
                        VerticalAnchor.Type type2 = types[index];
                        if (nBTCompound.contains(type2.getCodecName())) {
                            if (type != null) {
                                throw new NbtCodecException(
                                        "Multiple anchor types in " + nBTCompound);
                            }
                            type = type2;
                        }
                    }
                    if (type == null) {
                        throw new NbtCodecException("No anchor type in " + nBTCompound);
                    }
                    int value2 =
                            nBTCompound.getNumberTagValueOrThrow(type.getCodecName()).intValue();
                    return new VerticalAnchor(type, value2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        VerticalAnchor verticalAnchor)
                        throws NbtCodecException {
                    nBTCompound.setTag(
                            verticalAnchor.type.getCodecName(), new NBTInt(verticalAnchor.offset));
                }
            };

    private final VerticalAnchor.Type type;

    private final int offset;

    public VerticalAnchor(VerticalAnchor.Type type2, int offset) {
        this.type = type2;
        this.offset = offset;
    }

    public static VerticalAnchor absolute(int value) {
        return new VerticalAnchor(VerticalAnchor.Type.ABSOLUTE, value);
    }

    public static VerticalAnchor aboveBottom(int value) {
        return new VerticalAnchor(VerticalAnchor.Type.ABOVE_BOTTOM, value);
    }

    public static VerticalAnchor belowTop(int value) {
        return new VerticalAnchor(VerticalAnchor.Type.BELOW_TOP, value);
    }

    public static VerticalAnchor relativeToSeaLevel(int value) {
        return new VerticalAnchor(VerticalAnchor.Type.RELATIVE_TO_SEA_LEVEL, value);
    }

    public VerticalAnchor.Type getType() {
        return this.type;
    }

    public int getOffset() {
        return this.offset;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            VerticalAnchor verticalAnchor = (VerticalAnchor) result;
            return this.offset != verticalAnchor.offset ? false : this.type == verticalAnchor.type;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.offset);
    }

    public enum Type implements CodecNameable {
        ABSOLUTE("absolute"),
        ABOVE_BOTTOM("above_bottom"),
        BELOW_TOP("below_top"),
        RELATIVE_TO_SEA_LEVEL("relative_to_sea_level");

        private final String id;

        Type(String id) {
            this.id = id;
        }

        @Override
        public String getCodecName() {
            return this.id;
        }
    }
}
