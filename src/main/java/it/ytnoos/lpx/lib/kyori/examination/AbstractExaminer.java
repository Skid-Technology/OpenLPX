package it.ytnoos.lpx.lib.kyori.examination;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntFunction;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class AbstractExaminer<R> implements Examiner<R> {

    @NotNull
    @Override
    public R examine(@Nullable Object result) {
        if (result == null) {
            return this.nil();
        }
        if (result instanceof String) {
            return this.examine((String) result);
        }
        if (result instanceof Examinable) {
            return this.examine((Examinable) result);
        }
        if (result instanceof Collection) {
            return ((R) this.collection((Collection) result));
        }
        if (result instanceof Map) {
            return ((R) this.map((Map) result));
        }
        if (result.getClass().isArray()) {
            Class<?> classValue;
            if ((classValue = result.getClass().getComponentType()).isPrimitive()) {
                if (classValue == boolean.class) {
                    return this.examine((boolean[]) result);
                }
                if (classValue == byte.class) {
                    return this.examine((byte[]) result);
                }
                if (classValue == char.class) {
                    return this.examine((char[]) result);
                }
                if (classValue == double.class) {
                    return this.examine((double[]) result);
                }
                if (classValue == float.class) {
                    return this.examine((float[]) result);
                }
                if (classValue == int.class) {
                    return this.examine((int[]) result);
                }
                if (classValue == long.class) {
                    return this.examine((long[]) result);
                }
                if (classValue == short.class) {
                    return this.examine((short[]) result);
                }
            }
            return this.array((Object[]) result);
        } else {
            if (result instanceof Boolean) {
                return this.examine(((Boolean) result).booleanValue());
            }
            if (result instanceof Character) {
                return this.examine(((Character) result).charValue());
            }
            if (result instanceof Number) {
                if (result instanceof Byte) {
                    return this.examine(((Byte) result).byteValue());
                }
                if (result instanceof Double) {
                    return this.examine(((Double) result).doubleValue());
                }
                if (result instanceof Float) {
                    return this.examine(((Float) result).floatValue());
                }
                if (result instanceof Integer) {
                    return this.examine(((Integer) result).intValue());
                }
                if (result instanceof Long) {
                    return this.examine(((Long) result).longValue());
                }
                if (result instanceof Short) {
                    return this.examine(((Short) result).shortValue());
                }
            } else if (result instanceof BaseStream) {
                if (result instanceof Stream) {
                    return ((R) this.stream((Stream) result));
                }
                if (result instanceof DoubleStream) {
                    return this.stream((DoubleStream) result);
                }
                if (result instanceof IntStream) {
                    return this.stream((IntStream) result);
                }
                if (result instanceof LongStream) {
                    return this.stream((LongStream) result);
                }
            }
            return this.scalar(result);
        }
    }

    @NotNull
    private <E> R array(E @NotNull [] es) {
        Stream<E> stream2 = Arrays.stream(es);
        return this.array(es, stream2.map(this::examine));
    }

    @NotNull
    protected abstract <E> R array(E @NotNull [] es, @NotNull Stream<R> stream);

    @NotNull
    private <E> R collection(@NotNull Collection<E> values) {
        Stream<E> stream2 = values.stream();
        return this.collection(values, stream2.map(this::examine));
    }

    @NotNull
    protected abstract <E> R collection(@NotNull Collection<E> values, @NotNull Stream<R> stream);

    @NotNull
    @Override
    public R examine(@NotNull String text, @NotNull Stream<? extends ExaminableProperty> stream) {
        return this.examinable(
                text,
                stream.map(
                        result -> new SimpleImmutableEntry<>(result.name(), result.examine(this))));
    }

    @NotNull
    protected abstract R examinable(@NotNull String text, @NotNull Stream<Entry<String, R>> stream);

    @NotNull
    private <K, V> R map(@NotNull Map<K, V> entries) {
        Stream<Entry<K, V>> stream2 = entries.entrySet().stream();
        return this.map(
                entries,
                stream2.map(
                        result ->
                                new SimpleImmutableEntry<>(
                                        this.examine(result.getKey()),
                                        this.examine(result.getValue()))));
    }

    @NotNull
    protected abstract <K, V> R map(
            @NotNull Map<K, V> entries, @NotNull Stream<Entry<R, R>> stream);

    @NotNull
    protected abstract R nil();

    @NotNull
    protected abstract R scalar(@NotNull Object result);

    @NotNull
    protected abstract <T> R stream(@NotNull Stream<T> stream2);

    @NotNull
    protected abstract R stream(@NotNull DoubleStream doubleStream);

    @NotNull
    protected abstract R stream(@NotNull IntStream intStream);

    @NotNull
    protected abstract R stream(@NotNull LongStream longStream);

    @NotNull
    @Override
    public R examine(boolean @Nullable [] matchess) {
        if (matchess == null) {
            return this.nil();
        }
        int value = matchess.length;
        return this.array(value, result -> this.examine(matchess[result]));
    }

    @NotNull
    @Override
    public R examine(byte @Nullable [] values) {
        if (values == null) {
            return this.nil();
        }
        int value = values.length;
        return this.array(value, result -> this.examine(values[result]));
    }

    @NotNull
    @Override
    public R examine(char @Nullable [] values) {
        if (values == null) {
            return this.nil();
        }
        int value = values.length;
        return this.array(value, result -> this.examine(values[result]));
    }

    @NotNull
    @Override
    public R examine(double @Nullable [] values) {
        if (values == null) {
            return this.nil();
        }
        int value = values.length;
        return this.array(value, result -> this.examine(values[result]));
    }

    @NotNull
    @Override
    public R examine(float @Nullable [] values) {
        if (values == null) {
            return this.nil();
        }
        int value = values.length;
        return this.array(value, result -> this.examine(values[result]));
    }

    @NotNull
    @Override
    public R examine(int @Nullable [] values) {
        if (values == null) {
            return this.nil();
        }
        int value = values.length;
        return this.array(value, result -> this.examine(values[result]));
    }

    @NotNull
    @Override
    public R examine(long @Nullable [] values) {
        if (values == null) {
            return this.nil();
        }
        int value = values.length;
        return this.array(value, result -> this.examine(values[result]));
    }

    @NotNull
    @Override
    public R examine(short @Nullable [] values) {
        if (values == null) {
            return this.nil();
        }
        int value = values.length;
        return this.array(value, result -> this.examine(values[result]));
    }

    @NotNull
    protected abstract R array(int value, IntFunction<R> intFunction);
}
