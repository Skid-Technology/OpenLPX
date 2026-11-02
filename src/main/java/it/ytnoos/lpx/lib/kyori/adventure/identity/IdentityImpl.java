package it.ytnoos.lpx.lib.kyori.adventure.identity;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

final class IdentityImpl implements Examinable, Identity {

    private final UUID uuid;

    IdentityImpl(UUID uuid) {
        this.uuid = uuid;
    }

    @NotNull
    @Override
    public UUID uuid() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof Identity)) {
            return false;
        }
        Identity identity = (Identity) result;
        return this.uuid.equals(identity.uuid());
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }
}
