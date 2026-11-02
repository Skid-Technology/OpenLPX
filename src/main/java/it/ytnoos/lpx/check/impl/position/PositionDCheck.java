package it.ytnoos.lpx.check.impl.position;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;

import org.bukkit.configuration.file.FileConfiguration;

public class PositionDCheck extends AbstractCheck<WrapperPlayClientPlayerFlying> {

    private int max;

    public PositionDCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        FileConfiguration checkConfiguration = this.getCheckConfiguration();
        this.max = checkConfiguration.getInt(this.createText((String) "max"), 1000000);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        if (!wrapperPlayClientPlayerFlying.hasRotationChanged()) {
            return CheckResult.ALLOW;
        } else {
            float yaw = wrapperPlayClientPlayerFlying.getLocation().getYaw();
            if (Math.abs(yaw) > this.max) {
                FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("yaw", yaw)};
                this.flagViolation(wrapperPlayClientPlayerFlying, flagInfos);
                return CheckResult.DENY;
            } else {
                return CheckResult.ALLOW;
            }
        }
    }
}
