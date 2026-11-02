package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NullMarked
public final class NormalNoise {

    public static final NbtCodec<NormalNoise> CODEC =
            (new NbtMapCodec<NormalNoise>() {

                        public NormalNoise decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            double value =
                                    nBTCompound
                                            .getNumberTagValueOrDefault("base_amplitude", 1.0)
                                            .doubleValue();
                            int value2 =
                                    nBTCompound.getNumberTagValueOrThrow("base_octave").intValue();
                            int value3 =
                                    nBTCompound
                                            .getNumberTagValueOrDefault("octave_count", 1)
                                            .intValue();
                            NormalNoise.Normalization or =
                                    nBTCompound.getOr(
                                            "normalize",
                                            NormalNoise.Normalization.CODEC,
                                            NormalNoise.Normalization.ENABLED,
                                            packetWrapper);
                            List<Double> listOrEmpty =
                                    nBTCompound.getListOrEmpty(
                                            "amplitude_modifiers", NbtCodecs.DOUBLE, packetWrapper);
                            return new NormalNoise(value, value2, value3, or, listOrEmpty);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                NormalNoise normalNoise)
                                throws NbtCodecException {
                            if (normalNoise.baseAmplitude != 1.0) {
                                nBTCompound.setTag(
                                        "base_amplitude", new NBTDouble(normalNoise.baseAmplitude));
                            }
                            nBTCompound.setTag("base_octave", new NBTInt(normalNoise.baseOctave));
                            if (normalNoise.octaveCount != 1) {
                                nBTCompound.setTag(
                                        "octave_count", new NBTInt(normalNoise.octaveCount));
                            }
                            if (normalNoise.normalize != NormalNoise.Normalization.ENABLED) {
                                nBTCompound.set(
                                        "normalize",
                                        normalNoise.normalize,
                                        NormalNoise.Normalization.CODEC,
                                        packetWrapper);
                            }
                            if (!normalNoise.amplitudeModifiers.isEmpty()) {
                                nBTCompound.setList(
                                        "amplitude_modifiers",
                                        normalNoise.amplitudeModifiers,
                                        NbtCodecs.DOUBLE,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    private final double baseAmplitude;

    private final int baseOctave;

    private final int octaveCount;

    private final NormalNoise.Normalization normalize;

    private final List<Double> amplitudeModifiers;

    public NormalNoise(double value, int value2) {
        this(value, value2, 1, NormalNoise.Normalization.ENABLED, Collections.emptyList());
    }

    public NormalNoise(
            double baseAmplitude,
            int baseOctave,
            int octaveCount,
            NormalNoise.Normalization normalization,
            List<Double> values) {
        this.baseAmplitude = baseAmplitude;
        this.baseOctave = baseOctave;
        this.octaveCount = octaveCount;
        this.normalize = normalization;
        this.amplitudeModifiers = Collections.unmodifiableList(values);
    }

    public double getBaseAmplitude() {
        return this.baseAmplitude;
    }

    public int getBaseOctave() {
        return this.baseOctave;
    }

    public int getOctaveCount() {
        return this.octaveCount;
    }

    public NormalNoise.Normalization getNormalize() {
        return this.normalize;
    }

    public List<Double> getAmplitudeModifiers() {
        return this.amplitudeModifiers;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result == null || this.getClass() != result.getClass()) {
            return false;
        } else {
            NormalNoise normalNoise;
            if (Double.compare(
                            (normalNoise = (NormalNoise) result).baseAmplitude, this.baseAmplitude)
                    != 0) {
                return false;
            } else if (this.baseOctave != normalNoise.baseOctave) {
                return false;
            } else if (this.octaveCount != normalNoise.octaveCount) {
                return false;
            } else {
                return this.normalize != normalNoise.normalize
                        ? false
                        : this.amplitudeModifiers.equals(normalNoise.amplitudeModifiers);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.baseAmplitude,
                this.baseOctave,
                this.octaveCount,
                this.normalize,
                this.amplitudeModifiers);
    }

    public enum Normalization {
        DISABLED,
        ENABLED,
        LEGACY;

        public static final NbtCodec<NormalNoise.Normalization> CODEC =
                new NbtCodec<NormalNoise.Normalization>() {

                    public NormalNoise.Normalization decode(NBT nBT, PacketWrapper<?> packetWrapper)
                            throws NbtCodecException {
                        if (nBT instanceof NBTNumber) {
                            return ((NBTNumber) nBT).getAsByte() != 0
                                    ? NormalNoise.Normalization.ENABLED
                                    : NormalNoise.Normalization.DISABLED;
                        } else {
                            String value = nBT.castOrThrow(NBTString.class).getValue();
                            if ("legacy".equals(value)) {
                                return NormalNoise.Normalization.LEGACY;
                            } else {
                                throw new NbtCodecException("Invalid normalization type: " + value);
                            }
                        }
                    }

                    public NBT encode(
                            PacketWrapper<?> packetWrapper,
                            NormalNoise.Normalization normalization) {
                        switch (normalization) {
                            case DISABLED:
                                return new NBTByte(false);
                            case ENABLED:
                                return new NBTByte(true);
                            case LEGACY:
                                return new NBTString("legacy");
                            default:
                                throw new IllegalArgumentException(
                                        "Unknown normalization: " + normalization);
                        }
                    }
                };
    }
}
