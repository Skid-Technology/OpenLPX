package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Axis;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;

public enum Direction implements CodecNameable {
    DOWN("down", -1, Axis.Y, new Vector3i(0, -1, 0)),
    UP("up", -1, Axis.Y, new Vector3i(0, 1, 0)),
    NORTH("north", 0, Axis.Z, new Vector3i(0, 0, -1)),
    SOUTH("south", 1, Axis.Z, new Vector3i(0, 0, 1)),
    WEST("west", 2, Axis.X, new Vector3i(-1, 0, 0)),
    EAST("east", 3, Axis.X, new Vector3i(1, 0, 0));

    public static final NbtCodec<Direction> CODEC = NbtCodecs.forEnum(values());

    private static final Direction[] HORIZONTAL_VALUES = new Direction[] {NORTH, SOUTH, WEST, EAST};

    private static final Direction[] VALUES = values();

    private final String id;

    private final int horizontalIndex;

    private final Axis axis;

    private final Vector3i vec3i;

    Direction(String id, int horizontalIndex, Axis axis2, Vector3i vector3i) {
        this.id = id;
        this.horizontalIndex = horizontalIndex;
        this.axis = axis2;
        this.vec3i = vector3i;
    }

    @Override
    public String getCodecName() {
        return this.id;
    }

    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }

    public static Direction getByHorizontalIndex(int value) {
        return HORIZONTAL_VALUES[value % HORIZONTAL_VALUES.length];
    }

    public static Direction getByIndex(int value) {
        return VALUES[value];
    }

    public Vector3i getVector() {
        return this.vec3i;
    }

    public Axis getAxis() {
        return this.axis;
    }
}
