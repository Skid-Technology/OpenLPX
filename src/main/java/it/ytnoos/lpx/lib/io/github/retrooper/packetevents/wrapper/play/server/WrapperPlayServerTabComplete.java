package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WrapperPlayServerTabComplete extends PacketWrapper<WrapperPlayServerTabComplete> {

    private Optional<Integer> transactionID;

    private Optional<WrapperPlayServerTabComplete.CommandRange> commandRange;

    private List<WrapperPlayServerTabComplete.CommandMatch> commandMatches;

    public WrapperPlayServerTabComplete(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerTabComplete(
            @Nullable Integer value,
            @NotNull WrapperPlayServerTabComplete.CommandRange commandRange,
            List<WrapperPlayServerTabComplete.CommandMatch> commandMatches) {
        super(PacketType.Play.Server.TAB_COMPLETE);
        this.setTransactionId(value);
        this.setCommandRange(commandRange);
        this.commandMatches = commandMatches;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.transactionID = Optional.of(this.readVarInt());
            int value = this.readVarInt();
            int value2 = this.readVarInt();
            int value3 = this.readVarInt();
            this.commandRange =
                    Optional.of(
                            new WrapperPlayServerTabComplete.CommandRange(value, value + value2));
            this.commandMatches = new ArrayList<>(value3);
            for (int index = 0; index < value3; index++) {
                String text = this.readString();
                Component component = this.readOptional(PacketWrapper::readComponent);
                WrapperPlayServerTabComplete.CommandMatch commandMatch =
                        new WrapperPlayServerTabComplete.CommandMatch(text, component);
                this.commandMatches.add(commandMatch);
            }
        } else {
            int value4 = this.readVarInt();
            this.commandMatches = new ArrayList<>(value4);
            for (int index2 = 0; index2 < value4; index2++) {
                String text2 = this.readString();
                WrapperPlayServerTabComplete.CommandMatch commandMatch2 =
                        new WrapperPlayServerTabComplete.CommandMatch(text2, null);
                this.commandMatches.add(commandMatch2);
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.writeVarInt(this.transactionID.orElse(-1));
            WrapperPlayServerTabComplete.CommandRange commandRange2 = this.commandRange.get();
            this.writeVarInt(commandRange2.getBegin());
            this.writeVarInt(commandRange2.getLength());
            this.writeVarInt(this.commandMatches.size());
            for (WrapperPlayServerTabComplete.CommandMatch commandMatch : this.commandMatches) {
                this.writeString(commandMatch.getText());
                boolean present = commandMatch.getTooltip().isPresent();
                this.writeBoolean(present);
                if (present) {
                    this.writeComponent(commandMatch.getTooltip().get());
                }
            }
        } else {
            this.writeVarInt(this.commandMatches.size());
            for (WrapperPlayServerTabComplete.CommandMatch commandMatch2 : this.commandMatches) {
                this.writeString(commandMatch2.getText());
            }
        }
    }

    public void copy(WrapperPlayServerTabComplete wrapperPlayServerTabComplete) {
        this.transactionID = wrapperPlayServerTabComplete.transactionID;
        this.commandRange = wrapperPlayServerTabComplete.commandRange;
        this.commandMatches = wrapperPlayServerTabComplete.commandMatches;
    }

    public Optional<Integer> getTransactionId() {
        return this.transactionID;
    }

    public void setTransactionId(@Nullable Integer value) {
        this.transactionID = Optional.ofNullable(value);
    }

    public Optional<WrapperPlayServerTabComplete.CommandRange> getCommandRange() {
        return this.commandRange;
    }

    public void setCommandRange(@Nullable WrapperPlayServerTabComplete.CommandRange commandRange2) {
        this.commandRange = Optional.ofNullable(commandRange2);
    }

    public List<WrapperPlayServerTabComplete.CommandMatch> getCommandMatches() {
        return this.commandMatches;
    }

    public void setCommandMatches(List<WrapperPlayServerTabComplete.CommandMatch> commandMatches) {
        this.commandMatches = commandMatches;
    }

    public static class CommandMatch {

        private String text;

        private Optional<Component> tooltip;

        public CommandMatch(String text, @Nullable Component component) {
            this.text = text;
            this.setTooltip(component);
        }

        public CommandMatch(String text) {
            this.text = text;
            this.tooltip = Optional.empty();
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Optional<Component> getTooltip() {
            return this.tooltip;
        }

        public void setTooltip(@Nullable Component component) {
            if (component != null) {
                this.tooltip = Optional.of(component);
            } else {
                this.tooltip = Optional.empty();
            }
        }
    }

    public static class CommandRange {

        private int begin;

        private int end;

        public CommandRange(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        public int getBegin() {
            return this.begin;
        }

        public void setBegin(int begin) {
            this.begin = begin;
        }

        public int getEnd() {
            return this.end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getLength() {
            return this.end - this.begin;
        }
    }
}
