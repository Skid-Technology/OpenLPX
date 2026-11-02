package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public abstract class WrapperCommonServerServerLinks<T extends WrapperCommonServerServerLinks<T>>
        extends PacketWrapper<T> {

    private List<WrapperCommonServerServerLinks.ServerLink> links;

    public WrapperCommonServerServerLinks(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperCommonServerServerLinks(
            PacketTypeCommon packetTypeCommon,
            List<WrapperCommonServerServerLinks.ServerLink> links) {
        super(packetTypeCommon);
        this.links = links;
    }

    @Override
    public void read() {
        this.links = this.readList(WrapperCommonServerServerLinks.ServerLink::read);
    }

    @Override
    public void write() {
        this.writeList(this.links, WrapperCommonServerServerLinks.ServerLink::write);
    }

    public void copy(T t) {
        this.links = t.getLinks();
    }

    public List<WrapperCommonServerServerLinks.ServerLink> getLinks() {
        return this.links;
    }

    public void setLinks(List<WrapperCommonServerServerLinks.ServerLink> links) {
        this.links = links;
    }

    public enum KnownType {
        BUG_REPORT,
        COMMUNITY_GUIDELINES,
        SUPPORT,
        STATUS,
        FEEDBACK,
        COMMUNITY,
        WEBSITE,
        FORUMS,
        NEWS,
        ANNOUNCEMENTS
    }

    public static final class ServerLink {

        @Nullable private final WrapperCommonServerServerLinks.KnownType knownType;

        @Nullable private final Component customType;

        private final String url;

        public ServerLink(
                @Nullable WrapperCommonServerServerLinks.KnownType knownType2,
                @Nullable Component component,
                String url) {
            if (knownType2 == null == (component == null)) {
                throw new IllegalStateException(
                        "Illegal state of both known type and custom type combined: "
                                + knownType2
                                + " / "
                                + component);
            }
            this.knownType = knownType2;
            this.customType = component;
            this.url = url;
        }

        public static WrapperCommonServerServerLinks.ServerLink read(
                PacketWrapper<?> packetWrapper) {
            WrapperCommonServerServerLinks.KnownType knownType;
            Component component;
            if (packetWrapper.readBoolean()) {
                knownType =
                        packetWrapper.readEnum(WrapperCommonServerServerLinks.KnownType.values());
                component = null;
            } else {
                knownType = null;
                component = packetWrapper.readComponent();
            }
            String text = packetWrapper.readString();
            return new WrapperCommonServerServerLinks.ServerLink(knownType, component, text);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                WrapperCommonServerServerLinks.ServerLink serverLink) {
            if (serverLink.getKnownType() != null) {
                packetWrapper.writeBoolean(true);
                packetWrapper.writeEnum(serverLink.getKnownType());
            } else {
                assert serverLink.getCustomType() != null;
                packetWrapper.writeBoolean(false);
                packetWrapper.writeComponent(serverLink.getCustomType());
            }
            packetWrapper.writeString(serverLink.getUrl());
        }

        public ServerLink(WrapperCommonServerServerLinks.KnownType knownType, String text) {
            this(knownType, null, text);
        }

        public ServerLink(Component component, String text) {
            this(null, component, text);
        }

        @Nullable
        public WrapperCommonServerServerLinks.KnownType getKnownType() {
            return this.knownType;
        }

        @Nullable
        public Component getCustomType() {
            return this.customType;
        }

        public String getUrl() {
            return this.url;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof WrapperCommonServerServerLinks.ServerLink)) {
                return false;
            } else {
                WrapperCommonServerServerLinks.ServerLink serverLink =
                        (WrapperCommonServerServerLinks.ServerLink) result;
                if (this.knownType != serverLink.knownType) {
                    return false;
                } else {
                    return !Objects.equals(this.customType, serverLink.customType)
                            ? false
                            : this.url.equals(serverLink.url);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.knownType, this.customType, this.url);
        }

        @Override
        public String toString() {
            return "ServerLink{knownType="
                    + this.knownType
                    + ", customType="
                    + this.customType
                    + ", url='"
                    + this.url
                    + '\''
                    + '}';
        }
    }
}
