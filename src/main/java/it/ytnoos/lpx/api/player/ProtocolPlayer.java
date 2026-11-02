package it.ytnoos.lpx.api.player;

import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.check.CheckResultInfo;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.protocol.CheckContainer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ProtocolPlayer {

    Optional<Player> getPlayer();

    User getUser();

    String getUsername();

    UUID getUniqueId();

    PrinterData getPrinterData();

    boolean isBedrock();

    void setBedrock(boolean matches);

    boolean isProcessingPayload();

    void setProcessingPayload(boolean matches);

    Map<Object, CheckContainer<?>> getChecks();

    CheckContainer<?> getChecks(Object result);

    boolean hasAnyCheck(Object result, boolean matches);

    CheckResultInfo handleChecks(Object result, Object result2);

    CheckResultInfo handleChecks(Object result, Object result2, boolean matches);

    <T> Check<T> getCheck(Object result, CheckType checkType, LPX lPX);

    ReceivedPacket getLastReceivedPacket();

    long getLastAlert();

    long getLastPunish();

    long getLastTeleport();

    void updateLastAlert();

    long getLastPortalTeleport();

    long getLastShears();

    long getLastContainerInteract();

    void setLastReceivedPacket(ReceivedPacket receivedPacket);

    void updateLastPunish();

    void updateLastTeleport();

    void updateLastPortalTeleport();

    void updateLastShears();

    void updateLastContainerInteract();

    long incrementPacketCount();

    long getPacketCount();

    long getLastKeepAliveSent();

    void setLastKeepAliveSent(long value);

    double getPlayerPing();

    void setPlayerPing(double value);

    void flag(Check<?> check, Object result, FlagInfo... flagInfo);

    void punish(Check<?> check);

    void kick(String text);

    boolean isKicked();

    boolean ignoreChecks();

    boolean hasExemptPermission();

    default boolean usesComponents() {
        return this.getUser().getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_20_5);
    }

    default boolean usesHashedStacks() {
        return this.getUser().getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_21_5);
    }

    default boolean serverUsesHashedStacks() {
        return PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isNewerThanOrEquals(ServerVersion.V_1_21_5);
    }

    default boolean isLegacyClientOnLegacyServer() {
        return !this.usesHashedStacks() && !this.serverUsesHashedStacks();
    }

    default boolean isLegacyClientOnHashedServer() {
        return !this.usesHashedStacks() && this.serverUsesHashedStacks();
    }

    default boolean isHashedClientOnLegacyServer() {
        return this.usesHashedStacks() && !this.serverUsesHashedStacks();
    }

    default boolean isHashedClientOnHashedServer() {
        return this.usesHashedStacks() && this.serverUsesHashedStacks();
    }

    default double itemFloodMultiplier() {
        if (this.isLegacyClientOnHashedServer()) {
            return 1.5;
        } else {
            return this.isHashedClientOnHashedServer() ? 0.5 : 1.0;
        }
    }

    LPX lpx();
}
