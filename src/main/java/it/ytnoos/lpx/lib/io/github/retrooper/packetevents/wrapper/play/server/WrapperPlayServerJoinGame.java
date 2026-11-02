package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.GameMode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Difficulty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Dimension;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.WorldBlockPosition;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.WorldType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionTypeRef;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WrapperPlayServerJoinGame extends PacketWrapper<WrapperPlayServerJoinGame> {

    private int entityID;

    private boolean hardcore;

    private GameMode gameMode;

    @Nullable private GameMode previousGameMode;

    private List<String> worldNames;

    private NBTCompound dimensionCodec;

    private DimensionTypeRef dimensionTypeRef;

    private Difficulty difficulty;

    private String worldName;

    private long hashedSeed;

    private int maxPlayers;

    private int viewDistance;

    private int simulationDistance;

    private boolean reducedDebugInfo;

    private boolean enableRespawnScreen;

    private boolean limitedCrafting;

    private boolean isDebug;

    private boolean isFlat;

    private WorldBlockPosition lastDeathPosition;

    private Integer portalCooldown;

    private int seaLevel;

    private boolean onlineMode;

    private boolean enforcesSecureChat;

    public WrapperPlayServerJoinGame(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Deprecated
    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            Dimension dimension,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                dimension.asDimensionTypeRef(),
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                worldBlockPosition,
                value6);
    }

    @Deprecated
    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            Dimension dimension,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            boolean matches6,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                dimension.asDimensionTypeRef(),
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                matches6,
                worldBlockPosition,
                value6);
    }

    @Deprecated
    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            Dimension dimension,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            boolean matches6,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6,
            boolean matches7) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                dimension.asDimensionTypeRef(),
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                matches6,
                worldBlockPosition,
                value6,
                matches7);
    }

    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            DimensionType dimensionType,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                (DimensionTypeRef) null,
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                worldBlockPosition,
                value6);
        this.dimensionTypeRef = dimensionType.asRef(this);
    }

    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            DimensionType dimensionType,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            boolean matches6,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                (DimensionTypeRef) null,
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                matches6,
                worldBlockPosition,
                value6);
        this.dimensionTypeRef = dimensionType.asRef(this);
    }

    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            DimensionType dimensionType,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            boolean matches6,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6,
            boolean matches7) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                dimensionType,
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                matches6,
                worldBlockPosition,
                value6,
                62,
                matches7);
    }

    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            DimensionType dimensionType,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            boolean matches6,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6,
            int value7,
            boolean matches7) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                (DimensionTypeRef) null,
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                matches6,
                worldBlockPosition,
                value6,
                value7,
                matches7);
        this.dimensionTypeRef = dimensionType.asRef(this);
    }

    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            DimensionTypeRef dimensionTypeRef,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                dimensionTypeRef,
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                false,
                matches4,
                matches5,
                worldBlockPosition,
                value6);
    }

    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            DimensionTypeRef dimensionTypeRef,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            boolean matches6,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                dimensionTypeRef,
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                matches6,
                worldBlockPosition,
                value6,
                false);
    }

    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            DimensionTypeRef dimensionTypeRef,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            boolean matches6,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6,
            boolean matches7) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                dimensionTypeRef,
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                matches6,
                worldBlockPosition,
                value6,
                62,
                matches7);
    }

    public WrapperPlayServerJoinGame(
            int value,
            boolean matches,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            List<String> values,
            NBTCompound nBTCompound,
            DimensionTypeRef dimensionTypeRef,
            Difficulty difficulty,
            String text,
            long value2,
            int value3,
            int value4,
            int value5,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            boolean matches5,
            boolean matches6,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer value6,
            int value7,
            boolean matches7) {
        this(
                value,
                matches,
                gameMode,
                gameMode2,
                values,
                nBTCompound,
                dimensionTypeRef,
                difficulty,
                text,
                value2,
                value3,
                value4,
                value5,
                matches2,
                matches3,
                matches4,
                matches5,
                matches6,
                worldBlockPosition,
                value6,
                value7,
                true,
                matches7);
    }

    public WrapperPlayServerJoinGame(
            int entityID,
            boolean hardcore,
            GameMode gameMode2,
            @Nullable GameMode gameMode3,
            List<String> worldNames,
            NBTCompound nBTCompound,
            DimensionTypeRef dimensionTypeRef2,
            Difficulty difficulty2,
            String worldName,
            long hashedSeed,
            int maxPlayers,
            int viewDistance,
            int simulationDistance,
            boolean reducedDebugInfo,
            boolean enableRespawnScreen,
            boolean limitedCrafting,
            boolean isDebug,
            boolean isFlat,
            WorldBlockPosition worldBlockPosition,
            @Nullable Integer portalCooldown,
            int seaLevel,
            boolean onlineMode,
            boolean enforcesSecureChat) {
        super(PacketType.Play.Server.JOIN_GAME);
        this.entityID = entityID;
        this.hardcore = hardcore;
        this.gameMode = gameMode2;
        this.previousGameMode = gameMode3;
        this.worldNames = worldNames;
        this.dimensionCodec = nBTCompound;
        this.dimensionTypeRef = dimensionTypeRef2;
        this.difficulty = difficulty2;
        this.worldName = worldName;
        this.hashedSeed = hashedSeed;
        this.maxPlayers = maxPlayers;
        this.viewDistance = viewDistance;
        this.simulationDistance = simulationDistance;
        this.reducedDebugInfo = reducedDebugInfo;
        this.enableRespawnScreen = enableRespawnScreen;
        this.limitedCrafting = limitedCrafting;
        this.isDebug = isDebug;
        this.isFlat = isFlat;
        this.lastDeathPosition = worldBlockPosition;
        this.portalCooldown = portalCooldown;
        this.seaLevel = seaLevel;
        this.onlineMode = onlineMode;
        this.enforcesSecureChat = enforcesSecureChat;
    }

    @Override
    public void read() {
        this.entityID = this.readInt();
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2);
        boolean newerThanOrEquals2 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19);
        boolean newerThanOrEquals3 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18);
        boolean newerThanOrEquals4 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2);
        boolean newerThanOrEquals5 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16);
        boolean newerThanOrEquals6 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15);
        boolean newerThanOrEquals7 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14);
        if (newerThanOrEquals4) {
            this.hardcore = this.readBoolean();
            if (!newerThanOrEquals) {
                this.gameMode = this.readGameMode();
            }
        } else {
            short value = this.readUnsignedByte();
            this.hardcore = (value & 8) == 8;
            this.gameMode = GameMode.getById(value & -9);
        }
        if (newerThanOrEquals5) {
            if (!newerThanOrEquals) {
                this.previousGameMode = this.readGameMode();
            }
            int value2 = this.readVarInt();
            this.worldNames = new ArrayList<>(value2);
            for (int index = 0; index < value2; index++) {
                this.worldNames.add(this.readString());
            }
            if (!newerThanOrEquals) {
                this.dimensionCodec = this.readNBT();
                this.dimensionTypeRef = DimensionTypeRef.read(this);
                this.worldName = this.readString();
            }
        } else {
            this.previousGameMode = this.gameMode;
            this.dimensionCodec = new NBTCompound();
            this.dimensionTypeRef = DimensionTypeRef.read(this);
            if (!newerThanOrEquals7) {
                this.difficulty = Difficulty.getById(this.readByte());
            }
        }
        if (newerThanOrEquals6 && !newerThanOrEquals) {
            this.hashedSeed = this.readLong();
        }
        if (newerThanOrEquals5) {
            this.maxPlayers = newerThanOrEquals4 ? this.readVarInt() : this.readUnsignedByte();
            this.viewDistance = this.readVarInt();
            if (newerThanOrEquals3) {
                this.simulationDistance = this.readVarInt();
            }
            this.reducedDebugInfo = this.readBoolean();
            this.enableRespawnScreen = this.readBoolean();
            if (newerThanOrEquals) {
                this.limitedCrafting = this.readBoolean();
                this.dimensionTypeRef = DimensionTypeRef.read(this);
                this.worldName = this.readString();
                this.hashedSeed = this.readLong();
                this.gameMode = this.readGameMode();
                if (this.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
                    int value3 = this.readVarInt();
                    this.previousGameMode = value3 == 0 ? null : GameMode.getById(value3 - 1);
                } else {
                    this.previousGameMode = this.readGameMode();
                }
            }
            this.isDebug = this.readBoolean();
            this.isFlat = this.readBoolean();
        } else {
            this.maxPlayers = this.readUnsignedByte();
            String text = this.readString(16);
            this.isFlat =
                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.DimensionType
                            .isFlat(text);
            this.isDebug =
                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.DimensionType
                            .isDebug(text);
            if (newerThanOrEquals7) {
                this.viewDistance = this.readVarInt();
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
                this.reducedDebugInfo = this.readBoolean();
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15)) {
                this.enableRespawnScreen = this.readBoolean();
            }
        }
        if (newerThanOrEquals2) {
            this.lastDeathPosition = this.readOptional(PacketWrapper::readWorldBlockPosition);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20)) {
            this.portalCooldown = this.readVarInt();
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                    this.seaLevel = this.readVarInt();
                }
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_2)) {
                    this.onlineMode = this.readBoolean();
                }
                this.enforcesSecureChat = this.readBoolean();
            }
        }
    }

    @Override
    public void write() {
        this.writeInt(this.entityID);
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2);
        boolean newerThanOrEquals2 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19);
        boolean newerThanOrEquals3 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18);
        boolean newerThanOrEquals4 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2);
        boolean newerThanOrEquals5 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16);
        boolean newerThanOrEquals6 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14);
        boolean newerThanOrEquals7 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15);
        if (newerThanOrEquals4) {
            this.writeBoolean(this.hardcore);
            if (!newerThanOrEquals) {
                this.writeGameMode(this.gameMode);
            }
        } else {
            int id = this.gameMode.getId();
            if (this.hardcore) {
                id |= 8;
            }
            this.writeByte(id);
        }
        if (newerThanOrEquals5) {
            if (this.previousGameMode == null) {
                this.previousGameMode = this.gameMode;
            }
            if (!newerThanOrEquals) {
                this.writeGameMode(this.previousGameMode);
            }
            this.writeVarInt(this.worldNames.size());
            for (String text : this.worldNames) {
                this.writeString(text);
            }
            if (!newerThanOrEquals) {
                this.writeNBT(this.dimensionCodec);
                DimensionTypeRef.write(this, this.dimensionTypeRef);
                this.writeString(this.worldName);
            }
        } else {
            this.previousGameMode = this.gameMode;
            DimensionTypeRef.write(this, this.dimensionTypeRef);
            if (!newerThanOrEquals6) {
                this.writeByte(this.difficulty.getId());
            }
        }
        if (newerThanOrEquals7 && !newerThanOrEquals) {
            this.writeLong(this.hashedSeed);
        }
        if (newerThanOrEquals5) {
            if (newerThanOrEquals4) {
                this.writeVarInt(this.maxPlayers);
            } else {
                this.writeByte(this.maxPlayers);
            }
            this.writeVarInt(this.viewDistance);
            if (newerThanOrEquals3) {
                this.writeVarInt(this.simulationDistance);
            }
            this.writeBoolean(this.reducedDebugInfo);
            this.writeBoolean(this.enableRespawnScreen);
            if (newerThanOrEquals) {
                this.writeBoolean(this.limitedCrafting);
                DimensionTypeRef.write(this, this.dimensionTypeRef);
                this.writeString(this.worldName);
                this.writeLong(this.hashedSeed);
                this.writeGameMode(this.gameMode);
                if (this.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
                    this.writeVarInt(
                            this.previousGameMode != null ? this.previousGameMode.getId() + 1 : 0);
                } else {
                    this.writeGameMode(this.previousGameMode);
                }
            }
            this.writeBoolean(this.isDebug);
            this.writeBoolean(this.isFlat);
        } else {
            this.writeByte(this.maxPlayers);
            String text2;
            if (this.isFlat) {
                text2 = WorldType.FLAT.getName();
            } else if (this.isDebug) {
                text2 = WorldType.DEBUG_ALL_BLOCK_STATES.getName();
            } else {
                text2 = WorldType.DEFAULT.getName();
            }
            this.writeString(text2, 16);
            if (newerThanOrEquals6) {
                this.writeVarInt(this.viewDistance);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
                this.writeBoolean(this.reducedDebugInfo);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15)) {
                this.writeBoolean(this.enableRespawnScreen);
            }
        }
        if (newerThanOrEquals2) {
            this.writeOptional(this.lastDeathPosition, PacketWrapper::writeWorldBlockPosition);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20)) {
            int value = this.portalCooldown != null ? this.portalCooldown : 0;
            this.writeVarInt(value);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeVarInt(this.seaLevel);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_2)) {
            this.writeBoolean(this.onlineMode);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            this.writeBoolean(this.enforcesSecureChat);
        }
    }

    public void copy(WrapperPlayServerJoinGame wrapperPlayServerJoinGame) {
        this.entityID = wrapperPlayServerJoinGame.entityID;
        this.hardcore = wrapperPlayServerJoinGame.hardcore;
        this.gameMode = wrapperPlayServerJoinGame.gameMode;
        this.previousGameMode = wrapperPlayServerJoinGame.previousGameMode;
        this.worldNames = wrapperPlayServerJoinGame.worldNames;
        this.dimensionCodec = wrapperPlayServerJoinGame.dimensionCodec;
        this.dimensionTypeRef = wrapperPlayServerJoinGame.dimensionTypeRef;
        this.difficulty = wrapperPlayServerJoinGame.difficulty;
        this.worldName = wrapperPlayServerJoinGame.worldName;
        this.hashedSeed = wrapperPlayServerJoinGame.hashedSeed;
        this.maxPlayers = wrapperPlayServerJoinGame.maxPlayers;
        this.viewDistance = wrapperPlayServerJoinGame.viewDistance;
        this.simulationDistance = wrapperPlayServerJoinGame.simulationDistance;
        this.reducedDebugInfo = wrapperPlayServerJoinGame.reducedDebugInfo;
        this.enableRespawnScreen = wrapperPlayServerJoinGame.enableRespawnScreen;
        this.limitedCrafting = wrapperPlayServerJoinGame.limitedCrafting;
        this.isDebug = wrapperPlayServerJoinGame.isDebug;
        this.isFlat = wrapperPlayServerJoinGame.isFlat;
        this.lastDeathPosition = wrapperPlayServerJoinGame.lastDeathPosition;
        this.portalCooldown = wrapperPlayServerJoinGame.portalCooldown;
        this.seaLevel = wrapperPlayServerJoinGame.seaLevel;
        this.onlineMode = wrapperPlayServerJoinGame.onlineMode;
        this.enforcesSecureChat = wrapperPlayServerJoinGame.enforcesSecureChat;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public boolean isHardcore() {
        return this.hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public void setGameMode(GameMode gameMode2) {
        this.gameMode = gameMode2;
    }

    @Nullable
    public GameMode getPreviousGameMode() {
        return this.previousGameMode;
    }

    public void setPreviousGameMode(@Nullable GameMode gameMode) {
        this.previousGameMode = gameMode;
    }

    public List<String> getWorldNames() {
        return this.worldNames;
    }

    public void setWorldNames(List<String> worldNames) {
        this.worldNames = worldNames;
    }

    public NBTCompound getDimensionCodec() {
        return this.dimensionCodec;
    }

    public void setDimensionCodec(NBTCompound nBTCompound) {
        this.dimensionCodec = nBTCompound;
    }

    public DimensionTypeRef getDimensionTypeRef() {
        return this.dimensionTypeRef;
    }

    public void setDimensionTypeRef(DimensionTypeRef dimensionTypeRef2) {
        this.dimensionTypeRef = dimensionTypeRef2;
    }

    public DimensionType getDimensionType() {
        IRegistry registry = this.replaceRegistry(DimensionTypes.getRegistry());
        return this.dimensionTypeRef.resolve(registry, this);
    }

    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionTypeRef = dimensionType.asRef(this);
    }

    @Deprecated
    public Dimension getDimension() {
        return Dimension.fromDimensionTypeRef(this.dimensionTypeRef);
    }

    @Deprecated
    public void setDimension(Dimension dimension) {
        this.dimensionTypeRef = dimension.asDimensionTypeRef();
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty difficulty2) {
        this.difficulty = difficulty2;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public long getHashedSeed() {
        return this.hashedSeed;
    }

    public void setHashedSeed(long hashedSeed) {
        this.hashedSeed = hashedSeed;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
    }

    public int getSimulationDistance() {
        return this.simulationDistance;
    }

    public void setSimulationDistance(int simulationDistance) {
        this.simulationDistance = simulationDistance;
    }

    public boolean isReducedDebugInfo() {
        return this.reducedDebugInfo;
    }

    public void setReducedDebugInfo(boolean reducedDebugInfo) {
        this.reducedDebugInfo = reducedDebugInfo;
    }

    public boolean isRespawnScreenEnabled() {
        return this.enableRespawnScreen;
    }

    public void setRespawnScreenEnabled(boolean enableRespawnScreen) {
        this.enableRespawnScreen = enableRespawnScreen;
    }

    public boolean isLimitedCrafting() {
        return this.limitedCrafting;
    }

    public void setLimitedCrafting(boolean limitedCrafting) {
        this.limitedCrafting = limitedCrafting;
    }

    public boolean isDebug() {
        return this.isDebug;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public boolean isFlat() {
        return this.isFlat;
    }

    public void setFlat(boolean isFlat) {
        this.isFlat = isFlat;
    }

    @Nullable
    public WorldBlockPosition getLastDeathPosition() {
        return this.lastDeathPosition;
    }

    public void setLastDeathPosition(@Nullable WorldBlockPosition worldBlockPosition) {
        this.lastDeathPosition = worldBlockPosition;
    }

    public Optional<Integer> getPortalCooldown() {
        return Optional.ofNullable(this.portalCooldown);
    }

    public void setPortalCooldown(int portalCooldown) {
        this.portalCooldown = portalCooldown;
    }

    public int getSeaLevel() {
        return this.seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public boolean isOnlineMode() {
        return this.onlineMode;
    }

    public void setOnlineMode(boolean onlineMode) {
        this.onlineMode = onlineMode;
    }

    public boolean isEnforcesSecureChat() {
        return this.enforcesSecureChat;
    }

    public void setEnforcesSecureChat(boolean enforcesSecureChat) {
        this.enforcesSecureChat = enforcesSecureChat;
    }
}
