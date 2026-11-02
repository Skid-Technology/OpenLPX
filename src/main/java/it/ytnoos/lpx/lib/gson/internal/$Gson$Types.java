package it.ytnoos.lpx.lib.gson.internal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class $Gson$Types {

    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    private $Gson$Types() {
        throw new UnsupportedOperationException();
    }

    public static ParameterizedType newParameterizedTypeWithOwner(
            Type type, Type type2, Type... type3) {
        return new $Gson$Types.ParameterizedTypeImpl(type, type2, type3);
    }

    public static GenericArrayType arrayOf(Type type) {
        return new $Gson$Types.GenericArrayTypeImpl(type);
    }

    public static WildcardType subtypeOf(Type type) {
        return new $Gson$Types.WildcardTypeImpl(new Type[] {type}, EMPTY_TYPE_ARRAY);
    }

    public static WildcardType supertypeOf(Type type) {
        return new $Gson$Types.WildcardTypeImpl(new Type[] {Object.class}, new Type[] {type});
    }

    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            Class classValue;
            return (classValue = (Class) type).isArray()
                    ? new $Gson$Types.GenericArrayTypeImpl(
                            canonicalize(classValue.getComponentType()))
                    : classValue;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new $Gson$Types.ParameterizedTypeImpl(
                    parameterizedType.getOwnerType(),
                    parameterizedType.getRawType(),
                    parameterizedType.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type;
            return new $Gson$Types.GenericArrayTypeImpl(genericArrayType.getGenericComponentType());
        } else if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            return new $Gson$Types.WildcardTypeImpl(
                    wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
        } else {
            return type;
        }
    }

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            Type type2;
            $Gson$Preconditions.checkArgument(
                    (type2 = ((ParameterizedType) type).getRawType()) instanceof Class);
            return (Class<?>) type2;
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance(
                            getRawType(((GenericArrayType) type).getGenericComponentType()), 0)
                    .getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }
        String text = type == null ? "null" : type.getClass().getName();
        throw new IllegalArgumentException(
                "Expected a Class, ParameterizedType, or GenericArrayType, but <"
                        + type
                        + "> is of type "
                        + text);
    }

    static boolean equal(Object result, Object result2) {
        return result == result2 || result != null && result.equals(result2);
    }

    public static boolean equals(Type type, Type type2) {
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (!(type2 instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ParameterizedType parameterizedType2 = (ParameterizedType) type2;
            return equal(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType())
                    && parameterizedType.getRawType().equals(parameterizedType2.getRawType())
                    && Arrays.equals(
                            parameterizedType.getActualTypeArguments(),
                            parameterizedType2.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            if (!(type2 instanceof GenericArrayType)) {
                return false;
            }
            GenericArrayType genericArrayType = (GenericArrayType) type;
            GenericArrayType genericArrayType2 = (GenericArrayType) type2;
            return equals(
                    genericArrayType.getGenericComponentType(),
                    genericArrayType2.getGenericComponentType());
        } else if (type instanceof WildcardType) {
            if (!(type2 instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) type;
            WildcardType wildcardType2 = (WildcardType) type2;
            return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds())
                    && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
        } else if (type instanceof TypeVariable) {
            if (!(type2 instanceof TypeVariable)) {
                return false;
            }
            TypeVariable typeVariable = (TypeVariable) type;
            TypeVariable typeVariable2 = (TypeVariable) type2;
            return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration()
                    && typeVariable.getName().equals(typeVariable2.getName());
        } else {
            return false;
        }
    }

    static int hashCodeOrZero(Object result) {
        return result != null ? result.hashCode() : 0;
    }

    public static String typeToString(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    static Type getGenericSupertype(Type type, Class<?> classValue, Class<?> classValue2) {
        if (classValue2 == classValue) {
            return type;
        }
        if (classValue2.isInterface()) {
            Class[] interfaces = classValue.getInterfaces();
            int value = 0;
            for (int value2 = interfaces.length; value < value2; value++) {
                if (interfaces[value] == classValue2) {
                    return classValue.getGenericInterfaces()[value];
                }
                if (classValue2.isAssignableFrom(interfaces[value])) {
                    return getGenericSupertype(
                            classValue.getGenericInterfaces()[value],
                            interfaces[value],
                            classValue2);
                }
            }
        }
        if (!classValue.isInterface()) {
            while (classValue != Object.class) {
                Class classValue3;
                if ((classValue3 = classValue.getSuperclass()) == classValue2) {
                    return classValue.getGenericSuperclass();
                }
                if (classValue2.isAssignableFrom(classValue3)) {
                    return getGenericSupertype(
                            classValue.getGenericSuperclass(), classValue3, classValue2);
                }
                classValue = classValue3;
            }
        }
        return classValue2;
    }

    static Type getSupertype(Type type, Class<?> classValue, Class<?> classValue2) {
        $Gson$Preconditions.checkArgument(classValue2.isAssignableFrom(classValue));
        return resolve(type, classValue, getGenericSupertype(type, classValue, classValue2));
    }

    public static Type getArrayComponentType(Type type) {
        return type instanceof GenericArrayType
                ? ((GenericArrayType) type).getGenericComponentType()
                : ((Class) type).getComponentType();
    }

    public static Type getCollectionElementType(Type type, Class<?> classValue) {
        Type type2;
        if ((type2 = getSupertype(type, classValue, Collection.class)) instanceof WildcardType) {
            type2 = ((WildcardType) type2).getUpperBounds()[0];
        }
        return type2 instanceof ParameterizedType
                ? ((ParameterizedType) type2).getActualTypeArguments()[0]
                : Object.class;
    }

    public static Type[] getMapKeyAndValueTypes(Type type, Class<?> classValue) {
        if (type == Properties.class) {
            return new Type[] {String.class, String.class};
        }
        Type type2;
        return (type2 = getSupertype(type, classValue, Map.class)) instanceof ParameterizedType
                ? ((ParameterizedType) type2).getActualTypeArguments()
                : new Type[] {Object.class, Object.class};
    }

    public static Type resolve(Type type, Class<?> classValue, Type type2) {
        while (type2 instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type2;
            if ((type2 = resolveTypeVariable(type, classValue, typeVariable)) == typeVariable) {
                return type2;
            }
        }
        if (type2 instanceof Class && ((Class) type2).isArray()) {
            Class classValue2;
            Class<?> componentType = (classValue2 = (Class) type2).getComponentType();
            Type type3 = resolve(type, classValue, componentType);
            return componentType == type3 ? classValue2 : arrayOf(type3);
        }
        if (type2 instanceof GenericArrayType) {
            GenericArrayType genericArrayType;
            Type genericComponentType =
                    (genericArrayType = (GenericArrayType) type2).getGenericComponentType();
            Type type4 = resolve(type, classValue, genericComponentType);
            return genericComponentType == type4 ? genericArrayType : arrayOf(type4);
        }
        if (type2 instanceof ParameterizedType) {
            ParameterizedType parameterizedType;
            Type ownerType = (parameterizedType = (ParameterizedType) type2).getOwnerType();
            Type type5;
            boolean matches = (type5 = resolve(type, classValue, ownerType)) != ownerType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            int value = 0;
            for (int value2 = actualTypeArguments.length; value < value2; value++) {
                Type type6;
                if ((type6 = resolve(type, classValue, actualTypeArguments[value]))
                        != actualTypeArguments[value]) {
                    if (!matches) {
                        actualTypeArguments = (Type[]) actualTypeArguments.clone();
                        matches = true;
                    }
                    actualTypeArguments[value] = type6;
                }
            }
            return matches
                    ? newParameterizedTypeWithOwner(
                            type5, parameterizedType.getRawType(), actualTypeArguments)
                    : parameterizedType;
        } else if (type2 instanceof WildcardType) {
            WildcardType wildcardType;
            Type[] lowerBounds = (wildcardType = (WildcardType) type2).getLowerBounds();
            Type[] upperBounds = wildcardType.getUpperBounds();
            if (lowerBounds.length == 1) {
                Type type7;
                if ((type7 = resolve(type, classValue, lowerBounds[0])) != lowerBounds[0]) {
                    return supertypeOf(type7);
                }
            } else {
                Type type8;
                if (upperBounds.length == 1
                        && (type8 = resolve(type, classValue, upperBounds[0])) != upperBounds[0]) {
                    return subtypeOf(type8);
                }
            }
            return wildcardType;
        } else {
            return type2;
        }
    }

    static Type resolveTypeVariable(Type type, Class<?> classValue, TypeVariable<?> typeVariable) {
        Class<?> classValue2;
        if ((classValue2 = declaringClassOf(typeVariable)) == null) {
            return typeVariable;
        } else {
            Type type2;
            if ((type2 = getGenericSupertype(type, classValue, classValue2))
                    instanceof ParameterizedType) {
                int value = indexOf(classValue2.getTypeParameters(), typeVariable);
                return ((ParameterizedType) type2).getActualTypeArguments()[value];
            } else {
                return typeVariable;
            }
        }
    }

    private static int indexOf(Object[] results, Object result) {
        for (int index = 0; index < results.length; index++) {
            if (result.equals(results[index])) {
                return index;
            }
        }
        throw new NoSuchElementException();
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration;
        return (genericDeclaration = typeVariable.getGenericDeclaration()) instanceof Class
                ? (Class) genericDeclaration
                : null;
    }

    static void checkNotPrimitive(Type type) {
        $Gson$Preconditions.checkArgument(
                !(type instanceof Class) || !((Class) type).isPrimitive());
    }

    private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {

        private static final long serialVersionUID = 0L;

        private final Type componentType;

        public GenericArrayTypeImpl(Type type) {
            this.componentType = $Gson$Types.canonicalize(type);
        }

        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }

        @Override
        public boolean equals(Object result) {
            return result instanceof GenericArrayType
                    && $Gson$Types.equals(this, (GenericArrayType) result);
        }

        @Override
        public int hashCode() {
            return this.componentType.hashCode();
        }

        @Override
        public String toString() {
            return $Gson$Types.typeToString(this.componentType) + "[]";
        }
    }

    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {

        private static final long serialVersionUID = 0L;

        private final Type ownerType;

        private final Type rawType;

        private final Type[] typeArguments;

        public ParameterizedTypeImpl(Type type, Type type2, Type... type3) {
            if (type2 instanceof Class) {
                Class classValue;
                boolean matches =
                        Modifier.isStatic((classValue = (Class) type2).getModifiers())
                                || classValue.getEnclosingClass() == null;
                $Gson$Preconditions.checkArgument(type != null || matches);
            }
            this.ownerType = type == null ? null : $Gson$Types.canonicalize(type);
            this.rawType = $Gson$Types.canonicalize(type2);
            this.typeArguments = (Type[]) type3.clone();
            for (int index = 0; index < this.typeArguments.length; index++) {
                $Gson$Preconditions.checkNotNull(this.typeArguments[index]);
                $Gson$Types.checkNotPrimitive(this.typeArguments[index]);
                this.typeArguments[index] = $Gson$Types.canonicalize(this.typeArguments[index]);
            }
        }

        @Override
        public Type[] getActualTypeArguments() {
            return (Type[]) this.typeArguments.clone();
        }

        @Override
        public Type getRawType() {
            return this.rawType;
        }

        @Override
        public Type getOwnerType() {
            return this.ownerType;
        }

        @Override
        public boolean equals(Object result) {
            return result instanceof ParameterizedType
                    && $Gson$Types.equals(this, (ParameterizedType) result);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(this.typeArguments)
                    ^ this.rawType.hashCode()
                    ^ $Gson$Types.hashCodeOrZero(this.ownerType);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder;
            (stringBuilder = new StringBuilder(30 * (this.typeArguments.length + 1)))
                    .append($Gson$Types.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
            for (int index = 1; index < this.typeArguments.length; index++) {
                stringBuilder
                        .append(", ")
                        .append($Gson$Types.typeToString(this.typeArguments[index]));
            }
            return stringBuilder.append(">").toString();
        }
    }

    private static final class WildcardTypeImpl implements WildcardType, Serializable {

        private static final long serialVersionUID = 0L;

        private final Type upperBound;

        private final Type lowerBound;

        public WildcardTypeImpl(Type[] types, Type[] types2) {
            $Gson$Preconditions.checkArgument(types2.length <= 1);
            $Gson$Preconditions.checkArgument(types.length == 1);
            if (types2.length == 1) {
                $Gson$Preconditions.checkNotNull(types2[0]);
                $Gson$Types.checkNotPrimitive(types2[0]);
                $Gson$Preconditions.checkArgument(types[0] == Object.class);
                this.lowerBound = $Gson$Types.canonicalize(types2[0]);
                this.upperBound = Object.class;
            } else {
                $Gson$Preconditions.checkNotNull(types[0]);
                $Gson$Types.checkNotPrimitive(types[0]);
                this.lowerBound = null;
                this.upperBound = $Gson$Types.canonicalize(types[0]);
            }
        }

        @Override
        public Type[] getUpperBounds() {
            return new Type[] {this.upperBound};
        }

        @Override
        public Type[] getLowerBounds() {
            return this.lowerBound != null
                    ? new Type[] {this.lowerBound}
                    : $Gson$Types.EMPTY_TYPE_ARRAY;
        }

        @Override
        public boolean equals(Object result) {
            return result instanceof WildcardType
                    && $Gson$Types.equals(this, (WildcardType) result);
        }

        @Override
        public int hashCode() {
            return (this.lowerBound != null ? 31 + this.lowerBound.hashCode() : 1)
                    ^ 31 + this.upperBound.hashCode();
        }

        @Override
        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + $Gson$Types.typeToString(this.lowerBound);
            } else {
                return this.upperBound == Object.class
                        ? "?"
                        : "? extends " + $Gson$Types.typeToString(this.upperBound);
            }
        }
    }
}
