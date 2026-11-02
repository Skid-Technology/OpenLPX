package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.key.KeyPattern;
import it.ytnoos.lpx.lib.kyori.adventure.key.Keyed;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ResourceLocation implements Keyed {

    public static final NbtCodec<ResourceLocation> CODEC =
            NbtCodecs.STRING.apply(ResourceLocation::new, ResourceLocation::toString);

    public static final String VANILLA_NAMESPACE = "minecraft";

    private final Key key;

    public ResourceLocation(Key key2) {
        this.key = key2;
    }

    public ResourceLocation(@KeyPattern.Namespace String text, @KeyPattern.Value String text2) {
        this.key = Key.key(text, text2);
    }

    public ResourceLocation(String text) {
        this.key = Key.key(text);
    }

    public static ResourceLocation read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readIdentifier();
    }

    public static void write(PacketWrapper<?> packetWrapper, ResourceLocation resourceLocation) {
        packetWrapper.writeIdentifier(resourceLocation);
    }

    public static ResourceLocation decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        return new ResourceLocation(((NBTString) nBT).getValue());
    }

    public static NBT encode(PacketWrapper<?> packetWrapper, ResourceLocation resourceLocation) {
        return new NBTString(resourceLocation.toString());
    }

    public static String getNamespace(String text) {
        int value;
        return (value = text.indexOf(58)) > 0 ? text.substring(0, value) : "minecraft";
    }

    public static String getPath(String text) {
        int value;
        return (value = text.indexOf(58)) != -1 ? text.substring(value + 1) : text;
    }

    @Contract("null -> null; !null -> !null")
    @Nullable
    public static String normString(@Nullable String text) {
        if (text == null) {
            return null;
        } else {
            int value;
            if ((value = text.indexOf(58)) > 0) {
                return text;
            } else {
                return value == -1 ? "minecraft:" + text : "minecraft" + text;
            }
        }
    }

    @Override
    public Key key() {
        return this.key;
    }

    public String getNamespace() {
        return this.key.namespace();
    }

    public String getKey() {
        return this.key.value();
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public boolean equals(Object result) {
        return result instanceof ResourceLocation
                ? ((ResourceLocation) result).key.equals(this.key)
                : false;
    }

    @Override
    public String toString() {
        return this.key.asString();
    }

    public static ResourceLocation minecraft(@KeyPattern.Value String text) {
        return new ResourceLocation("minecraft", text);
    }
}
