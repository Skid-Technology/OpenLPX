package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.blockentity.decopot.DecoratedPotPattern;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class PotteryPatternComponent {

    private DecoratedPotPattern pattern;

    public PotteryPatternComponent(DecoratedPotPattern decoratedPotPattern) {
        this.pattern = decoratedPotPattern;
    }

    public static PotteryPatternComponent read(PacketWrapper<?> packetWrapper) {
        DecoratedPotPattern decoratedPotPattern = DecoratedPotPattern.read(packetWrapper);
        return new PotteryPatternComponent(decoratedPotPattern);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, PotteryPatternComponent potteryPatternComponent) {
        DecoratedPotPattern.write(packetWrapper, potteryPatternComponent.pattern);
    }

    public DecoratedPotPattern getPattern() {
        return this.pattern;
    }

    public void setPattern(DecoratedPotPattern decoratedPotPattern) {
        this.pattern = decoratedPotPattern;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof PotteryPatternComponent)) {
            return false;
        }
        PotteryPatternComponent potteryPatternComponent = (PotteryPatternComponent) result;
        return this.pattern.equals(potteryPatternComponent.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.pattern);
    }
}
