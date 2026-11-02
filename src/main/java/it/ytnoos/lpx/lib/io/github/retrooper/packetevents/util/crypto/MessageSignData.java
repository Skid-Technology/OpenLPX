package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto;

import java.time.Instant;

public class MessageSignData {

    private final SaltSignature saltSignature;

    private final Instant timestamp;

    private boolean signedPreview;

    public MessageSignData(SaltSignature saltSignature2, Instant instant) {
        this.saltSignature = saltSignature2;
        this.timestamp = instant;
    }

    public MessageSignData(SaltSignature saltSignature2, Instant instant, boolean signedPreview) {
        this.saltSignature = saltSignature2;
        this.timestamp = instant;
        this.signedPreview = signedPreview;
    }

    public SaltSignature getSaltSignature() {
        return this.saltSignature;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public boolean isSignedPreview() {
        return this.signedPreview;
    }

    public void setSignedPreview(boolean signedPreview) {
        this.signedPreview = signedPreview;
    }
}
