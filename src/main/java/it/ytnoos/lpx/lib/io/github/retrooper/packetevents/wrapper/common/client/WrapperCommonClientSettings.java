package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.HumanoidArm;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.SkinSection;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperCommonClientSettings<T extends WrapperCommonClientSettings<T>>
        extends PacketWrapper<T> {

    private String locale;

    private int viewDistance;

    private WrapperCommonClientSettings.ChatVisibility chatVisibility;

    private boolean chatColors;

    private byte skinMask;

    private HumanoidArm mainHand;

    private boolean textFilteringEnabled;

    private boolean allowServerListings;

    private WrapperCommonClientSettings.ParticleStatus particleStatus;

    @Obsolete private byte ignoredDifficulty;

    public WrapperCommonClientSettings(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperCommonClientSettings(
            PacketTypeCommon packetTypeCommon,
            String locale,
            int viewDistance,
            WrapperCommonClientSettings.ChatVisibility chatVisibility2,
            boolean chatColors,
            byte skinMask,
            HumanoidArm humanoidArm,
            boolean textFilteringEnabled,
            boolean allowServerListings,
            WrapperCommonClientSettings.ParticleStatus particleStatus2,
            byte ignoredDifficulty) {
        super(packetTypeCommon);
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatVisibility = chatVisibility2;
        this.chatColors = chatColors;
        this.skinMask = skinMask;
        this.mainHand = humanoidArm;
        this.textFilteringEnabled = textFilteringEnabled;
        this.allowServerListings = allowServerListings;
        this.particleStatus = particleStatus2;
        this.ignoredDifficulty = ignoredDifficulty;
    }

    @Override
    public void read() {
        this.locale =
                this.readString(
                        this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_12) ? 16 : 7);
        this.viewDistance = this.readByte();
        this.chatVisibility = this.readEnum(WrapperCommonClientSettings.ChatVisibility.values());
        this.chatColors = this.readBoolean();
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.ignoredDifficulty = this.readByte();
            if (this.readBoolean()) {
                this.skinMask = SkinSection.CAPE.getMask();
            }
        } else {
            this.skinMask = (byte) this.readUnsignedByte();
        }
        this.mainHand =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                        ? this.readEnum(HumanoidArm.values())
                        : HumanoidArm.RIGHT;
        this.textFilteringEnabled =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17) && this.readBoolean();
        this.allowServerListings =
                this.serverVersion.isOlderThan(ServerVersion.V_1_18) || this.readBoolean();
        this.particleStatus =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)
                        ? this.readEnum(WrapperCommonClientSettings.ParticleStatus.values())
                        : WrapperCommonClientSettings.ParticleStatus.ALL;
    }

    @Override
    public void write() {
        this.writeString(
                this.locale, this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_12) ? 16 : 7);
        this.writeByte(this.viewDistance);
        this.writeEnum(this.chatVisibility);
        this.writeBoolean(this.chatColors);
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeByte(this.ignoredDifficulty);
            this.writeBoolean(SkinSection.CAPE.isSet(this.skinMask));
        } else {
            this.writeByte(this.skinMask);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.writeEnum(this.mainHand);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            this.writeBoolean(this.textFilteringEnabled);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.writeBoolean(this.allowServerListings);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeEnum(this.particleStatus);
        }
    }

    public void copy(T t) {
        this.locale = t.getLocale();
        this.viewDistance = t.getViewDistance();
        this.chatVisibility = t.getChatVisibility();
        this.chatColors = t.isChatColors();
        this.skinMask = t.getSkinMask();
        this.mainHand = t.getMainHand();
        this.textFilteringEnabled = t.isTextFilteringEnabled();
        this.allowServerListings = t.isServerListingAllowed();
        this.particleStatus = t.getParticleStatus();
        this.ignoredDifficulty = t.getIgnoredDifficulty();
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
    }

    public WrapperCommonClientSettings.ChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }

    public void setChatVisibility(WrapperCommonClientSettings.ChatVisibility chatVisibility2) {
        this.chatVisibility = chatVisibility2;
    }

    public boolean isChatColors() {
        return this.chatColors;
    }

    public void setChatColors(boolean chatColors) {
        this.chatColors = chatColors;
    }

    public byte getSkinMask() {
        return this.skinMask;
    }

    public void setSkinMask(byte skinMask) {
        this.skinMask = skinMask;
    }

    public SkinSection getVisibleSkinSection() {
        return new SkinSection(this.skinMask);
    }

    public void setVisibleSkinSections(SkinSection skinSection) {
        this.skinMask = skinSection.getMask();
    }

    public boolean isSkinSectionVisible(SkinSection skinSection) {
        return skinSection.isSet(this.skinMask);
    }

    public void setSkinSectionVisible(SkinSection skinSection, boolean matches) {
        this.skinMask = skinSection.set(this.skinMask, matches);
    }

    public HumanoidArm getMainHand() {
        return this.mainHand;
    }

    public void setMainHand(HumanoidArm humanoidArm) {
        this.mainHand = humanoidArm;
    }

    public boolean isTextFilteringEnabled() {
        return this.textFilteringEnabled;
    }

    public void setTextFilteringEnabled(boolean textFilteringEnabled) {
        this.textFilteringEnabled = textFilteringEnabled;
    }

    public boolean isServerListingAllowed() {
        return this.allowServerListings;
    }

    public void setServerListingAllowed(boolean allowServerListings) {
        this.allowServerListings = allowServerListings;
    }

    public WrapperCommonClientSettings.ParticleStatus getParticleStatus() {
        return this.particleStatus;
    }

    public void setParticleStatus(WrapperCommonClientSettings.ParticleStatus particleStatus2) {
        this.particleStatus = particleStatus2;
    }

    @Obsolete
    public byte getIgnoredDifficulty() {
        return this.ignoredDifficulty;
    }

    @Obsolete
    public void setIgnoredDifficulty(byte ignoredDifficulty) {
        this.ignoredDifficulty = ignoredDifficulty;
    }

    public enum ChatVisibility {
        FULL,
        SYSTEM,
        HIDDEN
    }

    public enum ParticleStatus {
        ALL,
        DECREASED,
        MINIMAL
    }
}
