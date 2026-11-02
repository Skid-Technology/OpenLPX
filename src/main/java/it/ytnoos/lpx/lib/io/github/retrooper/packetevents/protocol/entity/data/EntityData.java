package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data;

public class EntityData<T> {

    private int index;

    private EntityDataType<T> type;

    private T value;

    public EntityData(int index, EntityDataType<T> entityDataType, T t) {
        this.index = index;
        this.type = entityDataType;
        this.value = (T) t;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public EntityDataType<T> getType() {
        return this.type;
    }

    public void setType(EntityDataType<T> entityDataType) {
        this.type = entityDataType;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T t) {
        this.value = (T) t;
    }
}
