package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public abstract class WrapperCommonServerClearDialog<T extends WrapperCommonServerClearDialog<T>>
        extends PacketWrapper<T> {

    public WrapperCommonServerClearDialog(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperCommonServerClearDialog(PacketTypeCommon packetTypeCommon) {
        super(packetTypeCommon);
    }
}
