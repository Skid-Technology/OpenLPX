package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alerts.AlertManager;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.player.PrinterData;
import it.ytnoos.lpx.api.player.PrinterStatus;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.config.Messages;
import it.ytnoos.lpx.config.PrinterOptions;
import it.ytnoos.lpx.utils.ColorUtils;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class PlayerStateListener implements Listener {

    private final LPXBukkit lpx;

    public PlayerStateListener(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        if (this.lpx.getFirstJoinTime() == 0L) {
            this.lpx.setFirstJoinTime(System.currentTimeMillis());
        }
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (options.isEnabled7() && !this.lpx.isUpdated() && player.hasPermission("lpx.update")) {
            LPXBukkit lPXBukkit2 = this.lpx;
            ConfigManager configManager2 = lPXBukkit2.getConfigManager();
            Messages messages = configManager2.getMessages();
            String text5 = messages.getText5();
            ColorUtils.handleText(player, text5);
        }
        lPXBukkit = this.lpx;
        ConfigManager configManager3 = lPXBukkit.getConfigManager();
        GeneralOptions options2 = configManager3.getOptions();
        if (!options2.isEnabled2()) {
            lPXBukkit = this.lpx;
            AlertManager alertManager = lPXBukkit.getAlertManager();
            alertManager.testPlayer(player);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        PrinterOptions printerOptions = options.getPrinterOptions();
        if (printerOptions.isAutomatic()) {
            ProtocolPlayer protocolPlayer =
                    (ProtocolPlayer)
                            this.lpx
                                    .getRegistry()
                                    .getPlayer(blockPlaceEvent.getPlayer())
                                    .orElse(null);
            if (protocolPlayer != null) {
                PrinterData printerData = protocolPlayer.getPrinterData();
                printerData.updateLastPlace();
                if (!printerData.isUsingPrinter()) {
                    int value = printerData.increaseBlocksPlaced();
                    LPXBukkit lPXBukkit2 = this.lpx;
                    ConfigManager configManager2 = lPXBukkit2.getConfigManager();
                    GeneralOptions options2 = configManager2.getOptions();
                    PrinterOptions printerOptions2 = options2.getPrinterOptions();
                    if (value >= printerOptions2.getLimit()) {
                        printerData.setPrinterMode(PrinterStatus.AUTOMATIC);
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void commandExecute(PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        ProtocolPlayer protocolPlayer =
                (ProtocolPlayer)
                        this.lpx
                                .getRegistry()
                                .getPlayer(playerCommandPreprocessEvent.getPlayer())
                                .orElse(null);
        if (protocolPlayer != null && !protocolPlayer.ignoreChecks()) {
            if (protocolPlayer
                            .handleChecks(
                                    playerCommandPreprocessEvent.getClass(),
                                    playerCommandPreprocessEvent)
                            .result()
                    == CheckResult.DENY) {
                playerCommandPreprocessEvent.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void portalTeleport(PlayerTeleportEvent playerTeleportEvent) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (options.isEnabled9()) {
            TeleportCause cause = playerTeleportEvent.getCause();
            if (cause == TeleportCause.END_PORTAL
                    || cause == TeleportCause.NETHER_PORTAL
                    || cause.name().equals("END_GATEWAY")) {
                ProtocolPlayer protocolPlayer =
                        (ProtocolPlayer)
                                this.lpx
                                        .getRegistry()
                                        .getPlayer(playerTeleportEvent.getPlayer())
                                        .orElse(null);
                if (protocolPlayer != null && !protocolPlayer.ignoreChecks()) {
                    long value =
                            System.currentTimeMillis() - protocolPlayer.getLastPortalTeleport();
                    LPXBukkit lPXBukkit2 = this.lpx;
                    ConfigManager configManager2 = lPXBukkit2.getConfigManager();
                    GeneralOptions options2 = configManager2.getOptions();
                    if (value <= options2.getLimit2()) {
                        playerTeleportEvent.setCancelled(true);
                    } else {
                        protocolPlayer.updateLastPortalTeleport();
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void teleportEvent(PlayerTeleportEvent playerTeleportEvent) {
        this.lpx
                .getRegistry()
                .getPlayer(playerTeleportEvent.getPlayer())
                .ifPresent(ProtocolPlayer::updateLastTeleport);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void respawnEvent(PlayerRespawnEvent playerRespawnEvent) {
        this.lpx
                .getRegistry()
                .getPlayer(playerRespawnEvent.getPlayer())
                .ifPresent(ProtocolPlayer::updateLastTeleport);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void projectileLaunch(ProjectileLaunchEvent projectileLaunchEvent) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (options.isEnabled10()) {
            Projectile entity = projectileLaunchEvent.getEntity();
            if (entity instanceof Arrow) {
                double value = entity.getVelocity().lengthSquared();
                LPXBukkit lPXBukkit2 = this.lpx;
                ConfigManager configManager2 = lPXBukkit2.getConfigManager();
                GeneralOptions options2 = configManager2.getOptions();
                if (value > options2.getLimit3()) {
                    projectileLaunchEvent.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void shearEntity(PlayerShearEntityEvent playerShearEntityEvent) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (options.isEnabled11()) {
            ProtocolPlayer protocolPlayer =
                    (ProtocolPlayer)
                            this.lpx
                                    .getRegistry()
                                    .getPlayer(playerShearEntityEvent.getPlayer())
                                    .orElse(null);
            if (protocolPlayer != null && !protocolPlayer.ignoreChecks()) {
                long value = System.currentTimeMillis() - protocolPlayer.getLastShears();
                LPXBukkit lPXBukkit2 = this.lpx;
                ConfigManager configManager2 = lPXBukkit2.getConfigManager();
                GeneralOptions options2 = configManager2.getOptions();
                if (value <= options2.getLimit4()) {
                    playerShearEntityEvent.setCancelled(true);
                } else {
                    protocolPlayer.updateLastShears();
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void respawnEvent(BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (options.isEnabled12() && player.getOpenInventory() != null) {
            blockBreakEvent.getPlayer().closeInventory();
        }
    }
}
