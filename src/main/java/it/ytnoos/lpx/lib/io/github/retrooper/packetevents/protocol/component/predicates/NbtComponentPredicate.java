package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.predicates;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Experimental;

import java.util.Objects;

@Experimental
public class NbtComponentPredicate implements IComponentPredicate {

    private final NBT tag;

    public NbtComponentPredicate(NBT nBT) {
        this.tag = nBT;
    }

    public static NbtComponentPredicate read(PacketWrapper<?> packetWrapper) {
        NBT nBT = packetWrapper.readNBTRaw();
        return new NbtComponentPredicate(nBT);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, NbtComponentPredicate nbtComponentPredicate) {
        packetWrapper.writeNBTRaw(nbtComponentPredicate.tag);
    }

    public NBT getTag() {
        return this.tag;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof NbtComponentPredicate)) {
            return false;
        }
        NbtComponentPredicate nbtComponentPredicate = (NbtComponentPredicate) result;
        return this.tag.equals(nbtComponentPredicate.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.tag);
    }
}
