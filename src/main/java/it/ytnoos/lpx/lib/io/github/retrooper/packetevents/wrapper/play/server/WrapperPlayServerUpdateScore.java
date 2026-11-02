package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.score.ScoreFormat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayServerUpdateScore extends PacketWrapper<WrapperPlayServerUpdateScore> {

    private String entityName;

    private WrapperPlayServerUpdateScore.Action action;

    private String objectiveName;

    private Optional<Integer> value;

    @Nullable private Component entityDisplayName;

    @Nullable private ScoreFormat scoreFormat;

    public WrapperPlayServerUpdateScore(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUpdateScore(
            String entityName,
            WrapperPlayServerUpdateScore.Action action2,
            String objectiveName,
            Optional<Integer> optional) {
        super(PacketType.Play.Server.UPDATE_SCORE);
        this.entityName = entityName;
        this.action = action2;
        this.objectiveName = objectiveName;
        this.value = optional;
    }

    public WrapperPlayServerUpdateScore(
            String entityName,
            WrapperPlayServerUpdateScore.Action action2,
            String objectiveName,
            int value2,
            @Nullable Component component,
            @Nullable ScoreFormat scoreFormat2) {
        super(PacketType.Play.Server.UPDATE_SCORE);
        this.entityName = entityName;
        this.action = action2;
        this.objectiveName = objectiveName;
        this.value = Optional.of(value2);
        this.entityDisplayName = component;
        this.scoreFormat = scoreFormat2;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            this.entityName = this.readString();
            this.objectiveName = this.readString();
            this.value = Optional.of(this.readVarInt());
            this.entityDisplayName = this.readOptional(PacketWrapper::readComponent);
            this.scoreFormat = this.readOptional(ScoreFormat::readTyped);
        } else if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.entityName = this.readString(16);
            this.action = WrapperPlayServerUpdateScore.Action.VALUES[this.readByte()];
            if (this.action != WrapperPlayServerUpdateScore.Action.REMOVE_ITEM) {
                this.objectiveName = this.readString(16);
                this.value = Optional.of(this.readInt());
            } else {
                this.objectiveName = "";
                this.value = Optional.empty();
            }
        } else {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
                this.entityName = this.readString();
            } else {
                this.entityName = this.readString(40);
            }
            this.action = WrapperPlayServerUpdateScore.Action.VALUES[this.readByte()];
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
                this.objectiveName = this.readString();
            } else {
                this.objectiveName = this.readString(16);
            }
            if (this.action != WrapperPlayServerUpdateScore.Action.REMOVE_ITEM) {
                this.value = Optional.of(this.readVarInt());
            } else {
                this.objectiveName = "";
                this.value = Optional.empty();
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            this.writeString(this.entityName);
            this.writeString(this.objectiveName);
            this.writeVarInt(this.value.orElse(0));
            this.writeOptional(this.entityDisplayName, PacketWrapper::writeComponent);
            this.writeOptional(this.scoreFormat, ScoreFormat::writeTyped);
        } else if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeString(this.entityName, 16);
            this.writeByte(this.action.ordinal());
            if (this.action != WrapperPlayServerUpdateScore.Action.REMOVE_ITEM) {
                this.writeString(this.objectiveName, 16);
                this.writeInt(this.value.orElse(-1));
            } else {
                this.objectiveName = "";
                this.value = Optional.empty();
            }
        } else {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
                this.writeString(this.entityName);
            } else {
                this.writeString(this.entityName, 40);
            }
            this.writeByte(this.action.ordinal());
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
                this.writeString(this.objectiveName);
            } else {
                this.writeString(this.objectiveName, 16);
            }
            if (this.action != WrapperPlayServerUpdateScore.Action.REMOVE_ITEM) {
                this.writeVarInt(this.value.orElse(-1));
            }
        }
    }

    public void copy(WrapperPlayServerUpdateScore wrapperPlayServerUpdateScore) {
        this.entityName = wrapperPlayServerUpdateScore.entityName;
        this.action = wrapperPlayServerUpdateScore.action;
        this.objectiveName = wrapperPlayServerUpdateScore.objectiveName;
        this.value = wrapperPlayServerUpdateScore.value;
        this.entityDisplayName = wrapperPlayServerUpdateScore.entityDisplayName;
        this.scoreFormat = wrapperPlayServerUpdateScore.scoreFormat;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public WrapperPlayServerUpdateScore.Action getAction() {
        return this.action;
    }

    public void setAction(WrapperPlayServerUpdateScore.Action action2) {
        this.action = action2;
    }

    public String getObjectiveName() {
        return this.objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public Optional<Integer> getValue() {
        return this.value;
    }

    public void setValue(Optional<Integer> optional) {
        this.value = optional;
    }

    @Nullable
    public Component getEntityDisplayName() {
        return this.entityDisplayName;
    }

    public void setEntityDisplayName(@Nullable Component component) {
        this.entityDisplayName = component;
    }

    @Nullable
    public ScoreFormat getScoreFormat() {
        return this.scoreFormat;
    }

    public void setScoreFormat(@Nullable ScoreFormat scoreFormat2) {
        this.scoreFormat = scoreFormat2;
    }

    public enum Action {
        CREATE_OR_UPDATE_ITEM,
        REMOVE_ITEM;

        public static final WrapperPlayServerUpdateScore.Action[] VALUES = values();
    }
}
