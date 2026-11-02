package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.netty;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.netty.buffer.ByteBufAllocationOperatorModernImpl;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.netty.buffer.ByteBufOperatorModernImpl;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.netty.channel.ChannelOperatorModernImpl;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.NettyManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufAllocationOperator;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufOperator;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelOperator;

public class NettyManagerImpl implements NettyManager {

    private static final ByteBufOperator BYTE_BUF_OPERATOR = new ByteBufOperatorModernImpl();

    private static final ByteBufAllocationOperator BYTE_BUF_ALLOCATION_OPERATOR =
            new ByteBufAllocationOperatorModernImpl();

    private static final ChannelOperator CHANNEL_OPERATOR = new ChannelOperatorModernImpl();

    @Override
    public ChannelOperator getChannelOperator() {
        return CHANNEL_OPERATOR;
    }

    @Override
    public ByteBufOperator getByteBufOperator() {
        return BYTE_BUF_OPERATOR;
    }

    @Override
    public ByteBufAllocationOperator getByteBufAllocationOperator() {
        return BYTE_BUF_ALLOCATION_OPERATOR;
    }
}
