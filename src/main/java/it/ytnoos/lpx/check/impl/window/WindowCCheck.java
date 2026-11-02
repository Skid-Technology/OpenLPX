package it.ytnoos.lpx.check.impl.window;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;

import org.bukkit.entity.Player;

public class WindowCCheck extends AbstractCheck<WrapperPlayClientClickWindow> {

    private static final String TEXT = "slot";

    public WindowCCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientClickWindow wrapperPlayClientClickWindow) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        } else if (protocolPlayer.lpx().getInventoryViewAdapter(player).isNull()) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {FlagInfo.of(TEXT, wrapperPlayClientClickWindow.getSlot())};
            this.flagViolation(wrapperPlayClientClickWindow, flagInfos);
            return CheckResult.DENY;
        } else {
            return CheckResult.ALLOW;
        }
    }
}
