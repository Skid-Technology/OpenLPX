package it.ytnoos.lpx.inventories.smartinvs;

import java.util.function.Consumer;

public class InventoryListener<T> {

    private final Class<T> type;

    private final Consumer<T> consumer;

    public InventoryListener(Class<T> classValue, Consumer<T> consumer2) {
        this.type = classValue;
        this.consumer = consumer2;
    }

    public void accept(Object result) {
        this.consumer.accept((T) result);
    }

    public Class getType() {
        return this.type;
    }
}
