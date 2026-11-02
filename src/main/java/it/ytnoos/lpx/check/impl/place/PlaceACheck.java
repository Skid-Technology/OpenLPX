package it.ytnoos.lpx.check.impl.place;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import it.ytnoos.lpx.utils.TimeUtils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlaceACheck extends AbstractCheck<WrapperPlayClientPlayerBlockPlacement> {

    private double value;

    private long time;

    public PlaceACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Double value2 = ((Double) (double) 100.0);
        String text = (String) "max";
        String text2 = text;
        this.value = this.calculateValue(text2, ((double) value2));
        Long value3 = ((Long) 100L);
        String text3 = "teleport-grace";
        text = text3;
        String text4 = text;
        this.time = this.calculateTime(text4, ((long) value3));
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPlayerBlockPlacement wrapperPlayClientPlayerBlockPlacement) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        } else {
            long value2 = this.time + Math.max(0, (int) protocolPlayer.getPlayerPing());
            long lastTeleport = protocolPlayer.getLastTeleport();
            Long value3 = ((Long) lastTeleport);
            if (TimeUtils.calculateTime(value3) < value2) {
                return CheckResult.ALLOW;
            } else {
                Vector3i blockPosition = wrapperPlayClientPlayerBlockPlacement.getBlockPosition();
                double x = blockPosition.getX();
                double z = blockPosition.getZ();
                if (x == -1.0 && z == -1.0) {
                    return CheckResult.ALLOW;
                } else {
                    Location location = player.getLocation();
                    double value4 = Math.abs(x - location.getX()) + Math.abs(z - location.getZ());
                    if (value4 > this.value) {
                        FlagInfo[] flagInfos = new FlagInfo[5];
                        flagInfos[0] = FlagInfo.of("x", x);
                        flagInfos[1] = FlagInfo.of("z", z);
                        flagInfos[2] = FlagInfo.of("player-x", location.getX());
                        flagInfos[3] = FlagInfo.of("player-z", location.getZ());
                        flagInfos[4] = FlagInfo.of("distance", (int) value4);
                        this.increaseBuffer(wrapperPlayClientPlayerBlockPlacement, flagInfos);
                        return CheckResult.DENY;
                    } else {
                        this.decayBuffer();
                        return CheckResult.ALLOW;
                    }
                }
            }
        }
    }
}
