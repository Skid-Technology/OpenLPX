package it.ytnoos.lpx.lib.gson;

import it.ytnoos.lpx.lib.gson.reflect.TypeToken;

public interface TypeAdapterFactory {

    <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken);
}
