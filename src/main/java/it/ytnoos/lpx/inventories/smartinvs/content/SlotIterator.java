package it.ytnoos.lpx.inventories.smartinvs.content;

import com.google.common.collect.Sets;

import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;

import java.util.Optional;
import java.util.Set;

public interface SlotIterator {

    Optional<ClickableItem> get();

    SlotIterator set(ClickableItem clickableItem);

    SlotIterator previous();

    SlotIterator next();

    SlotIterator blacklist(int value, int value2);

    SlotIterator createSlotIterator(SlotPos slotPos);

    int row();

    SlotIterator createSlotIterator(int value);

    int calculateLimit();

    SlotIterator column(int value);

    boolean testState();

    boolean testState2();

    boolean testState3();

    SlotIterator createSlotIterator(boolean matches);

    class Impl implements SlotIterator {

        private final InventoryContents contents;

        private final SmartInventory smartInventory;

        private final SlotIterator.Type type;

        private final Set<SlotPos> blacklisted = Sets.newHashSet();

        private boolean enabled = false;

        private boolean enabled2 = true;

        private int row;

        private int column;

        public Impl(
                InventoryContents inventoryContents,
                SmartInventory smartInventory2,
                SlotIterator.Type type2,
                int row,
                int column) {
            this.contents = inventoryContents;
            this.smartInventory = smartInventory2;
            this.type = type2;
            this.row = row;
            this.column = column;
        }

        public Impl(
                InventoryContents inventoryContents,
                SmartInventory smartInventory,
                SlotIterator.Type type) {
            this((inventoryContents), (smartInventory), (type), (0), (0));
        }

        @Override
        public Optional<ClickableItem> get() {
            InventoryContents inventoryContents = this.contents;
            int value = this.row;
            int value2 = this.column;
            Integer value3 = ((Integer) value2);
            int value4 = value;
            Integer value5 = ((Integer) value4);
            value = value5;
            int value6 = value;
            return inventoryContents.get(value6, ((int) value3));
        }

        @Override
        public SlotIterator set(ClickableItem clickableItem) {
            if (this.testState4()) {
                InventoryContents inventoryContents = this.contents;
                int value = this.row;
                int value2 = this.column;
                Integer value3 = ((Integer) value2);
                int value4 = value;
                Integer value5 = ((Integer) value4);
                value = value5;
                int value6 = ((int) value3);
                int value7 = value;
                inventoryContents.set(value7, value6, clickableItem);
            }
            return this;
        }

        @Override
        public SlotIterator previous() {
            if (row == 0 && column == 0) {
                enabled = true;
                return this;
            }
            do {
                if (!enabled) enabled = true;
                else
                    switch (type) {
                        case HORIZONTAL:
                            if (--column < 0) {
                                column = smartInventory.getColumns() - 1;
                                row--;
                            }
                            break;
                        case VERTICAL:
                            if (--row < 0) {
                                row = smartInventory.getRows() - 1;
                                column--;
                            }
                            break;
                    }
            } while (!testState4() && (row != 0 || column != 0));
            return this;
        }

        @Override
        public SlotIterator next() {
            if (this.testState2()) {
                this.enabled = true;
                return this;
            }
            do {
                if (!this.enabled) {
                    this.enabled = true;
                } else {
                    switch (this.type) {
                        case HORIZONTAL:
                            SlotIterator.Impl impl = this;
                            int value = impl.column + 1;
                            impl.column = value;
                            SmartInventory smartInventory2 = this.smartInventory;
                            this.column = value % smartInventory2.getColumns();
                            if (this.column == 0) {
                                SlotIterator.Impl impl2 = this;
                                impl2.row++;
                            }
                            break;
                        case VERTICAL:
                            SlotIterator.Impl impl3 = this;
                            int value2 = impl3.row + 1;
                            impl3.row = value2;
                            SmartInventory smartInventory3 = this.smartInventory;
                            this.row = value2 % smartInventory3.getRows();
                            if (this.row == 0) {
                                SlotIterator.Impl impl4 = this;
                                impl4.column++;
                            }
                    }
                }
            } while (!this.testState4() && !this.testState2());
            return this;
        }

        @Override
        public SlotIterator blacklist(int value, int value2) {
            Set<it.ytnoos.lpx.inventories.smartinvs.content.SlotPos> values = this.blacklisted;
            Integer value3 = ((Integer) value2);
            Integer value4 = ((Integer) value);
            values.add(SlotPos.of(value4, value3));
            return this;
        }

        @Override
        public SlotIterator createSlotIterator(SlotPos slotPos) {
            int row = slotPos.getRow();
            int column = slotPos.getColumn();
            Integer value = ((Integer) column);
            Integer value2 = ((Integer) row);
            return this.blacklist(value2, value);
        }

        @Override
        public int row() {
            return this.row;
        }

        @Override
        public SlotIterator createSlotIterator(int row) {
            this.row = row;
            return this;
        }

        @Override
        public int calculateLimit() {
            return this.column;
        }

        @Override
        public SlotIterator column(int column) {
            this.column = column;
            return this;
        }

        @Override
        public boolean testState() {
            return this.enabled;
        }

        @Override
        public boolean testState2() {
            int value = this.row;
            SmartInventory smartInventory2 = this.smartInventory;
            if (value == smartInventory2.getRows() - 1) {
                value = this.column;
                smartInventory2 = this.smartInventory;
                if (value == smartInventory2.getColumns() - 1) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean testState3() {
            return this.enabled2;
        }

        @Override
        public SlotIterator createSlotIterator(boolean enabled2) {
            this.enabled2 = enabled2;
            return this;
        }

        private boolean testState4() {
            Set<it.ytnoos.lpx.inventories.smartinvs.content.SlotPos> values = this.blacklisted;
            int value = this.row;
            int value2 = this.column;
            Integer value3 = ((Integer) value2);
            Integer value4 = ((Integer) value);
            return !values.contains(SlotPos.of(value4, value3))
                    && (this.enabled2 || !this.get().isPresent());
        }
    }

    enum Type {
        HORIZONTAL,
        VERTICAL;

        private static final SlotIterator.Type[] TYPES = createTypes();

        private static SlotIterator.Type[] createTypes() {
            SlotIterator.Type[] types = new SlotIterator.Type[] {HORIZONTAL, null};
            types[1] = VERTICAL;
            return types;
        }
    }
}
