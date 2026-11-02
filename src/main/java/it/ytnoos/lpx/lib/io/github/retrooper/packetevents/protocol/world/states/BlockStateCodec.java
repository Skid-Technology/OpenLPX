package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateValue;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureIndexUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

@NullMarked
public final class BlockStateCodec {

    public static final NbtCodec<WrappedBlockState> FULL_CODEC =
            (new NbtMapCodec<WrappedBlockState>() {

                        public WrappedBlockState decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            boolean newerThanOrEquals =
                                    packetWrapper
                                            .getServerVersion()
                                            .isNewerThanOrEquals(ServerVersion.V_26_3);
                            StateType orThrow =
                                    nBTCompound.getOrThrow(
                                            newerThanOrEquals ? "id" : "Name",
                                            StateType.CODEC,
                                            packetWrapper);
                            WrappedBlockState wrappedBlockState;
                            NBTCompound nBTCompound2;
                            if (!(wrappedBlockState =
                                                    WrappedBlockState.getDefaultState(
                                                            packetWrapper
                                                                    .getServerVersion()
                                                                    .toClientVersion(),
                                                            orThrow))
                                            .data.isEmpty()
                                    && (nBTCompound2 =
                                                    nBTCompound.getCompoundTagOrNull(
                                                            newerThanOrEquals
                                                                    ? "properties"
                                                                    : "Properties"))
                                            != null) {
                                BlockStateCodec.decodeProperties(wrappedBlockState, nBTCompound2);
                            }
                            return wrappedBlockState;
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                WrappedBlockState wrappedBlockState)
                                throws NbtCodecException {
                            boolean newerThanOrEquals =
                                    packetWrapper
                                            .getServerVersion()
                                            .isNewerThanOrEquals(ServerVersion.V_26_3);
                            nBTCompound.set(
                                    newerThanOrEquals ? "id" : "Name",
                                    wrappedBlockState.type,
                                    StateType.CODEC,
                                    packetWrapper);
                            if (!wrappedBlockState.data.isEmpty()) {
                                nBTCompound.setTag(
                                        newerThanOrEquals ? "properties" : "Properties",
                                        BlockStateCodec.encodeProperties(wrappedBlockState));
                            }
                        }
                    })
                    .codec();

    public static final NbtCodec<WrappedBlockState> CODEC =
            new NbtCodec<WrappedBlockState>() {

                public WrappedBlockState decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    if (nBT instanceof NBTString) {
                        StateType stateType = StateType.CODEC.decode(nBT, packetWrapper);
                        return WrappedBlockState.getDefaultState(
                                packetWrapper.getServerVersion().toClientVersion(), stateType);
                    } else {
                        return BlockStateCodec.FULL_CODEC.decode(nBT, packetWrapper);
                    }
                }

                public NBT encode(
                        PacketWrapper<?> packetWrapper, WrappedBlockState wrappedBlockState)
                        throws NbtCodecException {
                    if (packetWrapper
                            .getServerVersion()
                            .isNewerThanOrEquals(ServerVersion.V_26_3)) {
                        WrappedBlockState defaultState =
                                WrappedBlockState.getDefaultState(
                                        packetWrapper.getServerVersion().toClientVersion(),
                                        wrappedBlockState.type,
                                        false);
                        if (wrappedBlockState.data.equals(defaultState.data)) {
                            return StateType.CODEC.encode(packetWrapper, wrappedBlockState.type);
                        }
                    }
                    return BlockStateCodec.FULL_CODEC.encode(packetWrapper, wrappedBlockState);
                }
            };

    private BlockStateCodec() {}

    private static NBTCompound encodeProperties(WrappedBlockState wrappedBlockState) {
        NBTCompound nBTCompound = new NBTCompound();
        Iterator iterator2 = wrappedBlockState.getInternalData().entrySet().iterator();
        while (iterator2.hasNext()) {
            Entry entry;
            StateValue stateValue = (StateValue) (entry = (Entry) iterator2.next()).getKey();
            String text = entry.getValue().toString().toLowerCase(Locale.ROOT);
            nBTCompound.setTag(stateValue.getName(), new NBTString(text));
        }
        return nBTCompound;
    }

    private static void decodeProperties(
            WrappedBlockState wrappedBlockState, NBTCompound nBTCompound) {
        boolean matches = false;
        for (Entry<String, NBT> entry : nBTCompound.getTags().entrySet()) {
            StateValue stateValue =
                    AdventureIndexUtil.indexValueOrThrow(
                            StateValue.NAME_INDEX, (String) entry.getKey());
            if (wrappedBlockState.hasProperty(stateValue)) {
                Object result;
                if (stateValue.getDataClass() == boolean.class
                        && entry.getValue() instanceof NBTNumber) {
                    result = ((NBTNumber) entry.getValue()).getAsByte() != 0;
                } else if (entry.getValue() instanceof NBTNumber) {
                    Number asNumber = ((NBTNumber) entry.getValue()).getAsNumber();
                    result = stateValue.parse(asNumber.toString());
                } else {
                    NBTString nBTString = ((NBT) entry.getValue()).castOrThrow(NBTString.class);
                    result = stateValue.parse(nBTString.getValue().toUpperCase(Locale.ROOT));
                }
                if (!matches) {
                    wrappedBlockState.checkIfCloneNeeded();
                    matches = true;
                }
                wrappedBlockState.data.put(stateValue, result);
            }
        }
        if (matches) {
            wrappedBlockState.checkIsStillValid();
        }
    }
}
