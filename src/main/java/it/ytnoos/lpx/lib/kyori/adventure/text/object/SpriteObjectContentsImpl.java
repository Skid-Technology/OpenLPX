package it.ytnoos.lpx.lib.kyori.adventure.text.object;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

final class SpriteObjectContentsImpl implements SpriteObjectContents {

    private final Key atlas;

    private final Key sprite;

    SpriteObjectContentsImpl(@NotNull Key key, @NotNull Key key2) {
        this.atlas = key;
        this.sprite = key2;
    }

    @NotNull
    @Override
    public Key atlas() {
        return this.atlas;
    }

    @NotNull
    @Override
    public Key sprite() {
        return this.sprite;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof SpriteObjectContents)) {
            return false;
        }
        SpriteObjectContentsImpl spriteObjectContentsImpl = (SpriteObjectContentsImpl) result;
        return Objects.equals(this.atlas, spriteObjectContentsImpl.atlas())
                && Objects.equals(this.sprite, spriteObjectContentsImpl.sprite());
    }

    @Override
    public int hashCode() {
        int value = this.atlas.hashCode();
        return 31 * value + this.sprite.hashCode();
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }
}
