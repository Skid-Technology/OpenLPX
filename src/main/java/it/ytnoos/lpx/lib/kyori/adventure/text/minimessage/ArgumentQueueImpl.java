package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

final class ArgumentQueueImpl<T extends Tag.Argument> implements ArgumentQueue {

    private final Context context;

    final List<T> args;

    private int ptr = 0;

    ArgumentQueueImpl(Context context2, List<T> args) {
        this.context = context2;
        this.args = args;
    }

    @NotNull
    @Override
    public T pop() {
        if (!this.hasNext()) {
            throw this.context.newException("Missing argument for this tag!", this);
        } else {
            return this.args.get(this.ptr++);
        }
    }

    @NotNull
    @Override
    public T popOr(@NotNull String text) {
        Objects.requireNonNull(text, "errorMessage");
        if (!this.hasNext()) {
            throw this.context.newException(text, this);
        } else {
            return this.args.get(this.ptr++);
        }
    }

    @NotNull
    @Override
    public T popOr(@NotNull Supplier<String> supplier) {
        Objects.requireNonNull(supplier, "errorMessage");
        if (!this.hasNext()) {
            throw this.context.newException(
                    Objects.requireNonNull((String) supplier.get(), "errorMessage.get()"), this);
        } else {
            return this.args.get(this.ptr++);
        }
    }

    @Nullable
    @Override
    public T peek() {
        return this.hasNext() ? this.args.get(this.ptr) : null;
    }

    @Override
    public boolean hasNext() {
        return this.ptr < this.args.size();
    }

    @Override
    public void reset() {
        this.ptr = 0;
    }

    @Override
    public String toString() {
        return this.args.toString();
    }
}
