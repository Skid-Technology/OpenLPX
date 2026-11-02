package it.ytnoos.lpx.check.impl.position;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.EntityScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PositionCCheck extends AbstractCheck<WrapperPlayClientPlayerFlying> {

    private int max;

    private long lastTeleport;

    private static final String TEXT = "max";

    private static final int DEFAULT_MAX_POSITION = 300000;

    private static final long TELEPORT_INTERVAL_MILLIS = 1000L;

    public PositionCCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        String text = TEXT;
        this.max = checkConfiguration.getInt(this.createText(text), DEFAULT_MAX_POSITION);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        if (wrapperPlayClientPlayerFlying.hasPositionChanged()
                && Math.abs(wrapperPlayClientPlayerFlying.getLocation().getY()) > this.max) {
            this.teleportBelow(protocolPlayer);
            FlagInfo[] flagInfos =
                    new FlagInfo[] {
                        FlagInfo.of("y", wrapperPlayClientPlayerFlying.getLocation().getY())
                    };
            this.flagViolation(wrapperPlayClientPlayerFlying, flagInfos);
            return CheckResult.DENY;
        } else {
            return CheckResult.ALLOW;
        }
    }

    private void teleportBelow(ProtocolPlayer protocolPlayer) {
        if (System.currentTimeMillis() - this.lastTeleport >= TELEPORT_INTERVAL_MILLIS) {
            this.lastTeleport = System.currentTimeMillis();
            Optional<Player> player = protocolPlayer.getPlayer();
            player.ifPresent(
                    result -> {
                        EntityScheduler entityScheduler = FoliaScheduler.getEntityScheduler();
                        LPXPlugin plugin = this.getOptions().getCheckOptions().getPlugin();
                        Player player2 = ((Player) result);
                        entityScheduler.run(
                                result,
                                plugin,
                                result2 -> {
                                    if (player2.getLocation().getY() > this.max) {
                                        player2.teleport(
                                                player2.getLocation()
                                                        .clone()
                                                        .subtract(0.0, this.max - 5, 0.0));
                                    }
                                },
                                () -> {});
                    });
        }
    }
}
