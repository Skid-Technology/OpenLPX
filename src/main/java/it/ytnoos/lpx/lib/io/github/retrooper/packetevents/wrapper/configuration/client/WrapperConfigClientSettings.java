package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Configuration.Client;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.HumanoidArm;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonClientSettings;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonClientSettings.ParticleStatus;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

public class WrapperConfigClientSettings
        extends WrapperCommonClientSettings<WrapperConfigClientSettings> {

    public WrapperConfigClientSettings(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperConfigClientSettings(
            String text,
            int value,
            WrapperCommonClientSettings.ChatVisibility chatVisibility,
            boolean matches,
            byte value2,
            HumanoidArm humanoidArm,
            boolean matches2,
            boolean matches3,
            ParticleStatus particleStatus) {
        super(
                Client.CLIENT_SETTINGS,
                text,
                value,
                chatVisibility,
                matches,
                value2,
                humanoidArm,
                matches2,
                matches3,
                particleStatus,
                (byte) 0);
    }

    @Deprecated
    public WrapperConfigClientSettings(
            String text,
            int value,
            WrapperConfigClientSettings.ChatVisibility chatVisibility,
            boolean matches,
            byte value2,
            HumanoidArm humanoidArm,
            boolean matches2,
            boolean matches3) {
        this(
                text,
                value,
                chatVisibility.modern,
                matches,
                value2,
                humanoidArm,
                matches2,
                matches3,
                ParticleStatus.ALL);
    }

    @Deprecated
    public WrapperConfigClientSettings.ChatVisibility getVisibility() {
        return (WrapperConfigClientSettings.ChatVisibility)
                WrapperConfigClientSettings.ChatVisibility.MODERN_INDEX.valueOrThrow(
                        this.getChatVisibility());
    }

    @Deprecated
    public void setVisibility(WrapperConfigClientSettings.ChatVisibility chatVisibility) {
        this.setChatVisibility(chatVisibility.modern);
    }

    @Deprecated
    public boolean isChatColorable() {
        return this.isChatColors();
    }

    @Deprecated
    public void setChatColorable(boolean matches) {
        this.setChatColors(matches);
    }

    @Deprecated
    public byte getVisibleSkinSectionMask() {
        return this.getSkinMask();
    }

    @Deprecated
    public void setVisibleSkinSectionMask(byte value) {
        this.setSkinMask(value);
    }

    @Deprecated
    public HumanoidArm getHand() {
        return this.getMainHand();
    }

    @Deprecated
    public void setHand(HumanoidArm humanoidArm) {
        this.setMainHand(humanoidArm);
    }

    @Deprecated
    public boolean isAllowServerListings() {
        return this.isServerListingAllowed();
    }

    @Deprecated
    public void setAllowServerListings(boolean matches) {
        this.setServerListingAllowed(matches);
    }

    @Deprecated
    public enum ChatVisibility {
        FULL(
                it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client
                        .WrapperCommonClientSettings.ChatVisibility.FULL),
        SYSTEM(
                it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client
                        .WrapperCommonClientSettings.ChatVisibility.SYSTEM),
        HIDDEN(
                it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client
                        .WrapperCommonClientSettings.ChatVisibility.HIDDEN);

        public static final WrapperConfigClientSettings.ChatVisibility[] VALUES = values();

        private static final Index<
                        WrapperCommonClientSettings.ChatVisibility,
                        WrapperConfigClientSettings.ChatVisibility>
                MODERN_INDEX =
                        Index.create(
                                WrapperConfigClientSettings.ChatVisibility.class,
                                result -> result.modern);

        private final WrapperCommonClientSettings.ChatVisibility modern;

        ChatVisibility(WrapperCommonClientSettings.ChatVisibility chatVisibility) {
            this.modern = chatVisibility;
        }
    }
}
