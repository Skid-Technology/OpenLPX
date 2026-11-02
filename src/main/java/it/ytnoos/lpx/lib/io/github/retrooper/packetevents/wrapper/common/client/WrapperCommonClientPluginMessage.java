package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public abstract class WrapperCommonClientPluginMessage<
                T extends WrapperCommonClientPluginMessage<T>>
        extends PacketWrapper<T> {

    protected String channelName;

    protected byte[] data;

    public WrapperCommonClientPluginMessage(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    protected WrapperCommonClientPluginMessage(PacketTypeCommon packetTypeCommon) {
        super(packetTypeCommon);
    }

    protected WrapperCommonClientPluginMessage(
            PacketTypeCommon packetTypeCommon, ResourceLocation resourceLocation, byte[] values) {
        this(packetTypeCommon, resourceLocation.toString(), values);
    }

    public WrapperCommonClientPluginMessage(
            PacketTypeCommon packetTypeCommon, String channelName, byte[] data) {
        super(packetTypeCommon);
        this.channelName = channelName;
        this.data = data;
    }

    public void copy(T t) {
        this.channelName = t.channelName;
        this.data = t.data;
    }

    public final String getChannelName() {
        return this.channelName;
    }

    public final void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public final byte[] getData() {
        return this.data;
    }

    public final void setData(byte[] data) {
        this.data = data;
    }
}
