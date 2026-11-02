package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.UUID;

@Deprecated
class MessageVerifierHelper {

    public static boolean verify(
            UUID uUID, MessageSignData messageSignData, PublicKey publicKey, Component component)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return verify(
                uUID,
                messageSignData,
                publicKey,
                AdventureSerializer.serializer().asJson(component));
    }

    public static boolean verify(
            UUID uUID, MessageSignData messageSignData, PublicKey publicKey, String text)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature;
        (signature = Signature.getInstance("SHA256withRSA")).initVerify(publicKey);
        byte[] values;
        ByteBuffer byteBuffer;
        (byteBuffer = ByteBuffer.wrap(values = new byte[32]).order(ByteOrder.BIG_ENDIAN))
                .putLong(messageSignData.getSaltSignature().getSalt());
        byteBuffer.putLong(uUID.getMostSignificantBits());
        byteBuffer.putLong(uUID.getLeastSignificantBits());
        byteBuffer.putLong(messageSignData.getTimestamp().getEpochSecond());
        signature.update(values);
        signature.update(text.getBytes(StandardCharsets.UTF_8));
        return signature.verify(messageSignData.getSaltSignature().getSignature());
    }
}
