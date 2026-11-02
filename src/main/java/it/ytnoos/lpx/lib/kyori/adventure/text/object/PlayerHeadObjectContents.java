package it.ytnoos.lpx.lib.kyori.adventure.text.object;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.util.PlatformAPI;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@NonExtendable
public interface PlayerHeadObjectContents extends ObjectContents {

    boolean DEFAULT_HAT = true;

    @Nullable
    String name();

    @Nullable
    UUID id();

    @NotNull
    @Unmodifiable
    List<PlayerHeadObjectContents.ProfileProperty> profileProperties();

    boolean hat();

    @Nullable
    Key texture();

    @Contract(value = "-> new", pure = true)
    @NotNull
    PlayerHeadObjectContents.Builder toBuilder();

    @Contract(value = "_, _ -> new", pure = true)
    static PlayerHeadObjectContents.ProfileProperty property(
            @NotNull String text, @NotNull String text2) {
        return new PlayerHeadObjectContentsImpl.ProfilePropertyImpl(
                Objects.requireNonNull(text, "name"), Objects.requireNonNull(text2, "value"), null);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    static PlayerHeadObjectContents.ProfileProperty property(
            @NotNull String text, @NotNull String text2, @Nullable String text3) {
        return new PlayerHeadObjectContentsImpl.ProfilePropertyImpl(
                Objects.requireNonNull(text, "name"),
                Objects.requireNonNull(text2, "value"),
                text3);
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("name", this.name()),
                ExaminableProperty.of("id", this.id()),
                ExaminableProperty.of("profileProperties", this.profileProperties()),
                ExaminableProperty.of("hat", this.hat()),
                ExaminableProperty.of("texture", this.texture()));
    }

    interface Builder {

        @Contract("_ -> this")
        @NotNull
        PlayerHeadObjectContents.Builder name(@Nullable String text);

        @Contract("_ -> this")
        @NotNull
        PlayerHeadObjectContents.Builder id(@Nullable UUID uUID);

        @Contract("_ -> this")
        @NotNull
        PlayerHeadObjectContents.Builder profileProperty(
                @NotNull PlayerHeadObjectContents.ProfileProperty profileProperty2);

        @Contract("_ -> this")
        @NotNull
        PlayerHeadObjectContents.Builder profileProperties(
                @NotNull Collection<PlayerHeadObjectContents.ProfileProperty> values);

        @Contract("_ -> this")
        @NotNull
        PlayerHeadObjectContents.Builder skin(
                @NotNull PlayerHeadObjectContents.SkinSource skinSource);

        @Contract("_ -> this")
        @NotNull
        PlayerHeadObjectContents.Builder hat(boolean matches);

        @Contract("_ -> this")
        @NotNull
        PlayerHeadObjectContents.Builder texture(@Nullable Key key);

        @Contract(value = "-> new", pure = true)
        @NotNull
        PlayerHeadObjectContents build();
    }

    interface ProfileProperty extends Examinable {

        @NotNull
        String name();

        @NotNull
        String value();

        @Nullable
        String signature();

        @NotNull
        @Override
        default Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("name", this.name()),
                    ExaminableProperty.of("value", this.value()),
                    ExaminableProperty.of("signature", this.signature()));
        }
    }

    interface SkinSource {

        @PlatformAPI
        @Internal
        void applySkinToPlayerHeadContents(@NotNull PlayerHeadObjectContents.Builder builder);
    }
}
