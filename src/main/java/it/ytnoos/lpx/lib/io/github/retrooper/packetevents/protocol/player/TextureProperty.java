package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

import org.jetbrains.annotations.Nullable;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

public class TextureProperty {

    private final String name;

    private final String value;

    private final String signature;

    public TextureProperty(String name, String value, @Nullable String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    @Nullable
    public String getSignature() {
        return this.signature;
    }

    public boolean isSignatureValid(PublicKey publicKey) {
        if (this.getSignature() == null) {
            return false;
        }
        try {
            Signature signature2;
            (signature2 = Signature.getInstance("SHA1withRSA")).initVerify(publicKey);
            signature2.update(this.value.getBytes());
            return signature2.verify(Base64.getDecoder().decode(this.signature));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
