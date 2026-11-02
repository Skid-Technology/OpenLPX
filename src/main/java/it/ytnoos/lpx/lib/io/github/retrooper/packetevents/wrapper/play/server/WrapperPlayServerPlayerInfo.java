package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.GameMode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.TextureProperty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.UserProfile;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.SignatureData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class WrapperPlayServerPlayerInfo extends PacketWrapper<WrapperPlayServerPlayerInfo> {

    @Nullable private WrapperPlayServerPlayerInfo.Action action;

    private List<WrapperPlayServerPlayerInfo.PlayerData> playerDataList;

    public WrapperPlayServerPlayerInfo(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerPlayerInfo(
            @NotNull WrapperPlayServerPlayerInfo.Action action2,
            List<WrapperPlayServerPlayerInfo.PlayerData> playerDataList) {
        super(PacketType.Play.Server.PLAYER_INFO);
        this.action = action2;
        this.playerDataList = playerDataList;
    }

    public WrapperPlayServerPlayerInfo(
            @NotNull WrapperPlayServerPlayerInfo.Action action2,
            WrapperPlayServerPlayerInfo.PlayerData... playerData) {
        super(PacketType.Play.Server.PLAYER_INFO);
        this.action = action2;
        this.playerDataList = new ArrayList<>();
        Collections.addAll(this.playerDataList, playerData);
    }

    @Override
    public void read() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.playerDataList = new ArrayList<>(1);
            TextComponent textComponent = Component.text(this.readString());
            boolean matches = this.readBoolean();
            short value = this.readShort();
            WrapperPlayServerPlayerInfo.PlayerData playerData =
                    new WrapperPlayServerPlayerInfo.PlayerData(
                            textComponent, null, GameMode.SURVIVAL, value);
            this.playerDataList.add(playerData);
            if (matches) {
                this.action = null;
            } else {
                this.action = WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER;
            }
        } else {
            this.action = WrapperPlayServerPlayerInfo.Action.VALUES[this.readVarInt()];
            int value2 = this.readVarInt();
            this.playerDataList = new ArrayList<>(value2);
            for (int index = 0; index < value2; index++) {
                WrapperPlayServerPlayerInfo.PlayerData playerData2 = null;
                UUID uUID = this.readUUID();
                switch (this.action) {
                    case ADD_PLAYER:
                        String text2 = this.readString(16);
                        UserProfile userProfile = new UserProfile(uUID, text2);
                        int value3 = this.readVarInt();
                        for (int index2 = 0; index2 < value3; index2++) {
                            String text3 = this.readString();
                            String text4 = this.readString();
                            String text5 = this.readOptional(PacketWrapper::readString);
                            TextureProperty textureProperty =
                                    new TextureProperty(text3, text4, text5);
                            userProfile.getTextureProperties().add(textureProperty);
                        }
                        GameMode byId = GameMode.getById(this.readVarInt());
                        int value4 = this.readVarInt();
                        Component component = this.readBoolean() ? this.readComponent() : null;
                        SignatureData signatureData = null;
                        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
                            signatureData = this.readOptional(PacketWrapper::readSignatureData);
                        }
                        playerData2 =
                                new WrapperPlayServerPlayerInfo.PlayerData(
                                        component, userProfile, byId, signatureData, value4);
                        break;
                    case UPDATE_GAME_MODE:
                        GameMode byId2 = GameMode.getById(this.readVarInt());
                        playerData2 =
                                new WrapperPlayServerPlayerInfo.PlayerData(
                                        null, new UserProfile(uUID, null), byId2, -1);
                        break;
                    case UPDATE_LATENCY:
                        int value5 = this.readVarInt();
                        playerData2 =
                                new WrapperPlayServerPlayerInfo.PlayerData(
                                        null, new UserProfile(uUID, null), null, value5);
                        break;
                    case UPDATE_DISPLAY_NAME:
                        Component component2 = this.readBoolean() ? this.readComponent() : null;
                        playerData2 =
                                new WrapperPlayServerPlayerInfo.PlayerData(
                                        component2, new UserProfile(uUID, null), null, -1);
                        break;
                    case REMOVE_PLAYER:
                        playerData2 =
                                new WrapperPlayServerPlayerInfo.PlayerData(
                                        null, new UserProfile(uUID, null), null, -1);
                }
                if (playerData2 != null) {
                    this.playerDataList.add(playerData2);
                }
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            WrapperPlayServerPlayerInfo.PlayerData playerData;
            String text =
                    ((TextComponent) (playerData = this.playerDataList.get(0)).displayName)
                            .content();
            this.writeString(text);
            this.writeBoolean(this.action != WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER);
            this.writeShort(playerData.ping);
        } else {
            this.writeVarInt(this.action.ordinal());
            this.writeVarInt(this.playerDataList.size());
            for (WrapperPlayServerPlayerInfo.PlayerData playerData2 : this.playerDataList) {
                this.writeUUID(playerData2.userProfile.getUUID());
                switch (this.action) {
                    case ADD_PLAYER:
                        this.writeString(playerData2.userProfile.getName(), 16);
                        this.writeList(
                                playerData2.userProfile.getTextureProperties(),
                                (result, result2) -> {
                                    result.writeString(result2.getName());
                                    result.writeString(result2.getValue());
                                    result.writeOptional(
                                            result2.getSignature(), PacketWrapper::writeString);
                                });
                        this.writeVarInt(playerData2.gameMode.ordinal());
                        this.writeVarInt(playerData2.ping);
                        if (playerData2.displayName != null) {
                            this.writeBoolean(true);
                            this.writeComponent(playerData2.displayName);
                        } else {
                            this.writeBoolean(false);
                        }
                        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
                            this.writeOptional(
                                    playerData2.getSignatureData(),
                                    PacketWrapper::writeSignatureData);
                        }
                        break;
                    case UPDATE_GAME_MODE:
                        this.writeVarInt(playerData2.gameMode.ordinal());
                        break;
                    case UPDATE_LATENCY:
                        this.writeVarInt(playerData2.ping);
                        break;
                    case UPDATE_DISPLAY_NAME:
                        if (playerData2.displayName != null) {
                            this.writeBoolean(true);
                            this.writeComponent(playerData2.displayName);
                        } else {
                            this.writeBoolean(false);
                        }
                    case REMOVE_PLAYER:
                }
            }
        }
    }

    public void copy(WrapperPlayServerPlayerInfo wrapperPlayServerPlayerInfo) {
        this.action = wrapperPlayServerPlayerInfo.action;
        this.playerDataList = wrapperPlayServerPlayerInfo.playerDataList;
    }

    @Nullable
    public WrapperPlayServerPlayerInfo.Action getAction() {
        return this.action;
    }

    public void setAction(@NotNull WrapperPlayServerPlayerInfo.Action action2) {
        this.action = action2;
    }

    public List<WrapperPlayServerPlayerInfo.PlayerData> getPlayerDataList() {
        return this.playerDataList;
    }

    public void setPlayerDataList(List<WrapperPlayServerPlayerInfo.PlayerData> playerDataList) {
        this.playerDataList = playerDataList;
    }

    public enum Action {
        ADD_PLAYER,
        UPDATE_GAME_MODE,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        REMOVE_PLAYER;

        public static final WrapperPlayServerPlayerInfo.Action[] VALUES = values();
    }

    public static class PlayerData {

        @Nullable private Component displayName;

        @Nullable private UserProfile userProfile;

        @Nullable private GameMode gameMode;

        @Nullable private SignatureData signatureData;

        private int ping;

        public PlayerData(
                @Nullable Component component,
                @Nullable UserProfile userProfile2,
                @Nullable GameMode gameMode2,
                @Nullable SignatureData signatureData2,
                int ping) {
            this.displayName = component;
            this.userProfile = userProfile2;
            this.gameMode = gameMode2;
            this.signatureData = signatureData2;
            this.ping = ping;
        }

        public PlayerData(
                @Nullable Component component,
                @Nullable UserProfile userProfile,
                @Nullable GameMode gameMode,
                int value) {
            this(component, userProfile, gameMode, null, value);
        }

        @Nullable
        public UserProfile getUserProfile() {
            return this.userProfile;
        }

        public void setUserProfile(@Nullable UserProfile userProfile2) {
            this.userProfile = userProfile2;
        }

        public SignatureData getSignatureData() {
            return this.signatureData;
        }

        public void setSignatureData(SignatureData signatureData2) {
            this.signatureData = signatureData2;
        }

        @Deprecated
        @Nullable
        public UserProfile getUser() {
            return this.userProfile;
        }

        @Deprecated
        public void setUser(@Nullable UserProfile userProfile2) {
            this.userProfile = userProfile2;
        }

        @Nullable
        public GameMode getGameMode() {
            return this.gameMode;
        }

        public void setGameMode(@Nullable GameMode gameMode2) {
            this.gameMode = gameMode2;
        }

        public int getPing() {
            return this.ping;
        }

        public void setPing(int ping) {
            this.ping = ping;
        }

        @Nullable
        public Component getDisplayName() {
            return this.displayName;
        }

        public void setDisplayName(@Nullable Component component) {
            this.displayName = component;
        }
    }
}
