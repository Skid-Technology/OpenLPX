package it.ytnoos.lpx.utils;

import it.ytnoos.lpx.compat.ReflectionNames;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlockStateUtils {

    private static final Map<Class<?>, Method> ENTRIES;

    private static final String TEXT = "getState";

    public static BlockState getBlockState(Block block) {
        try {
            Method method =
                    ENTRIES.computeIfAbsent(
                            block.getClass(),
                            result -> {
                                try {
                                    String text = TEXT;
                                    Class[] classs = new Class[] {boolean.class};
                                    Class classValue = ((Class) result);
                                    String text2 =
                                            ReflectionNames.methodName(text, classValue, classs);
                                    return classValue.getMethod(text2, classs);
                                } catch (NoSuchMethodException noSuchMethodException) {
                                    return null;
                                }
                            });
            if (method != null) {
                Object[] results = new Object[] {false};
                return (BlockState) method.invoke(block, results);
            }
        } catch (Exception exception) {
        }
        return block.getState();
    }

    static {
        ConcurrentHashMap entries = new ConcurrentHashMap();
        ENTRIES = entries;
    }
}
