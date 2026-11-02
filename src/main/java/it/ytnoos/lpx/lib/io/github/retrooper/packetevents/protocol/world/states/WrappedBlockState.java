package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer.SequentialNBTReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.BlockFace;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Attachment;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Axis;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Bloom;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.CopperGolemPose;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.CreakingHeartState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.East;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Face;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Half;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Hinge;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Instrument;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Leaves;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Mode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.North;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Orientation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Part;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.PotentSulfurState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.SculkSensorPhase;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Shape;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.SideChainPart;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.South;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Thickness;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Tilt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.TrialSpawnerState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.Type;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.VerticalDirection;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.enums.West;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateValue;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.LogManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.MappingHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class WrappedBlockState {

    private static final ClientVersion[] MAPPING_VERSION_STEPS =
            new ClientVersion[] {
                ClientVersion.V_1_13,
                ClientVersion.V_1_13_2,
                ClientVersion.V_1_14,
                ClientVersion.V_1_15,
                ClientVersion.V_1_16,
                ClientVersion.V_1_16_2,
                ClientVersion.V_1_17,
                ClientVersion.V_1_19,
                ClientVersion.V_1_19_3,
                ClientVersion.V_1_19_4,
                ClientVersion.V_1_20,
                ClientVersion.V_1_20_2,
                ClientVersion.V_1_20_3,
                ClientVersion.V_1_20_5,
                ClientVersion.V_1_21_2,
                ClientVersion.V_1_21_4,
                ClientVersion.V_1_21_5,
                ClientVersion.V_1_21_6,
                ClientVersion.V_1_21_9,
                ClientVersion.V_26_1,
                ClientVersion.V_26_2,
                ClientVersion.V_26_3
            };

    private static final byte[] MAPPING_INDEXES;

    private static final ClientVersion[] MAPPING_VERSIONS;

    private static final byte HIGHEST_MAPPING_INDEX;

    private static final boolean PRELOAD_BLOCK_STATE_MAPPINGS =
            Boolean.getBoolean("packetevents.mappings.preload");

    protected static final WrappedBlockState AIR;

    private static final Map<String, WrappedBlockState>[] BY_STRING;

    private static final Map<Integer, WrappedBlockState>[] BY_ID;

    private static final Map<WrappedBlockState, String>[] INTO_STRING;

    private static final Map<WrappedBlockState, Integer>[] INTO_ID;

    private static final Map<StateType, WrappedBlockState>[] DEFAULT_STATES;

    private static final Map<String, String> STRING_UPDATER;

    int globalID;

    StateType type;

    Map<StateValue, Object> data = new HashMap<>(0);

    boolean hasClonedData = false;

    byte mappingsIndex;

    @Deprecated
    public WrappedBlockState(
            StateType stateType, String[] texts, int globalID, byte mappingsIndex) {
        this.type = stateType;
        this.globalID = globalID;
        if (texts != null) {
            int value3 = texts.length;
            for (int index = 0; index < value3; index++) {
                String text = texts[index];
                try {
                    String[] texts2;
                    StateValue stateValue = StateValue.byName((texts2 = text.split("="))[0]);
                    this.data.put(
                            stateValue,
                            stateValue.getParser().apply(texts2[1].toUpperCase(Locale.ROOT)));
                } catch (Exception exception) {
                    exception.printStackTrace();
                    PacketEvents.getAPI()
                            .getLogManager()
                            .warn("Failed to parse block state: " + text);
                }
            }
        }
        this.mappingsIndex = mappingsIndex;
    }

    public WrappedBlockState(
            StateType stateType,
            Map<StateValue, Object> entries,
            int globalID,
            byte mappingsIndex) {
        this.globalID = globalID;
        this.type = stateType;
        this.data = entries;
        this.mappingsIndex = mappingsIndex;
    }

    public static WrappedBlockState read(PacketWrapper<?> packetWrapper) {
        return getByGlobalId(
                packetWrapper.getServerVersion().toClientVersion(), packetWrapper.readVarInt());
    }

    public static void write(PacketWrapper<?> packetWrapper, WrappedBlockState wrappedBlockState) {
        packetWrapper.writeVarInt(wrappedBlockState.globalID);
    }

    private static byte loadMappings(ClientVersion clientVersion) {
        byte mappingsIndex = getMappingsIndex(clientVersion);
        if (!PRELOAD_BLOCK_STATE_MAPPINGS && BY_ID[mappingsIndex].isEmpty()) {
            loadMappings0(getMappingsVersion(clientVersion), mappingsIndex);
        }
        return mappingsIndex;
    }

    private static synchronized void loadMappings0(ClientVersion clientVersion, byte value) {
        if (BY_ID[value].isEmpty()) {
            PacketEvents.getAPI()
                    .getLogManager()
                    .info("Loading block mappings for " + clientVersion + "/" + value + "...");
            long value2 = System.nanoTime();
            if (value == 1) {
                loadLegacy(buildStateDataCache());
            } else {
                loadModern(buildStateDataCache(), clientVersion);
            }
            double value3 = (System.nanoTime() - value2) / 1000000.0;
            PacketEvents.getAPI()
                    .getLogManager()
                    .info(
                            "Finished loading block mappings for "
                                    + clientVersion
                                    + "/"
                                    + value
                                    + " in "
                                    + value3
                                    + "ms");
        }
    }

    private static Map<Map<StateValue, Object>, WrappedBlockState.StateCacheValue>
            buildStateDataCache() {
        HashMap<Map<StateValue, Object>, WrappedBlockState.StateCacheValue> entries =
                new HashMap<>();
        for (byte index = 0; index < HIGHEST_MAPPING_INDEX; index++) {
            Map<
                            Integer,
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states
                                    .WrappedBlockState>
                    entries2;
            if ((entries2 = BY_ID[index]) != null) {
                for (WrappedBlockState wrappedBlockState : entries2.values()) {
                    entries.computeIfAbsent(
                            wrappedBlockState.data, WrappedBlockState.StateCacheValue::new);
                }
            }
        }
        return entries;
    }

    @Deprecated
    public static WrappedBlockState decode(NBT nBT, ClientVersion clientVersion) {
        return BlockStateCodec.CODEC.decode(nBT, PacketWrapper.createDummyWrapper(clientVersion));
    }

    @Deprecated
    public static NBT encode(WrappedBlockState wrappedBlockState, ClientVersion clientVersion) {
        return BlockStateCodec.CODEC.encode(
                PacketWrapper.createDummyWrapper(clientVersion), wrappedBlockState);
    }

    @NotNull
    public static WrappedBlockState getByGlobalId(int value) {
        return getByGlobalId(value, true);
    }

    @NotNull
    public static WrappedBlockState getByGlobalId(int value, boolean matches) {
        return getByGlobalId(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                value,
                matches);
    }

    @NotNull
    public static WrappedBlockState getByGlobalId(ClientVersion clientVersion, int value) {
        return getByGlobalId(clientVersion, value, true);
    }

    @NotNull
    public static WrappedBlockState getByGlobalId(
            ClientVersion clientVersion, int value, boolean matches) {
        if (value == 0) {
            return AIR;
        }
        byte value2 = loadMappings(clientVersion);
        WrappedBlockState orDefault = BY_ID[value2].getOrDefault(value, AIR);
        return matches ? orDefault.clone() : orDefault;
    }

    @NotNull
    public static WrappedBlockState getByString(String text) {
        return getByString(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(), text);
    }

    @NotNull
    public static WrappedBlockState getByString(ClientVersion clientVersion, String text) {
        return getByString(clientVersion, text, true);
    }

    @NotNull
    public static WrappedBlockState getByString(
            ClientVersion clientVersion, String text, boolean matches) {
        byte value = loadMappings(clientVersion);
        WrappedBlockState orDefault =
                BY_STRING[value].getOrDefault(text.replace("minecraft:", ""), AIR);
        return matches ? orDefault.clone() : orDefault;
    }

    @NotNull
    public static WrappedBlockState getDefaultState(StateType stateType) {
        return getDefaultState(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(), stateType);
    }

    @NotNull
    public static WrappedBlockState getDefaultState(
            ClientVersion clientVersion, StateType stateType) {
        return getDefaultState(clientVersion, stateType, true);
    }

    @NotNull
    public static WrappedBlockState getDefaultState(
            ClientVersion clientVersion, StateType stateType, boolean matches) {
        if (stateType == StateTypes.AIR) {
            return AIR;
        } else {
            byte value = loadMappings(clientVersion);
            WrappedBlockState wrappedBlockState;
            if ((wrappedBlockState = DEFAULT_STATES[value].get(stateType)) == null) {
                PacketEvents.getAPI()
                        .getLogManager()
                        .debug(
                                "Default state for "
                                        + stateType.getName()
                                        + " is null. Returning AIR");
                return AIR;
            } else {
                return matches ? wrappedBlockState.clone() : wrappedBlockState;
            }
        }
    }

    private static byte getMappingsIndex(ClientVersion clientVersion) {
        return MAPPING_INDEXES[clientVersion.ordinal()];
    }

    private static ClientVersion getMappingsVersion(ClientVersion clientVersion) {
        return MAPPING_VERSIONS[clientVersion.ordinal()];
    }

    private static void loadLegacy(
            Map<Map<StateValue, Object>, WrappedBlockState.StateCacheValue> entries) {
        HashMap entries2 = new HashMap();
        HashMap entries3 = new HashMap();
        HashMap entries4 = new HashMap();
        HashMap entries5 = new HashMap();
        HashMap entries6 = new HashMap();
        try {
            SequentialNBTReader.Compound compound =
                    MappingHelper.decompress("mappings/data/block_state/legacy");
            try {
                compound.skipOne();
                SequentialNBTReader.Compound compound2 =
                        (SequentialNBTReader.Compound) compound.next().getValue();
                while (compound2.hasNext()) {
                    Entry entry;
                    SequentialNBTReader.Compound compound3 =
                            (SequentialNBTReader.Compound)
                                    (entry = (Entry) compound2.next()).getValue();
                    StateType stateType;
                    if ((stateType = StateTypes.getByName((String) entry.getKey())) == null) {
                        PacketEvents.getAPI()
                                .getLogManager()
                                .warn("Could not find type for " + (String) entry.getKey());
                        compound3.skip();
                    } else {
                        while (compound3.hasNext()) {
                            Entry entry2;
                            String text;
                            int value =
                                    (text = (String) (entry2 = (Entry) compound3.next()).getKey())
                                            .indexOf(58);
                            int value2 = Integer.parseInt(text.substring(0, value));
                            int value3 = Integer.parseInt(text.substring(value + 1));
                            int value4 = value2 << 4 | value3;
                            SequentialNBTReader.Compound compound4;
                            WrappedBlockState.StateCacheValue stateCacheValue;
                            if (!(compound4 = (SequentialNBTReader.Compound) entry2.getValue())
                                    .hasNext()) {
                                stateCacheValue = WrappedBlockState.StateCacheValue.EMPTY;
                            } else {
                                LinkedHashMap entries7 = new LinkedHashMap(3);
                                SequentialNBTReader.Compound compound5 = compound4;
                                while (compound5.hasNext()) {
                                    Entry entry3;
                                    StateValue stateValue;
                                    if ((stateValue =
                                                    StateValue.byName(
                                                            (String)
                                                                    (entry3 =
                                                                                    (Entry)
                                                                                            compound5
                                                                                                    .next())
                                                                            .getKey()))
                                            == null) {
                                        PacketEvents.getAPI()
                                                .getLogManager()
                                                .warn(
                                                        "Could not find value for "
                                                                + (String) entry3.getKey());
                                    } else {
                                        NBT nBT;
                                        Serializable serializable;
                                        if ((nBT = (NBT) entry3.getValue()) instanceof NBTByte) {
                                            serializable = ((NBTByte) nBT).getAsInt() == 1;
                                        } else if (nBT instanceof NBTNumber) {
                                            serializable = ((NBTNumber) nBT).getAsInt();
                                        } else {
                                            if (!(nBT instanceof NBTString)) {
                                                PacketEvents.getAPI()
                                                        .getLogManager()
                                                        .warn(
                                                                "Unknown NBT type in legacy mapping: "
                                                                        + nBT.getClass()
                                                                                .getSimpleName());
                                                continue;
                                            }
                                            serializable = ((NBTString) nBT).getValue();
                                        }
                                        entries7.put(
                                                stateValue,
                                                stateValue
                                                        .getParser()
                                                        .apply(
                                                                serializable
                                                                        .toString()
                                                                        .toUpperCase(Locale.ROOT)));
                                    }
                                }
                                stateCacheValue =
                                        entries.computeIfAbsent(
                                                entries7, WrappedBlockState.StateCacheValue::new);
                            }
                            String text2 = (String) entry.getKey() + stateCacheValue.getString();
                            WrappedBlockState wrappedBlockState =
                                    new WrappedBlockState(
                                            stateType, stateCacheValue.map, value4, (byte) 1);
                            entries2.put(value4, wrappedBlockState);
                            entries5.put(wrappedBlockState, text2);
                            entries3.put(wrappedBlockState, value4);
                            entries4.putIfAbsent(text2, wrappedBlockState);
                            entries6.putIfAbsent(stateType, wrappedBlockState);
                        }
                    }
                }
                BY_ID[1] = entries2;
                INTO_ID[1] = entries3;
                BY_STRING[1] = entries4;
                INTO_STRING[1] = entries5;
                DEFAULT_STATES[1] = entries6;
            } catch (Throwable throwable) {
                if (compound != null) {
                    try {
                        compound.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            if (compound != null) {
                compound.close();
            }
        } catch (IOException oException) {
            throw new RuntimeException("Failed to load legacy block mappings", oException);
        }
    }

    private static void loadModern(
            Map<Map<StateValue, Object>, WrappedBlockState.StateCacheValue> entries,
            ClientVersion clientVersion) {
        try {
            SequentialNBTReader.Compound compound =
                    MappingHelper.decompress("mappings/data/block_state/" + clientVersion.name());
            try {
                compound.skipOne();
                byte mappingsIndex = getMappingsIndex(clientVersion);
                SequentialNBTReader.List values =
                        (SequentialNBTReader.List) compound.next().getValue();
                HashMap entries2 = new HashMap();
                HashMap entries3 = new HashMap();
                HashMap entries4 = new HashMap();
                HashMap entries5 = new HashMap();
                HashMap entries6 = new HashMap();
                int value = 0;
                while (values.hasNext()) {
                    SequentialNBTReader.Compound compound2;
                    String text;
                    StateType stateType;
                    if ((stateType =
                                    StateTypes.getByName(
                                            text =
                                                    ((NBTString)
                                                                    (compound2 =
                                                                                    (SequentialNBTReader
                                                                                                    .Compound)
                                                                                            ((NBT)
                                                                                                    values
                                                                                                            .next()))
                                                                            .next()
                                                                            .getValue())
                                                            .getValue()))
                            == null) {
                        for (Entry<String, String> entry : STRING_UPDATER.entrySet()) {
                            text =
                                    text.replace(
                                            (CharSequence) entry.getKey(),
                                            (CharSequence) entry.getValue());
                        }
                        if ((stateType = StateTypes.getByName(text)) == null) {
                            PacketEvents.getAPI()
                                    .getLogManager()
                                    .warn("Unknown block type: " + text);
                            compound2.skip();
                            continue;
                        }
                    }
                    Entry<String, NBT> entry2 = compound2.next();
                    int value2 = 0;
                    if (!((String) entry2.getKey()).equals("def")) {
                        PacketEvents.getAPI()
                                .getLogManager()
                                .warn("No default state for " + stateType + " using 0");
                    } else {
                        value2 = ((NBTNumber) entry2.getValue()).getAsInt();
                        entry2 = compound2.next();
                    }
                    int value3 = 0;
                    for (SequentialNBTReader.List values2 =
                                    (SequentialNBTReader.List) entry2.getValue();
                            values2.hasNext();
                            value3++) {
                        SequentialNBTReader.Compound compound3;
                        WrappedBlockState.StateCacheValue stateCacheValue;
                        if (!(compound3 = (SequentialNBTReader.Compound) ((NBT) values2.next()))
                                .hasNext()) {
                            stateCacheValue = WrappedBlockState.StateCacheValue.EMPTY;
                        } else {
                            LinkedHashMap entries7 = new LinkedHashMap(3);
                            SequentialNBTReader.Compound compound4 = compound3;
                            while (compound4.hasNext()) {
                                Entry entry3;
                                StateValue stateValue;
                                if ((stateValue =
                                                StateValue.byName(
                                                        (String)
                                                                (entry3 = (Entry) compound4.next())
                                                                        .getKey()))
                                        == null) {
                                    PacketEvents.getAPI()
                                            .getLogManager()
                                            .warn(
                                                    "Could not find value for "
                                                            + (String) entry3.getKey());
                                } else {
                                    NBT nBT;
                                    Serializable serializable;
                                    if ((nBT = (NBT) entry3.getValue()) instanceof NBTByte) {
                                        serializable = ((NBTByte) nBT).getAsInt() == 1;
                                    } else if (nBT instanceof NBTNumber) {
                                        serializable = ((NBTNumber) nBT).getAsInt();
                                    } else {
                                        if (!(nBT instanceof NBTString)) {
                                            PacketEvents.getAPI()
                                                    .getLogManager()
                                                    .warn(
                                                            "Unknown NBT typeString in modern mapping: "
                                                                    + nBT.getClass()
                                                                            .getSimpleName());
                                            continue;
                                        }
                                        serializable = ((NBTString) nBT).getValue();
                                    }
                                    entries7.put(
                                            stateValue,
                                            stateValue
                                                    .getParser()
                                                    .apply(
                                                            serializable
                                                                    .toString()
                                                                    .toUpperCase(Locale.ROOT)));
                                }
                            }
                            stateCacheValue =
                                    entries.computeIfAbsent(
                                            entries7, WrappedBlockState.StateCacheValue::new);
                        }
                        String text2 = text + stateCacheValue.getString();
                        WrappedBlockState wrappedBlockState =
                                new WrappedBlockState(
                                        stateType, stateCacheValue.map, value, mappingsIndex);
                        if (value2 == value3) {
                            entries6.put(stateType, wrappedBlockState);
                        }
                        entries4.put(text2, wrappedBlockState);
                        entries2.put(value, wrappedBlockState);
                        entries5.put(wrappedBlockState, text2);
                        entries3.put(wrappedBlockState, value);
                        value++;
                    }
                }
                BY_ID[mappingsIndex] = entries2;
                INTO_ID[mappingsIndex] = entries3;
                BY_STRING[mappingsIndex] = entries4;
                INTO_STRING[mappingsIndex] = entries5;
                DEFAULT_STATES[mappingsIndex] = entries6;
            } catch (Throwable throwable) {
                if (compound != null) {
                    try {
                        compound.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            if (compound != null) {
                compound.close();
            }
        } catch (IOException oException) {
            throw new RuntimeException("Failed to load modern block mappings", oException);
        }
    }

    public WrappedBlockState clone() {
        return new WrappedBlockState(this.type, this.data, this.globalID, this.mappingsIndex);
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof WrappedBlockState)) {
            return false;
        }
        WrappedBlockState wrappedBlockState = (WrappedBlockState) result;
        return this.type == wrappedBlockState.type && this.data.equals(wrappedBlockState.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.data);
    }

    public StateType getType() {
        return this.type;
    }

    public Object getData(StateValue stateValue) {
        return this.data.get(stateValue);
    }

    public void setData(StateValue stateValue, Object result) {
        this.checkIfCloneNeeded();
        this.data.put(stateValue, result);
        this.checkIsStillValid();
    }

    public int getAge() {
        return (Integer) this.data.get(StateValue.AGE);
    }

    public void setAge(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.AGE, value);
        this.checkIsStillValid();
    }

    public boolean isAttached() {
        return (Boolean) this.data.get(StateValue.ATTACHED);
    }

    public void setAttached(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.ATTACHED, matches);
        this.checkIsStillValid();
    }

    public Attachment getAttachment() {
        return (Attachment) this.data.get(StateValue.ATTACHMENT);
    }

    public void setAttachment(Attachment attachment) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.ATTACHMENT, attachment);
        this.checkIsStillValid();
    }

    public Axis getAxis() {
        return (Axis) this.data.get(StateValue.AXIS);
    }

    public void setAxis(Axis axis) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.AXIS, axis);
        this.checkIsStillValid();
    }

    public boolean isBerries() {
        return (Boolean) this.data.get(StateValue.BERRIES);
    }

    public void setBerries(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.BERRIES, matches);
        this.checkIsStillValid();
    }

    public int getBites() {
        return (Integer) this.data.get(StateValue.BITES);
    }

    public void setBites(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.BITES, value);
        this.checkIsStillValid();
    }

    public boolean isBottom() {
        return (Boolean) this.data.get(StateValue.BOTTOM);
    }

    public void setBottom(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.BOTTOM, matches);
        this.checkIsStillValid();
    }

    public int getCandles() {
        return (Integer) this.data.get(StateValue.CANDLES);
    }

    public void setCandles(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.CANDLES, value);
        this.checkIsStillValid();
    }

    public int getCharges() {
        return (Integer) this.data.get(StateValue.CHARGES);
    }

    public void setCharges(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.CHARGES, value);
        this.checkIsStillValid();
    }

    public boolean isConditional() {
        return (Boolean) this.data.get(StateValue.CONDITIONAL);
    }

    public void setConditional(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.CONDITIONAL, matches);
        this.checkIsStillValid();
    }

    public int getDelay() {
        return (Integer) this.data.get(StateValue.DELAY);
    }

    public void setDelay(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.DELAY, value);
        this.checkIsStillValid();
    }

    public boolean isDisarmed() {
        return (Boolean) this.data.get(StateValue.DISARMED);
    }

    public void setDisarmed(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.DISARMED, matches);
        this.checkIsStillValid();
    }

    public int getDistance() {
        return (Integer) this.data.get(StateValue.DISTANCE);
    }

    public void setDistance(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.DISTANCE, value);
        this.checkIsStillValid();
    }

    public boolean isDown() {
        return (Boolean) this.data.get(StateValue.DOWN);
    }

    public void setDown(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.DOWN, matches);
        this.checkIsStillValid();
    }

    public boolean isDrag() {
        return (Boolean) this.data.get(StateValue.DRAG);
    }

    public void setDrag(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.DRAG, matches);
        this.checkIsStillValid();
    }

    public boolean isDusted() {
        return (Boolean) this.data.get(StateValue.DUSTED);
    }

    public void setDusted(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.DUSTED, matches);
        this.checkIsStillValid();
    }

    public int getEggs() {
        return (Integer) this.data.get(StateValue.EGGS);
    }

    public void setEggs(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.EGGS, value);
        this.checkIsStillValid();
    }

    public boolean isEnabled() {
        return (Boolean) this.data.get(StateValue.ENABLED);
    }

    public void setEnabled(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.ENABLED, matches);
        this.checkIsStillValid();
    }

    public boolean isExtended() {
        return (Boolean) this.data.get(StateValue.EXTENDED);
    }

    public void setExtended(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.EXTENDED, matches);
        this.checkIsStillValid();
    }

    public boolean isEye() {
        return (Boolean) this.data.get(StateValue.EYE);
    }

    public void setEye(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.EYE, matches);
        this.checkIsStillValid();
    }

    public Face getFace() {
        return (Face) this.data.get(StateValue.FACE);
    }

    public void setFace(Face face) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.FACE, face);
        this.checkIsStillValid();
    }

    public BlockFace getFacing() {
        return (BlockFace) this.data.get(StateValue.FACING);
    }

    public void setFacing(BlockFace blockFace) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.FACING, blockFace);
        this.checkIsStillValid();
    }

    public int getFlowerAmount() {
        return (Integer) this.data.get(StateValue.FLOWER_AMOUNT);
    }

    public void setFlowerAmount(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.FLOWER_AMOUNT, value);
        this.checkIsStillValid();
    }

    public Half getHalf() {
        return (Half) this.data.get(StateValue.HALF);
    }

    public void setHalf(Half half) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HALF, half);
        this.checkIsStillValid();
    }

    public boolean isHanging() {
        return (Boolean) this.data.get(StateValue.HANGING);
    }

    public void setHanging(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HANGING, matches);
        this.checkIsStillValid();
    }

    public boolean isHasBook() {
        return (Boolean) this.data.get(StateValue.HAS_BOOK);
    }

    public void setHasBook(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HAS_BOOK, matches);
        this.checkIsStillValid();
    }

    public boolean isHasBottle0() {
        return (Boolean) this.data.get(StateValue.HAS_BOTTLE_0);
    }

    public void setHasBottle0(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HAS_BOTTLE_0, matches);
        this.checkIsStillValid();
    }

    public boolean isHasBottle1() {
        return (Boolean) this.data.get(StateValue.HAS_BOTTLE_1);
    }

    public void setHasBottle1(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HAS_BOTTLE_1, matches);
        this.checkIsStillValid();
    }

    public boolean isHasBottle2() {
        return (Boolean) this.data.get(StateValue.HAS_BOTTLE_2);
    }

    public void setHasBottle2(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HAS_BOTTLE_2, matches);
        this.checkIsStillValid();
    }

    public boolean isHasRecord() {
        return (Boolean) this.data.get(StateValue.HAS_RECORD);
    }

    public void setHasRecord(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HAS_RECORD, matches);
        this.checkIsStillValid();
    }

    public int getHatch() {
        return (Integer) this.data.get(StateValue.HATCH);
    }

    public void setHatch(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HATCH, value);
        this.checkIsStillValid();
    }

    public Hinge getHinge() {
        return (Hinge) this.data.get(StateValue.HINGE);
    }

    public void setHinge(Hinge hinge) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HINGE, hinge);
        this.checkIsStillValid();
    }

    public int getHoneyLevel() {
        return (Integer) this.data.get(StateValue.HONEY_LEVEL);
    }

    public void setHoneyLevel(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HONEY_LEVEL, value);
        this.checkIsStillValid();
    }

    public boolean isInWall() {
        return (Boolean) this.data.get(StateValue.IN_WALL);
    }

    public void setInWall(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.IN_WALL, matches);
        this.checkIsStillValid();
    }

    public Instrument getInstrument() {
        return (Instrument) this.data.get(StateValue.INSTRUMENT);
    }

    public void setInstrument(Instrument instrument) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.INSTRUMENT, instrument);
        this.checkIsStillValid();
    }

    public boolean isInverted() {
        return (Boolean) this.data.get(StateValue.INVERTED);
    }

    public void setInverted(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.INVERTED, matches);
        this.checkIsStillValid();
    }

    public int getLayers() {
        return (Integer) this.data.get(StateValue.LAYERS);
    }

    public void setLayers(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.LAYERS, value);
        this.checkIsStillValid();
    }

    public Leaves getLeaves() {
        return (Leaves) this.data.get(StateValue.LEAVES);
    }

    public void setLeaves(Leaves leaves) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.LEAVES, leaves);
        this.checkIsStillValid();
    }

    public int getLevel() {
        return (Integer) this.data.get(StateValue.LEVEL);
    }

    public void setLevel(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.LEVEL, value);
        this.checkIsStillValid();
    }

    public boolean isLit() {
        return (Boolean) this.data.get(StateValue.LIT);
    }

    public void setLit(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.LIT, matches);
        this.checkIsStillValid();
    }

    public boolean isTip() {
        return (Boolean) this.data.get(StateValue.TIP);
    }

    public void setTip(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.TIP, matches);
        this.checkIsStillValid();
    }

    public boolean isLocked() {
        return (Boolean) this.data.get(StateValue.LOCKED);
    }

    public void setLocked(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.LOCKED, matches);
        this.checkIsStillValid();
    }

    public Mode getMode() {
        return (Mode) this.data.get(StateValue.MODE);
    }

    public void setMode(Mode mode) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.MODE, mode);
        this.checkIsStillValid();
    }

    public int getMoisture() {
        return (Integer) this.data.get(StateValue.MOISTURE);
    }

    public void setMoisture(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.MOISTURE, value);
        this.checkIsStillValid();
    }

    public North getNorth() {
        return (North) this.data.get(StateValue.NORTH);
    }

    public void setNorth(North north) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.NORTH, north);
        this.checkIsStillValid();
    }

    public int getNote() {
        return (Integer) this.data.get(StateValue.NOTE);
    }

    public void setNote(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.NOTE, value);
        this.checkIsStillValid();
    }

    public boolean isOccupied() {
        return (Boolean) this.data.get(StateValue.OCCUPIED);
    }

    public void setOccupied(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.OCCUPIED, matches);
        this.checkIsStillValid();
    }

    public boolean isShrieking() {
        return (Boolean) this.data.get(StateValue.SHRIEKING);
    }

    public void setShrieking(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SHRIEKING, matches);
        this.checkIsStillValid();
    }

    public boolean isCanSummon() {
        return (Boolean) this.data.get(StateValue.CAN_SUMMON);
    }

    public void setCanSummon(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.CAN_SUMMON, matches);
        this.checkIsStillValid();
    }

    public boolean isOpen() {
        return (Boolean) this.data.get(StateValue.OPEN);
    }

    public void setOpen(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.OPEN, matches);
        this.checkIsStillValid();
    }

    public Orientation getOrientation() {
        return (Orientation) this.data.get(StateValue.ORIENTATION);
    }

    public void setOrientation(Orientation orientation) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.ORIENTATION, orientation);
        this.checkIsStillValid();
    }

    public Part getPart() {
        return (Part) this.data.get(StateValue.PART);
    }

    public void setPart(Part part) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.PART, part);
        this.checkIsStillValid();
    }

    public boolean isPersistent() {
        return (Boolean) this.data.get(StateValue.PERSISTENT);
    }

    public void setPersistent(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.PERSISTENT, matches);
        this.checkIsStillValid();
    }

    public int getPickles() {
        return (Integer) this.data.get(StateValue.PICKLES);
    }

    public void setPickles(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.PICKLES, value);
        this.checkIsStillValid();
    }

    public int getPower() {
        return (Integer) this.data.get(StateValue.POWER);
    }

    public void setPower(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.POWER, value);
        this.checkIsStillValid();
    }

    public boolean isPowered() {
        return (Boolean) this.data.get(StateValue.POWERED);
    }

    public void setPowered(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.POWERED, matches);
        this.checkIsStillValid();
    }

    public int getRotation() {
        return (Integer) this.data.get(StateValue.ROTATION);
    }

    public void setRotation(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.ROTATION, value);
        this.checkIsStillValid();
    }

    public SculkSensorPhase getSculkSensorPhase() {
        return (SculkSensorPhase) this.data.get(StateValue.SCULK_SENSOR_PHASE);
    }

    public void setSculkSensorPhase(SculkSensorPhase sculkSensorPhase) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SCULK_SENSOR_PHASE, sculkSensorPhase);
        this.checkIsStillValid();
    }

    public Shape getShape() {
        return (Shape) this.data.get(StateValue.SHAPE);
    }

    public void setShape(Shape shape) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SHAPE, shape);
        this.checkIsStillValid();
    }

    public boolean isShort() {
        return (Boolean) this.data.get(StateValue.SHORT);
    }

    public void setShort(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SHORT, matches);
        this.checkIsStillValid();
    }

    public boolean isSignalFire() {
        return (Boolean) this.data.get(StateValue.SIGNAL_FIRE);
    }

    public void setSignalFire(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SIGNAL_FIRE, matches);
        this.checkIsStillValid();
    }

    public boolean isSlotZeroOccupied() {
        return (Boolean) this.data.get(StateValue.SLOT_0_OCCUPIED);
    }

    public void setSlotZeroOccupied(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SLOT_0_OCCUPIED, matches);
        this.checkIsStillValid();
    }

    public boolean isSlotOneOccupied() {
        return (Boolean) this.data.get(StateValue.SLOT_1_OCCUPIED);
    }

    public void setSlotOneOccupied(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SLOT_1_OCCUPIED, matches);
        this.checkIsStillValid();
    }

    public boolean isSlotTwoOccupied() {
        return (Boolean) this.data.get(StateValue.SLOT_2_OCCUPIED);
    }

    public void setSlotTwoOccupied(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SLOT_2_OCCUPIED, matches);
        this.checkIsStillValid();
    }

    public boolean isSlotThreeOccupied() {
        return (Boolean) this.data.get(StateValue.SLOT_3_OCCUPIED);
    }

    public void setSlotThreeOccupied(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SLOT_3_OCCUPIED, matches);
        this.checkIsStillValid();
    }

    public boolean isSlotFourOccupied() {
        return (Boolean) this.data.get(StateValue.SLOT_4_OCCUPIED);
    }

    public void setSlotFourOccupied(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SLOT_4_OCCUPIED, matches);
        this.checkIsStillValid();
    }

    public boolean isSlotFiveOccupied() {
        return (Boolean) this.data.get(StateValue.SLOT_5_OCCUPIED);
    }

    public void setSlotFiveOccupied(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SLOT_5_OCCUPIED, matches);
        this.checkIsStillValid();
    }

    public boolean isSnowy() {
        return (Boolean) this.data.get(StateValue.SNOWY);
    }

    public void setSnowy(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SNOWY, matches);
        this.checkIsStillValid();
    }

    public int getStage() {
        return (Integer) this.data.get(StateValue.STAGE);
    }

    public void setStage(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.STAGE, value);
        this.checkIsStillValid();
    }

    public South getSouth() {
        return (South) this.data.get(StateValue.SOUTH);
    }

    public void setSouth(South south) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SOUTH, south);
        this.checkIsStillValid();
    }

    public Thickness getThickness() {
        return (Thickness) this.data.get(StateValue.THICKNESS);
    }

    public void setThickness(Thickness thickness) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.THICKNESS, thickness);
        this.checkIsStillValid();
    }

    public Tilt getTilt() {
        return (Tilt) this.data.get(StateValue.TILT);
    }

    public void setTilt(Tilt tilt) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.TILT, tilt);
        this.checkIsStillValid();
    }

    public boolean isTriggered() {
        return (Boolean) this.data.get(StateValue.TRIGGERED);
    }

    public void setTriggered(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.TRIGGERED, matches);
        this.checkIsStillValid();
    }

    public Type getTypeData() {
        return (Type) this.data.get(StateValue.TYPE);
    }

    public void setTypeData(Type type) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.TYPE, type);
        this.checkIsStillValid();
    }

    public boolean isUnstable() {
        return (Boolean) this.data.get(StateValue.UNSTABLE);
    }

    public void setUnstable(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.UNSTABLE, matches);
        this.checkIsStillValid();
    }

    public boolean isUp() {
        return (Boolean) this.data.get(StateValue.UP);
    }

    public void setUp(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.UP, matches);
        this.checkIsStillValid();
    }

    public VerticalDirection getVerticalDirection() {
        return (VerticalDirection) this.data.get(StateValue.VERTICAL_DIRECTION);
    }

    public void setVerticalDirection(VerticalDirection verticalDirection) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.VERTICAL_DIRECTION, verticalDirection);
        this.checkIsStillValid();
    }

    public boolean isWaterlogged() {
        return (Boolean) this.data.get(StateValue.WATERLOGGED);
    }

    public void setWaterlogged(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.WATERLOGGED, matches);
        this.checkIsStillValid();
    }

    public East getEast() {
        return (East) this.data.get(StateValue.EAST);
    }

    public void setEast(East east) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.EAST, east);
        this.checkIsStillValid();
    }

    public West getWest() {
        return (West) this.data.get(StateValue.WEST);
    }

    public void setWest(West west) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.WEST, west);
        this.checkIsStillValid();
    }

    public Bloom getBloom() {
        return (Bloom) this.data.get(StateValue.BLOOM);
    }

    public void setBloom(Bloom bloom) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.BLOOM, bloom);
        this.checkIsStillValid();
    }

    public boolean isCracked() {
        return (Boolean) this.data.get(StateValue.CRACKED);
    }

    public void setCracked(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.CRACKED, matches);
        this.checkIsStillValid();
    }

    public boolean isCrafting() {
        return (Boolean) this.data.get(StateValue.CRAFTING);
    }

    public void setCrafting(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.CRAFTING, matches);
        this.checkIsStillValid();
    }

    public TrialSpawnerState getTrialSpawnerState() {
        return (TrialSpawnerState) this.data.get(StateValue.TRIAL_SPAWNER_STATE);
    }

    public void setTrialSpawnerState(TrialSpawnerState trialSpawnerState) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.TRIAL_SPAWNER_STATE, trialSpawnerState);
        this.checkIsStillValid();
    }

    @Obsolete
    public CreakingHeartState getCreaking() {
        return (CreakingHeartState) this.data.get(StateValue.CREAKING);
    }

    @Obsolete
    public void setCreaking(CreakingHeartState creakingHeartState) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.CREAKING, creakingHeartState);
        this.checkIsStillValid();
    }

    @Obsolete
    public boolean isActive() {
        return (Boolean) this.data.get(StateValue.ACTIVE);
    }

    @Obsolete
    public void setActive(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.ACTIVE, matches);
        this.checkIsStillValid();
    }

    public boolean isNatural() {
        return (Boolean) this.data.get(StateValue.NATURAL);
    }

    public void setNatural(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.NATURAL, matches);
        this.checkIsStillValid();
    }

    public int getSegmentAmount() {
        return (Integer) this.data.get(StateValue.SEGMENT_AMOUNT);
    }

    public void setSegmentAmount(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SEGMENT_AMOUNT, value);
        this.checkIsStillValid();
    }

    public CreakingHeartState getCreakingHeartState() {
        return (CreakingHeartState) this.data.get(StateValue.CREAKING_HEART_STATE);
    }

    public void setCreakingHeartState(CreakingHeartState creakingHeartState) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.CREAKING_HEART_STATE, creakingHeartState);
        this.checkIsStillValid();
    }

    public boolean isMap() {
        return (Boolean) this.data.get(StateValue.MAP);
    }

    public void setMap(boolean matches) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.MAP, matches);
        this.checkIsStillValid();
    }

    public int getHydration() {
        return (Integer) this.data.get(StateValue.HYDRATION);
    }

    public void setHydration(int value) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.HYDRATION, value);
        this.checkIsStillValid();
    }

    public SideChainPart getSideChain() {
        return (SideChainPart) this.data.get(StateValue.SIDE_CHAIN);
    }

    public void setSideChain(SideChainPart sideChainPart) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.SIDE_CHAIN, sideChainPart);
        this.checkIsStillValid();
    }

    public CopperGolemPose getCopperGolemPose() {
        return (CopperGolemPose) this.data.get(StateValue.COPPER_GOLEM_POSE);
    }

    public void setCopperGolemPose(CopperGolemPose copperGolemPose) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.COPPER_GOLEM_POSE, copperGolemPose);
        this.checkIsStillValid();
    }

    public PotentSulfurState getPotentSulfurState() {
        return (PotentSulfurState) this.data.get(StateValue.POTENT_SULFUR_STATE);
    }

    public void setPotentSulfurState(PotentSulfurState potentSulfurState) {
        this.checkIfCloneNeeded();
        this.data.put(StateValue.POTENT_SULFUR_STATE, potentSulfurState);
        this.checkIsStillValid();
    }

    protected void checkIfCloneNeeded() {
        if (!this.hasClonedData) {
            this.data = new HashMap<>(this.data);
            this.hasClonedData = true;
        }
    }

    protected void checkIsStillValid() {
        int value = this.globalID;
        this.globalID = this.getGlobalIdNoCache();
        if (this.globalID == -1) {
            WrappedBlockState wrappedBlockState =
                    BY_ID[this.mappingsIndex].getOrDefault(value, AIR).clone();
            this.type = wrappedBlockState.type;
            this.globalID = wrappedBlockState.globalID;
            this.data = new HashMap<>(wrappedBlockState.data);
            if (PacketEvents.getAPI().getSettings().isDebugEnabled()) {
                PacketEvents.getAPI()
                        .getLogManager()
                        .warn(
                                "Attempt to modify an unknown property for this game version and block!");
                PacketEvents.getAPI().getLogManager().warn("Block: " + this.type.getName());
                for (Entry<StateValue, Object> entry : this.data.entrySet()) {
                    PacketEvents.getAPI()
                            .getLogManager()
                            .warn(entry.getKey() + ": " + entry.getValue());
                }
                new IllegalStateException("An invalid modification was made to a block!")
                        .printStackTrace();
            }
        }
    }

    @Internal
    public Map<StateValue, Object> getInternalData() {
        return this.data;
    }

    public boolean isFluid() {
        StateType stateType;
        return (stateType = this.getType()) == StateTypes.WATER
                || stateType == StateTypes.LAVA
                || this.hasProperty(StateValue.WATERLOGGED) && this.isWaterlogged();
    }

    public boolean hasProperty(StateValue stateValue) {
        return this.data.containsKey(stateValue);
    }

    public int getGlobalId() {
        return this.globalID;
    }

    private int getGlobalIdNoCache() {
        return INTO_ID[this.mappingsIndex].getOrDefault(this, -1);
    }

    @Override
    public String toString() {
        return INTO_STRING[this.mappingsIndex].get(this);
    }

    @Internal
    public static void ensureLoad() {
        if (PRELOAD_BLOCK_STATE_MAPPINGS) {
            LogManager logManager;
            (logManager = PacketEvents.getAPI().getLogManager())
                    .info("Preloading block mappings...");
            long value = System.nanoTime();
            HashMap entries;
            loadLegacy(entries = new HashMap());
            ClientVersion[] clientVersions = MAPPING_VERSION_STEPS;
            int value2 = MAPPING_VERSION_STEPS.length;
            for (int index = 0; index < value2; index++) {
                ClientVersion clientVersion = clientVersions[index];
                loadModern(entries, clientVersion);
            }
            double value3 = (System.nanoTime() - value) / 1000000.0;
            logManager.info("Finish preloading block mappings in " + value3 + "ms");
        }
    }

    static {
        ClientVersion[] clientVersions;
        MAPPING_INDEXES = new byte[(clientVersions = ClientVersion.values()).length];
        MAPPING_VERSIONS = new ClientVersion[clientVersions.length];
        ClientVersion clientVersion = clientVersions[0];
        int value = 0;
        int value2 = 0;
        while (value < clientVersions.length) {
            ClientVersion clientVersion2 = clientVersions[value];
            if (value2 < MAPPING_VERSION_STEPS.length
                    && clientVersion2 == MAPPING_VERSION_STEPS[value2]) {
                value2++;
                clientVersion = clientVersion2;
            }
            MAPPING_INDEXES[clientVersion2.ordinal()] = (byte) (1 + value2);
            MAPPING_VERSIONS[clientVersion2.ordinal()] = clientVersion;
            value++;
        }
        HIGHEST_MAPPING_INDEX = MAPPING_INDEXES[clientVersions.length - 1];
        AIR = new WrappedBlockState(StateTypes.AIR, new EnumMap<>(StateValue.class), 0, (byte) 0);
        BY_STRING = new Map[HIGHEST_MAPPING_INDEX + 1];
        BY_ID = new Map[HIGHEST_MAPPING_INDEX + 1];
        INTO_STRING = new Map[HIGHEST_MAPPING_INDEX + 1];
        INTO_ID = new Map[HIGHEST_MAPPING_INDEX + 1];
        DEFAULT_STATES = new Map[HIGHEST_MAPPING_INDEX + 1];
        STRING_UPDATER = new HashMap<>();
        STRING_UPDATER.put("grass_path", "dirt_path");
        Arrays.fill(BY_STRING, Collections.emptyMap());
        Arrays.fill(BY_ID, Collections.emptyMap());
        Arrays.fill(INTO_STRING, Collections.emptyMap());
        Arrays.fill(INTO_ID, Collections.emptyMap());
        Arrays.fill(DEFAULT_STATES, Collections.emptyMap());
        String key = AIR.getType().getMapped().getName().getKey();
        BY_STRING[0] = Collections.singletonMap(key, AIR);
        BY_ID[0] = Collections.singletonMap(AIR.getGlobalId(), AIR);
        INTO_STRING[0] = Collections.singletonMap(AIR, key);
        INTO_ID[0] = Collections.singletonMap(AIR, AIR.getGlobalId());
        DEFAULT_STATES[0] = Collections.singletonMap(AIR.getType(), AIR);
    }

    private static final class StateCacheValue {

        public static final WrappedBlockState.StateCacheValue EMPTY =
                new WrappedBlockState.StateCacheValue(Collections.emptyMap());

        private final Map<StateValue, Object> map;

        private String string;

        public StateCacheValue(Map<StateValue, Object> entries) {
            this.map = entries;
        }

        public String getString() {
            if (this.string == null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Entry<StateValue, Object> entry : this.map.entrySet()) {
                    stringBuilder
                            .append(((StateValue) entry.getKey()).getName())
                            .append('=')
                            .append(String.valueOf(entry.getValue()).toLowerCase(Locale.ROOT))
                            .append(',');
                }
                this.string =
                        stringBuilder.length() == 0
                                ? ""
                                : '['
                                        + stringBuilder.substring(0, stringBuilder.length() - 1)
                                        + ']';
            }
            return this.string;
        }
    }
}
