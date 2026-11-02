package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ItemSwingAnimation {

    private static final ItemSwingAnimation.Type[] TYPES = ItemSwingAnimation.Type.values();

    private ItemSwingAnimation.Type type;

    private int duration;

    public ItemSwingAnimation(ItemSwingAnimation.Type type2, int duration) {
        this.type = type2;
        this.duration = duration;
    }

    public static ItemSwingAnimation read(PacketWrapper<?> packetWrapper) {
        ItemSwingAnimation.Type type = packetWrapper.readEnum(TYPES, ItemSwingAnimation.Type.NONE);
        int value = packetWrapper.readVarInt();
        return new ItemSwingAnimation(type, value);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemSwingAnimation itemSwingAnimation) {
        packetWrapper.writeEnum(itemSwingAnimation.type);
        packetWrapper.writeVarInt(itemSwingAnimation.duration);
    }

    public ItemSwingAnimation.Type getType() {
        return this.type;
    }

    public void setType(ItemSwingAnimation.Type type2) {
        this.type = type2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemSwingAnimation itemSwingAnimation = (ItemSwingAnimation) result;
            return this.duration != itemSwingAnimation.duration
                    ? false
                    : this.type == itemSwingAnimation.type;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.duration);
    }

    public enum Type {
        NONE,
        WHACK,
        STAB
    }
}
