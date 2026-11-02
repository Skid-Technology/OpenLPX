package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class DebugStickState {

    private Map<StateType, String> properties;

    public DebugStickState(Map<StateType, String> entries) {
        this.properties = entries;
    }

    public static DebugStickState read(PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound = packetWrapper.readNBT();
        HashMap entries = new HashMap(nBTCompound.size());
        Iterator<Entry<String, it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT>>
                iterator2 = nBTCompound.getTags().entrySet().iterator();
        while (iterator2.hasNext()) {
            Entry entry;
            StateType byName =
                    StateTypes.getByName((String) (entry = (Entry) iterator2.next()).getKey());
            String value = ((NBTString) entry.getValue()).getValue();
            entries.put(byName, value);
        }
        return new DebugStickState(entries);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugStickState debugStickState) {
        NBTCompound nBTCompound = new NBTCompound();
        for (Entry<StateType, String> entry : debugStickState.properties.entrySet()) {
            nBTCompound.setTag(
                    ((StateType) entry.getKey()).getName(),
                    new NBTString((String) entry.getValue()));
        }
        packetWrapper.writeNBT(nBTCompound);
    }

    @Nullable
    public String getProperty(StateType stateType) {
        return this.properties.get(stateType);
    }

    public void setProperty(StateType stateType, @Nullable String text) {
        if (text != null) {
            this.properties.put(stateType, text);
        } else {
            this.properties.remove(stateType);
        }
    }

    public Map<StateType, String> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<StateType, String> entries) {
        this.properties = entries;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof DebugStickState)) {
            return false;
        }
        DebugStickState debugStickState = (DebugStickState) result;
        return this.properties.equals(debugStickState.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.properties);
    }
}
