package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public class WrapperPlayServerSetTitleSubtitle
        extends PacketWrapper<WrapperPlayServerSetTitleSubtitle> {

    @Deprecated public static boolean HANDLE_JSON = true;

    private Component subtitle;

    public WrapperPlayServerSetTitleSubtitle(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Deprecated
    public WrapperPlayServerSetTitleSubtitle(String text) {
        super(PacketType.Play.Server.SET_TITLE_SUBTITLE);
        this.subtitle = this.getSerializers().fromJson(text);
    }

    public WrapperPlayServerSetTitleSubtitle(Component component) {
        super(PacketType.Play.Server.SET_TITLE_SUBTITLE);
        this.subtitle = component;
    }

    @Override
    public void read() {
        this.subtitle = this.readComponent();
    }

    @Override
    public void write() {
        this.writeComponent(this.subtitle);
    }

    public void copy(WrapperPlayServerSetTitleSubtitle wrapperPlayServerSetTitleSubtitle) {
        this.subtitle = wrapperPlayServerSetTitleSubtitle.subtitle;
    }

    public Component getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(Component component) {
        this.subtitle = component;
    }

    @Deprecated
    public String getSubtitleJson() {
        return this.getSerializers().asJson(this.getSubtitle());
    }

    @Deprecated
    public void setSubtitleJson(String text) {
        this.setSubtitle(this.getSerializers().fromJson(text));
    }
}
