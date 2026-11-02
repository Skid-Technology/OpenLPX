package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.mapdecoration.MapDecorationType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.mapdecoration.MapDecorationTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class ItemMapDecorations {

    private Map<String, ItemMapDecorations.Decoration> decorations;

    public ItemMapDecorations(Map<String, ItemMapDecorations.Decoration> entries) {
        this.decorations = entries;
    }

    public static ItemMapDecorations read(PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound = packetWrapper.readNBT();
        HashMap entries = new HashMap(nBTCompound.size());
        Iterator<Entry<String, it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT>>
                iterator2 = nBTCompound.getTags().entrySet().iterator();
        while (iterator2.hasNext()) {
            Entry entry;
            ItemMapDecorations.Decoration decoration =
                    ItemMapDecorations.Decoration.readCompound(
                            (NBTCompound) (entry = (Entry) iterator2.next()).getValue());
            entries.put((String) entry.getKey(), decoration);
        }
        return new ItemMapDecorations(entries);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemMapDecorations itemMapDecorations) {
        NBTCompound nBTCompound = new NBTCompound();
        for (Entry<
                        String,
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                .builtin.item.ItemMapDecorations.Decoration>
                entry : itemMapDecorations.decorations.entrySet()) {
            NBTCompound nBTCompound2;
            ItemMapDecorations.Decoration.writeCompound(
                    nBTCompound2 = new NBTCompound(),
                    (ItemMapDecorations.Decoration) entry.getValue());
            nBTCompound.setTag((String) entry.getKey(), nBTCompound2);
        }
        packetWrapper.writeNBT(nBTCompound);
    }

    @Nullable
    public ItemMapDecorations.Decoration getDecoration(String text) {
        return this.decorations.get(text);
    }

    public void setDecoration(String text, @Nullable ItemMapDecorations.Decoration decoration) {
        if (decoration != null) {
            this.decorations.put(text, decoration);
        } else {
            this.decorations.remove(text);
        }
    }

    public Map<String, ItemMapDecorations.Decoration> getDecorations() {
        return this.decorations;
    }

    public void setDecorations(Map<String, ItemMapDecorations.Decoration> entries) {
        this.decorations = entries;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemMapDecorations)) {
            return false;
        }
        ItemMapDecorations itemMapDecorations = (ItemMapDecorations) result;
        return this.decorations.equals(itemMapDecorations.decorations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.decorations);
    }

    public static final class Decoration {

        private MapDecorationType type;

        private double x;

        private double z;

        private float rotation;

        public Decoration(MapDecorationType mapDecorationType, double x, double z, float rotation) {
            this.type = mapDecorationType;
            this.x = x;
            this.z = z;
            this.rotation = rotation;
        }

        @Internal
        public static ItemMapDecorations.Decoration readCompound(NBTCompound nBTCompound) {
            MapDecorationType byName =
                    MapDecorationTypes.getByName(nBTCompound.getStringTagValueOrThrow("type"));
            double asDouble = nBTCompound.getNumberTagOrThrow("x").getAsDouble();
            double asDouble2 = nBTCompound.getNumberTagOrThrow("z").getAsDouble();
            float asFloat = nBTCompound.getNumberTagOrThrow("rotation").getAsFloat();
            return new ItemMapDecorations.Decoration(byName, asDouble, asDouble2, asFloat);
        }

        @Internal
        public static void writeCompound(
                NBTCompound nBTCompound, ItemMapDecorations.Decoration decoration) {
            nBTCompound.setTag("type", new NBTString(decoration.type.getName().toString()));
            nBTCompound.setTag("x", new NBTDouble(decoration.x));
            nBTCompound.setTag("z", new NBTDouble(decoration.z));
            nBTCompound.setTag("rotation", new NBTFloat(decoration.rotation));
        }

        public MapDecorationType getType() {
            return this.type;
        }

        public void setType(MapDecorationType mapDecorationType) {
            this.type = mapDecorationType;
        }

        public double getX() {
            return this.x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getZ() {
            return this.z;
        }

        public void setZ(double z) {
            this.z = z;
        }

        public float getRotation() {
            return this.rotation;
        }

        public void setRotation(float rotation) {
            this.rotation = rotation;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof ItemMapDecorations.Decoration)) {
                return false;
            } else {
                ItemMapDecorations.Decoration decoration;
                if (Double.compare((decoration = (ItemMapDecorations.Decoration) result).x, this.x)
                        != 0) {
                    return false;
                } else if (Double.compare(decoration.z, this.z) != 0) {
                    return false;
                } else {
                    return Float.compare(decoration.rotation, this.rotation) != 0
                            ? false
                            : this.type.equals(decoration.type);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.type, this.x, this.z, this.rotation);
        }
    }
}
