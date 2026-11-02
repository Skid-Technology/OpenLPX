package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion;

import it.ytnoos.lpx.compat.ReflectionNames;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.SpigotReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CustomPipelineUtil {

    private static Method DECODE_METHOD;

    private static Method ENCODE_METHOD;

    private static Method MTM_DECODE;

    private static Method MTM_ENCODE;

    public static void init() {
        Class<?> nettyClass = SpigotReflectionUtil.getNettyClass("channel.ChannelHandlerContext");
        try {
            Class<?> classValue = SpigotReflectionUtil.BYTE_TO_MESSAGE_DECODER;
            Class[] classs =
                    new Class[] {nettyClass, SpigotReflectionUtil.BYTE_BUF_CLASS, List.class};
            DECODE_METHOD =
                    classValue.getDeclaredMethod(
                            ReflectionNames.methodName("decode", classValue, classs), classs);
            DECODE_METHOD.setAccessible(true);
        } catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
        }
        try {
            Class<?> classValue2 = SpigotReflectionUtil.MESSAGE_TO_BYTE_ENCODER;
            Class[] classs2 =
                    new Class[] {nettyClass, Object.class, SpigotReflectionUtil.BYTE_BUF_CLASS};
            ENCODE_METHOD =
                    classValue2.getDeclaredMethod(
                            ReflectionNames.methodName("encode", classValue2, classs2), classs2);
            ENCODE_METHOD.setAccessible(true);
        } catch (NoSuchMethodException noSuchMethodException2) {
            noSuchMethodException2.printStackTrace();
        }
        try {
            Class<?> nettyClass2 =
                    SpigotReflectionUtil.getNettyClass("handler.codec.MessageToMessageDecoder");
            Class[] classs3 = new Class[] {nettyClass, Object.class, List.class};
            MTM_DECODE =
                    nettyClass2.getDeclaredMethod(
                            ReflectionNames.methodName("decode", nettyClass2, classs3), classs3);
            MTM_DECODE.setAccessible(true);
        } catch (NoSuchMethodException noSuchMethodException3) {
            noSuchMethodException3.printStackTrace();
        }
        try {
            Class<?> nettyClass3 =
                    SpigotReflectionUtil.getNettyClass("handler.codec.MessageToMessageEncoder");
            Class[] classs4 = new Class[] {nettyClass, Object.class, List.class};
            MTM_ENCODE =
                    nettyClass3.getDeclaredMethod(
                            ReflectionNames.methodName("encode", nettyClass3, classs4), classs4);
            MTM_ENCODE.setAccessible(true);
        } catch (NoSuchMethodException noSuchMethodException4) {
            noSuchMethodException4.printStackTrace();
        }
    }

    public static List<Object> callDecode(Object result, Object result2, Object result3)
            throws InvocationTargetException {
        ArrayList<Object> values = new ArrayList<>();
        try {
            DECODE_METHOD.invoke(result, result2, result3, values);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        return values;
    }

    public static void callEncode(Object result, Object result2, Object result3, Object result4)
            throws InvocationTargetException {
        try {
            ENCODE_METHOD.invoke(result, result2, result3, result4);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
    }

    public static List<Object> callMTMEncode(Object result, Object result2, Object result3) {
        ArrayList<Object> values = new ArrayList<>();
        try {
            MTM_ENCODE.invoke(result, result2, result3, values);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        return values;
    }

    public static List<Object> callMTMDecode(Object result, Object result2, Object result3)
            throws InvocationTargetException {
        ArrayList<Object> values = new ArrayList<>();
        try {
            MTM_DECODE.invoke(result, result2, result3, values);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        return values;
    }
}
