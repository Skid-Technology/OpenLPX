package it.ytnoos.lpx.check.impl.window;

import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow.WindowClickType;

import org.bukkit.entity.Player;

public class WindowDCheck extends AbstractCheck<WrapperPlayClientClickWindow> {

    public WindowDCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientClickWindow wrapperPlayClientClickWindow) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        } else {
            InventoryViewAdapter inventoryViewAdapter =
                    protocolPlayer.lpx().getInventoryViewAdapter(player);
            if (inventoryViewAdapter.isNull()) {
                return CheckResult.ALLOW;
            } else {
                String type = inventoryViewAdapter.getType();
                WindowClickType windowClickType = wrapperPlayClientClickWindow.getWindowClickType();
                if (type.contains("LECTERN") && windowClickType == WindowClickType.QUICK_MOVE) {
                    FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("inventory", type), null};
                    flagInfos[1] = FlagInfo.of("click-type", windowClickType.name());
                    this.flagViolation(wrapperPlayClientClickWindow, flagInfos);
                    return CheckResult.DENY;
                } else {
                    return CheckResult.ALLOW;
                }
            }
        }
    }
}
