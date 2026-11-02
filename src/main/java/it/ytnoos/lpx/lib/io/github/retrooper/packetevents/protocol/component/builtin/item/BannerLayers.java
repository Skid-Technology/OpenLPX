package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.banner.BannerPattern;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.banner.BannerPatterns;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class BannerLayers {

    private List<BannerLayers.Layer> layers;

    public BannerLayers(List<BannerLayers.Layer> layers) {
        this.layers = layers;
    }

    public static BannerLayers read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(BannerLayers.Layer::read);
        return new BannerLayers(values);
    }

    public static void write(PacketWrapper<?> packetWrapper, BannerLayers bannerLayers) {
        packetWrapper.writeList(bannerLayers.layers, BannerLayers.Layer::write);
    }

    public void addLayer(BannerLayers.Layer layer) {
        this.layers.add(layer);
    }

    public List<BannerLayers.Layer> getLayers() {
        return this.layers;
    }

    public void setLayers(List<BannerLayers.Layer> layers) {
        this.layers = layers;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof BannerLayers)) {
            return false;
        }
        BannerLayers bannerLayers = (BannerLayers) result;
        return this.layers.equals(bannerLayers.layers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.layers);
    }

    public static class Layer {

        private BannerPattern pattern;

        private DyeColor color;

        public Layer(BannerPattern bannerPattern, DyeColor dyeColor) {
            this.pattern = bannerPattern;
            this.color = dyeColor;
        }

        public static BannerLayers.Layer read(PacketWrapper<?> packetWrapper) {
            BannerPattern bannerPattern =
                    packetWrapper.readMappedEntityOrDirect(
                            BannerPatterns.getRegistry(), BannerPattern::readDirect);
            DyeColor dyeColor = DyeColor.read(packetWrapper);
            return new BannerLayers.Layer(bannerPattern, dyeColor);
        }

        public static void write(PacketWrapper<?> packetWrapper, BannerLayers.Layer layer) {
            packetWrapper.writeMappedEntityOrDirect(layer.pattern, BannerPattern::writeDirect);
            DyeColor.write(packetWrapper, layer.color);
        }

        public BannerPattern getPattern() {
            return this.pattern;
        }

        public void setPattern(BannerPattern bannerPattern) {
            this.pattern = bannerPattern;
        }

        public DyeColor getColor() {
            return this.color;
        }

        public void setColor(DyeColor dyeColor) {
            this.color = dyeColor;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof BannerLayers.Layer)) {
                return false;
            }
            BannerLayers.Layer layer = (BannerLayers.Layer) result;
            return !this.pattern.equals(layer.pattern) ? false : this.color == layer.color;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.pattern, this.color);
        }
    }
}
