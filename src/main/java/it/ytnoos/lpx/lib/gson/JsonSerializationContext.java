package it.ytnoos.lpx.lib.gson;

import java.lang.reflect.Type;

public interface JsonSerializationContext {

    JsonElement serialize(Object result);

    JsonElement serialize(Object result, Type type);
}
