package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.waypoint;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class WaypointIcon {

    public static final ResourceLocation ICON_STYLE_DEFAULT = new ResourceLocation("default");

    public static final ResourceLocation ICON_STYLE_BOWTIE = new ResourceLocation("bowtie");

    private final ResourceLocation style;

    @Nullable private final Color color;

    public WaypointIcon(ResourceLocation resourceLocation, @Nullable Color color2) {
        this.style = resourceLocation;
        this.color = color2;
    }

    public static WaypointIcon read(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation = packetWrapper.readIdentifier();
        Color color = packetWrapper.readOptional(Color::readShort);
        return new WaypointIcon(resourceLocation, color);
    }

    public static void write(PacketWrapper<?> packetWrapper, WaypointIcon waypointIcon) {
        packetWrapper.writeIdentifier(waypointIcon.style);
        packetWrapper.writeOptional(waypointIcon.color, Color::writeShort);
    }

    public ResourceLocation getStyle() {
        return this.style;
    }

    @Nullable
    public Color getColor() {
        return this.color;
    }
}
