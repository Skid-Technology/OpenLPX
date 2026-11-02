package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class WrapperPlayServerPlayerChatHeader
        extends PacketWrapper<WrapperPlayServerPlayerChatHeader> {

    private byte @Nullable [] previousSignature;

    private UUID playerUUID;

    private byte[] signature;

    private byte[] hash;

    public WrapperPlayServerPlayerChatHeader(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerPlayerChatHeader(
            byte @Nullable [] previousSignature, UUID playerUUID, byte[] signature, byte[] hash) {
        super(PacketType.Play.Server.PLAYER_CHAT_HEADER);
        this.previousSignature = previousSignature;
        this.playerUUID = playerUUID;
        this.signature = signature;
        this.hash = hash;
    }

    @Override
    public void read() {
        this.previousSignature = this.readOptional(PacketWrapper::readByteArray);
        this.playerUUID = this.readUUID();
        this.signature = this.readByteArray();
        this.hash = this.readByteArray();
    }

    @Override
    public void write() {
        this.writeOptional(this.previousSignature, PacketWrapper::writeByteArray);
        this.writeUUID(this.playerUUID);
        this.writeByteArray(this.signature);
        this.writeByteArray(this.hash);
    }

    public void copy(WrapperPlayServerPlayerChatHeader wrapperPlayServerPlayerChatHeader) {
        this.previousSignature = wrapperPlayServerPlayerChatHeader.previousSignature;
        this.playerUUID = wrapperPlayServerPlayerChatHeader.playerUUID;
        this.signature = wrapperPlayServerPlayerChatHeader.signature;
        this.hash = wrapperPlayServerPlayerChatHeader.hash;
    }

    public Optional<byte[]> getPreviousSignature() {
        return Optional.ofNullable(this.previousSignature);
    }

    public void setPreviousSignature(byte @Nullable [] previousSignature) {
        this.previousSignature = previousSignature;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getHash() {
        return this.hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }
}
