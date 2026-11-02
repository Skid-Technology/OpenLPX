package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.TextureProperty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.UserProfile;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.UUID;

public class WrapperLoginServerLoginSuccess extends PacketWrapper<WrapperLoginServerLoginSuccess> {

    private static final UUID FALLBACK_SESSION_ID = new UUID(0L, 0L);

    private UserProfile userProfile;

    private UUID sessionId;

    @Obsolete private boolean strictErrorHandling;

    public WrapperLoginServerLoginSuccess(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperLoginServerLoginSuccess(UUID uUID, String text) {
        this(new UserProfile(uUID, text));
    }

    public WrapperLoginServerLoginSuccess(UserProfile userProfile) {
        this(userProfile, true);
    }

    public WrapperLoginServerLoginSuccess(UserProfile userProfile, boolean matches) {
        this(userProfile, FALLBACK_SESSION_ID, matches);
    }

    public WrapperLoginServerLoginSuccess(UserProfile userProfile, UUID uUID) {
        this(userProfile, uUID, true);
    }

    public WrapperLoginServerLoginSuccess(
            UserProfile userProfile2, UUID sessionId, boolean strictErrorHandling) {
        super(PacketType.Login.Server.LOGIN_SUCCESS);
        this.userProfile = userProfile2;
        this.sessionId = sessionId;
        this.strictErrorHandling = strictErrorHandling;
    }

    @Override
    public void read() {
        UUID uUID;
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            uUID = this.readUUID();
        } else {
            uUID = UUID.fromString(this.readString(36));
        }
        String text = this.readString(16);
        this.userProfile = new UserProfile(uUID, text);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            int value = this.readVarInt();
            for (int index = 0; index < value; index++) {
                String text2 = this.readString();
                String text3 = this.readString();
                String text4 = this.readOptional(PacketWrapper::readString);
                TextureProperty textureProperty = new TextureProperty(text2, text3, text4);
                this.userProfile.getTextureProperties().add(textureProperty);
            }
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_2)) {
            this.sessionId = this.readUUID();
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)
                && this.serverVersion.isOlderThan(ServerVersion.V_1_21_2)) {
            this.strictErrorHandling = this.readBoolean();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            this.writeUUID(this.userProfile.getUUID());
        } else {
            this.writeString(this.userProfile.getUUID().toString(), 36);
        }
        this.writeString(this.userProfile.getName(), 16);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.writeVarInt(this.userProfile.getTextureProperties().size());
            for (TextureProperty textureProperty : this.userProfile.getTextureProperties()) {
                this.writeString(textureProperty.getName());
                this.writeString(textureProperty.getValue());
                this.writeOptional(textureProperty.getSignature(), PacketWrapper::writeString);
            }
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_2)) {
            this.writeUUID(this.sessionId);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)
                && this.serverVersion.isOlderThan(ServerVersion.V_1_21_2)) {
            this.writeBoolean(this.strictErrorHandling);
        }
    }

    public void copy(WrapperLoginServerLoginSuccess wrapperLoginServerLoginSuccess) {
        this.userProfile = wrapperLoginServerLoginSuccess.userProfile;
        this.sessionId = wrapperLoginServerLoginSuccess.sessionId;
        this.strictErrorHandling = wrapperLoginServerLoginSuccess.strictErrorHandling;
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(UserProfile userProfile2) {
        this.userProfile = userProfile2;
    }

    public UUID getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    @Obsolete
    public boolean isStrictErrorHandling() {
        return this.strictErrorHandling;
    }

    @Obsolete
    public void setStrictErrorHandling(boolean strictErrorHandling) {
        this.strictErrorHandling = strictErrorHandling;
    }
}
