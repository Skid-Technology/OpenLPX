package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.util.RGBLike;

import org.jetbrains.annotations.Range;
import org.jspecify.annotations.NullMarked;

import java.util.Arrays;
import java.util.Objects;

@NullMarked
public class Color implements RGBLike {

    private static final NbtCodec<Color> INT_CODEC = NbtCodecs.INT.apply(Color::new, Color::asRGB);

    private static final NbtCodec<Color> FLOATS_CODEC =
            NbtCodecs.FLOAT
                    .applyFixedList(3)
                    .apply(
                            result -> new Color(result.get(0), result.get(1), result.get(2)),
                            result ->
                                    Arrays.asList(
                                            result.red / 255.0F,
                                            result.green / 255.0F,
                                            result.blue / 255.0F));

    public static final NbtCodec<Color> CODEC =
            new NbtCodec<Color>() {

                private final NbtCodec<Color> decodeCodec =
                        Color.INT_CODEC.withAlternative(Color.FLOATS_CODEC);

                public Color decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    return this.decodeCodec.decode(nBT, packetWrapper);
                }

                public NBT encode(PacketWrapper<?> packetWrapper, Color color)
                        throws NbtCodecException {
                    return packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_21_2)
                            ? Color.INT_CODEC.encode(packetWrapper, color)
                            : Color.FLOATS_CODEC.encode(packetWrapper, color);
                }
            };

    public static final Color WHITE = new Color(-1);

    public static final Color BLACK = new Color(-16777216);

    protected static final int BIT_MASK = 255;

    protected final int red;

    protected final int green;

    protected final int blue;

    public Color(
            @Range(from = 0L, to = 255L) int value,
            @Range(from = 0L, to = 255L) int value2,
            @Range(from = 0L, to = 255L) int value3) {
        this.red = MathUtil.clamp(value, 0, 255);
        this.green = MathUtil.clamp(value2, 0, 255);
        this.blue = MathUtil.clamp(value3, 0, 255);
    }

    public Color(
            @Range(from = 0L, to = 1L) float value,
            @Range(from = 0L, to = 1L) float value2,
            @Range(from = 0L, to = 1L) float value3) {
        this(
                MathUtil.floor(value * 255.0F),
                MathUtil.floor(value2 * 255.0F),
                MathUtil.floor(value3 * 255.0F));
    }

    public Color(int value) {
        this(value >> 16 & 0xFF, value >> 8 & 0xFF, value & 0xFF);
    }

    public static Color read(PacketWrapper<?> packetWrapper) {
        return new Color(packetWrapper.readInt());
    }

    public static void write(PacketWrapper<?> packetWrapper, Color color) {
        packetWrapper.writeInt(color.asRGB());
    }

    public static Color readShort(PacketWrapper<?> packetWrapper) {
        return new Color(
                packetWrapper.readUnsignedByte(),
                packetWrapper.readUnsignedByte(),
                packetWrapper.readUnsignedByte());
    }

    public static void writeShort(PacketWrapper<?> packetWrapper, Color color) {
        packetWrapper.writeByte(color.red);
        packetWrapper.writeByte(color.green);
        packetWrapper.writeByte(color.blue);
    }

    @Deprecated
    public static Color decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        return CODEC.decode(nBT, packetWrapper);
    }

    @Deprecated
    public static Color decode(NBT nBT, ClientVersion clientVersion) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion));
    }

    @Deprecated
    public static NBT encode(PacketWrapper<?> packetWrapper, Color color) {
        return CODEC.encode(packetWrapper, color);
    }

    @Deprecated
    public static NBT encode(Color color, ClientVersion clientVersion) {
        return encode(PacketWrapper.createDummyWrapper(clientVersion), color);
    }

    public AlphaColor withAlpha() {
        return this.withAlpha(255);
    }

    public AlphaColor withAlpha(@Range(from = 0L, to = 255L) int value) {
        return new AlphaColor(value, this.red, this.green, this.blue);
    }

    public Color withRed(@Range(from = 0L, to = 255L) int value) {
        return new Color(value, this.green, this.blue);
    }

    public Color withGreen(@Range(from = 0L, to = 255L) int value) {
        return new Color(this.red, value, this.blue);
    }

    public Color withBlue(@Range(from = 0L, to = 255L) int value) {
        return new Color(this.red, this.green, value);
    }

    public int asRGB() {
        return this.red << 16 | this.green << 8 | this.blue;
    }

    public Color plus(Color color) {
        return new Color(this.red + color.red, this.green + color.green, this.blue + color.blue);
    }

    public Color minus(Color color) {
        return new Color(this.red - color.red, this.green - color.green, this.blue - color.blue);
    }

    public Color times(Color color) {
        if (color instanceof AlphaColor) {
            return color.times(this);
        } else if (this.red == 255 && this.green == 255 && this.blue == 255) {
            return color;
        } else {
            return color.red == 255 && color.green == 255 && color.blue == 255
                    ? this
                    : new Color(
                            this.red * color.red / 255,
                            this.green * color.green / 255,
                            this.blue * color.blue / 255);
        }
    }

    public AlphaColor blendWith(AlphaColor alphaColor) {
        int value;
        if ((value = alphaColor.alpha()) == 255) {
            return alphaColor;
        }
        if (value == 0) {
            return this.withAlpha();
        }
        int value2 = value + (255 - value);
        return new AlphaColor(
                value2,
                alphaBlendChannel(value2, value, this.red, alphaColor.red),
                alphaBlendChannel(value2, value, this.green, alphaColor.green),
                alphaBlendChannel(value2, value, this.blue, alphaColor.blue));
    }

    protected static int alphaBlendChannel(int value, int value2, int value3, int value4) {
        return (value4 * value2 + value3 * (value - value2)) / value;
    }

    public Color asGrayscale() {
        int value = (int) (this.red * 0.3F + this.green * 0.59F + this.blue * 0.11F);
        return new Color(value, value, value);
    }

    public Color scale(float value) {
        return this.scale(value, value, value);
    }

    public Color scale(float value, float value2, float value3) {
        return new Color(
                (int) (this.red * value), (int) (this.green * value2), (int) (this.blue * value3));
    }

    public Color lerpSrgb(Color color, float value) {
        return new Color(
                MathUtil.lerp(value, this.red, color.red),
                MathUtil.lerp(value, this.green, color.green),
                MathUtil.lerp(value, this.blue, color.blue));
    }

    @Range(from = 0L, to = 255L)
    public int alpha() {
        return 255;
    }

    @Override
    @Range(from = 0L, to = 255L)
    public int red() {
        return this.red;
    }

    @Override
    @Range(from = 0L, to = 255L)
    public int green() {
        return this.green;
    }

    @Override
    @Range(from = 0L, to = 255L)
    public int blue() {
        return this.blue;
    }

    @Override
    public boolean equals(Object result) {
        return result instanceof Color ? this.asRGB() == ((Color) result).asRGB() : false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.asRGB());
    }
}
