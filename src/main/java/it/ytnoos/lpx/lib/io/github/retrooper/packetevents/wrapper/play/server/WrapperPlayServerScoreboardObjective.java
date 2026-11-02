package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.score.ScoreFormat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.LegacyFormat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class WrapperPlayServerScoreboardObjective
        extends PacketWrapper<WrapperPlayServerScoreboardObjective> {

    private String name;

    private WrapperPlayServerScoreboardObjective.ObjectiveMode mode;

    private Component displayName;

    @Nullable private WrapperPlayServerScoreboardObjective.RenderType renderType;

    @Nullable private ScoreFormat scoreFormat;

    public WrapperPlayServerScoreboardObjective(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerScoreboardObjective(
            String text,
            WrapperPlayServerScoreboardObjective.ObjectiveMode objectiveMode,
            Component component,
            @Nullable WrapperPlayServerScoreboardObjective.RenderType renderType) {
        this(text, objectiveMode, component, renderType, null);
    }

    public WrapperPlayServerScoreboardObjective(
            String name,
            WrapperPlayServerScoreboardObjective.ObjectiveMode objectiveMode,
            Component component,
            @Nullable WrapperPlayServerScoreboardObjective.RenderType renderType2,
            @Nullable ScoreFormat scoreFormat2) {
        super(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        this.name = name;
        this.mode = objectiveMode;
        this.displayName = component;
        this.renderType = renderType2;
        this.scoreFormat = scoreFormat2;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.name = this.readString();
        } else {
            this.name = this.readString(16);
        }
        this.mode = WrapperPlayServerScoreboardObjective.ObjectiveMode.getById(this.readByte());
        if (this.mode != WrapperPlayServerScoreboardObjective.ObjectiveMode.CREATE
                && this.mode != WrapperPlayServerScoreboardObjective.ObjectiveMode.UPDATE) {
            this.displayName = Component.empty();
            this.renderType = WrapperPlayServerScoreboardObjective.RenderType.INTEGER;
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
                this.scoreFormat = null;
            }
        } else if (this.serverVersion.isOlderThan(ServerVersion.V_1_13)) {
            this.displayName = this.getSerializers().fromLegacy(this.readString(32));
            this.renderType =
                    WrapperPlayServerScoreboardObjective.RenderType.getByName(this.readString());
        } else {
            this.displayName = this.readComponent();
            this.renderType =
                    WrapperPlayServerScoreboardObjective.RenderType.getById(this.readVarInt());
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
                this.scoreFormat = this.readOptional(ScoreFormat::readTyped);
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.writeString(this.name);
        } else {
            this.writeString(this.name, 16);
        }
        this.writeByte((byte) this.mode.ordinal());
        if (this.mode == WrapperPlayServerScoreboardObjective.ObjectiveMode.CREATE
                || this.mode == WrapperPlayServerScoreboardObjective.ObjectiveMode.UPDATE) {
            if (this.serverVersion.isOlderThan(ServerVersion.V_1_13)) {
                String text = this.getSerializers().asLegacy(this.displayName);
                this.writeString(LegacyFormat.trimLegacyFormat(text, 32));
                if (this.renderType != null) {
                    this.writeString(this.renderType.name().toLowerCase(Locale.ROOT));
                } else {
                    this.writeString(
                            WrapperPlayServerScoreboardObjective.RenderType.INTEGER
                                    .name()
                                    .toLowerCase(Locale.ROOT));
                }
            } else {
                this.writeComponent(this.displayName);
                if (this.renderType != null) {
                    this.writeVarInt(this.renderType.ordinal());
                } else {
                    this.writeVarInt(
                            WrapperPlayServerScoreboardObjective.RenderType.INTEGER.ordinal());
                }
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
                    this.writeOptional(this.scoreFormat, ScoreFormat::writeTyped);
                }
            }
        }
    }

    public void copy(WrapperPlayServerScoreboardObjective wrapperPlayServerScoreboardObjective) {
        this.name = wrapperPlayServerScoreboardObjective.name;
        this.mode = wrapperPlayServerScoreboardObjective.mode;
        this.displayName = wrapperPlayServerScoreboardObjective.displayName;
        this.renderType = wrapperPlayServerScoreboardObjective.renderType;
        this.scoreFormat = wrapperPlayServerScoreboardObjective.scoreFormat;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WrapperPlayServerScoreboardObjective.ObjectiveMode getMode() {
        return this.mode;
    }

    public void setMode(WrapperPlayServerScoreboardObjective.ObjectiveMode objectiveMode) {
        this.mode = objectiveMode;
    }

    public Component getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(@Nullable Component component) {
        this.displayName = component;
    }

    @Nullable
    public WrapperPlayServerScoreboardObjective.RenderType getRenderType() {
        return this.renderType;
    }

    public void setRenderType(
            @Nullable WrapperPlayServerScoreboardObjective.RenderType renderType2) {
        this.renderType = renderType2;
    }

    @Nullable
    public ScoreFormat getScoreFormat() {
        return this.scoreFormat;
    }

    public void setScoreFormat(@Nullable ScoreFormat scoreFormat2) {
        this.scoreFormat = scoreFormat2;
    }

    public enum ObjectiveMode {
        CREATE,
        REMOVE,
        UPDATE;

        private static final WrapperPlayServerScoreboardObjective.ObjectiveMode[] VALUES = values();

        @Nullable
        public static WrapperPlayServerScoreboardObjective.ObjectiveMode getByName(String text) {
            WrapperPlayServerScoreboardObjective.ObjectiveMode[] objectiveModes = VALUES;
            int value = VALUES.length;
            for (int index = 0; index < value; index++) {
                WrapperPlayServerScoreboardObjective.ObjectiveMode objectiveMode;
                if ((objectiveMode = objectiveModes[index]).name().equalsIgnoreCase(text)) {
                    return objectiveMode;
                }
            }
            return null;
        }

        public static WrapperPlayServerScoreboardObjective.ObjectiveMode getById(int value) {
            return VALUES[value];
        }
    }

    public enum RenderType {
        INTEGER,
        HEARTS;

        private static final WrapperPlayServerScoreboardObjective.RenderType[] VALUES = values();

        @Nullable
        public static WrapperPlayServerScoreboardObjective.RenderType getByName(String text) {
            WrapperPlayServerScoreboardObjective.RenderType[] renderTypes = VALUES;
            int value = VALUES.length;
            for (int index = 0; index < value; index++) {
                WrapperPlayServerScoreboardObjective.RenderType renderType;
                if ((renderType = renderTypes[index]).name().equalsIgnoreCase(text)) {
                    return renderType;
                }
            }
            return null;
        }

        @Nullable
        public static WrapperPlayServerScoreboardObjective.RenderType getById(int value) {
            return VALUES[value];
        }
    }
}
