package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperLoginClientLoginSuccessAck
        extends PacketWrapper<WrapperLoginClientLoginSuccessAck> {

    public WrapperLoginClientLoginSuccessAck(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperLoginClientLoginSuccessAck() {
        super(PacketType.Login.Client.LOGIN_SUCCESS_ACK);
    }
}
