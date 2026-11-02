package it.ytnoos.lpx.check.impl.window;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;

import net.minecraft.server.v1_8_R3.Container;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class WindowACheck extends AbstractCheck<WrapperPlayClientClickWindow> {

    public WindowACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientClickWindow wrapperPlayClientClickWindow) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        } else {
            Container container = ((CraftPlayer) player).getHandle().activeContainer;
            if (container == null) {
                return CheckResult.ALLOW;
            } else {
                int slot = wrapperPlayClientClickWindow.getSlot();
                int size2 = container.c.size();
                if (slot < size2 && (slot >= 0 || slot == -999 || slot == -1)) {
                    this.decayBuffer();
                    return CheckResult.ALLOW;
                } else {
                    InventoryView bukkitView = container.getBukkitView();
                    FlagInfo[] flagInfos = new FlagInfo[5];
                    flagInfos[0] = FlagInfo.of("slot", slot);
                    flagInfos[1] = FlagInfo.of("size", size2);
                    flagInfos[2] = FlagInfo.of("inventory", bukkitView.getType().name());
                    flagInfos[3] =
                            FlagInfo.of(
                                    "top-inventory", bukkitView.getTopInventory().getType().name());
                    flagInfos[4] =
                            FlagInfo.of(
                                    "bottom-inventory",
                                    bukkitView.getBottomInventory().getType().name());
                    this.increaseBuffer(wrapperPlayClientClickWindow, flagInfos);
                    return CheckResult.DENY;
                }
            }
        }
    }
}
