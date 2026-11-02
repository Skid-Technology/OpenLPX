package it.ytnoos.lpx.lib.kyori.adventure.util;

import it.ytnoos.lpx.lib.kyori.adventure.internal.properties.AdventureProperties;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;

public final class Services {

    private static final boolean SERVICE_LOAD_FAILURES_ARE_FATAL =
            Boolean.TRUE.equals(AdventureProperties.SERVICE_LOAD_FAILURES_ARE_FATAL.value());

    private Services() {}

    @NotNull
    public static <P> Optional<P> service(@NotNull Class<P> classValue) {
        Iterator<P> iterator2 = Services0.loader(classValue).iterator();
        while (iterator2.hasNext()) {
            P p;
            try {
                p = iterator2.next();
            } catch (Throwable throwable) {
                if (!SERVICE_LOAD_FAILURES_ARE_FATAL) {
                    continue;
                }
                throw new IllegalStateException(
                        "Encountered an exception loading service " + classValue, throwable);
            }
            if (iterator2.hasNext()) {
                throw new IllegalStateException(
                        "Expected to find one service " + classValue + ", found multiple");
            }
            return Optional.of((P) p);
        }
        return Optional.empty();
    }

    @NotNull
    public static <P> Optional<P> serviceWithFallback(@NotNull Class<P> classValue) {
        Iterator<P> iterator2 = Services0.loader(classValue).iterator();
        Object result = null;
        while (iterator2.hasNext()) {
            P p;
            try {
                p = iterator2.next();
            } catch (Throwable throwable) {
                if (!SERVICE_LOAD_FAILURES_ARE_FATAL) {
                    continue;
                }
                throw new IllegalStateException(
                        "Encountered an exception loading service " + classValue, throwable);
            }
            if (!(p instanceof Services.Fallback)) {
                return Optional.of((P) p);
            }
            if (result == null) {
                result = p;
            }
        }
        return Optional.ofNullable((P) result);
    }

    public static <P> Set<P> services(Class<? extends P> classValue) {
        ServiceLoader serviceLoader = Services0.loader(classValue);
        HashSet values = new HashSet();
        for (Object result : serviceLoader) {
            try {
            } catch (ServiceConfigurationError serviceConfigurationError) {
                if (!SERVICE_LOAD_FAILURES_ARE_FATAL) {
                    continue;
                }
                throw new IllegalStateException(
                        "Encountered an exception loading a provider for " + classValue + ": ",
                        serviceConfigurationError);
            }
            values.add(result);
        }
        return Collections.unmodifiableSet(values);
    }

    public interface Fallback {}
}
