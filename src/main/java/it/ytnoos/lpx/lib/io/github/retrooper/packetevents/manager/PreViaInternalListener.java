package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketListenerAbstract;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketListenerPriority;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.handshaking.client.WrapperHandshakingClientHandshake;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public class PreViaInternalListener extends PacketListenerAbstract {

    public PreViaInternalListener() {
        super(PacketListenerPriority.LOWEST);
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {
        User user = packetReceiveEvent.getUser();
        if (packetReceiveEvent.getPacketType() == PacketType.Handshaking.Client.HANDSHAKE) {
            WrapperHandshakingClientHandshake wrapperHandshakingClientHandshake;
            ClientVersion clientVersion =
                    (wrapperHandshakingClientHandshake =
                                    new WrapperHandshakingClientHandshake(packetReceiveEvent))
                            .getClientVersion();
            user.setClientVersion(clientVersion);
            user.setPreViaDecoderState(wrapperHandshakingClientHandshake.getNextConnectionState());
            user.setPreViaEncoderState(wrapperHandshakingClientHandshake.getNextConnectionState());
        } else if (packetReceiveEvent.getPacketType()
                == PacketType.Login.Client.LOGIN_SUCCESS_ACK) {
            user.setPreViaDecoderState(ConnectionState.CONFIGURATION);
        } else if (packetReceiveEvent.getPacketType() == PacketType.Play.Client.CONFIGURATION_ACK) {
            user.setPreViaDecoderState(ConnectionState.CONFIGURATION);
        } else if (packetReceiveEvent.getPacketType()
                == PacketType.Configuration.Client.CONFIGURATION_END_ACK) {
            user.setPreViaDecoderState(ConnectionState.PLAY);
        }
    }

    @Override
    public void onPacketSend(PacketSendEvent packetSendEvent) {
        User user = packetSendEvent.getUser();
        if (packetSendEvent.getPacketType() == PacketType.Login.Server.LOGIN_SUCCESS) {
            if (packetSendEvent.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
                user.setPreViaEncoderState(ConnectionState.CONFIGURATION);
            } else {
                user.setPreViaEncoderState(ConnectionState.PLAY);
            }
        } else if (packetSendEvent.getPacketType() == PacketType.Play.Server.CONFIGURATION_START) {
            user.setPreViaEncoderState(ConnectionState.CONFIGURATION);
        } else if (packetSendEvent.getPacketType()
                == PacketType.Configuration.Server.CONFIGURATION_END) {
            user.setPreViaEncoderState(ConnectionState.PLAY);
        }
    }
}
