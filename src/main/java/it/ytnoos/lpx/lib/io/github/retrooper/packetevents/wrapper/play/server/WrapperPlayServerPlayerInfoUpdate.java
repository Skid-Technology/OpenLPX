package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.RemoteChatSession;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.GameMode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.TextureProperty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.UserProfile;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class WrapperPlayServerPlayerInfoUpdate
        extends PacketWrapper<WrapperPlayServerPlayerInfoUpdate> {

    private EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> actions;

    private List<WrapperPlayServerPlayerInfoUpdate.PlayerInfo> entries;

    public WrapperPlayServerPlayerInfoUpdate(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerPlayerInfoUpdate(
            EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> actions,
            List<WrapperPlayServerPlayerInfoUpdate.PlayerInfo> entries) {
        super(PacketType.Play.Server.PLAYER_INFO_UPDATE);
        this.actions = actions;
        this.entries = entries;
    }

    public WrapperPlayServerPlayerInfoUpdate(
            EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> actions,
            WrapperPlayServerPlayerInfoUpdate.PlayerInfo... playerInfo) {
        super(PacketType.Play.Server.PLAYER_INFO_UPDATE);
        this.actions = actions;
        this.entries = new ArrayList<>();
        Collections.addAll(this.entries, playerInfo);
    }

    public WrapperPlayServerPlayerInfoUpdate(
            WrapperPlayServerPlayerInfoUpdate.Action action,
            List<WrapperPlayServerPlayerInfoUpdate.PlayerInfo> values) {
        this(EnumSet.of(action), values);
    }

    public WrapperPlayServerPlayerInfoUpdate(
            WrapperPlayServerPlayerInfoUpdate.Action action,
            WrapperPlayServerPlayerInfoUpdate.PlayerInfo... playerInfo) {
        this(EnumSet.of(action), playerInfo);
    }

    @Override
    public void read() {
        this.actions = this.readEnumSet(WrapperPlayServerPlayerInfoUpdate.Action.class);
        this.entries =
                this.readList(
                        result -> {
                            UUID uUID = result.readUUID();
                            UserProfile userProfile = new UserProfile(uUID, null);
                            GameMode gameMode = GameMode.defaultGameMode();
                            boolean matches = false;
                            int value = 0;
                            RemoteChatSession remoteChatSession = null;
                            Component component = null;
                            int value2 = 0;
                            boolean matches2 = false;
                            Iterator<
                                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents
                                                    .wrapper.play.server
                                                    .WrapperPlayServerPlayerInfoUpdate.Action>
                                    iterator2 = this.actions.iterator();
                            while (iterator2.hasNext()) {
                                switch ((WrapperPlayServerPlayerInfoUpdate.Action)
                                        iterator2.next()) {
                                    case ADD_PLAYER:
                                        userProfile.setUUID(uUID);
                                        userProfile.setName(result.readString(16));
                                        int value3 = result.readVarInt();
                                        for (int index = 0; index < value3; index++) {
                                            String text = result.readString();
                                            String text2 = result.readString();
                                            String text3 =
                                                    result.readOptional(PacketWrapper::readString);
                                            TextureProperty textureProperty =
                                                    new TextureProperty(text, text2, text3);
                                            userProfile.getTextureProperties().add(textureProperty);
                                        }
                                        break;
                                    case INITIALIZE_CHAT:
                                        remoteChatSession =
                                                result.readOptional(
                                                        PacketWrapper::readRemoteChatSession);
                                        break;
                                    case UPDATE_GAME_MODE:
                                        gameMode = GameMode.getById(result.readVarInt());
                                        break;
                                    case UPDATE_LISTED:
                                        matches = result.readBoolean();
                                        break;
                                    case UPDATE_LATENCY:
                                        value = result.readVarInt();
                                        break;
                                    case UPDATE_DISPLAY_NAME:
                                        component =
                                                result.readOptional(PacketWrapper::readComponent);
                                        break;
                                    case UPDATE_LIST_ORDER:
                                        if (this.serverVersion.isNewerThanOrEquals(
                                                ServerVersion.V_1_21_2)) {
                                            value2 = result.readVarInt();
                                        }
                                        break;
                                    case UPDATE_HAT:
                                        if (this.serverVersion.isNewerThanOrEquals(
                                                ServerVersion.V_1_21_4)) {
                                            matches2 = result.readBoolean();
                                        }
                                }
                            }
                            return new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                                    userProfile,
                                    matches,
                                    value,
                                    gameMode,
                                    component,
                                    remoteChatSession,
                                    value2,
                                    matches2);
                        });
    }

    @Override
    public void write() {
        this.writeEnumSet(this.actions, WrapperPlayServerPlayerInfoUpdate.Action.class);
        this.writeList(
                this.entries,
                (result, result2) -> {
                    result.writeUUID(result2.getProfileId());
                    Iterator<
                                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play
                                            .server.WrapperPlayServerPlayerInfoUpdate.Action>
                            iterator2 = this.actions.iterator();
                    while (iterator2.hasNext()) {
                        switch ((WrapperPlayServerPlayerInfoUpdate.Action) iterator2.next()) {
                            case ADD_PLAYER:
                                result.writeString(result2.getGameProfile().getName(), 16);
                                this.writeList(
                                        result2.getGameProfile().getTextureProperties(),
                                        (result3, result4) -> {
                                            result3.writeString(result4.getName());
                                            result3.writeString(result4.getValue());
                                            result3.writeOptional(
                                                    result4.getSignature(),
                                                    PacketWrapper::writeString);
                                        });
                                break;
                            case INITIALIZE_CHAT:
                                result.writeOptional(
                                        result2.getChatSession(),
                                        PacketWrapper::writeRemoteChatSession);
                                break;
                            case UPDATE_GAME_MODE:
                                result.writeVarInt(result2.getGameMode().getId());
                                break;
                            case UPDATE_LISTED:
                                result.writeBoolean(result2.isListed());
                                break;
                            case UPDATE_LATENCY:
                                result.writeVarInt(result2.getLatency());
                                break;
                            case UPDATE_DISPLAY_NAME:
                                result.writeOptional(
                                        result2.getDisplayName(), PacketWrapper::writeComponent);
                                break;
                            case UPDATE_LIST_ORDER:
                                if (this.serverVersion.isNewerThanOrEquals(
                                        ServerVersion.V_1_21_2)) {
                                    result.writeVarInt(result2.getListOrder());
                                }
                                break;
                            case UPDATE_HAT:
                                if (this.serverVersion.isNewerThanOrEquals(
                                        ServerVersion.V_1_21_4)) {
                                    result.writeBoolean(result2.isShowHat());
                                }
                        }
                    }
                });
    }

    public void copy(WrapperPlayServerPlayerInfoUpdate wrapperPlayServerPlayerInfoUpdate) {
        this.actions = wrapperPlayServerPlayerInfoUpdate.actions;
        this.entries = wrapperPlayServerPlayerInfoUpdate.entries;
    }

    public EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> getActions() {
        return this.actions;
    }

    public void setActions(EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> actions) {
        this.actions = actions;
    }

    public List<WrapperPlayServerPlayerInfoUpdate.PlayerInfo> getEntries() {
        return this.entries;
    }

    public void setEntries(List<WrapperPlayServerPlayerInfoUpdate.PlayerInfo> entries) {
        this.entries = entries;
    }

    public enum Action {
        ADD_PLAYER,
        INITIALIZE_CHAT,
        UPDATE_GAME_MODE,
        UPDATE_LISTED,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        UPDATE_LIST_ORDER,
        UPDATE_HAT;

        public static final WrapperPlayServerPlayerInfoUpdate.Action[] VALUES = values();
    }

    public static class PlayerInfo {

        private UserProfile profile;

        private boolean listed = true;

        private int latency;

        private GameMode gameMode;

        @Nullable private Component displayName;

        @Nullable private RemoteChatSession chatSession;

        private int listOrder;

        private boolean showHat;

        public PlayerInfo(WrapperPlayServerPlayerInfoUpdate.PlayerInfo playerInfo) {
            this.profile = playerInfo.profile;
            this.listed = playerInfo.listed;
            this.latency = playerInfo.latency;
            this.gameMode = playerInfo.gameMode;
            this.displayName = playerInfo.displayName;
            this.chatSession = playerInfo.chatSession;
            this.listOrder = playerInfo.listOrder;
            this.showHat = playerInfo.showHat;
        }

        public PlayerInfo(UUID uUID) {
            this(new UserProfile(uUID, ""));
        }

        public PlayerInfo(UserProfile userProfile) {
            this.profile = userProfile;
        }

        public PlayerInfo(
                UserProfile userProfile,
                boolean matches,
                int value,
                GameMode gameMode,
                @Nullable Component component,
                @Nullable RemoteChatSession remoteChatSession) {
            this(userProfile, matches, value, gameMode, component, remoteChatSession, 0);
        }

        public PlayerInfo(
                UserProfile userProfile,
                boolean matches,
                int value,
                GameMode gameMode,
                @Nullable Component component,
                @Nullable RemoteChatSession remoteChatSession,
                int value2) {
            this(
                    userProfile,
                    matches,
                    value,
                    gameMode,
                    component,
                    remoteChatSession,
                    value2,
                    false);
        }

        public PlayerInfo(
                UserProfile userProfile,
                boolean listed,
                int latency,
                GameMode gameMode2,
                @Nullable Component component,
                @Nullable RemoteChatSession remoteChatSession,
                int listOrder,
                boolean showHat) {
            this.profile = userProfile;
            this.listed = listed;
            this.latency = latency;
            this.gameMode = gameMode2;
            this.displayName = component;
            this.chatSession = remoteChatSession;
            this.listOrder = listOrder;
            this.showHat = showHat;
        }

        public UUID getProfileId() {
            return this.profile.getUUID();
        }

        public UserProfile getGameProfile() {
            return this.profile;
        }

        public boolean isListed() {
            return this.listed;
        }

        public int getLatency() {
            return this.latency;
        }

        public GameMode getGameMode() {
            return this.gameMode;
        }

        @Nullable
        public Component getDisplayName() {
            return this.displayName;
        }

        @Nullable
        public RemoteChatSession getChatSession() {
            return this.chatSession;
        }

        public int getListOrder() {
            return this.listOrder;
        }

        public boolean isShowHat() {
            return this.showHat;
        }

        public void setGameProfile(UserProfile userProfile) {
            this.profile = userProfile;
        }

        public void setListed(boolean listed) {
            this.listed = listed;
        }

        public void setLatency(int latency) {
            this.latency = latency;
        }

        public void setGameMode(GameMode gameMode2) {
            this.gameMode = gameMode2;
        }

        public void setDisplayName(@Nullable Component component) {
            this.displayName = component;
        }

        public void setChatSession(@Nullable RemoteChatSession remoteChatSession) {
            this.chatSession = remoteChatSession;
        }

        public void setListOrder(int listOrder) {
            this.listOrder = listOrder;
        }

        public void setShowHat(boolean showHat) {
            this.showHat = showHat;
        }
    }
}
