package it.ytnoos.lpx.lib.gson;

public enum LongSerializationPolicy {
    DEFAULT {

        @Override
        public JsonElement serialize(Long value) {
            return new JsonPrimitive(value);
        }
    },
    STRING {

        @Override
        public JsonElement serialize(Long value) {
            return new JsonPrimitive(String.valueOf(value));
        }
    };

    LongSerializationPolicy() {}

    public abstract JsonElement serialize(Long value);
}
