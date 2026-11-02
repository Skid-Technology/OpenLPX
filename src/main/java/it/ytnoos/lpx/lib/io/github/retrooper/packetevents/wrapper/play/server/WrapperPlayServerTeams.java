package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.LegacyComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.ColorUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class WrapperPlayServerTeams extends PacketWrapper<WrapperPlayServerTeams> {

    private String teamName;

    private WrapperPlayServerTeams.TeamMode teamMode;

    private Collection<String> players;

    private Optional<WrapperPlayServerTeams.ScoreBoardTeamInfo> teamInfo;

    public WrapperPlayServerTeams(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerTeams(
            String text,
            WrapperPlayServerTeams.TeamMode teamMode,
            @Nullable WrapperPlayServerTeams.ScoreBoardTeamInfo scoreBoardTeamInfo,
            String... text2) {
        this(text, teamMode, scoreBoardTeamInfo, Arrays.asList(text2));
    }

    public WrapperPlayServerTeams(
            String teamName,
            WrapperPlayServerTeams.TeamMode teamMode2,
            @Nullable WrapperPlayServerTeams.ScoreBoardTeamInfo scoreBoardTeamInfo,
            Collection<String> players) {
        super(PacketType.Play.Server.TEAMS);
        this.teamName = teamName;
        this.teamMode = teamMode2;
        this.players = players;
        this.teamInfo = Optional.ofNullable(scoreBoardTeamInfo);
    }

    @Deprecated
    public WrapperPlayServerTeams(
            String text,
            WrapperPlayServerTeams.TeamMode teamMode,
            Optional<WrapperPlayServerTeams.ScoreBoardTeamInfo> optional,
            String... text2) {
        this(text, teamMode, optional, Arrays.asList(text2));
    }

    @Deprecated
    public WrapperPlayServerTeams(
            String teamName,
            WrapperPlayServerTeams.TeamMode teamMode2,
            Optional<WrapperPlayServerTeams.ScoreBoardTeamInfo> optional,
            Collection<String> players) {
        super(PacketType.Play.Server.TEAMS);
        this.teamName = teamName;
        this.teamMode = teamMode2;
        this.players = players;
        this.teamInfo = optional;
    }

    @Override
    public void read() {
        int value = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18) ? 32767 : 16;
        this.teamName = this.readString(value);
        this.teamMode = WrapperPlayServerTeams.TeamMode.values()[this.readByte()];
        WrapperPlayServerTeams.ScoreBoardTeamInfo scoreBoardTeamInfo = null;
        if (this.teamMode == WrapperPlayServerTeams.TeamMode.CREATE
                || this.teamMode == WrapperPlayServerTeams.TeamMode.UPDATE) {
            scoreBoardTeamInfo = WrapperPlayServerTeams.ScoreBoardTeamInfo.read(this);
        }
        this.teamInfo = Optional.ofNullable(scoreBoardTeamInfo);
        this.players = new ArrayList<>();
        if (this.teamMode == WrapperPlayServerTeams.TeamMode.CREATE
                || this.teamMode == WrapperPlayServerTeams.TeamMode.ADD_ENTITIES
                || this.teamMode == WrapperPlayServerTeams.TeamMode.REMOVE_ENTITIES) {
            int value2;
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
                value2 = this.readShort();
            } else {
                value2 = this.readVarInt();
            }
            for (int index = 0; index < value2; index++) {
                this.players.add(this.readString(40));
            }
        }
    }

    @Override
    public void write() {
        int value = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18) ? 32767 : 16;
        this.writeString(this.teamName, value);
        this.writeByte(this.teamMode.ordinal());
        if (this.teamMode == WrapperPlayServerTeams.TeamMode.CREATE
                || this.teamMode == WrapperPlayServerTeams.TeamMode.UPDATE) {
            WrapperPlayServerTeams.ScoreBoardTeamInfo scoreBoardTeamInfo =
                    this.teamInfo.orElse(WrapperPlayServerTeams.ScoreBoardTeamInfo.EMPTY);
            WrapperPlayServerTeams.ScoreBoardTeamInfo.write(this, scoreBoardTeamInfo);
        }
        if (this.teamMode == WrapperPlayServerTeams.TeamMode.CREATE
                || this.teamMode == WrapperPlayServerTeams.TeamMode.ADD_ENTITIES
                || this.teamMode == WrapperPlayServerTeams.TeamMode.REMOVE_ENTITIES) {
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
                this.writeShort(this.players.size());
            } else {
                this.writeVarInt(this.players.size());
            }
            for (String text : this.players) {
                this.writeString(text, 40);
            }
        }
    }

    public void copy(WrapperPlayServerTeams wrapperPlayServerTeams) {
        this.teamName = wrapperPlayServerTeams.teamName;
        this.teamMode = wrapperPlayServerTeams.teamMode;
        this.players = wrapperPlayServerTeams.players;
        this.teamInfo = wrapperPlayServerTeams.teamInfo;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public WrapperPlayServerTeams.TeamMode getTeamMode() {
        return this.teamMode;
    }

    public void setTeamMode(WrapperPlayServerTeams.TeamMode teamMode2) {
        this.teamMode = teamMode2;
    }

    public Collection<String> getPlayers() {
        return this.players;
    }

    public void setPlayers(Collection<String> players) {
        this.players = players;
    }

    public Optional<WrapperPlayServerTeams.ScoreBoardTeamInfo> getTeamInfo() {
        return this.teamInfo;
    }

    public void setTeamInfo(
            @Nullable WrapperPlayServerTeams.ScoreBoardTeamInfo scoreBoardTeamInfo) {
        this.teamInfo = Optional.ofNullable(scoreBoardTeamInfo);
    }

    public enum CollisionRule {
        ALWAYS("always"),
        NEVER("never"),
        PUSH_OTHER_TEAMS("pushOtherTeams"),
        PUSH_OWN_TEAM("pushOwnTeam");

        private final String id;

        CollisionRule(String id) {
            this.id = id;
        }

        @Nullable
        public static WrapperPlayServerTeams.CollisionRule fromID(String text) {
            WrapperPlayServerTeams.CollisionRule[] collisionRules;
            int value = (collisionRules = values()).length;
            for (int index = 0; index < value; index++) {
                WrapperPlayServerTeams.CollisionRule collisionRule;
                if ((collisionRule = collisionRules[index]).id.equalsIgnoreCase(text)) {
                    return collisionRule;
                }
            }
            return null;
        }

        public String getId() {
            return this.id;
        }
    }

    public enum NameTagVisibility {
        ALWAYS("always"),
        NEVER("never"),
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
        HIDE_FOR_OWN_TEAM("hideForOwnTeam");

        private final String id;

        NameTagVisibility(String id) {
            this.id = id;
        }

        @Nullable
        public static WrapperPlayServerTeams.NameTagVisibility fromID(String text) {
            WrapperPlayServerTeams.NameTagVisibility[] nameTagVisibilitys;
            int value = (nameTagVisibilitys = values()).length;
            for (int index = 0; index < value; index++) {
                WrapperPlayServerTeams.NameTagVisibility nameTagVisibility;
                if ((nameTagVisibility = nameTagVisibilitys[index]).id.equalsIgnoreCase(text)) {
                    return nameTagVisibility;
                }
            }
            return null;
        }

        public String getId() {
            return this.id;
        }
    }

    public enum OptionData {
        NONE((byte) 0),
        FRIENDLY_FIRE((byte) 1),
        FRIENDLY_CAN_SEE_INVISIBLE((byte) 2),
        ALL((byte) 3);

        private static final WrapperPlayServerTeams.OptionData[] VALUES = values();

        private final byte byteValue;

        OptionData(byte byteValue) {
            this.byteValue = byteValue;
        }

        public byte getByteValue() {
            return this.byteValue;
        }

        @Nullable
        public static WrapperPlayServerTeams.OptionData fromValue(byte value) {
            WrapperPlayServerTeams.OptionData[] optionDatas = VALUES;
            int value2 = VALUES.length;
            for (int index = 0; index < value2; index++) {
                WrapperPlayServerTeams.OptionData optionData;
                if ((optionData = optionDatas[index]).getByteValue() == value) {
                    return optionData;
                }
            }
            return null;
        }
    }

    public static class ScoreBoardTeamInfo {

        private static final WrapperPlayServerTeams.ScoreBoardTeamInfo EMPTY =
                new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                        LegacyComponent.empty(),
                        LegacyComponent.empty(),
                        LegacyComponent.empty(),
                        WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                        WrapperPlayServerTeams.CollisionRule.ALWAYS,
                        NamedTextColor.WHITE,
                        WrapperPlayServerTeams.OptionData.NONE);

        private LegacyComponent displayName;

        private LegacyComponent prefix;

        private LegacyComponent suffix;

        private WrapperPlayServerTeams.NameTagVisibility tagVisibility;

        private WrapperPlayServerTeams.CollisionRule collisionRule;

        private NamedTextColor color;

        private WrapperPlayServerTeams.OptionData optionData;

        public ScoreBoardTeamInfo(
                Component component,
                @Nullable Component component2,
                @Nullable Component component3,
                WrapperPlayServerTeams.NameTagVisibility nameTagVisibility,
                WrapperPlayServerTeams.CollisionRule collisionRule,
                NamedTextColor namedTextColor,
                WrapperPlayServerTeams.OptionData optionData) {
            this(
                    LegacyComponent.wrapOrEmpty(component),
                    LegacyComponent.wrapOrEmpty(component2),
                    LegacyComponent.wrapOrEmpty(component3),
                    nameTagVisibility,
                    collisionRule,
                    namedTextColor,
                    optionData);
        }

        public ScoreBoardTeamInfo(
                LegacyComponent legacyComponent,
                LegacyComponent legacyComponent2,
                LegacyComponent legacyComponent3,
                WrapperPlayServerTeams.NameTagVisibility nameTagVisibility,
                WrapperPlayServerTeams.CollisionRule collisionRule2,
                NamedTextColor namedTextColor,
                WrapperPlayServerTeams.OptionData optionData2) {
            this.displayName = legacyComponent;
            this.prefix = legacyComponent2;
            this.suffix = legacyComponent3;
            this.tagVisibility = nameTagVisibility;
            this.collisionRule = collisionRule2;
            this.color = namedTextColor;
            this.optionData = optionData2;
        }

        public static WrapperPlayServerTeams.ScoreBoardTeamInfo read(
                PacketWrapper<?> packetWrapper) {
            if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_13)) {
                LegacyComponent legacyComponent = new LegacyComponent(packetWrapper.readString(32));
                LegacyComponent legacyComponent2 =
                        new LegacyComponent(packetWrapper.readString(32));
                LegacyComponent legacyComponent3 =
                        new LegacyComponent(packetWrapper.readString(32));
                WrapperPlayServerTeams.OptionData optionData =
                        packetWrapper.readEnum(WrapperPlayServerTeams.OptionData.values());
                WrapperPlayServerTeams.NameTagVisibility nameTagVisibility =
                        WrapperPlayServerTeams.NameTagVisibility.ALWAYS;
                WrapperPlayServerTeams.CollisionRule collisionRule =
                        WrapperPlayServerTeams.CollisionRule.ALWAYS;
                NamedTextColor namedTextColor = NamedTextColor.WHITE;
                if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_8)) {
                    nameTagVisibility =
                            WrapperPlayServerTeams.NameTagVisibility.fromID(
                                    packetWrapper.readString(32));
                    if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
                        collisionRule =
                                WrapperPlayServerTeams.CollisionRule.fromID(
                                        packetWrapper.readString(32));
                    }
                    if (packetWrapper
                            .getServerVersion()
                            .isNewerThanOrEquals(ServerVersion.V_1_17)) {
                        int value;
                        if ((value = packetWrapper.readVarInt()) == 21) {
                            value = -1;
                        }
                        namedTextColor = ColorUtil.fromId(value);
                    } else {
                        namedTextColor = ColorUtil.fromId(packetWrapper.readByte());
                    }
                }
                return new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                        legacyComponent,
                        legacyComponent2,
                        legacyComponent3,
                        nameTagVisibility,
                        collisionRule,
                        namedTextColor,
                        optionData);
            } else {
                LegacyComponent legacyComponent4 =
                        LegacyComponent.wrapOrEmpty(packetWrapper.readComponent());
                if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_2)) {
                    WrapperPlayServerTeams.OptionData optionData2 =
                            packetWrapper.readEnum(WrapperPlayServerTeams.OptionData.values());
                    WrapperPlayServerTeams.NameTagVisibility nameTagVisibility2;
                    WrapperPlayServerTeams.CollisionRule collisionRule2;
                    if (packetWrapper
                            .getServerVersion()
                            .isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
                        nameTagVisibility2 =
                                packetWrapper.readEnum(
                                        WrapperPlayServerTeams.NameTagVisibility.class);
                        collisionRule2 =
                                packetWrapper.readEnum(WrapperPlayServerTeams.CollisionRule.class);
                    } else {
                        nameTagVisibility2 =
                                WrapperPlayServerTeams.NameTagVisibility.fromID(
                                        packetWrapper.readString(40));
                        collisionRule2 =
                                WrapperPlayServerTeams.CollisionRule.fromID(
                                        packetWrapper.readString(40));
                    }
                    NamedTextColor namedTextColor2 = ColorUtil.fromId(packetWrapper.readByte());
                    LegacyComponent legacyComponent5 =
                            LegacyComponent.wrapOrEmpty(packetWrapper.readComponent());
                    LegacyComponent legacyComponent6 =
                            LegacyComponent.wrapOrEmpty(packetWrapper.readComponent());
                    return new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                            legacyComponent4,
                            legacyComponent5,
                            legacyComponent6,
                            nameTagVisibility2,
                            collisionRule2,
                            namedTextColor2,
                            optionData2);
                } else {
                    LegacyComponent legacyComponent7 =
                            LegacyComponent.wrapOrEmpty(packetWrapper.readComponent());
                    LegacyComponent legacyComponent8 =
                            LegacyComponent.wrapOrEmpty(packetWrapper.readComponent());
                    WrapperPlayServerTeams.NameTagVisibility nameTagVisibility3 =
                            packetWrapper.readEnum(WrapperPlayServerTeams.NameTagVisibility.class);
                    WrapperPlayServerTeams.CollisionRule collisionRule3 =
                            packetWrapper.readEnum(WrapperPlayServerTeams.CollisionRule.class);
                    NamedTextColor namedTextColor3 =
                            packetWrapper.readOptional(
                                    result -> ColorUtil.fromId(result.readVarInt()));
                    WrapperPlayServerTeams.OptionData optionData3 =
                            packetWrapper.readEnum(WrapperPlayServerTeams.OptionData.values());
                    return new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                            legacyComponent4,
                            legacyComponent7,
                            legacyComponent8,
                            nameTagVisibility3,
                            collisionRule3,
                            namedTextColor3,
                            optionData3);
                }
            }
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                WrapperPlayServerTeams.ScoreBoardTeamInfo scoreBoardTeamInfo) {
            if (packetWrapper.getServerVersion().isOlderThanOrEquals(ServerVersion.V_1_12_2)) {
                packetWrapper.writeString(scoreBoardTeamInfo.displayName.getLegacy());
                packetWrapper.writeString(scoreBoardTeamInfo.prefix.getLegacy());
                packetWrapper.writeString(scoreBoardTeamInfo.suffix.getLegacy());
                packetWrapper.writeEnum(scoreBoardTeamInfo.optionData);
                if (packetWrapper.getServerVersion().isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
                    packetWrapper.writeString(
                            WrapperPlayServerTeams.NameTagVisibility.ALWAYS.getId(), 32);
                    packetWrapper.writeByte(15);
                } else {
                    packetWrapper.writeString(scoreBoardTeamInfo.tagVisibility.getId(), 32);
                    if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
                        packetWrapper.writeString(scoreBoardTeamInfo.collisionRule.getId(), 32);
                    }
                    packetWrapper.writeByte(ColorUtil.getId(scoreBoardTeamInfo.color));
                }
            } else {
                packetWrapper.writeComponent(scoreBoardTeamInfo.displayName.getComponent());
                if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_2)) {
                    packetWrapper.writeComponent(scoreBoardTeamInfo.prefix.getComponent());
                    packetWrapper.writeComponent(scoreBoardTeamInfo.suffix.getComponent());
                    packetWrapper.writeEnum(scoreBoardTeamInfo.tagVisibility);
                    packetWrapper.writeEnum(scoreBoardTeamInfo.collisionRule);
                    packetWrapper.writeOptional(
                            scoreBoardTeamInfo.color,
                            (result, result2) -> result.writeVarInt(ColorUtil.getId(result2)));
                    packetWrapper.writeEnum(scoreBoardTeamInfo.optionData);
                } else {
                    packetWrapper.writeEnum(scoreBoardTeamInfo.optionData);
                    if (packetWrapper
                            .getServerVersion()
                            .isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
                        packetWrapper.writeEnum(scoreBoardTeamInfo.tagVisibility);
                        packetWrapper.writeEnum(scoreBoardTeamInfo.collisionRule);
                    } else {
                        packetWrapper.writeString(scoreBoardTeamInfo.tagVisibility.getId());
                        packetWrapper.writeString(scoreBoardTeamInfo.collisionRule.getId());
                    }
                    if (packetWrapper
                            .getServerVersion()
                            .isNewerThanOrEquals(ServerVersion.V_1_17)) {
                        int value;
                        if ((value = ColorUtil.getId(scoreBoardTeamInfo.color)) < 0) {
                            value = 21;
                        }
                        packetWrapper.writeVarInt(value);
                    } else {
                        packetWrapper.writeByte(ColorUtil.getId(scoreBoardTeamInfo.color));
                    }
                    packetWrapper.writeComponent(scoreBoardTeamInfo.prefix.getComponent());
                    packetWrapper.writeComponent(scoreBoardTeamInfo.suffix.getComponent());
                }
            }
        }

        public LegacyComponent getLegacyDisplayName() {
            return this.displayName;
        }

        public void setLegacyDisplayName(LegacyComponent legacyComponent) {
            this.displayName = legacyComponent;
        }

        public Component getDisplayName() {
            return this.displayName.getComponent();
        }

        public void setDisplayName(Component component) {
            this.displayName = new LegacyComponent(component);
        }

        public LegacyComponent getLegacyPrefix() {
            return this.prefix;
        }

        public void setLegacyPrefix(LegacyComponent legacyComponent) {
            this.prefix = legacyComponent;
        }

        public Component getPrefix() {
            return this.prefix.getComponent();
        }

        public void setPrefix(Component component) {
            this.prefix = new LegacyComponent(component);
        }

        public LegacyComponent getLegacySuffix() {
            return this.suffix;
        }

        public void setLegacySuffix(LegacyComponent legacyComponent) {
            this.suffix = legacyComponent;
        }

        public Component getSuffix() {
            return this.suffix.getComponent();
        }

        public void setSuffix(Component component) {
            this.suffix = new LegacyComponent(component);
        }

        public WrapperPlayServerTeams.NameTagVisibility getTagVisibility() {
            return this.tagVisibility;
        }

        public void setTagVisibility(WrapperPlayServerTeams.NameTagVisibility nameTagVisibility) {
            this.tagVisibility = nameTagVisibility;
        }

        public WrapperPlayServerTeams.CollisionRule getCollisionRule() {
            return this.collisionRule;
        }

        public void setCollisionRule(WrapperPlayServerTeams.CollisionRule collisionRule2) {
            this.collisionRule = collisionRule2;
        }

        public NamedTextColor getColor() {
            return this.color;
        }

        public void setColor(NamedTextColor namedTextColor) {
            this.color = namedTextColor;
        }

        public WrapperPlayServerTeams.OptionData getOptionData() {
            return this.optionData;
        }

        public void setOptionData(WrapperPlayServerTeams.OptionData optionData2) {
            this.optionData = optionData2;
        }
    }

    public enum TeamMode {
        CREATE,
        REMOVE,
        UPDATE,
        ADD_ENTITIES,
        REMOVE_ENTITIES
    }
}
