package it.ytnoos.lpx.lib.kyori.adventure.chat;

import it.ytnoos.lpx.lib.kyori.adventure.identity.Identity;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.time.Instant;

final class SignedMessageImpl implements SignedMessage {

    static final SecureRandom RANDOM = new SecureRandom();

    private final Instant instant = Instant.now();

    private final long salt = RANDOM.nextLong();

    private final String message;

    private final Component unsignedContent;

    SignedMessageImpl(String message, Component component) {
        this.message = message;
        this.unsignedContent = component;
    }

    @NotNull
    @Override
    public Instant timestamp() {
        return this.instant;
    }

    @Override
    public long salt() {
        return this.salt;
    }

    @Override
    public SignedMessage.Signature signature() {
        return null;
    }

    @Nullable
    @Override
    public Component unsignedContent() {
        return this.unsignedContent;
    }

    @NotNull
    @Override
    public String message() {
        return this.message;
    }

    @NotNull
    @Override
    public Identity identity() {
        return Identity.nil();
    }

    static final class SignatureImpl implements SignedMessage.Signature {

        final byte[] signature;

        SignatureImpl(byte[] signature) {
            this.signature = signature;
        }

        @Override
        public byte[] bytes() {
            return this.signature;
        }
    }
}
