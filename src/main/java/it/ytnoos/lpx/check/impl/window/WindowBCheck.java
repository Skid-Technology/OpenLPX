package it.ytnoos.lpx.check.impl.window;

import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow.WindowClickType;

import org.bukkit.entity.Player;

public class WindowBCheck extends AbstractCheck<WrapperPlayClientClickWindow> {

    private boolean enabled;

    private ServerVersion serverVersion;

    private int limit;

    public WindowBCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Boolean matches = ((Boolean) (boolean) false);
        this.enabled = this.testEnabled((String) "cancel-on-new-versions", ((boolean) matches));
        this.serverVersion = ServerVersion.V_1_21_5;
        Integer value = Integer.valueOf((byte) 8);
        this.limit = Math.max(1, this.calculateLimit((String) "new-version-max-vl", ((int) value)));
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
                int slot = wrapperPlayClientClickWindow.getSlot();
                int button = wrapperPlayClientClickWindow.getButton();
                WindowClickType windowClickType = wrapperPlayClientClickWindow.getWindowClickType();
                int value = inventoryViewAdapter.countSlots();
                if (slot >= value
                        || windowClickType == WindowClickType.SWAP && (slot < 0 || button < 0)) {
                    FlagInfo[] flagInfos = new FlagInfo[9];
                    flagInfos[0] =
                            FlagInfo.of(
                                    "action-number",
                                    wrapperPlayClientClickWindow.getActionNumber().orElse(null));
                    flagInfos[1] = FlagInfo.of("action", windowClickType.name());
                    flagInfos[2] = FlagInfo.of("button", wrapperPlayClientClickWindow.getButton());
                    flagInfos[3] = FlagInfo.of("slot", slot);
                    flagInfos[4] =
                            FlagInfo.of("converted-slot", inventoryViewAdapter.convertSlot(slot));
                    flagInfos[5] = FlagInfo.of("size", value);
                    flagInfos[6] = FlagInfo.of("inventory", inventoryViewAdapter.getType());
                    flagInfos[7] =
                            FlagInfo.of(
                                    "top-inventory",
                                    inventoryViewAdapter.getTopInventory().getType().name());
                    flagInfos[8] =
                            FlagInfo.of(
                                    "bottom-inventory",
                                    inventoryViewAdapter.getBottomInventory().getType().name());
                    boolean matches = this.increaseBuffer(wrapperPlayClientClickWindow, flagInfos);
                    return this.testPlayer(protocolPlayer) && !matches
                            ? CheckResult.ALLOW
                            : CheckResult.DENY;
                } else {
                    this.decayBuffer();
                    return CheckResult.ALLOW;
                }
            }
        }
    }

    private boolean testPlayer(ProtocolPlayer protocolPlayer) {
        if (!this.enabled) {
            return false;
        }
        ServerVersion serverVersion2 =
                protocolPlayer.getUser().getClientVersion().toServerVersion();
        return serverVersion2 == null
                ? false
                : serverVersion2.isNewerThanOrEquals(this.serverVersion)
                        && this.getOptions().getVl() < this.limit;
    }
}
