package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.IntFunction;

public class PotDecorations {

    @Nullable private ItemStack back;

    @Nullable private ItemStack left;

    @Nullable private ItemStack right;

    @Nullable private ItemStack front;

    @Obsolete
    private PotDecorations(Queue<Optional<ItemType>> queue) {
        this(
                queue.isEmpty() ? null : (ItemType) ((Optional) queue.remove()).orElse(null),
                queue.isEmpty() ? null : (ItemType) ((Optional) queue.remove()).orElse(null),
                queue.isEmpty() ? null : (ItemType) ((Optional) queue.remove()).orElse(null),
                queue.isEmpty() ? null : (ItemType) ((Optional) queue.remove()).orElse(null));
    }

    @Obsolete
    public PotDecorations(
            @Nullable ItemType itemType,
            @Nullable ItemType itemType2,
            @Nullable ItemType itemType3,
            @Nullable ItemType itemType4) {
        this(
                itemType != null ? ItemStack.builder().type(itemType).build() : null,
                itemType2 != null ? ItemStack.builder().type(itemType2).build() : null,
                itemType3 != null ? ItemStack.builder().type(itemType3).build() : null,
                itemType4 != null ? ItemStack.builder().type(itemType4).build() : null);
    }

    public PotDecorations(
            @Nullable ItemStack itemStack,
            @Nullable ItemStack itemStack2,
            @Nullable ItemStack itemStack3,
            @Nullable ItemStack itemStack4) {
        this.back = itemStack;
        this.left = itemStack2;
        this.right = itemStack3;
        this.front = itemStack4;
    }

    @Obsolete
    private List<Optional<ItemType>> asList() {
        return Arrays.asList(
                Optional.ofNullable(this.back).map(ItemStack::getType),
                Optional.ofNullable(this.left).map(ItemStack::getType),
                Optional.ofNullable(this.right).map(ItemStack::getType),
                Optional.ofNullable(this.front).map(ItemStack::getType));
    }

    public static PotDecorations read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
            return new PotDecorations(
                    packetWrapper.readOptional(ItemStackSerialization::readTemplate),
                    packetWrapper.readOptional(ItemStackSerialization::readTemplate),
                    packetWrapper.readOptional(ItemStackSerialization::readTemplate),
                    packetWrapper.readOptional(ItemStackSerialization::readTemplate));
        }
        IntFunction intFunction = ArrayDeque::new;
        Queue queue =
                ((Queue)
                        packetWrapper.readCollection(
                                intFunction,
                                result -> {
                                    ItemType itemType;
                                    return (itemType =
                                                            ((ItemType)
                                                                    ((PacketWrapper) packetWrapper)
                                                                            .readMappedEntity(
                                                                                    ItemTypes
                                                                                            .getRegistry())))
                                                    == ItemTypes.BRICK
                                            ? Optional.empty()
                                            : Optional.of(itemType);
                                }));
        return new PotDecorations(queue);
    }

    public static void write(PacketWrapper<?> packetWrapper, PotDecorations potDecorations) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
            packetWrapper.writeOptional(potDecorations.back, ItemStackSerialization::writeTemplate);
            packetWrapper.writeOptional(potDecorations.left, ItemStackSerialization::writeTemplate);
            packetWrapper.writeOptional(
                    potDecorations.right, ItemStackSerialization::writeTemplate);
            packetWrapper.writeOptional(
                    potDecorations.front, ItemStackSerialization::writeTemplate);
        } else {
            packetWrapper.writeList(
                    potDecorations.asList(),
                    (result, result2) -> result.writeMappedEntity(result2.orElse(ItemTypes.BRICK)));
        }
    }

    @Nullable
    public ItemStack getBackStack() {
        return this.back;
    }

    public void setBackStack(@Nullable ItemStack itemStack) {
        this.back = itemStack;
    }

    @Nullable
    public ItemStack getLeftStack() {
        return this.left;
    }

    public void setLeftStack(@Nullable ItemStack itemStack) {
        this.left = itemStack;
    }

    @Nullable
    public ItemStack getRightStack() {
        return this.right;
    }

    public void setRightStack(@Nullable ItemStack itemStack) {
        this.right = itemStack;
    }

    @Nullable
    public ItemStack getFrontStack() {
        return this.front;
    }

    public void setFrontStack(@Nullable ItemStack itemStack) {
        this.front = itemStack;
    }

    @Obsolete
    @Nullable
    public ItemType getBack() {
        return this.back != null ? this.back.getType() : null;
    }

    @Obsolete
    public void setBack(@Nullable ItemType itemType) {
        this.back = itemType != null ? ItemStack.builder().type(itemType).build() : null;
    }

    @Obsolete
    @Nullable
    public ItemType getLeft() {
        return this.left != null ? this.left.getType() : null;
    }

    @Obsolete
    public void setLeft(@Nullable ItemType itemType) {
        this.left = itemType != null ? ItemStack.builder().type(itemType).build() : null;
    }

    @Obsolete
    @Nullable
    public ItemType getRight() {
        return this.right != null ? this.right.getType() : null;
    }

    @Obsolete
    public void setRight(@Nullable ItemType itemType) {
        this.right = itemType != null ? ItemStack.builder().type(itemType).build() : null;
    }

    @Obsolete
    @Nullable
    public ItemType getFront() {
        return this.front != null ? this.front.getType() : null;
    }

    @Obsolete
    public void setFront(@Nullable ItemType itemType) {
        this.front = itemType != null ? ItemStack.builder().type(itemType).build() : null;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof PotDecorations)) {
            return false;
        } else {
            PotDecorations potDecorations = (PotDecorations) result;
            if (!Objects.equals(this.back, potDecorations.back)) {
                return false;
            } else if (!Objects.equals(this.left, potDecorations.left)) {
                return false;
            } else {
                return !Objects.equals(this.right, potDecorations.right)
                        ? false
                        : Objects.equals(this.front, potDecorations.front);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.back, this.left, this.right, this.front);
    }
}
