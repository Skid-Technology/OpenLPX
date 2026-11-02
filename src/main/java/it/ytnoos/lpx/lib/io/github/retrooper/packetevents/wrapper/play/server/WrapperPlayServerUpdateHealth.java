package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerUpdateHealth extends PacketWrapper<WrapperPlayServerUpdateHealth> {

    private float health;

    private int food;

    private float foodSaturation;

    public WrapperPlayServerUpdateHealth(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUpdateHealth(float health, int food, float foodSaturation) {
        super(PacketType.Play.Server.UPDATE_HEALTH);
        this.health = health;
        this.food = food;
        this.foodSaturation = foodSaturation;
    }

    @Override
    public void read() {
        this.health = this.readFloat();
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.food = this.readShort();
        } else {
            this.food = this.readVarInt();
        }
        this.foodSaturation = this.readFloat();
    }

    @Override
    public void write() {
        this.writeFloat(this.health);
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeShort(this.food);
        } else {
            this.writeVarInt(this.food);
        }
        this.writeFloat(this.foodSaturation);
    }

    public void copy(WrapperPlayServerUpdateHealth wrapperPlayServerUpdateHealth) {
        this.health = wrapperPlayServerUpdateHealth.health;
        this.food = wrapperPlayServerUpdateHealth.food;
        this.foodSaturation = wrapperPlayServerUpdateHealth.foodSaturation;
    }

    public float getHealth() {
        return this.health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public int getFood() {
        return this.food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public float getFoodSaturation() {
        return this.foodSaturation;
    }

    public void setFoodSaturation(float foodSaturation) {
        this.foodSaturation = foodSaturation;
    }
}
