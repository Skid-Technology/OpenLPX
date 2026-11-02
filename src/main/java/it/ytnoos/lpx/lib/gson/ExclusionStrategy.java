package it.ytnoos.lpx.lib.gson;

public interface ExclusionStrategy {

    boolean shouldSkipField(FieldAttributes fieldAttributes);

    boolean shouldSkipClass(Class<?> classValue);
}
