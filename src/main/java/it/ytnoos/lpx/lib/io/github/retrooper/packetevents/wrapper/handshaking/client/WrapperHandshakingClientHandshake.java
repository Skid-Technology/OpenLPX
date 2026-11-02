package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.handshaking.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.InvalidHandshakeException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperHandshakingClientHandshake
        extends PacketWrapper<WrapperHandshakingClientHandshake> {

    private int protocolVersion;

    private ClientVersion clientVersion;

    private String serverAddress;

    private int serverPort;

    private WrapperHandshakingClientHandshake.ConnectionIntention intention;

    public WrapperHandshakingClientHandshake(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    @Deprecated
    public WrapperHandshakingClientHandshake(
            int value, String text, int value2, ConnectionState connectionState) {
        this(value, text, value2, WrapperHandshakingClientHandshake.ConnectionIntention.LOGIN);
        this.setNextConnectionState(connectionState);
    }

    public WrapperHandshakingClientHandshake(
            int protocolVersion,
            String serverAddress,
            int serverPort,
            WrapperHandshakingClientHandshake.ConnectionIntention connectionIntention) {
        super(PacketType.Handshaking.Client.HANDSHAKE);
        this.protocolVersion = protocolVersion;
        this.clientVersion = ClientVersion.getById(protocolVersion);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.intention = connectionIntention;
    }

    @Override
    public void read() {
        try {
            this.protocolVersion = this.readVarInt();
            this.clientVersion = ClientVersion.getById(this.protocolVersion);
            this.serverAddress = this.readString(32767);
            this.serverPort = this.readUnsignedShort();
            int value = this.readVarInt();
            this.intention = WrapperHandshakingClientHandshake.ConnectionIntention.fromId(value);
        } catch (Exception exception) {
            throw new InvalidHandshakeException();
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.protocolVersion);
        this.writeString(this.serverAddress, 32767);
        this.writeShort(this.serverPort);
        this.writeVarInt(this.intention.getId());
    }

    public void copy(WrapperHandshakingClientHandshake wrapperHandshakingClientHandshake) {
        this.protocolVersion = wrapperHandshakingClientHandshake.protocolVersion;
        this.clientVersion = wrapperHandshakingClientHandshake.clientVersion;
        this.serverAddress = wrapperHandshakingClientHandshake.serverAddress;
        this.serverPort = wrapperHandshakingClientHandshake.serverPort;
        this.intention = wrapperHandshakingClientHandshake.intention;
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
        this.clientVersion = ClientVersion.getById(protocolVersion);
    }

    @Override
    public ClientVersion getClientVersion() {
        return this.clientVersion;
    }

    @Override
    public void setClientVersion(ClientVersion clientVersion2) {
        this.clientVersion = clientVersion2;
        this.protocolVersion = clientVersion2.getProtocolVersion();
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public ConnectionState getNextConnectionState() {
        return this.intention.getTargetState();
    }

    @Deprecated
    public void setNextConnectionState(ConnectionState connectionState) {
        switch (connectionState) {
            case LOGIN:
                this.intention = WrapperHandshakingClientHandshake.ConnectionIntention.LOGIN;
                break;
            case STATUS:
                this.intention = WrapperHandshakingClientHandshake.ConnectionIntention.STATUS;
                break;
            default:
                throw new IllegalArgumentException(
                        "Illegal next connection state: " + connectionState);
        }
    }

    public WrapperHandshakingClientHandshake.ConnectionIntention getIntention() {
        return this.intention;
    }

    public void setIntention(
            WrapperHandshakingClientHandshake.ConnectionIntention connectionIntention) {
        this.intention = connectionIntention;
    }

    public enum ConnectionIntention {
        STATUS(1, ConnectionState.STATUS),
        LOGIN(2, ConnectionState.LOGIN),
        TRANSFER(3, ConnectionState.LOGIN);

        private final int id;

        private final ConnectionState targetState;

        ConnectionIntention(int id, ConnectionState connectionState) {
            this.id = id;
            this.targetState = connectionState;
        }

        public static WrapperHandshakingClientHandshake.ConnectionIntention fromId(int value) {
            switch (value) {
                case 1:
                    return STATUS;
                case 2:
                    return LOGIN;
                case 3:
                    return TRANSFER;
                default:
                    throw new IllegalArgumentException("Illegal connection intention: " + value);
            }
        }

        public int getId() {
            return this.id;
        }

        public ConnectionState getTargetState() {
            return this.targetState;
        }
    }
}
