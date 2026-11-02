package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class ItemFireworks {

    private int flightDuration;

    private List<FireworkExplosion> explosions;

    public ItemFireworks(int flightDuration, List<FireworkExplosion> explosions) {
        this.flightDuration = flightDuration;
        this.explosions = explosions;
    }

    public static ItemFireworks read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        List values = packetWrapper.readList(FireworkExplosion::read, 128);
        return new ItemFireworks(value, values);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemFireworks itemFireworks) {
        packetWrapper.writeVarInt(itemFireworks.flightDuration);
        packetWrapper.writeList(itemFireworks.explosions, FireworkExplosion::write);
    }

    public int getFlightDuration() {
        return this.flightDuration;
    }

    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

    public void addExplosion(FireworkExplosion fireworkExplosion) {
        this.explosions.add(fireworkExplosion);
    }

    public List<FireworkExplosion> getExplosions() {
        return this.explosions;
    }

    public void setExplosions(List<FireworkExplosion> explosions) {
        this.explosions = explosions;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemFireworks)) {
            return false;
        }
        ItemFireworks itemFireworks = (ItemFireworks) result;
        return this.flightDuration != itemFireworks.flightDuration
                ? false
                : this.explosions.equals(itemFireworks.explosions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.flightDuration, this.explosions);
    }
}
