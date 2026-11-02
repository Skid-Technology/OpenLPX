package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.utils.BlockStateUtils;
import it.ytnoos.lpx.utils.TimeUtils;

import org.bukkit.block.Block;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener {

    private final LPXBukkit lpx;

    public InventoryListener(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void interactContainer(PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = playerInteractEvent.getClickedBlock();
            if (BlockStateUtils.getBlockState(clickedBlock) instanceof InventoryHolder) {
                LPXBukkit lPXBukkit = this.lpx;
                ConfigManager configManager = lPXBukkit.getConfigManager();
                GeneralOptions options = configManager.getOptions();
                if (options.isEnabled14()) {
                    ProtocolPlayer protocolPlayer =
                            (ProtocolPlayer)
                                    this.lpx
                                            .getRegistry()
                                            .getPlayer(playerInteractEvent.getPlayer())
                                            .orElse(null);
                    if (protocolPlayer != null
                            && !protocolPlayer.getPrinterData().isUsingPrinter()) {
                        long lastContainerInteract = protocolPlayer.getLastContainerInteract();
                        Long value = ((Long) lastContainerInteract);
                        long value2 = TimeUtils.calculateTime(value);
                        LPXBukkit lPXBukkit2 = this.lpx;
                        ConfigManager configManager2 = lPXBukkit2.getConfigManager();
                        GeneralOptions options2 = configManager2.getOptions();
                        if (value2 < options2.getTime()) {
                            playerInteractEvent.setUseInteractedBlock(Result.DENY);
                            return;
                        }
                        if (!playerInteractEvent.isCancelled()) {
                            protocolPlayer.updateLastContainerInteract();
                        }
                        return;
                    }
                    return;
                }
            }
        }
    }
}
