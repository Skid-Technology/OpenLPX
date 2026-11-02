package it.ytnoos.lpx.check.impl.position;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.PrinterStatus;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.config.PrinterOptions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import it.ytnoos.lpx.player.ProtocolPlayerImpl;
import it.ytnoos.lpx.utils.TimeUtils;

public class PositionACheck extends AbstractCheck<WrapperPlayClientPlayerFlying> {

    public PositionACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        if (!wrapperPlayClientPlayerFlying.hasPositionChanged()) {
            return CheckResult.ALLOW;
        }
        Location location = wrapperPlayClientPlayerFlying.getLocation();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        Double value = ((Double) (double) 3.0E7);
        Double value2 = ((Double) x);
        double value3 = value2;
        double value4 = value3;
        if (this.testValue(value4, ((double) value))) {
            Double value5 = ((Double) (double) 2.0E7);
            Double value6 = ((Double) y);
            value3 = value6;
            double value7 = value3;
            if (this.testValue(value7, ((double) value5))) {
                Double value8 = ((Double) (double) 3.0E7);
                Double value9 = ((Double) z);
                value3 = value9;
                double value10 = value3;
                if (this.testValue(value10, ((double) value8))) {
                    if (protocolPlayer.getPrinterData().getPrinterStatus()
                            == PrinterStatus.AUTOMATIC) {
                        long lastPlace = protocolPlayer.getPrinterData().getLastPlace();
                        Long value11 = ((Long) lastPlace);
                        long value12 = TimeUtils.calculateTime(value11);
                        ProtocolPlayerImpl protocolPlayerImpl = (ProtocolPlayerImpl) protocolPlayer;
                        LPXBukkit lpx = protocolPlayerImpl.getLpx();
                        ConfigManager configManager = lpx.getConfigManager();
                        GeneralOptions options = configManager.getOptions();
                        PrinterOptions printerOptions = options.getPrinterOptions();
                        if (value12 > printerOptions.getLimit2()) {
                            protocolPlayer.getPrinterData().setPrinterMode(false);
                        }
                    }
                    return CheckResult.ALLOW;
                }
            }
        }
        FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("x", x), null, null};
        flagInfos[1] = FlagInfo.of("y", y);
        flagInfos[2] = FlagInfo.of("z", z);
        this.flagViolation(wrapperPlayClientPlayerFlying, flagInfos);
        return CheckResult.DENY;
    }

    public boolean isPreVia() {
        return true;
    }

    private boolean testValue(double value, double value2) {
        return !Double.isNaN(value) && !Double.isInfinite(value) && !(Math.abs(value) > value2);
    }
}
