package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class RedstoneListener implements Listener {

    private final LPXBukkit lpx;

    private final BlockFace[] blockFaces = BlockFace.values();

    public RedstoneListener(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
    }

    @EventHandler
    public void onRedstoneEvent(BlockRedstoneEvent blockRedstoneEvent) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (options.isEnabled13()) {
            Block block = blockRedstoneEvent.getBlock();
            String text = block.getType().name();
            if (text.contains("TRAP") && text.contains("DOOR")) {
                if (this.testBlock(block)) {
                    blockRedstoneEvent.setNewCurrent(blockRedstoneEvent.getOldCurrent());
                }
            }
        }
    }

    private boolean testBlock(Block block) {
        BlockFace[] blockFaces2 = this.blockFaces;
        int value = blockFaces2.length;
        for (int index = 0; index < value; index++) {
            BlockFace blockFace = blockFaces2[index];
            if (block.getRelative(blockFace).getType().name().contains("RAIL")) {
                return true;
            }
        }
        return false;
    }
}
