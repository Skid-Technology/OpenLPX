package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayClientAdvancementTab
        extends PacketWrapper<WrapperPlayClientAdvancementTab> {

    private WrapperPlayClientAdvancementTab.Action action;

    @Nullable private String tabID;

    public WrapperPlayClientAdvancementTab(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientAdvancementTab(
            WrapperPlayClientAdvancementTab.Action action2, @Nullable String tabID) {
        super(PacketType.Play.Client.ADVANCEMENT_TAB);
        this.action = action2;
        this.tabID = tabID;
    }

    @Override
    public void read() {
        this.action = WrapperPlayClientAdvancementTab.Action.getById(this.readVarInt());
        if (this.action == WrapperPlayClientAdvancementTab.Action.OPENED_TAB) {
            this.tabID = this.readString();
        }
    }

    public void copy(WrapperPlayClientAdvancementTab wrapperPlayClientAdvancementTab) {
        this.action = wrapperPlayClientAdvancementTab.action;
        this.tabID = wrapperPlayClientAdvancementTab.tabID;
    }

    @Override
    public void write() {
        this.writeVarInt(this.action.ordinal());
        if (this.action == WrapperPlayClientAdvancementTab.Action.OPENED_TAB) {
            this.writeString(this.tabID);
        }
    }

    public WrapperPlayClientAdvancementTab.Action getAction() {
        return this.action;
    }

    public void setAction(WrapperPlayClientAdvancementTab.Action action2) {
        this.action = action2;
    }

    public Optional<String> getTabId() {
        return Optional.ofNullable(this.tabID);
    }

    public void setTabId(String tabID) {
        this.tabID = tabID;
    }

    public enum Action {
        OPENED_TAB,
        CLOSED_SCREEN;

        private static final WrapperPlayClientAdvancementTab.Action[] VALUES = values();

        public static WrapperPlayClientAdvancementTab.Action getById(int value) {
            return VALUES[value];
        }
    }
}
