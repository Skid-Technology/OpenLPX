package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.tropicalfish.TropicalFishPattern;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.tropicalfish.TropicalFishPatterns;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class TropicalFishPatternComponent {

    private TropicalFishPattern variant;

    public TropicalFishPatternComponent(TropicalFishPattern tropicalFishPattern) {
        this.variant = tropicalFishPattern;
    }

    public static TropicalFishPatternComponent read(PacketWrapper<?> packetWrapper) {
        TropicalFishPattern tropicalFishPattern =
                packetWrapper.readMappedEntity(TropicalFishPatterns.getRegistry());
        return new TropicalFishPatternComponent(tropicalFishPattern);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            TropicalFishPatternComponent tropicalFishPatternComponent) {
        packetWrapper.writeMappedEntity(tropicalFishPatternComponent.variant);
    }

    public TropicalFishPattern getVariant() {
        return this.variant;
    }

    public void setVariant(TropicalFishPattern tropicalFishPattern) {
        this.variant = tropicalFishPattern;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof TropicalFishPatternComponent)) {
            return false;
        }
        TropicalFishPatternComponent tropicalFishPatternComponent =
                (TropicalFishPatternComponent) result;
        return this.variant.equals(tropicalFishPatternComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
