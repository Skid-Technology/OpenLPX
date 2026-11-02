package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MinecraftEncryptionUtil {

    public static byte[] decryptRSA(PrivateKey privateKey, byte[] values) {
        return decrypt("RSA/ECB/PKCS1Padding", privateKey, values);
    }

    public static byte[] encryptRSA(PublicKey publicKey, byte[] values) {
        return encrypt("RSA/ECB/PKCS1Padding", publicKey, values);
    }

    public static byte[] decrypt(Cipher cipher, byte[] values) {
        try {
            return cipher.doFinal(values);
        } catch (IllegalBlockSizeException | BadPaddingException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(String text, PrivateKey privateKey, byte[] values) {
        try {
            Cipher cipher;
            (cipher = Cipher.getInstance(text)).init(2, privateKey);
            return cipher.doFinal(values);
        } catch (NoSuchPaddingException
                | NoSuchAlgorithmException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] encrypt(String text, PublicKey publicKey, byte[] values) {
        try {
            Cipher cipher;
            (cipher = Cipher.getInstance(text)).init(1, publicKey);
            return cipher.doFinal(values);
        } catch (NoSuchPaddingException
                | NoSuchAlgorithmException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] encrypt(Cipher cipher, byte[] values) {
        return decrypt(cipher, values);
    }

    public static PublicKey publicKey(byte[] values) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(values);
            return KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
