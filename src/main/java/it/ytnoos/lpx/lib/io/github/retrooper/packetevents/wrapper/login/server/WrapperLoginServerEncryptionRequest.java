package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.MinecraftEncryptionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.security.PublicKey;

public class WrapperLoginServerEncryptionRequest
        extends PacketWrapper<WrapperLoginServerEncryptionRequest> {

    private String serverID;

    private byte[] publicKeyBytes;

    private byte[] verifyToken;

    private boolean shouldAuthenticate;

    public WrapperLoginServerEncryptionRequest(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperLoginServerEncryptionRequest(String text, PublicKey publicKey, byte[] values) {
        this(text, publicKey.getEncoded(), values);
    }

    public WrapperLoginServerEncryptionRequest(
            String text, PublicKey publicKey, byte[] values, boolean matches) {
        this(text, publicKey.getEncoded(), values, matches);
    }

    public WrapperLoginServerEncryptionRequest(String text, byte[] values, byte[] values2) {
        this(text, values, values2, true);
    }

    public WrapperLoginServerEncryptionRequest(
            String serverID,
            byte[] publicKeyBytes,
            byte[] verifyToken,
            boolean shouldAuthenticate) {
        super(PacketType.Login.Server.ENCRYPTION_REQUEST);
        this.serverID = serverID;
        this.publicKeyBytes = publicKeyBytes;
        this.verifyToken = verifyToken;
        this.shouldAuthenticate = shouldAuthenticate;
    }

    @Override
    public void read() {
        this.serverID = this.readString(20);
        this.publicKeyBytes = this.readByteArray(512);
        this.verifyToken = this.readByteArray(ByteBufHelper.readableBytes(this.buffer));
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            this.shouldAuthenticate = this.readBoolean();
        }
    }

    @Override
    public void write() {
        this.writeString(this.serverID, 20);
        this.writeByteArray(this.publicKeyBytes);
        this.writeByteArray(this.verifyToken);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            this.writeBoolean(this.shouldAuthenticate);
        }
    }

    public void copy(WrapperLoginServerEncryptionRequest wrapperLoginServerEncryptionRequest) {
        this.serverID = wrapperLoginServerEncryptionRequest.serverID;
        this.publicKeyBytes = wrapperLoginServerEncryptionRequest.publicKeyBytes;
        this.verifyToken = wrapperLoginServerEncryptionRequest.verifyToken;
        this.shouldAuthenticate = wrapperLoginServerEncryptionRequest.shouldAuthenticate;
    }

    public String getServerId() {
        return this.serverID;
    }

    public void setServerId(String serverID) {
        this.serverID = serverID;
    }

    public byte[] getPublicKeyBytes() {
        return this.publicKeyBytes;
    }

    public void setPublicKeyBytes(byte[] publicKeyBytes) {
        this.publicKeyBytes = publicKeyBytes;
    }

    public PublicKey getPublicKey() {
        return MinecraftEncryptionUtil.publicKey(this.publicKeyBytes);
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKeyBytes = publicKey.getEncoded();
    }

    public byte[] getVerifyToken() {
        return this.verifyToken;
    }

    public void setVerifyToken(byte[] verifyToken) {
        this.verifyToken = verifyToken;
    }

    public boolean isShouldAuthenticate() {
        return this.shouldAuthenticate;
    }

    public void setShouldAuthenticate(boolean shouldAuthenticate) {
        this.shouldAuthenticate = shouldAuthenticate;
    }
}
