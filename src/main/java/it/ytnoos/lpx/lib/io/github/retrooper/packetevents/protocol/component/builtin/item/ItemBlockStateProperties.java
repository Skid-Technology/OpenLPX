package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateValue;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class ItemBlockStateProperties {

    private Map<String, String> properties;

    public ItemBlockStateProperties(Map<String, String> entries) {
        this.properties = entries;
    }

    public static ItemBlockStateProperties read(PacketWrapper<?> packetWrapper) {
        Map entries = packetWrapper.readMap(PacketWrapper::readString, PacketWrapper::readString);
        return new ItemBlockStateProperties(entries);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemBlockStateProperties itemBlockStateProperties) {
        packetWrapper.writeMap(
                itemBlockStateProperties.properties,
                PacketWrapper::writeString,
                PacketWrapper::writeString);
    }

    @Nullable
    public Object getProperty(StateValue stateValue) {
        String text;
        return (text = this.getProperty(stateValue.getName())) != null
                ? stateValue.getParser().apply(text)
                : null;
    }

    @Nullable
    public String getProperty(String text) {
        return this.properties.get(text);
    }

    public void setProperty(StateValue stateValue, @Nullable Object result) {
        this.setProperty(stateValue.getName(), result == null ? null : result.toString());
    }

    public void setProperty(String text, @Nullable String text2) {
        if (text2 == null) {
            this.properties.remove(text);
        } else {
            this.properties.put(text, text2);
        }
    }

    public void unsetProperty(StateValue stateValue) {
        this.unsetProperty(stateValue.getName());
    }

    public void unsetProperty(String text) {
        this.setProperty(text, null);
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, String> entries) {
        this.properties = entries;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemBlockStateProperties)) {
            return false;
        }
        ItemBlockStateProperties itemBlockStateProperties = (ItemBlockStateProperties) result;
        return this.properties.equals(itemBlockStateProperties.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.properties);
    }
}
