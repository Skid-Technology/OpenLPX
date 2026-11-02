package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.BackwardCompatUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.GsonDataComponentValue;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.codec.NBTCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.Reflection;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValueConverterRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

@NullMarked
@Internal
public final class AdventureConversionInjector {

    private AdventureConversionInjector() {}

    public static void inject() {
        if (BackwardCompatUtil.IS_4_17_0_OR_NEWER) {
            try {
                Class<?> classValue =
                        Class.forName(
                                "it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValueConversionImpl");
                Class<?> classValue2 =
                        Class.forName(
                                "it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValueConverterRegistry$RegisteredConversion");
                Class<?> classValue3 =
                        Class.forName(
                                "it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValueConverterRegistry$ConversionCache");
                BiFunction biFunction =
                        (result, result2) ->
                                GsonDataComponentValue.gsonDataComponentValue(
                                        NBTCodec.nbtToJson(
                                                ((NbtTagHolder) result2).getTag(), false));
                Constructor constructor;
                (constructor =
                                classValue.getDeclaredConstructor(
                                        Class.class, Class.class, BiFunction.class))
                        .setAccessible(true);
                DataComponentValueConverterRegistry.Conversion conversion =
                        (DataComponentValueConverterRegistry.Conversion)
                                constructor.newInstance(
                                        NbtTagHolder.class,
                                        GsonDataComponentValue.class,
                                        biFunction);
                Constructor constructor2;
                (constructor2 =
                                classValue2.getDeclaredConstructor(
                                        Key.class,
                                        DataComponentValueConverterRegistry.Conversion.class))
                        .setAccessible(true);
                Key key2 = Key.key("packetevents:hack/nbt2gson");
                Object result3 = constructor2.newInstance(key2, conversion);
                ConcurrentHashMap entries;
                (entries = new ConcurrentHashMap(1)).put(GsonDataComponentValue.class, result3);
                ((Map) Reflection.getField(classValue3, "CACHE").get(null))
                        .put(NbtTagHolder.class, entries);
            } catch (ReflectiveOperationException reflectiveOperationException) {
                throw new RuntimeException(
                        "Failed to inject into conversion cache", reflectiveOperationException);
            }
        }
    }

    public static void uninject() {
        if (BackwardCompatUtil.IS_4_17_0_OR_NEWER) {
            try {
                ((Map)
                                Reflection.getField(
                                                Class.forName(
                                                        "it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValueConverterRegistry$ConversionCache"),
                                                "CACHE")
                                        .get(null))
                        .remove(NbtTagHolder.class);
            } catch (ReflectiveOperationException reflectiveOperationException) {
                throw new RuntimeException(
                        "Failed to uninject from conversion cache", reflectiveOperationException);
            }
        }
    }
}
