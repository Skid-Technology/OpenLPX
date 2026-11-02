package it.ytnoos.lpx.lib.gson.reflect;

import it.ytnoos.lpx.lib.gson.internal.$Gson$Preconditions;
import it.ytnoos.lpx.lib.gson.internal.$Gson$Types;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

public class TypeToken<T> {

    final Class<? super T> rawType;

    final Type type;

    final int hashCode;

    public TypeToken() {
        this.type = getSuperclassTypeParameter(this.getClass());
        this.rawType = (Class<? super T>) $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    TypeToken(Type type2) {
        this.type = $Gson$Types.canonicalize($Gson$Preconditions.checkNotNull(type2));
        this.rawType = (Class<? super T>) $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    static Type getSuperclassTypeParameter(Class<?> classValue) {
        Type type;
        if ((type = classValue.getGenericSuperclass()) instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        } else {
            return $Gson$Types.canonicalize(((ParameterizedType) type).getActualTypeArguments()[0]);
        }
    }

    public final Class<? super T> getRawType() {
        return this.rawType;
    }

    public final Type getType() {
        return this.type;
    }

    @Deprecated
    public boolean isAssignableFrom(Class<?> classValue) {
        return this.isAssignableFrom((Type) classValue);
    }

    @Deprecated
    public boolean isAssignableFrom(Type type2) {
        if (type2 == null) {
            return false;
        } else if (this.type.equals(type2)) {
            return true;
        } else if (this.type instanceof Class) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(type2));
        } else if (this.type instanceof ParameterizedType) {
            return isAssignableFrom(type2, (ParameterizedType) this.type, new HashMap<>());
        } else if (!(this.type instanceof GenericArrayType)) {
            throw buildUnexpectedTypeError(
                    this.type, Class.class, ParameterizedType.class, GenericArrayType.class);
        } else {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(type2))
                    && isAssignableFrom(type2, (GenericArrayType) this.type);
        }
    }

    @Deprecated
    public boolean isAssignableFrom(TypeToken<?> typeToken) {
        return this.isAssignableFrom(typeToken.getType());
    }

    private static boolean isAssignableFrom(Type type, GenericArrayType genericArrayType) {
        Type type2;
        if (!((type2 = genericArrayType.getGenericComponentType()) instanceof ParameterizedType)) {
            return true;
        }
        Type type3 = type;
        if (type instanceof GenericArrayType) {
            type3 = ((GenericArrayType) type).getGenericComponentType();
        } else if (type instanceof Class) {
            Class classValue = (Class) type;
            while (classValue.isArray()) {
                classValue = classValue.getComponentType();
            }
            type3 = classValue;
        }
        return isAssignableFrom(type3, (ParameterizedType) type2, new HashMap<>());
    }

    private static boolean isAssignableFrom(
            Type type, ParameterizedType parameterizedType, Map<String, Type> entries) {
        if (type == null) {
            return false;
        }
        if (parameterizedType.equals(type)) {
            return true;
        }
        Class<?> rawType = $Gson$Types.getRawType(type);
        ParameterizedType parameterizedType2 = null;
        if (type instanceof ParameterizedType) {
            parameterizedType2 = (ParameterizedType) type;
        }
        if (parameterizedType2 != null) {
            Type[] actualTypeArguments = parameterizedType2.getActualTypeArguments();
            TypeVariable[] typeParameters = rawType.getTypeParameters();
            for (int index = 0; index < actualTypeArguments.length; index++) {
                Type type2 = actualTypeArguments[index];
                TypeVariable typeVariable = typeParameters[index];
                while (type2 instanceof TypeVariable) {
                    TypeVariable typeVariable2 = (TypeVariable) type2;
                    type2 = (Type) entries.get(typeVariable2.getName());
                }
                entries.put(typeVariable.getName(), type2);
            }
            if (typeEquals(parameterizedType2, parameterizedType, entries)) {
                return true;
            }
        }
        Type[] types;
        int value = (types = rawType.getGenericInterfaces()).length;
        for (int index2 = 0; index2 < value; index2++) {
            if (isAssignableFrom(types[index2], parameterizedType, new HashMap<>(entries))) {
                return true;
            }
        }
        return isAssignableFrom(
                rawType.getGenericSuperclass(), parameterizedType, new HashMap<>(entries));
    }

    private static boolean typeEquals(
            ParameterizedType parameterizedType,
            ParameterizedType parameterizedType2,
            Map<String, Type> entries) {
        if (parameterizedType.getRawType().equals(parameterizedType2.getRawType())) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
            for (int index = 0; index < actualTypeArguments.length; index++) {
                if (!matches(actualTypeArguments[index], actualTypeArguments2[index], entries)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static AssertionError buildUnexpectedTypeError(Type type, Class<?>... classValue) {
        StringBuilder stringBuilder = new StringBuilder("Unexpected type. Expected one of: ");
        int value = classValue.length;
        for (int index = 0; index < value; index++) {
            Class classValue2 = ((Class[]) classValue)[index];
            stringBuilder.append(classValue2.getName()).append(", ");
        }
        stringBuilder
                .append("but got: ")
                .append(type.getClass().getName())
                .append(", for type token: ")
                .append(type.toString())
                .append((char) (46));
        return new AssertionError(stringBuilder.toString());
    }

    private static boolean matches(Type type, Type type2, Map<String, Type> entries) {
        return type2.equals(type)
                || type instanceof TypeVariable
                        && type2.equals(entries.get(((TypeVariable) type).getName()));
    }

    @Override
    public final int hashCode() {
        return this.hashCode;
    }

    @Override
    public final boolean equals(Object result) {
        return result instanceof TypeToken
                && $Gson$Types.equals(this.type, ((TypeToken) result).type);
    }

    @Override
    public final String toString() {
        return $Gson$Types.typeToString(this.type);
    }

    public static TypeToken<?> get(Type type) {
        return new TypeToken(type);
    }

    public static <T> TypeToken<T> get(Class<T> classValue) {
        return new TypeToken<>(classValue);
    }

    public static TypeToken<?> getParameterized(Type type, Type... type2) {
        return new TypeToken($Gson$Types.newParameterizedTypeWithOwner(null, type, type2));
    }

    public static TypeToken<?> getArray(Type type) {
        return new TypeToken($Gson$Types.arrayOf(type));
    }
}
