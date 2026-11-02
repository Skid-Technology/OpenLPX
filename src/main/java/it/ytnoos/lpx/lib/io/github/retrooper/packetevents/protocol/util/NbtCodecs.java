package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.AlphaColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByteArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLongArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Either;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.UniqueIdUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@NullMarked
@Experimental
public final class NbtCodecs {

    public static final NbtCodec<Integer> INT =
            new NbtCodec<Integer>() {

                public Integer decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    return nBT.castOrThrow(NBTNumber.class).getAsInt();
                }

                public NBT encode(PacketWrapper<?> packetWrapper, Integer value) {
                    return new NBTInt(value);
                }
            };

    public static final NbtCodec<Double> DOUBLE =
            new NbtCodec<Double>() {

                public Double decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    return nBT.castOrThrow(NBTNumber.class).getAsDouble();
                }

                public NBT encode(PacketWrapper<?> packetWrapper, Double value) {
                    return new NBTDouble(value);
                }
            };

    public static final NbtCodec<Float> FLOAT =
            new NbtCodec<Float>() {

                public Float decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    return nBT.castOrThrow(NBTNumber.class).getAsFloat();
                }

                public NBT encode(PacketWrapper<?> packetWrapper, Float value) {
                    return new NBTFloat(value);
                }
            };

    public static final NbtCodec<Boolean> BOOLEAN =
            new NbtCodec<Boolean>() {

                public Boolean decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    return nBT.castOrThrow(NBTNumber.class).getAsByte() != 0;
                }

                public NBT encode(PacketWrapper<?> packetWrapper, Boolean matches) {
                    return new NBTByte(matches);
                }
            };

    public static final NbtCodec<String> STRING =
            new NbtCodec<String>() {

                public String decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    return nBT.castOrThrow(NBTString.class).getValue();
                }

                public NBT encode(PacketWrapper<?> packetWrapper, String text) {
                    return new NBTString(text);
                }
            };

    public static final NbtCodec<List<String>> STRING_LIST = STRING.applyList();

    public static final NbtCodec<List<? extends NBT>> GENERIC_LIST =
            new NbtCodec<List<? extends NBT>>() {

                public List<? extends NBT> decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    if (nBT instanceof NBTList) {
                        return ((NBTList) nBT).unwrapTags();
                    }
                    if (nBT instanceof NBTIntArray) {
                        int[] value = ((NBTIntArray) nBT).getValue();
                        ArrayList<NBT> values = new ArrayList<>(value.length);
                        int value2 = value.length;
                        for (int index = 0; index < value2; index++) {
                            int value3 = value[index];
                            values.add(new NBTInt(value3));
                        }
                        return values;
                    } else if (nBT instanceof NBTByteArray) {
                        byte[] value4 = ((NBTByteArray) nBT).getValue();
                        ArrayList<NBT> values2 = new ArrayList<>(value4.length);
                        int value5 = value4.length;
                        for (int index2 = 0; index2 < value5; index2++) {
                            byte value6 = value4[index2];
                            values2.add(new NBTByte(value6));
                        }
                        return values2;
                    } else {
                        if (!(nBT instanceof NBTLongArray)) {
                            throw new NbtCodecException("Not a list: " + nBT);
                        }
                        long[] value7 = ((NBTLongArray) nBT).getValue();
                        ArrayList<NBT> values3 = new ArrayList<>(value7.length);
                        int value8 = value7.length;
                        for (int index3 = 0; index3 < value8; index3++) {
                            long value9 = value7[index3];
                            values3.add(new NBTLong(value9));
                        }
                        return values3;
                    }
                }

                public NBT encode(PacketWrapper<?> packetWrapper, List<? extends NBT> values) {
                    if (values.isEmpty()) {
                        return new NBTList<>(NBTType.END, 0);
                    }
                    NBTType nBTType;
                    if ((nBTType = NBTList.getCommonTagType(values)) == NBTType.COMPOUND) {
                        NBTList nBTList = new NBTList<>(NBTType.COMPOUND, values.size());
                        for (NBT nBT : values) {
                            nBTList.addTagOrWrap(nBT);
                        }
                        return nBTList;
                    } else if (nBTType == NBTType.INT) {
                        int[] values2 = new int[values.size()];
                        for (int index = 0; index < values2.length; index++) {
                            values2[index] = ((NBTInt) values.get(index)).getAsInt();
                        }
                        return new NBTIntArray(values2);
                    } else if (nBTType == NBTType.BYTE) {
                        byte[] values3 = new byte[values.size()];
                        for (int index2 = 0; index2 < values3.length; index2++) {
                            values3[index2] = ((NBTByte) values.get(index2)).getAsByte();
                        }
                        return new NBTByteArray(values3);
                    } else {
                        if (nBTType != NBTType.LONG) {
                            return new NBTList(nBTType, values);
                        }
                        long[] values4 = new long[values.size()];
                        for (int index3 = 0; index3 < values4.length; index3++) {
                            values4[index3] = ((NBTLong) values.get(index3)).getAsLong();
                        }
                        return new NBTLongArray(values4);
                    }
                }
            };

    public static final NbtCodec<int[]> INT_ARRAY =
            new NbtCodec<int[]>() {

                public int[] decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    if (nBT instanceof NBTIntArray) {
                        return ((NBTIntArray) nBT).getValue();
                    }
                    List<? extends NBT> values;
                    int value;
                    int[] values2 =
                            new int
                                    [value =
                                            (values =
                                                            NbtCodecs.GENERIC_LIST.decode(
                                                                    nBT, packetWrapper))
                                                    .size()];
                    for (int index = 0; index < value; index++) {
                        values2[index] =
                                ((NBT) values.get(index)).castOrThrow(NBTNumber.class).getAsInt();
                    }
                    return values2;
                }

                public NBT encode(PacketWrapper<?> packetWrapper, int[] values) {
                    return new NBTIntArray(values);
                }
            };

    public static final NbtCodec<UUID> UUID =
            INT_ARRAY.apply(UniqueIdUtil::fromIntArray, UniqueIdUtil::toIntArray);

    public static final NbtCodec<UUID> STRING_UUID =
            new NbtCodec<UUID>() {

                public UUID decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    String text;
                    if ((text = ((NBTString) nBT).getValue()).length() == 36) {
                        try {
                            return java.util.UUID.fromString(text);
                        } catch (IllegalArgumentException illegalArgumentException) {
                        }
                    }
                    throw new NbtCodecException("Invalid UUID " + text);
                }

                public NBT encode(PacketWrapper<?> packetWrapper, UUID uUID)
                        throws NbtCodecException {
                    return new NBTString(uUID.toString());
                }
            };

    public static final NbtCodec<UUID> LENIENT_UUID = UUID.withAlternative(STRING_UUID);

    public static final NbtCodec<Color> RGB_COLOR =
            new NbtCodec<Color>() {

                public Color decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    if (nBT instanceof NBTString) {
                        String text;
                        if ((text = ((NBTString) nBT).getValue()).isEmpty()
                                || text.charAt(0) != '#') {
                            throw new NbtCodecException("Hex color must begin with #");
                        }
                        if (text.length() - 1 != 6) {
                            throw new NbtCodecException(
                                    "Hex color is wrong, expected 6 digits but got " + text);
                        }
                        try {
                            int value = Integer.parseInt(text.substring(1), 16);
                            return new Color(value);
                        } catch (NumberFormatException numberFormatException) {
                            throw new NbtCodecException(numberFormatException);
                        }
                    } else {
                        return nBT instanceof NBTNumber
                                ? new Color(((NBTNumber) nBT).getAsInt())
                                : Color.WHITE;
                    }
                }

                public NBT encode(PacketWrapper<?> packetWrapper, Color color) {
                    return new NBTInt(color.asRGB());
                }
            };

    public static final NbtCodec<AlphaColor> ARGB_COLOR =
            new NbtCodec<AlphaColor>() {

                public AlphaColor decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    if (nBT instanceof NBTString) {
                        String text;
                        if ((text = ((NBTString) nBT).getValue()).isEmpty()
                                || text.charAt(0) != '#') {
                            throw new NbtCodecException("Hex color must begin with #");
                        }
                        if (text.length() - 1 != 8) {
                            throw new NbtCodecException(
                                    "Hex color is wrong, expected 8 digits but got " + text);
                        }
                        try {
                            int value = Integer.parseUnsignedInt(text.substring(1), 16);
                            return new AlphaColor(value);
                        } catch (NumberFormatException numberFormatException) {
                            throw new NbtCodecException(numberFormatException);
                        }
                    } else {
                        return nBT instanceof NBTNumber
                                ? new AlphaColor(((NBTNumber) nBT).getAsInt())
                                : AlphaColor.WHITE;
                    }
                }

                public NBT encode(PacketWrapper<?> packetWrapper, AlphaColor alphaColor) {
                    return new NBTInt(alphaColor.asRGB());
                }
            };

    public static final NbtCodec<NBT> NOOP =
            new NbtCodec<NBT>() {

                public NBT decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    return nBT;
                }

                public NBT encode(PacketWrapper<?> packetWrapper, NBT nBT) {
                    return nBT;
                }
            };

    private static final NbtCodec<?> ERROR_CODEC =
            new NbtCodec<Object>() {

                @Override
                public Object decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public NBT encode(PacketWrapper<?> packetWrapper, Object result) {
                    throw new UnsupportedOperationException();
                }
            };

    private NbtCodecs() {}

    public static <T> NbtCodec<T> errorCodec() {
        return (NbtCodec<T>) ERROR_CODEC;
    }

    public static <T extends Enum<T> & CodecNameable> NbtCodec<T> forEnum(final T[] ts) {
        return new NbtCodec<T>() {

            private final Map<String, T> map = new HashMap<>(ts.length);

            {
                Enum[] result2 = ts;
                int value = ts.length;
                for (int value2 = 0; value2 < value; value2++) {
                    Enum enumValue = result2[value2];
                    Enum enumValue2;
                    if ((enumValue2 =
                                    this.map.putIfAbsent(
                                            ((CodecNameable) enumValue).getCodecName(),
                                            (T) enumValue))
                            != null) {
                        throw new IllegalStateException(
                                "Can't create codec for enum with duplicate names: " + enumValue2);
                    }
                }
            }

            public T decode(NBT result, PacketWrapper<?> result2) {
                String value2 = ((NBTString) result).getValue();
                Enum enumValue;
                if ((enumValue = this.map.get(value2)) == null) {
                    throw new NbtCodecException(
                            "Can't find " + value2 + " in " + this.map.keySet());
                } else {
                    return (T) enumValue;
                }
            }

            public NBT encode(PacketWrapper<?> result, T result2) {
                return new NBTString(((CodecNameable) result2).getCodecName());
            }
        };
    }

    public static <T extends MappedEntity> NbtCodec<T> forRegistry(final IRegistry<T> registry) {
        return new NbtCodec<T>() {

            public T decode(NBT result, PacketWrapper<?> result2) {
                ClientVersion clientVersion = result2.getServerVersion().toClientVersion();
                IRegistry registry2 = result2.replaceRegistry(registry);
                MappedEntity mappedEntity = null;
                if (result instanceof NBTNumber) {
                    int asInt = ((NBTNumber) result).getAsInt();
                    if ((mappedEntity = registry2.getById(clientVersion, asInt)) == null) {
                        throw new NbtCodecException(
                                "Can't find #" + asInt + " in " + registry.getRegistryKey());
                    }
                } else if (result instanceof NBTString) {
                    String value = ((NBTString) result).getValue();
                    if ((mappedEntity = registry2.getByName(clientVersion, value)) == null) {
                        throw new NbtCodecException(
                                "Can't find " + value + " in " + registry.getRegistryKey());
                    }
                }
                if (mappedEntity == null) {
                    throw new NbtCodecException(
                            "Can't decode registry " + registry.getRegistryKey());
                } else {
                    return (T) mappedEntity;
                }
            }

            public NBT encode(PacketWrapper<?> result, T result2) {
                if (!result2.isRegistered()) {
                    throw new NbtCodecException("Unregistered entry");
                } else {
                    return ResourceLocation.CODEC.encode(result, result2.getName());
                }
            }
        };
    }

    public static <L, R> NbtCodec<Either<L, R>> either(
            final NbtCodec<L> nbtCodec, final NbtCodec<R> nbtCodec2) {
        return new NbtCodec<Either<L, R>>() {

            public Either<L, R> decode(NBT result, PacketWrapper<?> result2)
                    throws NbtCodecException {
                try {
                    return Either.createLeft((L) nbtCodec.decode(result, result2));
                } catch (NbtCodecException nbtCodecException) {
                    try {
                        return Either.createRight((R) nbtCodec2.decode(result, result2));
                    } catch (NbtCodecException nbtCodecException2) {
                        nbtCodecException2.addSuppressed(nbtCodecException);
                        throw nbtCodecException2;
                    }
                }
            }

            public NBT encode(PacketWrapper<?> result, Either<L, R> result2)
                    throws NbtCodecException {
                Function<L, NBT> function =
                        result3 -> nbtCodec.encode(((PacketWrapper) result), result3);
                return result2.map(
                        function, result3 -> nbtCodec2.encode(((PacketWrapper) result), result3));
            }
        };
    }

    public static <T> NbtMapCodec<T> forUnit(final Supplier<T> supplier) {
        return new NbtMapCodec<T>() {

            @Override
            public T decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                    throws NbtCodecException {
                return (T) supplier.get();
            }

            @Override
            public void encode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, T t)
                    throws NbtCodecException {}
        };
    }
}
