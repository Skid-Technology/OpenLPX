package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;

import org.jetbrains.annotations.Range;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class AlphaColor extends Color {

    public static final AlphaColor WHITE = new AlphaColor(-1);

    public static final AlphaColor BLACK = new AlphaColor(-16777216);

    public static final AlphaColor TRANSPARENT = new AlphaColor(0);

    private final int alpha;

    public AlphaColor(
            @Range(from = 0L, to = 255L) int value,
            @Range(from = 0L, to = 255L) int value2,
            @Range(from = 0L, to = 255L) int value3) {
        this(255, value, value2, value3);
    }

    public AlphaColor(
            @Range(from = 0L, to = 255L) int value,
            @Range(from = 0L, to = 255L) int value2,
            @Range(from = 0L, to = 255L) int value3,
            @Range(from = 0L, to = 255L) int value4) {
        super(value2, value3, value4);
        this.alpha = MathUtil.clamp(value, 0, 255);
    }

    public AlphaColor(
            @Range(from = 0L, to = 1L) float value,
            @Range(from = 0L, to = 1L) float value2,
            @Range(from = 0L, to = 1L) float value3) {
        this(1.0F, value, value2, value3);
    }

    public AlphaColor(
            @Range(from = 0L, to = 1L) float value,
            @Range(from = 0L, to = 1L) float value2,
            @Range(from = 0L, to = 1L) float value3,
            @Range(from = 0L, to = 1L) float value4) {
        super(value2, value3, value4);
        this.alpha = MathUtil.floor(value * 255.0F);
    }

    public AlphaColor(int value) {
        this(value >> 24 & 0xFF, value >> 16 & 0xFF, value >> 8 & 0xFF, value & 0xFF);
    }

    public static AlphaColor decode(NBT nBT, ClientVersion clientVersion) {
        if (nBT instanceof NBTNumber) {
            return new AlphaColor(((NBTNumber) nBT).getAsInt());
        }
        NBTList nBTList;
        float asFloat = ((NBTNumber) (nBTList = (NBTList) nBT).getTag(0)).getAsFloat();
        float asFloat2 = ((NBTNumber) nBTList.getTag(1)).getAsFloat();
        float asFloat3 = ((NBTNumber) nBTList.getTag(2)).getAsFloat();
        float asFloat4 = ((NBTNumber) nBTList.getTag(3)).getAsFloat();
        return new AlphaColor(asFloat4, asFloat, asFloat2, asFloat3);
    }

    public static NBT encode(AlphaColor alphaColor, ClientVersion clientVersion) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_21_2)) {
            return new NBTInt(alphaColor.asRGB());
        }
        NBTList nBTList;
        (nBTList = new NBTList<>(NBTType.FLOAT, 4)).addTag(new NBTFloat(alphaColor.red));
        nBTList.addTag(new NBTFloat(alphaColor.green));
        nBTList.addTag(new NBTFloat(alphaColor.blue));
        nBTList.addTag(new NBTFloat(alphaColor.alpha));
        return nBTList;
    }

    @Override
    public AlphaColor withAlpha() {
        return this;
    }

    public AlphaColor withRed(@Range(from = 0L, to = 255L) int value) {
        return new AlphaColor(this.alpha, value, this.green, this.blue);
    }

    public AlphaColor withGreen(@Range(from = 0L, to = 255L) int value) {
        return new AlphaColor(this.alpha, this.red, value, this.blue);
    }

    public AlphaColor withBlue(@Range(from = 0L, to = 255L) int value) {
        return new AlphaColor(this.alpha, this.red, this.green, value);
    }

    @Override
    public int asRGB() {
        return this.alpha << 24 | this.red << 16 | this.green << 8 | this.blue;
    }

    public AlphaColor plus(Color color) {
        return new AlphaColor(
                this.alpha, this.red + color.red, this.green + color.green, this.blue + color.blue);
    }

    public AlphaColor minus(Color color) {
        return new AlphaColor(
                this.alpha, this.red - color.red, this.green - color.green, this.blue - color.blue);
    }

    public AlphaColor times(Color color) {
        return color.alpha() == 255 && color.red == 255 && color.green == 255 && color.blue == 255
                ? this
                : new AlphaColor(
                        this.alpha * color.alpha() / 255,
                        this.red * color.red / 255,
                        this.green * color.green / 255,
                        this.blue * color.blue / 255);
    }

    @Override
    public AlphaColor blendWith(AlphaColor alphaColor) {
        int value = alphaColor.alpha;
        if (alphaColor.alpha == 255) {
            return alphaColor;
        }
        if (value == 0) {
            return this;
        }
        int value2 = value + this.alpha * (255 - value) / 255;
        return new AlphaColor(
                value2,
                alphaBlendChannel(value2, value, this.red, alphaColor.red),
                alphaBlendChannel(value2, value, this.green, alphaColor.green),
                alphaBlendChannel(value2, value, this.blue, alphaColor.blue));
    }

    protected static int alphaBlendChannel(int value, int value2, int value3, int value4) {
        return (value4 * value2 + value3 * (value - value2)) / value;
    }

    public AlphaColor asGrayscale() {
        int value = (int) (this.red * 0.3F + this.green * 0.59F + this.blue * 0.11F);
        return new AlphaColor(this.alpha, value, value, value);
    }

    public AlphaColor scale(float value) {
        return this.scale(value, value, value);
    }

    public AlphaColor scale(float value, float value2, float value3) {
        return new AlphaColor(
                this.alpha,
                (int) (this.red * value),
                (int) (this.green * value2),
                (int) (this.blue * value3));
    }

    public AlphaColor lerpSrgb(Color color, float value) {
        return new AlphaColor(
                MathUtil.lerp(value, this.alpha, color.alpha()),
                MathUtil.lerp(value, this.red, color.red),
                MathUtil.lerp(value, this.green, color.green),
                MathUtil.lerp(value, this.blue, color.blue));
    }

    @Override
    @Range(from = 0L, to = 255L)
    public int alpha() {
        return this.alpha;
    }
}
