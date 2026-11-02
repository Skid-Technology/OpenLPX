package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.banner.BannerPattern;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.banner.BannerPatterns;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ItemProvidesBannerPatterns {

    private MappedEntitySet<BannerPattern> set;

    public ItemProvidesBannerPatterns(MappedEntitySet<BannerPattern> mappedEntitySet) {
        this.set = mappedEntitySet;
    }

    public ItemProvidesBannerPatterns(ResourceLocation resourceLocation) {
        this(new MappedEntitySet<>(resourceLocation));
    }

    public static ItemProvidesBannerPatterns read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            MappedEntitySet<BannerPattern> mappedEntitySet =
                    MappedEntitySet.read(packetWrapper, BannerPatterns.getRegistry());
            return new ItemProvidesBannerPatterns(mappedEntitySet);
        } else {
            ResourceLocation resourceLocation = packetWrapper.readIdentifier();
            return new ItemProvidesBannerPatterns(resourceLocation);
        }
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemProvidesBannerPatterns itemProvidesBannerPatterns) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            MappedEntitySet.write(packetWrapper, itemProvidesBannerPatterns.set);
        } else {
            packetWrapper.writeIdentifier(itemProvidesBannerPatterns.getTagKey());
        }
    }

    public MappedEntitySet<BannerPattern> getSet() {
        return this.set;
    }

    public void setSet(MappedEntitySet<BannerPattern> mappedEntitySet) {
        this.set = mappedEntitySet;
    }

    public ResourceLocation getTagKey() {
        ResourceLocation resourceLocation;
        if ((resourceLocation = this.set.getTagKey()) == null) {
            throw new IllegalStateException("No tag key present");
        } else {
            return resourceLocation;
        }
    }

    public void setTagKey(ResourceLocation resourceLocation) {
        this.set = new MappedEntitySet<>(resourceLocation);
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ItemProvidesBannerPatterns)) {
            return false;
        }
        ItemProvidesBannerPatterns itemProvidesBannerPatterns = (ItemProvidesBannerPatterns) result;
        return this.set.equals(itemProvidesBannerPatterns.set);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.set);
    }
}
