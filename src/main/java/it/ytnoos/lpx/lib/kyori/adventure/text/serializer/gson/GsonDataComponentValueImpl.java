package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonNull;
import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValue;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

class GsonDataComponentValueImpl implements GsonDataComponentValue {

    private final JsonElement element;

    GsonDataComponentValueImpl(@NotNull JsonElement jsonElement) {
        this.element = jsonElement;
    }

    @NotNull
    @Override
    public JsonElement element() {
        return this.element;
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("element", this.element));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            GsonDataComponentValueImpl gsonDataComponentValueImpl =
                    (GsonDataComponentValueImpl) result;
            return Objects.equals(this.element, gsonDataComponentValueImpl.element);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.element);
    }

    static final class RemovedGsonComponentValueImpl extends GsonDataComponentValueImpl
            implements DataComponentValue.Removed {

        static final GsonDataComponentValueImpl.RemovedGsonComponentValueImpl INSTANCE =
                new GsonDataComponentValueImpl.RemovedGsonComponentValueImpl();

        private RemovedGsonComponentValueImpl() {
            super(JsonNull.INSTANCE);
        }
    }
}
