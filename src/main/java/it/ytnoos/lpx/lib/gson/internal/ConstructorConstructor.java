package it.ytnoos.lpx.lib.gson.internal;

import it.ytnoos.lpx.lib.gson.InstanceCreator;
import it.ytnoos.lpx.lib.gson.JsonIOException;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ConstructorConstructor {

    private final Map<Type, InstanceCreator<?>> instanceCreators;

    public ConstructorConstructor(Map<Type, InstanceCreator<?>> entries) {
        this.instanceCreators = entries;
    }

    public <T> ObjectConstructor<T> get(TypeToken<T> typeToken) {
        final Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        final InstanceCreator instanceCreator;
        if ((instanceCreator = this.instanceCreators.get(type)) != null) {
            return new ObjectConstructor<T>() {

                @Override
                public T construct() {
                    return (T) instanceCreator.createInstance(type);
                }
            };
        }
        final InstanceCreator instanceCreator2;
        if ((instanceCreator2 = this.instanceCreators.get(rawType)) != null) {
            return new ObjectConstructor<T>() {

                @Override
                public T construct() {
                    return (T) instanceCreator2.createInstance(type);
                }
            };
        }
        ObjectConstructor objectConstructor;
        if ((objectConstructor = this.newDefaultConstructor(rawType)) != null) {
            return objectConstructor;
        }
        ObjectConstructor objectConstructor2;
        return (objectConstructor2 = this.newDefaultImplementationConstructor(type, rawType))
                        != null
                ? objectConstructor2
                : this.newUnsafeAllocator(type, rawType);
    }

    private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> classValue) {
        try {
            final Constructor constructor;
            if (!(constructor = classValue.getDeclaredConstructor()).isAccessible()) {
                constructor.setAccessible(true);
            }
            return new ObjectConstructor<T>() {

                @Override
                public T construct() {
                    try {
                        return (T) constructor.newInstance((Object[]) (Object) null);
                    } catch (InstantiationException instantiationException) {
                        throw new RuntimeException(
                                "Failed to invoke " + constructor + " with no args",
                                instantiationException);
                    } catch (InvocationTargetException invocationTargetException) {
                        throw new RuntimeException(
                                "Failed to invoke " + constructor + " with no args",
                                invocationTargetException.getTargetException());
                    } catch (IllegalAccessException result) {
                        throw new AssertionError(result);
                    }
                }
            };
        } catch (NoSuchMethodException result) {
            return null;
        }
    }

    private <T> ObjectConstructor<T> newDefaultImplementationConstructor(
            final Type type, Class<? super T> result) {
        if (Collection.class.isAssignableFrom(result)) {
            if (SortedSet.class.isAssignableFrom(result)) {
                return new ObjectConstructor<T>() {

                    @Override
                    public T construct() {
                        return (T) (new TreeSet());
                    }
                };
            } else if (EnumSet.class.isAssignableFrom(result)) {
                return new ObjectConstructor<T>() {

                    @Override
                    public T construct() {
                        if (type instanceof ParameterizedType) {
                            Type result;
                            if ((result = ((ParameterizedType) type).getActualTypeArguments()[0])
                                    instanceof Class) {
                                return (T) EnumSet.noneOf((Class) result);
                            } else {
                                throw new JsonIOException(
                                        "Invalid EnumSet type: " + type.toString());
                            }
                        } else {
                            throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                        }
                    }
                };
            } else if (Set.class.isAssignableFrom(result)) {
                return new ObjectConstructor<T>() {

                    @Override
                    public T construct() {
                        return (T) (new LinkedHashSet());
                    }
                };
            } else {
                return Queue.class.isAssignableFrom(result)
                        ? new ObjectConstructor<T>() {

                            @Override
                            public T construct() {
                                return (T) (new ArrayDeque());
                            }
                        }
                        : new ObjectConstructor<T>() {

                            @Override
                            public T construct() {
                                return (T) (new ArrayList());
                            }
                        };
            }
        } else if (Map.class.isAssignableFrom(result)) {
            if (ConcurrentNavigableMap.class.isAssignableFrom(result)) {
                return new ObjectConstructor<T>() {

                    @Override
                    public T construct() {
                        return (T) (new ConcurrentSkipListMap());
                    }
                };
            } else if (ConcurrentMap.class.isAssignableFrom(result)) {
                return new ObjectConstructor<T>() {

                    @Override
                    public T construct() {
                        return (T) (new ConcurrentHashMap());
                    }
                };
            } else if (SortedMap.class.isAssignableFrom(result)) {
                return new ObjectConstructor<T>() {

                    @Override
                    public T construct() {
                        return (T) (new TreeMap());
                    }
                };
            } else {
                return type instanceof ParameterizedType
                                && !String.class.isAssignableFrom(
                                        TypeToken.get(
                                                        ((ParameterizedType) type)
                                                                .getActualTypeArguments()[0])
                                                .getRawType())
                        ? new ObjectConstructor<T>() {

                            @Override
                            public T construct() {
                                return (T) (new LinkedHashMap());
                            }
                        }
                        : new ObjectConstructor<T>() {

                            @Override
                            public T construct() {
                                return (T) (new LinkedTreeMap());
                            }
                        };
            }
        } else {
            return null;
        }
    }

    private <T> ObjectConstructor<T> newUnsafeAllocator(
            final Type type, final Class<? super T> classValue) {
        return new ObjectConstructor<T>() {

            private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();

            @Override
            public T construct() {
                try {
                    return (T) this.unsafeAllocator.newInstance(classValue);
                } catch (Exception exception) {
                    throw new RuntimeException(
                            "Unable to invoke no-args constructor for "
                                    + type
                                    + ". Register an InstanceCreator with Gson for this type may fix this problem.",
                            exception);
                }
            }
        };
    }

    @Override
    public String toString() {
        return this.instanceCreators.toString();
    }
}
