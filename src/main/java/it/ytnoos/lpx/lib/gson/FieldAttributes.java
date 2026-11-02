package it.ytnoos.lpx.lib.gson;

import it.ytnoos.lpx.lib.gson.internal.$Gson$Preconditions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public final class FieldAttributes {

    private final Field field;

    public FieldAttributes(Field field2) {
        $Gson$Preconditions.checkNotNull(field2);
        this.field = field2;
    }

    public Class<?> getDeclaringClass() {
        return this.field.getDeclaringClass();
    }

    public String getName() {
        return this.field.getName();
    }

    public Type getDeclaredType() {
        return this.field.getGenericType();
    }

    public Class<?> getDeclaredClass() {
        return this.field.getType();
    }

    public <T extends Annotation> T getAnnotation(Class<T> classValue) {
        return this.field.getAnnotation(classValue);
    }

    public Collection<Annotation> getAnnotations() {
        return Arrays.asList(this.field.getAnnotations());
    }

    public boolean hasModifier(int value) {
        return (this.field.getModifiers() & value) != 0;
    }

    Object get(Object result) throws IllegalAccessException {
        return this.field.get(result);
    }

    boolean isSynthetic() {
        return this.field.isSynthetic();
    }
}
