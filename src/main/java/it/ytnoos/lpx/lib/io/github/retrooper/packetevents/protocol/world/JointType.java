package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum JointType {
    ROLLABLE("rollable"),
    ALIGNED("aligned");

    private static final JointType[] VALUES = values();

    private final String name;

    JointType(String name) {
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }

    public Component getTranslatableName() {
        return Component.translatable("jigsaw_block.joint." + this.name);
    }

    public static Optional<JointType> byName(String text) {
        Stream<it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.JointType>
                stream2 = Arrays.stream(VALUES);
        return stream2.filter(result -> result.getSerializedName().equals(text)).findFirst();
    }
}
