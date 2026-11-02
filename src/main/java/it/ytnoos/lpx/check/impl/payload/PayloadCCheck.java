package it.ytnoos.lpx.check.impl.payload;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.check.BukkitCheckManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PayloadCCheck extends AbstractCheck<WrapperPlayClientPluginMessage> {

    public PayloadCCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        if (!BukkitCheckManager.VALUES.contains(wrapperPlayClientPluginMessage.getChannelName())) {
            return CheckResult.ALLOW;
        } else {
            Player player = (Player) protocolPlayer.getPlayer().orElse(null);
            if (player == null) {
                return CheckResult.ALLOW;
            } else {
                ItemStack itemInHand = player.getInventory().getItemInHand();
                if (itemInHand == null
                        || !itemInHand.getType().name().contains("QUILL")
                                && !itemInHand.getType().name().contains("WRITABLE")) {
                    FlagInfo[] flagInfos =
                            new FlagInfo[] {
                                FlagInfo.of(
                                        "hand",
                                        itemInHand == null ? "null" : itemInHand.getType().name())
                            };
                    this.flagViolation(wrapperPlayClientPluginMessage, flagInfos);
                    return CheckResult.DENY;
                } else {
                    return CheckResult.ALLOW;
                }
            }
        }
    }

    public boolean isPreVia() {
        return true;
    }
}
