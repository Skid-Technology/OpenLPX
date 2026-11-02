package it.ytnoos.lpx.inventories.smartinvs.content;

import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;

import java.util.Arrays;

public interface Pagination {

    ClickableItem[] createClickableItems();

    int getPage();

    Pagination page(int value);

    boolean isFirst();

    boolean isLast();

    Pagination first();

    Pagination previous();

    Pagination next();

    Pagination last();

    Pagination createPagination(SlotIterator slotIterator);

    Pagination setItems(ClickableItem[] clickableItems);

    Pagination setItemsPerPage(int value);

    class Impl implements Pagination {

        private int currentPage;

        private ClickableItem[] items = new ClickableItem[0];

        private int itemsPerPage = 5;

        @Override
        public ClickableItem[] createClickableItems() {
            return Arrays.copyOfRange(
                    this.items,
                    this.currentPage * this.itemsPerPage,
                    (this.currentPage + 1) * this.itemsPerPage);
        }

        @Override
        public int getPage() {
            return this.currentPage;
        }

        @Override
        public Pagination page(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        @Override
        public boolean isFirst() {
            return this.currentPage == 0;
        }

        @Override
        public boolean isLast() {
            int value = (int) Math.ceil((double) this.items.length / this.itemsPerPage);
            return this.currentPage >= value - 1;
        }

        @Override
        public Pagination first() {
            this.currentPage = 0;
            return this;
        }

        @Override
        public Pagination previous() {
            if (!this.isFirst()) {
                Pagination.Impl impl = this;
                impl.currentPage--;
            }
            return this;
        }

        @Override
        public Pagination next() {
            if (!this.isLast()) {
                Pagination.Impl impl = this;
                impl.currentPage++;
            }
            return this;
        }

        @Override
        public Pagination last() {
            this.currentPage = this.items.length / this.itemsPerPage;
            return this;
        }

        @Override
        public Pagination createPagination(SlotIterator slotIterator) {
            ClickableItem[] clickableItems = this.createClickableItems();
            int value = clickableItems.length;
            for (int index = 0; index < value; index++) {
                ClickableItem clickableItem = clickableItems[index];
                SlotIterator slotIterator2 = slotIterator.next();
                slotIterator2.set(clickableItem);
                if (slotIterator.testState2()) {
                    break;
                }
            }
            return this;
        }

        @Override
        public Pagination setItems(ClickableItem[] clickableItems) {
            this.items = clickableItems;
            return this;
        }

        @Override
        public Pagination setItemsPerPage(int itemsPerPage) {
            this.itemsPerPage = itemsPerPage;
            return this;
        }
    }
}
