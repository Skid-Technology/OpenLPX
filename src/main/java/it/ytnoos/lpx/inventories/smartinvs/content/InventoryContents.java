package it.ytnoos.lpx.inventories.smartinvs.content;

import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface InventoryContents {

    SmartInventory inventory();

    Pagination pagination();

    Optional iterator(Object result);

    SlotIterator newIterator(Object result, SlotIterator.Type type, int value, int value2);

    SlotIterator newIterator(SlotIterator.Type type, int value, int value2);

    SlotIterator newIterator(String text, SlotIterator.Type type, SlotPos slotPos);

    SlotIterator newIterator(SlotIterator.Type type, SlotPos slotPos);

    ClickableItem[][] all();

    Optional firstEmpty();

    Optional<ClickableItem> get(int value, int value2);

    Optional get(SlotPos slotPos);

    InventoryContents set(int value, int value2, ClickableItem clickableItem);

    InventoryContents set(SlotPos slotPos, ClickableItem clickableItem);

    InventoryContents add(ClickableItem clickableItem);

    InventoryContents fill(ClickableItem clickableItem);

    InventoryContents fillRow(int value, ClickableItem clickableItem);

    InventoryContents fillColumn(int value, ClickableItem clickableItem);

    InventoryContents fillBorders(ClickableItem clickableItem);

    InventoryContents fillBorders(
            int value, int value2, int value3, int value4, ClickableItem clickableItem);

    InventoryContents fillBorders(SlotPos slotPos, SlotPos slotPos2, ClickableItem clickableItem);

    Object property(Object result);

    Object property(String text, Object result);

    InventoryContents setProperty(Object result, Object result2);

    class Impl implements InventoryContents {

        private final SmartInventory smartInventory;

        private final Player player;

        private final ClickableItem[][] contents;

        private final Pagination pagination;

        private final Map<String, SlotIterator> entries;

        private final Map<String, Object> properties;

        public Impl(SmartInventory smartInventory2, Player player2) {
            Pagination.Impl impl = new Pagination.Impl();
            this.pagination = impl;
            HashMap entries2 = new HashMap();
            this.entries = entries2;
            HashMap entries3 = new HashMap();
            this.properties = entries3;
            this.smartInventory = smartInventory2;
            this.player = player2;
            int rows = smartInventory2.getRows();
            this.contents = new ClickableItem[rows][smartInventory2.getColumns()];
        }

        @Override
        public SmartInventory inventory() {
            return this.smartInventory;
        }

        @Override
        public Pagination pagination() {
            return this.pagination;
        }

        @Override
        public Optional<it.ytnoos.lpx.inventories.smartinvs.content.SlotIterator> iterator(
                Object result) {
            return Optional.ofNullable(this.entries.get(result));
        }

        @Override
        public SlotIterator newIterator(
                Object result, SlotIterator.Type type, int value, int value2) {
            SmartInventory smartInventory2 = this.smartInventory;
            InventoryContents.Impl impl = this;
            SlotIterator.Impl impl2 =
                    new SlotIterator.Impl(impl, smartInventory2, type, value, value2);
            this.entries.put((String) result, impl2);
            return impl2;
        }

        @Override
        public SlotIterator newIterator(String text, SlotIterator.Type type, SlotPos slotPos) {
            int row = slotPos.getRow();
            int column = slotPos.getColumn();
            Integer value = ((Integer) column);
            Integer value2 = ((Integer) row);
            int value3 = ((int) value2);
            return this.newIterator(text, type, value3, ((int) value));
        }

        @Override
        public SlotIterator newIterator(SlotIterator.Type type, int value, int value2) {
            SmartInventory smartInventory2 = this.smartInventory;
            InventoryContents.Impl impl = this;
            return new SlotIterator.Impl(impl, smartInventory2, type, value, value2);
        }

        @Override
        public SlotIterator newIterator(SlotIterator.Type type, SlotPos slotPos) {
            int row = slotPos.getRow();
            int column = slotPos.getColumn();
            Integer value = ((Integer) column);
            int value2 = row;
            Integer value3 = ((Integer) value2);
            row = value3;
            int value4 = row;
            return this.newIterator(type, value4, ((int) value));
        }

        @Override
        public ClickableItem[][] all() {
            return this.contents;
        }

        @Override
        public Optional<it.ytnoos.lpx.inventories.smartinvs.content.SlotPos> firstEmpty() {
            int value = 0;
            while (value < this.contents.length) {
                int value2 = 0;
                while (true) {
                    if (value2 >= this.contents[0].length) {
                        value++;
                        {
                            break;
                        }
                    }
                    int value3 = value2;
                    Integer value4 = ((Integer) value3);
                    int value5 = value;
                    Integer value6 = ((Integer) value5);
                    int value7 = ((int) value6);
                    Optional<ClickableItem> optional = this.get(value7, ((int) value4));
                    if (!optional.isPresent()) {
                        SlotPos slotPos = new SlotPos(value, value2);
                        return Optional.of(slotPos);
                    }
                    value2++;
                }
            }
            return Optional.empty();
        }

        @Override
        public Optional<ClickableItem> get(int value, int value2) {
            if (value >= this.contents.length) {
                return Optional.empty();
            } else {
                return value2 >= this.contents[value].length
                        ? Optional.empty()
                        : Optional.ofNullable(this.contents[value][value2]);
            }
        }

        @Override
        public Optional<ClickableItem> get(SlotPos slotPos) {
            int row = slotPos.getRow();
            int column = slotPos.getColumn();
            Integer value = ((Integer) column);
            int value2 = row;
            Integer value3 = ((Integer) value2);
            row = value3;
            int value4 = row;
            return this.get(value4, ((int) value));
        }

        @Override
        public InventoryContents set(int value, int value2, ClickableItem clickableItem) {
            if (value >= this.contents.length) {
                return this;
            }
            if (value2 >= this.contents[value].length) {
                return this;
            }
            this.contents[value][value2] = clickableItem;
            ItemStack itemStack;
            if (clickableItem != null) {
                itemStack = clickableItem.getItem();
            } else {
                itemStack = null;
            }
            ItemStack itemStack2 = itemStack;
            Integer value3 = ((Integer) value2);
            Integer value4 = ((Integer) value);
            int value5 = ((int) value3);
            int value6 = ((int) value4);
            this.update(value6, value5, itemStack2);
            return this;
        }

        @Override
        public InventoryContents set(SlotPos slotPos, ClickableItem clickableItem) {
            int row = slotPos.getRow();
            int column = slotPos.getColumn();
            Integer value = ((Integer) column);
            int value2 = row;
            Integer value3 = ((Integer) value2);
            row = value3;
            int value4 = ((int) value);
            int value5 = row;
            return this.set(value5, value4, clickableItem);
        }

        @Override
        public InventoryContents add(ClickableItem clickableItem) {
            int value = 0;
            while (value < this.contents.length) {
                for (int index = 0; index < this.contents[0].length; index++) {
                    if (this.contents[value][index] == null) {
                        int value2 = index;
                        Integer value3 = ((Integer) value2);
                        int value4 = value;
                        Integer value5 = ((Integer) value4);
                        int value6 = ((int) value3);
                        int value7 = ((int) value5);
                        this.set(value7, value6, clickableItem);
                        return this;
                    }
                }
                value++;
            }
            return this;
        }

        @Override
        public InventoryContents fill(ClickableItem clickableItem) {
            int value = 0;
            while (value < this.contents.length) {
                for (int index = 0; index < this.contents[value].length; index++) {
                    int value2 = index;
                    Integer value3 = ((Integer) value2);
                    int value4 = value;
                    Integer value5 = ((Integer) value4);
                    int value6 = ((int) value3);
                    int value7 = ((int) value5);
                    this.set(value7, value6, clickableItem);
                }
                value++;
            }
            return this;
        }

        @Override
        public InventoryContents fillRow(int value, ClickableItem clickableItem) {
            if (value >= this.contents.length) {
                return this;
            }
            for (int index = 0; index < this.contents[value].length; index++) {
                {
                    int value2 = index;
                    Integer value3 = ((Integer) value2);
                    Integer value4 = ((Integer) value);
                    int value5 = ((int) value3);
                    int value6 = ((int) value4);
                    this.set(value6, value5, clickableItem);
                }
            }
            return this;
        }

        @Override
        public InventoryContents fillColumn(int value, ClickableItem clickableItem) {
            for (int index = 0; index < this.contents.length; index++) {
                {
                    Integer value2 = ((Integer) value);
                    int value3 = index;
                    Integer value4 = ((Integer) value3);
                    int value5 = ((int) value2);
                    int value6 = ((int) value4);
                    this.set(value6, value5, clickableItem);
                }
            }
            return this;
        }

        @Override
        public InventoryContents fillBorders(ClickableItem clickableItem) {
            SmartInventory smartInventory2 = this.smartInventory;
            int value = smartInventory2.getRows() - 1;
            SmartInventory smartInventory3 = this.smartInventory;
            int value2 = smartInventory3.getColumns() - 1;
            Integer value3 = ((Integer) value2);
            Integer value4 = ((Integer) value);
            Integer value5 = Integer.valueOf((byte) 0);
            Integer value6 = Integer.valueOf((byte) 0);
            this.fillBorders(value6, value5, value4, value3, clickableItem);
            return this;
        }

        @Override
        public InventoryContents fillBorders(
                int value, int value2, int value3, int value4, ClickableItem clickableItem) {
            int value5 = value;
            while (value5 <= value3) {
                int value6 = value2;
                while (value6 <= value4) {
                    label51:
                    {
                        label66:
                        {
                            int value7 = value5;
                            {
                                if (value5 == value) {
                                    break label66;
                                }
                                value7 = value5;
                            }
                            {
                                if (value7 == value3) {
                                    break label66;
                                }
                                value7 = value6;
                            }
                            if (value7 != value2 && value6 != value4) {
                                break label51;
                            }
                        }
                        int value8 = value6;
                        Integer value9 = ((Integer) value8);
                        int value10 = value5;
                        Integer value11 = ((Integer) value10);
                        int value12 = ((int) value9);
                        int value13 = ((int) value11);
                        this.set(value13, value12, clickableItem);
                    }
                    value6++;
                }
                value5++;
            }
            return this;
        }

        @Override
        public InventoryContents fillBorders(
                SlotPos slotPos, SlotPos slotPos2, ClickableItem clickableItem) {
            int row = slotPos.getRow();
            int column = slotPos.getColumn();
            int row2 = slotPos2.getRow();
            int column2 = slotPos2.getColumn();
            Integer value = ((Integer) column2);
            Integer value2 = ((Integer) row2);
            Integer value3 = ((Integer) column);
            Integer value4 = ((Integer) row);
            return this.fillBorders(value4, value3, value2, value, clickableItem);
        }

        @Override
        public Object property(Object result) {
            return this.properties.get(result);
        }

        @Override
        public Object property(String text, Object result) {
            return this.properties.containsKey(text) ? this.properties.get(text) : result;
        }

        @Override
        public InventoryContents setProperty(Object result, Object result2) {
            this.properties.put((String) result, result2);
            return this;
        }

        private void update(int value, int value2, ItemStack itemStack) {
            SmartInventory smartInventory2 = this.smartInventory;
            InventoryManager manager = smartInventory2.getManager();
            SmartInventory smartInventory3 = this.smartInventory;
            if (manager.getOpenedPlayers(smartInventory3).contains(this.player)) {
                Inventory topInventory = this.player.getOpenInventory().getTopInventory();
                SmartInventory smartInventory4 = this.smartInventory;
                topInventory.setItem(smartInventory4.getColumns() * value + value2, itemStack);
            }
        }
    }
}
