package it.ytnoos.lpx.lib.kyori.adventure.text.object;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@NonExtendable
public interface ObjectContents extends Examinable {

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    static SpriteObjectContents sprite(@NotNull Key key, @NotNull Key key2) {
        return new SpriteObjectContentsImpl(
                Objects.requireNonNull(key, "atlas"), Objects.requireNonNull(key2, "sprite"));
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static SpriteObjectContents sprite(@NotNull Key key) {
        return new SpriteObjectContentsImpl(
                SpriteObjectContents.DEFAULT_ATLAS, Objects.requireNonNull(key, "sprite"));
    }

    @Contract(value = "-> new", pure = true)
    static PlayerHeadObjectContents.@NotNull Builder playerHead() {
        return new PlayerHeadObjectContentsImpl.BuilderImpl();
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static PlayerHeadObjectContents playerHead(@NotNull String text) {
        return new PlayerHeadObjectContentsImpl(text, null, Collections.emptyList(), true, null);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static PlayerHeadObjectContents playerHead(@NotNull UUID uUID) {
        return new PlayerHeadObjectContentsImpl(null, uUID, Collections.emptyList(), true, null);
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    static PlayerHeadObjectContents playerHead(
            PlayerHeadObjectContents.@NotNull SkinSource skinSource) {
        return playerHead().skin(skinSource).build();
    }
}
