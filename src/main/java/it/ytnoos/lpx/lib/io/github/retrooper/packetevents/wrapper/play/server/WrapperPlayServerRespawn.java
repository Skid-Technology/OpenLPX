package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.GameMode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Difficulty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Dimension;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.WorldBlockPosition;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.WorldType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionTypeRef;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayServerRespawn extends PacketWrapper<WrapperPlayServerRespawn> {

    public static final byte KEEP_NOTHING = 0;

    public static final byte KEEP_ATTRIBUTES = 1;

    public static final byte KEEP_ENTITY_DATA = 2;

    public static final byte KEEP_ALL_DATA = 3;

    static final int FALLBACK_SEA_LEVEL = 62;

    private DimensionTypeRef dimensionTypeRef;

    private Optional<String> worldName;

    private Difficulty difficulty;

    private long hashedSeed;

    private GameMode gameMode;

    @Nullable private GameMode previousGameMode;

    private boolean worldDebug;

    private boolean worldFlat;

    private byte keptData;

    private WorldBlockPosition lastDeathPosition;

    private Integer portalCooldown;

    private int seaLevel;

    private String levelType;

    public WrapperPlayServerRespawn(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Deprecated
    public WrapperPlayServerRespawn(
            Dimension dimension,
            @Nullable String text,
            Difficulty difficulty,
            long value,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            boolean matches,
            boolean matches2,
            boolean matches3,
            @Nullable ResourceLocation resourceLocation,
            @Nullable WorldBlockPosition worldBlockPosition,
            @Nullable Integer value2) {
        this(
                dimension.asDimensionTypeRef(),
                text,
                difficulty,
                value,
                gameMode,
                gameMode2,
                matches,
                matches2,
                matches3,
                resourceLocation,
                worldBlockPosition,
                value2);
    }

    @Deprecated
    public WrapperPlayServerRespawn(
            Dimension dimension,
            @Nullable String text,
            Difficulty difficulty,
            long value,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            boolean matches,
            boolean matches2,
            byte value2,
            @Nullable WorldBlockPosition worldBlockPosition,
            @Nullable Integer value3) {
        this(
                dimension.asDimensionTypeRef(),
                text,
                difficulty,
                value,
                gameMode,
                gameMode2,
                matches,
                matches2,
                value2,
                worldBlockPosition,
                value3);
    }

    public WrapperPlayServerRespawn(
            DimensionType dimensionType,
            @Nullable String text,
            Difficulty difficulty,
            long value,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            boolean matches,
            boolean matches2,
            boolean matches3,
            @Nullable ResourceLocation resourceLocation,
            @Nullable WorldBlockPosition worldBlockPosition,
            @Nullable Integer value2) {
        this(
                (DimensionTypeRef) null,
                text,
                difficulty,
                value,
                gameMode,
                gameMode2,
                matches,
                matches2,
                matches3,
                resourceLocation,
                worldBlockPosition,
                value2);
        this.dimensionTypeRef = dimensionType.asRef(this);
    }

    public WrapperPlayServerRespawn(
            DimensionType dimensionType,
            @Nullable String text,
            Difficulty difficulty,
            long value,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            boolean matches,
            boolean matches2,
            byte value2,
            @Nullable WorldBlockPosition worldBlockPosition,
            @Nullable Integer value3) {
        this(
                dimensionType,
                text,
                difficulty,
                value,
                gameMode,
                gameMode2,
                matches,
                matches2,
                value2,
                worldBlockPosition,
                value3,
                62);
    }

    public WrapperPlayServerRespawn(
            DimensionType dimensionType,
            @Nullable String text,
            Difficulty difficulty,
            long value,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            boolean matches,
            boolean matches2,
            byte value2,
            @Nullable WorldBlockPosition worldBlockPosition,
            @Nullable Integer value3,
            int value4) {
        this(
                (DimensionTypeRef) null,
                text,
                difficulty,
                value,
                gameMode,
                gameMode2,
                matches,
                matches2,
                value2,
                worldBlockPosition,
                value3,
                value4);
        this.dimensionTypeRef = dimensionType.asRef(this);
    }

    public WrapperPlayServerRespawn(
            DimensionTypeRef dimensionTypeRef,
            @Nullable String text,
            Difficulty difficulty,
            long value,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            boolean matches,
            boolean matches2,
            boolean matches3,
            @Nullable ResourceLocation resourceLocation,
            @Nullable WorldBlockPosition worldBlockPosition,
            @Nullable Integer value2) {
        this(
                dimensionTypeRef,
                text,
                difficulty,
                value,
                gameMode,
                gameMode2,
                matches,
                matches2,
                (byte) (matches3 ? 3 : 0),
                worldBlockPosition,
                value2);
    }

    public WrapperPlayServerRespawn(
            DimensionTypeRef dimensionTypeRef,
            @Nullable String text,
            Difficulty difficulty,
            long value,
            GameMode gameMode,
            @Nullable GameMode gameMode2,
            boolean matches,
            boolean matches2,
            byte value2,
            @Nullable WorldBlockPosition worldBlockPosition,
            @Nullable Integer value3) {
        this(
                dimensionTypeRef,
                text,
                difficulty,
                value,
                gameMode,
                gameMode2,
                matches,
                matches2,
                value2,
                worldBlockPosition,
                value3,
                62);
    }

    public WrapperPlayServerRespawn(
            DimensionTypeRef dimensionTypeRef2,
            @Nullable String text,
            Difficulty difficulty2,
            long hashedSeed,
            GameMode gameMode2,
            @Nullable GameMode gameMode3,
            boolean worldDebug,
            boolean worldFlat,
            byte keptData,
            @Nullable WorldBlockPosition worldBlockPosition,
            @Nullable Integer portalCooldown,
            int seaLevel) {
        super(PacketType.Play.Server.RESPAWN);
        this.dimensionTypeRef = dimensionTypeRef2;
        this.setWorldName(text);
        this.difficulty = difficulty2;
        this.hashedSeed = hashedSeed;
        this.gameMode = gameMode2;
        this.previousGameMode = gameMode3;
        this.worldDebug = worldDebug;
        this.worldFlat = worldFlat;
        this.keptData = keptData;
        this.lastDeathPosition = worldBlockPosition;
        this.portalCooldown = portalCooldown;
        this.seaLevel = seaLevel;
    }

    @Override
    public void read() {
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14);
        boolean newerThanOrEquals2 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15);
        boolean newerThanOrEquals3 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16);
        boolean newerThanOrEquals4 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19);
        boolean newerThanOrEquals5 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3);
        boolean newerThanOrEquals6 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2);
        this.dimensionTypeRef = DimensionTypeRef.read(this);
        if (newerThanOrEquals3) {
            this.worldName = Optional.of(this.readString());
            this.hashedSeed = this.readLong();
            if (newerThanOrEquals6) {
                this.gameMode = this.readGameMode();
            } else {
                this.gameMode = GameMode.getById(this.readUnsignedByte());
            }
            if (this.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
                int value = this.readVarInt();
                this.previousGameMode = value == 0 ? null : GameMode.getById(value - 1);
            } else {
                this.previousGameMode = this.readGameMode();
            }
            this.worldDebug = this.readBoolean();
            this.worldFlat = this.readBoolean();
            if (newerThanOrEquals5) {
                if (!newerThanOrEquals6) {
                    this.keptData = this.readByte();
                }
            } else {
                this.keptData = (byte) (this.readBoolean() ? 3 : 2);
            }
            if (newerThanOrEquals4) {
                this.lastDeathPosition = this.readOptional(PacketWrapper::readWorldBlockPosition);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20)) {
                this.portalCooldown = this.readVarInt();
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                this.seaLevel = this.readVarInt();
            }
            if (newerThanOrEquals6) {
                this.keptData = this.readByte();
            }
        } else {
            this.worldName = Optional.empty();
            this.hashedSeed = 0L;
            if (newerThanOrEquals2) {
                this.hashedSeed = this.readLong();
            } else if (!newerThanOrEquals) {
                this.difficulty = Difficulty.getById(this.readByte());
            }
            this.gameMode = GameMode.getById(this.readByte());
            this.levelType = this.readString(16);
            this.worldFlat =
                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.DimensionType
                            .isFlat(this.levelType);
            this.worldDebug =
                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.DimensionType
                            .isDebug(this.levelType);
        }
    }

    @Override
    public void write() {
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14);
        boolean newerThanOrEquals2 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15);
        boolean newerThanOrEquals3 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16);
        boolean newerThanOrEquals4 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19);
        boolean newerThanOrEquals5 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3);
        boolean newerThanOrEquals6 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2);
        DimensionTypeRef.write(this, this.dimensionTypeRef);
        if (newerThanOrEquals3) {
            this.writeString(this.worldName.orElse(""));
            this.writeLong(this.hashedSeed);
            this.writeGameMode(this.gameMode);
            if (this.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
                this.writeVarInt(
                        this.previousGameMode != null ? this.previousGameMode.getId() + 1 : 0);
            } else {
                this.writeGameMode(this.previousGameMode);
            }
            this.writeBoolean(this.worldDebug);
            this.writeBoolean(this.worldFlat);
            if (newerThanOrEquals5) {
                if (!newerThanOrEquals6) {
                    this.writeByte(this.keptData);
                }
            } else {
                this.writeBoolean((this.keptData & 1) != 0);
            }
            if (newerThanOrEquals4) {
                this.writeOptional(this.lastDeathPosition, PacketWrapper::writeWorldBlockPosition);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20)) {
                int value = this.portalCooldown != null ? this.portalCooldown : 0;
                this.writeVarInt(value);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                this.writeVarInt(this.seaLevel);
            }
            if (newerThanOrEquals6) {
                this.writeByte(this.keptData);
            }
        } else {
            if (newerThanOrEquals2) {
                this.writeLong(this.hashedSeed);
            } else if (!newerThanOrEquals) {
                int value2 =
                        this.difficulty == null
                                ? Difficulty.NORMAL.getId()
                                : this.difficulty.getId();
                this.writeByte(value2);
            }
            this.writeByte(this.gameMode.ordinal());
            if (this.worldFlat) {
                this.writeString(WorldType.FLAT.getName());
            } else if (this.worldDebug) {
                this.writeString(WorldType.DEBUG_ALL_BLOCK_STATES.getName());
            } else {
                this.writeString(
                        this.levelType == null ? WorldType.DEFAULT.getName() : this.levelType, 16);
            }
        }
    }

    public void copy(WrapperPlayServerRespawn wrapperPlayServerRespawn) {
        this.dimensionTypeRef = wrapperPlayServerRespawn.dimensionTypeRef;
        this.worldName = wrapperPlayServerRespawn.worldName;
        this.difficulty = wrapperPlayServerRespawn.difficulty;
        this.hashedSeed = wrapperPlayServerRespawn.hashedSeed;
        this.gameMode = wrapperPlayServerRespawn.gameMode;
        this.previousGameMode = wrapperPlayServerRespawn.previousGameMode;
        this.worldDebug = wrapperPlayServerRespawn.worldDebug;
        this.worldFlat = wrapperPlayServerRespawn.worldFlat;
        this.keptData = wrapperPlayServerRespawn.keptData;
        this.lastDeathPosition = wrapperPlayServerRespawn.lastDeathPosition;
        this.portalCooldown = wrapperPlayServerRespawn.portalCooldown;
        this.seaLevel = wrapperPlayServerRespawn.seaLevel;
        this.levelType = wrapperPlayServerRespawn.levelType;
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

    public Optional<String> getWorldName() {
        return this.worldName;
    }

    public void setWorldName(@Nullable String text) {
        this.worldName = Optional.ofNullable(text);
    }

    @Nullable
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty difficulty2) {
        this.difficulty = difficulty2;
    }

    public long getHashedSeed() {
        return this.hashedSeed;
    }

    public void setHashedSeed(long hashedSeed) {
        this.hashedSeed = hashedSeed;
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

    public boolean isWorldDebug() {
        return this.worldDebug;
    }

    public void setWorldDebug(boolean worldDebug) {
        this.worldDebug = worldDebug;
    }

    public boolean isWorldFlat() {
        return this.worldFlat;
    }

    public void setWorldFlat(boolean worldFlat) {
        this.worldFlat = worldFlat;
    }

    public boolean isKeepingAllPlayerData() {
        return (this.keptData & 1) != 0;
    }

    public void setKeepingAllPlayerData(boolean matches) {
        this.keptData = (byte) (matches ? 3 : 2);
    }

    public byte getKeptData() {
        return this.keptData;
    }

    public void setKeptData(byte keptData) {
        this.keptData = keptData;
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
}
