package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayClientTabComplete extends PacketWrapper<WrapperPlayClientTabComplete> {

    private Optional<Integer> transactionId;

    private boolean assumeCommand;

    private String text;

    @Nullable private Vector3i blockPosition;

    public WrapperPlayClientTabComplete(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientTabComplete(int value, String text, @Nullable Vector3i vector3i) {
        super(PacketType.Play.Client.TAB_COMPLETE);
        this.transactionId = Optional.of(value);
        this.assumeCommand = true;
        this.text = text;
        this.blockPosition = vector3i;
    }

    @Deprecated
    public WrapperPlayClientTabComplete(
            String text, boolean assumeCommand, @Nullable Vector3i vector3i) {
        super(PacketType.Play.Client.TAB_COMPLETE);
        this.transactionId = Optional.empty();
        this.text = text;
        this.assumeCommand = assumeCommand;
        this.blockPosition = vector3i;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            short value;
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13_1)) {
                value = 32500;
            } else {
                value = 256;
            }
            this.transactionId = Optional.of(this.readVarInt());
            this.text = this.readString(value);
        } else {
            this.transactionId = Optional.empty();
            this.text = this.readString((short) 32767);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                this.assumeCommand = this.readBoolean();
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
                this.blockPosition = this.readOptional(PacketWrapper::readBlockPosition);
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            short value;
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13_1)) {
                value = 32500;
            } else {
                value = 256;
            }
            this.writeVarInt(this.transactionId.orElse(0));
            this.writeString(this.text, value);
        } else {
            this.writeString(this.text, (short) 32767);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                this.writeBoolean(this.assumeCommand);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
                this.writeOptional(this.blockPosition, PacketWrapper::writeBlockPosition);
            }
        }
    }

    public void copy(WrapperPlayClientTabComplete wrapperPlayClientTabComplete) {
        this.text = wrapperPlayClientTabComplete.text;
        this.assumeCommand = wrapperPlayClientTabComplete.assumeCommand;
        this.transactionId = wrapperPlayClientTabComplete.transactionId;
        this.blockPosition = wrapperPlayClientTabComplete.blockPosition;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Optional<Integer> getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(@Nullable Integer value) {
        this.transactionId = Optional.ofNullable(value);
    }

    @Deprecated
    public boolean isAssumeCommand() {
        return this.assumeCommand;
    }

    @Deprecated
    public void setAssumeCommand(boolean assumeCommand) {
        this.assumeCommand = assumeCommand;
    }

    public Optional<Vector3i> getBlockPosition() {
        return Optional.ofNullable(this.blockPosition);
    }

    public void setBlockPosition(@Nullable Vector3i vector3i) {
        this.blockPosition = vector3i;
    }
}
