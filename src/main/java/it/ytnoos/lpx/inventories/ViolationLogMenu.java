package it.ytnoos.lpx.inventories;

import com.google.common.collect.Lists;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alerts.ViolationRecord;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.Messages;
import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryProvider;
import it.ytnoos.lpx.inventories.smartinvs.content.Pagination;
import it.ytnoos.lpx.inventories.smartinvs.content.SlotIterator;
import it.ytnoos.lpx.utils.ColorUtils;
import it.ytnoos.lpx.utils.ItemBuilder;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class ViolationLogMenu implements InventoryProvider {

    private final Format format;

    private final LPXBukkit lpx;

    public ViolationLogMenu(LPXBukkit lPXBukkit) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");
        this.format = simpleDateFormat;
        this.lpx = lPXBukkit;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        Pagination pagination2 = inventoryContents.pagination();
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        Messages messages = configManager.getMessages();
        String mainColor = messages.getMainColor();
        lPXBukkit = this.lpx;
        ConfigManager configManager2 = lPXBukkit.getConfigManager();
        Messages messages2 = configManager2.getMessages();
        String secondColor = messages2.getSecondColor();
        ArrayList<ClickableItem> values = Lists.newArrayList();
        lPXBukkit = this.lpx;
        for (ViolationRecord violationRecord :
                (Iterable<ViolationRecord>) ((Iterable) lPXBukkit.getValues())) {
            String path = violationRecord.getCheckOptions().getCheckOptions().getPath();
            String text = path.substring(0, path.length() - 1);
            String text2 = mainColor + "Config: " + secondColor + text;
            ArrayList<String> values2 = new ArrayList<>();
            values2.add(text2);
            long timestamp = violationRecord.getTimestamp();
            Long value = ((Long) timestamp);
            String text3 = this.createText(value);
            values2.add(mainColor + "Time: " + secondColor + text3);
            values2.add("");
            values2.add(mainColor + "Infos:");
            FlagInfo[] info = violationRecord.getInfo();
            int value2 = info.length;
            for (int index = 0; index < value2; index++) {
                FlagInfo flagInfo = info[index];
                String name = flagInfo.getName();
                String text4 = String.valueOf(flagInfo.getValue());
                String text5 = ColorUtils.escapeTags(text4);
                String text6 = mainColor + name + ": " + secondColor + text5;
                values2.add(text6);
            }
            values2.add("");
            values2.add(secondColor + "Press Q to delete this log");
            Material material = Material.BOOK;
            ItemBuilder itemBuilder = ItemBuilder.of(material);
            String text7 = mainColor + violationRecord.getName();
            ItemBuilder itemBuilder2 = itemBuilder.setName(text7);
            ItemBuilder itemBuilder3 = itemBuilder2.setLore(((List) values2));
            ItemStack itemStack = itemBuilder3.getItemStack();
            Consumer<org.bukkit.event.inventory.InventoryClickEvent> consumer =
                    result -> {
                        if (result.getClick() == ClickType.DROP
                                || result.getClick() == ClickType.CONTROL_DROP) {
                            LPXBukkit inventory2 = this.lpx;
                            Object[] result3 = new Object[0];
                            inventory2.getValues().remove(violationRecord);
                            SmartInventory inventory3 = this.getInventory();
                            int page = pagination2.getPage();
                            int value3 = page;
                            Integer value4 = value3;
                            Integer value5 = value4;
                            int value6 = value5;
                            int result4 = this.calculateLimit(value6);
                            int result5 = result4;
                            Integer result6 = result5;
                            Integer result7 = result6;
                            Player result8 = player;
                            Player player2 = result8;
                            int value7 = result7;
                            inventory3.open(player2, value7);
                        }
                    };
            values.add(ClickableItem.of(itemStack, consumer));
        }
        ClickableItem[] clickableItems = values.toArray(new ClickableItem[0]);
        pagination2.setItems(clickableItems);
        Integer value8 = Integer.valueOf((byte) 36);
        pagination2.setItemsPerPage(value8);
        SlotIterator.Type type = SlotIterator.Type.HORIZONTAL;
        Integer value9 = Integer.valueOf((byte) 0);
        Integer value10 = Integer.valueOf((byte) 0);
        SlotIterator.Type type2 = type;
        type = type2;
        int value11 = ((int) value10);
        SlotIterator.Type type3 = type;
        SlotIterator slotIterator = inventoryContents.newIterator(type3, value11, ((int) value9));
        pagination2.createPagination(slotIterator);
        if (!pagination2.isFirst()) {
            ItemBuilder itemBuilder4 = new ItemBuilder(Material.ARROW);
            String text8 = secondColor + "Previous page";
            ItemBuilder itemBuilder5 = itemBuilder4.setName(text8);
            ItemStack itemStack2 = itemBuilder5.getItemStack();
            Consumer consumer2 =
                    result2 -> {
                        SmartInventory inventory2 = this.getInventory();
                        Pagination pagination3 = pagination2.previous();
                        Object[] results = new Object[0];
                        int page = pagination3.getPage();
                        int value3 = page;
                        Integer value4 = value3;
                        Integer value5 = value4;
                        Player result4 = player;
                        Player result5 = result4;
                        Player result6 = result5;
                        Player result7 = result6;
                        Player result3 = result7;
                        int value12 = value5;
                        Player player3 = result3;
                        inventory2.open(player3, value12);
                    };
            ClickableItem clickableItem = ClickableItem.of(itemStack2, consumer2);
            Integer value13 = Integer.valueOf((byte) 0);
            Integer value14 = Integer.valueOf((byte) 4);
            int value15 = ((int) value13);
            int value16 = ((int) value14);
            inventoryContents.set(value16, value15, clickableItem);
        }
        if (!pagination2.isLast()) {
            ItemBuilder itemBuilder6 = new ItemBuilder(Material.ARROW);
            String text9 = secondColor + "Next page";
            ItemBuilder itemBuilder7 = itemBuilder6.setName(text9);
            ItemStack itemStack3 = itemBuilder7.getItemStack();
            Consumer consumer3 =
                    result2 -> {
                        SmartInventory inventory2 = this.getInventory();
                        Pagination pagination3 = pagination2.next();
                        Object[] results = new Object[0];
                        int page2 = pagination3.getPage();
                        Integer value17 = ((Integer) page2);
                        Player result3 = player;
                        int result8 = value17;
                        Player player4 = result3;
                        inventory2.open(player4, result8);
                    };
            ClickableItem clickableItem2 = ClickableItem.of(itemStack3, consumer3);
            Integer value18 = Integer.valueOf((byte) 8);
            Integer value19 = Integer.valueOf((byte) 4);
            int value20 = ((int) value18);
            int value21 = ((int) value19);
            inventoryContents.set(value21, value20, clickableItem2);
        }
    }

    public SmartInventory getInventory() {
        SmartInventory.Builder builder2 = SmartInventory.builder();
        builder2 = builder2.id((String) "violations");
        ViolationLogMenu violationLogMenu = this;
        builder2 = builder2.provider(violationLogMenu);
        LPXBukkit lPXBukkit = this.lpx;
        InventoryManager inventoryManager = lPXBukkit.getInventoryManager();
        builder2 = builder2.manager(inventoryManager);
        Integer value = Integer.valueOf((byte) 9);
        Integer value2 = Integer.valueOf((byte) 5);
        builder2 = builder2.size(value2, value);
        lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        Messages messages = configManager.getMessages();
        String mainColor = messages.getMainColor();
        LPXBukkit lPXBukkit2 = this.lpx;
        ConfigManager configManager2 = lPXBukkit2.getConfigManager();
        Messages messages2 = configManager2.getMessages();
        String secondColor = messages2.getSecondColor();
        String text =
                mainColor + (String) "<bold>LPX >><reset>" + secondColor + " Recent Violations";
        return builder2.title(text).build();
    }

    private String createText(long value) {
        Format format2 = this.format;
        Date date = new Date(value);
        return format2.format(date);
    }

    private int calculateLimit(int value) {
        LPXBukkit lPXBukkit = this.lpx;
        int size2 = lPXBukkit.getValues().size();
        if (size2 == 0) {
            return 0;
        }
        int value2 = (size2 - 1) / 36;
        return Math.min(value, value2);
    }
}
