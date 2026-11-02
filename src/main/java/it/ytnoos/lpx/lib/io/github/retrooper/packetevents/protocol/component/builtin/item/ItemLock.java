package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ItemLock {

    @Obsolete private String code;

    private NBTCompound predicate;

    @Obsolete
    public ItemLock(String code) {
        this.code = code;
        this.predicate = new NBTCompound();
    }

    public ItemLock(NBTCompound nBTCompound) {
        this.code = "packetevents$invalid_lock";
        this.predicate = nBTCompound;
    }

    public static ItemLock read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            return new ItemLock(packetWrapper.readNBT());
        }
        NBTString nBTString = (NBTString) packetWrapper.readNBTRaw();
        return new ItemLock(nBTString.getValue());
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemLock itemLock) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            packetWrapper.writeNBT(itemLock.predicate);
        } else {
            packetWrapper.writeNBTRaw(new NBTString(itemLock.code));
        }
    }

    @Obsolete
    public String getCode() {
        return this.code;
    }

    @Obsolete
    public void setCode(String code) {
        this.code = code;
    }

    public NBTCompound getPredicate() {
        return this.predicate;
    }

    public void setPredicate(NBTCompound nBTCompound) {
        this.predicate = nBTCompound;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemLock itemLock = (ItemLock) result;
            return !this.code.equals(itemLock.code)
                    ? false
                    : this.predicate.equals(itemLock.predicate);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code, this.predicate);
    }
}
