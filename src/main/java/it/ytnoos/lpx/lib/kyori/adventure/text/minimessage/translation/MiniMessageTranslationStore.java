package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.translation;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.MiniMessage;
import it.ytnoos.lpx.lib.kyori.adventure.translation.TranslationStore;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface MiniMessageTranslationStore extends TranslationStore.StringBased<String> {

    @NotNull
    static MiniMessageTranslationStore create(@NotNull Key key) {
        return create(key, MiniMessage.miniMessage());
    }

    @NotNull
    static MiniMessageTranslationStore create(@NotNull Key key, @NotNull MiniMessage miniMessage) {
        return new MiniMessageTranslationStoreImpl(key, miniMessage);
    }
}
