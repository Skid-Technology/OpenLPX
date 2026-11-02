package it.ytnoos.lpx.lib.gson;

import it.ytnoos.lpx.lib.gson.internal.$Gson$Preconditions;
import it.ytnoos.lpx.lib.gson.internal.Excluder;
import it.ytnoos.lpx.lib.gson.internal.bind.TreeTypeAdapter;
import it.ytnoos.lpx.lib.gson.internal.bind.TypeAdapters;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GsonBuilder {

    private Excluder excluder = Excluder.DEFAULT;

    private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;

    private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;

    private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap<>();

    private final List<TypeAdapterFactory> factories = new ArrayList<>();

    private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList<>();

    private boolean serializeNulls = false;

    private String datePattern;

    private int dateStyle = 2;

    private int timeStyle = 2;

    private boolean complexMapKeySerialization = false;

    private boolean serializeSpecialFloatingPointValues = false;

    private boolean escapeHtmlChars = true;

    private boolean prettyPrinting = false;

    private boolean generateNonExecutableJson = false;

    private boolean lenient = false;

    public GsonBuilder setVersion(double value) {
        this.excluder = this.excluder.withVersion(value);
        return this;
    }

    public GsonBuilder excludeFieldsWithModifiers(int... value) {
        this.excluder = this.excluder.withModifiers(value);
        return this;
    }

    public GsonBuilder generateNonExecutableJson() {
        this.generateNonExecutableJson = true;
        return this;
    }

    public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
        this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
        return this;
    }

    public GsonBuilder serializeNulls() {
        this.serializeNulls = true;
        return this;
    }

    public GsonBuilder enableComplexMapKeySerialization() {
        this.complexMapKeySerialization = true;
        return this;
    }

    public GsonBuilder disableInnerClassSerialization() {
        this.excluder = this.excluder.disableInnerClassSerialization();
        return this;
    }

    public GsonBuilder setLongSerializationPolicy(
            LongSerializationPolicy longSerializationPolicy2) {
        this.longSerializationPolicy = longSerializationPolicy2;
        return this;
    }

    public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy fieldNamingPolicy2) {
        this.fieldNamingPolicy = fieldNamingPolicy2;
        return this;
    }

    public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
        this.fieldNamingPolicy = fieldNamingStrategy;
        return this;
    }

    public GsonBuilder setExclusionStrategies(ExclusionStrategy... exclusionStrategy) {
        int value = exclusionStrategy.length;
        for (int index = 0; index < value; index++) {
            ExclusionStrategy exclusionStrategy2 = ((ExclusionStrategy[]) exclusionStrategy)[index];
            this.excluder = this.excluder.withExclusionStrategy(exclusionStrategy2, true, true);
        }
        return this;
    }

    public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy exclusionStrategy) {
        this.excluder = this.excluder.withExclusionStrategy(exclusionStrategy, true, false);
        return this;
    }

    public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy exclusionStrategy) {
        this.excluder = this.excluder.withExclusionStrategy(exclusionStrategy, false, true);
        return this;
    }

    public GsonBuilder setPrettyPrinting() {
        this.prettyPrinting = true;
        return this;
    }

    public GsonBuilder setLenient() {
        this.lenient = true;
        return this;
    }

    public GsonBuilder disableHtmlEscaping() {
        this.escapeHtmlChars = false;
        return this;
    }

    public GsonBuilder setDateFormat(String datePattern) {
        this.datePattern = datePattern;
        return this;
    }

    public GsonBuilder setDateFormat(int dateStyle) {
        this.dateStyle = dateStyle;
        this.datePattern = null;
        return this;
    }

    public GsonBuilder setDateFormat(int dateStyle, int timeStyle) {
        this.dateStyle = dateStyle;
        this.timeStyle = timeStyle;
        this.datePattern = null;
        return this;
    }

    public GsonBuilder registerTypeAdapter(Type type, Object result) {
        $Gson$Preconditions.checkArgument(
                result instanceof JsonSerializer
                        || result instanceof JsonDeserializer
                        || result instanceof InstanceCreator
                        || result instanceof TypeAdapter);
        if (result instanceof InstanceCreator) {
            this.instanceCreators.put(type, (InstanceCreator<?>) result);
        }
        if (result instanceof JsonSerializer || result instanceof JsonDeserializer) {
            TypeToken typeToken = TypeToken.get(type);
            this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(typeToken, result));
        }
        if (result instanceof TypeAdapter) {
            this.factories.add(TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter) result));
        }
        return this;
    }

    public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory typeAdapterFactory) {
        this.factories.add(typeAdapterFactory);
        return this;
    }

    public GsonBuilder registerTypeHierarchyAdapter(Class<?> classValue, Object result) {
        $Gson$Preconditions.checkArgument(
                result instanceof JsonSerializer
                        || result instanceof JsonDeserializer
                        || result instanceof TypeAdapter);
        if (result instanceof JsonDeserializer || result instanceof JsonSerializer) {
            this.hierarchyFactories.add(
                    0, TreeTypeAdapter.newTypeHierarchyFactory(classValue, result));
        }
        if (result instanceof TypeAdapter) {
            this.factories.add(
                    TypeAdapters.newTypeHierarchyFactory(classValue, (TypeAdapter) result));
        }
        return this;
    }

    public GsonBuilder serializeSpecialFloatingPointValues() {
        this.serializeSpecialFloatingPointValues = true;
        return this;
    }

    public Gson create() {
        ArrayList values;
        (values = new ArrayList()).addAll(this.factories);
        Collections.reverse(values);
        values.addAll(this.hierarchyFactories);
        this.addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, values);
        return new Gson(
                this.excluder,
                this.fieldNamingPolicy,
                this.instanceCreators,
                this.serializeNulls,
                this.complexMapKeySerialization,
                this.generateNonExecutableJson,
                this.escapeHtmlChars,
                this.prettyPrinting,
                this.lenient,
                this.serializeSpecialFloatingPointValues,
                this.longSerializationPolicy,
                values);
    }

    private void addTypeAdaptersForDate(
            String text, int value, int value2, List<TypeAdapterFactory> values) {
        DefaultDateTypeAdapter defaultDateTypeAdapter;
        if (text != null && !"".equals(text.trim())) {
            defaultDateTypeAdapter = new DefaultDateTypeAdapter(text);
        } else {
            if (value == 2 || value2 == 2) {
                return;
            }
            defaultDateTypeAdapter = new DefaultDateTypeAdapter(value, value2);
        }
        values.add(TreeTypeAdapter.newFactory(TypeToken.get(Date.class), defaultDateTypeAdapter));
        values.add(
                TreeTypeAdapter.newFactory(TypeToken.get(Timestamp.class), defaultDateTypeAdapter));
        values.add(
                TreeTypeAdapter.newFactory(
                        TypeToken.get(java.sql.Date.class), defaultDateTypeAdapter));
    }
}
