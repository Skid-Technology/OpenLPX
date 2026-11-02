package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager;

import io.netty.channel.Channel;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.SpigotChannelInjector;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.handlers.PacketEventsEncoder;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager.player.PlayerManagerImpl;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.protocolsupport.ProtocolSupportUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion.ViaVersionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.ProtocolPacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.InternalPacketListener;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.LogManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.handshaking.client.WrapperHandshakingClientHandshake;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.server.WrapperLoginServerLoginSuccess;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.server.WrapperLoginServerSetCompression;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.UUID;

@Internal
public class InternalBukkitPacketListener extends InternalPacketListener {

    @Nullable
    private PacketEventsEncoder getEncoder(ProtocolPacketEvent protocolPacketEvent) {
        return ((SpigotChannelInjector) PacketEvents.getAPI().getInjector())
                .getEncoder((Channel) protocolPacketEvent.getUser().getChannel());
    }

    @Override
    protected void handlePlayEnter(ProtocolPacketEvent protocolPacketEvent) {
        PacketEventsEncoder packetEventsEncoder;
        if ((packetEventsEncoder = this.getEncoder(protocolPacketEvent)) != null) {
            packetEventsEncoder.handledCompression = true;
        }
    }

    @Override
    public void onPacketSend(PacketSendEvent packetSendEvent) {
        super.onPacketSend(packetSendEvent);
        if (packetSendEvent.getPacketType() == PacketType.Login.Server.LOGIN_SUCCESS) {
            WrapperLoginServerLoginSuccess wrapperLoginServerLoginSuccess =
                    new WrapperLoginServerLoginSuccess(packetSendEvent);
            this.tryUpdatePlayerReference(
                    packetSendEvent,
                    packetSendEvent.getUser(),
                    wrapperLoginServerLoginSuccess.getUserProfile().getUUID());
        } else if (packetSendEvent.getPacketType() == PacketType.Play.Server.JOIN_GAME) {
            this.tryUpdatePlayerReference(
                    packetSendEvent,
                    packetSendEvent.getUser(),
                    packetSendEvent.getUser().getUUID());
        } else {
            PacketEventsEncoder packetEventsEncoder;
            if (packetSendEvent.getPacketType() == PacketType.Login.Server.SET_COMPRESSION
                    && new WrapperLoginServerSetCompression(packetSendEvent).getThreshold() > 0
                    && (packetEventsEncoder = this.getEncoder(packetSendEvent)) != null) {
                packetEventsEncoder.handleCompression = true;
            }
        }
    }

    private void tryUpdatePlayerReference(PacketSendEvent packetSendEvent, User user, UUID uUID) {
        PacketEventsAPI packetEventsAPI;
        WeakReference weakReference;
        Player player;
        if ((player =
                        (weakReference =
                                                ((PlayerManagerImpl)
                                                                (packetEventsAPI =
                                                                                PacketEvents
                                                                                        .getAPI())
                                                                        .getPlayerManager())
                                                        .joiningPlayers.remove(uUID))
                                        != null
                                ? (Player) weakReference.get()
                                : null)
                != null) {
            ((SpigotChannelInjector) packetEventsAPI.getInjector()).updatePlayer(user, player);
            if (packetEventsAPI.getLogManager().isDebug()) {
                packetEventsAPI
                        .getLogManager()
                        .debug(
                                "Updated player reference on packet handling for "
                                        + player.getUniqueId());
            }
            packetSendEvent.setPlayer(player);
        }
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {
        if (packetReceiveEvent.getPacketType() == PacketType.Handshaking.Client.HANDSHAKE) {
            User user = packetReceiveEvent.getUser();
            WrapperHandshakingClientHandshake wrapperHandshakingClientHandshake;
            ClientVersion clientVersion =
                    (wrapperHandshakingClientHandshake =
                                    new WrapperHandshakingClientHandshake(packetReceiveEvent))
                            .getClientVersion();
            ConnectionState nextConnectionState =
                    wrapperHandshakingClientHandshake.getNextConnectionState();
            String text;
            if (!this.isPreVia()) {
                if (ViaVersionUtil.isAvailable()) {
                    clientVersion = ClientVersion.getById(ViaVersionUtil.getProtocolVersion(user));
                    text = "ViaVersion";
                } else if (ProtocolSupportUtil.isAvailable()) {
                    clientVersion =
                            ClientVersion.getById(
                                    ProtocolSupportUtil.getProtocolVersion(user.getAddress()));
                    text = "ProtocolSupport";
                } else {
                    text = null;
                }
            } else {
                text = "Client Version Handshake";
            }
            LogManager logManager;
            if ((logManager = PacketEvents.getAPI().getLogManager()).isDebug()) {
                logManager.debug(
                        "Processed handshake for "
                                + packetReceiveEvent.getAddress()
                                + ": "
                                + nextConnectionState.name()
                                + " / "
                                + wrapperHandshakingClientHandshake
                                        .getClientVersion()
                                        .getReleaseName()
                                + (text != null ? " (using " + text + ")" : ""));
            }
            user.setClientVersion(clientVersion);
            user.setConnectionState(nextConnectionState);
        } else {
            super.onPacketReceive(packetReceiveEvent);
        }
    }
}
