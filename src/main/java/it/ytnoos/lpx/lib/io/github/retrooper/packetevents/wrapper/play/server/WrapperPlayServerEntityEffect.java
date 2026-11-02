package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.BitSet;

public class WrapperPlayServerEntityEffect extends PacketWrapper<WrapperPlayServerEntityEffect> {

    private int entityID;

    private PotionType potionType;

    private int effectAmplifier;

    private int effectDurationTicks;

    private byte flags;

    private NBTCompound factorData;

    public WrapperPlayServerEntityEffect(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityEffect(
            int entityID,
            PotionType potionType2,
            int effectAmplifier,
            int effectDurationTicks,
            byte flags) {
        super(PacketType.Play.Server.ENTITY_EFFECT);
        this.entityID = entityID;
        this.potionType = potionType2;
        this.effectAmplifier = effectAmplifier;
        this.effectDurationTicks = effectDurationTicks;
        this.flags = flags;
    }

    @Override
    public void read() {
        this.entityID = this.readVarInt();
        int value;
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18_2)) {
            value = this.readVarInt();
        } else {
            value = this.readByte();
        }
        this.potionType = PotionTypes.getById(value, this.serverVersion);
        this.effectAmplifier =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)
                        ? this.readVarInt()
                        : this.readByte();
        this.effectDurationTicks = this.readVarInt();
        if (this.serverVersion.isNewerThan(ServerVersion.V_1_7_10)) {
            this.flags = this.readByte();
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                && this.serverVersion.isOlderThan(ServerVersion.V_1_20_5)) {
            this.factorData = this.readOptional(PacketWrapper::readNBT);
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityID);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18_2)) {
            this.writeVarInt(this.potionType.getId(this.serverVersion.toClientVersion()));
        } else {
            this.writeByte(this.potionType.getId(this.serverVersion.toClientVersion()));
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            this.writeVarInt(this.effectAmplifier);
        } else {
            this.writeByte(this.effectAmplifier);
        }
        this.writeVarInt(this.effectDurationTicks);
        if (this.serverVersion.isNewerThan(ServerVersion.V_1_7_10)) {
            this.writeByte(this.flags);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                && this.serverVersion.isOlderThan(ServerVersion.V_1_20_5)) {
            this.writeOptional(this.factorData, PacketWrapper::writeNBT);
        }
    }

    public void copy(WrapperPlayServerEntityEffect wrapperPlayServerEntityEffect) {
        this.entityID = wrapperPlayServerEntityEffect.entityID;
        this.potionType = wrapperPlayServerEntityEffect.potionType;
        this.effectAmplifier = wrapperPlayServerEntityEffect.effectAmplifier;
        this.effectDurationTicks = wrapperPlayServerEntityEffect.effectDurationTicks;
        this.flags = wrapperPlayServerEntityEffect.flags;
        this.factorData = wrapperPlayServerEntityEffect.factorData;
    }

    public PotionType getPotionType() {
        return this.potionType;
    }

    public void setPotionType(PotionType potionType2) {
        this.potionType = potionType2;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public int getEffectAmplifier() {
        return this.effectAmplifier;
    }

    public void setEffectAmplifier(int effectAmplifier) {
        this.effectAmplifier = effectAmplifier;
    }

    public int getEffectDurationTicks() {
        return this.effectDurationTicks;
    }

    public void setEffectDurationTicks(int effectDurationTicks) {
        this.effectDurationTicks = effectDurationTicks;
    }

    private byte getFlags() {
        return this.flags;
    }

    private void setFlags(byte flags) {
        this.flags = flags;
    }

    @Nullable
    public NBTCompound getFactorData() {
        return this.factorData;
    }

    public void setFactorData(@Nullable NBTCompound nBTCompound) {
        this.factorData = nBTCompound;
    }

    private byte constructFlags(boolean matches, boolean matches2, boolean matches3) {
        BitSet bitSet = new BitSet(3);
        bitSet.set(0, matches);
        bitSet.set(1, matches2);
        bitSet.set(2, matches3);
        return bitSet.toByteArray()[0];
    }

    public boolean isAmbient() {
        return this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_8_8)
                ? false
                : (this.getFlags() & 1) == 1;
    }

    public void setAmbient(boolean matches) {
        if (this.serverVersion.isNewerThan(ServerVersion.V_1_8_8)) {
            this.setFlags(this.constructFlags(matches, this.isVisible(), this.isShowIcon()));
        }
    }

    public boolean isVisible() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            return false;
        } else {
            return this.serverVersion.isOlderThan(ServerVersion.V_1_10)
                    ? this.getFlags() != 0
                    : (this.getFlags() & 2) == 2;
        }
    }

    public void setVisible(boolean matches) {
        if (!this.serverVersion.isOlderThan(ServerVersion.V_1_10)) {
            this.setFlags(this.constructFlags(this.isAmbient(), matches, this.isShowIcon()));
        }
    }

    public boolean isShowIcon() {
        return this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_13_2)
                ? false
                : (this.getFlags() & 4) == 4;
    }

    public void setShowIcon(boolean matches) {
        if (!this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_13_2)) {
            this.setFlags(this.constructFlags(this.isAmbient(), this.isVisible(), matches));
        }
    }
}
