package it.ytnoos.lpx.inventories.smartinvs;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {

    private final ItemStack item;

    private final Consumer<InventoryClickEvent> consumer;

    private ClickableItem(ItemStack itemStack, Consumer<InventoryClickEvent> consumer2) {
        this.item = itemStack;
        this.consumer = consumer2;
    }

    public static ClickableItem empty(ItemStack itemStack) {
        Consumer consumer = result -> {};
        return of(itemStack, consumer);
    }

    public static ClickableItem of(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        return new ClickableItem(itemStack, consumer);
    }

    public void run(Object result) {
        this.consumer.accept((InventoryClickEvent) result);
    }

    public ItemStack getItem() {
        return this.item;
    }
}
