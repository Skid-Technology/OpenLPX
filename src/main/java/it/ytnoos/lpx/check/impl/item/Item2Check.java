package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.config.CreativeExemption;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPickItemFromBlock;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Item2Check extends AbstractCheck<WrapperPlayClientPickItemFromBlock> {

    private CreativeExemption creativeExemption;

    private static final String TEXT = "options.disable-item-decoding";

    public Item2Check(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        String string =
                this.getOptions()
                        .getCheckOptions()
                        .getConfig()
                        .getString(TEXT, CreativeExemption.DEFAULT.name());
        this.creativeExemption = CreativeExemption.fromString(string);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPickItemFromBlock wrapperPlayClientPickItemFromBlock) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        } else if (this.creativeExemption == CreativeExemption.CREATIVE
                && player.getGameMode() == GameMode.CREATIVE) {
            wrapperPlayClientPickItemFromBlock.setIncludeData(false);
            return CheckResult.CHANGE;
        } else {
            return CheckResult.ALLOW;
        }
    }

    public boolean isPreVia() {
        return true;
    }
}
