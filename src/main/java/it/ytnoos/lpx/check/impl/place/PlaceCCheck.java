package it.ytnoos.lpx.check.impl.place;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlaceCCheck extends AbstractCheck<WrapperPlayClientPlayerDigging> {

    private double max;

    public PlaceCCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Double value = ((Double) (double) 50.0);
        this.max = this.calculateValue((String) "max", ((double) value));
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPlayerDigging wrapperPlayClientPlayerDigging) {
        Player player = (Player) protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        } else if (wrapperPlayClientPlayerDigging.getAction().ordinal() > 2) {
            return CheckResult.ALLOW;
        } else {
            Location location = player.getLocation();
            Vector3i blockPosition = wrapperPlayClientPlayerDigging.getBlockPosition();
            double value = location.getX() - (blockPosition.getX() + 0.5);
            double value2 = location.getY() - (blockPosition.getY() + 0.5) + 1.5;
            double value3 = location.getZ() - (blockPosition.getZ() + 0.5);
            double value4 = value * value + value2 * value2 + value3 * value3;
            if (value4 > this.max) {
                FlagInfo[] flagInfos = new FlagInfo[4];
                flagInfos[0] = FlagInfo.of("distanceX", value);
                flagInfos[1] = FlagInfo.of("distanceY", value2);
                flagInfos[2] = FlagInfo.of("distanceZ", value3);
                flagInfos[3] = FlagInfo.of("distance", (int) value4);
                this.increaseBuffer(wrapperPlayClientPlayerDigging, flagInfos);
                return CheckResult.DENY;
            } else {
                this.decayBuffer();
                return CheckResult.ALLOW;
            }
        }
    }
}
