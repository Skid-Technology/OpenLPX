package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.MinecraftEncryptionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.SaltSignature;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class WrapperLoginClientEncryptionResponse
        extends PacketWrapper<WrapperLoginClientEncryptionResponse> {

    private byte[] encryptedSharedSecret;

    private byte[] encryptedVerifyToken;

    private SaltSignature saltSignature;

    public WrapperLoginClientEncryptionResponse(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperLoginClientEncryptionResponse(
            ClientVersion clientVersion,
            byte[] encryptedSharedSecret,
            byte[] encryptedVerifyToken) {
        super(PacketType.Login.Client.ENCRYPTION_RESPONSE.getId(), clientVersion);
        this.encryptedSharedSecret = encryptedSharedSecret;
        this.encryptedVerifyToken = encryptedVerifyToken;
    }

    public WrapperLoginClientEncryptionResponse(
            ClientVersion clientVersion, SaltSignature saltSignature2) {
        super(PacketType.Login.Client.ENCRYPTION_RESPONSE.getId(), clientVersion);
        this.saltSignature = saltSignature2;
    }

    @Override
    public void read() {
        this.encryptedSharedSecret = this.readByteArray(ByteBufHelper.readableBytes(this.buffer));
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                && this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_2)
                && !this.readBoolean()) {
            this.saltSignature = this.readSaltSignature();
        } else {
            this.encryptedVerifyToken = this.readByteArray();
        }
    }

    @Override
    public void write() {
        this.writeByteArray(this.encryptedSharedSecret);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                && this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_2)) {
            this.writeBoolean(this.saltSignature == null);
            if (this.saltSignature != null) {
                this.writeSaltSignature(this.saltSignature);
            } else {
                this.writeByteArray(this.encryptedVerifyToken);
            }
        } else {
            this.writeByteArray(this.encryptedVerifyToken);
        }
    }

    public void copy(WrapperLoginClientEncryptionResponse wrapperLoginClientEncryptionResponse) {
        this.encryptedSharedSecret = wrapperLoginClientEncryptionResponse.encryptedSharedSecret;
        this.encryptedVerifyToken = wrapperLoginClientEncryptionResponse.encryptedVerifyToken;
        this.saltSignature = wrapperLoginClientEncryptionResponse.saltSignature;
    }

    public byte[] getEncryptedSharedSecret() {
        return this.encryptedSharedSecret;
    }

    public void setEncryptedSharedSecret(byte[] encryptedSharedSecret) {
        this.encryptedSharedSecret = encryptedSharedSecret;
    }

    public SecretKey getSecretKey(PrivateKey privateKey) {
        byte[] encryptedSharedSecret = this.getEncryptedSharedSecret();
        byte[] values;
        return (values =
                                MinecraftEncryptionUtil.decrypt(
                                        privateKey.getAlgorithm(),
                                        privateKey,
                                        encryptedSharedSecret))
                        != null
                ? new SecretKeySpec(values, "AES")
                : null;
    }

    public void setSharedKey(SecretKey secretKey, PublicKey publicKey) {
        this.encryptedSharedSecret =
                MinecraftEncryptionUtil.encrypt(
                        publicKey.getAlgorithm(), publicKey, secretKey.getEncoded());
    }

    public Optional<byte[]> getEncryptedVerifyToken() {
        return Optional.ofNullable(this.encryptedVerifyToken);
    }

    public void setEncryptedVerifyToken(byte[] encryptedVerifyToken) {
        this.encryptedVerifyToken = encryptedVerifyToken;
    }

    public Optional<SaltSignature> getSaltSignature() {
        return Optional.ofNullable(this.saltSignature);
    }

    public void setSaltSignature(@Nullable SaltSignature saltSignature2) {
        this.saltSignature = saltSignature2;
    }
}
