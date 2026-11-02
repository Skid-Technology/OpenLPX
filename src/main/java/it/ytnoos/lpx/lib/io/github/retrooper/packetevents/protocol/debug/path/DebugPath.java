package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.path;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Set;

@NullMarked
public final class DebugPath {

    private final boolean reached;

    private final int nextNodeIndex;

    private final Vector3i target;

    private final List<DebugNode> nodes;

    private final Set<DebugNode> targetNodes;

    private final List<DebugNode> openSet;

    private final List<DebugNode> closedSet;

    public DebugPath(
            boolean reached,
            int nextNodeIndex,
            Vector3i vector3i,
            List<DebugNode> nodes,
            Set<DebugNode> targetNodes,
            List<DebugNode> openSet,
            List<DebugNode> closedSet) {
        this.reached = reached;
        this.nextNodeIndex = nextNodeIndex;
        this.target = vector3i;
        this.nodes = nodes;
        this.targetNodes = targetNodes;
        this.openSet = openSet;
        this.closedSet = closedSet;
    }

    public static DebugPath read(PacketWrapper<?> packetWrapper) {
        boolean matches = packetWrapper.readBoolean();
        int value = packetWrapper.readInt();
        Vector3i vector3i = packetWrapper.readBlockPosition();
        List values = packetWrapper.readList(DebugNode::read);
        Set values2 = packetWrapper.readSet(DebugNode::read);
        List values3 = packetWrapper.readList(DebugNode::read);
        List values4 = packetWrapper.readList(DebugNode::read);
        return new DebugPath(matches, value, vector3i, values, values2, values3, values4);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugPath debugPath) {
        packetWrapper.writeBoolean(debugPath.reached);
        packetWrapper.writeInt(debugPath.nextNodeIndex);
        packetWrapper.writeBlockPosition(debugPath.target);
        packetWrapper.writeList(debugPath.nodes, DebugNode::write);
        packetWrapper.writeSet(debugPath.targetNodes, DebugNode::write);
        packetWrapper.writeList(debugPath.openSet, DebugNode::write);
        packetWrapper.writeList(debugPath.closedSet, DebugNode::write);
    }

    public boolean isReached() {
        return this.reached;
    }

    public int getNextNodeIndex() {
        return this.nextNodeIndex;
    }

    public Vector3i getTarget() {
        return this.target;
    }

    public List<DebugNode> getNodes() {
        return this.nodes;
    }

    public Set<DebugNode> getTargetNodes() {
        return this.targetNodes;
    }

    public List<DebugNode> getOpenSet() {
        return this.openSet;
    }

    public List<DebugNode> getClosedSet() {
        return this.closedSet;
    }
}
