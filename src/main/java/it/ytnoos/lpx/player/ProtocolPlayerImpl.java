package it.ytnoos.lpx.player;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alerts.AlertManager;
import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.CheckResultInfo;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.check.abstraction.CheckManager;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.player.PrinterData;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.CheckContainer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.utils.TimeUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProtocolPlayerImpl implements ProtocolPlayer {

    private final LPXBukkit lpx;

    private final Map<Object, CheckContainer<?>> checks;

    private final User user;

    private final PrinterData printerData;

    @Nullable private Player player;

    private boolean enabled = false;

    private boolean enabled2 = false;

    private boolean enabled3 = false;

    private boolean enabled4 = false;

    private ReceivedPacket receivedPacket;

    private long time;

    private long time2;

    private long lastTeleport;

    private long time3;

    private long time4;

    private long time5;

    private long time6 = 0L;

    private long time7 = 0L;

    private double value = 0.0;

    private final AtomicBoolean atomicBoolean;

    private volatile long time8;

    private static final String TEXT = "lpx.bypass";

    public ProtocolPlayerImpl(LPXBukkit lPXBukkit, User user2) {
        AtomicBoolean atomicBoolean2 = new AtomicBoolean();
        this.atomicBoolean = atomicBoolean2;
        this.lpx = lPXBukkit;
        this.user = user2;
        this.checks = lPXBukkit.getCheckManager().createPlayerChecks(this);
        ProtocolPlayerImpl protocolPlayerImpl = this;
        PrinterDataImpl printerDataImpl = new PrinterDataImpl(lPXBukkit, protocolPlayerImpl);
        this.printerData = printerDataImpl;
    }

    public Optional<Player> getPlayer() {
        if (this.player != null) {
            return Optional.of(this.player);
        }
        UUID uUID = this.user.getUUID();
        if (uUID == null) {
            return Optional.empty();
        }
        Player player2 = Bukkit.getPlayer(uUID);
        if (player2 == null) {
            return Optional.empty();
        }
        this.player = player2;
        this.enabled = player2.hasPermission(TEXT);
        return Optional.of(player2);
    }

    public User getUser() {
        return this.user;
    }

    public String getUsername() {
        return this.user.getName();
    }

    public UUID getUniqueId() {
        return this.user.getUUID();
    }

    public PrinterData getPrinterData() {
        return this.printerData;
    }

    public boolean isBedrock() {
        return this.enabled3;
    }

    public void setBedrock(boolean enabled3) {
        this.enabled3 = enabled3;
    }

    public boolean isProcessingPayload() {
        return this.enabled4;
    }

    public void setProcessingPayload(boolean enabled4) {
        this.enabled4 = enabled4;
    }

    public LPXBukkit getLpx() {
        return this.lpx;
    }

    public Map<Object, CheckContainer<?>> getChecks() {
        return this.checks;
    }

    public CheckContainer<?> getChecks(Object result) {
        CheckContainer checkContainer = this.checks.get(result);
        return checkContainer != null ? checkContainer : CheckManager.EMPTY_CONTAINER;
    }

    public boolean hasAnyCheck(Object result, boolean matches) {
        Iterator<? extends Check<?>> iterator2 = this.getChecks(result).iterator();
        while (iterator2.hasNext()) {
            if (((Check) iterator2.next()).isPreVia() == matches) {
                return true;
            }
        }
        return false;
    }

    public CheckResultInfo handleChecks(Object result, Object result2) {
        return this.handleChecks(result, result2, false);
    }

    public CheckResultInfo handleChecks(Object result2, Object result3, boolean matches) {
        CheckResultInfo checkResultInfo = new CheckResultInfo(CheckResult.ALLOW);
        CheckResultInfo checkResultInfo2 = checkResultInfo;
        for (Check check : this.getChecks(result2)) {
            if (check.isPreVia() == matches) {
                CheckResult checkResult = check.handle(result3).merge(checkResultInfo2.result());
                if (checkResult != checkResultInfo2.result()) {
                    CheckResultInfo checkResultInfo3 = new CheckResultInfo(checkResult, check);
                    checkResultInfo2 = checkResultInfo3;
                }
                if (checkResult == CheckResult.DENY) {
                    return checkResultInfo2;
                }
            }
        }
        return checkResultInfo2;
    }

    public <T> Check<T> getCheck(Object result, CheckType checkType, LPX lPX) {
        for (Check check : this.checks.get(result)) {
            if (check.getType() == checkType) {
                return check;
            }
        }
        return null;
    }

    public ReceivedPacket getLastReceivedPacket() {
        return this.receivedPacket;
    }

    public long getLastAlert() {
        return this.time;
    }

    public long getLastPunish() {
        return this.time2;
    }

    public long getLastTeleport() {
        return this.lastTeleport;
    }

    public long getLastPortalTeleport() {
        return this.time3;
    }

    public long getLastShears() {
        return this.time4;
    }

    public long getLastContainerInteract() {
        return this.time5;
    }

    public void setLastReceivedPacket(ReceivedPacket receivedPacket2) {
        this.receivedPacket = receivedPacket2;
    }

    public void updateLastPunish() {
        this.time2 = TimeUtils.current();
    }

    public boolean isAtomicBoolean() {
        return this.atomicBoolean.compareAndSet(false, true);
    }

    public long getTime8() {
        return this.time8;
    }

    public void handleEnabled(boolean matches) {
        if (matches) {
            this.time8 = TimeUtils.current();
        }
        this.atomicBoolean.set(false);
    }

    public void updateLastAlert() {
        this.time = TimeUtils.current();
    }

    public void updateLastTeleport() {
        this.lastTeleport = TimeUtils.current();
    }

    public void updateLastPortalTeleport() {
        this.time3 = TimeUtils.current();
    }

    public void updateLastShears() {
        this.time4 = TimeUtils.current();
    }

    public void updateLastContainerInteract() {
        this.time5 = TimeUtils.current();
    }

    public long incrementPacketCount() {
        ProtocolPlayerImpl protocolPlayerImpl = this;
        long value = protocolPlayerImpl.time6 + 1L;
        protocolPlayerImpl.time6 = value;
        return value;
    }

    public long getPacketCount() {
        return this.time6;
    }

    public long getLastKeepAliveSent() {
        return this.time7;
    }

    public void setLastKeepAliveSent(long time7) {
        this.time7 = time7;
    }

    public double getPlayerPing() {
        return this.value;
    }

    public void setPlayerPing(double value) {
        this.value = value;
    }

    public void flag(Check<?> check, Object result, FlagInfo... flagInfo) {
        LPXBukkit lPXBukkit = this.lpx;
        AlertManager alertManager = lPXBukkit.getAlertManager();
        FlagInfo[] flagInfos = ((FlagInfo[]) flagInfo);
        Check check2 = ((Check) check);
        ProtocolPlayerImpl protocolPlayerImpl = this;
        ProtocolPlayer protocolPlayer = ((ProtocolPlayer) protocolPlayerImpl);
        alertManager.flag(protocolPlayer, check2, flagInfos);
    }

    public void punish(Check<?> check) {
        LPXBukkit lPXBukkit = this.lpx;
        AlertManager alertManager = lPXBukkit.getAlertManager();
        Check check2 = ((Check) check);
        ProtocolPlayerImpl protocolPlayerImpl = this;
        ProtocolPlayer protocolPlayer = ((ProtocolPlayer) protocolPlayerImpl);
        alertManager.punish(protocolPlayer, check2);
    }

    public void kick(String text) {
        if (!this.isKicked()) {
            this.enabled2 = true;
            ProtocolPlayerImpl protocolPlayerImpl = this;
            ProtocolPlayer protocolPlayer = ((ProtocolPlayer) protocolPlayerImpl);
            this.lpx.forceKick(protocolPlayer, text);
        }
    }

    public boolean isKicked() {
        return this.enabled2 || !this.getPlayer().isPresent();
    }

    public boolean ignoreChecks() {
        if (!this.enabled3) {
            LPXBukkit lPXBukkit = this.lpx;
            ConfigManager configManager = lPXBukkit.getConfigManager();
            GeneralOptions options = configManager.getOptions();
            if (!options.isEnabled5() || !this.hasExemptPermission()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasExemptPermission() {
        return this.enabled;
    }

    public LPX lpx() {
        return this.lpx;
    }
}
