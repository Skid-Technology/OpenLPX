package it.ytnoos.lpx.inventories.smartinvs;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.opener.ChestInventoryOpener;
import it.ytnoos.lpx.inventories.smartinvs.opener.InventoryOpener;
import it.ytnoos.lpx.inventories.smartinvs.opener.SpecialInventoryOpener;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.EntityScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class InventoryManager {

    private final Plugin plugin;

    private final LPXBukkit lpx;

    private final PluginManager pluginManager;

    private final Map<Player, SmartInventory> inventories;

    private final Map<Player, InventoryContents> contents;

    private final List<InventoryOpener> values;

    private final List<InventoryOpener> values2;

    public InventoryManager(LPXBukkit lPXBukkit) {
        this.plugin = lPXBukkit.getPlugin();
        this.lpx = lPXBukkit;
        this.pluginManager = Bukkit.getPluginManager();
        HashMap entries = new HashMap();
        this.inventories = entries;
        HashMap entries2 = new HashMap();
        this.contents = entries2;
        InventoryOpener[] inventoryOpeners = new InventoryOpener[2];
        ChestInventoryOpener chestInventoryOpener = new ChestInventoryOpener();
        inventoryOpeners[0] = chestInventoryOpener;
        SpecialInventoryOpener specialInventoryOpener = new SpecialInventoryOpener();
        inventoryOpeners[1] = specialInventoryOpener;
        this.values = Arrays.asList(inventoryOpeners);
        ArrayList values3 = new ArrayList();
        this.values2 = values3;
    }

    public void init() {
        PluginManager pluginManager2 = this.pluginManager;
        InventoryManager.InvListener invListener = new InventoryManager.InvListener();
        pluginManager2.registerEvents(invListener, this.plugin);
    }

    public Optional findOpener(InventoryType inventoryType) {
        Stream<InventoryOpener> stream2 = this.values2.stream();
        Optional<InventoryOpener> optional =
                stream2.filter(
                                result -> {
                                    InventoryType inventoryType2 = inventoryType;
                                    InventoryType inventoryType3 = inventoryType2;
                                    InventoryType inventoryType4 = inventoryType3;
                                    InventoryType inventoryType5 = inventoryType4;
                                    InventoryType inventoryType6 = inventoryType5;
                                    return result.supports(inventoryType6);
                                })
                        .findAny();
        Optional optional2 = optional;
        if (!optional.isPresent()) {
            stream2 = this.values.stream();
            optional2 =
                    stream2.filter(
                                    result -> {
                                        InventoryType inventoryType2 = inventoryType;
                                        InventoryType inventoryType3 = inventoryType2;
                                        InventoryType inventoryType4 = inventoryType3;
                                        InventoryType inventoryType5 = inventoryType4;
                                        InventoryType inventoryType6 = inventoryType5;
                                        return result.supports(inventoryType6);
                                    })
                            .findAny();
        }
        return optional2;
    }

    public void handleInventoryOpeners(InventoryOpener[] inventoryOpeners) {
        this.values2.addAll(Arrays.asList(inventoryOpeners));
    }

    public List getOpenedPlayers(SmartInventory smartInventory) {
        ArrayList values = new ArrayList();
        this.inventories.forEach(
                (result, result2) -> {
                    if (smartInventory.equals(result2)) {
                        values.add(result);
                    }
                });
        return values;
    }

    public Optional<it.ytnoos.lpx.inventories.smartinvs.SmartInventory> getInventory(
            Object result) {
        return Optional.ofNullable(this.inventories.get(result));
    }

    protected void setInventory(Player player, SmartInventory smartInventory) {
        if (smartInventory == null) {
            this.inventories.remove(player);
        } else {
            this.inventories.put(player, smartInventory);
        }
    }

    public Optional<InventoryContents> getContents(Object result) {
        return Optional.ofNullable(this.contents.get(result));
    }

    protected void setContents(Player player, InventoryContents inventoryContents) {
        if (inventoryContents == null) {
            this.contents.remove(player);
        } else {
            this.contents.put(player, inventoryContents);
        }
    }

    class InvListener implements Listener {

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
            Player player = (Player) inventoryClickEvent.getWhoClicked();
            if (InventoryManager.this.inventories.containsKey(player)) {
                if (inventoryClickEvent.getAction() != InventoryAction.COLLECT_TO_CURSOR
                        && inventoryClickEvent.getAction()
                                != InventoryAction.MOVE_TO_OTHER_INVENTORY
                        && inventoryClickEvent.getAction() != InventoryAction.NOTHING) {
                    if (inventoryClickEvent.getClickedInventory()
                            == InventoryManager.this
                                    .lpx
                                    .getInventoryViewAdapter(player)
                                    .getTopInventory()) {
                        inventoryClickEvent.setCancelled(true);
                        int value = inventoryClickEvent.getSlot() / 9;
                        int value2 = inventoryClickEvent.getSlot() % 9;
                        if (value < 0 || value2 < 0) {
                            return;
                        }
                        SmartInventory smartInventory =
                                InventoryManager.this.inventories.get(player);
                        if (value >= smartInventory.getRows()) {
                            return;
                        }
                        if (value2 >= smartInventory.getColumns()) {
                            return;
                        }
                        Stream<
                                        it.ytnoos.lpx.inventories.smartinvs.InventoryListener<
                                                ? extends org.bukkit.event.Event>>
                                stream2 = smartInventory.getListeners().stream();
                        stream2 =
                                stream2.filter(
                                        result -> {
                                            return result.getType() == InventoryClickEvent.class;
                                        });
                        stream2.forEach(
                                result2 -> {
                                    InventoryClickEvent inventoryClickEvent2 = inventoryClickEvent;
                                    InventoryClickEvent inventoryClickEvent3 = inventoryClickEvent2;
                                    InventoryClickEvent inventoryClickEvent4 = inventoryClickEvent3;
                                    InventoryClickEvent inventoryClickEvent5 = inventoryClickEvent4;
                                    result2.accept(inventoryClickEvent5);
                                });
                        InventoryContents inventoryContents =
                                InventoryManager.this.contents.get(player);
                        Integer value3 = ((Integer) value2);
                        Integer value4 = ((Integer) value);
                        int value5 = ((int) value4);
                        Optional<ClickableItem> optional =
                                inventoryContents.get(value5, ((int) value3));
                        optional.ifPresent(
                                result2 -> {
                                    InventoryClickEvent inventoryClickEvent2 = inventoryClickEvent;
                                    InventoryClickEvent inventoryClickEvent3 = inventoryClickEvent2;
                                    InventoryClickEvent inventoryClickEvent4 = inventoryClickEvent3;
                                    InventoryClickEvent inventoryClickEvent5 = inventoryClickEvent4;
                                    result2.run(inventoryClickEvent5);
                                });
                        player.updateInventory();
                    }
                } else {
                    inventoryClickEvent.setCancelled(true);
                }
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryDrag(InventoryDragEvent inventoryDragEvent) {
            Player player = (Player) inventoryDragEvent.getWhoClicked();
            if (InventoryManager.this.inventories.containsKey(player)) {
                SmartInventory smartInventory = InventoryManager.this.inventories.get(player);
                for (int value : inventoryDragEvent.getRawSlots()) {
                    if (value
                            < InventoryManager.this
                                    .lpx
                                    .getInventoryViewAdapter(player)
                                    .getTopInventory()
                                    .getSize()) {
                        inventoryDragEvent.setCancelled(true);
                        break;
                    }
                }
                Stream<
                                it.ytnoos.lpx.inventories.smartinvs.InventoryListener<
                                        ? extends org.bukkit.event.Event>>
                        stream2 = smartInventory.getListeners().stream();
                stream2 =
                        stream2.filter(
                                result -> {
                                    return result.getType() == InventoryDragEvent.class;
                                });
                stream2.forEach(
                        result2 -> {
                            InventoryDragEvent result = inventoryDragEvent;
                            result2.accept(result);
                        });
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryOpen(InventoryOpenEvent inventoryOpenEvent) {
            Player player = (Player) inventoryOpenEvent.getPlayer();
            if (InventoryManager.this.inventories.containsKey(player)) {
                SmartInventory smartInventory = InventoryManager.this.inventories.get(player);
                Stream<
                                it.ytnoos.lpx.inventories.smartinvs.InventoryListener<
                                        ? extends org.bukkit.event.Event>>
                        stream2 = smartInventory.getListeners().stream();
                Stream<
                                it.ytnoos.lpx.inventories.smartinvs.InventoryListener<
                                        ? extends org.bukkit.event.Event>>
                        stream3 =
                                stream2.filter(
                                        result -> {
                                            return result.getType() == InventoryOpenEvent.class;
                                        });
                stream3.forEach(
                        result2 -> {
                            result2.accept(inventoryOpenEvent);
                        });
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
            Player player = (Player) inventoryCloseEvent.getPlayer();
            if (InventoryManager.this.inventories.containsKey(player)) {
                SmartInventory smartInventory = InventoryManager.this.inventories.get(player);
                Stream<
                                it.ytnoos.lpx.inventories.smartinvs.InventoryListener<
                                        ? extends org.bukkit.event.Event>>
                        stream2 = smartInventory.getListeners().stream();
                stream2 =
                        stream2.filter(
                                result -> {
                                    return result.getType() == InventoryCloseEvent.class;
                                });
                stream2.forEach(
                        result2 -> {
                            result2.accept(inventoryCloseEvent);
                        });
                if (smartInventory.isCloseable()) {
                    inventoryCloseEvent.getInventory().clear();
                    InventoryManager.this.inventories.remove(player);
                    InventoryManager.this.contents.remove(player);
                } else {
                    EntityScheduler entityScheduler = FoliaScheduler.getEntityScheduler();
                    entityScheduler.run(
                            player,
                            InventoryManager.this.plugin,
                            result -> player.openInventory(inventoryCloseEvent.getInventory()),
                            null);
                }
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
            Player player = playerQuitEvent.getPlayer();
            if (InventoryManager.this.inventories.containsKey(player)) {
                SmartInventory smartInventory = InventoryManager.this.inventories.get(player);
                Stream<
                                it.ytnoos.lpx.inventories.smartinvs.InventoryListener<
                                        ? extends org.bukkit.event.Event>>
                        stream2 = smartInventory.getListeners().stream();
                Stream<
                                it.ytnoos.lpx.inventories.smartinvs.InventoryListener<
                                        ? extends org.bukkit.event.Event>>
                        stream3 =
                                stream2.filter(
                                        result -> {
                                            return result.getType() == PlayerQuitEvent.class;
                                        });
                stream3.forEach(
                        result2 -> {
                            result2.accept(playerQuitEvent);
                        });
                InventoryManager.this.inventories.remove(player);
                InventoryManager.this.contents.remove(player);
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onPluginDisable(PluginDisableEvent pluginDisableEvent) {
            HashMap<Player, SmartInventory> entries =
                    new HashMap<>(InventoryManager.this.inventories);
            entries.forEach(
                    (result, result2) -> {
                        Stream<
                                        it.ytnoos.lpx.inventories.smartinvs.InventoryListener<
                                                ? extends org.bukkit.event.Event>>
                                stream2 = result2.getListeners().stream();
                        stream2 =
                                stream2.filter(
                                        result3 -> {
                                            return result3.getType() == PluginDisableEvent.class;
                                        });
                        stream2.forEach(
                                result4 -> {
                                    result4.accept(pluginDisableEvent);
                                });
                        Player player = ((Player) result);
                        result2.close(player);
                    });
            InventoryManager.this.inventories.clear();
            InventoryManager.this.contents.clear();
        }
    }
}
