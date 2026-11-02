package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public abstract class WrapperCommonServerPluginMessage<
                T extends WrapperCommonServerPluginMessage<T>>
        extends PacketWrapper<T> {

    protected String channelName;

    protected byte[] data;

    public WrapperCommonServerPluginMessage(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    protected WrapperCommonServerPluginMessage(PacketTypeCommon packetTypeCommon) {
        super(packetTypeCommon);
    }

    protected WrapperCommonServerPluginMessage(
            PacketTypeCommon packetTypeCommon, ResourceLocation resourceLocation, byte[] values) {
        this(packetTypeCommon, resourceLocation.toString(), values);
    }

    public WrapperCommonServerPluginMessage(
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
