package it.ytnoos.lpx.check.impl.book;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BookCCheck extends AbstractCheck<WrapperPlayClientEditBook> {

    public BookCCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, WrapperPlayClientEditBook wrapperPlayClientEditBook) {
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
                this.flagViolation(wrapperPlayClientEditBook, flagInfos);
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
