package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.SignatureData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class WrapperLoginClientLoginStart extends PacketWrapper<WrapperLoginClientLoginStart> {

    private String username;

    @Nullable private SignatureData signatureData;

    @Nullable private UUID playerUUID;

    public WrapperLoginClientLoginStart(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperLoginClientLoginStart(ClientVersion clientVersion, String text) {
        this(clientVersion, text, null, null);
    }

    public WrapperLoginClientLoginStart(
            ClientVersion clientVersion, String text, @Nullable SignatureData signatureData) {
        this(clientVersion, text, signatureData, null);
    }

    public WrapperLoginClientLoginStart(
            ClientVersion clientVersion,
            String username,
            @Nullable SignatureData signatureData2,
            @Nullable UUID playerUUID) {
        super(PacketType.Login.Client.LOGIN_START.getId(), clientVersion);
        this.username = username;
        this.signatureData = signatureData2;
        this.playerUUID = playerUUID;
    }

    @Override
    public void read() {
        this.username = this.readString(16);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_3)) {
                this.signatureData = this.readOptional(PacketWrapper::readSignatureData);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
                this.playerUUID = this.readUUID();
            } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_1)) {
                this.playerUUID = this.readOptional(PacketWrapper::readUUID);
            }
        }
    }

    @Override
    public void write() {
        this.writeString(this.username, 16);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_3)) {
                this.writeOptional(this.signatureData, PacketWrapper::writeSignatureData);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
                this.writeUUID(
                        Objects.requireNonNull(
                                this.playerUUID, "playerUUID is required for >= 1.20.2"));
            } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_1)) {
                this.writeOptional(this.playerUUID, PacketWrapper::writeUUID);
            }
        }
    }

    public void copy(WrapperLoginClientLoginStart wrapperLoginClientLoginStart) {
        this.username = wrapperLoginClientLoginStart.username;
        this.signatureData = wrapperLoginClientLoginStart.signatureData;
        this.playerUUID = wrapperLoginClientLoginStart.playerUUID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Optional<SignatureData> getSignatureData() {
        return Optional.ofNullable(this.signatureData);
    }

    public void setSignatureData(@Nullable SignatureData signatureData2) {
        this.signatureData = signatureData2;
    }

    public Optional<UUID> getPlayerUUID() {
        return Optional.ofNullable(this.playerUUID);
    }

    public void setPlayerUUID(@Nullable UUID playerUUID) {
        this.playerUUID = playerUUID;
    }
}
