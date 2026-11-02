package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientUpdateCommandBlock
        extends PacketWrapper<WrapperPlayClientUpdateCommandBlock> {

    private Vector3i position;

    private String command;

    private WrapperPlayClientUpdateCommandBlock.CommandBlockMode mode;

    private boolean doesTrackOutput;

    private boolean conditional;

    private boolean automatic;

    private short flags;

    public WrapperPlayClientUpdateCommandBlock(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientUpdateCommandBlock(
            Vector3i vector3i,
            String command,
            WrapperPlayClientUpdateCommandBlock.CommandBlockMode commandBlockMode,
            boolean doesTrackOutput,
            boolean conditional,
            boolean automatic) {
        super(PacketType.Play.Client.UPDATE_COMMAND_BLOCK);
        this.position = vector3i;
        this.command = command;
        this.mode = commandBlockMode;
        this.doesTrackOutput = doesTrackOutput;
        this.conditional = conditional;
        this.automatic = automatic;
    }

    @Override
    public void read() {
        this.position = new Vector3i(this.readLong(), this.serverVersion);
        this.command = this.readString();
        this.mode = WrapperPlayClientUpdateCommandBlock.CommandBlockMode.getById(this.readVarInt());
        this.flags = this.readUnsignedByte();
        this.doesTrackOutput = (this.flags & 1) != 0;
        this.conditional = (this.flags & 2) != 0;
        this.automatic = (this.flags & 4) != 0;
    }

    @Override
    public void write() {
        this.writeLong(this.position.getSerializedPosition(this.serverVersion));
        this.writeString(this.command);
        this.writeVarInt(this.mode.ordinal());
        if (this.doesTrackOutput) {
            this.flags = (short) (this.flags | 1);
        }
        if (this.conditional) {
            this.flags = (short) (this.flags | 2);
        }
        if (this.automatic) {
            this.flags = (short) (this.flags | 4);
        }
        this.writeByte(this.flags);
    }

    public void copy(WrapperPlayClientUpdateCommandBlock wrapperPlayClientUpdateCommandBlock) {
        this.position = wrapperPlayClientUpdateCommandBlock.position;
        this.command = wrapperPlayClientUpdateCommandBlock.command;
        this.mode = wrapperPlayClientUpdateCommandBlock.mode;
        this.doesTrackOutput = wrapperPlayClientUpdateCommandBlock.doesTrackOutput;
        this.conditional = wrapperPlayClientUpdateCommandBlock.conditional;
        this.automatic = wrapperPlayClientUpdateCommandBlock.automatic;
        this.flags = wrapperPlayClientUpdateCommandBlock.flags;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public WrapperPlayClientUpdateCommandBlock.CommandBlockMode getMode() {
        return this.mode;
    }

    public void setMode(WrapperPlayClientUpdateCommandBlock.CommandBlockMode commandBlockMode) {
        this.mode = commandBlockMode;
    }

    public boolean isDoesTrackOutput() {
        return this.doesTrackOutput;
    }

    public void setDoesTrackOutput(boolean doesTrackOutput) {
        this.doesTrackOutput = doesTrackOutput;
    }

    public boolean isConditional() {
        return this.conditional;
    }

    public void setConditional(boolean conditional) {
        this.conditional = conditional;
    }

    public boolean isAutomatic() {
        return this.automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public short getFlags() {
        return this.flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public enum CommandBlockMode {
        SEQUENCE,
        AUTO,
        REDSTONE;

        private static final WrapperPlayClientUpdateCommandBlock.CommandBlockMode[] VALUES =
                values();

        public static WrapperPlayClientUpdateCommandBlock.CommandBlockMode getById(int value) {
            return VALUES[value];
        }
    }
}
