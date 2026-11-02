package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.Node;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;

public class WrapperPlayServerDeclareCommands
        extends PacketWrapper<WrapperPlayServerDeclareCommands> {

    private List<Node> nodes;

    private int rootIndex;

    public WrapperPlayServerDeclareCommands(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDeclareCommands(List<Node> nodes, int rootIndex) {
        super(PacketType.Play.Server.DECLARE_COMMANDS);
        this.nodes = nodes;
        this.rootIndex = rootIndex;
    }

    @Override
    public void read() {
        this.nodes = this.readList(PacketWrapper::readNode);
        this.rootIndex = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeList(this.nodes, PacketWrapper::writeNode);
        this.writeVarInt(this.rootIndex);
    }

    public void copy(WrapperPlayServerDeclareCommands wrapperPlayServerDeclareCommands) {
        this.nodes = wrapperPlayServerDeclareCommands.nodes;
        this.rootIndex = wrapperPlayServerDeclareCommands.rootIndex;
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public int getRootIndex() {
        return this.rootIndex;
    }

    public void setRootIndex(int rootIndex) {
        this.rootIndex = rootIndex;
    }
}
