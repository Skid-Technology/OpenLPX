package it.ytnoos.lpx.lib.kyori.adventure.audience;

import it.ytnoos.lpx.lib.kyori.adventure.chat.ChatType;
import it.ytnoos.lpx.lib.kyori.adventure.chat.SignedMessage;
import it.ytnoos.lpx.lib.kyori.adventure.identity.Identified;
import it.ytnoos.lpx.lib.kyori.adventure.identity.Identity;
import it.ytnoos.lpx.lib.kyori.adventure.inventory.Book;
import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointer;
import it.ytnoos.lpx.lib.kyori.adventure.resource.ResourcePackInfoLike;
import it.ytnoos.lpx.lib.kyori.adventure.resource.ResourcePackRequest;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

final class EmptyAudience implements Audience {

    static final EmptyAudience INSTANCE = new EmptyAudience();

    @NotNull
    @Override
    public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
        return Optional.empty();
    }

    @Contract("_, null -> null; _, !null -> !null")
    @Nullable
    @Override
    public <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T t) {
        return (T) t;
    }

    @Override
    @UnknownNullability
    public <T> T getOrDefaultFrom(
            @NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> supplier) {
        return (T) supplier.get();
    }

    @NotNull
    @Override
    public Audience filterAudience(@NotNull Predicate<? super Audience> predicate) {
        return this;
    }

    @Override
    public void forEachAudience(@NotNull Consumer<? super Audience> consumer) {}

    @Override
    public void sendMessage(@NotNull ComponentLike componentLike) {}

    @Override
    public void sendMessage(@NotNull Component component) {}

    @Deprecated
    @Override
    public void sendMessage(
            @NotNull Identified identified,
            @NotNull Component component,
            @NotNull MessageType messageType) {}

    @Deprecated
    @Override
    public void sendMessage(
            @NotNull Identity identity,
            @NotNull Component component,
            @NotNull MessageType messageType) {}

    @Override
    public void sendMessage(@NotNull Component component, ChatType.@NotNull Bound bound) {}

    @Override
    public void sendMessage(@NotNull SignedMessage signedMessage, ChatType.@NotNull Bound bound) {}

    @Override
    public void deleteMessage(SignedMessage.@NotNull Signature signature) {}

    @Override
    public void sendActionBar(@NotNull ComponentLike componentLike) {}

    @Override
    public void sendPlayerListHeader(@NotNull ComponentLike componentLike) {}

    @Override
    public void sendPlayerListFooter(@NotNull ComponentLike componentLike) {}

    @Override
    public void sendPlayerListHeaderAndFooter(
            @NotNull ComponentLike componentLike, @NotNull ComponentLike componentLike2) {}

    @Override
    public void openBook(Book.@NotNull Builder builder) {}

    @Override
    public void sendResourcePacks(
            @NotNull ResourcePackInfoLike resourcePackInfoLike,
            @NotNull ResourcePackInfoLike... resourcePackInfoLike2) {}

    @Override
    public void removeResourcePacks(@NotNull ResourcePackRequest resourcePackRequest) {}

    @Override
    public void removeResourcePacks(
            @NotNull ResourcePackInfoLike resourcePackInfoLike,
            @NotNull ResourcePackInfoLike... resourcePackInfoLike2) {}

    @Override
    public boolean equals(Object result) {
        return this == result;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "EmptyAudience";
    }
}
