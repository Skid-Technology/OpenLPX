package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;

@NullMarked
@Internal
public final class VersionRange {

    public static final VersionRange ALL_VERSIONS = new VersionRange(null);

    private static final ClientVersion[] VERSIONS = ClientVersion.values();

    private final ClientVersion minimum;

    private final ClientVersion maximum;

    public VersionRange(@Nullable ClientVersion clientVersion) {
        this(clientVersion, clientVersion);
    }

    public VersionRange(
            @Nullable ClientVersion clientVersion, @Nullable ClientVersion clientVersion2) {
        this.minimum = clientVersion != null ? clientVersion : ClientVersion.getOldest();
        this.maximum = clientVersion2 != null ? clientVersion2 : ClientVersion.getLatest();
        if (this.minimum.compareTo(this.maximum) > 0) {
            throw new IllegalArgumentException(
                    "Minimum version is newer than maximum version: "
                            + this.minimum
                            + " > "
                            + this.maximum);
        }
    }

    public void iterate(Consumer<ClientVersion> consumer) {
        if (this.minimum == this.maximum) {
            consumer.accept(this.maximum);
        } else {
            consumer.accept(this.minimum);
            for (int value = this.minimum.ordinal() + 1; value < this.maximum.ordinal(); value++) {
                consumer.accept(VERSIONS[value]);
            }
            consumer.accept(this.maximum);
        }
    }

    public ClientVersion[] getAll() {
        return Arrays.copyOfRange(VERSIONS, this.minimum.ordinal(), this.maximum.ordinal() + 1);
    }

    public boolean contains(ClientVersion clientVersion) {
        return clientVersion.compareTo(this.minimum) >= 0
                && clientVersion.compareTo(this.maximum) <= 0;
    }

    public ClientVersion getMinimum() {
        return this.minimum;
    }

    public ClientVersion getMaximum() {
        return this.maximum;
    }

    @Override
    public String toString() {
        return "(" + this.minimum + " to " + this.maximum + ")";
    }
}
