package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.score;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public final class FixedScoreFormat implements ScoreFormat {

    private final Component value;

    public FixedScoreFormat(Component component) {
        this.value = component;
    }

    public static FixedScoreFormat read(PacketWrapper<?> packetWrapper) {
        return new FixedScoreFormat(packetWrapper.readComponent());
    }

    public static void write(PacketWrapper<?> packetWrapper, FixedScoreFormat fixedScoreFormat) {
        packetWrapper.writeComponent(fixedScoreFormat.value);
    }

    @Override
    public Component format(int value2) {
        return this.value;
    }

    @Override
    public ScoreFormatType<FixedScoreFormat> getType() {
        return ScoreFormatTypes.FIXED;
    }

    public Component getValue() {
        return this.value;
    }
}
