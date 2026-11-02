package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import it.ytnoos.lpx.lib.kyori.adventure.dialog.DialogLike;
import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

abstract class PayloadImpl implements ClickEvent.Payload {

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    static final class CustomImpl extends PayloadImpl implements ClickEvent.Payload.Custom {

        private final Key key;

        private final BinaryTagHolder nbt;

        CustomImpl(@NotNull Key key2, @NotNull BinaryTagHolder binaryTagHolder) {
            this.key = key2;
            this.nbt = binaryTagHolder;
        }

        @NotNull
        @Override
        public Key key() {
            return this.key;
        }

        @NotNull
        @Override
        public String data() {
            return this.nbt.string();
        }

        @NotNull
        @Override
        public BinaryTagHolder nbt() {
            return this.nbt;
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("key", this.key), ExaminableProperty.of("nbt", this.nbt));
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (result != null && this.getClass() == result.getClass()) {
                PayloadImpl.CustomImpl customImpl = (PayloadImpl.CustomImpl) result;
                return Objects.equals(this.key, customImpl.key)
                        && Objects.equals(this.nbt, customImpl.nbt);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int value = this.key.hashCode();
            return 31 * value + this.nbt.hashCode();
        }
    }

    static final class DialogImpl extends PayloadImpl implements ClickEvent.Payload.Dialog {

        private final DialogLike dialogLike;

        DialogImpl(@NotNull DialogLike dialogLike2) {
            this.dialogLike = dialogLike2;
        }

        @NotNull
        @Override
        public DialogLike dialog() {
            return this.dialogLike;
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("dialog", this.dialogLike));
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (result != null && this.getClass() == result.getClass()) {
                PayloadImpl.DialogImpl dialogImpl = (PayloadImpl.DialogImpl) result;
                return Objects.equals(this.dialogLike, dialogImpl.dialogLike);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return this.dialogLike.hashCode();
        }
    }

    static final class IntImpl extends PayloadImpl implements ClickEvent.Payload.Int {

        private final int integer;

        IntImpl(int integer) {
            this.integer = integer;
        }

        @Override
        public int integer() {
            return this.integer;
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("integer", this.integer));
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (result != null && this.getClass() == result.getClass()) {
                PayloadImpl.IntImpl intImpl = (PayloadImpl.IntImpl) result;
                return Objects.equals(this.integer, intImpl.integer);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return this.integer;
        }
    }

    static final class TextImpl extends PayloadImpl implements ClickEvent.Payload.Text {

        private final String value;

        TextImpl(@NotNull String value) {
            this.value = value;
        }

        @NotNull
        @Override
        public String value() {
            return this.value;
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("value", this.value));
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (result != null && this.getClass() == result.getClass()) {
                PayloadImpl.TextImpl textImpl = (PayloadImpl.TextImpl) result;
                return Objects.equals(this.value, textImpl.value);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return this.value.hashCode();
        }
    }
}
