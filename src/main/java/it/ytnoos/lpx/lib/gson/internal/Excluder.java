package it.ytnoos.lpx.lib.gson.internal;

import it.ytnoos.lpx.lib.gson.ExclusionStrategy;
import it.ytnoos.lpx.lib.gson.FieldAttributes;
import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.annotations.Expose;
import it.ytnoos.lpx.lib.gson.annotations.Since;
import it.ytnoos.lpx.lib.gson.annotations.Until;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Excluder implements TypeAdapterFactory, Cloneable {

    public static final Excluder DEFAULT = new Excluder();

    private double version = -1.0;

    private int modifiers = 136;

    private boolean serializeInnerClasses = true;

    private boolean requireExpose;

    private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();

    private List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();

    protected Excluder clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    public Excluder withVersion(double value) {
        Excluder excluder;
        (excluder = this.clone()).version = value;
        return excluder;
    }

    public Excluder withModifiers(int... value) {
        Excluder excluder;
        (excluder = this.clone()).modifiers = 0;
        int value2 = value.length;
        for (int index = 0; index < value2; index++) {
            int value3 = ((int[]) value)[index];
            excluder.modifiers |= value3;
        }
        return excluder;
    }

    public Excluder disableInnerClassSerialization() {
        Excluder excluder;
        (excluder = this.clone()).serializeInnerClasses = false;
        return excluder;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() {
        Excluder excluder;
        (excluder = this.clone()).requireExpose = true;
        return excluder;
    }

    public Excluder withExclusionStrategy(
            ExclusionStrategy exclusionStrategy, boolean matches, boolean matches2) {
        Excluder excluder = this.clone();
        if (matches) {
            excluder.serializationStrategies = new ArrayList<>(this.serializationStrategies);
            excluder.serializationStrategies.add(exclusionStrategy);
        }
        if (matches2) {
            excluder.deserializationStrategies = new ArrayList<>(this.deserializationStrategies);
            excluder.deserializationStrategies.add(exclusionStrategy);
        }
        return excluder;
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        final boolean matches = this.excludeClass(rawType, true);
        final boolean matches2 = this.excludeClass(rawType, false);
        return !matches && !matches2
                ? null
                : new TypeAdapter<T>() {

                    private TypeAdapter<T> delegate;

                    @Override
                    public T read(JsonReader result) throws IOException {
                        if (matches2) {
                            result.skipValue();
                            return null;
                        } else {
                            return (T) this.delegate().read(result);
                        }
                    }

                    @Override
                    public void write(JsonWriter result, T result2) throws IOException {
                        if (matches) {
                            result.nullValue();
                        } else {
                            this.delegate().write(result, result2);
                        }
                    }

                    private TypeAdapter<T> delegate() {
                        TypeAdapter result2 = this.delegate;
                        return this.delegate != null
                                ? result2
                                : (this.delegate =
                                        gson.getDelegateAdapter(Excluder.this, typeToken));
                    }
                };
    }

    public boolean excludeField(Field field, boolean matches) {
        if ((this.modifiers & field.getModifiers()) != 0) {
            return true;
        }
        if (this.version != -1.0
                && !this.isValidVersion(
                        field.getAnnotation(Since.class), field.getAnnotation(Until.class))) {
            return true;
        }
        if (field.isSynthetic()) {
            return true;
        }
        Expose expose;
        if (!this.requireExpose
                || (expose = field.getAnnotation(Expose.class)) != null
                        && (matches ? expose.serialize() : expose.deserialize())) {
            if (!this.serializeInnerClasses && this.isInnerClass(field.getType())) {
                return true;
            }
            if (this.isAnonymousOrLocal(field.getType())) {
                return true;
            }
            List<ExclusionStrategy> values;
            if (!(values = matches ? this.serializationStrategies : this.deserializationStrategies)
                    .isEmpty()) {
                FieldAttributes fieldAttributes = new FieldAttributes(field);
                Iterator<ExclusionStrategy> iterator2 = values.iterator();
                while (iterator2.hasNext()) {
                    if (((ExclusionStrategy) iterator2.next()).shouldSkipField(fieldAttributes)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean excludeClass(Class<?> classValue, boolean matches) {
        if (this.version != -1.0
                && !this.isValidVersion(
                        classValue.getAnnotation(Since.class),
                        classValue.getAnnotation(Until.class))) {
            return true;
        }
        if (!this.serializeInnerClasses && this.isInnerClass(classValue)) {
            return true;
        }
        if (this.isAnonymousOrLocal(classValue)) {
            return true;
        }
        Iterator<ExclusionStrategy> iterator2 =
                (matches ? this.serializationStrategies : this.deserializationStrategies)
                        .iterator();
        while (iterator2.hasNext()) {
            if (((ExclusionStrategy) iterator2.next()).shouldSkipClass(classValue)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnonymousOrLocal(Class<?> classValue) {
        return !Enum.class.isAssignableFrom(classValue)
                && (classValue.isAnonymousClass() || classValue.isLocalClass());
    }

    private boolean isInnerClass(Class<?> classValue) {
        return classValue.isMemberClass() && !this.isStatic(classValue);
    }

    private boolean isStatic(Class<?> classValue) {
        return (classValue.getModifiers() & 8) != 0;
    }

    private boolean isValidVersion(Since since, Until until) {
        return this.isValidSince(since) && this.isValidUntil(until);
    }

    private boolean isValidSince(Since since) {
        return since == null || !(since.value() > this.version);
    }

    private boolean isValidUntil(Until until) {
        return until == null || !(until.value() <= this.version);
    }
}
