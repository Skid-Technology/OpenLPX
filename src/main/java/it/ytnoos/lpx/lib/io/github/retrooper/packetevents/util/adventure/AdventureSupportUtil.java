package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.BackwardCompatUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.Reflection;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Method;

@NullMarked
@Internal
public class AdventureSupportUtil {

    private static final Method CLICK_EVENT_VALUE =
            Reflection.getMethod(ClickEvent.class, "value", 0);

    private AdventureSupportUtil() {}

    public static String getStringValue(ClickEvent clickEvent) {
        if (BackwardCompatUtil.IS_4_22_0_OR_NEWER) {
            return ((ClickEvent.Payload.Text) clickEvent.payload()).value();
        }
        try {
            return (String) CLICK_EVENT_VALUE.invoke(clickEvent);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public static int getIntValue(ClickEvent clickEvent) {
        if (BackwardCompatUtil.IS_4_22_0_OR_NEWER) {
            return ((ClickEvent.Payload.Int) clickEvent.payload()).integer();
        }
        try {
            return Integer.parseInt((String) CLICK_EVENT_VALUE.invoke(clickEvent));
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }
}
