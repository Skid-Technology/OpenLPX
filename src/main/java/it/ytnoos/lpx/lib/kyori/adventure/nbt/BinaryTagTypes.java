package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public final class BinaryTagTypes {

    public static final BinaryTagType<EndBinaryTag> END =
            BinaryTagType.register(
                    EndBinaryTag.class, (byte) 0, result -> EndBinaryTag.endBinaryTag(), null);

    public static final BinaryTagType<ByteBinaryTag> BYTE =
            BinaryTagType.registerNumeric(
                    ByteBinaryTag.class,
                    (byte) 1,
                    result -> ByteBinaryTag.byteBinaryTag(result.readByte()),
                    (result, result2) -> result2.writeByte(result.value()));

    public static final BinaryTagType<ShortBinaryTag> SHORT =
            BinaryTagType.registerNumeric(
                    ShortBinaryTag.class,
                    (byte) 2,
                    result -> ShortBinaryTag.shortBinaryTag(result.readShort()),
                    (result, result2) -> result2.writeShort(result.value()));

    public static final BinaryTagType<IntBinaryTag> INT =
            BinaryTagType.registerNumeric(
                    IntBinaryTag.class,
                    (byte) 3,
                    result -> IntBinaryTag.intBinaryTag(result.readInt()),
                    (result, result2) -> result2.writeInt(result.value()));

    public static final BinaryTagType<LongBinaryTag> LONG =
            BinaryTagType.registerNumeric(
                    LongBinaryTag.class,
                    (byte) 4,
                    result -> LongBinaryTag.longBinaryTag(result.readLong()),
                    (result, result2) -> result2.writeLong(result.value()));

    public static final BinaryTagType<FloatBinaryTag> FLOAT =
            BinaryTagType.registerNumeric(
                    FloatBinaryTag.class,
                    (byte) 5,
                    result -> FloatBinaryTag.floatBinaryTag(result.readFloat()),
                    (result, result2) -> result2.writeFloat(result.value()));

    public static final BinaryTagType<DoubleBinaryTag> DOUBLE =
            BinaryTagType.registerNumeric(
                    DoubleBinaryTag.class,
                    (byte) 6,
                    result -> DoubleBinaryTag.doubleBinaryTag(result.readDouble()),
                    (result, result2) -> result2.writeDouble(result.value()));

    public static final BinaryTagType<ByteArrayBinaryTag> BYTE_ARRAY =
            BinaryTagType.register(
                    ByteArrayBinaryTag.class,
                    (byte) 7,
                    result -> {
                        ByteArrayBinaryTag byteArrayBinaryTag2;
                        int value = result.readInt();
                        BinaryTagScope binaryTagScope = TrackingDataInput.enter(result, value);
                        try {
                            byte[] values = new byte[value];
                            result.readFully(values);
                            byteArrayBinaryTag2 = ByteArrayBinaryTag.byteArrayBinaryTag(values);
                        } catch (Throwable throwable) {
                            if (binaryTagScope != null) {
                                try {
                                    binaryTagScope.close();
                                } catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        if (binaryTagScope != null) {
                            binaryTagScope.close();
                        }
                        return byteArrayBinaryTag2;
                    },
                    (result, result2) -> {
                        byte[] values = ByteArrayBinaryTagImpl.value(result);
                        result2.writeInt(values.length);
                        result2.write(values);
                    });

    public static final BinaryTagType<StringBinaryTag> STRING =
            BinaryTagType.register(
                    StringBinaryTag.class,
                    (byte) 8,
                    result -> StringBinaryTag.stringBinaryTag(result.readUTF()),
                    (result, result2) -> result2.writeUTF(result.value()));

    public static final BinaryTagType<ListBinaryTag> LIST =
            BinaryTagType.register(
                    ListBinaryTag.class,
                    (byte) 9,
                    result -> {
                        ListBinaryTag listBinaryTag2;
                        BinaryTagType binaryTagType2 =
                                BinaryTagType.binaryTagType(result.readByte());
                        int value = result.readInt();
                        BinaryTagScope binaryTagScope = TrackingDataInput.enter(result, value * 8L);
                        try {
                            ArrayList<BinaryTag> values = new ArrayList<>(value);
                            for (int index = 0; index < value; index++) {
                                values.add(binaryTagType2.read(result));
                            }
                            listBinaryTag2 = ListBinaryTag.listBinaryTag(binaryTagType2, values);
                        } catch (Throwable throwable) {
                            if (binaryTagScope != null) {
                                try {
                                    binaryTagScope.close();
                                } catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        if (binaryTagScope != null) {
                            binaryTagScope.close();
                        }
                        return listBinaryTag2;
                    },
                    (result, result2) -> {
                        BinaryTag binaryTag;
                        ListBinaryTag listBinaryTag = result.wrapHeterogeneity();
                        result2.writeByte(listBinaryTag.elementType().id());
                        int size2 = listBinaryTag.size();
                        result2.writeInt(size2);
                        Iterator iterator2 = listBinaryTag.iterator();
                        while (iterator2.hasNext()) {
                            BinaryTagType.writeUntyped(
                                    (binaryTag = (BinaryTag) iterator2.next()).type(),
                                    binaryTag,
                                    result2);
                        }
                    });

    public static final BinaryTagType<CompoundBinaryTag> COMPOUND =
            BinaryTagType.register(
                    CompoundBinaryTag.class,
                    (byte) 10,
                    result -> {
                        CompoundBinaryTagImpl compoundBinaryTagImpl;
                        BinaryTagType binaryTagType2;
                        BinaryTagScope binaryTagScope = TrackingDataInput.enter(result);
                        try {
                            HashMap entries = new HashMap();
                            while ((binaryTagType2 = BinaryTagType.binaryTagType(result.readByte()))
                                    != END) {
                                String text = result.readUTF();
                                BinaryTag binaryTag = binaryTagType2.read(result);
                                entries.put(text, binaryTag);
                            }
                            compoundBinaryTagImpl = new CompoundBinaryTagImpl(entries);
                        } catch (Throwable throwable) {
                            if (binaryTagScope != null) {
                                try {
                                    binaryTagScope.close();
                                } catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        if (binaryTagScope != null) {
                            binaryTagScope.close();
                        }
                        return compoundBinaryTagImpl;
                    },
                    (result, result2) -> {
                        Entry entry;
                        BinaryTag binaryTag;
                        Iterator iterator2 = result.iterator();
                        while (iterator2.hasNext()) {
                            if ((binaryTag =
                                            (BinaryTag)
                                                    (entry = (Entry) iterator2.next()).getValue())
                                    != null) {
                                BinaryTagType binaryTagType = binaryTag.type();
                                result2.writeByte(binaryTagType.id());
                                if (binaryTagType != END) {
                                    result2.writeUTF((String) entry.getKey());
                                    BinaryTagType.writeUntyped(binaryTagType, binaryTag, result2);
                                }
                            }
                        }
                        result2.writeByte(END.id());
                    });

    public static final BinaryTagType<IntArrayBinaryTag> INT_ARRAY =
            BinaryTagType.register(
                    IntArrayBinaryTag.class,
                    (byte) 11,
                    result -> {
                        IntArrayBinaryTag intArrayBinaryTag2;
                        int value = result.readInt();
                        BinaryTagScope binaryTagScope = TrackingDataInput.enter(result, value * 4L);
                        try {
                            int[] values = new int[value];
                            for (int index = 0; index < value; index++) {
                                values[index] = result.readInt();
                            }
                            intArrayBinaryTag2 = IntArrayBinaryTag.intArrayBinaryTag(values);
                        } catch (Throwable throwable) {
                            if (binaryTagScope != null) {
                                try {
                                    binaryTagScope.close();
                                } catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        if (binaryTagScope != null) {
                            binaryTagScope.close();
                        }
                        return intArrayBinaryTag2;
                    },
                    (result, result2) -> {
                        int[] values;
                        int value2 = (values = IntArrayBinaryTagImpl.value(result)).length;
                        result2.writeInt(value2);
                        for (int index = 0; index < value2; index++) {
                            result2.writeInt(values[index]);
                        }
                    });

    public static final BinaryTagType<LongArrayBinaryTag> LONG_ARRAY =
            BinaryTagType.register(
                    LongArrayBinaryTag.class,
                    (byte) 12,
                    result -> {
                        LongArrayBinaryTag longArrayBinaryTag2;
                        int value = result.readInt();
                        BinaryTagScope binaryTagScope = TrackingDataInput.enter(result, value * 8L);
                        try {
                            long[] values = new long[value];
                            for (int index = 0; index < value; index++) {
                                values[index] = result.readLong();
                            }
                            longArrayBinaryTag2 = LongArrayBinaryTag.longArrayBinaryTag(values);
                        } catch (Throwable throwable) {
                            if (binaryTagScope != null) {
                                try {
                                    binaryTagScope.close();
                                } catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        if (binaryTagScope != null) {
                            binaryTagScope.close();
                        }
                        return longArrayBinaryTag2;
                    },
                    (result, result2) -> {
                        long[] values;
                        int value2 = (values = LongArrayBinaryTagImpl.value(result)).length;
                        result2.writeInt(value2);
                        for (int index = 0; index < value2; index++) {
                            result2.writeLong(values[index]);
                        }
                    });

    public static final BinaryTagType<BinaryTag> LIST_WILDCARD =
            new BinaryTagType.Impl<>(
                    BinaryTag.class,
                    (byte) 127,
                    result -> {
                        throw new IllegalArgumentException(
                                "Unable to read values of placeholder type. This tag type exists only to indicate heterogeneous lists");
                    },
                    (result, result2) -> {
                        throw new IllegalArgumentException(
                                "Unable to write values of placeholder type. This tag type exists only to indicate heterogeneous lists");
                    });

    private BinaryTagTypes() {}
}
