package it.ytnoos.lpx.check.impl.payload;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;

import org.bukkit.entity.Player;

public class PayloadFCheck extends AbstractCheck<WrapperPlayClientPluginMessage> {

    public PayloadFCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (!wrapperPlayClientPluginMessage
                .getChannelName()
                .equalsIgnoreCase("purpur:beehive_c2s")) {
            return CheckResult.ALLOW;
        }
        ByteBuf byteBuf = null;
        try {
            byteBuf = Unpooled.copiedBuffer(wrapperPlayClientPluginMessage.getData());
            Vector3i vector3i = new Vector3i(ByteBufHelper.readLong(byteBuf));
            if (!player.getWorld().isChunkLoaded(vector3i.getX() >> 4, vector3i.getZ() >> 4)) {
                FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("blockpos", vector3i)};
                this.flagViolation(wrapperPlayClientPluginMessage, flagInfos);
                return CheckResult.DENY;
            }
        } catch (Exception exception) {
            FlagInfo[] flagInfos2 =
                    new FlagInfo[] {FlagInfo.of("exception", exception.getMessage())};
            this.flagViolation(wrapperPlayClientPluginMessage, flagInfos2);
            return CheckResult.DENY;
        } finally {
            if (byteBuf != null && byteBuf.refCnt() > 0) {
                byteBuf.release();
            }
        }
        return CheckResult.ALLOW;
    }
}
