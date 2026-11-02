package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class SulfurCubeContentComponent {

    private ItemStack stack;

    public SulfurCubeContentComponent(ItemStack itemStack) {
        this.stack = itemStack;
    }

    public static SulfurCubeContentComponent read(PacketWrapper<?> packetWrapper) {
        ItemStack itemStack = ItemStackSerialization.readTemplate(packetWrapper);
        return new SulfurCubeContentComponent(itemStack);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SulfurCubeContentComponent sulfurCubeContentComponent) {
        ItemStackSerialization.writeTemplate(packetWrapper, sulfurCubeContentComponent.stack);
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void setStack(ItemStack itemStack) {
        this.stack = itemStack;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            SulfurCubeContentComponent sulfurCubeContentComponent =
                    (SulfurCubeContentComponent) result;
            return this.stack.equals(sulfurCubeContentComponent.stack);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.stack);
    }
}
