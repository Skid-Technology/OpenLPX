package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.config.CreativeExemption;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.utils.ItemDecodeLimits;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Item0Check extends AbstractCheck<NBTDecodeEvent> {

    private CreativeExemption creativeExemption;

    private ItemDecodeLimits itemDecodeLimits;

    private int limit;

    private RollingIntervalCounter rollingIntervalCounter;

    public Item0Check(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        String string =
                this.getOptions()
                        .getCheckOptions()
                        .getConfig()
                        .getString(
                                "options.disable-item-decoding", CreativeExemption.DEFAULT.name());
        this.creativeExemption = CreativeExemption.fromString(string);
        this.itemDecodeLimits = ItemDecodeLimits.defaults();
        this.limit =
                this.getOptions()
                        .getCheckOptions()
                        .getConfig()
                        .getInt("options.dialog-payload-max-bytes", 32768);
        RollingIntervalCounter rollingIntervalCounter2 =
                new RollingIntervalCounter(
                        this.getOptions()
                                .getCheckOptions()
                                .getConfig()
                                .getInt("options.dialog-payload-bytes-per-second", 262144));
        this.rollingIntervalCounter = rollingIntervalCounter2;
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, NBTDecodeEvent nBTDecodeEvent) {
        if (protocolPlayer.isProcessingPayload()) {
            return CheckResult.ALLOW;
        }
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        int decodedSizeBytes = nBTDecodeEvent.getNbt().getDecodedSizeBytes();
        if (this.creativeExemption != CreativeExemption.NONE) {
            if (this.testPlayer(protocolPlayer)) {
                if (decodedSizeBytes < this.limit) {
                    this.rollingIntervalCounter.increment(decodedSizeBytes);
                    if (!this.rollingIntervalCounter.isOverLimit()) {
                        return CheckResult.ALLOW;
                    }
                }
                Boolean matches = ((Boolean) (boolean) true);
                return this.check(nBTDecodeEvent, ((boolean) matches));
            }
        }
        boolean matches2 = player != null && player.getGameMode() == GameMode.CREATIVE;
        boolean matches3 = decodedSizeBytes >= this.itemDecodeLimits.calculateLimit(protocolPlayer);
        switch (this.creativeExemption) {
            case NONE:
                return CheckResult.ALLOW;
            case SMART:
                if (!matches3 || matches2) {
                    return CheckResult.ALLOW;
                }
                break;
            case ALL:
                if (matches2) {
                    return CheckResult.ALLOW;
                }
            case CREATIVE:
        }
        boolean matches4 = !matches2 || this.creativeExemption != CreativeExemption.CREATIVE;
        Boolean matches5 = ((Boolean) matches4);
        return this.check(nBTDecodeEvent, ((boolean) matches5));
    }

    private boolean testPlayer(ProtocolPlayer protocolPlayer) {
        ReceivedPacket lastReceivedPacket = protocolPlayer.getLastReceivedPacket();
        if (lastReceivedPacket == null) {
            return false;
        }
        PacketTypeCommon packetType = lastReceivedPacket.getPacketType();
        return packetType == Client.CUSTOM_CLICK_ACTION
                || packetType
                        == it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype
                                .PacketType.Configuration.Client.CUSTOM_CLICK_ACTION;
    }

    private CheckResult check(NBTDecodeEvent nBTDecodeEvent, boolean matches) {
        NBT nbt = nBTDecodeEvent.getNbt();
        ReceivedPacket lastReceivedPacket = this.player.getLastReceivedPacket();
        if (lastReceivedPacket != null && this.player.isLegacyClientOnHashedServer()) {
            lastReceivedPacket.addOriginalNbt(nbt instanceof NBTList ? nbt.copy() : nbt);
        }
        if (nbt instanceof NBTCompound) {
            NBTCompound nBTCompound = new NBTCompound();
            nBTDecodeEvent.setNbt(nBTCompound);
        } else if (nbt instanceof NBTString) {
            if (matches || ((NBTString) nbt).getValue().length() >= 256) {
                NBTString nBTString = new NBTString("");
                nBTDecodeEvent.setNbt(nBTString);
            }
        } else if (nbt instanceof NBTList nBTList2) {
            if (matches) {
                NBTList nBTList = new NBTList(nBTList2.getType(), 0);
                nBTDecodeEvent.setNbt(nBTList);
            } else {
                for (int value = nBTList2.size() - 1; value >= 0; value--) {
                    NBT tag = nBTList2.getTag(value);
                    if (tag instanceof NBTString) {
                        if (((NBTString) tag).getValue().length() >= 256) {
                            nBTList2.removeTag(value);
                        }
                    } else {
                        nBTList2.removeTag(value);
                    }
                }
                nBTDecodeEvent.setNbt(nBTList2);
            }
        }
        return CheckResult.CHANGE;
    }

    public boolean isPreVia() {
        return true;
    }
}
