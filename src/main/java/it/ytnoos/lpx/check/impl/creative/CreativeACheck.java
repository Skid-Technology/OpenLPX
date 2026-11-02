package it.ytnoos.lpx.check.impl.creative;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CreativeACheck extends AbstractCheck<PacketReceiveEvent> {

    public CreativeACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        if (packetReceiveEvent.getPacketType() != Client.CREATIVE_INVENTORY_ACTION) {
            return CheckResult.ALLOW;
        } else {
            Player player = (Player) protocolPlayer.getPlayer().orElse(null);
            if (player == null) {
                return CheckResult.ALLOW;
            } else if (!protocolPlayer.getPrinterData().isUsingPrinter()
                    && player.getGameMode() != GameMode.CREATIVE) {
                FlagInfo[] flagInfos = new FlagInfo[0];
                this.flagViolation(packetReceiveEvent, flagInfos);
                return CheckResult.DENY;
            } else {
                return CheckResult.ALLOW;
            }
        }
    }

    public boolean isPreVia() {
        return true;
    }
}
