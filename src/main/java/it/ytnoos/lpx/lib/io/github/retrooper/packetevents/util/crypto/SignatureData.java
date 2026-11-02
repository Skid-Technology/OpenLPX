package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto;

import java.security.PublicKey;
import java.time.Instant;

public class SignatureData {

    private final Instant timestamp;

    private final PublicKey publicKey;

    private final byte[] signature;

    public SignatureData(Instant instant, PublicKey publicKey2, byte[] signature) {
        this.timestamp = instant;
        this.publicKey = publicKey2;
        this.signature = signature;
    }

    public SignatureData(Instant instant, byte[] values, byte[] signature) {
        this.timestamp = instant;
        this.publicKey = MinecraftEncryptionUtil.publicKey(values);
        this.signature = signature;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public byte[] getSignature() {
        return this.signature;
    }
}
