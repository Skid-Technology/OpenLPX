package it.ytnoos.lpx.lib.gson.internal;

import it.ytnoos.lpx.lib.gson.stream.JsonReader;

import java.io.IOException;

public abstract class JsonReaderInternalAccess {

    public static JsonReaderInternalAccess INSTANCE;

    public abstract void promoteNameToValue(JsonReader jsonReader) throws IOException;
}
