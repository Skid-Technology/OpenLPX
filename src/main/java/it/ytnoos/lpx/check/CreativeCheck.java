package it.ytnoos.lpx.check;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public abstract class CreativeCheck extends AbstractCheck<PacketReceiveEvent> {

    private final PacketTypeCommon packetType;

    public CreativeCheck(
            ProtocolPlayer protocolPlayer,
            CheckOptions checkOptions,
            PacketTypeCommon packetTypeCommon) {
        super(protocolPlayer, checkOptions);
        this.packetType = packetTypeCommon;
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        if (packetReceiveEvent.getPacketType() != this.packetType) {
            return CheckResult.ALLOW;
        } else {
            Player player = (Player) protocolPlayer.getPlayer().orElse(null);
            if (player != null && player.getGameMode() != GameMode.CREATIVE) {
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
