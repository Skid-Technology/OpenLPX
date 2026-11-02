package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufAllocationOperator;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufOperator;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelOperator;

public interface NettyManager {

    ChannelOperator getChannelOperator();

    ByteBufOperator getByteBufOperator();

    ByteBufAllocationOperator getByteBufAllocationOperator();
}
