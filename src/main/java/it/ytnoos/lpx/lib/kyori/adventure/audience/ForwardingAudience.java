package it.ytnoos.lpx.lib.kyori.adventure.audience;

import it.ytnoos.lpx.lib.kyori.adventure.bossbar.BossBar;
import it.ytnoos.lpx.lib.kyori.adventure.chat.ChatType;
import it.ytnoos.lpx.lib.kyori.adventure.chat.SignedMessage;
import it.ytnoos.lpx.lib.kyori.adventure.dialog.DialogLike;
import it.ytnoos.lpx.lib.kyori.adventure.identity.Identified;
import it.ytnoos.lpx.lib.kyori.adventure.identity.Identity;
import it.ytnoos.lpx.lib.kyori.adventure.inventory.Book;
import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointer;
import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointers;
import it.ytnoos.lpx.lib.kyori.adventure.resource.ResourcePackRequest;
import it.ytnoos.lpx.lib.kyori.adventure.sound.Sound;
import it.ytnoos.lpx.lib.kyori.adventure.sound.SoundStop;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.title.TitlePart;

import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@FunctionalInterface
public interface ForwardingAudience extends Audience {

    @OverrideOnly
    @NotNull
    Iterable<? extends Audience> audiences();

    @NotNull
    @Override
    default Pointers pointers() {
        return Pointers.empty();
    }

    @NotNull
    @Override
    default Audience filterAudience(@NotNull Predicate<? super Audience> predicate) {
        ArrayList<it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> values = null;
        for (Audience audience2 : this.audiences()) {
            Audience audience3;
            if (predicate.test(audience2)
                    && (audience3 = audience2.filterAudience(predicate)) != Audience.empty()) {
                if (values == null) {
                    values = new ArrayList();
                }
                values.add(audience3);
            }
        }
        return values != null ? Audience.audience(values) : Audience.empty();
    }

    @Override
    default void forEachAudience(@NotNull Consumer<? super Audience> consumer) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).forEachAudience(consumer);
        }
    }

    @Override
    default void sendMessage(@NotNull Component component) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendMessage(component);
        }
    }

    @Override
    default void sendMessage(@NotNull Component component, ChatType.@NotNull Bound bound) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendMessage(component, bound);
        }
    }

    @Override
    default void sendMessage(@NotNull SignedMessage signedMessage, ChatType.@NotNull Bound bound) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendMessage(signedMessage, bound);
        }
    }

    @Override
    default void deleteMessage(SignedMessage.@NotNull Signature signature) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).deleteMessage(signature);
        }
    }

    @Deprecated
    @Override
    default void sendMessage(
            @NotNull Identified identified,
            @NotNull Component component,
            @NotNull MessageType messageType) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendMessage(identified, component, messageType);
        }
    }

    @Deprecated
    @Override
    default void sendMessage(
            @NotNull Identity identity,
            @NotNull Component component,
            @NotNull MessageType messageType) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendMessage(identity, component, messageType);
        }
    }

    @Override
    default void sendActionBar(@NotNull Component component) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendActionBar(component);
        }
    }

    @Override
    default void sendPlayerListHeader(@NotNull Component component) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendPlayerListHeader(component);
        }
    }

    @Override
    default void sendPlayerListFooter(@NotNull Component component) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendPlayerListFooter(component);
        }
    }

    @Override
    default void sendPlayerListHeaderAndFooter(
            @NotNull Component component, @NotNull Component component2) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendPlayerListHeaderAndFooter(component, component2);
        }
    }

    @Override
    default <T> void sendTitlePart(@NotNull TitlePart<T> titlePart, @NotNull T t) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendTitlePart(titlePart, t);
        }
    }

    @Override
    default void clearTitle() {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).clearTitle();
        }
    }

    @Override
    default void resetTitle() {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).resetTitle();
        }
    }

    @Override
    default void showBossBar(@NotNull BossBar bossBar) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).showBossBar(bossBar);
        }
    }

    @Override
    default void hideBossBar(@NotNull BossBar bossBar) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).hideBossBar(bossBar);
        }
    }

    @Override
    default void playSound(@NotNull Sound sound) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).playSound(sound);
        }
    }

    @Override
    default void playSound(@NotNull Sound sound, double value, double value2, double value3) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).playSound(sound, value, value2, value3);
        }
    }

    @Override
    default void playSound(@NotNull Sound sound, Sound.@NotNull Emitter emitter) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).playSound(sound, emitter);
        }
    }

    @Override
    default void stopSound(@NotNull SoundStop soundStop) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).stopSound(soundStop);
        }
    }

    @Override
    default void openBook(@NotNull Book book) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).openBook(book);
        }
    }

    @Override
    default void sendResourcePacks(@NotNull ResourcePackRequest resourcePackRequest) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).sendResourcePacks(resourcePackRequest);
        }
    }

    @Override
    default void removeResourcePacks(@NotNull Iterable<UUID> iterable) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).removeResourcePacks(iterable);
        }
    }

    @Override
    default void removeResourcePacks(@NotNull UUID uUID, @NotNull UUID... uUID2) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).removeResourcePacks(uUID, uUID2);
        }
    }

    @Override
    default void clearResourcePacks() {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).clearResourcePacks();
        }
    }

    @Override
    default void showDialog(@NotNull DialogLike dialogLike) {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).showDialog(dialogLike);
        }
    }

    @Override
    default void closeDialog() {
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.audience.Audience> iterator2 =
                this.audiences().iterator();
        while (iterator2.hasNext()) {
            ((Audience) iterator2.next()).closeDialog();
        }
    }

    interface Single extends ForwardingAudience {

        @OverrideOnly
        @NotNull
        Audience audience();

        @Deprecated
        @NotNull
        @Override
        default Iterable<? extends Audience> audiences() {
            return Collections.singleton(this.audience());
        }

        @NotNull
        @Override
        default <T> Optional<T> get(@NotNull Pointer<T> pointer) {
            return this.audience().get(pointer);
        }

        @Contract("_, null -> null; _, !null -> !null")
        @Nullable
        @Override
        default <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T t) {
            return this.audience().getOrDefault(pointer, (T) t);
        }

        @Override
        @UnknownNullability
        default <T> T getOrDefaultFrom(
                @NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> supplier) {
            return this.audience().getOrDefaultFrom(pointer, supplier);
        }

        @NotNull
        @Override
        default Audience filterAudience(@NotNull Predicate<? super Audience> predicate) {
            Audience audience2 = this.audience();
            return predicate.test(audience2) ? this : Audience.empty();
        }

        @Override
        default void forEachAudience(@NotNull Consumer<? super Audience> consumer) {
            this.audience().forEachAudience(consumer);
        }

        @NotNull
        @Override
        default Pointers pointers() {
            return this.audience().pointers();
        }

        @Override
        default void sendMessage(@NotNull Component component) {
            this.audience().sendMessage(component);
        }

        @Override
        default void sendMessage(@NotNull Component component, ChatType.@NotNull Bound bound) {
            this.audience().sendMessage(component, bound);
        }

        @Override
        default void sendMessage(
                @NotNull SignedMessage signedMessage, ChatType.@NotNull Bound bound) {
            this.audience().sendMessage(signedMessage, bound);
        }

        @Override
        default void deleteMessage(SignedMessage.@NotNull Signature signature) {
            this.audience().deleteMessage(signature);
        }

        @Deprecated
        @Override
        default void sendMessage(
                @NotNull Identified identified,
                @NotNull Component component,
                @NotNull MessageType messageType) {
            this.audience().sendMessage(identified, component, messageType);
        }

        @Deprecated
        @Override
        default void sendMessage(
                @NotNull Identity identity,
                @NotNull Component component,
                @NotNull MessageType messageType) {
            this.audience().sendMessage(identity, component, messageType);
        }

        @Override
        default void sendActionBar(@NotNull Component component) {
            this.audience().sendActionBar(component);
        }

        @Override
        default void sendPlayerListHeader(@NotNull Component component) {
            this.audience().sendPlayerListHeader(component);
        }

        @Override
        default void sendPlayerListFooter(@NotNull Component component) {
            this.audience().sendPlayerListFooter(component);
        }

        @Override
        default void sendPlayerListHeaderAndFooter(
                @NotNull Component component, @NotNull Component component2) {
            this.audience().sendPlayerListHeaderAndFooter(component, component2);
        }

        @Override
        default <T> void sendTitlePart(@NotNull TitlePart<T> titlePart, @NotNull T t) {
            this.audience().sendTitlePart(titlePart, t);
        }

        @Override
        default void clearTitle() {
            this.audience().clearTitle();
        }

        @Override
        default void resetTitle() {
            this.audience().resetTitle();
        }

        @Override
        default void showBossBar(@NotNull BossBar bossBar) {
            this.audience().showBossBar(bossBar);
        }

        @Override
        default void hideBossBar(@NotNull BossBar bossBar) {
            this.audience().hideBossBar(bossBar);
        }

        @Override
        default void playSound(@NotNull Sound sound) {
            this.audience().playSound(sound);
        }

        @Override
        default void playSound(@NotNull Sound sound, double value, double value2, double value3) {
            this.audience().playSound(sound, value, value2, value3);
        }

        @Override
        default void playSound(@NotNull Sound sound, Sound.@NotNull Emitter emitter) {
            this.audience().playSound(sound, emitter);
        }

        @Override
        default void stopSound(@NotNull SoundStop soundStop) {
            this.audience().stopSound(soundStop);
        }

        @Override
        default void openBook(@NotNull Book book) {
            this.audience().openBook(book);
        }

        @Override
        default void sendResourcePacks(@NotNull ResourcePackRequest resourcePackRequest) {
            this.audience()
                    .sendResourcePacks(
                            resourcePackRequest.callback(
                                    Audiences.unwrapCallback(
                                            this,
                                            this.audience(),
                                            resourcePackRequest.callback())));
        }

        @Override
        default void removeResourcePacks(@NotNull Iterable<UUID> iterable) {
            this.audience().removeResourcePacks(iterable);
        }

        @Override
        default void removeResourcePacks(@NotNull UUID uUID, @NotNull UUID... uUID2) {
            this.audience().removeResourcePacks(uUID, uUID2);
        }

        @Override
        default void clearResourcePacks() {
            this.audience().clearResourcePacks();
        }

        @Override
        default void showDialog(@NotNull DialogLike dialogLike) {
            this.audience().showDialog(dialogLike);
        }

        @Override
        default void closeDialog() {
            this.audience().closeDialog();
        }
    }
}
