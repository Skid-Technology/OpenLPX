package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.LightData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerUpdateLight extends PacketWrapper<WrapperPlayServerUpdateLight> {

    private int x;

    private int z;

    private LightData lightData;

    public WrapperPlayServerUpdateLight(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUpdateLight(int x, int z, LightData lightData2) {
        super(PacketType.Play.Server.UPDATE_LIGHT);
        this.x = x;
        this.z = z;
        this.lightData = lightData2;
    }

    @Override
    public void read() {
        this.x = this.readVarInt();
        this.z = this.readVarInt();
        this.lightData = LightData.read(this);
    }

    @Override
    public void write() {
        this.writeVarInt(this.x);
        this.writeVarInt(this.z);
        LightData.write(this, this.lightData);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public LightData getLightData() {
        return this.lightData;
    }

    public void setLightData(LightData lightData2) {
        this.lightData = lightData2;
    }

    public void copy(WrapperPlayServerUpdateLight wrapperPlayServerUpdateLight) {
        this.x = wrapperPlayServerUpdateLight.x;
        this.z = wrapperPlayServerUpdateLight.z;
        this.lightData = wrapperPlayServerUpdateLight.lightData.clone();
    }
}
