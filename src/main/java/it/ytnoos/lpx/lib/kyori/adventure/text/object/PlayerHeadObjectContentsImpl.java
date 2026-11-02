package it.ytnoos.lpx.lib.kyori.adventure.text.object;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

final class PlayerHeadObjectContentsImpl implements PlayerHeadObjectContents {

    @Nullable private final String name;

    @Nullable private final UUID id;

    private final List<PlayerHeadObjectContents.ProfileProperty> properties;

    private final boolean hat;

    @Nullable private final Key texture;

    PlayerHeadObjectContentsImpl(
            @Nullable String name,
            @Nullable UUID id,
            @NotNull List<PlayerHeadObjectContents.ProfileProperty> values,
            boolean hat,
            @Nullable Key key) {
        this.name = name;
        this.id = id;
        if (values.isEmpty()) {
            this.properties = Collections.emptyList();
        } else {
            this.properties =
                    Collections.unmodifiableList(
                            new ArrayList<>(Objects.requireNonNull(values, "properties")));
        }
        this.hat = hat;
        this.texture = key;
    }

    @Nullable
    @Override
    public String name() {
        return this.name;
    }

    @Nullable
    @Override
    public UUID id() {
        return this.id;
    }

    @NotNull
    @Override
    public List<PlayerHeadObjectContents.ProfileProperty> profileProperties() {
        return this.properties;
    }

    @Override
    public boolean hat() {
        return this.hat;
    }

    @Nullable
    @Override
    public Key texture() {
        return this.texture;
    }

    @NotNull
    @Override
    public PlayerHeadObjectContents.Builder toBuilder() {
        return new PlayerHeadObjectContentsImpl.BuilderImpl(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof PlayerHeadObjectContents)) {
            return false;
        }
        PlayerHeadObjectContentsImpl playerHeadObjectContentsImpl =
                (PlayerHeadObjectContentsImpl) result;
        return Objects.equals(this.name, playerHeadObjectContentsImpl.name)
                && Objects.equals(this.id, playerHeadObjectContentsImpl.id)
                && Objects.equals(this.properties, playerHeadObjectContentsImpl.properties)
                && this.hat == playerHeadObjectContentsImpl.hat
                && Objects.equals(this.texture, playerHeadObjectContentsImpl.texture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.id, this.properties, this.hat, this.texture);
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    static final class BuilderImpl implements PlayerHeadObjectContents.Builder {

        @Nullable private String name;

        @Nullable private UUID id;

        private final List<PlayerHeadObjectContents.ProfileProperty> properties = new ArrayList<>();

        private boolean hat = true;

        @Nullable private Key texture;

        BuilderImpl() {}

        BuilderImpl(@NotNull PlayerHeadObjectContentsImpl playerHeadObjectContentsImpl) {
            this.name = playerHeadObjectContentsImpl.name;
            this.id = playerHeadObjectContentsImpl.id;
            this.properties.addAll(playerHeadObjectContentsImpl.properties);
            this.hat = playerHeadObjectContentsImpl.hat;
            this.texture = playerHeadObjectContentsImpl.texture;
        }

        @Override
        public PlayerHeadObjectContents.@NotNull Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        @Override
        public PlayerHeadObjectContents.@NotNull Builder id(@Nullable UUID id) {
            this.id = id;
            return this;
        }

        @Override
        public PlayerHeadObjectContents.@NotNull Builder profileProperty(
                PlayerHeadObjectContents.@NotNull ProfileProperty profileProperty2) {
            this.properties.add(Objects.requireNonNull(profileProperty2, "property"));
            return this;
        }

        @Override
        public PlayerHeadObjectContents.@NotNull Builder profileProperties(
                @NotNull Collection<PlayerHeadObjectContents.ProfileProperty> values) {
            for (PlayerHeadObjectContents.ProfileProperty profileProperty2 :
                    Objects.requireNonNull(values, "properties")) {
                this.profileProperty(profileProperty2);
            }
            return this;
        }

        private void clearProfile() {
            this.name = null;
            this.id = null;
            this.properties.clear();
            this.texture = null;
        }

        @Override
        public PlayerHeadObjectContents.@NotNull Builder skin(
                PlayerHeadObjectContents.@NotNull SkinSource skinSource) {
            this.clearProfile();
            Objects.requireNonNull(skinSource, "skinSource").applySkinToPlayerHeadContents(this);
            return this;
        }

        @Override
        public PlayerHeadObjectContents.@NotNull Builder hat(boolean hat) {
            this.hat = hat;
            return this;
        }

        @Override
        public PlayerHeadObjectContents.@NotNull Builder texture(@Nullable Key key) {
            this.texture = key;
            return this;
        }

        @NotNull
        @Override
        public PlayerHeadObjectContents build() {
            return new PlayerHeadObjectContentsImpl(
                    this.name, this.id, this.properties, this.hat, this.texture);
        }
    }

    static final class ProfilePropertyImpl implements PlayerHeadObjectContents.ProfileProperty {

        private final String name;

        private final String value;

        @Nullable private final String signature;

        ProfilePropertyImpl(
                @NotNull String name, @NotNull String value, @Nullable String signature) {
            this.name = name;
            this.value = value;
            this.signature = signature;
        }

        @NotNull
        @Override
        public String name() {
            return this.name;
        }

        @NotNull
        @Override
        public String value() {
            return this.value;
        }

        @Nullable
        @Override
        public String signature() {
            return this.signature;
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof PlayerHeadObjectContentsImpl.ProfilePropertyImpl)) {
                return false;
            }
            PlayerHeadObjectContentsImpl.ProfilePropertyImpl profilePropertyImpl =
                    (PlayerHeadObjectContentsImpl.ProfilePropertyImpl) result;
            return Objects.equals(this.name, profilePropertyImpl.name)
                    && Objects.equals(this.value, profilePropertyImpl.value)
                    && Objects.equals(this.signature, profilePropertyImpl.signature);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.name, this.value, this.signature);
        }

        @Override
        public String toString() {
            return Internals.toString(this);
        }
    }
}
