package it.ytnoos.lpx.check.impl.payload;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.check.BukkitCheckManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import it.ytnoos.lpx.utils.PacketWrapperUtils;

public class PayloadDirectionCheck extends AbstractCheck<WrapperPlayClientPluginMessage> {

    private final boolean preVia;

    private static final String TEXT = "exception";

    public PayloadDirectionCheck(
            ProtocolPlayer protocolPlayer, CheckOptions checkOptions, boolean preVia) {
        super(protocolPlayer, checkOptions);
        this.preVia = preVia;
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        if (!BukkitCheckManager.VALUES.contains(wrapperPlayClientPluginMessage.getChannelName())) {
            return CheckResult.ALLOW;
        }
        if (!protocolPlayer.hasAnyCheck(ItemStackDecodeEvent.class, this.preVia)) {
            return CheckResult.ALLOW;
        }
        PacketWrapper packetWrapper = null;
        PacketWrapper packetWrapper2 = null;
        try {
            ByteBuf byteBuf = Unpooled.copiedBuffer(wrapperPlayClientPluginMessage.getData());
            boolean matches = this.preVia;
            Boolean matches2 = ((Boolean) matches);
            ProtocolPlayer protocolPlayer2 = protocolPlayer;
            ProtocolPlayer protocolPlayer3 = protocolPlayer2;
            PacketWrapper packetWrapper3 =
                    PacketWrapperUtils.createPacketWrapper(
                            protocolPlayer3, byteBuf, ((boolean) matches2));
            packetWrapper = packetWrapper3;
            ItemStack itemStack = packetWrapper3.readItemStack();
            NBTCompound nBT = itemStack.getNBT();
            protocolPlayer.setProcessingPayload(true);
            CheckResult checkResult = CheckResult.ALLOW;
            if (nBT != null) {
                NBTDecodeEvent nBTDecodeEvent =
                        new NBTDecodeEvent(wrapperPlayClientPluginMessage, null, nBT);
                CheckResult checkResult2 =
                        protocolPlayer
                                .handleChecks(NBTDecodeEvent.class, nBTDecodeEvent, this.preVia)
                                .result();
                checkResult = checkResult2;
                if (checkResult2 == CheckResult.DENY) {
                    return checkResult;
                }
            }
            ItemStackDecodeEvent itemStackDecodeEvent =
                    new ItemStackDecodeEvent(wrapperPlayClientPluginMessage, null, itemStack);
            CheckResult checkResult3 =
                    checkResult.merge(
                            protocolPlayer
                                    .handleChecks(
                                            ItemStackDecodeEvent.class,
                                            itemStackDecodeEvent,
                                            this.preVia)
                                    .result());
            checkResult = checkResult3;
            if (checkResult3 == CheckResult.DENY) {
                return checkResult;
            }
            if (checkResult == CheckResult.CHANGE) {
                ByteBuf byteBuf2 = Unpooled.buffer();
                boolean matches3 = this.preVia;
                Boolean matches4 = ((Boolean) matches3);
                protocolPlayer2 = protocolPlayer;
                ProtocolPlayer protocolPlayer4 = protocolPlayer2;
                PacketWrapper packetWrapper4 =
                        PacketWrapperUtils.createPacketWrapper(
                                protocolPlayer4, byteBuf2, ((boolean) matches4));
                packetWrapper2 = packetWrapper4;
                packetWrapper4.writeItemStack(itemStack);
                ByteBufHelper.capacity(
                        packetWrapper2.buffer, ByteBufHelper.writerIndex(packetWrapper2.buffer));
                wrapperPlayClientPluginMessage.setData(ByteBufHelper.array(packetWrapper2.buffer));
            }
            return checkResult;
        } catch (Throwable throwable) {
            FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of(TEXT, throwable.getMessage())};
            this.flagViolation(wrapperPlayClientPluginMessage, flagInfos);
            return CheckResult.DENY;
        } finally {
            protocolPlayer.setProcessingPayload(false);
            PacketWrapper packetWrapper5 = packetWrapper;
            PacketWrapperUtils.handlePacketWrapper(packetWrapper5);
            PacketWrapper packetWrapper6 = packetWrapper2;
            PacketWrapperUtils.handlePacketWrapper(packetWrapper6);
        }
    }

    public boolean isPreVia() {
        return this.preVia;
    }
}
