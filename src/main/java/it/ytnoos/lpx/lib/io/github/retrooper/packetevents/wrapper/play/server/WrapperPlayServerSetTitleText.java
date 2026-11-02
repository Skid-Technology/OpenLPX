package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public class WrapperPlayServerSetTitleText extends PacketWrapper<WrapperPlayServerSetTitleText> {

    @Deprecated public static boolean HANDLE_JSON = true;

    private Component title;

    public WrapperPlayServerSetTitleText(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSetTitleText(String text) {
        super(PacketType.Play.Server.SET_TITLE_TEXT);
        this.title = this.getSerializers().fromJson(text);
    }

    public WrapperPlayServerSetTitleText(Component component) {
        super(PacketType.Play.Server.SET_TITLE_TEXT);
        this.title = component;
    }

    @Override
    public void read() {
        this.title = this.readComponent();
    }

    @Override
    public void write() {
        this.writeComponent(this.title);
    }

    public void copy(WrapperPlayServerSetTitleText wrapperPlayServerSetTitleText) {
        this.title = wrapperPlayServerSetTitleText.title;
    }

    public Component getTitle() {
        return this.title;
    }

    public void setTitle(Component component) {
        this.title = component;
    }

    @Deprecated
    public String getTitleJson() {
        return this.getSerializers().asJson(this.getTitle());
    }

    @Deprecated
    public void setTitleJson(String text) {
        this.setTitle(this.getSerializers().fromJson(text));
    }
}
