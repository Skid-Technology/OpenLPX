package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.GsonComponentSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class WrapperPlayServerTitle extends PacketWrapper<WrapperPlayServerTitle> {

    @Deprecated public static boolean HANDLE_JSON = true;

    private WrapperPlayServerTitle.TitleAction action;

    @Nullable private Component title;

    @Nullable private Component subtitle;

    @Nullable private Component actionBar;

    private int fadeInTicks;

    private int stayTicks;

    private int fadeOutTicks;

    public WrapperPlayServerTitle(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerTitle(
            WrapperPlayServerTitle.TitleAction titleAction,
            @Nullable Component component,
            @Nullable Component component2,
            @Nullable Component component3,
            int fadeInTicks,
            int stayTicks,
            int fadeOutTicks) {
        super(PacketType.Play.Server.TITLE);
        this.action = titleAction;
        this.title = component;
        this.subtitle = component2;
        this.actionBar = component3;
        this.fadeInTicks = fadeInTicks;
        this.stayTicks = stayTicks;
        this.fadeOutTicks = fadeOutTicks;
    }

    @Deprecated
    public WrapperPlayServerTitle(
            WrapperPlayServerTitle.TitleAction titleAction,
            @Nullable String text,
            @Nullable String text2,
            @Nullable String text3,
            int fadeInTicks,
            int stayTicks,
            int fadeOutTicks) {
        super(PacketType.Play.Server.TITLE);
        this.action = titleAction;
        GsonComponentSerializer gsonComponentSerializer = this.getSerializers().gson();
        this.title = gsonComponentSerializer.deserializeOrNull(text);
        this.subtitle = gsonComponentSerializer.deserializeOrNull(text2);
        this.actionBar = gsonComponentSerializer.deserializeOrNull(text3);
        this.fadeInTicks = fadeInTicks;
        this.stayTicks = stayTicks;
        this.fadeOutTicks = fadeOutTicks;
    }

    @Override
    public void read() {
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11);
        int value = this.readVarInt();
        if (newerThanOrEquals) {
            this.action = WrapperPlayServerTitle.TitleAction.fromId(value);
        } else {
            this.action = WrapperPlayServerTitle.TitleAction.fromLegacyId(value);
        }
        switch ((WrapperPlayServerTitle.TitleAction) Objects.requireNonNull(this.action)) {
            case SET_TITLE:
                this.title = this.readComponent();
                break;
            case SET_SUBTITLE:
                this.subtitle = this.readComponent();
                break;
            case SET_ACTION_BAR:
                this.actionBar = this.readComponent();
                break;
            case SET_TIMES_AND_DISPLAY:
                this.fadeInTicks = this.readInt();
                this.stayTicks = this.readInt();
                this.fadeOutTicks = this.readInt();
        }
    }

    public void copy(WrapperPlayServerTitle wrapperPlayServerTitle) {
        this.action = wrapperPlayServerTitle.action;
        this.title = wrapperPlayServerTitle.title;
        this.subtitle = wrapperPlayServerTitle.subtitle;
        this.actionBar = wrapperPlayServerTitle.actionBar;
        this.fadeInTicks = wrapperPlayServerTitle.fadeInTicks;
        this.stayTicks = wrapperPlayServerTitle.stayTicks;
        this.fadeOutTicks = wrapperPlayServerTitle.fadeOutTicks;
    }

    @Override
    public void write() {
        int value =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11)
                        ? this.action.getId()
                        : this.action.getLegacyId();
        this.writeVarInt(value);
        switch (this.action) {
            case SET_TITLE:
                this.writeComponent(this.title);
                break;
            case SET_SUBTITLE:
                this.writeComponent(this.subtitle);
                break;
            case SET_ACTION_BAR:
                this.writeComponent(this.actionBar);
                break;
            case SET_TIMES_AND_DISPLAY:
                this.writeInt(this.fadeInTicks);
                this.writeInt(this.stayTicks);
                this.writeInt(this.fadeOutTicks);
        }
    }

    public WrapperPlayServerTitle.TitleAction getAction() {
        return this.action;
    }

    public void setAction(WrapperPlayServerTitle.TitleAction titleAction) {
        this.action = titleAction;
    }

    @Nullable
    public Component getTitle() {
        return this.title;
    }

    public void setTitle(@Nullable Component component) {
        this.title = component;
    }

    @Deprecated
    @Nullable
    public String getTitleJson() {
        return this.getSerializers().asJson(this.getTitle());
    }

    @Deprecated
    public void setTitleJson(@Nullable String text) {
        this.setTitle(this.getSerializers().fromJson(text));
    }

    @Nullable
    public Component getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(@Nullable Component component) {
        this.subtitle = component;
    }

    @Deprecated
    @Nullable
    public String getSubtitleJson() {
        return this.getSerializers().asJson(this.getSubtitle());
    }

    @Deprecated
    public void setSubtitleJson(@Nullable String text) {
        this.setSubtitle(this.getSerializers().fromJson(text));
    }

    @Nullable
    public Component getActionBar() {
        return this.actionBar;
    }

    public void setActionBar(@Nullable Component component) {
        this.actionBar = component;
    }

    @Deprecated
    @Nullable
    public String getActionBarJson() {
        return this.getSerializers().asJson(this.getActionBar());
    }

    @Deprecated
    public void setActionBarJson(@Nullable String text) {
        this.setActionBar(this.getSerializers().fromJson(text));
    }

    public int getFadeInTicks() {
        return this.fadeInTicks;
    }

    public void setFadeInTicks(int fadeInTicks) {
        this.fadeInTicks = fadeInTicks;
    }

    public int getStayTicks() {
        return this.stayTicks;
    }

    public void setStayTicks(int stayTicks) {
        this.stayTicks = stayTicks;
    }

    public int getFadeOutTicks() {
        return this.fadeOutTicks;
    }

    public void setFadeOutTicks(int fadeOutTicks) {
        this.fadeOutTicks = fadeOutTicks;
    }

    public enum TitleAction {
        SET_TITLE(0),
        SET_SUBTITLE(1),
        SET_ACTION_BAR,
        SET_TIMES_AND_DISPLAY(2),
        HIDE(3),
        RESET(4);

        private final int legacyId;

        TitleAction() {
            this(-1);
        }

        TitleAction(int legacyId) {
            this.legacyId = legacyId;
        }

        public static WrapperPlayServerTitle.TitleAction fromId(int value) {
            return values()[value];
        }

        public static WrapperPlayServerTitle.TitleAction fromLegacyId(int value) {
            WrapperPlayServerTitle.TitleAction[] titleActions;
            int value2 = (titleActions = values()).length;
            for (int index = 0; index < value2; index++) {
                WrapperPlayServerTitle.TitleAction titleAction;
                if ((titleAction = titleActions[index]).legacyId == value) {
                    return titleAction;
                }
            }
            return null;
        }

        public int getId() {
            return this.ordinal();
        }

        public int getLegacyId() {
            return this.legacyId;
        }
    }
}
