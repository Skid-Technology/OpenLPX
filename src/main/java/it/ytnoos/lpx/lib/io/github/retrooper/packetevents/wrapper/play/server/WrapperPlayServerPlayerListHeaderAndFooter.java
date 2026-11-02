package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.GsonComponentSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public class WrapperPlayServerPlayerListHeaderAndFooter
        extends PacketWrapper<WrapperPlayServerPlayerListHeaderAndFooter> {

    @Deprecated public static boolean HANDLE_JSON = true;

    private Component header;

    private Component footer;

    public WrapperPlayServerPlayerListHeaderAndFooter(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Deprecated
    public WrapperPlayServerPlayerListHeaderAndFooter(String text, String text2) {
        super(PacketType.Play.Server.PLAYER_LIST_HEADER_AND_FOOTER);
        GsonComponentSerializer gsonComponentSerializer = this.getSerializers().gson();
        this.header = gsonComponentSerializer.deserializeOrNull(text);
        this.footer = gsonComponentSerializer.deserializeOrNull(text2);
    }

    public WrapperPlayServerPlayerListHeaderAndFooter(Component component, Component component2) {
        super(PacketType.Play.Server.PLAYER_LIST_HEADER_AND_FOOTER);
        this.header = component;
        this.footer = component2;
    }

    @Override
    public void read() {
        this.header = this.readComponent();
        this.footer = this.readComponent();
    }

    @Override
    public void write() {
        this.writeComponent(this.header);
        this.writeComponent(this.footer);
    }

    public void copy(
            WrapperPlayServerPlayerListHeaderAndFooter wrapperPlayServerPlayerListHeaderAndFooter) {
        this.header = wrapperPlayServerPlayerListHeaderAndFooter.header;
        this.footer = wrapperPlayServerPlayerListHeaderAndFooter.footer;
    }

    public Component getHeader() {
        return this.header;
    }

    public void setHeader(Component component) {
        this.header = component;
    }

    public Component getFooter() {
        return this.footer;
    }

    public void setFooter(Component component) {
        this.footer = component;
    }

    @Deprecated
    public String getHeaderJson() {
        return this.getSerializers().asJson(this.getHeader());
    }

    @Deprecated
    public void setHeaderJson(String text) {
        this.setHeader(this.getSerializers().fromJson(text));
    }

    @Deprecated
    public String getFooterJson() {
        return this.getSerializers().asJson(this.getFooter());
    }

    @Deprecated
    public void setFooterJson(String text) {
        this.setFooter(this.getSerializers().fromJson(text));
    }

    @Deprecated
    public Component getHeaderComponent() {
        return this.getHeader();
    }

    @Deprecated
    public void setHeaderComponent(Component component) {
        this.setHeader(component);
    }

    @Deprecated
    public Component getFooterComponent() {
        return this.getFooter();
    }

    @Deprecated
    public void setFooterComponent(Component component) {
        this.setFooter(component);
    }
}
