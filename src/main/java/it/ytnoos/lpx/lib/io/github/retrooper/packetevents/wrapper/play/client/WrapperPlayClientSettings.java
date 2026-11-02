package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.HumanoidArm;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonClientSettings;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

public class WrapperPlayClientSettings
        extends WrapperCommonClientSettings<WrapperPlayClientSettings> {

    public WrapperPlayClientSettings(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSettings(
            String text,
            int value,
            WrapperCommonClientSettings.ChatVisibility chatVisibility,
            boolean matches,
            byte value2,
            HumanoidArm humanoidArm,
            boolean matches2,
            boolean matches3,
            WrapperCommonClientSettings.ParticleStatus particleStatus) {
        super(
                PacketType.Play.Client.CLIENT_SETTINGS,
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
    public WrapperPlayClientSettings(
            String text,
            int value,
            WrapperPlayClientSettings.ChatVisibility chatVisibility,
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
                WrapperCommonClientSettings.ParticleStatus.ALL);
    }

    @Deprecated
    public WrapperPlayClientSettings.ChatVisibility getVisibility() {
        return WrapperPlayClientSettings.ChatVisibility.MODERN_INDEX.valueOrThrow(
                super.getChatVisibility());
    }

    @Deprecated
    public void setVisibility(WrapperPlayClientSettings.ChatVisibility chatVisibility) {
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
    public enum ChatVisibility {
        FULL(WrapperCommonClientSettings.ChatVisibility.FULL),
        SYSTEM(WrapperCommonClientSettings.ChatVisibility.SYSTEM),
        HIDDEN(WrapperCommonClientSettings.ChatVisibility.HIDDEN);

        public static final WrapperPlayClientSettings.ChatVisibility[] VALUES = values();

        private static final Index<
                        WrapperCommonClientSettings.ChatVisibility,
                        WrapperPlayClientSettings.ChatVisibility>
                MODERN_INDEX =
                        Index.create(
                                WrapperPlayClientSettings.ChatVisibility.class,
                                result -> result.modern);

        private final WrapperCommonClientSettings.ChatVisibility modern;

        ChatVisibility(WrapperCommonClientSettings.ChatVisibility chatVisibility) {
            this.modern = chatVisibility;
        }
    }
}
