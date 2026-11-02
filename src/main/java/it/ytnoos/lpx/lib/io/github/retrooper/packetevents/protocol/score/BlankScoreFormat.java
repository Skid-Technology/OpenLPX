package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.score;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public final class BlankScoreFormat implements ScoreFormat {

    public static final BlankScoreFormat INSTANCE = new BlankScoreFormat();

    private BlankScoreFormat() {}

    public static BlankScoreFormat read(PacketWrapper<?> packetWrapper) {
        return INSTANCE;
    }

    public static void write(PacketWrapper<?> packetWrapper, BlankScoreFormat blankScoreFormat) {}

    @Override
    public Component format(int value) {
        return Component.empty();
    }

    @Override
    public ScoreFormatType<BlankScoreFormat> getType() {
        return ScoreFormatTypes.BLANK;
    }
}
