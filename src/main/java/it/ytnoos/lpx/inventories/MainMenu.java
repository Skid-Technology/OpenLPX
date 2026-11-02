package it.ytnoos.lpx.inventories;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.Messages;
import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.utils.ItemBuilder;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MainMenu implements InventoryProvider {

    private final LPXBukkit lpx;

    public MainMenu(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        Messages messages = configManager.getMessages();
        String mainColor = messages.getMainColor();
        lPXBukkit = this.lpx;
        ConfigManager configManager2 = lPXBukkit.getConfigManager();
        Messages messages2 = configManager2.getMessages();
        String secondColor = messages2.getSecondColor();
        Material material = Material.WATER_BUCKET;
        ItemBuilder itemBuilder = ItemBuilder.of(material);
        String releaseName = PacketEvents.getAPI().getServerManager().getVersion().getReleaseName();
        String text = mainColor + "Protocol version: " + secondColor + releaseName;
        itemBuilder = itemBuilder.setName(text);
        ItemStack itemStack = itemBuilder.getItemStack();
        ClickableItem clickableItem = ClickableItem.empty(itemStack);
        Integer value = Integer.valueOf((byte) 0);
        Integer value2 = Integer.valueOf((byte) 0);
        int value3 = value;
        int value4 = value3;
        int value5 = ((int) value2);
        inventoryContents.set(value5, value4, clickableItem);
        if (player.hasPermission("lpx.reload")) {
            Material material2 = Material.NETHER_STAR;
            itemBuilder = ItemBuilder.of(material2);
            String text2 = mainColor + "Reload Config";
            itemBuilder = itemBuilder.setName(text2);
            ItemStack itemStack2 = itemBuilder.getItemStack();
            Consumer consumer =
                    result -> {
                        player.performCommand("lpx reload");
                        player.closeInventory();
                    };
            ClickableItem clickableItem2 = ClickableItem.of(itemStack2, consumer);
            Integer value6 = Integer.valueOf((byte) 4);
            Integer value7 = Integer.valueOf((byte) 0);
            value3 = value6;
            int value8 = value3;
            int value9 = ((int) value7);
            inventoryContents.set(value9, value8, clickableItem2);
        } else {
            Material material3 = Material.BARRIER;
            itemBuilder = ItemBuilder.of(material3);
            String text3 = mainColor + "Reload Config";
            itemBuilder = itemBuilder.setName(text3);
            String[] texts = new String[] {"<red><bold>NO PERMISSION"};
            itemBuilder = itemBuilder.setLore(texts);
            ItemStack itemStack3 = itemBuilder.getItemStack();
            ClickableItem clickableItem3 = ClickableItem.empty(itemStack3);
            Integer value10 = Integer.valueOf((byte) 4);
            Integer value11 = Integer.valueOf((byte) 0);
            value3 = value10;
            int value12 = value3;
            int value13 = ((int) value11);
            inventoryContents.set(value13, value12, clickableItem3);
        }
        Material material4 = Material.BOOK;
        itemBuilder = ItemBuilder.of(material4);
        String text4 = mainColor + "Recent violations";
        itemBuilder = itemBuilder.setName(text4);
        ItemStack itemStack4 = itemBuilder.getItemStack();
        Consumer consumer2 =
                result2 -> {
                    LPXBukkit lPXBukkit2 = this.lpx;
                    SmartInventory smartInventory =
                            lPXBukkit2.getMenuFactory().createSmartInventory();
                    smartInventory.createInventory(player);
                };
        ClickableItem clickableItem4 = ClickableItem.of(itemStack4, consumer2);
        Integer value14 = Integer.valueOf((byte) 8);
        Integer value15 = Integer.valueOf((byte) 0);
        value3 = value14;
        int value16 = value3;
        int value17 = ((int) value15);
        inventoryContents.set(value17, value16, clickableItem4);
    }

    public SmartInventory getInventory() {
        SmartInventory.Builder builder2 = SmartInventory.builder();
        builder2 = builder2.id((String) "main");
        MainMenu mainMenu = this;
        builder2 = builder2.provider(mainMenu);
        LPXBukkit lPXBukkit = this.lpx;
        InventoryManager inventoryManager = lPXBukkit.getInventoryManager();
        builder2 = builder2.manager(inventoryManager);
        Integer value = Integer.valueOf((byte) 9);
        Integer value2 = Integer.valueOf((byte) 1);
        builder2 = builder2.size(value2, value);
        lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        Messages messages = configManager.getMessages();
        String mainColor = messages.getMainColor();
        LPXBukkit lPXBukkit2 = this.lpx;
        ConfigManager configManager2 = lPXBukkit2.getConfigManager();
        Messages messages2 = configManager2.getMessages();
        String secondColor = messages2.getSecondColor();
        String text = mainColor + (String) "<bold>LPX >><reset>" + secondColor + " Main GUI";
        return builder2.title(text).build();
    }
}
