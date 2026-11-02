package it.ytnoos.lpx.lib.kyori.adventure.util;

import java.util.ServiceLoader;

final class Services0 {

    private Services0() {}

    static <S> ServiceLoader<S> loader(Class<S> classValue) {
        return ServiceLoader.load(classValue, classValue.getClassLoader());
    }
}
