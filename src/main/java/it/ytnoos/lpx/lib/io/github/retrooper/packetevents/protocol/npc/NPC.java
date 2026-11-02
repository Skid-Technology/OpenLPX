package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.npc;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.Equipment;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.GameMode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.TextureProperty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.UserProfile;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEquipment;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityHeadLook;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityRelativeMove;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityRelativeMoveAndRotation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityRotation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityTeleport;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoRemove;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnPlayer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class NPC {

    private final int id;

    private final UserProfile profile;

    private GameMode gamemode;

    private Component tabName;

    private NamedTextColor nameColor;

    private Component prefixName;

    private Component suffixName;

    private String teamName;

    private int displayPing = 0;

    private Location location = new Location(0.0, 0.0, 0.0, 0.0F, 0.0F);

    private ItemStack mainHand = null;

    private ItemStack offHand = null;

    private ItemStack helmet = null;

    private ItemStack chestPlate = null;

    private ItemStack leggings = null;

    private ItemStack boots = null;

    private final Set<Object> channels = new HashSet<>();

    public NPC(
            UserProfile userProfile,
            int id,
            GameMode gameMode,
            @Nullable Component component,
            @Nullable NamedTextColor namedTextColor,
            @Nullable Component component2,
            @Nullable Component component3) {
        this.profile = userProfile;
        this.id = id;
        this.gamemode = gameMode;
        this.tabName = component;
        this.nameColor = namedTextColor;
        this.prefixName = component2;
        this.suffixName = component3;
        this.teamName = "npc-" + this.id;
    }

    public NPC(UserProfile userProfile, int value, @Nullable Component component) {
        this(userProfile, value, GameMode.SURVIVAL, component, null, null, null);
    }

    public NPC(UserProfile userProfile, int value) {
        this(userProfile, value, null);
    }

    public boolean hasSpawned(Object result) {
        return this.channels.contains(result);
    }

    public void spawn(Object result) {
        if (!this.hasSpawned(result)) {
            PacketWrapper packetWrapper;
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
                packetWrapper =
                        new WrapperPlayServerPlayerInfoUpdate(
                                WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER,
                                this.getModernPlayerInfoData());
            } else {
                packetWrapper =
                        new WrapperPlayServerPlayerInfo(
                                WrapperPlayServerPlayerInfo.Action.ADD_PLAYER,
                                this.getLegacyPlayerInfoData());
            }
            PacketEvents.getAPI().getProtocolManager().sendPacket(result, packetWrapper);
            PacketWrapper packetWrapper2;
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
                packetWrapper2 =
                        new WrapperPlayServerSpawnEntity(
                                this.getId(),
                                this.getProfile().getUUID(),
                                EntityTypes.PLAYER,
                                this.getLocation(),
                                this.getLocation().getYaw(),
                                0,
                                null);
            } else {
                packetWrapper2 =
                        new WrapperPlayServerSpawnPlayer(
                                this.getId(), this.getProfile().getUUID(), this.getLocation());
            }
            PacketEvents.getAPI().getProtocolManager().sendPacket(result, packetWrapper2);
            if (this.getNameColor() != null
                    || this.getPrefixName() != null
                    || this.getSuffixName() != null) {
                PacketEvents.getAPI()
                        .getProtocolManager()
                        .sendPacket(result, this.generateTeamsData());
            }
            this.channels.add(result);
        }
    }

    public void despawn(Object result) {
        if (this.hasSpawned(result)) {
            WrapperPlayServerDestroyEntities wrapperPlayServerDestroyEntities =
                    new WrapperPlayServerDestroyEntities(this.getId());
            PacketEvents.getAPI()
                    .getProtocolManager()
                    .sendPacket(result, wrapperPlayServerDestroyEntities);
            this.channels.remove(result);
        }
    }

    public void despawnAll() {
        for (Object result : this.channels) {
            WrapperPlayServerDestroyEntities wrapperPlayServerDestroyEntities =
                    new WrapperPlayServerDestroyEntities(this.getId());
            PacketEvents.getAPI()
                    .getProtocolManager()
                    .sendPacket(result, wrapperPlayServerDestroyEntities);
        }
        this.channels.clear();
    }

    public void teleport(Location location) {
        this.setLocation(location);
        for (Object result : this.channels) {
            WrapperPlayServerEntityTeleport wrapperPlayServerEntityTeleport =
                    new WrapperPlayServerEntityTeleport(this.getId(), location, true);
            PacketEvents.getAPI()
                    .getProtocolManager()
                    .sendPacket(result, wrapperPlayServerEntityTeleport);
        }
    }

    public void updateLocation(Location location) {
        Location location2 = this.getLocation();
        this.setLocation(location);
        double value = Math.abs(location.getPosition().getX() - location2.getPosition().getX());
        double value2 = Math.abs(location.getPosition().getY() - location2.getPosition().getY());
        double value3 = Math.abs(location.getPosition().getZ() - location2.getPosition().getZ());
        boolean matches = value > 8.0 || value2 > 8.0 || value3 > 8.0;
        for (Object result : this.channels) {
            if (matches) {
                WrapperPlayServerEntityTeleport wrapperPlayServerEntityTeleport =
                        new WrapperPlayServerEntityTeleport(this.getId(), location, true);
                PacketEvents.getAPI()
                        .getProtocolManager()
                        .sendPacket(result, wrapperPlayServerEntityTeleport);
            } else {
                boolean matches2 =
                        location.getYaw() != location2.getYaw()
                                || location.getPitch() != location2.getPitch();
                boolean matches3;
                double value4 =
                        (matches3 =
                                        location.getPosition().getX()
                                                        != location2.getPosition().getX()
                                                || location.getPosition().getY()
                                                        != location2.getPosition().getY()
                                                || location.getPosition().getZ()
                                                        != location2.getPosition().getZ())
                                ? location.getPosition().getX() - location2.getPosition().getX()
                                : 0.0;
                double value5 =
                        matches3
                                ? location.getPosition().getY() - location2.getPosition().getY()
                                : 0.0;
                double value6 =
                        matches3
                                ? location.getPosition().getZ() - location2.getPosition().getZ()
                                : 0.0;
                if (matches3 && matches2) {
                    WrapperPlayServerEntityRelativeMoveAndRotation
                            wrapperPlayServerEntityRelativeMoveAndRotation =
                                    new WrapperPlayServerEntityRelativeMoveAndRotation(
                                            this.getId(),
                                            value4,
                                            value5,
                                            value6,
                                            location.getYaw(),
                                            location.getPitch(),
                                            true);
                    PacketEvents.getAPI()
                            .getProtocolManager()
                            .sendPacket(result, wrapperPlayServerEntityRelativeMoveAndRotation);
                    WrapperPlayServerEntityHeadLook wrapperPlayServerEntityHeadLook =
                            new WrapperPlayServerEntityHeadLook(this.getId(), location.getYaw());
                    PacketEvents.getAPI()
                            .getProtocolManager()
                            .sendPacket(result, wrapperPlayServerEntityHeadLook);
                } else if (matches3) {
                    WrapperPlayServerEntityRelativeMove wrapperPlayServerEntityRelativeMove =
                            new WrapperPlayServerEntityRelativeMove(
                                    this.getId(), value4, value5, value6, true);
                    PacketEvents.getAPI()
                            .getProtocolManager()
                            .sendPacket(result, wrapperPlayServerEntityRelativeMove);
                } else if (matches2) {
                    WrapperPlayServerEntityRotation wrapperPlayServerEntityRotation =
                            new WrapperPlayServerEntityRotation(
                                    this.getId(), location.getYaw(), location.getPitch(), true);
                    PacketEvents.getAPI()
                            .getProtocolManager()
                            .sendPacket(result, wrapperPlayServerEntityRotation);
                    WrapperPlayServerEntityHeadLook wrapperPlayServerEntityHeadLook2 =
                            new WrapperPlayServerEntityHeadLook(this.getId(), location.getYaw());
                    PacketEvents.getAPI()
                            .getProtocolManager()
                            .sendPacket(result, wrapperPlayServerEntityHeadLook2);
                }
            }
        }
    }

    public void updateRotation(float value, float value2) {
        this.getLocation().setYaw(value);
        this.getLocation().setPitch(value2);
        for (Object result : this.channels) {
            WrapperPlayServerEntityRotation wrapperPlayServerEntityRotation =
                    new WrapperPlayServerEntityRotation(this.getId(), value, value2, true);
            PacketEvents.getAPI()
                    .getProtocolManager()
                    .sendPacket(result, wrapperPlayServerEntityRotation);
            WrapperPlayServerEntityHeadLook wrapperPlayServerEntityHeadLook =
                    new WrapperPlayServerEntityHeadLook(this.getId(), value);
            PacketEvents.getAPI()
                    .getProtocolManager()
                    .sendPacket(result, wrapperPlayServerEntityHeadLook);
        }
    }

    public void updateTabPing(int value) {
        this.setDisplayPing(value);
        for (Object result : this.channels) {
            PacketWrapper packetWrapper;
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
                packetWrapper =
                        new WrapperPlayServerPlayerInfoUpdate(
                                WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LATENCY,
                                this.getModernPlayerInfoData());
            } else {
                packetWrapper =
                        new WrapperPlayServerPlayerInfo(
                                WrapperPlayServerPlayerInfo.Action.UPDATE_LATENCY,
                                this.getLegacyPlayerInfoData());
            }
            PacketEvents.getAPI().getProtocolManager().sendPacket(result, packetWrapper);
        }
    }

    public void updateGameMode(GameMode gameMode) {
        this.setGameMode(gameMode);
        for (Object result : this.channels) {
            PacketWrapper packetWrapper;
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
                packetWrapper =
                        new WrapperPlayServerPlayerInfoUpdate(
                                WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_GAME_MODE,
                                this.getModernPlayerInfoData());
            } else {
                packetWrapper =
                        new WrapperPlayServerPlayerInfo(
                                WrapperPlayServerPlayerInfo.Action.UPDATE_GAME_MODE,
                                this.getLegacyPlayerInfoData());
            }
            PacketEvents.getAPI().getProtocolManager().sendPacket(result, packetWrapper);
        }
    }

    public void changeSkin(UUID uUID, List<TextureProperty> values) {
        for (Object result : this.channels) {
            PacketWrapper packetWrapper;
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
                packetWrapper = new WrapperPlayServerPlayerInfoRemove(this.getProfile().getUUID());
            } else {
                packetWrapper =
                        new WrapperPlayServerPlayerInfo(
                                WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER,
                                this.getLegacyPlayerInfoData());
            }
            PacketEvents.getAPI().getProtocolManager().sendPacket(result, packetWrapper);
            WrapperPlayServerDestroyEntities wrapperPlayServerDestroyEntities =
                    new WrapperPlayServerDestroyEntities(this.getId());
            PacketEvents.getAPI()
                    .getProtocolManager()
                    .sendPacket(result, wrapperPlayServerDestroyEntities);
            this.getProfile().setTextureProperties(values);
            this.getProfile().setUUID(uUID);
            PacketWrapper packetWrapper2;
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
                packetWrapper2 =
                        new WrapperPlayServerPlayerInfoUpdate(
                                WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER,
                                this.getModernPlayerInfoData());
            } else {
                packetWrapper2 =
                        new WrapperPlayServerPlayerInfo(
                                WrapperPlayServerPlayerInfo.Action.ADD_PLAYER,
                                this.getLegacyPlayerInfoData());
            }
            PacketEvents.getAPI().getProtocolManager().sendPacket(result, packetWrapper2);
            PacketWrapper packetWrapper3;
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
                packetWrapper3 =
                        new WrapperPlayServerSpawnEntity(
                                this.getId(),
                                this.getProfile().getUUID(),
                                EntityTypes.PLAYER,
                                this.getLocation(),
                                this.getLocation().getYaw(),
                                0,
                                null);
            } else {
                packetWrapper3 =
                        new WrapperPlayServerSpawnPlayer(
                                this.getId(), this.getProfile().getUUID(), this.getLocation());
            }
            PacketEvents.getAPI().getProtocolManager().sendPacket(result, packetWrapper3);
        }
    }

    public void updateNameTag() {
        for (Object result : this.channels) {
            WrapperPlayServerTeams wrapperPlayServerTeams =
                    new WrapperPlayServerTeams(
                            this.teamName,
                            WrapperPlayServerTeams.TeamMode.REMOVE,
                            Optional.empty());
            PacketEvents.getAPI().getProtocolManager().sendPacket(result, wrapperPlayServerTeams);
            if (this.getNameColor() != null
                    || this.getPrefixName() != null
                    || this.getSuffixName() != null) {
                PacketEvents.getAPI()
                        .getProtocolManager()
                        .sendPacket(result, this.generateTeamsData());
            }
        }
    }

    public void updateEquipment() {
        for (Object result : this.channels) {
            ArrayList<Equipment> values = new ArrayList<>();
            ItemStack itemStack;
            if ((itemStack = this.getMainHand()) == null) {
                itemStack = ItemStack.EMPTY;
            }
            values.add(new Equipment(EquipmentSlot.MAIN_HAND, itemStack));
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_9)) {
                ItemStack itemStack2;
                if ((itemStack2 = this.getOffHand()) == null) {
                    itemStack2 = ItemStack.EMPTY;
                }
                values.add(new Equipment(EquipmentSlot.OFF_HAND, itemStack2));
            }
            ItemStack itemStack3;
            if ((itemStack3 = this.getHelmet()) == null) {
                itemStack3 = ItemStack.EMPTY;
            }
            values.add(new Equipment(EquipmentSlot.HELMET, itemStack3));
            ItemStack itemStack4;
            if ((itemStack4 = this.getChestplate()) == null) {
                itemStack4 = ItemStack.EMPTY;
            }
            values.add(new Equipment(EquipmentSlot.CHEST_PLATE, itemStack4));
            ItemStack itemStack5;
            if ((itemStack5 = this.getLeggings()) == null) {
                itemStack5 = ItemStack.EMPTY;
            }
            values.add(new Equipment(EquipmentSlot.LEGGINGS, itemStack5));
            ItemStack itemStack6;
            if ((itemStack6 = this.getBoots()) == null) {
                itemStack6 = ItemStack.EMPTY;
            }
            values.add(new Equipment(EquipmentSlot.BOOTS, itemStack6));
            WrapperPlayServerEntityEquipment wrapperPlayServerEntityEquipment =
                    new WrapperPlayServerEntityEquipment(this.getId(), values);
            PacketEvents.getAPI()
                    .getProtocolManager()
                    .sendPacket(result, wrapperPlayServerEntityEquipment);
        }
    }

    public ItemStack getMainHand() {
        return this.mainHand;
    }

    public void setMainHand(ItemStack itemStack) {
        this.mainHand = itemStack;
    }

    public ItemStack getOffHand() {
        return this.offHand;
    }

    public void setOffHand(ItemStack itemStack) {
        this.offHand = itemStack;
    }

    public ItemStack getHelmet() {
        return this.helmet;
    }

    public void setHelmet(ItemStack itemStack) {
        this.helmet = itemStack;
    }

    public ItemStack getChestplate() {
        return this.chestPlate;
    }

    public void setChestplate(ItemStack itemStack) {
        this.chestPlate = itemStack;
    }

    public ItemStack getLeggings() {
        return this.leggings;
    }

    public void setLeggings(ItemStack itemStack) {
        this.leggings = itemStack;
    }

    public ItemStack getBoots() {
        return this.boots;
    }

    public void setBoots(ItemStack itemStack) {
        this.boots = itemStack;
    }

    @Nullable
    public NamedTextColor getNameColor() {
        return this.nameColor;
    }

    public void setNameColor(@Nullable NamedTextColor namedTextColor) {
        this.nameColor = namedTextColor;
    }

    @Nullable
    public Component getPrefixName() {
        return this.prefixName;
    }

    public void setPrefixName(@Nullable Component component) {
        this.prefixName = component;
    }

    @Nullable
    public Component getSuffixName() {
        return this.suffixName;
    }

    public void setSuffixName(@Nullable Component component) {
        this.suffixName = component;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isOlderThan(ServerVersion.V_1_18)) {
            this.teamName = teamName.substring(0, 16);
        } else {
            this.teamName = teamName;
        }
    }

    @Nullable
    public Component getTabName() {
        return this.tabName;
    }

    public void setTabName(@Nullable Component component) {
        this.tabName = component;
    }

    public int getId() {
        return this.id;
    }

    public UserProfile getProfile() {
        return this.profile;
    }

    public GameMode getGameMode() {
        return this.gamemode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gamemode = gameMode;
    }

    private WrapperPlayServerTeams generateTeamsData() {
        return new WrapperPlayServerTeams(
                this.teamName,
                WrapperPlayServerTeams.TeamMode.CREATE,
                Optional.of(
                        new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                                Component.text(this.teamName),
                                this.prefixName,
                                this.suffixName,
                                WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                                WrapperPlayServerTeams.CollisionRule.ALWAYS,
                                this.nameColor,
                                WrapperPlayServerTeams.OptionData.NONE)),
                this.getProfile().getName());
    }

    public WrapperPlayServerPlayerInfo.PlayerData getLegacyPlayerInfoData() {
        return new WrapperPlayServerPlayerInfo.PlayerData(
                this.getTabName(), this.getProfile(), this.getGameMode(), this.getDisplayPing());
    }

    public WrapperPlayServerPlayerInfoUpdate.PlayerInfo getModernPlayerInfoData() {
        return new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                this.getProfile(),
                true,
                this.getDisplayPing(),
                this.getGameMode(),
                this.getTabName(),
                null);
    }

    public int getDisplayPing() {
        return this.displayPing;
    }

    public void setDisplayPing(int displayPing) {
        this.displayPing = displayPing;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location2) {
        this.location = location2;
    }

    public Set<Object> getChannels() {
        return this.channels;
    }
}
