package it.ytnoos.lpx.lib.kyori.adventure.text.object;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

@NonExtendable
public interface SpriteObjectContents extends ObjectContents {

    Key DEFAULT_ATLAS = Key.key("minecraft:blocks");

    @NotNull
    Key atlas();

    @NotNull
    Key sprite();

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("atlas", this.atlas()),
                ExaminableProperty.of("sprite", this.sprite()));
    }
}
