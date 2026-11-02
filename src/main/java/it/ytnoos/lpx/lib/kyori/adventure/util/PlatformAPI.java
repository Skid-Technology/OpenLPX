package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.TYPE,
    ElementType.METHOD,
    ElementType.CONSTRUCTOR,
    ElementType.PACKAGE,
    ElementType.ANNOTATION_TYPE
})
@Internal
public @interface PlatformAPI {}
